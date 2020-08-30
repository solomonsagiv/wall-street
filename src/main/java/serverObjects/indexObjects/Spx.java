package serverObjects.indexObjects;

import DDE.DDECells;
import DDE.DDECellsEnum;
import api.tws.requesters.SpxRequester;
import charts.myCharts.FourLineChart;
import charts.myCharts.OpAvg15Future_E2_IndexCounter_Index_Chart;
import charts.myCharts.OpAvgFuture_E2_IndexCounter_Index_Chart;
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
    public ApiEnum getApi() {
        return ApiEnum.DDE;
    }

    @Override
    public void initBaseId() {
        setBaseId( 10000 );
    }

    @Override
    public void openChartsOnStart() {

        FourLineChart chart = new FourLineChart( client );
        chart.createChart( );

        OpAvgFuture_E2_IndexCounter_Index_Chart chart2 = new OpAvgFuture_E2_IndexCounter_Index_Chart( client );
        try {
            chart2.createChart( );
        } catch ( CloneNotSupportedException cloneNotSupportedException ) {
            cloneNotSupportedException.printStackTrace( );
        }

        OpAvg15Future_E2_IndexCounter_Index_Chart chart3 = new OpAvg15Future_E2_IndexCounter_Index_Chart( client );
        try {
            chart3.createChart( );
        } catch ( CloneNotSupportedException cloneNotSupportedException ) {
            cloneNotSupportedException.printStackTrace( );
        }

    }

    @Override
    public double getTheoAvgMargin() {
        return 0.05;
    }

}
