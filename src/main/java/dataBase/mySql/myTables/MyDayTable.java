package dataBase.mySql.myTables;

import api.Manifest;
import dataBase.mySql.mySqlComps.MyColumnSql;
import dataBase.mySql.mySqlComps.MyTableSql;
import options.OptionsEnum;
import serverObjects.BASE_CLIENT_OBJECT;

import java.time.LocalDate;
import java.time.LocalTime;

public abstract class MyDayTable extends MyTableSql {

    // Constructor
    public MyDayTable(BASE_CLIENT_OBJECT client, String tableName) {
        super(client, tableName);
    }

    @Override
    public void insert() {
        super.insertFromSuper();
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

    @Override
    public MyTableSql getObject() {
        return null;
    }

}
