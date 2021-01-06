package serverObjects.indexObjects;

import DDE.DDECells;
import DDE.DDECellsEnum;
import api.Manifest;
import api.tws.requesters.SpxRequester;
import basketFinder.handlers.StocksHandler;
import charts.myCharts.FuturesChart;
import dataBase.mySql.mySqlComps.TablesEnum;
import dataBase.mySql.myTables.index.IndexStocksTable;
import exp.E;
import exp.ExpStrings;
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
        setLogicService( new LogicService( this, ExpStrings.e1 ) );
        roll( );
        myTableHandler( );
        initStocksHandler( );
    }

    private void initStocksHandler() {
        stocksHandler = new StocksHandler( 10200, client );
    }

    // get instance
    public static Spx getInstance() {
        if ( client == null ) {
            client = new Spx( );
        }
        return client;
    }

    private void myTableHandler() {
        tablesHandler.addTable( TablesEnum.INDEX_STOCKS, new IndexStocksTable( this ) );
    }

    private void roll() {
        rollHandler = new RollHandler( this );

        Roll quarter_quarterFar = new Roll( this, ExpStrings.e1, ExpStrings.e2, RollPriceEnum.FUTURE );
        rollHandler.addRoll( RollEnum.E1_E2, quarter_quarterFar );
    }

    @Override
    public void initExpHandler() throws NullPointerException {

        // E1
        OptionsDDeCells e1DDeCells = new OptionsDDeCells( "R12C10", "R12C9", "R12C11" );
        E e = new E( this, ExpStrings.e1, TwsContractsEnum.FUTURE, new IndexOptionsCalc( this, ExpStrings.e1 ), e1DDeCells );

        // E2
        OptionsDDeCells e2DDeCells = new OptionsDDeCells( "R13C10", "R13C9", "R13C11" );
        E e2 = new E( this, ExpStrings.e2, TwsContractsEnum.FUTURE_FAR, new IndexOptionsCalc( this, ExpStrings.e2 ), e2DDeCells );

        // Add to
        Exps exps = new Exps( this );
        exps.addExp( e, ExpStrings.e1 );
        exps.addExp( e2, ExpStrings.e2 );
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

        ddeCells.addCell( DDECellsEnum.OPEN, "R13C4" );
        ddeCells.addCell( DDECellsEnum.HIGH, "R13C1" );
        ddeCells.addCell( DDECellsEnum.LOW, "R13C2" );
        ddeCells.addCell( DDECellsEnum.BASE, "R11C5" );
        ddeCells.addCell( DDECellsEnum.FUT_DAY, "R9C10" );
        ddeCells.addCell( DDECellsEnum.FUT_WEEK, "R10C10" );
        ddeCells.addCell( DDECellsEnum.FUT_MONTH, "R11C10" );
        ddeCells.addCell( DDECellsEnum.FUT_QUARTER, "R12C10" );
        ddeCells.addCell( DDECellsEnum.FUT_QUARTER_FAR, "R13C10" );

        setDdeCells( ddeCells );

    }

    @Override
    public void setIndexBid( double indexBid ) {
        super.setIndexBid( indexBid );

        // Margin counter
        double bidMargin = index - indexBid;
        double askMargin = getIndexAsk( ) - index;
        double marginOfMarings = askMargin - bidMargin;

        if ( marginOfMarings > 0 ) {
            indBidMarginCounter += marginOfMarings;
        }
    }

    @Override
    public void setIndexAsk( double indexAsk ) {
        super.setIndexAsk( indexAsk );
        // Margin counter
        double bidMargin = index - getIndexBid( );
        double askMargin = indexAsk - index;
        double marginOfMarings = bidMargin - askMargin;

        if ( marginOfMarings > 0 && marginOfMarings < 5 ) {
            indAskMarginCounter += marginOfMarings;
        }
    }

    @Override
    public void setIndex( double index ) {
        super.setIndex( index );
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
    public void openChartsOnStart() {
        if ( Manifest.OPEN_CHARTS ) {
            FuturesChart chart = new FuturesChart( this );
            chart.createChart();
        }
    }

    @Override
    public double getTheoAvgMargin() {
        return 0.05;
    }

}
