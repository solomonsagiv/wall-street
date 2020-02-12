package serverObjects;

import api.Manifest;
import api.tws.TwsRequestHandler;
import arik.Arik;
import arik.locals.Emojis;
import backGround.BackRunner;
import dataBase.DB;
import dataBase.HBsession;
import dataBase.mySql.MySqlService;
import gui.FuturePanel;
import lists.ListsService;
import locals.L;
import locals.LocalHandler;
import options.OptionsDataHandler;
import options.OptionsHandler;
import service.MyBaseService;
import service.MyServiceHandler;
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

public abstract class BASE_CLIENT_OBJECT implements IDataBase, IBaseClient {

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
    private ArrayList< MyThread > threads = new ArrayList<>( );
    private HashMap< String, Integer > ids = new HashMap<>( );
    private boolean started = false;
    private boolean loadStatusFromHB = false;
    private boolean loadArraysFromHB = false;

    // Lists map
    private String name = null;
    private BackRunner backRunner;

    // DB
    private int dbId = 0;
    private DB db;
    private Tables tables;
    private TablesHandler tablesHandler;
    private HBsession hBsession;

    // Options handler
    private OptionsDataHandler optionsDataHandler;

    // Panel
    private FuturePanel futurePanel;

    // MyService
    private MyServiceHandler myServiceHandler = new MyServiceHandler( this );

    // OpMove
    private double equalMovePlag = 0;

    // Basic
    private double dbContract = 0;
    private double index = 0;
    private double indexBid = 0;
    private double indexAsk = 0;
    private double future = 0;
    private double futureBid = 0;
    private double futureAsk = 0;
    private double open = 0;
    private double high = 0;
    private double low = 0;
    private double base = 0;
    private int futureBidAskCounter = 0;
    private double indexBidAskMargin = 0;

    // Services
    ListsService listsService;
    MySqlService mySqlService;

    // Races
    private double racesMargin = 0;
    private double optimiPesimiMargin = 0;
    private int conUp = 0;
    private int conDown = 0;
    private int indexUp = 0;
    private int indexDown = 0;
    private int optimiPesimiCount = 0;

    List indexList = new ArrayList< Double >( );
    List indexBidList = new ArrayList< Double >( );
    List indexAskList = new ArrayList< Double >( );
    List indexRacesList = new ArrayList< Double >( );

    public BASE_CLIENT_OBJECT() {

        initTwsData( );
        LocalHandler.clients.add( this );

        // Call subClasses abstract functions
        initIds( );
        initName( );
        initRacesMargin( );
        initStartOfIndexTrading( );
        initEndOfIndexTrading( );
        initEndOfFutureTrading( );
        initDbId( );
        initTables( );
        initTablesHandlers( );

        // MyServices
        listsService = new ListsService( this );
        mySqlService = new MySqlService( this );

    }

    // Start all
    public void startAll() {

        if ( Manifest.DB ) {
            getDb( ).startAll( );
        } else {
            setLoadStatusFromHB( true );
            setLoadArraysFromHB( true );
            setLoadFromDb( true );
        }

        if ( getOptionsHandler( ).getMainOptions( ).isGotData( ) && getOptionsHandler( ).getOptionsQuarter( ).isGotData( ) && isLoadFromDb( ) ) {

            myServiceHandler.getHandler( ).start( );

            getPanel( ).getUpdater( ).getHandler( ).start( );

//            // Index Move
//            getOptionsHandler().getOptionsMonth().getEqualMoveCalculator().getHandler().start();
//            getOptionsHandler().getOptionsQuarter().getEqualMoveCalculator().getHandler().start();
//
//            if (this instanceof SpxCLIENTObject) {
//                // OpAvg move
//                getOptionsHandler().getOptionsMonth().getOpAvgEqualMoveCalculator().getHandler().start();
//            }
//
//            getLogic().getLogicRunner().getHandler().start();
//            getRegularListUpdater().getHandler().start();

            setStarted( true );
        }
    }

