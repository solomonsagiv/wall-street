package dataBase.mySql.myTables.stock;

import dataBase.mySql.myBaseTables.MyArraysTable;
import dataBase.mySql.mySqlComps.MyColumnSql;
import dataBase.mySql.mySqlComps.MyLoadAbleColumn;
import dataBase.mySql.mySqlComps.MySqlColumnEnum;
import exp.ExpStrings;
import myJson.MyJson;
import org.json.JSONArray;
import serverObjects.BASE_CLIENT_OBJECT;

import java.rmi.UnknownHostException;
import java.text.ParseException;
import java.time.LocalTime;

public class StockArraysTable extends MyArraysTable {

    // Constructor
    public StockArraysTable(BASE_CLIENT_OBJECT client) {
        super(client);
    }

    @Override
    public void initColumns() {
        addColumn(new MyColumnSql<String>(this, MySqlColumnEnum.time) {
            @Override
            public String getObject() {
                return LocalTime.now().toString();
            }
        });
        addColumn(new MyLoadAbleColumn<String>(this, MySqlColumnEnum.indexList) {
            @Override
            public String getObject() throws UnknownHostException, ParseException {
                return client.getIndexSeries().getLastJson().toString();
            }

            @Override
            public void setLoadedObject(String object) {
                MyJson json = new MyJson(object);
                client.getIndexSeries().add(json);
            }

            @Override
            public String getResetObject() {
                return new JSONArray().toString();
            }
        });
        addColumn(new MyLoadAbleColumn<Double>(this, MySqlColumnEnum.opList) {
            @Override
            public Double getObject() {
                int last = client.getExps().getMainExp().getOptions().getOpList().size() - 1;
                return client.getExps().getMainExp().getOptions().getOpList().get(last);
            }

            @Override
            public void setLoadedObject(Double object) {
                client.getExps().getMainExp().getOptions().getOpList().add(object);
            }

            @Override
            public Double getResetObject() {
                return null;
            }
        });
        addColumn(new MyLoadAbleColumn<String>(this, MySqlColumnEnum.indexBidAskCounterList) {
            @Override
            public String getObject() throws UnknownHostException, ParseException {
                return client.getIndexBidAskCounterSeries().getLastJson().toString();
            }

            @Override
            public void setLoadedObject(String object) {
                client.getIndexBidAskCounterSeries().add(new MyJson(object));
            }

            @Override
            public String getResetObject() {
                return new JSONArray().toString();
            }
        });
        addColumn(new MyLoadAbleColumn<String>(this, MySqlColumnEnum.conWeekBidAskCounterList) {
            @Override
            public String getObject() throws UnknownHostException, ParseException {
                return client.getExps().getExp(ExpStrings.week).getOptions().getConBidAskCounterSeries().getLastJson().toString();
            }

            @Override
            public void setLoadedObject(String object) {
                System.out.println(object);
                client.getExps().getExp(ExpStrings.week).getOptions().getConBidAskCounterSeries().add(new MyJson(object));
            }

            @Override
            public String getResetObject() {
                return new JSONArray().toString();
            }
        });
        addColumn(new MyLoadAbleColumn<String>(this, MySqlColumnEnum.conMonthBidAskCounterList) {
            @Override
            public String getObject() throws UnknownHostException, ParseException {
                return client.getExps().getExp(ExpStrings.month).getOptions().getConBidAskCounterSeries().getLastJson().toString();
            }

            @Override
            public void setLoadedObject(String object) {
                client.getExps().getExp(ExpStrings.month).getOptions().getConBidAskCounterSeries().add(new MyJson(object));
            }

            @Override
            public String getResetObject() {
                return new JSONArray().toString();
            }
        });
    }
}
