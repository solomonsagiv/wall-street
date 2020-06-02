package serverObjects.indexObjects;

import DDE.DDECells;
import DDE.DDECellsEnum;
import api.tws.requesters.SpxRequester;
import basketFinder.BasketService;
import basketFinder.BasketService2;
import basketFinder.handlers.SpxStocksHandler;
import basketFinder.handlers.StocksHandler;
import charts.myCharts.IndexVsQuarterVSOpAvgLiveChart;
import charts.myCharts.OpAvgFuture_E2_IndexCounter_Index_Chart;
import dataBase.mySql.mySqlComps.TablesEnum;
import dataBase.mySql.myTables.index.IndexStocksTable;
import exp.E1;
import exp.E2;
import exp.ExpEnum;
import exp.ExpHandler;
import logic.LogicService;
import options.IndexOptionsCalc;
import options.Options;
import options.OptionsDDeCells;
import options.OptionsEnum;
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
    private BasketService2 basketService2;
    private StocksHandler stocksHandler;

    // Constructor
    public Spx() {
        setName("spx");
        setIndexBidAskMargin(.5);
        setDbId(2);
        setStrikeMargin(5);
        setBaseId(10000);
        initDDECells();
        setIndexStartTime(LocalTime.of(16, 31, 0));
        setIndexEndTime(LocalTime.of(23, 0, 0));
        setFutureEndTime(LocalTime.of(23, 15, 0));
        setiTwsRequester(new SpxRequester());
        setLogicService(new LogicService(this, OptionsEnum.QUARTER));
        roll();
        baskets();
        myTableHandler();
    }

    private void myTableHandler() {
        tablesHandler.addTable(TablesEnum.INDEX_STOCKS, new IndexStocksTable(this));
    }

    private void baskets() {
        stocksHandler = new SpxStocksHandler(10200);
        basketService = new BasketService(this, stocksHandler, 350);

        basketService2 = new BasketService2(this, stocksHandler, 350);
    }

    private void roll() {
        rollHandler = new RollHandler(this);

        Roll quarter_quarterFar = new Roll(this, OptionsEnum.QUARTER, OptionsEnum.QUARTER_FAR, RollPriceEnum.FUTURE);
        rollHandler.addRoll(RollEnum.QUARTER_QUARTER_FAR, quarter_quarterFar);
    }

    // Get instance
    public static Spx getInstance() {
        if (client == null) {
            client = new Spx();
        }
        return client;
    }

    @Override
    public void startAll() {
        super.startAll();
        createCharts();
    }

    private void createCharts() {
        try {
            // 5 lines
            IndexVsQuarterVSOpAvgLiveChart indexVsQuarterVSOpAvgLiveChart = new IndexVsQuarterVSOpAvgLiveChart(this);
            indexVsQuarterVSOpAvgLiveChart.createChart();

            // OpAvg
            OpAvgFuture_E2_IndexCounter_Index_Chart opAvgFuture_e2_indexCounter_index_chart = new OpAvgFuture_E2_IndexCounter_Index_Chart(this);
            opAvgFuture_e2_indexCounter_index_chart.createChart();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initExpHandler() throws NullPointerException {

        // E1
        OptionsDDeCells e1DDeCells = new OptionsDDeCells("R19C2", "R19C1", "R19C3");
        Options e1_options = new Options(getBaseId() + 3000, this, TwsContractsEnum.OPT_E1, e1DDeCells);
        E1 e1 = new E1( this, e1_options );

        // E2
        OptionsDDeCells e2DDeCells = new OptionsDDeCells("R21C2", "R21C1", "R21C3");
        Options e2_options = new Options(getBaseId() + 4000, this, TwsContractsEnum.OPT_E2, e2DDeCells);
        E2 e2 = new E2( this, e2_options );

        // Add to
        ExpHandler expHandler = new ExpHandler(this);
        expHandler.addExp(e1, ExpEnum.E1);
        expHandler.addExp(e2, ExpEnum.E2);
        expHandler.setMainExp(e1);
        setExpHandler(expHandler);
    }

    @Override
    public void initDDECells() {
        DDECells ddeCells = new DDECells() {
            @Override
            public boolean isWorkWithDDE() {
                return true;
            }
        };

        // Ind
        ddeCells.addCell(DDECellsEnum.IND_BID, "R2C2");
        ddeCells.addCell(DDECellsEnum.IND, "R2C3");
        ddeCells.addCell(DDECellsEnum.IND_ASK, "R2C4");
        ddeCells.addCell(DDECellsEnum.INDEX_MOVE_15, "R3C1");


        ddeCells.addCell(DDECellsEnum.OPEN, "R10C4");
        ddeCells.addCell(DDECellsEnum.HIGH, "R10C1");
        ddeCells.addCell(DDECellsEnum.LOW, "R10C2");
        ddeCells.addCell(DDECellsEnum.BASE, "R8C5");

        setDdeCells(ddeCells);
    }

    @Override
    public ApiEnum getApi() {
        return ApiEnum.DDE;
    }

    @Override
    public void initBaseId() {
        setBaseId(10000);
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

    public BasketService2 getBasketService2() {
        return basketService2;
    }

}
