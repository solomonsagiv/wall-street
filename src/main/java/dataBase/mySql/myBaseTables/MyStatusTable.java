package dataBase.mySql.myBaseTables;

import dataBase.mySql.mySqlComps.MySqlTable;
import serverObjects.BASE_CLIENT_OBJECT;

public abstract class MyStatusTable extends MySqlTable {

    // Constructor
    public MyStatusTable( BASE_CLIENT_OBJECT client ) {
        super( client );
    }

    @Override
    public void insert() {}

    @Override
    public void load() {
        super.load();
        setLoad(true);
    }

    @Override
    public void update() {
        super.update( );
    }

    @Override
    public void reset() {super.reset();}

}
