package dataBase.mySql.tables;

import dataBase.mySql.mySqlComps.MyColumnSql;
import dataBase.mySql.mySqlComps.MyTableSql;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.DaxCLIENTObject;

import java.time.LocalTime;

public class MyArraysTable extends MyTableSql {

    public static void main( String[] args ) {

        MyArraysTable myArraysTable = new MyArraysTable( DaxCLIENTObject.getInstance(), "arrays" );
        myArraysTable.update();
    }

    private MyColumnSql<String> name;
    private MyColumnSql<String> time;
    private MyColumnSql<String> indexlist;
    private MyColumnSql<String> opList;
    private MyColumnSql<String> equalMoveList;
    private MyColumnSql<String> opAvgMoveList;

    public MyArraysTable( BASE_CLIENT_OBJECT client, String tableName ) {
        super( client, tableName );
    }

    @Override
    public void initColumns() {
        name = new MyColumnSql<>( this, "name" ) {
            @Override
            public String getObject() {
                return client.getName();
            }
        };

        time = new MyColumnSql<>( this, "time" ) {
            @Override
            public String getObject() {
                return LocalTime.now().toString();
            }
        };

        indexlist = new MyColumnSql<>( this, "indexlist" ) {
            @Override
            public String getObject() {
                return client.getIndexList().toString();
            }
        };

        opList = new MyColumnSql<>( this, "opList" ) {
            @Override
            public String getObject() {
                return client.getOptionsHandler().getMainOptions().getOpList().toString();
            }
        };

        equalMoveList = new MyColumnSql<>( this, "equalMoveList" ) {
            @Override
            public String getObject() {
                return client.getOptionsHandler().getMainOptions().getEqualMoveService().getMoveList().toString();
            }
        };

        opAvgMoveList = new MyColumnSql<>( this, "opAvgMoveList" ) {
            @Override
            public String getObject() {
                return client.getOptionsHandler().getMainOptions().getOpAvgMoveService().getMoveList().toString();
            }
        };

    }

    @Override
    public void insert() {}

    @Override
    public void load() {

    }

    @Override
    public void update() {
        super.updateFromSuper();
    }

    @Override
    public void reset() {

    }

    @Override
    public MyTableSql getObject() {
        return null;
    }
}
