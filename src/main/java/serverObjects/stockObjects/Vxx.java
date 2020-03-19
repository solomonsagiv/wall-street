package serverObjects.stockObjects;

import DDE.DDECells;
import api.tws.TwsHandler;
import serverObjects.ApiEnum;
import tws.MyContract;
import tws.TwsContractsEnum;

import java.time.LocalTime;

public class Vxx extends STOCK_OBJECT {

    static Vxx client = null;

    // Constructor
    public Vxx() {
        super( );
    }

    // Get instance
    public static Vxx getInstance() {
        if ( client == null ) {
            client = new Vxx( );
        }
        return client;
    }

    @Override
    public double getTheoAvgMargin() {
        return 0.05;
    }

    @Override
    public void initIds() {
        setBaseId( 40000 );
        setDbId( 5 );
    }

    @Override
    public void initTwsHandler() {
        TwsHandler twsData = new TwsHandler( );

        MyContract indexContract = new MyContract( getBaseId( ) + 1, TwsContractsEnum.INDEX );
        indexContract.symbol( "VXX" );
        indexContract.secType( "STK" );
        indexContract.primaryExch( "BATS" );
        indexContract.currency( "USD" );
        indexContract.exchange( "SMART" );
        twsData.addContract( indexContract );

        MyContract optionsMonthContract = new MyContract( getBaseId( ) + 2000, TwsContractsEnum.OPT_MONTH );
        optionsMonthContract.secType( "OPT" );
        optionsMonthContract.currency( "USD" );
        optionsMonthContract.exchange( "SMART" );
        optionsMonthContract.tradingClass( "VXX" );
        optionsMonthContract.multiplier( "100" );
        optionsMonthContract.symbol( "VXX" );
        optionsMonthContract.includeExpired( true );

        twsData.addContract( optionsMonthContract );

        MyContract optionsQuarterContract = new MyContract( getBaseId( ) + 3000, TwsContractsEnum.OPT_QUARTER );
        optionsMonthContract.secType( "OPT" );
        optionsMonthContract.currency( "USD" );
        optionsMonthContract.exchange( "SMART" );
        optionsMonthContract.tradingClass( "VXX" );
        optionsMonthContract.multiplier( "100" );
        optionsMonthContract.symbol( "VXX" );
        optionsMonthContract.includeExpired( true );

        twsData.addContract( optionsQuarterContract );

        setTwsHandler( twsData );
    }

    @Override
    public void initName() {
        setName( "Vxx" );
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
        setStartOfIndexTrading( LocalTime.of( 15, 30, 0 ) );
    }

    @Override
    public void initEndOfIndexTrading() {
        setEndOfIndexTrading( LocalTime.of( 22, 0, 0 ) );
    }

    @Override
    public void initEndOfFutureTrading() {
        setEndFutureTrading( LocalTime.of( 22, 0, 0 ) );
    }

    @Override
    public void initDbId() {
        // TODO
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
