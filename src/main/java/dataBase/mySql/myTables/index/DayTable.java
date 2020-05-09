package dataBase.mySql.myTables.index;

import api.Manifest;
import dataBase.mySql.myBaseTables.MyDayTable;
import dataBase.mySql.mySqlComps.MyColumnSql;
import dataBase.mySql.mySqlComps.MySqlColumnEnum;
import options.OptionsEnum;
import roll.RollEnum;
import serverObjects.BASE_CLIENT_OBJECT;
import java.time.LocalDate;
import java.time.LocalTime;

public class DayTable extends MyDayTable {

    // Constructor
    public DayTable(BASE_CLIENT_OBJECT client) {
        super(client, client.getName());
    }

    @Override
    public void initColumns() {
        addColumn(new MyColumnSql<>(this, "date", MySqlColumnEnum.DATE) {
            @Override
            public String getObject() {
                return LocalDate.now().toString();
            }
        });
        addColumn(new MyColumnSql<>(this, "exp_name", MySqlColumnEnum.DATE.EXP_NAME) {
            @Override
            public String getObject() {
                return Manifest.EXP;
            }
        });
        addColumn(new MyColumnSql<>(this, "time", MySqlColumnEnum.TIME) {
            @Override
            public String getObject() {
                return LocalTime.now().toString();
            }
        });
        addColumn(new MyColumnSql<>(this, "con", MySqlColumnEnum.CON) {
            @Override
            public Double getObject() {
                return client.getOptionsHandler().getMainOptions().getContract();
            }
        });
        addColumn(new MyColumnSql<>(this, "conQuarterFar", MySqlColumnEnum.CON_QUARTER_FAR) {
            @Override
            public Double getObject() {
                return client.getOptionsHandler().getOptions(OptionsEnum.QUARTER_FAR).getContract();
            }
        });
        addColumn(new MyColumnSql<>(this, "conQuarterFarBid", MySqlColumnEnum.CON_QUARTER_FAR_BID) {
            @Override
            public Double getObject() {
                return client.getOptionsHandler().getOptions(OptionsEnum.QUARTER_FAR).getContractBid();
            }
        });
        addColumn(new MyColumnSql<>(this, "conQuarterFarAsk", MySqlColumnEnum.CON_QUARTER_FAR_ASK) {
            @Override
            public Double getObject() {
                return client.getOptionsHandler().getOptions(OptionsEnum.QUARTER_FAR).getContractAsk();
            }
        });
        addColumn(new MyColumnSql<>(this, "conQuarter", MySqlColumnEnum.CON_QUARTER) {
            @Override
            public Double getObject() {
                return client.getOptionsHandler().getOptions(OptionsEnum.QUARTER).getContract();
            }
        });
        addColumn(new MyColumnSql<>(this, "conQuarterBid", MySqlColumnEnum.CON_QUARTER_BID) {
            @Override
            public Double getObject() {
                return client.getOptionsHandler().getOptions(OptionsEnum.QUARTER).getContractBid();
            }
        });
        addColumn(new MyColumnSql<>(this, "conQuarterAsk", MySqlColumnEnum.CON_QUARTER_ASK) {
            @Override
            public Double getObject() {
                return client.getOptionsHandler().getOptions(OptionsEnum.QUARTER).getContractAsk();
            }
        });
        addColumn(new MyColumnSql<>(this, "e1", MySqlColumnEnum.E1) {
            @Override
            public Double getObject() {
                return client.getOptionsHandler().getOptions(OptionsEnum.QUARTER).getFuture();
            }
        });
        addColumn(new MyColumnSql<>(this, "e1_bid", MySqlColumnEnum.E1_BID) {
            @Override
            public Double getObject() {
                return client.getOptionsHandler().getOptions(OptionsEnum.QUARTER).getFutureBid();
            }
        });
        addColumn(new MyColumnSql<>(this, "e1_ask", MySqlColumnEnum.E1_ASK) {
            @Override
            public Double getObject() {
                return client.getOptionsHandler().getOptions(OptionsEnum.QUARTER).getFutureAsk();
            }
        });
        addColumn(new MyColumnSql<>(this, "e2", MySqlColumnEnum.E2) {
            @Override
            public Double getObject() {
                return client.getOptionsHandler().getOptions(OptionsEnum.QUARTER_FAR).getFuture();
            }
        });
        addColumn(new MyColumnSql<>(this, "e2_bid", MySqlColumnEnum.E2_BID) {
            @Override
            public Double getObject() {
                return client.getOptionsHandler().getOptions(OptionsEnum.QUARTER_FAR).getFutureBid();
            }
        });
        addColumn(new MyColumnSql<>(this, "e2_ask", MySqlColumnEnum.E2_ASK) {
            @Override
            public Double getObject() {
                return client.getOptionsHandler().getOptions(OptionsEnum.QUARTER_FAR).getFutureAsk();
            }
        });
        addColumn(new MyColumnSql<>(this, "ind", MySqlColumnEnum.IND) {
            @Override
            public Double getObject() {
                return client.getIndex();
            }
        });
        addColumn(new MyColumnSql<>(this, "indBid", MySqlColumnEnum.IND_BID) {
            @Override
            public Double getObject() {
                return client.getIndexBid();
            }
        });
        addColumn(new MyColumnSql<>(this, "indAsk", MySqlColumnEnum.IND_ASK) {
            @Override
            public Double getObject() {
                return client.getIndexAsk();
            }
        });
        addColumn(new MyColumnSql<>(this, "indBidAskCounter", MySqlColumnEnum.IND_BID_ASK_COUNTER) {
            @Override
            public Integer getObject() {
                return (int) client.getIndexBidAskCounter();
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
        addColumn(new MyColumnSql<>(this, "op_avg", MySqlColumnEnum.OP_AVG) {
            @Override
            public Double getObject() {
                try {
                    return client.getOptionsHandler().getMainOptions().getOpAvgFuture();
                } catch ( Exception e ) {
                    return 0.0;
                }
            }
        });
        addColumn(new MyColumnSql<Double>(this, "roll", MySqlColumnEnum.ROLL) {
            @Override
            public Double getObject() {
                try {
                    return client.getRollHandler().getRoll( RollEnum.QUARTER_QUARTER_FAR).getRoll();
                } catch (Exception e) {
                    return 0.0;
                }
            }
        });
        addColumn(new MyColumnSql<Double>(this, "rollAvg", MySqlColumnEnum.ROLL_AVG) {
            @Override
            public Double getObject() {
                try {
                    return client.getRollHandler().getRoll(RollEnum.QUARTER_QUARTER_FAR).getAvg();
                } catch (Exception e) {
                    return 0.0;
                }
            }
        });
    }
}
