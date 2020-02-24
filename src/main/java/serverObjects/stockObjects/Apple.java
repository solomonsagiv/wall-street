package serverObjects.stockObjects;

import com.ib.client.Contract;
import dataBase.mySql.mySqlComps.MyTableHandler;
import dataBase.mySql.tables.MyDayTable;
import dataBase.mySql.tables.MySumTable;
import tws.TwsData;
import java.time.LocalTime;

public class Apple extends STOCK_OBJECT {

    static Apple client = null;

    // Constructor
    public Apple() {
        super( );
    }

    // Get instance
    public static Apple getInstance() {
        if ( client == null ) {
            client = new Apple( );
        }
        return client;
    }

    @Override
    public double getTheoAvgMargin() {
        return 0.05;
    }

    @Override
    public void initIds() {
        setDbId( 4 );
    }

    @Override
    public void initTwsData() {
        TwsData twsData = getTwsData( );

        twsData.setQuantity( 3 );

        Contract indexContract = new Contract( );
        indexContract.symbol( "AAPL" );
        indexContract.secType( "STK" );
        indexContract.primaryExch( "NASDAQ" );
        indexContract.currency( "USD" );
        indexContract.exchange( "SMART" );
        twsData.setIndexContract( indexContract );

        Contract optionsMonthContract = new Contract( );
        optionsMonthContract.secType( "OPT" );
        optionsMonthContract.currency( "USD" );
        optionsMonthContract.exchange( "SMART" );
        optionsMonthContract.tradingClass( "AAPL" );
        optionsMonthContract.multiplier( "100" );
        optionsMonthContract.symbol( "AAPL" );
        optionsMonthContract.includeExpired( true );
        twsData.setOptionMonthContract( optionsMonthContract );

    }

    @Override
    public void initName() {
        setName( "apple" );
    }

    @Override
    public void initRacesMargin() {

    }

    @Override
    public double getStrikeMargin() {
        return 2.5;
    }

    @Override
    public void initStartOfIndexTrading() {
        setStartOfIndexTrading( LocalTime.of( 16, 30, 0 ) );
    }

    @Override
    public void initEndOfIndexTrading() {
        setEndOfIndexTrading( LocalTime.of( 23, 0, 0 ) );
    }

    @Override
    public void initEndOfFutureTrading() {
        setEndFutureTrading( LocalTime.of( 23, 0, 0 ) );
    }

    @Override
    public void initDbId() {
        setBaseId( 30000 );
        getTwsData( ).setIndexId( getBaseId( ) + 1 );
        getTwsData( ).setFutureId( getBaseId( ) + 2 );
    }

    @Override
    public void initTablesHandlers() {
        MyDayTable myDayTable = new MyDayTable( this, "apple" );
        MySumTable mySumTable = new MySumTable( this, "apple_daily" );

        myTableHandler = new MyTableHandler( this, myDayTable, mySumTable );
    }
}
