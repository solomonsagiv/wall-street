package service;

import serverObjects.BASE_CLIENT_OBJECT;
import threads.MyThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyServiceHandler extends MyThread implements Runnable {

    // Variables
    private List< MyBaseService > servies;
    private ExecutorService executor;
    final int sleep = 100;
    int sleepCount = 0;

    // Constructor
    public MyServiceHandler( BASE_CLIENT_OBJECT client ) {
        super( client );
        servies = new ArrayList<>();
    }

    @Override
    public void run() {
        init( );
    }

    private void init() {

        executor = Executors.newCachedThreadPool();

        while ( isRun( ) ) {
            try {

                Thread.sleep( sleep );

                executServices( );

                initSleepCount( );

            } catch ( InterruptedException e ) {
                e.printStackTrace( );
                executor.shutdownNow();
                break;
            }
        }
    }

    public void addService( MyBaseService newService ) {
        if ( !isExist( newService ) ) {
            servies.add( newService );
        }
    }

    public boolean isExist( MyBaseService newService ) {
        boolean exist = false;

        for ( MyBaseService service: servies ) {
            if ( service.equals( newService ) ) {
                exist = true;
                break;
            }
        }

        return exist;
    }

    public void removeService( MyBaseService service ) {
        servies.remove( service );
    }

    private void initSleepCount() {
        if ( sleepCount == 10000 ) {
            sleepCount = 0;
        }
        sleepCount += sleep;
    }

    private void executServices() {
        for ( MyBaseService service : servies ) {
            service.execute( sleepCount );
        }
    }

    @Override
    public void initRunnable() {
        setRunnable( this );
    }
}
