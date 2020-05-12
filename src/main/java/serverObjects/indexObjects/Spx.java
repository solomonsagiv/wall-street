package serverObjects.indexObjects;

import DDE.DDECells;
import DDE.DDECellsEnum;
import api.tws.requesters.SpxRequester;
import basketFinder.BasketService;
import basketFinder.handlers.SpxStocksHandler;
import basketFinder.handlers.StocksHandler;
import dataBase.mySql.mySqlComps.TablesEnum;
import dataBase.mySql.myTables.index.IndexStocksTable;
import logic.LogicService;
import options.IndexOptions;
import options.OptionsDDeCells;
import options.OptionsEnum;
import options.OptionsHandler;
import roll.Roll;
import roll.RollEnum;
import roll.RollHandler;
import roll.RollPriceEnum;
import serverObjects.ApiEnum;
import tws.TwsContractsEnum;

import java.time.LocalTime;

public class Spx extends INDEX_CLIENT_OBJECT {

    static Spx client = null;

    private BasketService basketService;
    private StocksHandler stocksHandler;

    // Constructor
    public Spx() {
        setName( "spx" );
        setIndexBidAskMargin( .5 );
        setDbId( 2 );
        setStrikeMargin( 5 );
        setBaseId( 10000 );
        initDDECells( );
        setIndexStartTime( LocalTime.of( 16, 31, 0 ) );
        setIndexEndTime( LocalTime.of( 23, 0, 0 ) );
        setFutureEndTime( LocalTime.of( 23, 15, 0 ) );
        setiTwsRequester( new SpxRequester( ) );
        setLogicService( new LogicService( this, OptionsEnum.QUARTER ) );
        roll( );
        baskets();
        myTableHandler();
    }

    private void myTableHandler() {
        tablesHandler.addTable( TablesEnum.INDEX_STOCKS, new IndexStocksTable( this ) );
    }

    private void baskets() {
        stocksHandler = new SpxStocksHandler( 10200);
        basketService = new BasketService(this, stocksHandler, 300);
    }

    private void roll() {
        rollHandler = new RollHandler( this );

        Roll quarter_quarterFar = new Roll( this, OptionsEnum.QUARTER, OptionsEnum.QUARTER_FAR, RollPriceEnum.FUTURE );
        rollHandler.addRoll( RollEnum.QUARTER_QUARTER_FAR, quarter_quarterFar );
    }

    // Get instance
    public static Spx getInstance() {
        if ( client == null ) {
            client = new Spx( );
        }
        return client;
    }

    @Override
    public void initOptionsHandler() throws NullPointerException {

        // Fut Quarter
        OptionsDDeCells quarterDDeCells = new OptionsDDeCells( "R19C2", "R19C1", "R19C3" );
        IndexOptions quarterOptions = new IndexOptions( getBaseId( ) + 3000, this, OptionsEnum.QUARTER, TwsContractsEnum.OPT_QUARTER, quarterDDeCells );

        // Fut Quarter far
        OptionsDDeCells quarterFarDDeCells = new OptionsDDeCells( "R21C2", "R21C1", "R21C3" );
        IndexOptions quarterFarOptions = new IndexOptions( getBaseId( ) + 4000, this, OptionsEnum.QUARTER_FAR, TwsContractsEnum.OPT_QUARTER_FAR, quarterFarDDeCells );

        OptionsHandler optionsHandler = new OptionsHandler( this );
        optionsHandler.addOptions( quarterOptions );
        optionsHandler.addOptions( quarterFarOptions );
        optionsHandler.setMainOptions( quarterOptions );
        setOptionsHandler( optionsHandler );
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
        ddeCells.addCell( DDECellsEnum.INDEX_MOVE_15, "R3C1" );


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
    public void initBaseId() {
        setBaseId( 10000 );
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
