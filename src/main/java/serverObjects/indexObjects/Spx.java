package serverObjects.indexObjects;

import DDE.DDECells;
import api.Manifest;
import api.tws.TwsHandler;
import com.ib.client.Contract;
import dataBase.mySql.mySqlComps.MyTableHandler;
import dataBase.mySql.tables.MyDayTable;
import dataBase.mySql.tables.MySumTable;
import serverObjects.ApiEnum;
import tws.MyContract;
import tws.TwsContractsEnum;
import tws.TwsData;

import java.time.LocalTime;

public class Spx extends INDEX_CLIENT_OBJECT {

    static Spx client = null;

    // Constructor
    public Spx() {
        super( );
    }

    // Get instance
    public static Spx getInstance() {
        if ( client == null ) {
            client = new Spx( );
        }
        return client;
    }

    @Override
    public double getEqualMovePlag() {
         return .25;
    }

    @Override
    public double getIndexBidAskMargin() {
        return .5;
    }

    @Override
    public void initTwsHandler() {

        TwsHandler twsHandler = new TwsHandler();

        // Index
        MyContract indexContract = new MyContract( getBaseId() + 1, TwsContractsEnum.INDEX );
        indexContract.symbol( "SPX" );
        indexContract.secType( "IND" );
        indexContract.currency( "USD" );
        indexContract.exchange( "CBOE" );
        indexContract.multiplier( "50" );

        twsHandler.addContract( indexContract );

        // Future
        MyContract futureContract = new MyContract( getBaseId() + 2, TwsContractsEnum.FUTURE );
        futureContract.symbol( "ES" );
        futureContract.secType( "FUT" );
        futureContract.currency( "USD" );
        futureContract.lastTradeDateOrContractMonth( Manifest.EXPIRY );
        futureContract.exchange( "GLOBEX" );
        futureContract.multiplier( "50" );

        twsHandler.addContract( futureContract );

        // Week options
        MyContract optWeekContract = new MyContract( getBaseId() + 1000, TwsContractsEnum.OPT_WEEK );
        optWeekContract.secType( "OPT" );
        optWeekContract.currency( "USD" );
        optWeekContract.exchange( "SMART" );
        optWeekContract.tradingClass( "SPXW" );
        optWeekContract.multiplier( "100" );
        optWeekContract.symbol( "SPXW" );
        optWeekContract.includeExpired( true );

        twsHandler.addContract( optWeekContract );

        // Month options
        MyContract optionsMonthContract = new MyContract( getBaseId() + 2000, TwsContractsEnum.OPT_MONTH );
        optionsMonthContract.secType( "OPT" );
        optionsMonthContract.currency( "USD" );
        optionsMonthContract.exchange( "SMART" );
        optionsMonthContract.tradingClass( "SPX" );
        optionsMonthContract.multiplier( "100" );
        optionsMonthContract.symbol( "SPX" );
        optionsMonthContract.includeExpired( true );

        twsHandler.addContract( optionsMonthContract );

        // Quarter options
        MyContract optionsQuarterContract = new MyContract( getBaseId() + 3000, TwsContractsEnum.OPT_QUARTER );
        optionsQuarterContract.secType( "OPT" );
        optionsQuarterContract.currency( "USD" );
        optionsQuarterContract.exchange( "SMART" );
        optionsQuarterContract.tradingClass( "SPX" );
        optionsQuarterContract.multiplier( "100" );
        optionsQuarterContract.symbol( "SPX" );
        optionsQuarterContract.includeExpired( true );

        twsHandler.addContract( optionsQuarterContract );

        MyContract optionsQuarterFarContract = new MyContract( getBaseId() + 4000, TwsContractsEnum.OPT_QUARTER_FAR );
        optionsQuarterFarContract.secType( "OPT" );
        optionsQuarterFarContract.currency( "USD" );
        optionsQuarterFarContract.exchange( "SMART" );
        optionsQuarterFarContract.tradingClass( "SPX" );
        optionsQuarterFarContract.multiplier( "100" );
        optionsQuarterFarContract.symbol( "SPX" );
        optionsQuarterFarContract.includeExpired( true );

        twsHandler.addContract( optionsQuarterFarContract );

        setTwsHandler( twsHandler );
    }

    @Override
    public void initName() {
        setName( "spx" );
    }

    @Override
    public void initRacesMargin() {
        setRacesMargin( .3 );
    }

    @Override
    public double getStrikeMargin() {
        return 5;
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
        setEndFutureTrading( LocalTime.of( 23, 15, 0 ) );
    }

    @Override
    public void initIds() {
        setBaseId( 10000 );
    }

    @Override
    public void initDbId() {
        setDbId( 2 );
    }

    @Override
    public void initTablesHandlers() {

        MyDayTable myDayTable = new MyDayTable( this, "spx" );
        MySumTable mySumTable = new MySumTable( this, "spx_daily" );

        myTableHandler = new MyTableHandler( this, myDayTable, mySumTable);
    }

    @Override
    public void initDDECells() {
        DDECells ddeCells = new DDECells( ) {
            @Override
            public boolean isWorkWithDDE() {
                return true;
            }
        };

        // Fut
        ddeCells.setFutCell( "R3C10" );
        ddeCells.setFutBidCell( "R4C10" );
        ddeCells.setFutAskCell( "R2C10" );

        // Ind
        ddeCells.setIndCell( "R6C3" );
        ddeCells.setIndBidCell( "R6C4" );
        ddeCells.setIndAskCell( "R6C2" );

        setDdeCells( ddeCells );
    }

    @Override
    public ApiEnum getApi() {
        return ApiEnum.DDE;
    }

    @Override
    public void requestApi() {

    }

    @Override
    public double getTheoAvgMargin() {
        return 0.05;
    }


}
