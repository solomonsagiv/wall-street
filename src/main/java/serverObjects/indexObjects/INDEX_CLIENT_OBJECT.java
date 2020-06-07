package serverObjects.indexObjects;

import basketFinder.BasketService;
import basketFinder.handlers.StocksHandler;
import dataBase.mySql.TablesHandler;
import dataBase.mySql.myBaseTables.MyBoundsTable;
import dataBase.mySql.mySqlComps.TablesEnum;
import dataBase.mySql.myTables.TwsContractsTable;
import dataBase.mySql.myTables.index.ArraysTable;
import dataBase.mySql.myTables.index.DayTable;
import dataBase.mySql.myTables.index.StatusTable;
import dataBase.mySql.myTables.index.SumTable;
import exp.E1;
import exp.E2;
import exp.ExpEnum;
import exp.ExpHandler;
import myJson.MyJson;
import options.JsonEnum;
import options.Options;
import options.optionsCalcs.IndexOptionsCalc;
import serverObjects.BASE_CLIENT_OBJECT;
import tws.TwsContractsEnum;

public abstract class INDEX_CLIENT_OBJECT extends BASE_CLIENT_OBJECT {

    protected BasketService basketService;
    protected StocksHandler stocksHandler;

    public INDEX_CLIENT_OBJECT() {
        super();
        initTablesHandler();
    }

    public void initTablesHandler() {
        tablesHandler = new TablesHandler();
        tablesHandler.addTable(TablesEnum.TWS_CONTRACTS, new TwsContractsTable(this));
        tablesHandler.addTable(TablesEnum.DAY, new DayTable(this));
        tablesHandler.addTable(TablesEnum.STATUS, new StatusTable(this));
        tablesHandler.addTable(TablesEnum.SUM, new SumTable(this));
        tablesHandler.addTable(TablesEnum.ARRAYS, new ArraysTable(this));
        tablesHandler.addTable(TablesEnum.BOUNDS, new MyBoundsTable(this, "bounds"));
    }

    @Override
    public void initExpHandler() throws NullPointerException {

        // E1
        Options e1_options = new Options(getBaseId() + 3000, this, TwsContractsEnum.OPT_E1);
        E1 e1 = new E1(this, e1_options, );


        // E2
        Options e2_options = new Options(getBaseId() + 4000, this, TwsContractsEnum.OPT_E2);
        E2 e2 = new E2(this, e2_options, new IndexOptionsCalc());

        // Append to handler
        ExpHandler expHandler = new ExpHandler(this);
        expHandler.addExp(e1, ExpEnum.E1);
        expHandler.addExp(e2, ExpEnum.E2);
        expHandler.setMainExp(e1);

        setExpHandler(expHandler);
    }

    public StocksHandler getStocksHandler() {
        return stocksHandler;
    }

    public BasketService getBasketService() {
        return basketService;
    }

    @Override
    public MyJson getAsJson() {
        MyJson json = new MyJson();
        json.put(JsonEnum.ind, getIndex());
        json.put(JsonEnum.indBid, getIndexBid());
        json.put(JsonEnum.indAsk, getIndexAsk());
        json.put(JsonEnum.indBidAskCounter, getIndexBidAskCounter());
        json.put(JsonEnum.open, getOpen());
        json.put(JsonEnum.high, getHigh());
        json.put(JsonEnum.low, getLow());
        json.put(JsonEnum.base, getBase());
        json.put(JsonEnum.e1, getExpHandler().getExp(ExpEnum.E1).getAsJson());
        json.put(JsonEnum.e2, getExpHandler().getExp(ExpEnum.E2).getAsJson());
        return json;
    }

    @Override
    public void loadFromJson(MyJson json) {
        setIndexBidAskCounter(json.getInt(JsonEnum.indBidAskCounter));
        getExpHandler().getExp(ExpEnum.E1).loadFromJson(new MyJson(json.getJSONObject(JsonEnum.e1).toString()));
        getExpHandler().getExp(ExpEnum.E2).loadFromJson(new MyJson(json.getJSONObject(JsonEnum.e2).toString()));
    }
}
