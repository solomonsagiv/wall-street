package dataBase.mySql.myTables.stock;

import dataBase.mySql.myBaseTables.MyArraysTable;
import dataBase.mySql.mySqlComps.MyColumnSql;
import dataBase.mySql.mySqlComps.MyLoadAbleColumn;
import dataBase.mySql.mySqlComps.MySqlColumnEnum;
import exp.ExpEnum;
import lists.MyChartPoint;
import myJson.MyJson;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeriesDataItem;
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
        addColumn( new MyColumnSql< String >( this, MySqlColumnEnum.time) {
            @Override
            public String getObject() {
                return LocalTime.now( ).toString( );
            }
        } );
        addColumn( new MyLoadAbleColumn< String >( this, MySqlColumnEnum.INDEX_LIST ) {
            @Override
            public String getObject() throws UnknownHostException {
                return client.getIndexSeries().getLastJson().toString();
            }

            @Override
            public void setLoadedObject( String object ) {
                client.getIndexSeries().add(new MyJson( object ) );
            }

            @Override
            public String getResetObject() {
                return new JSONArray( ).toString( );
            }
        } );

        addColumn( new MyLoadAbleColumn< Double >( this, MySqlColumnEnum.OP_LIST ) {
            @Override
            public Double getObject() {
                int last = client.getExps( ).getMainExp( ).getOptions().getOpList( ).size() - 1;
                return client.getExps( ).getMainExp( ).getOptions().getOpList( ).get(last);
            }

            @Override
            public void setLoadedObject( Double object ) {
                client.getExps( ).getMainExp( ).getOptions().getOpList( ).add(object);
            }

            @Override
            public Double getResetObject() {
                return null;
            }
        } );
        addColumn( new MyLoadAbleColumn< String >( this, MySqlColumnEnum.IND_BID_ASK_COUNTER_LIST ) {
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
        addColumn( new MyLoadAbleColumn< String >( this, MySqlColumnEnum.CON_WEEK_BID_ASK_COUNTER_LIST ) {
            @Override
            public String getObject() throws UnknownHostException {
                return client.getExps( ).getExp( ExpEnum.WEEK ).getOptions().getConBidAskCounterList( ).getLast().getAsJson().toString( );
            }

            @Override
            public void setLoadedObject( String object ) {
                client.getExps( ).getExp( ExpEnum.WEEK ).getOptions().getConBidAskCounterList( ).add(new MyChartPoint(new JSONObject(object)));
            }
            @Override
            public String getResetObject() {
                return new JSONArray( ).toString( );
            }
        } );
        addColumn( new MyLoadAbleColumn< String >( this, MySqlColumnEnum.CON_MONTH_BID_ASK_COUNTER_LIST ) {
                @Override
                public String getObject() throws UnknownHostException {
                    return client.getExps( ).getExp( ExpEnum.MONTH ).getOptions().getConBidAskCounterList( ).getLast().getAsJson().toString( );
                }

                @Override
                public void setLoadedObject( String object ) {
                    client.getExps( ).getExp( ExpEnum.MONTH ).getOptions().getConBidAskCounterList( ).add(new MyChartPoint(new JSONObject(object)));
                }

                @Override
                public String getResetObject() {
                    return new JSONArray( ).toString( );
                }
        } );
    }
}
