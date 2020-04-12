package dataBase.mySql.myBaseTables;

import dataBase.mySql.mySqlComps.MySqlTable;
import serverObjects.BASE_CLIENT_OBJECT;

public abstract class MySumTable extends MySqlTable {

    // Constructor
    public MySumTable( BASE_CLIENT_OBJECT client, String name ) {
        super( client, name );
    }

    @Override
    public void insert() {
        super.insert();
    }

    @Override
    public void load() {
    }

    @Override
    public void update() {
    }

    @Override
    public void reset() {
    }

}
