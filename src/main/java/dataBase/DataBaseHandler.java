package dataBase;

import dataBase.mySql.dataUpdaters.IDataUpdater;
import serverObjects.BASE_CLIENT_OBJECT;
import threads.MyThread;

public class DataBaseHandler extends MyThread implements Runnable {

    BASE_CLIENT_OBJECT client;
    IDataUpdater dataUpdater;

    public DataBaseHandler( BASE_CLIENT_OBJECT client, IDataUpdater dataUpdater ) {
        this.client = client;
        this.dataUpdater = dataUpdater;
    }

    @Override
    public void initRunnable() {
        setRunnable( this );
    }

    public void load() {
        // todo
    }

    @Override
    public void run() {

        while ( isRun() ) {
            try {
                // Sleep
                Thread.sleep( 200 );

                // Update database
                update();


            } catch ( InterruptedException e ) {
                e.printStackTrace();
                getHandler().close();
            } catch ( Exception e ) {
                e.printStackTrace();
            }

        }

    }

    private void update() {
        dataUpdater.insertData();
    }
}
