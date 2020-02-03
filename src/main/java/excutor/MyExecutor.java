package excutor;

import locals.MyObjects;
import serverObjects.BASE_CLIENT_OBJECT;
import threads.MyThread;

import java.time.LocalDate;
import java.time.LocalTime;

public class MyExecutor extends MyThread implements Runnable {

    int sleepCount = 0;
    int sleep = 1000;

    public MyExecutor( BASE_CLIENT_OBJECT client ) {
        super( client );
    }

    @Override
    public void run() {

        while ( isRun( ) ) {
            try {

                // Sleep
                Thread.sleep( sleep );

                // Execute
                execute( );

                handleSleep();

            } catch ( InterruptedException e ) {
                break;
            } catch ( Exception e ) {
                e.printStackTrace( );
            }
        }

    }

    private void execute() {

        // For each execute object
        for ( MyObjects.MyBaseObject object : getClient( ).getMyObjects( ) ) {
            // Every time

            new Thread( () -> {
                object.initMe( sleepCount );
            } ).start();
        }

    }

    private void handleSleep() {
        if ( sleepCount == 10000 ) {
            sleepCount = 0;
        }
        sleepCount += sleep;
    }

    @Override
    public void initRunnable() {
        setRunnable( this );
    }
}
