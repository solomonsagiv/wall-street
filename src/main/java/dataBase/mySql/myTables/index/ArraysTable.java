package dataBase.mySql.myTables.index;

import dataBase.mySql.myBaseTables.MyArraysTable;
import dataBase.mySql.mySqlComps.MyColumnSql;
import dataBase.mySql.mySqlComps.MyLoadAbleColumn;
import dataBase.mySql.mySqlComps.MySqlColumnEnum;
import lists.MyChartPoint;
import options.OptionsEnum;
import org.json.JSONArray;
import org.json.JSONObject;
import serverObjects.BASE_CLIENT_OBJECT;

import java.time.LocalTime;

public class ArraysTable extends MyArraysTable {

    // Constructor
    public ArraysTable(BASE_CLIENT_OBJECT client) {
        super( client );
    }

    @Override
    public void initColumns() {
        addColumn(new MyColumnSql<String>(this, "time", MySqlColumnEnum.TIME) {
            @Override
            public String getObject() {
                return LocalTime.now().toString();
            }
        });
        addColumn(new MyLoadAbleColumn<String>(this, "indexlist", MySqlColumnEnum.INDEX_LIST) {
            @Override
            public String getObject() {
                return client.getIndexList().getLast().getAsJson().toString();
            }

            @Override
            public void setLoadedObject(String object) {
                client.getIndexList().add(new MyChartPoint(new JSONObject(object)));
            }

            @Override
            public String getResetObject() {
                return new JSONArray().toString();
            }
        });
        addColumn(new MyLoadAbleColumn<Double>(this, "opList", MySqlColumnEnum.OP_LIST) {
            @Override
            public Double getObject() {
                int last = client.getOptionsHandler().getMainOptions().getOpList().size() - 1;
                return client.getOptionsHandler().getMainOptions().getOpList().get(last);
            }

            @Override
            public void setLoadedObject(Double object) {
                client.getOptionsHandler().getMainOptions().getOpList().add(object);
            }

            @Override
            public Double getResetObject() {
                return null;
            }
        });
        addColumn(new MyLoadAbleColumn<String>(this, "indexBidAskCounterList", MySqlColumnEnum.IND_BID_ASK_COUNTER_LIST) {
            @Override
            public String getObject() {
                return client.getIndexBidAskCounterList().getLast().getAsJson().toString();
            }

            @Override
            public void setLoadedObject(String object) {
                client.getIndexBidAskCounterList().add(new MyChartPoint(new JSONObject(object)));
            }

            @Override
            public String getResetObject() {
                return new JSONArray().toString();
            }
        });
        addColumn(new MyLoadAbleColumn<String>(this, "quarterFutBidAskCounterList", MySqlColumnEnum.QUARTER_FUT_BID_ASK_COUNTER_LIST) {
            @Override
            public String getObject() {
                return client.getOptionsHandler().getOptions(OptionsEnum.QUARTER).getFutBidAskCounterList().getLast().getAsJson().toString();
            }

            @Override
            public void setLoadedObject(String object) {
                System.out.println(client.getName());
                client.getOptionsHandler().getOptions(OptionsEnum.QUARTER).getFutBidAskCounterList().add(new MyChartPoint(new JSONObject(object)));
            }

            @Override
            public String getResetObject() {
                return new JSONArray().toString();
            }
        });
        addColumn(new MyLoadAbleColumn<String>(this, "quarterFarFutBidAskCounterList", MySqlColumnEnum.QUARTER_FAR_FUT_BID_ASK_COUNTER_LIST) {
            @Override
            public String getObject() {
                return client.getOptionsHandler().getOptions(OptionsEnum.QUARTER_FAR).getFutBidAskCounterList().getLast().getAsJson().toString();
            }

            @Override
            public void setLoadedObject(String object) {
                if (!object.isEmpty()) {
                    System.out.println(client.getName());
                    client.getOptionsHandler().getOptions(OptionsEnum.QUARTER_FAR).getFutBidAskCounterList().add(new MyChartPoint(new JSONObject(object)));
                }
            }

            @Override
            public String getResetObject() {
                return new JSONArray().toString();
            }
        });
    }
}
