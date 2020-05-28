package serverObjects.stockObjects;

import dataBase.mySql.TablesHandler;
import dataBase.mySql.myBaseTables.MyBoundsTable;
import dataBase.mySql.mySqlComps.TablesEnum;
import dataBase.mySql.myTables.TwsContractsTable;
import dataBase.mySql.myTables.stock.StockArraysTable;
import dataBase.mySql.myTables.stock.StockDayTable;
import dataBase.mySql.myTables.stock.StockStatusTable;
import dataBase.mySql.myTables.stock.StockSumTable;
import logic.LogicService;
import options.OptionsEnum;
import options.OptionsHandler;
import options.StockOptions;
import roll.Roll;
import roll.RollEnum;
import roll.RollHandler;
import roll.RollPriceEnum;
import serverObjects.ApiEnum;
import serverObjects.BASE_CLIENT_OBJECT;
import tws.MyContract;
import tws.TwsContractsEnum;

import java.time.LocalTime;

public abstract class STOCK_OBJECT extends BASE_CLIENT_OBJECT {

    public STOCK_OBJECT() {
        super( );
        setIndexStartTime( LocalTime.of( 16, 30, 0 ) );
        setIndexEndTime( LocalTime.of( 23, 0, 0 ) );
        setFutureEndTime( LocalTime.of( 23, 15, 0 ) );
        initTablesHandler( );
        setLogicService( new LogicService( this, OptionsEnum.MONTH ) );
        roll( );
    }

    protected void roll() {
        rollHandler = new RollHandler( this );

        Roll weekMonth = new Roll( this, OptionsEnum.WEEK, OptionsEnum.MONTH, RollPriceEnum.CONTRACT );
        rollHandler.addRoll( RollEnum.WEEK_MONTH, weekMonth );
    }

    public void initTablesHandler() {
        tablesHandler = new TablesHandler( );
        tablesHandler.addTable( TablesEnum.TWS_CONTRACTS, new TwsContractsTable( this ) );
        tablesHandler.addTable( TablesEnum.DAY, new StockDayTable( this ) );
        tablesHandler.addTable( TablesEnum.STATUS, new StockStatusTable( this ) );
        tablesHandler.addTable( TablesEnum.SUM, new StockSumTable( this ) );
        tablesHandler.addTable( TablesEnum.ARRAYS, new StockArraysTable( this ) );
        tablesHandler.addTable( TablesEnum.BOUNDS, new MyBoundsTable( this, "bounds" ) );
    }

    @Override
    public void initOptionsHandler() {

        // Week
        MyContract weekContract = getTwsHandler( ).getMyContract( TwsContractsEnum.OPT_WEEK );
        StockOptions weekOptions = new StockOptions( weekContract.getMyId( ), this, OptionsEnum.WEEK, TwsContractsEnum.OPT_WEEK );

        // Month
        MyContract monthContract = getTwsHandler( ).getMyContract( TwsContractsEnum.OPT_MONTH );
        StockOptions monthOptions = new StockOptions( monthContract.getMyId( ), this, OptionsEnum.MONTH, TwsContractsEnum.OPT_MONTH );

        OptionsHandler optionsHandler = new OptionsHandler( this );
        optionsHandler.addOptions( weekOptions );
        optionsHandler.addOptions( monthOptions );
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

    @Override
    public String toString() {
        return "BASE_CLIENT_OBJECT{" +
                ", optionsHandler=" + optionsHandler.toString( ) +
                ", startOfIndexTrading=" + getIndexStartTime( ) +
                ", endOfIndexTrading=" + getIndexEndTime( ) +
                ", endFutureTrading=" + getFutureEndTime( ) +
                ", loadFromDb=" + isLoadFromDb( ) +
                ", dbRunning=" + isDbRunning( ) +
                ", ids=" + getIds( ) +
                ", started=" + isStarted( ) +
                ", dbId=" + getDbId( ) +
                ", index=" + index +
                ", indexBidAskCounter=" + getIndexBidAskCounter( ) +
                ", indexBid=" + getIndexBid( ) +
                ", indexAsk=" + getIndexAsk( ) +
                ", open=" + getOpen( ) +
                ", high=" + getHigh( ) +
                ", low=" + getLow( ) +
                ", base=" + getBase( ) +
                ", indexBidAskMargin=" + getIndexBidAskMargin( ) +
                ", listsService=" + getListsService( ) +
                ", mySqlService=" + getMySqlService( ) +
                ", racesMargin=" + getRacesMargin( ) +
                ", conUp=" + getConUp( ) +
                ", conDown=" + getConDown( ) +
                ", indexUp=" + getIndexUp( ) +
                ", indexDown=" + getIndexDown( ) +
                ", indexList=" + getIndexList( ).size( ) +
                '}';
    }

}
