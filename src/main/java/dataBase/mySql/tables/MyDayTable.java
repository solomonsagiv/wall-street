package dataBase.mySql.tables;

import api.Manifest;
import dataBase.mySql.mySqlComps.MyColumnSql;
import dataBase.mySql.mySqlComps.MyTableSql;
import options.OptionsEnum;
import serverObjects.BASE_CLIENT_OBJECT;

import java.time.LocalDate;
import java.time.LocalTime;

public class MyDayTable extends MyTableSql {

    // Columns
    private int id;
    private MyColumnSql<String> date;
    private MyColumnSql<String> exp_name;
    private MyColumnSql<String> time;
    private MyColumnSql<Double> con;
    private MyColumnSql<Double> conDay;
    private MyColumnSql<Double> conMonth;
    private MyColumnSql<Double> conQuarter;
    private MyColumnSql<Double> ind;
    private MyColumnSql<Integer> con_up;
    private MyColumnSql<Integer> con_down;
    private MyColumnSql<Integer> index_up;
    private MyColumnSql<Integer> index_down;
    private MyColumnSql<String> options;
    private MyColumnSql<Double> base;
    private MyColumnSql<Double> op_avg;
    private MyColumnSql<Double> equalMove;
    private MyColumnSql<Double> opAvgMove;


    // Constructor
    public MyDayTable(BASE_CLIENT_OBJECT client, String tableName) {
        super(client, tableName);
    }

    @Override
    public void initColumns() {
        date = new MyColumnSql<>(this, "date", MyColumnSql.STRING) {
            @Override
            public String getObject() {
                return LocalDate.now().toString();
            }
        };

        exp_name = new MyColumnSql<>(this, "exp_name", MyColumnSql.STRING) {
            @Override
            public String getObject() {
                return Manifest.EXP;
            }
        };

        time = new MyColumnSql<>(this, "time", MyColumnSql.STRING) {
            @Override
            public String getObject() {
                return LocalTime.now().toString();
            }
        };

        con = new MyColumnSql<>(this, "con", MyColumnSql.DOUBLE) {
            @Override
            public Double getObject() {
                return client.getOptionsHandler().getMainOptions().getContract();
            }
        };

        conDay = new MyColumnSql<>(this, "conDay", MyColumnSql.DOUBLE) {
            @Override
            public Double getObject() {
                return client.getOptionsHandler().getOptions( OptionsEnum.WEEK ).getContract();
            }
        };

        conMonth = new MyColumnSql<>(this, "conMonth", MyColumnSql.DOUBLE) {
            @Override
            public Double getObject() {
                return client.getOptionsHandler().getOptions( OptionsEnum.MONTH ).getContract();
            }
        };

        conQuarter = new MyColumnSql<>(this, "conQuarter", MyColumnSql.DOUBLE) {
            @Override
            public Double getObject() {
                return client.getOptionsHandler().getOptions( OptionsEnum.QUARTER ).getContract();
            }
        };

        ind = new MyColumnSql<>(this, "ind", MyColumnSql.DOUBLE) {
            @Override
            public Double getObject() {
                return client.getIndex();
            }
        };

        con_up = new MyColumnSql<>(this, "con_up", MyColumnSql.INT) {
            @Override
            public Integer getObject() {
                return client.getConUp();
            }
        };

        con_down = new MyColumnSql<>(this, "con_down", MyColumnSql.INT) {
            @Override
            public Integer getObject() {
                return client.getConDown();
            }
        };

        index_up = new MyColumnSql<>(this, "index_up", MyColumnSql.INT) {
            @Override
            public Integer getObject() {
                return client.getIndexUp();
            }
        };

        index_down = new MyColumnSql<>(this, "index_down", MyColumnSql.INT) {
            @Override
            public Integer getObject() {
                return client.getIndexDown();
            }
        };

        options = new MyColumnSql<>(this, "options", MyColumnSql.STRING) {
            @Override
            public String getObject() {
                return client.getOptionsHandler().getMainOptions().getOptionsAsJson().toString();
            }
        };

        base = new MyColumnSql<>(this, "base", MyColumnSql.DOUBLE) {
            @Override
            public Double getObject() {
                return client.getBase();
            }
        };

        op_avg = new MyColumnSql<>(this, "op_avg", MyColumnSql.DOUBLE) {
            @Override
            public Double getObject() {
                return client.getOptionsHandler().getMainOptions().getOpAvg();
            }
        };

        equalMove = new MyColumnSql<>(this, "equalMove", MyColumnSql.DOUBLE) {
            @Override
            public Double getObject() {
                return client.getOptionsHandler().getMainOptions().getEqualMoveService().getMove();
            }
        };

        opAvgMove = new MyColumnSql<>(this, "opAvgMove", MyColumnSql.DOUBLE) {
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
