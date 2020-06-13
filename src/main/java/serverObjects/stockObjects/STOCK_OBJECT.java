package serverObjects.stockObjects;

import dataBase.mySql.TablesHandler;
import dataBase.mySql.myBaseTables.MyBoundsTable;
import dataBase.mySql.mySqlComps.TablesEnum;
import dataBase.mySql.myTables.TwsContractsTable;
import dataBase.mySql.myTables.stock.StockArraysTable;
import dataBase.mySql.myTables.stock.StockDayTable;
import dataBase.mySql.myTables.stock.StockStatusTable;
import dataBase.mySql.myTables.stock.StockSumTable;
import exp.ExpEnum;
import exp.ExpMonth;
import exp.ExpWeek;
import logic.LogicService;
import myJson.MyJson;
import options.JsonStrings;
import exp.Exps;
import options.optionsCalcs.StockOptionsCalc;
import roll.Roll;
import roll.RollEnum;
import roll.RollHandler;
import roll.RollPriceEnum;
import serverObjects.ApiEnum;
import serverObjects.BASE_CLIENT_OBJECT;
import java.time.LocalTime;

public abstract class STOCK_OBJECT extends BASE_CLIENT_OBJECT {

    public STOCK_OBJECT() {
        super( );
        setIndexStartTime( LocalTime.of( 16, 30, 0 ) );
        setIndexEndTime( LocalTime.of( 23, 0, 0 ) );
        setFutureEndTime( LocalTime.of( 23, 15, 0 ) );
        initTablesHandler( );
        setLogicService( new LogicService( this, ExpEnum.MONTH ) );
        roll( );
    }

    protected void roll() {
        rollHandler = new RollHandler( this );

        Roll weekMonth = new Roll( this, ExpEnum.WEEK, ExpEnum.MONTH, RollPriceEnum.CONTRACT );
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
    public void initExpHandler() {

        // Week
        ExpWeek expWeek = new ExpWeek( this, ExpEnum.WEEK, new StockOptionsCalc( this, ExpEnum.WEEK ) );

        // Month
        ExpMonth expMonth = new ExpMonth( this, ExpEnum.MONTH, new StockOptionsCalc( this, ExpEnum.MONTH ) );

        // Exp handler
        Exps exps = new Exps( this );
        exps.addExp( expWeek, ExpEnum.WEEK );
        exps.addExp( expMonth, ExpEnum.MONTH );
        exps.setMainExp( expMonth );

        setExps( exps );
    }

    @Override
    public void setIndex( double index ) {
        if ( this.index == 0 ) {
            getExps( ).initOptions( index );

            // Request options tws
            if ( getApi( ) == ApiEnum.TWS ) {
                getTwsHandler( ).requestOptions( getExps( ).getExpList( ) );
            }
        }
        this.index = index;
    }


    @Override
    public MyJson getAsJson() {
        MyJson json = new MyJson( );
        json.put( JsonStrings.ind, getIndex( ) );
        json.put( JsonStrings.indBid, getIndexBid( ) );
        json.put( JsonStrings.indAsk, getIndexAsk( ) );
        json.put( JsonStrings.indBidAskCounter, getIndexBidAskCounter( ) );
        json.put( JsonStrings.indUp, getIndexUp() );
        json.put( JsonStrings.indDown, getIndexDown() );
        json.put( JsonStrings.conUp, getConUp() );
        json.put( JsonStrings.conDown, getConDown() );
        json.put( JsonStrings.open, getOpen( ) );
        json.put( JsonStrings.high, getHigh( ) );
        json.put( JsonStrings.low, getLow( ) );
        json.put( JsonStrings.base, getBase( ) );
        json.put( JsonStrings.roll, getRollHandler().getRoll( RollEnum.WEEK_MONTH ).getAsJson());
        json.put( JsonStrings.week, getExps( ).getExp( ExpEnum.WEEK ).getAsJson( ) );
        json.put( JsonStrings.month, getExps( ).getExp( ExpEnum.MONTH ).getAsJson( ) );
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

    @Override
    public String toString() {
        return "BASE_CLIENT_OBJECT{" +
                ", optionsHandler=" + exps.toString( ) +
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
