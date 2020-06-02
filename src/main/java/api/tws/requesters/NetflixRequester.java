package api.tws.requesters;

import api.Downloader;
import api.tws.ITwsRequester;
import com.ib.client.TickAttr;
import options.Options;
import serverObjects.stockObjects.Netflix;
import tws.TwsContractsEnum;

import java.util.ArrayList;

public class NetflixRequester implements ITwsRequester {

    ArrayList< Options > optionsList;
    Netflix netflix;

    @Override
    public void request( Downloader downloader ) {
        try {
            netflix = Netflix.getInstance();
            optionsList = netflix.getExpHandler( ).getExpList( );

            // Index
            downloader.reqMktData( netflix.getTwsHandler( ).getMyContract( TwsContractsEnum.INDEX ).getMyId( ), netflix.getTwsHandler( ).getMyContract( TwsContractsEnum.INDEX ) );

            // Options
            netflix.getTwsHandler( ).requestOptions( netflix.getExpHandler( ).getExpList( ) );
        } catch ( Exception e ) {
            e.printStackTrace( );
        }
    }

    @Override
    public void reciever( int tickerId, int field, double price, TickAttr attribs ) {
        int index;
        int minID, maxID;

        // ---------- Apple ---------- //
        index = netflix.getTwsHandler( ).getMyContract( TwsContractsEnum.INDEX ).getMyId( );

        if ( tickerId == index && price > 0 ) {
            // Last
            if ( field == 4 ) {
                netflix.setIndex( price );
            }
            // Bid
            if ( field == 1 ) {
                netflix.setIndexBid( price );
            }
            // Ask
            if ( field == 2 ) {
                netflix.setIndexAsk( price );
            }

            // Bid
            if ( field == 6 ) {
                netflix.setHigh( price );
            }
            // Ask
            if ( field == 7 ) {
                netflix.setLow( price );
            }

            // Base
            if ( field == 9 ) {
                netflix.setBase( price );
            }

            // Open
            if ( field == 14 ) {
                netflix.setOpen( price );
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

    @Override
    public void sizeReciever( int tickerId, int field, int size ) {

    }
}
