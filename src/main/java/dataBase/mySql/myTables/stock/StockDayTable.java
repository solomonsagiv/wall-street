package dataBase.mySql.myTables.stock;

import api.Manifest;
import dataBase.mySql.myBaseTables.MyDayTable;
import dataBase.mySql.mySqlComps.MyColumnSql;
import dataBase.mySql.mySqlComps.MySqlColumnEnum;
import options.OptionsEnum;
import serverObjects.BASE_CLIENT_OBJECT;

import java.time.LocalDate;
import java.time.LocalTime;

public class StockDayTable extends MyDayTable {

    // Constructor
    public StockDayTable( BASE_CLIENT_OBJECT client ) {
        super(client, client.getName() );
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
                return client.getOptionsHandler().getMainOptions().getAsJson().toString();
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
                return client.getOptionsHandler().getMainOptions().getOpAvg();
            }
        });
    }
}
