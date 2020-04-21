package api.tws.requesters;

import api.Downloader;
import api.tws.ITwsRequester;
import com.ib.client.TickAttr;
import options.Options;
import serverObjects.stockObjects.Amazon;
import serverObjects.stockObjects.Ulta;
import tws.TwsContractsEnum;

import java.util.ArrayList;

public class UltaRequester implements ITwsRequester {

    ArrayList< Options > optionsList;
    Ulta ulta;

    @Override
    public void request( Downloader downloader ) {
        try {
            ulta = Ulta.getInstance();
            optionsList = ulta.getOptionsHandler( ).getOptionsList( );

            // Index
            downloader.reqMktData( ulta.getTwsHandler( ).getMyContract( TwsContractsEnum.INDEX ).getMyId( ), ulta.getTwsHandler( ).getMyContract( TwsContractsEnum.INDEX ) );

            // Options
            ulta.getTwsHandler( ).requestOptions( ulta.getOptionsHandler( ).getOptionsList( ) );
        } catch ( Exception e ) {
            e.printStackTrace( );
        }
    }

    @Override
    public void reciever( int tickerId, int field, double price, TickAttr attribs ) {
        int index;
        int minID, maxID;

        // ---------- Apple ---------- //
        index = ulta.getTwsHandler( ).getMyContract( TwsContractsEnum.INDEX ).getMyId( );

        if ( tickerId == index && price > 0 ) {
            // Last
            if ( field == 4 ) {
                ulta.setIndex( price );
            }
            // Bid
            if ( field == 1 ) {
                ulta.setIndexBid( price );
            }
            // Ask
            if ( field == 2 ) {
                ulta.setIndexAsk( price );
            }

            // Bid
            if ( field == 6 ) {
                ulta.setHigh( price );
            }
            // Ask
            if ( field == 7 ) {
                ulta.setLow( price );
            }

            // Base
            if ( field == 9 ) {
                ulta.setBase( price );
            }

            // Open
            if ( field == 14 ) {
                ulta.setOpen( price );
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
