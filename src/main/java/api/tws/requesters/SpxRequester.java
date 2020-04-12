package api.tws.requesters;

import api.Downloader;
import api.tws.ITwsRequester;
import com.ib.client.TickAttr;
import options.Options;
import serverObjects.indexObjects.Spx;
import tws.TwsContractsEnum;

import java.util.ArrayList;

public class SpxRequester implements ITwsRequester {

    ArrayList< Options > optionsList;
    Spx spx;


    @Override
    public void request( Downloader downloader ) {
        try {

            spx = Spx.getInstance();
            optionsList = spx.getOptionsHandler( ).getOptionsList( );

            // Index
            downloader.reqMktData( spx.getTwsHandler( ).getMyContract( TwsContractsEnum.INDEX ).getMyId( ), spx.getTwsHandler( ).getMyContract( TwsContractsEnum.INDEX ) );

            // Options
            spx.getTwsHandler( ).requestOptions( spx.getOptionsHandler( ).getOptionsList( ) );

        } catch ( Exception e ) {
            e.printStackTrace( );
        }
    }

    @Override
    public void reciever( int tickerId, int field, double price, TickAttr attribs ) {
        int index;
        int minID, maxID;

        // ---------- Apple ---------- //
        index = spx.getTwsHandler( ).getMyContract( TwsContractsEnum.INDEX ).getMyId( );

        if ( tickerId == index && price > 0 ) {
            // Last
            if ( field == 4 ) {
                spx.setIndex( price );
            }
            // Bid
            if ( field == 1 ) {
                spx.setIndexBid( price );
            }
            // Ask
            if ( field == 2 ) {
                spx.setIndexAsk( price );
            }

            // Bid
            if ( field == 6 ) {
                spx.setHigh( price );
            }
            // Ask
            if ( field == 7 ) {
                spx.setLow( price );
            }

            // Base
            if ( field == 9 ) {
                spx.setBase( price );
            }
        }

        for ( Options options : optionsList ) {
            minID = options.getMinId( );
            maxID = options.getMaxId( );

            if ( tickerId >= minID && tickerId <= maxID && price > 0 ) {
                // Bid
                if ( field == 1 ) {
                    options.getOptionById( tickerId ).setBid( price );
                }
                // Ask
                if ( field == 2 ) {
                    options.getOptionById( tickerId ).setAsk( price );
                }
            }
        }
    }
}
