package serverObjects.stockObjects;

import dataBase.mySql.TablesHandler;
import dataBase.mySql.myBaseTables.MyBoundsTable;
import dataBase.mySql.mySqlComps.TablesEnum;
import dataBase.mySql.myTables.TwsContractsTable;
import dataBase.mySql.myTables.index.ArraysTable;
import dataBase.mySql.myTables.stock.StockArraysTable;
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

import java.time.LocalTime;

public abstract class STOCK_OBJECT extends BASE_CLIENT_OBJECT {

    public STOCK_OBJECT() {
        super();
        setIndexStartTime( LocalTime.of(16, 30, 0));
        setIndexEndTime(LocalTime.of(23, 0, 0));
        setFutureEndTime(LocalTime.of(23, 15, 0));
        initTablesHandler();
        setLogicService( new LogicService( this, OptionsEnum.MONTH ) );
    }

    public void initTablesHandler() {
        tablesHandler = new TablesHandler();
        tablesHandler.addTable(TablesEnum.TWS_CONTRACTS, new TwsContractsTable(this));
        tablesHandler.addTable(TablesEnum.DAY, new StockDayTable(this));
        tablesHandler.addTable(TablesEnum.STATUS, new StockStatusTable(this));
        tablesHandler.addTable(TablesEnum.SUM, new StockSumTable(this));
        tablesHandler.addTable(TablesEnum.ARRAYS, new StockArraysTable(this));
        tablesHandler.addTable(TablesEnum.BOUNDS, new MyBoundsTable(this, "bounds"));
    }

    @Override
    public void initOptionsHandler() {

        // Month
        MyContract monthContract = getTwsHandler( ).getMyContract( TwsContractsEnum.OPT_WEEK );
        StockOptions monthOptions = new StockOptions( monthContract.getMyId( ), this, OptionsEnum.WEEK, TwsContractsEnum.OPT_WEEK );

        // Quarter
        MyContract quarterContract = getTwsHandler( ).getMyContract( TwsContractsEnum.OPT_MONTH );
        StockOptions quarterOptions = new StockOptions( quarterContract.getMyId( ), this, OptionsEnum.MONTH, TwsContractsEnum.OPT_MONTH );

        OptionsHandler optionsHandler = new OptionsHandler( this );
        optionsHandler.addOptions( monthOptions );
        optionsHandler.addOptions( quarterOptions );
        optionsHandler.setMainOptions( monthOptions );

        setOptionsHandler( optionsHandler );

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
