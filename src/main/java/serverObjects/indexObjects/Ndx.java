package serverObjects.indexObjects;

import DDE.DDECells;
import api.Manifest;
import api.tws.TwsHandler;
import serverObjects.ApiEnum;
import tws.MyContract;
import tws.TwsContractsEnum;

import java.time.LocalTime;

public class Ndx extends INDEX_CLIENT_OBJECT {

    static Ndx client = null;

    // Private constructor
    public Ndx() {
        super( );
        setName( "ndx" );
        setRacesMargin( 0.7 );
        setIndexBidAskMargin( 1.25 );
        setDbId( 3 );
        setStrikeMargin( 40 );
        setBaseId( 20000 );
        initTablesHandlers();
        initDDECells();
    }

    // Get instance
    public static Ndx getInstance() {
        if ( client == null ) {
            client = new Ndx( );
        }
        return client;
    }

    @Override
    public void initTwsHandler() {

        TwsHandler twsData = new TwsHandler( );

        MyContract indexContract = new MyContract( getBaseId() + 1, TwsContractsEnum.INDEX);
        indexContract.symbol( "NDX" );
        indexContract.secType( "IND" );
        indexContract.currency( "USD" );
        indexContract.exchange( "NASDAQ" );
        indexContract.multiplier( "20" );
        twsData.addContract( indexContract );

        MyContract futureContract = new MyContract( getBaseId() + 2, TwsContractsEnum.FUTURE );
        futureContract.symbol( "NQ" );
        futureContract.secType( "FUT" );
        futureContract.currency( "USD" );
        futureContract.exchange( "GLOBEX" );
        futureContract.lastTradeDateOrContractMonth( Manifest.EXPIRY );
        futureContract.multiplier( "20" );
        twsData.addContract( futureContract );

        MyContract optionsDayContract = new MyContract( getBaseId() + 1000, TwsContractsEnum.OPT_WEEK );
        optionsDayContract.secType( "OPT" );
        optionsDayContract.currency( "USD" );
        optionsDayContract.exchange( "SMART" );
        optionsDayContract.tradingClass( "NDXP" );
        optionsDayContract.symbol( "NDXP" );
        optionsDayContract.includeExpired( true );
        twsData.addContract( optionsDayContract );

        MyContract optionsMonthContract = new MyContract( getBaseId() + 2000, TwsContractsEnum.OPT_MONTH );
        optionsMonthContract.secType( "OPT" );
        optionsMonthContract.currency( "USD" );
        optionsMonthContract.exchange( "SMART" );
        optionsMonthContract.tradingClass( "NDX" );
        optionsMonthContract.symbol( "NDX" );
        optionsMonthContract.includeExpired( true );
        twsData.addContract( optionsMonthContract );

        MyContract optionsQuarterContract = new MyContract( getBaseId() + 3000, TwsContractsEnum.OPT_QUARTER );
        optionsQuarterContract.secType( "OPT" );
        optionsQuarterContract.currency( "USD" );
        optionsQuarterContract.exchange( "SMART" );
        optionsQuarterContract.symbol( "NDX" );
        optionsQuarterContract.multiplier( "100" );
        optionsQuarterContract.tradingClass( "NDX" );
        optionsQuarterContract.includeExpired( true );
        twsData.addContract( optionsQuarterContract );

        MyContract optionsQuarterFarContract = new MyContract( getBaseId() + 4000, TwsContractsEnum.OPT_QUARTER );
        optionsQuarterFarContract.secType( "OPT" );
        optionsQuarterFarContract.currency( "USD" );
        optionsQuarterFarContract.exchange( "SMART" );
        optionsQuarterFarContract.tradingClass( "NDX" );
        optionsQuarterFarContract.symbol( "NDX" );
        optionsQuarterFarContract.includeExpired( true );
        twsData.addContract( optionsQuarterFarContract );

        setTwsHandler(twsData);

    }

    @Override
    public void initDDECells() {

        DDECells ddeCells = new DDECells( ) {
            @Override
            public boolean isWorkWithDDE() {
                return true;
            }
        };

        // TODO init cells

        setDdeCells( ddeCells );

    }

    @Override
    public ApiEnum getApi() {
        return ApiEnum.TWS;
    }

    @Override
    public void requestApi() {

    }

    @Override
    public double getTheoAvgMargin() {
        return 0;
    }

}
