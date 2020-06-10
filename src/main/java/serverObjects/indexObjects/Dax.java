package serverObjects.indexObjects;

import DDE.DDECells;
import api.tws.requesters.DaxRequester;
import basketFinder.BasketService;
import basketFinder.handlers.DaxStocksHandler;
import basketFinder.handlers.StocksHandler;
import dataBase.mySql.mySqlComps.TablesEnum;
import dataBase.mySql.myTables.index.IndexStocksTable;
import logic.LogicService;
import options.optionsCalcs.IndexOptionsCalc;
import options.OptionsEnum;
import exp.Exps;
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

    // Get instance
    public static Dax getInstance() {
        if ( client == null ) {
            client = new Dax( );
        }
        return client;
    }

    @Override
    public void initExpHandler() throws NullPointerException {

        // Fut Quarter
        IndexOptionsCalc weekOptions = new IndexOptionsCalc( getBaseId( ) + 1000, this, OptionsEnum.WEEK, TwsContractsEnum.OPT_WEEK, null );

        // Fut Quarter far
        IndexOptionsCalc monthOptions = new IndexOptionsCalc( getBaseId( ) + 2000, this, OptionsEnum.MONTH, TwsContractsEnum.OPT_MONTH, null );

        Exps exps = new Exps( this );
        exps.appExp( weekOptions );
        exps.appExp( monthOptions );
        exps.setMainExp( weekOptions );
        setExps( exps );
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
