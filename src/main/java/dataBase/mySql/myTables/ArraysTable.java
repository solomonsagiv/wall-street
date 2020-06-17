package dataBase.mySql.myTables;

import dataBase.mySql.myBaseTables.MyArraysTable;
import dataBase.mySql.mySqlComps.MyColumnSql;
import dataBase.mySql.mySqlComps.MyLoadAbleColumn;
import dataBase.mySql.mySqlComps.MySqlColumnEnum;
import exp.ExpEnum;
import lists.MyChartPoint;
import myJson.MyJson;
import org.json.JSONArray;
import org.json.JSONObject;
import serverObjects.BASE_CLIENT_OBJECT;

import java.rmi.UnknownHostException;
import java.time.LocalTime;

public class ArraysTable extends MyArraysTable {

    // Constructor
    public ArraysTable( BASE_CLIENT_OBJECT client ) {
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
                client.getIndexSeries().add( new MyJson( object ) );
            }

            @Override
            public String getResetObject() {
                return new JSONArray( ).toString( );
            }
        } );
        addColumn( new MyLoadAbleColumn< Double >( this, MySqlColumnEnum.OP_LIST ) {
            @Override
            public Double getObject() {
                int last = client.getExps( ).getMainExp( ).getOpFutureList( ).size( ) - 1;
                return client.getExps( ).getMainExp( ).getOpFutureList( ).get( last );
            }

            @Override
            public void setLoadedObject( Double object ) {
                client.getExps( ).getMainExp( ).getOpFutureList( ).add( object );
            }

            @Override
            public Double getResetObject() {
                return null;
            }
        } );

        addColumn( new MyLoadAbleColumn< String >( this, MySqlColumnEnum.IND_BID_ASK_COUNTER_LIST ) {
            @Override
            public String getObject() throws UnknownHostException {
                return client.getIndexBidAskCounterSeries().getLastJson().toString();
            }

            @Override
            public void setLoadedObject( String object ) {
                if ( object != null ) {
                    client.getIndexBidAskCounterSeries().add( new MyJson( object ) );
                }
            }
            @Override
            public String getResetObject() {
                return new JSONArray( ).toString( );
            }
        } );
        addColumn( new MyLoadAbleColumn< String >( this, MySqlColumnEnum.OP_AVG_FUTURE_LIST ) {
            @Override
            public String getObject() throws UnknownHostException {
                return client.getExps( ).getExp( ExpEnum.E1 ).getOpAvgFutureSeries().getLastJson().toString();
            }
            @Override
            public void setLoadedObject( String object ) {
                if ( object != null ) {
                    MyChartPoint myChartPoint = new MyChartPoint( new JSONObject( object ) );
                    client.getExps( ).getExp( ExpEnum.E1 ).getOpAvgFutureSeries().add( new MyJson( object ) );
                }
            }
            @Override
            public String getResetObject() {
                return new JSONArray( ).toString( );
            }
        } );
        addColumn( new MyLoadAbleColumn< String >( this, MySqlColumnEnum.QUARTER_FUT_BID_ASK_COUNTER_LIST ) {
            @Override
            public String getObject() throws UnknownHostException {
                return client.getExps( ).getExp( ExpEnum.E1 ).getFutBidAskCounterSeries().getLastJson().toString();
            }

            @Override
            public void setLoadedObject( String object ) {
                client.getExps( ).getExp( ExpEnum.E1 ).getFutBidAskCounterSeries().add( new MyJson( object ) );
            }

            @Override
            public String getResetObject() {
                return new JSONArray( ).toString( );
            }
        } );
        addColumn( new MyLoadAbleColumn< String >( this, MySqlColumnEnum.QUARTER_FAR_FUT_BID_ASK_COUNTER_LIST ) {
            @Override
            public String getObject() throws UnknownHostException {
                return client.getExps( ).getExp( ExpEnum.E2 ).getFutBidAskCounterSeries().getLastJson().toString();
            }

            @Override
            public void setLoadedObject( String object ) {
                if ( object != null ) {
                    client.getExps( ).getExp( ExpEnum.E2 ).getFutBidAskCounterSeries().add( new MyJson( object ) );
                }
            }

            @Override
            public String getResetObject() {
                return new JSONArray( ).toString( );
            }
        } );
    }
}
