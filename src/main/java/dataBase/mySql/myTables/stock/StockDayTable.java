package dataBase.mySql.myTables.stock;

import dataBase.mySql.myBaseTables.MyDayTable;
import serverObjects.BASE_CLIENT_OBJECT;

public class StockDayTable extends MyDayTable {

    // Constructor
    public StockDayTable( BASE_CLIENT_OBJECT client ) {
        super(client, client.getName() );
    }
    
    @Override
    public void initColumns() {
//        addColumn(new MyColumnSql<>(this, "date", MySqlColumnEnum.date) {
//            @Override
//            public String getObject() {
//                return LocalDate.now().toString();
//            }
//        });
//        addColumn(new MyColumnSql<>(this, "exp_name", MySqlColumnEnum.date.exp_name) {
//            @Override
//            public String getObject() {
//                return Manifest.EXP;
//            }
//        });
//        addColumn(new MyColumnSql<>(this, "time", MySqlColumnEnum.time) {
//            @Override
//            public String getObject() {
//                return LocalTime.now().toString();
//            }
//        });
//        addColumn(new MyColumnSql<>(this, "con", MySqlColumnEnum.CON) {
//            @Override
//            public Double getObject() {
//                return client.getExps().getMainExp().getContract();
//            }
//        });
//        addColumn(new MyColumnSql<>(this, "conWeek", MySqlColumnEnum.CON_WEEK) {
//            @Override
//            public Double getObject() {
//                return client.getExps().getExp(OptionsEnum.WEEK).getContract();
//            }
//        });
//        addColumn(new MyColumnSql<>(this, "conWeekBid", MySqlColumnEnum.CON_WEEK_BID) {
//            @Override
//            public Double getObject() {
//                return client.getExps().getExp(OptionsEnum.WEEK).getContractBid();
//            }
//        });
//        addColumn(new MyColumnSql<>(this, "conWeekAsk", MySqlColumnEnum.CON_WEEK_ASK) {
//            @Override
//            public Double getObject() {
//                return client.getExps().getExp(OptionsEnum.WEEK).getContractAsk();
//            }
//        });
//        addColumn(new MyColumnSql<>(this, "conMonth", MySqlColumnEnum.CON_MONTH) {
//            @Override
//            public Double getObject() {
//                return client.getExps().getExp(OptionsEnum.MONTH).getContract();
//            }
//        });
//        addColumn(new MyColumnSql<>(this, "conMonthBid", MySqlColumnEnum.CON_MONTH_BID) {
//            @Override
//            public Double getObject() {
//                return client.getExps().getExp(OptionsEnum.MONTH).getContractBid();
//            }
//        });
//        addColumn(new MyColumnSql<>(this, "conMonthAsk", MySqlColumnEnum.CON_MONTH_ASK) {
//            @Override
//            public Double getObject() {
//                return client.getExps().getExp(OptionsEnum.MONTH).getContractAsk();
//            }
//        });
//        addColumn(new MyColumnSql<>(this, "con_bid_ask_counter", MySqlColumnEnum.CON_BID_ASK_COUNTER) {
//            @Override
//            public Integer getObject() {
//                return client.getExps().getExp(OptionsEnum.MONTH).getConBidAskCounter();
//            }
//        });
//        addColumn(new MyColumnSql<>(this, "ind", MySqlColumnEnum.IND) {
//            @Override
//            public Double getObject() {
//                return client.getIndex();
//            }
//        });
//        addColumn(new MyColumnSql<>(this, "indBid", MySqlColumnEnum.IND_BID) {
//            @Override
//            public Double getObject() {
//                return client.getIndexBid();
//            }
//        });
//        addColumn(new MyColumnSql<>(this, "indAsk", MySqlColumnEnum.IND_ASK) {
//            @Override
//            public Double getObject() {
//                return client.getIndexAsk();
//            }
//        });
//        addColumn(new MyColumnSql<>(this, "indBidAskCounter", MySqlColumnEnum.IND_BID_ASK_COUNTER) {
//            @Override
//            public Integer getObject() {
//                return (int) client.getIndexBidAskCounter();
//            }
//        });
//        addColumn(new MyColumnSql<>(this, "con_up", MySqlColumnEnum.CON_UP) {
//            @Override
//            public Integer getObject() {
//                return client.getConUp();
//            }
//        });
//        addColumn(new MyColumnSql<>(this, "con_down", MySqlColumnEnum.CON_DOWN) {
//            @Override
//            public Integer getObject() {
//                return client.getConDown();
//            }
//        });
//        addColumn(new MyColumnSql<>(this, "index_up", MySqlColumnEnum.IND_UP) {
//            @Override
//            public Integer getObject() {
//                return client.getIndexUp();
//            }
//        });
//        addColumn(new MyColumnSql<>(this, "index_down", MySqlColumnEnum.IND_DOWN) {
//            @Override
//            public Integer getObject() {
//                return client.getIndexDown();
//            }
//        });
//        addColumn(new MyColumnSql<>(this, "options", MySqlColumnEnum.OPTIONS) {
//            @Override
//            public String getObject() {
//                return client.getExps().getAllOptionsAsJson().toString();
//            }
//        });
//        addColumn(new MyColumnSql<>(this, "base", MySqlColumnEnum.BASE) {
//            @Override
//            public Double getObject() {
//                return client.getBase();
//            }
//        });
//        addColumn(new MyColumnSql<>(this, "op_avg", MySqlColumnEnum.OP_AVG) {
//            @Override
//            public Double getObject() {
//                return client.getExps().getMainExp().getOpAvg();
//            }
//        });
//        addColumn(new MyColumnSql<Double>(this, "roll", MySqlColumnEnum.ROLL) {
//            @Override
//            public Double getObject() {
//                try {
//                    return client.getRollHandler().getRoll( RollEnum.WEEK_MONTH).getRoll();
//                } catch (Exception e) {
//                    return 0.0;
//                }
//            }
//        });
//        addColumn(new MyColumnSql<Double>(this, "rollAvg", MySqlColumnEnum.ROLL_AVG) {
//            @Override
//            public Double getObject() {
//                try {
//                    return client.getRollHandler().getRoll(RollEnum.WEEK_MONTH).getAvg();
//                } catch (Exception e) {
//                    return 0.0;
//                }
//            }
//        });
    }
}
