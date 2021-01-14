package dataBase.mySql.myTables;

import dataBase.mySql.myBaseTables.MyArraysTable;
import dataBase.mySql.mySqlComps.MyColumnSql;
import dataBase.mySql.mySqlComps.MyLoadAbleColumn;
import dataBase.mySql.mySqlComps.MySqlColumnEnum;
import exp.E;
import exp.ExpStrings;
import myJson.MyJson;
import org.json.JSONArray;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.INDEX_CLIENT_OBJECT;

import java.rmi.UnknownHostException;
import java.text.ParseException;
import java.time.LocalTime;

public class ArraysTable extends MyArraysTable {

    INDEX_CLIENT_OBJECT client;

    // Constructor
    public ArraysTable( BASE_CLIENT_OBJECT client ) {
        super( client );
        this.client = ( INDEX_CLIENT_OBJECT ) client;
    }

    @Override
    public void initColumns() {
        addColumn( new MyColumnSql< String >( this, MySqlColumnEnum.time ) {
            @Override
            public String getObject() {
                return LocalTime.now( ).toString( );
            }
        } );
        addColumn( new MyLoadAbleColumn< String >( this, MySqlColumnEnum.indexList ) {
            @Override
            public String getObject() throws UnknownHostException, ParseException {
                return client.getIndexSeries( ).getLastJson( ).toString( );
            }

            @Override
            public void setLoadedObject( String object ) {
                client.getIndexSeries( ).add( new MyJson( object ) );
            }

            @Override
            public String getResetObject() {
                return new JSONArray( ).toString( );
            }
        } );
        addColumn( new MyLoadAbleColumn< Double >( this, MySqlColumnEnum.opList ) {
            @Override
            public Double getObject() {
                int last = client.getExps( ).getMainExp( ).getOpFutList( ).size( ) - 1;
                return client.getExps( ).getMainExp( ).getOpFutList( ).get( last );
            }

            @Override
            public void setLoadedObject( Double object ) {
                client.getExps( ).getMainExp( ).getOpFutList( ).add( object );
            }

            @Override
            public Double getResetObject() {
                return null;
            }
        } );

        addColumn( new MyLoadAbleColumn< String >( this, MySqlColumnEnum.indexBidAskCounterList ) {
            @Override
            public String getObject() throws UnknownHostException, ParseException {
                return client.getIndexBidAskCounterSeries( ).getLastJson( ).toString( );
            }

            @Override
            public void setLoadedObject( String object ) {
                if ( object != null ) {
                    client.getIndexBidAskCounterSeries( ).add( new MyJson( object ) );
                }
            }

            @Override
            public String getResetObject() {
                return new JSONArray( ).toString( );
            }
        } );
        addColumn( new MyLoadAbleColumn< String >( this, MySqlColumnEnum.opAvgFutureList ) {
            @Override
            public String getObject() throws UnknownHostException, ParseException {
                return client.getExps( ).getExp( ExpStrings.e1 ).getOpAvgFutSeries( ).getLastJson( ).toString( );
            }

            @Override
            public void setLoadedObject( String object ) {
                if ( object != null ) {
                    client.getExps( ).getExp( ExpStrings.e1 ).getOpAvgFutSeries( ).add( new MyJson( object ) );
                }
            }

            @Override
            public String getResetObject() {
                return new JSONArray( ).toString( );
            }
        } );
        addColumn( new MyLoadAbleColumn< String >( this, MySqlColumnEnum.quarterFutBidAskCounterList ) {
            @Override
            public String getObject() throws UnknownHostException, ParseException {
                return client.getExps( ).getExp( ExpStrings.e1 ).getFutBidAskCounterSeries( ).getLastJson( ).toString( );
            }

            @Override
            public void setLoadedObject( String object ) {
                client.getExps( ).getExp( ExpStrings.e1 ).getFutBidAskCounterSeries( ).add( new MyJson( object ) );
            }

            @Override
            public String getResetObject() {
                return new JSONArray( ).toString( );
            }
        } );
        addColumn( new MyLoadAbleColumn< String >( this, MySqlColumnEnum.quarterFarFutBidAskCounterList ) {
            @Override
            public String getObject() throws UnknownHostException, ParseException {
                return client.getExps( ).getExp( ExpStrings.e2 ).getFutBidAskCounterSeries( ).getLastJson( ).toString( );
            }

            @Override
            public void setLoadedObject( String object ) {
                if ( object != null ) {
                    client.getExps( ).getExp( ExpStrings.e2 ).getFutBidAskCounterSeries( ).add( new MyJson( object ) );
                }
            }

            @Override
            public String getResetObject() {
                return new JSONArray( ).toString( );
            }
        } );

    }
}
