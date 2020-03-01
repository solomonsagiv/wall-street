package serverObjects.indexObjects;

import DDE.DDECells;
import api.Manifest;
import com.ib.client.Contract;
import dataBase.mySql.mySqlComps.MyTableHandler;
import dataBase.mySql.tables.MyDayTable;
import dataBase.mySql.tables.MySumTable;
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
    public void initTwsData() {

        TwsData twsData = getTwsData( );

        twsData.setQuantity( 3 );

        Contract indexContract = new Contract( );
        indexContract.symbol( "SPX" );
        indexContract.secType( "IND" );
        indexContract.currency( "USD" );
        indexContract.exchange( "CBOE" );
        indexContract.multiplier( "50" );
        twsData.appendTwsContract( TwsContractsEnum.INDEX, indexContract );

        Contract futureContract = new Contract( );
        futureContract.symbol( "ES" );
        futureContract.secType( "FUT" );
        futureContract.currency( "USD" );
        futureContract.lastTradeDateOrContractMonth( Manifest.EXPIRY );
        futureContract.exchange( "GLOBEX" );
        futureContract.multiplier( "50" );
        twsData.appendTwsContract( TwsContractsEnum.FUTURE, futureContract );

        Contract optionsDayContract = new Contract( );
        optionsDayContract.secType( "OPT" );
        optionsDayContract.currency( "USD" );
        optionsDayContract.exchange( "SMART" );
        optionsDayContract.tradingClass( "SPXW" );
        optionsDayContract.multiplier( "100" );
        optionsDayContract.symbol( "SPXW" );
        optionsDayContract.includeExpired( true );
        twsData.appendTwsContract( TwsContractsEnum.WEEK, optionsDayContract );

        Contract optionsMonthContract = new Contract( );
        optionsMonthContract.secType( "OPT" );
        optionsMonthContract.currency( "USD" );
        optionsMonthContract.exchange( "SMART" );
        optionsMonthContract.tradingClass( "SPX" );
        optionsMonthContract.multiplier( "100" );
        optionsMonthContract.symbol( "SPX" );
        optionsMonthContract.includeExpired( true );
        twsData.appendTwsContract( TwsContractsEnum.MONTH, optionsMonthContract );

        Contract optionsQuarterContract = new Contract( );
        optionsQuarterContract.secType( "OPT" );
        optionsQuarterContract.currency( "USD" );
        optionsQuarterContract.exchange( "SMART" );
        optionsQuarterContract.tradingClass( "SPX" );
        optionsQuarterContract.multiplier( "100" );
        optionsQuarterContract.symbol( "SPX" );
        optionsQuarterContract.includeExpired( true );
        twsData.appendTwsContract( TwsContractsEnum.QUARTER, optionsQuarterContract );

        Contract optionsQuarterFarContract = new Contract( );
        optionsQuarterFarContract.secType( "OPT" );
        optionsQuarterFarContract.currency( "USD" );
        optionsQuarterFarContract.exchange( "SMART" );
        optionsQuarterFarContract.tradingClass( "SPX" );
        optionsQuarterFarContract.multiplier( "100" );
        optionsQuarterFarContract.symbol( "SPX" );
        optionsQuarterFarContract.includeExpired( true );
        twsData.appendTwsContract( TwsContractsEnum.QUARTER_FAR, optionsQuarterFarContract );

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
        getTwsData( ).setIndexId( getBaseId( ) + 1 );
        getTwsData( ).setFutureId( getBaseId( ) + 2 );
        getTwsData().setContractDetailsId(getBaseId() + 3);
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
    public double getTheoAvgMargin() {
        return 0.05;
    }


}
