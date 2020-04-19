package dataBase.mySql.myTables.index;

import api.Manifest;
import dataBase.mySql.myBaseTables.MySumTable;
import dataBase.mySql.mySqlComps.MyColumnSql;
import dataBase.mySql.mySqlComps.MySqlColumnEnum;
import serverObjects.BASE_CLIENT_OBJECT;

import java.time.LocalDate;

public class SumTable extends MySumTable {

    // Constructor
    public SumTable(BASE_CLIENT_OBJECT client) {
        super(client);
    }

    @Override
    public void initColumns() {

        addColumn(new MyColumnSql<>(this, "date", MySqlColumnEnum.DATE) {
            @Override
            public String getObject() {
                return LocalDate.now().toString();
            }
        });
        addColumn(new MyColumnSql<>(this, "exp_name", MySqlColumnEnum.EXP_NAME) {
            @Override
            public String getObject() {
                return Manifest.EXP;
            }
        });
        addColumn(new MyColumnSql<>(this, "open", MySqlColumnEnum.OPEN) {
            @Override
            public Double getObject() {
                return client.getOpen();
            }
        });
        addColumn(new MyColumnSql<>(this, "high", MySqlColumnEnum.HIGH) {
            @Override
            public Double getObject() {
                return client.getHigh();
            }
        });
        addColumn(new MyColumnSql<>(this, "low", MySqlColumnEnum.LOW) {
            @Override
            public Double getObject() {
                return client.getLow();
            }
        });
        addColumn(new MyColumnSql<>(this, "close", MySqlColumnEnum.CLOSE) {

            @Override
            public Double getObject() {
                return client.getIndex();
            }
        });
        addColumn(new MyColumnSql<>(this, "con_up", MySqlColumnEnum.CON_UP) {
            @Override
            public Integer getObject() {
                return client.getConUp();
            }
        });
        addColumn(new MyColumnSql<>(this, "con_down", MySqlColumnEnum.CON_DOWN) {
            @Override
            public Integer getObject() {
                return client.getConDown();
            }
        });
        addColumn(new MyColumnSql<>(this, "index_up", MySqlColumnEnum.IND_UP) {
            @Override
            public Integer getObject() {
                return client.getIndexUp();
            }
        });
        addColumn(new MyColumnSql<>(this, "index_down", MySqlColumnEnum.IND_DOWN) {
            @Override
            public Integer getObject() {
                return client.getIndexDown();
            }
        });
        addColumn(new MyColumnSql<>(this, "op_avg", MySqlColumnEnum.OP_AVG) {
            @Override
            public Double getObject() {
                return client.getOptionsHandler().getMainOptions().getOpAvg();
            }
        });
        addColumn(new MyColumnSql<>(this, "options", MySqlColumnEnum.OPTIONS) {
            @Override
            public String getObject() {
                return client.getOptionsHandler().getAllOptionsAsJson().toString();
            }
        });
        addColumn(new MyColumnSql<>(this, "base", MySqlColumnEnum.BASE) {
            @Override
            public Double getObject() {
                return client.getBase();
            }
        });
        addColumn(new MyColumnSql<>(this, "con_bid_ask_counter", MySqlColumnEnum.CON_BID_ASK_COUNTER) {
            @Override
            public Integer getObject() {
                return client.getOptionsHandler().getMainOptions().getConBidAskCounter();
            }
        });
        addColumn(new MyColumnSql<>(this, "indBidAskCounter", MySqlColumnEnum.IND_BID_ASK_COUNTER) {
            @Override
            public Integer getObject() {
                return (int) client.getIndexBidAskCounter();
            }
        });
    }
}
