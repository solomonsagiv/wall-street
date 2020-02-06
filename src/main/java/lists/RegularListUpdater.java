package lists;

import options.Options;
import serverObjects.BASE_CLIENT_OBJECT;
import threads.MyThread;

import java.util.Map;

// Regular list updater
public class RegularListUpdater extends MyThread implements Runnable {

    // Variables
    int sleep = 1000;
    int sleepCounter = 0;

    // Constructor
    public RegularListUpdater( BASE_CLIENT_OBJECT client ) {
        super( client );
        setName( "LIST UPDATER" );
    }

    @Override
    public void initRunnable() {
        setRunnable( this );
    }

    @Override
    public void run() {

        while ( isRun( ) ) {
            try {
                // Sleep
                Thread.sleep( sleep );

                // Insert objects
                insert( );

                // Handel sleep
                sleepCounter += sleep;
            } catch ( InterruptedException e ) {
                break;
            }
        }
    }

    private void insert() {
        for ( MyList list: getClient().getLists() ) {
            list.addVal( );
        }
    }
}
