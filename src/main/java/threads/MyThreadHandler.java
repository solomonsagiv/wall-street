package threads;

import arik.Arik;

import javax.swing.*;

public class MyThreadHandler {

    // Variables
    MyThread myThread;
    Thread thread;

    // Constructor
    public MyThreadHandler( MyThread myThread ) {
        this.myThread = myThread;
    }

    // ---------- Functions ---------- //

    // Start
    public void start() {

        if ( thread == null ) {

            myThread.setRun( true );
            thread = new Thread( myThread.getRunnable( ) );
            thread.start( );
        }

        try {
            Arik.getInstance( ).sendMessage( myThread.getClient( ).getName( ) + " " + myThread.getName( ) + " Started" );
        } catch ( Exception e ) {
            Arik.getInstance( ).sendMessage( myThread.getName( ) + " Started" );
        }
    }

    // Close
    public void close() {

        if ( thread != null ) {
            thread.interrupt( );
            myThread.setRun( false );
            thread = null;
        }
        JOptionPane.showMessageDialog( null, thread.getName() );
        try {
            Arik.getInstance( ).sendMessage( myThread.getClient( ).getName( ) + " " + myThread.getName( ) + " Closed" );
        } catch ( Exception e ) {
            Arik.getInstance( ).sendMessage( myThread.getName( ) + " Closed" );
        }
    }

    // Restart
    public void restart() {

        close( );
        start( );

        try {
            Arik.getInstance( ).sendMessage( myThread.getClient( ).getName( ) + " " + myThread.getName( ) + " Restarted" );
        } catch ( Exception e ) {
            Arik.getInstance( ).sendMessage( myThread.getName( ) + " Restarted" );
        }
    }
}
