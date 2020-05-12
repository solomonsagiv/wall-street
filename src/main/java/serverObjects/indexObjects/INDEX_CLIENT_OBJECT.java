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
import options.IndexOptions;
import options.OptionsEnum;
import options.OptionsHandler;
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
    public void initOptionsHandler() throws NullPointerException {

        IndexOptions optionsQuarter = new IndexOptions(getBaseId() + 3000, this, OptionsEnum.QUARTER, TwsContractsEnum.OPT_QUARTER);
        IndexOptions optionsQuarterFar = new IndexOptions(getBaseId() + 4000, this, OptionsEnum.QUARTER_FAR, TwsContractsEnum.OPT_QUARTER_FAR);

        OptionsHandler optionsHandler = new OptionsHandler(this);
        optionsHandler.addOptions(optionsQuarter);
        optionsHandler.addOptions(optionsQuarterFar);
        optionsHandler.setMainOptions(optionsQuarter);

        setOptionsHandler(optionsHandler);
    }


    public StocksHandler getStocksHandler() {
        return stocksHandler;
    }

    public BasketService getBasketService() {
        return basketService;
    }
}
