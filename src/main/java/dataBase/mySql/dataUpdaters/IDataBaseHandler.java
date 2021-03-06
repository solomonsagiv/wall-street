package dataBase.mySql.dataUpdaters;

import serverObjects.BASE_CLIENT_OBJECT;

public abstract class IDataBaseHandler {

    public abstract String getExcelPath();

    BASE_CLIENT_OBJECT client;

    public IDataBaseHandler( BASE_CLIENT_OBJECT client ) {
        this.client = client;
    }

    public abstract void insertData( int sleep );

    public abstract void loadData();

}
