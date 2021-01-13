package serverObjects.indexObjects;

import DDE.DDECells;
import api.tws.requesters.DaxRequester;
import basketFinder.BasketService;
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
        setIndexStartTime(LocalTime.of(10, 0, 0));
        setIndexEndTime(LocalTime.of(18, 30, 0));
        setFutureEndTime(LocalTime.of(18, 45, 0));
        setiTwsRequester(new DaxRequester());
        setLogicService(new LogicService(this, ExpStrings.e1));
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
