package dataBase.mySql.myBaseTables;

import dataBase.mySql.mySqlComps.MySqlTable;
import serverObjects.BASE_CLIENT_OBJECT;

public abstract class MyStatusTable extends MySqlTable {

    // Constructor
    public MyStatusTable( BASE_CLIENT_OBJECT client, String tableName ) {
        super( client, tableName );
    }

    @Override
    public void insert() {}

    @Override
    public void load() {
        super.load();
    }

    @Override
    public void update() {
        super.update( );
    }

    @Override
    public void reset() {super.reset();}

}
