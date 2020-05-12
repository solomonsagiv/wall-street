package serverObjects.indexObjects;

import DDE.DDECells;
import api.tws.requesters.DaxRequester;
import basketFinder.BasketService;
import basketFinder.handlers.DaxStocksHandler;
import basketFinder.handlers.StocksHandler;
import dataBase.mySql.mySqlComps.TablesEnum;
import dataBase.mySql.myTables.index.IndexStocksTable;
import logic.LogicService;
import options.IndexOptions;
import options.OptionsEnum;
import options.OptionsHandler;
import serverObjects.ApiEnum;
import tws.TwsContractsEnum;

import java.time.LocalTime;

public class Dax extends INDEX_CLIENT_OBJECT {

    static Dax client = null;

    // Constructor
    public Dax() {
        setName( "dax" );
        setIndexBidAskMargin( .5 );
        setDbId( 1 );
        setStrikeMargin( 5 );
        setBaseId( 100000 );
        initDDECells( );
        setIndexStartTime( LocalTime.of( 10, 0, 0 ) );
        setIndexEndTime( LocalTime.of( 18, 30, 0 ) );
        setFutureEndTime( LocalTime.of( 18, 45, 0 ) );
        setiTwsRequester( new DaxRequester( ) );
        setLogicService( new LogicService( this, OptionsEnum.MONTH ) );
//        roll( );
        baskets();
        myTableHandler();
    }

    private void myTableHandler() {
        tablesHandler.addTable( TablesEnum.INDEX_STOCKS, new IndexStocksTable( this ) );
    }

    private void baskets() {
        stocksHandler = new DaxStocksHandler( 103000);
        basketService = new BasketService(this, stocksHandler, 20);
    }

//    private void roll() {
//        rollHandler = new RollHandler( this );
//
//        Roll quarter_quarterFar = new Roll( this, OptionsEnum.WEEK, OptionsEnum.MONTH, RollPriceEnum.FUTURE );
//        rollHandler.addRoll( RollEnum.QUARTER_QUARTER_FAR, quarter_quarterFar );
//    }

    // Get instance
    public static Dax getInstance() {
        if ( client == null ) {
            client = new Dax( );
        }
        return client;
    }

    @Override
    public void initOptionsHandler() throws NullPointerException {

        // Fut Quarter
        IndexOptions weekOptions = new IndexOptions( getBaseId( ) + 1000, this, OptionsEnum.WEEK, TwsContractsEnum.OPT_WEEK, null );

        // Fut Quarter far
        IndexOptions monthOptions = new IndexOptions( getBaseId( ) + 2000, this, OptionsEnum.MONTH, TwsContractsEnum.OPT_MONTH, null );

        OptionsHandler optionsHandler = new OptionsHandler( this );
        optionsHandler.addOptions( weekOptions );
        optionsHandler.addOptions( monthOptions );
        optionsHandler.setMainOptions( weekOptions );
        setOptionsHandler( optionsHandler );
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
    public void initBaseId() {
        setBaseId( 100000 );
    }

    @Override
    public double getTheoAvgMargin() {
        return 0.05;
    }

    public BasketService getBasketService() {
        return basketService;
    }

    public StocksHandler getStocksHandler() {
        return stocksHandler;
    }
}
