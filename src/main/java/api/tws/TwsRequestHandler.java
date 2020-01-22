package api.tws;

import api.Downloader;
import com.ib.client.Contract;
import options.Option;
import options.Options;
import options.Strike;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.TwsData;
import threads.MyThread;

public class TwsRequestHandler {

    // Variables
    BASE_CLIENT_OBJECT client;
    Downloader downloader;
    Runner runner;

    // Constructor
    public TwsRequestHandler( BASE_CLIENT_OBJECT client ) {
        this.client = client;
        downloader = Downloader.getInstance( );
        runner = new Runner( );
    }

    public void startRunner() {
        getRunner( ).getHandler( ).start( );
    }

    public void closeRunner() {
        getRunner( ).getHandler( ).close( );
    }

    // Functions
    public void requestFutreAndIndex() {

        TwsData twsData = client.getTwsData( );

        System.out.println( client.getName( ) + "  Index id: " + twsData.getIndexId( ) );
        System.out.println( client.getName( ) + "  Future id: " + twsData.getFutureId( ) );

        // Future
        if ( twsData.getFutureContract( ) != null ) {
            downloader.reqMktData( twsData.getFutureId( ), twsData.getFutureContract( ) );
        }

        // Index
        if ( twsData.getIndexContract( ) != null ) {
            downloader.reqMktData( twsData.getIndexId( ), twsData.getIndexContract( ) );
        }


    }

    public void requestOptions( Options options ) {

        Contract contract = options.getTwsContract( );

        for ( Strike strike : options.getStrikes( ) ) {
            try {
                Option call = strike.getCall( );
                Option put = strike.getPut( );

                // ----- Call ----- //
                contract.strike( call.getStrike( ) );
                contract.right( call.getSide( ).toUpperCase( ) );

                // Request
                downloader.reqMktData( call.getId( ), contract );

                // ----- Put ----- //
                contract.strike( put.getStrike( ) );
                contract.right( put.getSide( ).toUpperCase( ) );

                // Request
                downloader.reqMktData( put.getId( ), contract );

                Thread.sleep( 100 );
            } catch ( Exception e ) {
                e.printStackTrace( );
            }

        }

    }

    public Runner getRunner() {
        if ( runner == null ) {
            runner = new Runner( );
        }
        return runner;
    }

    public void setRunner( Runner runner ) {
        this.runner = runner;
    }

    // ---------- Runner ---------- //
    class Runner extends MyThread implements Runnable {

        public Runner() {
            super( client );
            setName( "TwsRunner" );
        }

        @Override
        public void run() {

            while ( isRun( ) ) {
                try {
                    // Sleep
                    Thread.sleep( 1000 );

                    handle( );

                } catch ( InterruptedException e ) {
                    getHandler( ).close( );
                } catch ( Exception e ) {
                    e.printStackTrace( );
                }

            }

        }

        private void handle() {
            try {

                // For each options
                for ( Options options : getClient( ).getOptionsHandler( ).getOptionsList( ) ) {

                    // Request options on first time
                    if ( !options.isRequested( ) ) {

                        if ( getClient( ).getFuture( ) != 0 ) {
                            requestOptions( options );
                            options.setRequested( true );
                        }

                    }

                    // Look for adding new requests


                }

            } catch ( Exception e ) {
                e.printStackTrace( );
            }
        }

        @Override
        public void initRunnable() {
            setRunnable( this );
        }
    }
}

