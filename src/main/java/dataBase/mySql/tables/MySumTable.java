package dataBase.mySql.tables;

import api.Manifest;
import dataBase.mySql.mySqlComps.MyColumnSql;
import dataBase.mySql.mySqlComps.MyLoadAbleColumn;
import dataBase.mySql.mySqlComps.MyTableSql;
import serverObjects.BASE_CLIENT_OBJECT;
import java.time.LocalDate;

public class MySumTable extends MyTableSql {

    // Variables
    private MyColumnSql< String > date;
    private MyColumnSql< String > exp_name;
    private MyColumnSql< Double > open;
    private MyColumnSql< Double > high;
    private MyColumnSql< Double > low;
    private MyColumnSql< Double > close;
    private MyColumnSql< Integer > con_up;
    private MyColumnSql< Integer > con_down;
    private MyColumnSql< Integer > index_up;
    private MyColumnSql< Integer > index_down;
    private MyColumnSql< Double > op_avg;
    private MyColumnSql< String > options;
    private MyColumnSql< Double > base;
    private MyColumnSql< Integer > con_bid_ask_counter;
    private MyColumnSql< Double > equalMove;
    private MyColumnSql< Double > opAvgMove;

    // Constructor
    public MySumTable( BASE_CLIENT_OBJECT client, String name ) {
        super( client, name );
    }

    @Override
    public void initColumns() {
        date = new MyColumnSql<>( this, "date", MyColumnSql.STRING ) {
            @Override
            public String getObject() {
                return LocalDate.now( ).toString( );
            }

        };

        exp_name = new MyColumnSql<>( this, "exp_name", MyColumnSql.STRING ) {
            @Override
            public String getObject() {
                return Manifest.EXP;
            }
        };

        open = new MyColumnSql<>( this, "open", MyColumnSql.DOUBLE ) {
            @Override
            public Double getObject() {
                return client.getOpen( );
            }
        };

        high = new MyColumnSql<>( this, "high", MyColumnSql.DOUBLE ) {
            @Override
            public Double getObject() {
                return client.getHigh( );
            }
        };

        low = new MyColumnSql<>( this, "low", MyColumnSql.DOUBLE ) {
            @Override
            public Double getObject() {
                return client.getLow( );
            }
        };

        close = new MyColumnSql<>( this, "close", MyColumnSql.DOUBLE ) {
            @Override
            public Double getObject() {
                return client.getIndex( );
            }
        };

        con_up = new MyColumnSql<>( this, "con_up", MyColumnSql.INT ) {
            @Override
            public Integer getObject() {
                return client.getConUp( );
            }
        };

        con_down = new MyColumnSql<>( this, "con_down", MyColumnSql.INT ) {
            @Override
            public Integer getObject() {
                return client.getConDown( );
            }
        };

        index_up = new MyColumnSql<>( this, "index_up", MyColumnSql.INT ) {
            @Override
            public Integer getObject() {
                return client.getIndexUp( );
            }
        };


        index_down = new MyColumnSql<>( this, "index_down", MyColumnSql.INT ) {
            @Override
            public Integer getObject() {
                return client.getIndexDown( );
            }
        };

        op_avg = new MyColumnSql<>( this, "op_avg", MyColumnSql.DOUBLE ) {
            @Override
            public Double getObject() {
                return client.getOptionsHandler( ).getMainOptions( ).getOpAvg( );
            }
        };

        options = new MyColumnSql<>( this, "options", MyColumnSql.STRING ) {
            @Override
            public String getObject() {
                return client.getOptionsHandler( ).getAllOptionsAsJson( ).toString( );
            }
        };

        base = new MyColumnSql<>( this, "base", MyColumnSql.DOUBLE ) {
            @Override
            public Double getObject() {
                return client.getBase( );
            }
        };

        con_bid_ask_counter = new MyColumnSql<>( this, "con_bid_ask_counter", MyColumnSql.INT ) {
            @Override
            public Integer getObject() {
                return client.getOptionsHandler( ).getMainOptions( ).getContractBidAskCounter( );
            }
        };

        equalMove = new MyColumnSql<>( this, "equalMove", MyColumnSql.DOUBLE ) {
            @Override
            public Double getObject() {
                return client.getOptionsHandler( ).getMainOptions( ).getEqualMoveService( ).getMove( );
            }
        };

        opAvgMove = new MyColumnSql<>( this, "opAvgMove", MyColumnSql.DOUBLE ) {
            @Override
            public Double getObject() {
                return client.getOptionsHandler( ).getMainOptions( ).getOpAvgMoveService( ).getMove( );
            }
        };

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
