package serverObjects.indexObjects;

import basketFinder.BasketService;
import basketFinder.handlers.StocksHandler;
import dataBase.mySql.TablesHandler;
import dataBase.mySql.myBaseTables.MyBoundsTable;
import dataBase.mySql.myJsonTables.index.DayJsonTable;
import dataBase.mySql.mySqlComps.TablesEnum;
import dataBase.mySql.myTables.TwsContractsTable;
import dataBase.mySql.myTables.index.ArraysTable;
import dataBase.mySql.myTables.index.StatusTable;
import dataBase.mySql.myTables.index.SumTable;
import exp.E_Index;
import exp.ExpEnum;
import exp.ExpHandler;
import myJson.MyJson;
import options.JsonStrings;
import serverObjects.BASE_CLIENT_OBJECT;

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
        tablesHandler.addTable( TablesEnum.DAY, new DayJsonTable( this ) );
        tablesHandler.addTable( TablesEnum.STATUS, new StatusTable( this ) );
        tablesHandler.addTable( TablesEnum.SUM, new SumTable( this ) );
        tablesHandler.addTable( TablesEnum.ARRAYS, new ArraysTable( this ) );
        tablesHandler.addTable( TablesEnum.BOUNDS, new MyBoundsTable( this, "bounds" ) );
    }

    @Override
    public void initExpHandler() throws NullPointerException {
        // E1
        E_Index e1_index = new E_Index(this );

        // E2
        E_Index e2 = new E_Index( this );

        // Append to handler
        ExpHandler expHandler = new ExpHandler( this );
        expHandler.addExp( e1_index, ExpEnum.E1 );
        expHandler.addExp( e2, ExpEnum.E2 );
        expHandler.setMainExp( e1_index );

        setExpHandler( expHandler );
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
        json.put( JsonStrings.e1, getExpHandler( ).getExp( ExpEnum.E1 ).getAsJson( ) );
        json.put( JsonStrings.e2, getExpHandler( ).getExp( ExpEnum.E2 ).getAsJson( ) );
        return json;
    }

    @Override
    public void loadFromJson( MyJson json ) {
        setIndexBidAskCounter( json.getInt( JsonStrings.indBidAskCounter ) );
        getExpHandler( ).getExp( ExpEnum.E1 ).loadFromJson( new MyJson( json.getJSONObject( JsonStrings.e1 ).toString( ) ) );
        getExpHandler( ).getExp( ExpEnum.E2 ).loadFromJson( new MyJson( json.getJSONObject( JsonStrings.e2 ).toString( ) ) );
    }

    @Override
    public MyJson getResetJson() {
        MyJson json = new MyJson( );
        json.put( JsonStrings.e1, getExpHandler().getExp( ExpEnum.E1 ).getResetJson() );
        json.put( JsonStrings.e2, getExpHandler().getExp( ExpEnum.E2 ).getResetJson() );
        return json;
    }
}