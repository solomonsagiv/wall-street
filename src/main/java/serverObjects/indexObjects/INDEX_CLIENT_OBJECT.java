package serverObjects.indexObjects;

import basketFinder.BasketService;
import basketFinder.handlers.StocksHandler;
import dataBase.mySql.TablesHandler;
import dataBase.mySql.myBaseTables.MyBoundsTable;
import dataBase.mySql.myJsonTables.index.DayJsonTable;
import dataBase.mySql.mySqlComps.TablesEnum;
import dataBase.mySql.myTables.TwsContractsTable;
import dataBase.mySql.myTables.ArraysTable;
import dataBase.mySql.myTables.StatusJsonTable;
import dataBase.mySql.myTables.SumJsonTable;
import exp.E;
import exp.ExpEnum;
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
        super( );
        initTablesHandler( );
    }

    public void initTablesHandler() {
        tablesHandler = new TablesHandler( );
        tablesHandler.addTable( TablesEnum.TWS_CONTRACTS, new TwsContractsTable( this ) );
        tablesHandler.addTable( TablesEnum.DAY, new DayJsonTable( this, "JsonDay" ) );
        tablesHandler.addTable( TablesEnum.STATUS, new StatusJsonTable( this, "jsonStatus" ) );
        tablesHandler.addTable( TablesEnum.SUM, new SumJsonTable( this, "JsonSum" ) );
        tablesHandler.addTable( TablesEnum.ARRAYS, new ArraysTable( this ) );
        tablesHandler.addTable( TablesEnum.BOUNDS, new MyBoundsTable( this, "bounds" ) );
    }

    @Override
    public void initExpHandler() throws NullPointerException {
        // E1
        E e1 = new E(this, ExpEnum.E1, TwsContractsEnum.FUTURE, new IndexOptionsCalc( this, ExpEnum.E1 ) );

        // E2
        E e2 = new E(this, ExpEnum.E2, TwsContractsEnum.FUTURE_FAR, new IndexOptionsCalc( this, ExpEnum.E2 ) );

        // Append to handler
        Exps exps = new Exps( this );
        exps.addExp( e1, ExpEnum.E1 );
        exps.addExp( e2, ExpEnum.E2 );
        exps.setMainExp( e1 );

        setExps( exps );
    }

    public StocksHandler getStocksHandler() {
        return stocksHandler;
    }

    public BasketService getBasketService() {
        return basketService;
    }

    @Override
    public MyJson getAsJson() {
        MyJson json = new MyJson( );
        json.put( JsonStrings.ind, getIndex( ) );
        json.put( JsonStrings.indBid, getIndexBid( ) );
        json.put( JsonStrings.indAsk, getIndexAsk( ) );
        json.put( JsonStrings.indBidAskCounter, getIndexBidAskCounter( ) );
        json.put( JsonStrings.open, getOpen( ) );
        json.put( JsonStrings.high, getHigh( ) );
        json.put( JsonStrings.low, getLow( ) );
        json.put( JsonStrings.base, getBase( ) );
        json.put( JsonStrings.roll, getRollHandler().getRoll( RollEnum.E1_E2).getAsJson());
        json.put( JsonStrings.e1, getExps( ).getExp( ExpEnum.E1 ).getAsJson( ) );
        json.put( JsonStrings.e2, getExps( ).getExp( ExpEnum.E2 ).getAsJson( ) );
        return json;
    }

    @Override
    public void loadFromJson( MyJson json ) {
        setIndexBidAskCounter( json.getInt( JsonStrings.indBidAskCounter ) );
        getExps( ).getExp( ExpEnum.E1 ).loadFromJson( new MyJson( json.getJSONObject( JsonStrings.e1 ).toString( ) ) );
        getExps( ).getExp( ExpEnum.E2 ).loadFromJson( new MyJson( json.getJSONObject( JsonStrings.e2 ).toString( ) ) );
    }

    @Override
    public MyJson getResetJson() {
        MyJson json = new MyJson( );
        json.put( JsonStrings.e1, getExps().getExp( ExpEnum.E1 ).getResetJson() );
        json.put( JsonStrings.e2, getExps().getExp( ExpEnum.E2 ).getResetJson() );
        return json;
    }
}