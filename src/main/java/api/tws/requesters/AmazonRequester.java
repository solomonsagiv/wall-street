package api.tws.requesters;

import api.Downloader;
import api.tws.ITwsRequester;
import com.ib.client.TickAttr;
import options.Options;
import serverObjects.stockObjects.Amazon;
import serverObjects.stockObjects.Apple;
import tws.TwsContractsEnum;

import java.util.ArrayList;

public class AmazonRequester implements ITwsRequester {

    ArrayList< Options > optionsList;
    Amazon amazon;

    @Override
    public void request( Downloader downloader ) {
        try {
            amazon = Amazon.getInstance();
            optionsList = amazon.getOptionsHandler( ).getOptionsList( );

            // Index
            downloader.reqMktData( amazon.getTwsHandler( ).getMyContract( TwsContractsEnum.INDEX ).getMyId( ), amazon.getTwsHandler( ).getMyContract( TwsContractsEnum.INDEX ) );

            // Options
            amazon.getTwsHandler( ).requestOptions( amazon.getOptionsHandler( ).getOptionsList( ) );
        } catch ( Exception e ) {
            e.printStackTrace( );
        }
    }

    @Override
    public void reciever( int tickerId, int field, double price, TickAttr attribs ) {
        int index;
        int minID, maxID;

        // ---------- Apple ---------- //
        index = amazon.getTwsHandler( ).getMyContract( TwsContractsEnum.INDEX ).getMyId( );

        if ( tickerId == index && price > 0 ) {
            // Last
            if ( field == 4 ) {
                amazon.setIndex( price );
            }
            // Bid
            if ( field == 1 ) {
                amazon.setIndexBid( price );
            }
            // Ask
            if ( field == 2 ) {
                amazon.setIndexAsk( price );
            }

            // Bid
            if ( field == 6 ) {
                amazon.setHigh( price );
            }
            // Ask
            if ( field == 7 ) {
                amazon.setLow( price );
            }

            // Base
            if ( field == 9 ) {
                amazon.setBase( price );
            }

            // Open
            if ( field == 14 ) {
                amazon.setOpen( price );
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
