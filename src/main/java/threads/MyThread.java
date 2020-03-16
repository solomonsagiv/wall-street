package threads;

import serverObjects.BASE_CLIENT_OBJECT;

public abstract class MyThread {

    // Variables
    private boolean run = true;

    private String name;
    private MyThreadHandler handler;
    private Runnable runnable;

    private BASE_CLIENT_OBJECT client;

    public MyThread( BASE_CLIENT_OBJECT client ) {

        this.client = client;

        client.getThreads( ).add( this );

        initRunnable( );

    }

    public MyThread() {
        initRunnable( );
    }

    public abstract void initRunnable();

    // Getters and setters
    public boolean isRun() {
        return run;
    }

    public void setRun( boolean run ) {
        this.run = run;
    }

    public MyThreadHandler getHandler() {
        return handler;
    }

    public void setHandler( MyThreadHandler handler ) {
        this.handler = handler;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public void setRunnable( Runnable runnable ) {
        this.runnable = runnable;
        setHandler( new MyThreadHandler( this ) );
    }

    public BASE_CLIENT_OBJECT getClient() {
        return client;
    }



    public void setClient( BASE_CLIENT_OBJECT client ) {
        this.client = client;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

}
