package dataBase.mySql.myTables.stock;

import dataBase.mySql.myBaseTables.MyArraysTable;
import dataBase.mySql.mySqlComps.MyColumnSql;
import dataBase.mySql.mySqlComps.MyLoadAbleColumn;
import dataBase.mySql.mySqlComps.MySqlColumnEnum;
import lists.MyChartPoint;
import options.OptionsEnum;
import org.json.JSONArray;
import org.json.JSONObject;
import serverObjects.BASE_CLIENT_OBJECT;

import java.rmi.UnknownHostException;
import java.time.LocalTime;

public class StockArraysTable extends MyArraysTable {

    // Constructor
    public StockArraysTable( BASE_CLIENT_OBJECT client ) {
        super( client );
    }

    @Override
    public void initColumns() {
        addColumn( new MyColumnSql< String >( this, "time", MySqlColumnEnum.time) {
            @Override
            public String getObject() {
                return LocalTime.now( ).toString( );
            }
        } );
        addColumn( new MyLoadAbleColumn< String >( this, "indexlist", MySqlColumnEnum.INDEX_LIST ) {
            @Override
            public String getObject() throws UnknownHostException {
                return client.getIndexList( ).getLast().getAsJson().toString( );
            }

            @Override
            public void setLoadedObject( String object ) {
                client.getIndexList( ).add(new MyChartPoint(new JSONObject(object)));
            }

            @Override
            public String getResetObject() {
                return new JSONArray( ).toString( );
            }
        } );

        addColumn( new MyLoadAbleColumn< Double >( this, "opList", MySqlColumnEnum.OP_LIST ) {
            @Override
            public Double getObject() {
                int last = client.getExpHandler( ).getMainExp( ).getOpList( ).size() - 1;
                return client.getExpHandler( ).getMainExp( ).getOpList( ).get(last);
            }

            @Override
            public void setLoadedObject( Double object ) {
                client.getExpHandler( ).getMainExp( ).getOpList( ).add(object);
            }

            @Override
            public Double getResetObject() {
                return null;
            }
        } );
        addColumn( new MyLoadAbleColumn< String >( this, "indexBidAskCounterList", MySqlColumnEnum.IND_BID_ASK_COUNTER_LIST ) {
            @Override
            public String getObject() throws UnknownHostException {
                return client.getIndexBidAskCounterList( ).getLast().getAsJson().toString( );
            }

            @Override
            public void setLoadedObject( String object ) {
                client.getIndexBidAskCounterList( ).add(new MyChartPoint(new JSONObject(object)));
            }

            @Override
            public String getResetObject() {
                return new JSONArray( ).toString( );
            }
        } );
        addColumn( new MyLoadAbleColumn< String >( this, "conWeekBidAskCounterList", MySqlColumnEnum.CON_WEEK_BID_ASK_COUNTER_LIST ) {
            @Override
            public String getObject() throws UnknownHostException {
                return client.getExpHandler( ).getExp( OptionsEnum.WEEK ).getConBidAskCounterList( ).getLast().getAsJson().toString( );
            }

            @Override
            public void setLoadedObject( String object ) {
                client.getExpHandler( ).getExp( OptionsEnum.WEEK ).getConBidAskCounterList( ).add(new MyChartPoint(new JSONObject(object)));
            }
            @Override
            public String getResetObject() {
                return new JSONArray( ).toString( );
            }
        } );
        addColumn( new MyLoadAbleColumn< String >( this, "conMonthBidAskCounterList", MySqlColumnEnum.CON_MONTH_BID_ASK_COUNTER_LIST ) {
            @Override
            public String getObject() throws UnknownHostException {
                return client.getExpHandler( ).getExp( OptionsEnum.MONTH ).getConBidAskCounterList( ).getLast().getAsJson().toString( );
            }

            @Override
            public void setLoadedObject( String object ) {
                client.getExpHandler( ).getExp( OptionsEnum.MONTH ).getConBidAskCounterList( ).add(new MyChartPoint(new JSONObject(object)));
            }

            @Override
            public String getResetObject() {
                return new JSONArray( ).toString( );
            }
        } );
    }
}
