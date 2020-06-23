package dataBase.mySql.myBaseTables;

import dataBase.mySql.mySqlComps.MySqlTable;
import serverObjects.BASE_CLIENT_OBJECT;

public abstract class MyDayTable extends MySqlTable {

    // Constructor
    public MyDayTable(BASE_CLIENT_OBJECT client) {
        super(client);
    }

    @Override
    public String getName() {
        return getName() + "JsonDay";
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
