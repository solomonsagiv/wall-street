package serverObjects.stockObjects;

import dataBase.mySql.TablesHandler;
import dataBase.mySql.myBaseTables.MyBoundsTable;
import dataBase.mySql.myJsonTables.index.DayJsonTable;
import dataBase.mySql.mySqlComps.TablesEnum;
import dataBase.mySql.myTables.StatusJsonTable;
import dataBase.mySql.myTables.SumJsonTable;
import dataBase.mySql.myTables.TwsContractsTable;
import dataBase.mySql.myTables.stock.StockArraysTable;
import exp.ExpMonth;
import exp.ExpStrings;
import exp.ExpWeek;
import exp.Exps;
import logic.LogicService;
import myJson.MyJson;
import options.JsonStrings;
import options.optionsCalcs.StockOptionsCalc;
import roll.Roll;
import roll.RollEnum;
import roll.RollHandler;
import roll.RollPriceEnum;
import serverObjects.ApiEnum;
import serverObjects.BASE_CLIENT_OBJECT;
import tws.TwsContractsEnum;

import java.time.LocalTime;

public abstract class STOCK_OBJECT extends BASE_CLIENT_OBJECT {

    public STOCK_OBJECT() {
        super();
        setIndexStartTime(LocalTime.of(16, 30, 0));
        setIndexEndTime(LocalTime.of(23, 0, 0));
        setFutureEndTime(LocalTime.of(23, 15, 0));
        initTablesHandler();
        setLogicService(new LogicService(this, ExpStrings.month));
        roll();
    }

    protected void roll() {
        rollHandler = new RollHandler(this);

        Roll weekMonth = new Roll(this, ExpStrings.week, ExpStrings.month, RollPriceEnum.CONTRACT);
        rollHandler.addRoll(RollEnum.WEEK_MONTH, weekMonth);
    }

    public void initTablesHandler() {
        tablesHandler = new TablesHandler();
        tablesHandler.addTable(TablesEnum.TWS_CONTRACTS, new TwsContractsTable(this));
        tablesHandler.addTable(TablesEnum.DAY, new DayJsonTable(this));
        tablesHandler.addTable(TablesEnum.STATUS, new StatusJsonTable(this));
        tablesHandler.addTable(TablesEnum.SUM, new SumJsonTable(this));
        tablesHandler.addTable(TablesEnum.ARRAYS, new StockArraysTable(this));
        tablesHandler.addTable(TablesEnum.BOUNDS, new MyBoundsTable(this));
    }

    @Override
    public void initExpHandler() {

        // Week
        ExpWeek expWeek = new ExpWeek(this, ExpStrings.week, TwsContractsEnum.OPT_WEEK, new StockOptionsCalc(this, ExpStrings.week));

        // Month
        ExpMonth expMonth = new ExpMonth(this, ExpStrings.month, TwsContractsEnum.OPT_MONTH, new StockOptionsCalc(this, ExpStrings.month));

        // Exp handler
        Exps exps = new Exps(this);
        exps.addExp(expWeek, ExpStrings.week);
        exps.addExp(expMonth, ExpStrings.month);
        exps.setMainExp(expMonth);

        setExps(exps);
    }

    @Override
    public void setIndex(double index) {
        if (this.index == 0) {
            this.index = index;
            getExps().initOptions(index);

            // Request options tws
            if (getApi() == ApiEnum.TWS) {
                getTwsHandler().requestOptions(getExps().getExpList());
            }
        }
        this.index = index;
    }

    @Override
    public MyJson getAsJson() {
        MyJson json = new MyJson();
        json.put(JsonStrings.ind, getIndex());
        json.put(JsonStrings.indBid, getIndexBid());
        json.put(JsonStrings.indAsk, getIndexAsk());
        json.put(JsonStrings.indBidAskCounter, getIndexBidAskCounter());
        json.put(JsonStrings.indUp, getIndexUp());
        json.put(JsonStrings.indDown, getIndexDown());
        json.put(JsonStrings.conUp, getConUp());
        json.put(JsonStrings.conDown, getConDown());
        json.put(JsonStrings.open, getOpen());
        json.put(JsonStrings.high, getHigh());
        json.put(JsonStrings.low, getLow());
        json.put(JsonStrings.base, getBase());
        json.put(JsonStrings.roll, getRollHandler().getRoll(RollEnum.WEEK_MONTH).getAsJson());
        json.put(JsonStrings.exps, getExps().getAsJson());
        return json;
    }

    @Override
    public void loadFromJson(MyJson json) {
        setIndexBidAskCounter(json.getInt(JsonStrings.indBidAskCounter));
        getExps().loadFromJson(new MyJson(json.getJSONObject(JsonStrings.exps)));
    }

    @Override
    public MyJson getResetJson() {
        MyJson json = new MyJson();
        json.put(JsonStrings.week, getExps().getExp(ExpStrings.week).getResetJson());
        json.put(JsonStrings.month, getExps().getExp(ExpStrings.month).getResetJson());
        return json;
    }

    @Override
    public String toString() {
        return "BASE_CLIENT_OBJECT{" +
                ", optionsHandler=" + exps.toString() +
                ", startOfIndexTrading=" + getIndexStartTime() +
                ", endOfIndexTrading=" + getIndexEndTime() +
                ", endFutureTrading=" + getFutureEndTime() +
                ", loadFromDb=" + isLoadFromDb() +
                ", dbRunning=" + isDbRunning() +
                ", ids=" + getIds() +
                ", started=" + isStarted() +
                ", dbId=" + getDbId() +
                ", index=" + index +
                ", indexBidAskCounter=" + getIndexBidAskCounter() +
                ", indexBid=" + getIndexBid() +
                ", indexAsk=" + getIndexAsk() +
                ", open=" + getOpen() +
                ", high=" + getHigh() +
                ", low=" + getLow() +
                ", base=" + getBase() +
                ", indexBidAskMargin=" + getIndexBidAskMargin() +
                ", listsService=" + getListsService() +
                ", mySqlService=" + getMySqlService() +
                ", racesMargin=" + getRacesMargin() +
                ", conUp=" + getConUp() +
                ", conDown=" + getConDown() +
                ", indexUp=" + getIndexUp() +
                ", indexDown=" + getIndexDown() +
                ", indexList=" + getIndexSeries().getItemCount() +
                '}';
    }

}
