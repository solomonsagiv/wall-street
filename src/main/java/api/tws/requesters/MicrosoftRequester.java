package api.tws.requesters;

import api.Downloader;
import api.tws.ITwsRequester;
import com.ib.client.TickAttr;
import exp.Exp;
import options.Options;
import serverObjects.stockObjects.Microsoft;
import tws.TwsContractsEnum;

import java.util.ArrayList;

public class MicrosoftRequester implements ITwsRequester {
    int index;
    int minID, maxID;
    ArrayList< Exp > exp;
    Microsoft microsoft;

    boolean requested = false;

    @Override
    public void request( Downloader downloader ) {
        try {
            microsoft = Microsoft.getInstance( );
            exp = microsoft.getExps( ).getExpList( );
            index = microsoft.getTwsHandler( ).getMyContract( TwsContractsEnum.INDEX ).getMyId( );

            // Index
            downloader.reqMktData( microsoft.getTwsHandler( ).getMyContract( TwsContractsEnum.INDEX ).getMyId( ), microsoft.getTwsHandler( ).getMyContract( TwsContractsEnum.INDEX ) );

            // Options
            microsoft.getTwsHandler( ).requestOptions( microsoft.getExps( ).getExpList( ) );

            requested = true;
        } catch ( Exception e ) {
            e.printStackTrace( );
        }
    }

    @Override
    public void reciever( int tickerId, int field, double price, TickAttr attribs ) {

        try {
            if ( tickerId == index && price > 0 ) {
                // Last
                if ( field == 4 ) {
                    microsoft.setIndex( price );
                }
                // Bid
                if ( field == 1 ) {
                    microsoft.setIndexBid( price );
                }
                // Ask
                if ( field == 2 ) {
                    microsoft.setIndexAsk( price );
                }

                // Bid
                if ( field == 6 ) {
                    microsoft.setHigh( price );
                }
                // Ask
                if ( field == 7 ) {
                    microsoft.setLow( price );
                }

                // Base
                if ( field == 9 ) {
                    microsoft.setBase( price );
                }

                // Open
                if ( field == 14 ) {
                    microsoft.setOpen( price );
                }
            }

            for ( Exp exp : this.exp ) {

                Options options = exp.getOptions( );
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
        } catch ( Exception e ) {
            e.printStackTrace( );
        }
    }

    @Override
    public void sizeReciever( int tickerId, int field, int size ) {

    }

    @Override
    public boolean isRequested() {
        return requested;
    }
}
