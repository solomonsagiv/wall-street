package api.tws.requesters;

import api.Downloader;
import api.tws.ITwsRequester;
import api.tws.TwsHandler;
import com.ib.client.TickAttr;
import options.Options;
import options.OptionsEnum;
import serverObjects.indexObjects.Spx;
import tws.TwsContractsEnum;

public class SpxRequester implements ITwsRequester {

    Spx spx;
    int indexId, futureId, futureFarId;
    Options optionsQuarter, optionsQuarterFar;

    @Override
    public void request( Downloader downloader ) {
        try {
            init( );

            TwsHandler twsHandler = spx.getTwsHandler( );

            // Index
            downloader.reqMktData( twsHandler.getMyContract( TwsContractsEnum.INDEX ).getMyId( ), twsHandler.getMyContract( TwsContractsEnum.INDEX ) );

            // Futures
            // Quarter
            downloader.reqMktData( twsHandler.getMyContract( TwsContractsEnum.FUTURE ).getMyId( ), twsHandler.getMyContract( TwsContractsEnum.FUTURE ) );
            downloader.reqMktData( twsHandler.getMyContract( TwsContractsEnum.FUTURE_FAR ).getMyId( ), twsHandler.getMyContract( TwsContractsEnum.FUTURE_FAR ) );

        } catch ( Exception e ) {
            e.printStackTrace( );
        }
    }

    private void init() {
        spx = Spx.getInstance( );
        optionsQuarter = spx.getOptionsHandler( ).getOptions( OptionsEnum.QUARTER );
        optionsQuarterFar = spx.getOptionsHandler( ).getOptions( OptionsEnum.QUARTER_FAR );

        indexId = spx.getTwsHandler( ).getMyContract( TwsContractsEnum.INDEX ).getMyId( );
        futureId = spx.getTwsHandler( ).getMyContract( TwsContractsEnum.FUTURE ).getMyId( );
        futureFarId = spx.getTwsHandler( ).getMyContract( TwsContractsEnum.FUTURE_FAR ).getMyId( );
    }

    @Override
    public void reciever( int tickerId, int field, double price, TickAttr attribs ) {

        if ( spx.isStarted( ) ) {

            // ---------- Future ---------- //
            if ( tickerId == futureId && price > 0 ) {
                // Bid
                if ( field == 1 ) {
                    optionsQuarter.setFutureBid( price );
                }
                // Ask
                if ( field == 2 ) {
                    optionsQuarter.setFutureAsk( price );
                }
            }

            // ---------- Future far ---------- //
            if ( tickerId == futureFarId && price > 0 ) {
                // Bid
                if ( field == 1 ) {
                    optionsQuarterFar.setFutureBid( price );
                }
                // Ask
                if ( field == 2 ) {
                    optionsQuarterFar.setFutureAsk( price );
                }
            }
        }

    }
}
