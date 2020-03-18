package service;

import serverObjects.BASE_CLIENT_OBJECT;

public abstract class MyBaseService implements IMyService {

    protected int sleepCount = 0;

    BASE_CLIENT_OBJECT client;
    ServiceEnum type;

    public MyBaseService( BASE_CLIENT_OBJECT client ) {
        this.client = client;
        client.getMyServiceHandler( ).addService( this );
    }

    public MyBaseService() {}

    public void execute( int sleepCount ) {
        if ( sleepCount % getSleep( ) == 0 ) {
            this.sleepCount = sleepCount;
            try {
                go( );
            } catch ( Exception e ) {
                System.out.println( getClient() + " " + getName() );
                e.printStackTrace( );
            }
        }
    }

    // --------- Getters and setters --------- //
    public BASE_CLIENT_OBJECT getClient() {
        return client;
    }

    public ServiceEnum getType() {
        return type;
    }

}