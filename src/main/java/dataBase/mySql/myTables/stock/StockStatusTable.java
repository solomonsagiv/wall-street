package dataBase.mySql.myTables.stock;

import dataBase.mySql.myBaseTables.MyStatusTable;
import dataBase.mySql.mySqlComps.MyColumnSql;
import dataBase.mySql.mySqlComps.MyLoadAbleColumn;
import dataBase.mySql.mySqlComps.MySqlColumnEnum;
import myJson.MyJson;
import options.Options;
import roll.RollEnum;
import serverObjects.BASE_CLIENT_OBJECT;

import java.time.LocalTime;

public class StockStatusTable extends MyStatusTable {

    // Constructor
    public StockStatusTable(BASE_CLIENT_OBJECT client) {
        super(client, "status");
    }

    @Override
    public void initColumns() {
        addColumn(new MyColumnSql<>(this, "name", MySqlColumnEnum.NAME) {
            @Override
            public String getObject() {
                return client.getName();
            }
        });
        addColumn(new MyColumnSql<>(this, "time", MySqlColumnEnum.time) {
            @Override
            public String getObject() {
                return LocalTime.now().toString();
            }
        });
        addColumn(new MyColumnSql<>(this, "ind", MySqlColumnEnum.IND) {
            @Override
            public Double getObject() {
                return client.getIndex();
            }
        });
        addColumn(new MyLoadAbleColumn<Integer>(this, "conUp", MySqlColumnEnum.CON_UP) {
            @Override
            public Integer getObject() {
                return client.getConUp();
            }

            @Override
            public void setLoadedObject(Integer object) {
                client.setConUp(object);
            }

            @Override
            public Integer getResetObject() {
                return 0;
            }
        });
        addColumn(new MyLoadAbleColumn<Integer>(this, "conDown", MySqlColumnEnum.CON_DOWN) {
            @Override
            public Integer getObject() {
                return client.getConDown();
            }

            @Override
            public void setLoadedObject(Integer object) {
                client.setConDown(object);
            }

            @Override
            public Integer getResetObject() {
                return 0;
            }

        });
        addColumn(new MyLoadAbleColumn<Integer>(this, "indUp", MySqlColumnEnum.IND_UP) {
            @Override
            public Integer getObject() {
                return client.getIndexUp();
            }

            @Override
            public void setLoadedObject(Integer object) {
                client.setIndexUp(object);
            }

            @Override
            public Integer getResetObject() {
                return 0;
            }

        });
        addColumn(new MyLoadAbleColumn<Integer>(this, "indDown", MySqlColumnEnum.IND_DOWN) {
            @Override
            public Integer getObject() {
                return client.getIndexDown();
            }

            @Override
            public void setLoadedObject(Integer object) {
                client.setIndexDown(object);
            }

            @Override
            public Integer getResetObject() {
                return 0;
            }

        });
        addColumn(new MyColumnSql<Double>(this, "base", MySqlColumnEnum.BASE) {
            @Override
            public Double getObject() {
                return client.getBase();
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
        addColumn(new MyLoadAbleColumn<Integer>(this, "indexBidAskCounter", MySqlColumnEnum.IND_BID_ASK_COUNTER) {
            @Override
            public void setLoadedObject(Integer object) {
                client.setIndexBidAskCounter(object);
            }

            @Override
            public Integer getResetObject() {
                return 0;
            }

            @Override
            public Integer getObject() {
                return (int) client.getIndexBidAskCounter();
            }
        });
        addColumn(new MyLoadAbleColumn<String>(this, "options", MySqlColumnEnum.OPTIONS) {
            @Override
            public String getObject() {
                return client.getExps().getAllOptionsAsJson().toString();
            }

            @Override
            public void setLoadedObject(String object) {
                MyJson optionsData = new MyJson(object);
                for (Options options : client.getExps().getExpList()) {
                    try {
                        options.loadFromJson(optionsData.getMyJson(options.getType().toString()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public String getResetObject() {
                return client.getExps().getAllOptionsEmptyJson().toString();
            }
        });

        addColumn(new MyColumnSql<Double>(this, "roll", MySqlColumnEnum.ROLL) {
            @Override
            public Double getObject() {
                try {
                    return client.getRollHandler().getRoll(RollEnum.WEEK_MONTH).getRoll();
                } catch (Exception e) {
                    return 0.0;
                }
            }
        });
        addColumn(new MyColumnSql<Double>(this, "rollAvg", MySqlColumnEnum.ROLL_AVG) {
            @Override
            public Double getObject() {
                try {
                    return client.getRollHandler().getRoll(RollEnum.WEEK_MONTH).getAvg();
                } catch (Exception e) {
                    return 0.0;
                }
            }
        });
    }
}
