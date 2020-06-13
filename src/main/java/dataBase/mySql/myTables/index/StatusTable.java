package dataBase.mySql.myTables.index;

import dataBase.mySql.myBaseTables.MyStatusTable;
import serverObjects.BASE_CLIENT_OBJECT;

public class StatusTable extends MyStatusTable {

    // Constructor
    public StatusTable(BASE_CLIENT_OBJECT client ) {
        super(client, "status");
    }

    @Override
    public void initColumns() {
//        addColumn(new MyColumnSql<>(this, MySqlColumnEnum.NAME) {
//            @Override
//            public String getObject() {
//                return client.getName();
//            }
//        });
//        addColumn(new MyColumnSql<>(this, MySqlColumnEnum.time) {
//            @Override
//            public String getObject() {
//                return LocalTime.now().toString();
//            }
//        });
//        addColumn(new MyColumnSql<>(this, MySqlColumnEnum.IND) {
//            @Override
//            public Double getObject() {
//                return client.getIndex();
//            }
//        });
//        addColumn(new MyLoadAbleColumn<Integer>(this, MySqlColumnEnum.CON_UP) {
//            @Override
//            public Integer getObject() {
//                return client.getConUp();
//            }
//
//            @Override
//            public void setLoadedObject(Integer object) {
//                client.setConUp(object);
//            }
//            @Override
//            public Integer getResetObject() {
//                return 0;
//            }
//        });
//        addColumn(new MyLoadAbleColumn<Integer>(this, MySqlColumnEnum.CON_DOWN) {
//            @Override
//            public Integer getObject() {
//                return client.getConDown();
//            }
//
//            @Override
//            public void setLoadedObject(Integer object) {
//                client.setConDown(object);
//            }
//
//            @Override
//            public Integer getResetObject() {
//                return 0;
//            }
//
//        });
//        addColumn(new MyLoadAbleColumn<Integer>(this, MySqlColumnEnum.IND_UP) {
//            @Override
//            public Integer getObject() {
//                return client.getIndexUp();
//            }
//
//            @Override
//            public void setLoadedObject(Integer object) {
//                client.setIndexUp(object);
//            }
//
//            @Override
//            public Integer getResetObject() {
//                return 0;
//            }
//
//        });
//        addColumn(new MyLoadAbleColumn<Integer>(this, MySqlColumnEnum.IND_DOWN) {
//            @Override
//            public Integer getObject() {
//                return client.getIndexDown();
//            }
//
//            @Override
//            public void setLoadedObject(Integer object) {
//                client.setIndexDown(object);
//            }
//
//            @Override
//            public Integer getResetObject() {
//                return 0;
//            }
//
//        });
//        addColumn(new MyColumnSql<Double>(this, MySqlColumnEnum.BASE) {
//            @Override
//            public Double getObject() {
//                return client.getBase();
//            }
//        });
//        addColumn(new MyColumnSql<>(this, MySqlColumnEnum.OPEN) {
//            @Override
//            public Double getObject() {
//                return client.getOpen();
//            }
//        });
//        addColumn(new MyColumnSql<>(this, MySqlColumnEnum.HIGH) {
//            @Override
//            public Double getObject() {
//                return client.getHigh();
//            }
//        });
//        addColumn(new MyColumnSql<>(this, MySqlColumnEnum.LOW) {
//            @Override
//            public Double getObject() {
//                return client.getLow();
//            }
//        });
//        addColumn(new MyColumnSql<>(this, MySqlColumnEnum.E1) {
//            @Override
//            public Double getObject() {
//                return client.getExps().getExp( ExpEnum.E1).getFuture();
//            }
//        });
//        addColumn(new MyColumnSql<>(this, MySqlColumnEnum.E1_BID) {
//            @Override
//            public Double getObject() {
//                return client.getExps().getExp(ExpEnum.E1).getFutureBid();
//            }
//        });
//        addColumn(new MyColumnSql<>(this, MySqlColumnEnum.E1_ASK) {
//            @Override
//            public Double getObject() {
//                return client.getExps().getExp(ExpEnum.E1).getFutureAsk();
//            }
//        });
//        addColumn(new MyColumnSql<>(this, MySqlColumnEnum.E2) {
//            @Override
//            public Double getObject() {
//                return client.getExps().getExp(ExpEnum.E2).getFuture();
//            }
//        });
//        addColumn(new MyColumnSql<>(this, MySqlColumnEnum.E2_BID) {
//            @Override
//            public Double getObject() {
//                return client.getExps().getExp(ExpEnum.E2).getFutureBid();
//            }
//        });
//        addColumn(new MyColumnSql<>(this, MySqlColumnEnum.E2_ASK) {
//            @Override
//            public Double getObject() {
//                return client.getExps().getExp(ExpEnum.E2).getFutureAsk();
//            }
//        });
//        addColumn(new MyLoadAbleColumn<Integer>(this, MySqlColumnEnum.IND_BID_ASK_COUNTER) {
//            @Override
//            public void setLoadedObject(Integer object) {
//                client.setIndexBidAskCounter(object);
//            }
//
//            @Override
//            public Integer getResetObject() {
//                return 0;
//            }
//
//            @Override
//            public Integer getObject() {
//                return (int) client.getIndexBidAskCounter();
//            }
//        });
//        addColumn(new MyLoadAbleColumn<String>(this, MySqlColumnEnum.OPTIONS) {
//            @Override
//            public String getObject() {
//                return client.getExps().getAllOptionsAsJson().toString();
//            }
//
//            @Override
//            public void setLoadedObject(String object) {
//                MyJson optionsData = new MyJson(object);
//                for ( Exp exp : client.getExps().getExpList()) {
//                    try {
//                        Options options = exp.getOptions();
//                        options.loadFromJson(optionsData.getMyJson(options.getType().toString()));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            @Override
//            public String getResetObject() {
//                return client.getExps().getAllOptionsEmptyJson().toString();
//            }
//        });
//        addColumn(new MyColumnSql<Double>(this, MySqlColumnEnum.ROLL) {
//            @Override
//            public Double getObject() {
//                try {
//                    return client.getRollHandler().getRoll(RollEnum.QUARTER_QUARTER_FAR).getRoll();
//                } catch (Exception e) {
//                    return 0.0;
//                }
//            }
//        });
//        addColumn(new MyColumnSql<Double>(this, MySqlColumnEnum.ROLL_AVG) {
//            @Override
//            public Double getObject() {
//                try {
//                    return client.getRollHandler().getRoll(RollEnum.QUARTER_QUARTER_FAR).getAvg();
//                } catch (Exception e) {
//                    return 0.0;
//                }
//            }
//        });
    }
}
