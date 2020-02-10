package excutor;

import locals.MyObjects;
import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;

public class MyExecutorService extends MyBaseService {

    // Variables
    int sleepCount = 0;
    int sleep = 150;

    // Constructor
    public MyExecutorService( BASE_CLIENT_OBJECT client, String name, int type, int sleep ) {
        super( client, name, type, sleep );
    }

    @Override
    public void go() {

        // Execute
        execute( );

        // Handle sleep
        handleSleep( );

    }

    private void execute() {
        // For each execute object
        for ( MyObjects.MyBaseObject object : getClient( ).getMyObjects( ) ) {
            // Every time
            new Thread( () -> {
                object.initMe( sleepCount );
            } ).start( );
        }
    }

    private void handleSleep() {
        if ( sleepCount == 10000 ) {
            sleepCount = 0;
        }
        sleepCount += sleep;
    }

}

