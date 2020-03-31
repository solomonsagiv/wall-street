package serverObjects.stockObjects;

import DDE.DDECells;
import api.tws.TwsHandler;
import options.OptionsEnum;
import roll.Roll;
import roll.RollEnum;
import roll.RollHandler;
import serverObjects.ApiEnum;
import tws.MyContract;
import tws.TwsContractsEnum;

import java.time.LocalTime;

public class Apple extends STOCK_OBJECT {

    static Apple client = null;

    // Constructor
    public Apple() {
        super( );

        rollHandler = new RollHandler( this );
        Roll quarter_quarterFar = new Roll( getOptionsHandler().getOptions( OptionsEnum.QUARTER ), getOptionsHandler().getOptions( OptionsEnum.QUARTER_FAR ) );
        rollHandler.addRoll( RollEnum.QUARTER_QUARTER_FAR, quarter_quarterFar );

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

        MyContract optionsMonthContract = new MyContract( getBaseId( ) + 2000, TwsContractsEnum.OPT_MONTH );
        optionsMonthContract.secType( "OPT" );
        optionsMonthContract.currency( "USD" );
        optionsMonthContract.exchange( "SMART" );
        optionsMonthContract.tradingClass( "AAPL" );
        optionsMonthContract.multiplier( "100" );
        optionsMonthContract.symbol( "AAPL" );
        optionsMonthContract.includeExpired( true );

        twsData.addContract( optionsMonthContract );

        MyContract optionsQuarterContract = new MyContract( getBaseId( ) + 3000, TwsContractsEnum.OPT_QUARTER );
        optionsQuarterContract.secType( "OPT" );
        optionsQuarterContract.currency( "USD" );
        optionsQuarterContract.exchange( "SMART" );
        optionsQuarterContract.tradingClass( "AAPL" );
        optionsQuarterContract.multiplier( "100" );
        optionsQuarterContract.symbol( "AAPL" );
        optionsQuarterContract.includeExpired( true );
        twsData.addContract( optionsQuarterContract );

        MyContract optionsQuarterFarContract = new MyContract( getBaseId( ) + 4000, TwsContractsEnum.OPT_QUARTER_FAR );
        optionsQuarterFarContract.secType( "OPT" );
        optionsQuarterFarContract.currency( "USD" );
        optionsQuarterFarContract.exchange( "SMART" );
        optionsQuarterFarContract.tradingClass( "AAPL" );
        optionsQuarterFarContract.multiplier( "100" );
        optionsQuarterFarContract.symbol( "AAPL" );
        optionsQuarterFarContract.includeExpired( true );

        twsData.addContract( optionsQuarterFarContract );

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
