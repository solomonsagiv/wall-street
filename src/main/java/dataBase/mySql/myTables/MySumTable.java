package dataBase.mySql.myTables;

import api.Manifest;
import dataBase.mySql.mySqlComps.MyColumnSql;
import dataBase.mySql.mySqlComps.MyLoadAbleColumn;
import dataBase.mySql.mySqlComps.MyTableSql;
import serverObjects.BASE_CLIENT_OBJECT;
import java.time.LocalDate;

public abstract class MySumTable extends MyTableSql {

    // Constructor
    public MySumTable( BASE_CLIENT_OBJECT client, String name ) {
        super( client, name );
    }

    @Override
    public void initColumns() {

    }

    @Override
    public void insert() {
        super.insertFromSuper( );
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
