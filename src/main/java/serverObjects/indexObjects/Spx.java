package serverObjects.indexObjects;

import DDE.DDECells;
import DDE.DDECellsEnum;
import api.Manifest;
import api.tws.TwsHandler;
import options.IndexOptions;
import options.OptionsDDeCells;
import options.OptionsEnum;
import options.OptionsHandler;
import serverObjects.ApiEnum;
import tws.MyContract;
import tws.TwsContractsEnum;

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

        TwsHandler twsHandler = new TwsHandler( );

        // Index
        MyContract indexContract = new MyContract( getBaseId( ) + 1, TwsContractsEnum.INDEX );
        indexContract.symbol( "SPX" );
        indexContract.secType( "IND" );
        indexContract.currency( "USD" );
        indexContract.exchange( "CBOE" );
        indexContract.multiplier( "50" );
        twsHandler.addContract( indexContract );

        MyContract futureContract = new MyContract( getBaseId( ) + 2, TwsContractsEnum.FUTURE );
        futureContract.symbol( "ES" );
        futureContract.secType( "FUT" );
        futureContract.currency( "USD" );
        futureContract.lastTradeDateOrContractMonth( Manifest.EXPIRY );
        futureContract.exchange( "GLOBEX" );
        futureContract.multiplier( "50" );
        twsHandler.addContract( futureContract );

        // Week options
        MyContract optWeekContract = new MyContract( getBaseId( ) + 1000, TwsContractsEnum.OPT_WEEK );
        optWeekContract.secType( "OPT" );
        optWeekContract.currency( "USD" );
        optWeekContract.exchange( "SMART" );
        optWeekContract.tradingClass( "SPXW" );
        optWeekContract.multiplier( "100" );
        optWeekContract.symbol( "SPXW" );
        optWeekContract.includeExpired( true );
        twsHandler.addContract( optWeekContract );

        // Month options
        MyContract optionsMonthContract = new MyContract( getBaseId( ) + 2000, TwsContractsEnum.OPT_MONTH );
        optionsMonthContract.secType( "OPT" );
        optionsMonthContract.currency( "USD" );
        optionsMonthContract.exchange( "SMART" );
        optionsMonthContract.tradingClass( "SPX" );
        optionsMonthContract.multiplier( "100" );
        optionsMonthContract.symbol( "SPX" );
        optionsMonthContract.includeExpired( true );
        twsHandler.addContract( optionsMonthContract );

        // Quarter options
        MyContract optionsQuarterContract = new MyContract( getBaseId( ) + 3000, TwsContractsEnum.OPT_QUARTER );
        optionsQuarterContract.secType( "OPT" );
        optionsQuarterContract.currency( "USD" );
        optionsQuarterContract.exchange( "SMART" );
        optionsQuarterContract.tradingClass( "SPX" );
        optionsQuarterContract.multiplier( "100" );
        optionsQuarterContract.symbol( "SPX" );
        optionsQuarterContract.includeExpired( true );
        twsHandler.addContract( optionsQuarterContract );

        MyContract optionsQuarterFarContract = new MyContract( getBaseId( ) + 4000, TwsContractsEnum.OPT_QUARTER_FAR );
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
    public void initOptionsHandler() throws NullPointerException {

        // Fut month
        OptionsDDeCells monthDDeCells = new OptionsDDeCells( "R5C3", "R5C2", "R5C4" );
        IndexOptions monthOptions = new IndexOptions( getBaseId( ) + 2000, this, OptionsEnum.MONTH, getTwsHandler( ).getMyContract( TwsContractsEnum.OPT_MONTH ), monthDDeCells );

        // Fut Quarter
        OptionsDDeCells quarterDDeCells = new OptionsDDeCells( "R21C2", "R21C1", "R5C3" );
        IndexOptions quarterOptions = new IndexOptions( getBaseId( ) + 3000, this, OptionsEnum.QUARTER, getTwsHandler( ).getMyContract( TwsContractsEnum.OPT_QUARTER ), quarterDDeCells );

        OptionsHandler optionsHandler = new OptionsHandler( this ) {
            @Override
            public void initOptions() {
                addOptions( monthOptions );
                addOptions( quarterOptions );
            }

            @Override
            public void initMainOptions() {
                setMainOptions( monthOptions );
            }
        };
        setOptionsHandler( optionsHandler );
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
        setStartOfIndexTrading( LocalTime.of( 15, 30, 0 ) );
    }

    @Override
    public void initEndOfIndexTrading() {
        setEndOfIndexTrading( LocalTime.of( 22, 0, 0 ) );
    }

    @Override
    public void initEndOfFutureTrading() {
        setEndFutureTrading( LocalTime.of( 22, 15, 0 ) );
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
    public void initDDECells() {
        DDECells ddeCells = new DDECells( ) {
            @Override
            public boolean isWorkWithDDE() {
                return true;
            }
        };

        // Ind
        ddeCells.addCell( DDECellsEnum.IND_BID, "R2C2" );
        ddeCells.addCell( DDECellsEnum.IND, "R2C3" );
        ddeCells.addCell( DDECellsEnum.IND_ASK, "R2C4" );

        ddeCells.addCell( DDECellsEnum.OPEN, "R10C4" );
        ddeCells.addCell( DDECellsEnum.HIGH, "R10C1" );
        ddeCells.addCell( DDECellsEnum.LOW, "R10C2" );
        ddeCells.addCell( DDECellsEnum.BASE, "R8C5" );

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
