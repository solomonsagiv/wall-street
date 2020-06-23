package serverObjects.indexObjects;

import DDE.DDECells;
import DDE.DDECellsEnum;
import api.tws.requesters.SpxRequester;
import basketFinder.BasketService;
import basketFinder.BasketService2;
import basketFinder.handlers.StocksHandler;
import charts.myCharts.IndexVsQuarterVSOpAvgLiveChart;
import dataBase.mySql.mySqlComps.TablesEnum;
import dataBase.mySql.myTables.index.IndexStocksTable;
import exp.E;
import exp.ExpEnum;
import exp.Exps;
import logic.LogicService;
import options.OptionsDDeCells;
import options.optionsCalcs.IndexOptionsCalc;
import roll.Roll;
import roll.RollEnum;
import roll.RollHandler;
import roll.RollPriceEnum;
import serverObjects.ApiEnum;
import tws.TwsContractsEnum;

import java.time.LocalTime;

public class Spx extends INDEX_CLIENT_OBJECT {

    static Spx client = null;

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
        setLogicService( new LogicService( this, ExpEnum.E1 ) );
        roll( );
        myTableHandler( );
    }

    private void myTableHandler() {
        tablesHandler.addTable( TablesEnum.INDEX_STOCKS, new IndexStocksTable( this ) );
    }

    private void roll() {
        rollHandler = new RollHandler( this );

        Roll quarter_quarterFar = new Roll( this, ExpEnum.E1, ExpEnum.E2, RollPriceEnum.FUTURE );
        rollHandler.addRoll( RollEnum.E1_E2, quarter_quarterFar );
    }

    // Get instance
    public static Spx getInstance() {
        if ( client == null ) {
            client = new Spx( );
        }
        return client;
    }

    @Override
    public void startAll() {
        super.startAll( );
        createCharts( );
    }

    private void createCharts() {
        // 5 lines
        IndexVsQuarterVSOpAvgLiveChart indexVsQuarterVSOpAvgLiveChart = new IndexVsQuarterVSOpAvgLiveChart( this );
        indexVsQuarterVSOpAvgLiveChart.createChart( );
    }

    @Override
    public void initExpHandler() throws NullPointerException {

        // E1
        OptionsDDeCells e1DDeCells = new OptionsDDeCells( "R19C2", "R19C1", "R19C3" );
        E e = new E( this, ExpEnum.E1, TwsContractsEnum.FUTURE, new IndexOptionsCalc( this, ExpEnum.E1 ), e1DDeCells );

        // E2
        OptionsDDeCells e2DDeCells = new OptionsDDeCells( "R21C2", "R21C1", "R21C3" );
        E e2 = new E( this, ExpEnum.E2, TwsContractsEnum.FUTURE_FAR, new IndexOptionsCalc( this, ExpEnum.E2 ), e2DDeCells );

        // Add to
        Exps exps = new Exps( this );
        exps.addExp( e, ExpEnum.E1 );
        exps.addExp( e2, ExpEnum.E2 );
        exps.setMainExp( e );
        setExps( exps );
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

}
