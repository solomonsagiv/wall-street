package dataBase.mySql.tables;

import api.Manifest;
import dataBase.mySql.MySql;
import dataBase.mySql.mySqlComps.MyColumnSql;
import dataBase.mySql.mySqlComps.MyTableSql;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.SpxCLIENTObject;

import java.time.LocalDate;

public class MySumTable extends MyTableSql {

    // Variables
    private MyColumnSql<String> date;
    private MyColumnSql<String> exp_name;
    private MyColumnSql<Double> open;
    private MyColumnSql<Double> high;
    private MyColumnSql<Double> low;
    private MyColumnSql<Double> close;
    private MyColumnSql<Integer> con_up;
    private MyColumnSql<Integer> con_down;
    private MyColumnSql<Integer> index_up;
    private MyColumnSql<Integer> index_down;
    private MyColumnSql<Double> op_avg;
    private MyColumnSql<String> options;
    private MyColumnSql<Double> base;
    private MyColumnSql<Integer> con_bid_ask_counter;
    private MyColumnSql<Double> equalMove;
    private MyColumnSql<Double> opAvgMove;

    // Constructor
    public MySumTable( BASE_CLIENT_OBJECT client, String name ) {
        super( client, name );
    }

    @Override
    public void initColumns() {
        date = new MyColumnSql<>( this, "date" ) {
            @Override
            public String getObject() {
                return LocalDate.now().toString();
            }
        };

        exp_name = new MyColumnSql<>( this, "exp_name" ) {
            @Override
            public String getObject() {
                return Manifest.EXP;
            }
        };

        open = new MyColumnSql<>( this, "open") {
            @Override
            public Double getObject() {
                return client.getOpen();
            }
        };

        high = new MyColumnSql<>( this, "high") {
            @Override
            public Double getObject() {
                return client.getHigh();
            }
        };

        low = new MyColumnSql<>( this, "low") {
            @Override
            public Double getObject() {
                return client.getLow();
            }
        };

        close = new MyColumnSql<>( this, "close") {
            @Override
            public Double getObject() {
                return client.getIndex();
            }
        };

        con_up = new MyColumnSql<>( this, "con_up") {
            @Override
            public Integer getObject() {
                return client.getConUp();
            }
        };

        con_down = new MyColumnSql<>( this, "con_down") {
            @Override
            public Integer getObject() {
                return client.getConDown();
            }
        };

        index_up = new MyColumnSql<>( this, "index_up") {
            @Override
            public Integer getObject() {
                return client.getIndexUp();
            }
        };


        index_down = new MyColumnSql<>( this, "index_down") {
            @Override
            public Integer getObject() {
                return client.getIndexDown();
            }
        };

        op_avg = new MyColumnSql<>( this, "op_avg") {
            @Override
            public Double getObject() {
                return client.getOptionsHandler().getMainOptions().getOpAvg();
            }
        };

        options = new MyColumnSql<>(this, "options" ) {
            @Override
            public String getObject() {
                return client.getOptionsHandler().getAllOptionsAsJson().toString();
            }
        };

        base = new MyColumnSql<>( this, "base") {
            @Override
            public Double getObject() {
                return client.getBase();
            }
        };


        con_bid_ask_counter = new MyColumnSql<>( this, "con_bid_ask_counter") {
            @Override
            public Integer getObject() {
                return client.getOptionsHandler().getMainOptions().getContractBidAskCounter();
            }
        };

        equalMove = new MyColumnSql<>( this, "equalMove") {
            @Override
            public Double getObject() {
                return client.getOptionsHandler().getMainOptions().getEqualMoveService().getMove();
            }
        };

        opAvgMove = new MyColumnSql<>( this, "opAvgMove") {
            @Override
            public Double getObject() {
                return client.getOptionsHandler().getMainOptions().getOpAvgMoveService().getMove();
            }
        };

    }

    @Override
    public void insert() {
        super.insertFromSuper();
    }

    @Override
    public void load() {}

    @Override
    public void update() {}

    @Override
    public void reset() {}

    @Override
    public MyTableSql getObject() {
        return null;
    }
}
