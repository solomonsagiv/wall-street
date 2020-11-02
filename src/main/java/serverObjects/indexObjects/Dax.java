package serverObjects.indexObjects;

import DDE.DDECells;
import api.tws.requesters.DaxRequester;
import basketFinder.BasketService;
import basketFinder.handlers.DaxStocksHandler;
import basketFinder.handlers.StocksHandler;
import dataBase.mySql.mySqlComps.TablesEnum;
import dataBase.mySql.myTables.index.IndexStocksTable;
import exp.E;
import exp.ExpStrings;
import exp.Exps;
import logic.LogicService;
import options.optionsCalcs.IndexOptionsCalc;
import serverObjects.ApiEnum;
import tws.TwsContractsEnum;

import java.time.LocalTime;

public class Dax extends INDEX_CLIENT_OBJECT {

    static Dax client = null;

    // Constructor
    public Dax() {
        setName("dax");
        setIndexBidAskMargin(.5);
        setDbId(1);
        setStrikeMargin(5);
        setBaseId(100000);
        initDDECells();
        setIndexStartTime(LocalTime.of(10, 0, 0));
        setIndexEndTime(LocalTime.of(18, 30, 0));
        setFutureEndTime(LocalTime.of(18, 45, 0));
        setiTwsRequester(new DaxRequester());
        setLogicService(new LogicService(this, ExpStrings.e1));
        baskets();
        myTableHandler();
        getMyServiceHandler().removeService( getMySqlService() );
    }

    // Get instance
    public static Dax getInstance() {
        if (client == null) {
            client = new Dax();
        }
        return client;
    }

    private void myTableHandler() {
        tablesHandler.addTable(TablesEnum.INDEX_STOCKS, new IndexStocksTable(this));
    }

    private void baskets() {
        stocksHandler = new DaxStocksHandler(103000);
        basketService = new BasketService(this, stocksHandler, 20);
    }

    @Override
    public void initExpHandler() throws NullPointerException {
        // E1
        E e = new E(this, ExpStrings.e1, TwsContractsEnum.FUTURE, new IndexOptionsCalc(this, ExpStrings.e1));

        Exps exps = new Exps(this);
        exps.addExp(e, ExpStrings.e1);
        exps.setMainExp(e);
        setExps(exps);
    }

    @Override
    public void initDDECells() {
        DDECells ddeCells = new DDECells() {
            @Override
            public boolean isWorkWithDDE() {
                return false;
            }
        };
        setDdeCells(ddeCells);
    }

    @Override
    public ApiEnum getApi() {
        return ApiEnum.TWS;
    }

    @Override
    public void initBaseId() {
        setBaseId(100000);
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
