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

        // Options op lists
        for ( Options options : getClient( ).getOptionsHandler( ).getOptionsList( ) ) {
            options.getOpList( ).add( options.getOp( ) );
            options.getEqualMoveCalculator( ).getMoveIndexListIndex( ).add( options.getEqualMoveCalculator( ).getMoveIndex( ) );
            options.getOpAvgList( ).add( options.getContract().getVal() );
        }

        // For each list in listMap
        for ( Map.Entry< Integer, MyList > entry : getClient( ).getListMap( ).entrySet( ) ) {
            // Get the current list
            MyList myList = entry.getValue( );

            // Append the list object ( Val )
            myList.addVal( );

        }

    }
}
