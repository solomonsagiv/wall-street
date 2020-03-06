package serverObjects.stockObjects;

import DDE.DDECells;
import api.tws.TwsHandler;
import dataBase.mySql.mySqlComps.MyTableHandler;
import dataBase.mySql.tables.MyDayTable;
import dataBase.mySql.tables.MySumTable;
import serverObjects.ApiEnum;
import tws.MyContract;
import tws.TwsContractsEnum;

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
        setBaseId( 30000 );
        setDbId( 4 );
    }

    @Override
    public void initTwsHandler() {
        TwsHandler twsData = new TwsHandler( );

        MyContract indexContract = new MyContract( getBaseId( ) + 1, TwsContractsEnum.INDEX );
        indexContract.symbol( "AAPL" );
        indexContract.secType( "STK" );
        indexContract.primaryExch( "NASDAQ" );
        indexContract.currency( "USD" );
        indexContract.exchange( "SMART" );

        twsData.addContract( indexContract );

        MyContract optionsMonthContract = new MyContract( getBaseId( ) + 1000, TwsContractsEnum.OPT_MONTH );
        optionsMonthContract.secType( "OPT" );
        optionsMonthContract.currency( "USD" );
        optionsMonthContract.exchange( "SMART" );
        optionsMonthContract.tradingClass( "AAPL" );
        optionsMonthContract.multiplier( "100" );
        optionsMonthContract.symbol( "AAPL" );
        optionsMonthContract.includeExpired( true );

        twsData.addContract( optionsMonthContract );

        setTwsHandler( twsData );
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
        // TODO
    }

    @Override
    public void initTablesHandlers() {
        MyDayTable myDayTable = new MyDayTable( this, "apple" );
        MySumTable mySumTable = new MySumTable( this, "apple_daily" );

        myTableHandler = new MyTableHandler( this, myDayTable, mySumTable );
    }

    @Override
    public void initDDECells() {
        DDECells ddeCells = new DDECells( ) {
            @Override
            public boolean isWorkWithDDE() {
                return false;
            }
        };
        setDdeCells( ddeCells );
    }

    @Override
    public ApiEnum getApi() {
        return ApiEnum.TWS;
    }

    @Override
    public void requestApi() {
        TwsHandler handler = getTwsHandler( );
        handler.request( handler.getMyContract( TwsContractsEnum.INDEX ) );
    }
}