    public LocalDate convertStringToDate( String dateString ) {

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
    public void closeAll() {
        getMyServiceHandler( ).getHandler( ).close( );
        for ( MyThread myThread : getThreads( ) ) {
            myThread.getHandler( ).close( );
        }
        setStarted( false );
    }

    public void fullExport() {
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

    public String str( Object o ) {
        return String.valueOf( o );
    }

    public double floor( double d, int zeros ) {
        return Math.floor( d * zeros ) / zeros;
    }

    // ---------- Getters and Setters ---------- //
    public void setFuture( double future ) {
        if ( this.future == 0 ) {
            this.future = future;
            getOptionsHandler( ).initOptions( future );
        }
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted( boolean started ) {
        this.started = started;
    }

    public void setIndex( double index ) {
        this.index = index;
    }

    public int getConDown() {
        return conDown;
    }

    public void setConDown( int future_down ) {
        this.conDown = future_down;
    }

    public int getIndexUp() {
        return indexUp;
    }

    public void setIndexUp( int index_up ) {
        this.indexUp = index_up;
    }

    public int getIndexDown() {
        return indexDown;
    }

    public void setIndexDown( int index_down ) {
        this.indexDown = index_down;
    }

    public double getStartStrike() {
        return startStrike;
    }

    public void setStartStrike( double startStrike ) {
        this.startStrike = startStrike;
    }

    public double getEndStrike() {
        return endStrike;
    }

    public void setEndStrike( double endStrike ) {
        this.endStrike = endStrike;
    }

    public FuturePanel getPanel() {
        if ( futurePanel == null ) {
            futurePanel = new FuturePanel( this );
        }
        return futurePanel;
    }

    public void setPanel( FuturePanel panel ) {
        this.futurePanel = panel;
    }

    public double getRacesMargin() {
        return racesMargin;
    }

    public void setRacesMargin( double racesMargin ) {
        this.racesMargin = racesMargin;
    }

    public DefaultTableModel getModel() {
        return model;
    }

    public void setModel( DefaultTableModel model ) {
        this.model = model;
    }

    public LocalTime getStartOfIndexTrading() {
        return startOfIndexTrading;
    }

    public void setStartOfIndexTrading( LocalTime startOfIndexTrading ) {
        this.startOfIndexTrading = startOfIndexTrading;
    }

    public LocalTime getEndOfIndexTrading() {
        return endOfIndexTrading;
    }

    public void setEndOfIndexTrading( LocalTime endOfIndexTrading ) {
        this.endOfIndexTrading = endOfIndexTrading;
    }

    public void setStocksNames( String[] stocksNames ) {
        this.stocksNames = stocksNames;
    }

    public DB getDb() {
        if ( db == null ) {
            db = new DB( this );
        }
        return db;
    }

    public void setDb( DB db ) {
        this.db = db;
    }

    public BackRunner getBackRunner() {
        if ( backRunner == null ) {
            backRunner = new BackRunner( this );
        }
        return backRunner;
    }

    public String getExportLocation() {
        return "C:/Users/user/Desktop/Work/Data history/" + getName( ) + "/2019/May/";
    }

    public OptionsDataHandler getOptionsDataHandler() {
        if ( optionsDataHandler == null ) {
            optionsDataHandler = new OptionsDataHandler( this );
        }
        return optionsDataHandler;
    }

    public HashMap< String, Integer > getIds() {
        return ids;
    }

    public void setIds( HashMap< String, Integer > ids ) {
        this.ids = ids;
    }

    public String toStringPretty() {
        String originalToString = toString( );
        String newTostring = originalToString.replaceAll( ", ", "\n" );
        return newTostring;
    }

    public String getArikSumLine() {

        String text = "";
        text += "***** " + getName( ).toUpperCase( ) + " *****" + "\n";
        text += "Date: " + LocalDate.now( ).minusDays( 1 ) + "\n";
        text += "Open: " + open + "\n";
        text += "High: " + high + "\n";
        text += "Low: " + low + "\n";
        text += "Close: " + index + "\n";
        text += "OP avg: " + L.format100( getOptionsHandler( ).getMainOptions( ).getContract( ) ) + "\n";
        text += "Ind races: " + getIndexSum( ) + "\n";
        text += "Avg move: " + L.format100( getOptionsHandler( ).getMainOptions( ).getOpAvgMoveService( ).getMove( ) ) + "\n";
        text += "Contract counter: " + getOptionsHandler( ).getMainOptions( ).getContractBidAskCounter( ) + "\n";

        return text;
    }

    public TwsData getTwsData() {

        if ( twsData == null ) {
            twsData = new TwsData( );
        }

        return twsData;
    }

    public void setTwsData( TwsData twsData ) {
        this.twsData = twsData;
    }

    public boolean isLoadFromDb() {
        return loadStatusFromHB && loadArraysFromHB;
    }

    public void setLoadFromDb( boolean loadFromDb ) {
        this.loadFromDb = loadFromDb;
    }

    public void setFutureBid( double futureBid ) {

        if ( futureBid > this.futureBid ) {
            futureBidAskCounter++;
        }

        this.futureBid = futureBid;
    }

    public void setFutureAsk( double futureAsk ) {

        if ( futureAsk < this.futureAsk ) {
            futureBidAskCounter--;
        }

        this.futureAsk = futureAsk;
    }

    public int getConUp() {
        return conUp;
    }

    public void setConUp( int conUp ) {
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

    public boolean isDbRunning() {
        return dbRunning;
    }

    public void setDbRunning( boolean dbRunning ) {
        this.dbRunning = dbRunning;
    }

    public ArrayList< MyThread > getThreads() {
        return threads;
    }

    public void setThreads( ArrayList< MyThread > threads ) {
        this.threads = threads;
    }

    public int getDbId() {
        return dbId;
    }

    public void setDbId( int dbId ) {
        this.dbId = dbId;
    }

    public Tables getTables() {
        return tables;
    }

    public void setTables( Tables tables ) {
        this.tables = tables;
    }

    public TablesHandler getTablesHandler() {
        return tablesHandler;
    }

    public void setTablesHandler( TablesHandler tablesHandler ) {
        this.tablesHandler = tablesHandler;
    }

    public void setLoadStatusFromHB( boolean loadStatusFromHB ) {
        this.loadStatusFromHB = loadStatusFromHB;
    }

    public void setLoadArraysFromHB( boolean loadArraysFromHB ) {
        this.loadArraysFromHB = loadArraysFromHB;
    }

    public HBsession gethBsession() {
        if ( hBsession == null ) {
            hBsession = new HBsession( );
        }
        return hBsession;
    }

    public int getIndexSum() {
        return indexUp - indexDown;
    }

    public int getFutSum() {
        return conUp - conDown;
    }

    public double getEqualMovePlag() {
        return equalMovePlag;
    }

    public void setEqualMovePlag( double equalMovePlag ) {
        this.equalMovePlag = equalMovePlag;
    }

    public double getIndexBidAskMargin() {
        return indexBidAskMargin;
    }

    public void setIndexBidAskMargin( double indexBidAskMargin ) {
        this.indexBidAskMargin = indexBidAskMargin;
    }

    public LocalTime getEndFutureTrading() {
        return endFutureTrading;
    }

    public void setEndFutureTrading( LocalTime endFutureTrading ) {
        this.endFutureTrading = endFutureTrading;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public int getBaseId() {
        return baseId;
    }

    public void setBaseId( int baseId ) {
        this.baseId = baseId;
    }

    public TwsRequestHandler getTwsRequestHandler() {
        if ( twsRequestHandler == null ) {
            twsRequestHandler = new TwsRequestHandler( this );
        }
        return twsRequestHandler;
    }

    public OptionsHandler getOptionsHandler() {
        if ( optionsHandler == null ) {
            optionsHandler = new OptionsHandler( this );
        }
        return optionsHandler;
    }

    public abstract double getTheoAvgMargin();

    public MyServiceHandler getMyServiceHandler() {
        return myServiceHandler;
    }

    public JTable getRacesTable() {
        return racesTable;
    }

    public double getIndex() {
        return index;
    }

    public double getIndexBid() {
        return indexBid;
    }

    public double getIndexAsk() {
        return indexAsk;
    }

    public double getFuture() {
        return future;
    }

    public double getFutureBid() {
        return futureBid;
    }

    public double getFutureAsk() {
        return futureAsk;
    }

    public double getOpen() {
        return open;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getBase() {
        return base;
    }

    public int getFutureBidAskCounter() {
        return futureBidAskCounter;
    }

    public int getIndexSumRaces() {
        return indexUp - indexDown;
    }


    public void setRacesTable( JTable racesTable ) {
        this.racesTable = racesTable;
    }

    public void setIndexBid( double indexBid ) {
        this.indexBid = indexBid;
    }

    public void setIndexAsk( double indexAsk ) {
        this.indexAsk = indexAsk;
    }

    public void setOpen( double open ) {
        this.open = open;
    }

    public void setHigh( double high ) {
        this.high = high;
    }

    public void setLow( double low ) {
        this.low = low;
    }

    public void setBase( double base ) {
        this.base = base;
    }

    public String[] getStocksNames() {
        return stocksNames;
    }

    public List getIndexList() {
        return indexList;
    }

    public List getIndexBidList() {
        return indexBidList;
    }

    public List getIndexAskList() {
        return indexAskList;
    }

    public List getIndexRacesList() {
        return indexRacesList;
    }

    public ListsService getListsService() {
        return listsService;
    }

    @Override
    public String toString() {
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
                ", threads=" + threads +
                ", ids=" + ids +
                ", started=" + started +
                ", loadStatusFromHB=" + loadStatusFromHB +
                ", loadArraysFromHB=" + loadArraysFromHB +
                ", name='" + name + '\'' +
                ", backRunner=" + backRunner +
                ", dbId=" + dbId +
                ", db=" + db +
                ", tables=" + tables +
                ", tablesHandler=" + tablesHandler +
                ", hBsession=" + hBsession +
                ", optionsDataHandler=" + optionsDataHandler +
                ", futurePanel=" + futurePanel +
                ", equalMovePlag=" + equalMovePlag +
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
