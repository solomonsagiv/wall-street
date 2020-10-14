package serverObjects.indexObjects;

import DDE.DDECells;
import DDE.DDECellsEnum;
import api.Manifest;
import api.tws.requesters.SpxRequester;
import charts.myCharts.EDeltaChart;
import charts.myCharts.FourLineChart;
import charts.myCharts.FullCharts;
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
    }

    // Get instance
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
        OptionsDDeCells e1DDeCells = new OptionsDDeCells( "R19C2", "R19C1", "R19C3" );
        E e = new E( this, ExpStrings.e1, TwsContractsEnum.FUTURE, new IndexOptionsCalc( this, ExpStrings.e1 ), e1DDeCells );

        // E2
        OptionsDDeCells e2DDeCells = new OptionsDDeCells( "R21C2", "R21C1", "R21C3" );
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
        ddeCells.addCell( DDECellsEnum.INDEX_MOVE_15, "R3C1" );

        ddeCells.addCell( DDECellsEnum.OPEN, "R10C4" );
        ddeCells.addCell( DDECellsEnum.HIGH, "R10C1" );
        ddeCells.addCell( DDECellsEnum.LOW, "R10C2" );
        ddeCells.addCell( DDECellsEnum.BASE, "R8C5" );

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
            FourLineChart chart = new FourLineChart( client );
            chart.createChart( );

            EDeltaChart fullCharts = new EDeltaChart( client );
            try {
                fullCharts.createChart( );
            } catch ( CloneNotSupportedException e ) {
                e.printStackTrace( );
            }
        }

    }

    @Override
    public double getTheoAvgMargin() {
        return 0.05;
    }

}
