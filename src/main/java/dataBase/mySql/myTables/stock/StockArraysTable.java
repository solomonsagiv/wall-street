package dataBase.mySql.myTables.stock;

import dataBase.mySql.myBaseTables.MyArraysTable;
import dataBase.mySql.mySqlComps.MyColumnSql;
import dataBase.mySql.mySqlComps.MyLoadAbleColumn;
import dataBase.mySql.mySqlComps.MySqlColumnEnum;
import options.OptionsEnum;
import org.json.JSONArray;
import serverObjects.BASE_CLIENT_OBJECT;

import java.time.LocalTime;
import java.util.ArrayList;

public class StockArraysTable extends MyArraysTable {

    // Constructor
    public StockArraysTable( BASE_CLIENT_OBJECT client ) {
        super( client, "arrays" );
    }

    @Override
    public void initColumns() {
        addColumn( new MyColumnSql< String >( this, "name", MySqlColumnEnum.NAME ) {
            @Override
            public String getObject() {
                return client.getName( );
            }
        } );
        addColumn( new MyColumnSql< String >( this, "time", MySqlColumnEnum.TIME ) {
            @Override
            public String getObject() {
                return LocalTime.now( ).toString( );
            }
        } );
        addColumn( new MyLoadAbleColumn< String >( this, "indexlist", MySqlColumnEnum.INDEX_LIST ) {
            @Override
            public String getObject() {
                return client.getIndexList( ).toString( );
            }

            @Override
            public void setLoadedObject( String object ) {
                client.getIndexList( ).setData( new JSONArray( object ) );
            }

            @Override
            public String getResetObject() {
                return new JSONArray( ).toString( );
            }
        } );
        addColumn( new MyLoadAbleColumn< String >( this, "opList", MySqlColumnEnum.OP_LIST ) {
            @Override
            public String getObject() {
                return client.getOptionsHandler( ).getMainOptions( ).getOpList( ).toString( );
            }

            @Override
            public void setLoadedObject( String object ) {
                convertJsonArrayToDoubleArray( new JSONArray( object ), ( ArrayList< Double > ) client.getOptionsHandler( ).getMainOptions( ).getOpList( ) );
            }

            @Override
            public String getResetObject() {
                return new JSONArray( ).toString( );
            }
        } );
        addColumn( new MyLoadAbleColumn< String >( this, "indexBidAskCounterList", MySqlColumnEnum.IND_BID_ASK_COUNTER_LIST ) {
            @Override
            public String getObject() {
                return client.getIndexBidAskCounterList( ).toString( );
            }

            @Override
            public void setLoadedObject( String object ) {
                client.getIndexBidAskCounterList( ).setData( new JSONArray( object ) );
            }

            @Override
            public String getResetObject() {
                return new JSONArray( ).toString( );
            }
        } );
        addColumn( new MyLoadAbleColumn< String >( this, "quarterFutBidAskCounterList", MySqlColumnEnum.QUARTER_FUT_BID_ASK_COUNTER_LIST ) {
            @Override
            public String getObject() {
                return client.getOptionsHandler( ).getOptions( OptionsEnum.QUARTER ).getFutBidAskCounterList( ).toString( );
            }

            @Override
            public void setLoadedObject( String object ) {
                client.getOptionsHandler( ).getOptions( OptionsEnum.QUARTER ).getFutBidAskCounterList( ).setData( new JSONArray( object ) );
            }

            @Override
            public String getResetObject() {
                return new JSONArray( ).toString( );
            }
        } );
        addColumn( new MyLoadAbleColumn< String >( this, "quarterFarFutBidAskCounterList", MySqlColumnEnum.QUARTER_FAR_FUT_BID_ASK_COUNTER_LIST ) {
            @Override
            public String getObject() {
                return client.getOptionsHandler( ).getOptions( OptionsEnum.QUARTER_FAR ).getFutBidAskCounterList( ).toString( );
            }

            @Override
            public void setLoadedObject( String object ) {
                if ( !object.isEmpty( ) ) {
                    client.getOptionsHandler( ).getOptions( OptionsEnum.QUARTER_FAR ).getFutBidAskCounterList( ).setData( new JSONArray( object ) );
                }
            }

            @Override
            public String getResetObject() {
                return new JSONArray( ).toString( );
            }
        } );

    }
}
