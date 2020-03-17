package service;

import serverObjects.BASE_CLIENT_OBJECT;

public abstract class MyBaseService implements IMyService {

    public static final int LOGIC = 0;
    public static final int OP_AVG_MOVE = 1;
    public static final int EQUAL_MOVE = 2;
    public static final int REGULAR_LISTS = 3;
    public static final int MYSQL_RUNNER = 4;
    public static final int DDE_READER = 5;
    protected int sleepCount = 0;

    BASE_CLIENT_OBJECT client;

    public MyBaseService( BASE_CLIENT_OBJECT client ) {
        this.client = client;
        client.getMyServiceHandler().addService( this );
    }

    public MyBaseService() {
    }

    public void execute( int sleepCount ) {
        if ( sleepCount % getSleep() == 0 ) {
            this.sleepCount = sleepCount;
            go();
        }
    }

    // --------- Getters and setters --------- //
    public BASE_CLIENT_OBJECT getClient() {
        return client;
    }
}