package shlomi.logic;

import arik.Arik;
import serverObjects.BASE_CLIENT_OBJECT;
import threads.MyThread;

import java.time.LocalTime;

public class ShlomiTrader {

    // Local variables
    BASE_CLIENT_OBJECT client;

    Runner runner;
    Thread orderPlacedChecker;

    // Trading variebles
    Arik arik;

    // Constructor
    public ShlomiTrader( BASE_CLIENT_OBJECT client, Algoritem algoritem ) {
        this.client = client;
//        this.algoritem = algoritem;
        runner = new Runner( client );
    }

    private String str( Object o ) {
        return String.valueOf( o );
    }

    // Return the oposite value in minus or plus
    public int oposit( int num ) {
        return num * -1;
    }

    public double floor( double d ) {
        return Math.floor( d * 100 ) / 100;
    }

    // Getters and Setters
    public BASE_CLIENT_OBJECT getStock() {
        return client;
    }

    public void setStock( BASE_CLIENT_OBJECT stock ) {
        this.client = stock;
    }

    public Runner getShlomiRunner() {
        return runner;
    }

    public void setShlomiRunner( Runner runner ) {
        this.runner = runner;
    }

    public String toStringVertical() {
        String string = toString( );
        String[] array = string.split( ", " );
        String returnString = "";
        for ( int i = 0; i < array.length; i++ ) {
            returnString += array[ i ] + "\n";
        }
        return returnString;
    }

    // Shlomi runner
    class Runner extends MyThread implements Runnable {

        public Runner( BASE_CLIENT_OBJECT client ) {
            super( client );
            setName( "Shlomi trader" );
        }

        @Override
        public void initRunnable() {
            setRunnable( this );
        }

        // Constructor

        // Run methode
        @Override
        public void run() {
            running( );
        }

        // Running loop
        private void running() {

            while ( isRun( ) ) {
                try {

                    System.out.println( "Shlomi trader" );

                    // Option algoritam
//                    algoritem.doLogic( );

                    // Close
                    if ( LocalTime.now( ).isAfter( client.getEndOfIndexTrading( ) ) ) {
                        getHandler( ).close( );
                    }

                    // Sleep
                    Thread.sleep( 1000 );
                } catch ( InterruptedException e ) {
                    break;
                }
            }
        }

    }

}
