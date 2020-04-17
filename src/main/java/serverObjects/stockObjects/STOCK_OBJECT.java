package serverObjects.stockObjects;

import dataBase.mySql.TablesHandler;
import dataBase.mySql.myBaseTables.MyBoundsTable;
import dataBase.mySql.mySqlComps.TablesEnum;
import dataBase.mySql.myTables.TwsContractsTable;
import dataBase.mySql.myTables.index.ArraysTable;
import dataBase.mySql.myTables.stock.StockDayTable;
import dataBase.mySql.myTables.stock.StockStatusTable;
import dataBase.mySql.myTables.stock.StockSumTable;
import logic.LogicService;
import options.OptionsEnum;
import options.OptionsHandler;
import options.StockOptions;
import serverObjects.ApiEnum;
import serverObjects.BASE_CLIENT_OBJECT;
import tws.MyContract;
import tws.TwsContractsEnum;

public abstract class STOCK_OBJECT extends BASE_CLIENT_OBJECT {

    public STOCK_OBJECT() {
        super();
        initTablesHandler();
    }

    public void initTablesHandler() {
        tablesHandler = new TablesHandler();
        tablesHandler.addTable(TablesEnum.TWS_CONTRACTS, new TwsContractsTable(this));
        tablesHandler.addTable(TablesEnum.DAY, new StockDayTable(this));
        tablesHandler.addTable(TablesEnum.STATUS, new StockStatusTable(this));
        tablesHandler.addTable(TablesEnum.SUM, new StockSumTable(this));
        tablesHandler.addTable(TablesEnum.ARRAYS, new ArraysTable(this));
        tablesHandler.addTable(TablesEnum.BOUNDS, new MyBoundsTable(this, "bounds"));
    }

    @Override
    public void initOptionsHandler() {

        // Month
        MyContract monthContract = getTwsHandler( ).getMyContract( TwsContractsEnum.OPT_QUARTER );
        StockOptions monthOptions = new StockOptions( monthContract.getMyId( ), this, OptionsEnum.QUARTER, TwsContractsEnum.OPT_QUARTER );

        // Quarter
        MyContract quarterContract = getTwsHandler( ).getMyContract( TwsContractsEnum.OPT_QUARTER_FAR );
        StockOptions quarterOptions = new StockOptions( quarterContract.getMyId( ), this, OptionsEnum.QUARTER_FAR, TwsContractsEnum.OPT_QUARTER_FAR );

        OptionsHandler optionsHandler = new OptionsHandler( this );
        optionsHandler.addOptions( monthOptions );
        optionsHandler.addOptions( quarterOptions );
        optionsHandler.setMainOptions( monthOptions );

        setOptionsHandler( optionsHandler );

        LogicService logicService = new LogicService(this, quarterOptions );

    }

    @Override
    public void setIndex( double index ) {
        if ( this.index == 0 ) {
            getOptionsHandler( ).initOptions( index );

            // Request options tws
            if ( getApi( ) == ApiEnum.TWS ) {
                getTwsHandler( ).requestOptions( getOptionsHandler( ).getOptionsList( ) );
            }
        }
        this.index = index;
    }

}
