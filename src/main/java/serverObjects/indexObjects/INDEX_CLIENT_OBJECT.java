package serverObjects.indexObjects;

import basketFinder.BasketService;
import basketFinder.handlers.StocksHandler;
import dataBase.mySql.TablesHandler;
import dataBase.mySql.myBaseTables.MyBoundsTable;
import dataBase.mySql.myJsonTables.index.DayJsonTable;
import dataBase.mySql.mySqlComps.TablesEnum;
import dataBase.mySql.myTables.ArraysTable;
import dataBase.mySql.myTables.StatusJsonTable;
import dataBase.mySql.myTables.SumJsonTable;
import dataBase.mySql.myTables.TwsContractsTable;
import exp.E;
import exp.ExpStrings;
import exp.Exps;
import myJson.MyJson;
import options.JsonStrings;
import options.optionsCalcs.IndexOptionsCalc;
import roll.RollEnum;
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
        tablesHandler.addTable(TablesEnum.DAY, new DayJsonTable(this));
        tablesHandler.addTable(TablesEnum.STATUS, new StatusJsonTable(this));
        tablesHandler.addTable(TablesEnum.SUM, new SumJsonTable(this));
        tablesHandler.addTable(TablesEnum.ARRAYS, new ArraysTable(this));
        tablesHandler.addTable(TablesEnum.BOUNDS, new MyBoundsTable(this));
    }

    @Override
    public void initExpHandler() throws NullPointerException {
        // E1
        E e1 = new E(this, ExpStrings.e1, TwsContractsEnum.FUTURE, new IndexOptionsCalc(this, ExpStrings.e1));

        // E2
        E e2 = new E(this, ExpStrings.e2, TwsContractsEnum.FUTURE_FAR, new IndexOptionsCalc(this, ExpStrings.e2));

        // Append to handler
        Exps exps = new Exps(this);
        exps.addExp(e1, ExpStrings.e1);
        exps.addExp(e2, ExpStrings.e2);
        exps.setMainExp(e1);

        setExps(exps);
    }

    @Override
    public MyJson getAsJson() {
        MyJson json = new MyJson();
        json.put(JsonStrings.ind, getIndex());
        json.put(JsonStrings.indBid, getIndexBid());
        json.put(JsonStrings.indAsk, getIndexAsk());
        json.put(JsonStrings.indBidAskCounter, getIndexBidAskCounter());
        json.put(JsonStrings.open, getOpen());
        json.put(JsonStrings.high, getHigh());
        json.put(JsonStrings.low, getLow());
        json.put(JsonStrings.base, getBase());
        json.put(JsonStrings.roll, getRollHandler().getRoll(RollEnum.E1_E2).getAsJson());
        json.put(JsonStrings.exps, getExps().getAsJson());
        return json;
    }

    @Override
    public void loadFromJson(MyJson json) {
        setIndexBidAskCounter(json.getInt(JsonStrings.indBidAskCounter));
        getExps().loadFromJson(new MyJson(json.getJSONObject(JsonStrings.exps)));
    }

    public StocksHandler getStocksHandler() {
        return stocksHandler;
    }

    @Override
    public MyJson getResetJson() {
        MyJson json = new MyJson();
        json.put( JsonStrings.exps, exps.getResetJson() );
        return json;
    }

}