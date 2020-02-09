package serverObjects;

import api.Manifest;
import api.tws.TwsRequestHandler;
import arik.Arik;
import arik.locals.Emojis;
import backGround.BackRunner;
import dataBase.DB;
import dataBase.HBsession;
import excutor.MyExecutor;
import gui.FuturePanel;
import gui.FuturePanelLine;
import lists.MyDoubleList;
import lists.MyList;
import lists.RegularListUpdater;
import locals.L;
import locals.LocalHandler;
import locals.MyObjects;
import logic.Logic;
import options.OptionsDataHandler;
import options.OptionsHandler;
import serverObjects.indexObjects.SpxCLIENTObject;
import shlomi.positions.Positions;
import shlomi.Shlomi;
import tables.Tables;
import tables.TablesHandler;
import tables.daily.SpxTable;
import threads.MyThread;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public abstract class BASE_CLIENT_OBJECT implements IDataBase {

    // Table
    JTable racesTable;
    DefaultTableModel model = new DefaultTableModel( );
    // Options
    OptionsHandler optionsHandler;
    private double startStrike;
    private double endStrike;
    private TwsData twsData;
    private LocalTime startOfIndexTrading;
    private LocalTime endOfIndexTrading;
    private LocalTime endFutureTrading;
    private String[] stocksNames;
    private boolean loadFromDb = false;
    private boolean dbRunning = false;
    private TwsRequestHandler twsRequestHandler;
    // Base id
    private int baseId;
    // Position
    private Positions positions;
    private ArrayList< MyThread > threads = new ArrayList<>( );
    private HashMap< String, Integer > ids = new HashMap<>( );
    private boolean started = false;
    private boolean loadStatusFromHB = false;
    private boolean loadArraysFromHB = false;

    // Executor
    MyExecutor myExecutor;

    // Lists map
    private String name = null;
    private BackRunner backRunner;
    private List< MyList > lists;

    // ObjectsList
    private ArrayList< MyObjects.MyBaseObject > myObjects;

    // DB
    private int dbId = 0;
    private DB db;
    private Tables tables;
    private TablesHandler tablesHandler;
    private HBsession hBsession;

    // Races
    private Logic logic;

    // Trading
    private Shlomi shlomi;

    // Options handler
    private OptionsDataHandler optionsDataHandler;

    // Panel
    private FuturePanel futurePanel;
    private FuturePanelLine panelLine;

    // OpMove
    private double equalMovePlag = 0;

    // List updater
    private RegularListUpdater regularListUpdater;

    // Basic
    private double dbContract = 0;
    private MyObjects.MySimpleDouble index;
    private MyObjects.MySimpleDouble indexBid;
    private MyObjects.MySimpleDouble indexAsk;
    private MyObjects.MySimpleDouble future;
    private MyObjects.MySimpleDouble futureBid;
    private MyObjects.MySimpleDouble futureAsk;
    private MyObjects.MySimpleDouble open;
    private MyObjects.MySimpleDouble high;
    private MyObjects.MySimpleDouble low;
    private MyObjects.MySimpleDouble base;
    private MyObjects.MySimpleInteger futureBidAskCounter;
    private MyObjects.MySimpleDouble indexSumRaces;
    private double indexBidAskMargin = 0;

    MyList indexList;
    MyList indexBidList;
    MyList indexAskList;
    MyList indexRacesList;

    // Races
    private double racesMargin = 0;
    private double optimiPesimiMargin = 0;
    private int conUp = 0;
    private int conDown = 0;
    private int indexUp = 0;
    private int indexDown = 0;
    private int optimiPesimiCount = 0;

    public BASE_CLIENT_OBJECT() {

        initMyParameters( );

        LocalHandler.clients.add( this );
        myObjects = new ArrayList<>( );
        myExecutor = new MyExecutor( this );
        lists = new ArrayList<>( );

        // Call subClasses abstract functions
        initMyLists( );
        initIds( );
        initTwsData( );
        initOptions( );
        initName( );
        initRacesMargin( );
        initStrikeMargin( );
        initStartOfIndexTrading( );
        initEndOfIndexTrading( );
        initEndOfFutureTrading( );
        initLogic( );
        initDbId( );
        initTables( );
        initStrikeMarginForContract( );
        initTablesHandlers( );

    }

    public void initMyLists() {

        indexList = new MyDoubleList( this, getIndex( ), "Index" );
        indexBidList = new MyDoubleList( this, getIndexBid( ), "Indexbid" );
        indexAskList = new MyDoubleList( this, getIndexAsk( ), "IndexAsk");
        indexRacesList = new MyDoubleList( this, getIndexSumRaces( ), "IndexRaces" );

    }

    void initMyParameters() {
        index = new MyObjects.MySimpleDouble( );
        indexBid = new MyObjects.MySimpleDouble( );
        indexAsk = new MyObjects.MySimpleDouble( );
        future = new MyObjects.MySimpleDouble( );
        futureBid = new MyObjects.MySimpleDouble( );
        futureAsk = new MyObjects.MySimpleDouble( );
        open = new MyObjects.MySimpleDouble( );
        high = new MyObjects.MySimpleDouble( );
        low = new MyObjects.MySimpleDouble( );
        base = new MyObjects.MySimpleDouble( );
        futureBidAskCounter = new MyObjects.MySimpleInteger( );
        indexSumRaces = new MyObjects.MySimpleDouble( ) {
            @Override
            public double getVal() {
                return getIndexUp( ) - getIndexDown( );
            }
        };
    }

    ;

    public String getThreadsStatus() {

        String text = "";

        for ( MyThread thread : threads ) {

            text += thread.getName( ) + ": " + thread.isRun( ) + " \n";

        }

        return text;
    }

    // Abstract functions
    public abstract void initName();

    public abstract void initRacesMargin();

    public abstract void initStrikeMargin();

    public abstract void initStrikeMarginForContract();

    public abstract void initStartOfIndexTrading();

    public abstract void initEndOfIndexTrading();

    public abstract void initEndOfFutureTrading();

    public abstract void initIds();

    public abstract void initLogic();

    public abstract void initDbId();

    public abstract void initTwsData();

    public abstract void initTables();

    public abstract void initOptions();

    public abstract void initTablesHandlers();

    // Start all
    public void startAll() {

        if ( getOptionsHandler( ).getMainOptions( ).isGotData( ) && getOptionsHandler( ).getOptionsQuarter( ).isGotData( ) && isLoadFromDb( ) ){

                if ( Manifest.DB ) {
                    getDb( ).startAll( );
                } else {
                    setLoadStatusFromHB( true );
                    setLoadArraysFromHB( true );
                    setLoadFromDb( true );
                }

                getMyExecutor( ).getHandler( ).start( );

                getPanel( ).getUpdater( ).getHandler( ).start( );

                // Index Move
                getOptionsHandler( ).getOptionsMonth( ).getEqualMoveCalculator( ).getHandler( ).start( );
                getOptionsHandler( ).getOptionsQuarter( ).getEqualMoveCalculator( ).getHandler( ).start( );

                if ( this instanceof SpxCLIENTObject ) {
                    // OpAvg move
                    getOptionsHandler( ).getOptionsMonth( ).getOpAvgEqualMoveCalculator( ).getHandler( ).start( );
                }

                getLogic( ).getLogicRunner( ).getHandler( ).start( );
                getRegularListUpdater( ).getHandler( ).start( );

                setStarted( true );
            }
        }

        public LocalDate convertStringToDate ( String dateString ){

            if ( dateString.length( ) == 8 ) {

                String year = dateString.substring( 0, 4 );
                String month = dateString.substring( 4, 6 );
                String day = dateString.substring( 6, 8 );

                String fullDate = year + "-" + month + "-" + day;
                return LocalDate.parse( fullDate );
            }

            return null;

        }

        // Start all
        public void closeAll ( ) {

            for ( MyThread myThread : getThreads( ) ) {
                myThread.getHandler( ).close( );
            }

            setStarted( false );
        }


        public void fullExport ( ) {
            try {

                SpxTable.Handler arrayHandler = new SpxTable.Handler( this );
                arrayHandler.resetData( );

                getTablesHandler( ).getSumHandler( ).getHandler( ).insertLine( );
                getTablesHandler( ).getStatusHandler( ).getHandler( ).resetData( );
                getTablesHandler( ).getArrayHandler( ).getHandler( ).resetData( );

                // Notify
                Arik.getInstance( ).sendMessage( Arik.sagivID, getName( ) + " Export success " + Emojis.check_mark, null );

            } catch ( Exception e ) {
                // Notify
                Arik.getInstance( ).sendMessage( Arik.sagivID,
                        getName( ) + " Export faild " + Emojis.stop + "\n" + e.getStackTrace( ).toString( ), null );
            }

        }

        // ---------- basic functions ---------- //

        public String str ( Object o ){
            return String.valueOf( o );
        }

        public double floor ( double d, int zeros ){
            return Math.floor( d * zeros ) / zeros;
        }

        // ---------- Getters and Setters ---------- //

        public void setFuture ( double future ){
            if ( this.future.getVal( ) == 0 ) {
                getOptionsHandler( ).initOptions( future );
            }
            this.future.setVal( future );
        }

        public boolean isStarted ( ) {
            return started;
        }

        public void setStarted ( boolean started ){
            this.started = started;
        }

        public void setIndex ( double index ){
            this.index.setVal( index );
        }

        public Shlomi getShlomi ( ) {
            if ( shlomi == null ) {
                shlomi = new Shlomi( this );
            }
            return shlomi;
        }

        public void setShlomi ( Shlomi shlomi ){
            this.shlomi = shlomi;
        }

        public int getConDown ( ) {
            return conDown;
        }

        public void setConDown ( int future_down ){
            this.conDown = future_down;
        }

        public int getIndexUp ( ) {
            return indexUp;
        }

        public void setIndexUp ( int index_up ){
            this.indexUp = index_up;
        }

        public int getIndexDown ( ) {
            return indexDown;
        }

        public void setIndexDown ( int index_down ){
            this.indexDown = index_down;
        }

        public double getStartStrike ( ) {
            return startStrike;
        }

        public void setStartStrike ( double startStrike ){
            this.startStrike = startStrike;
        }

        public double getEndStrike ( ) {
            return endStrike;
        }

        public void setEndStrike ( double endStrike ){
            this.endStrike = endStrike;
        }

        public Logic getLogic ( ) {
            return logic;
        }

        public void setLogic ( Logic logic ){
            this.logic = logic;
        }

        public FuturePanel getPanel ( ) {
            if ( futurePanel == null ) {
                futurePanel = new FuturePanel( this );
            }
            return futurePanel;
        }

        public void setPanel ( FuturePanel panel ){
            this.futurePanel = panel;
        }

        public double getRacesMargin ( ) {
            return racesMargin;
        }

        public void setRacesMargin ( double racesMargin ){
            this.racesMargin = racesMargin;
        }

        public double getOptimiPesimiMargin ( ) {
            return optimiPesimiMargin;
        }

        public void setOptimiPesimiMargin ( double optimiPesimiMargin ){
            this.optimiPesimiMargin = optimiPesimiMargin;
        }

        public JTable getRacesTable ( ) {
            return racesTable;
        }

        public void setRacesTable ( JTable racesTable ){
            this.racesTable = racesTable;
        }

        public DefaultTableModel getModel ( ) {
            return model;
        }

        public void setModel ( DefaultTableModel model ){
            this.model = model;
        }

        public LocalTime getStartOfIndexTrading ( ) {
            return startOfIndexTrading;
        }

        public void setStartOfIndexTrading ( LocalTime startOfIndexTrading ){
            this.startOfIndexTrading = startOfIndexTrading;
        }

        public LocalTime getEndOfIndexTrading ( ) {
            return endOfIndexTrading;
        }

        public void setEndOfIndexTrading ( LocalTime endOfIndexTrading ){
            this.endOfIndexTrading = endOfIndexTrading;
        }

        public String[] getStocksNames ( ) {
            return stocksNames;
        }

        public void setStocksNames ( String[]stocksNames ){
            this.stocksNames = stocksNames;
        }

        public FuturePanelLine getPanelLine ( ) {
            if ( panelLine == null ) {
                panelLine = new FuturePanelLine( this );
            }
            return panelLine;
        }

        public void setPanelLine ( FuturePanelLine panelLine ){
            this.panelLine = panelLine;
        }

        public DB getDb ( ) {
            if ( db == null ) {
                db = new DB( this );
            }
            return db;
        }

        public void setDb ( DB db ){
            this.db = db;
        }

        public BackRunner getBackRunner ( ) {
            if ( backRunner == null ) {
                backRunner = new BackRunner( this );
            }
            return backRunner;
        }

        public String getExportLocation ( ) {
            return "C:/Users/user/Desktop/Work/Data history/" + getName( ) + "/2019/May/";
        }

        public OptionsDataHandler getOptionsDataHandler ( ) {
            if ( optionsDataHandler == null ) {
                optionsDataHandler = new OptionsDataHandler( this );
            }
            return optionsDataHandler;
        }

        public void setOptionsDataHandler ( OptionsDataHandler optionsDataHandler ){
            this.optionsDataHandler = optionsDataHandler;
        }

        public HashMap< String, Integer > getIds ( ) {
            return ids;
        }

        public void setIds ( HashMap < String, Integer > ids ){
            this.ids = ids;
        }


        public String toStringPretty ( ) {
            String originalToString = toString( );
            String newTostring = originalToString.replaceAll( ", ", "\n" );
            return newTostring;
        }

        public String getArikSumLine ( ) {

            String text = "";
            text += "***** " + getName( ).toUpperCase( ) + " *****" + "\n";
            text += "Date: " + LocalDate.now( ).minusDays( 1 ) + "\n";
            text += "Open: " + open.getVal( ) + "\n";
            text += "High: " + high.getVal( ) + "\n";
            text += "Low: " + low.getVal( ) + "\n";
            text += "Close: " + index.getVal( ) + "\n";
            text += "OP avg: " + L.format100( getOptionsHandler( ).getMainOptions( ).getContract( ).getVal( ) ) + "\n";
            text += "Ind races: " + getIndexSum( ) + "\n";
            text += "Avg move: " + L.format100( getOptionsHandler( ).getMainOptions( ).getOpAvgEqualMoveCalculator( ).getMove( ).getVal( ) ) + "\n";
            text += "Contract counter: " + getOptionsHandler( ).getMainOptions( ).getContractBidAskCounter( ) + "\n";

            return text;
        }

        public TwsData getTwsData ( ) {

            if ( twsData == null ) {
                twsData = new TwsData( );
            }

            return twsData;
        }

        public void setTwsData ( TwsData twsData ){
            this.twsData = twsData;
        }

        public boolean isLoadFromDb ( ) {
            return loadStatusFromHB && loadArraysFromHB;
        }

        public void setLoadFromDb ( boolean loadFromDb ){
            this.loadFromDb = loadFromDb;
        }

        public void setFutureBid ( double futureBid ){

            if ( futureBid > this.futureBid.getVal( ) ) {
                futureBidAskCounter.increment( );
            }

            this.futureBid.setVal( futureBid );
        }

        public void setFutureAsk ( double futureAsk ){

            if ( futureAsk < this.futureAsk.getVal( ) ) {
                futureBidAskCounter.decrement( );
            }

            this.futureAsk.setVal( futureAsk );
        }

        public int getConUp ( ) {
            return conUp;
        }

        public void setConUp ( int conUp ){
            this.conUp = conUp;
        }

        public void conUpPlus() {
            conUp++;
        }

        public void conDownPlus() {
            conDown++;
        }

        public void indUpPlus() {
            indexUp++;
        }

        public void indDownPlus() {
            indexDown++;
        }

        public boolean isDbRunning ( ) {
            return dbRunning;
        }

        public void setDbRunning ( boolean dbRunning ){
            this.dbRunning = dbRunning;
        }

        public ArrayList< MyThread > getThreads ( ) {
            return threads;
        }

        public void setThreads ( ArrayList < MyThread > threads ) {
            this.threads = threads;
        }

        public int getDbId ( ) {
            return dbId;
        }

        public void setDbId ( int dbId ){
            this.dbId = dbId;
        }

        public Tables getTables ( ) {
            return tables;
        }

        public void setTables ( Tables tables ){
            this.tables = tables;
        }

        public TablesHandler getTablesHandler ( ) {
            return tablesHandler;
        }

        public void setTablesHandler ( TablesHandler tablesHandler ){
            this.tablesHandler = tablesHandler;
        }

        public boolean isLoadStatusFromHB ( ) {
            return loadStatusFromHB;
        }

        public void setLoadStatusFromHB ( boolean loadStatusFromHB ){
            this.loadStatusFromHB = loadStatusFromHB;
        }

        public boolean isLoadArraysFromHB ( ) {
            return loadArraysFromHB;
        }

        public void setLoadArraysFromHB ( boolean loadArraysFromHB ){
            this.loadArraysFromHB = loadArraysFromHB;
        }

        public HBsession gethBsession ( ) {
            if ( hBsession == null ) {
                hBsession = new HBsession( );
            }
            return hBsession;
        }

        public int getIndexSum ( ) {
            return indexUp - indexDown;
        }

        public int getFutSum ( ) {
            return conUp - conDown;
        }

//    public double getOpAvgFromDb() {
//        return opAvgFromDb;
//    }
//
//    public void setOpAvgFromDb( double opAvgFromDb ) {
//        this.opAvgFromDb = opAvgFromDb;
//    }

        public Positions getPositions ( ) {
            if ( positions == null ) {
                positions = new Positions( this );
            }
            return positions;
        }

        public void setPositions ( Positions positions ){
            this.positions = positions;
        }

//    public double getOpAvg15FromDb() {
//        return opAvg15FromDb;
//    }
//
//    public void setOpAvg15FromDb( double opAvg15FromDb ) {
//        this.opAvg15FromDb = opAvg15FromDb;
//    }

        public double getDbContract ( ) {
            return dbContract;
        }

        public void setDbContract ( double dbContract ){
            this.dbContract = dbContract;
        }

        public double getEqualMovePlag ( ) {
            return equalMovePlag;
        }

        public void setEqualMovePlag ( double equalMovePlag ){
            this.equalMovePlag = equalMovePlag;
        }

        public double getIndexBidAskMargin ( ) {
            return indexBidAskMargin;
        }

        public void setIndexBidAskMargin ( double indexBidAskMargin ){
            this.indexBidAskMargin = indexBidAskMargin;
        }

        public LocalTime getEndFutureTrading ( ) {
            return endFutureTrading;
        }

        public void setEndFutureTrading ( LocalTime endFutureTrading ){
            this.endFutureTrading = endFutureTrading;
        }

        public String getName ( ) {
            return name;
        }

        public void setName ( String name ){
            this.name = name;
        }

        public RegularListUpdater getRegularListUpdater ( ) {
            if ( regularListUpdater == null ) {
                regularListUpdater = new RegularListUpdater( this );
            }
            return regularListUpdater;
        }

        public int getBaseId ( ) {
            return baseId;
        }

        public void setBaseId ( int baseId ){
            this.baseId = baseId;
        }

        public TwsRequestHandler getTwsRequestHandler ( ) {
            if ( twsRequestHandler == null ) {
                twsRequestHandler = new TwsRequestHandler( this );
            }
            return twsRequestHandler;
        }

        public OptionsHandler getOptionsHandler ( ) {
            if ( optionsHandler == null ) {
                optionsHandler = new OptionsHandler( this );
            }
            return optionsHandler;
        }

        public abstract double getTheoAvgMargin ( );

        public ArrayList< MyObjects.MyBaseObject > getMyObjects ( ) {
            return myObjects;
        }

        public MyExecutor getMyExecutor ( ) {
            return myExecutor;
        }

        public MyObjects.MySimpleDouble getIndex ( ) {
            return index;
        }

        public MyObjects.MySimpleDouble getIndexBid ( ) {
            return indexBid;
        }

        public MyObjects.MySimpleDouble getIndexAsk ( ) {
            return indexAsk;
        }

        public MyObjects.MySimpleDouble getFuture ( ) {
            return future;
        }

        public MyObjects.MySimpleDouble getFutureBid ( ) {
            return futureBid;
        }

        public MyObjects.MySimpleDouble getFutureAsk ( ) {
            return futureAsk;
        }

        public MyObjects.MySimpleDouble getOpen ( ) {
            return open;
        }

        public MyObjects.MySimpleDouble getHigh ( ) {
            return high;
        }

        public MyObjects.MySimpleDouble getLow ( ) {
            return low;
        }

        public MyObjects.MySimpleDouble getBase ( ) {
            return base;
        }

        public List< MyList > getLists ( ) {
            return lists;
        }

        public MyObjects.MySimpleDouble getIndexSumRaces ( ) {
            return indexSumRaces;
        }

        public MyList getIndexList ( ) {
            return indexList;
        }

        public MyList getIndexBidList ( ) {
            return indexBidList;
        }

        public MyList getIndexAskList ( ) {
            return indexAskList;
        }

        public MyList getIndexRacesList ( ) {
            return indexRacesList;
        }

        @Override
        public String toString ( ) {
            return "BASE_CLIENT_OBJECT{" +
                    "racesTable=" + racesTable +
                    ", model=" + model +
                    ", optionsHandler=" + optionsHandler +
                    ", startStrike=" + startStrike +
                    ", endStrike=" + endStrike +
                    ", twsData=" + twsData +
                    ", startOfIndexTrading=" + startOfIndexTrading +
                    ", endOfIndexTrading=" + endOfIndexTrading +
                    ", endFutureTrading=" + endFutureTrading +
                    ", stocksNames=" + Arrays.toString( stocksNames ) +
                    ", loadFromDb=" + loadFromDb +
                    ", dbRunning=" + dbRunning +
                    ", twsRequestHandler=" + twsRequestHandler +
                    ", baseId=" + baseId +
                    ", positions=" + positions +
                    ", threads=" + threads +
                    ", ids=" + ids +
                    ", started=" + started +
                    ", loadStatusFromHB=" + loadStatusFromHB +
                    ", loadArraysFromHB=" + loadArraysFromHB +
                    ", myExecutor=" + myExecutor +
                    ", name='" + name + '\'' +
                    ", backRunner=" + backRunner +
                    ", myObjects=" + myObjects +
                    ", dbId=" + dbId +
                    ", db=" + db +
                    ", tables=" + tables +
                    ", tablesHandler=" + tablesHandler +
                    ", hBsession=" + hBsession +
                    ", logic=" + logic +
                    ", shlomi=" + shlomi +
                    ", optionsDataHandler=" + optionsDataHandler +
                    ", futurePanel=" + futurePanel +
                    ", panelLine=" + panelLine +
                    ", equalMovePlag=" + equalMovePlag +
                    ", regularListUpdater=" + regularListUpdater +
                    ", dbContract=" + dbContract +
                    ", index=" + index +
                    ", indexBid=" + indexBid +
                    ", indexAsk=" + indexAsk +
                    ", future=" + future +
                    ", futureBid=" + futureBid +
                    ", futureAsk=" + futureAsk +
                    ", open=" + open +
                    ", high=" + high +
                    ", low=" + low +
                    ", base=" + base +
                    ", futureBidAskCounter=" + futureBidAskCounter +
                    ", indexBidAskMargin=" + indexBidAskMargin +
                    ", racesMargin=" + racesMargin +
                    ", optimiPesimiMargin=" + optimiPesimiMargin +
                    ", conUp=" + conUp +
                    ", conDown=" + conDown +
                    ", indexUp=" + indexUp +
                    ", indexDown=" + indexDown +
                    ", optimiPesimiCount=" + optimiPesimiCount +
                    '}';
        }

    }
