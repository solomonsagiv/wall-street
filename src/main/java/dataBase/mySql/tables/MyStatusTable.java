package dataBase.mySql.tables;

import dataBase.mySql.mySqlComps.MyColumnSql;
import dataBase.mySql.mySqlComps.MyTableSql;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.DaxCLIENTObject;
import serverObjects.indexObjects.SpxCLIENTObject;

import java.time.LocalTime;

public class MyStatusTable extends MyTableSql {


    public static void main( String[] args ) {
        MyStatusTable myStatusTable = new MyStatusTable( DaxCLIENTObject.getInstance(), "status" );
        myStatusTable.update();
    }

    private MyColumnSql<String> name;
    private MyColumnSql<String> time;
    private MyColumnSql<Double> ind;
    private MyColumnSql<Integer> conUp;
    private MyColumnSql<Integer> conDown;
    private MyColumnSql<Integer> indUp;
    private MyColumnSql<Integer> indDown;
    private MyColumnSql<Double> base;
    private MyColumnSql<Double> open;
    private MyColumnSql<Double> high;
    private MyColumnSql<Double> low;
    private MyColumnSql<String> options;

    public MyStatusTable( BASE_CLIENT_OBJECT client, String name ) {
        super( client, name );
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

        ind = new MyColumnSql<>( this, "ind" ) {
            @Override
            public Double getObject() {
                return client.getIndex();
            }
        };

        conUp = new MyColumnSql<>( this, "conUp" ) {
            @Override
            public Integer getObject() {
                return client.getConUp();
            }
        };

        conDown = new MyColumnSql<>( this, "conDown" ) {
            @Override
            public Integer getObject() {
                return client.getConDown();
            }
        };

        indUp = new MyColumnSql<>( this, "indUp" ) {
            @Override
            public Integer getObject() {
                return client.getIndexUp();
            }
        };

        indDown = new MyColumnSql<>( this, "indDown" ) {
            @Override
            public Integer getObject() {
                return client.getIndexDown();
            }
        };

        base = new MyColumnSql<>( this, "base" ) {
            @Override
            public Double getObject() {
                return client.getBase();
            }
        };

        open = new MyColumnSql<>( this, "open" ) {
            @Override
            public Double getObject() {
                return client.getOpen();
            }
        };

        high = new MyColumnSql<>( this, "high" ) {
            @Override
            public Double getObject() {
                return client.getHigh();
            }
        };

        low = new MyColumnSql<>( this, "low" ) {
            @Override
            public Double getObject() {
                return client.getLow();
            }
        };

        options = new MyColumnSql<>( this, "options" ) {
            @Override
            public String getObject() {
                return client.getOptionsHandler().getAllOptionsAsJson().toString();
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
