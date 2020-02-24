package serverObjects.indexObjects;

import api.Manifest;
import com.ib.client.Contract;
import dataBase.mySql.mySqlComps.MyTableHandler;
import dataBase.mySql.tables.MyDayTable;
import dataBase.mySql.tables.MySumTable;
import tws.TwsData;

import java.time.LocalTime;

public class Ndx extends INDEX_CLIENT_OBJECT {

    static Ndx client = null;

    // Private constructor
    public Ndx() {
        super( );
    }

    // Get instance
    public static Ndx getInstance() {
        if ( client == null ) {
            client = new Ndx( );
        }
        return client;
    }

    @Override
    public double getEqualMovePlag() {
        return .6;
    }

    @Override
    public double getIndexBidAskMargin() {
        return 1.25;
    }

    @Override
    public void initTwsData() {

        TwsData twsData = getTwsData( );

        Contract indexContract = new Contract( );
        indexContract.symbol( "NDX" );
        indexContract.secType( "IND" );
        indexContract.currency( "USD" );
        indexContract.exchange( "NASDAQ" );
        indexContract.multiplier( "20" );
        twsData.setIndexContract( indexContract );

        Contract futureContract = new Contract( );
        futureContract.symbol( "NQ" );
        futureContract.secType( "FUT" );
        futureContract.currency( "USD" );
        futureContract.exchange( "GLOBEX" );
        futureContract.lastTradeDateOrContractMonth( Manifest.EXPIRY );
        futureContract.multiplier( "20" );
        twsData.setFutureContract( futureContract );

        Contract optionsDayContract = new Contract( );
        optionsDayContract.secType( "OPT" );
        optionsDayContract.currency( "USD" );
        optionsDayContract.exchange( "SMART" );
        optionsDayContract.tradingClass( "NDXP" );
        optionsDayContract.symbol( "NDXP" );
        optionsDayContract.includeExpired( true );
        twsData.setOptionsDayContract( optionsDayContract );

        Contract optoinsMonthContract = new Contract( );
        optoinsMonthContract.secType( "OPT" );
        optoinsMonthContract.currency( "USD" );
        optoinsMonthContract.exchange( "SMART" );
        optoinsMonthContract.tradingClass( "NDX" );
        optoinsMonthContract.symbol( "NDX" );
        optoinsMonthContract.includeExpired( true );
        twsData.setOptionMonthContract( optoinsMonthContract );

        Contract optoinsQuarterContract = new Contract( );
        optoinsQuarterContract.secType( "OPT" );
        optoinsQuarterContract.currency( "USD" );
        optoinsQuarterContract.exchange( "SMART" );
        optoinsQuarterContract.symbol( "NDX" );
        optoinsQuarterContract.multiplier( "100" );
        optoinsQuarterContract.tradingClass( "NDX" );
        optoinsQuarterContract.includeExpired( true );
        twsData.setOptionsQuarterContract( optoinsQuarterContract );

        Contract optoinsQuarterFarContract = new Contract( );
        optoinsQuarterFarContract.secType( "OPT" );
        optoinsQuarterFarContract.currency( "USD" );
        optoinsQuarterFarContract.exchange( "SMART" );
        optoinsQuarterFarContract.tradingClass( "NDX" );
        optoinsQuarterFarContract.symbol( "NDX" );
        optoinsQuarterFarContract.includeExpired( true );
        twsData.setOptionsQuarterFarContract( optoinsQuarterFarContract );

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

        setBaseId( 20000 );
        getTwsData( ).setIndexId( getBaseId( ) + 1 );
        getTwsData( ).setFutureId( getBaseId( ) + 2 );

    }

    @Override
    public void initDbId() {
        setDbId( 3 );
    }

    @Override
    public void initName() {
        setName( "ndx" );
    }

    @Override
    public void initRacesMargin() {
        setRacesMargin( .7 );
    }

    @Override
    public double getStrikeMargin() {
        return 40;
    }

    @Override
    public void initTablesHandlers() {
        MyDayTable myDayTable = new MyDayTable( this, "ndx" );
        MySumTable mySumTable = new MySumTable( this, "ndx_daily" );

        myTableHandler = new MyTableHandler( this, myDayTable, mySumTable);
    }

    @Override
    public double getTheoAvgMargin() {
        return 0;
    }

}
