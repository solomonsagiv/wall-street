package dataBase.mySql.dataUpdaters;

import serverObjects.BASE_CLIENT_OBJECT;

public abstract class IDataUpdater {

    BASE_CLIENT_OBJECT client;

    public IDataUpdater( BASE_CLIENT_OBJECT client ) {
        this.client = client;
    }

    public abstract void insertData( int sleep );

}
