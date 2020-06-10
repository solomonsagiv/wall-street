package api.tws.requesters;

import api.Downloader;
import api.tws.ITwsRequester;
import com.ib.client.TickAttr;
import exp.Exp;
import options.Options;
import serverObjects.stockObjects.Amd;
import tws.TwsContractsEnum;

import java.util.ArrayList;

public class AmdRequester implements ITwsRequester {

    ArrayList< Exp > exps;
    Amd amd;

    @Override
    public void request( Downloader downloader ) {
        try {
            amd = Amd.getInstance();
            exps = amd.getExps( ).getExpList( );

            // Index
            downloader.reqMktData( amd.getTwsHandler( ).getMyContract( TwsContractsEnum.INDEX ).getMyId( ), amd.getTwsHandler( ).getMyContract( TwsContractsEnum.INDEX ) );

            // Options
            amd.getTwsHandler( ).requestOptions( amd.getExps( ).getExpList( ) );
        } catch ( Exception e ) {
            e.printStackTrace( );
        }
    }

    @Override
    public void reciever( int tickerId, int field, double price, TickAttr attribs ) {
        int index;
        int minID, maxID;

        // ---------- Apple ---------- //
        index = amd.getTwsHandler( ).getMyContract( TwsContractsEnum.INDEX ).getMyId( );

        if ( tickerId == index && price > 0 ) {
            // Last
            if ( field == 4 ) {
                amd.setIndex( price );
            }
            // Bid
            if ( field == 1 ) {
                amd.setIndexBid( price );
            }
            // Ask
            if ( field == 2 ) {
                amd.setIndexAsk( price );
            }

            // Bid
            if ( field == 6 ) {
                amd.setHigh( price );
            }
            // Ask
            if ( field == 7 ) {
                amd.setLow( price );
            }

            // Base
            if ( field == 9 ) {
                amd.setBase( price );
            }

            // Open
            if ( field == 14 ) {
                amd.setOpen( price );
            }
        }

        for ( Exp exp : exps ) {

            Options options = exp.getOptions();
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

    @Override
    public void sizeReciever( int tickerId, int field, int size ) {

    }
}
