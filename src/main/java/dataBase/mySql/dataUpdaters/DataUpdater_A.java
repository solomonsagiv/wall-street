package dataBase.mySql.dataUpdaters;

import dataBase.mySql.MySql;
import serverObjects.BASE_CLIENT_OBJECT;

public class DataUpdater_A implements IDataUpdater {

    @Override
    public void insertData( BASE_CLIENT_OBJECT client ) {

        MySql.insert( "INSERT INTO " );

    }
}
