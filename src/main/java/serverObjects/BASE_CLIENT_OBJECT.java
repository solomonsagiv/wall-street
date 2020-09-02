package serverObjects;

import DDE.DDECells;
import api.Downloader;
import api.Manifest;
import api.tws.ITwsRequester;
import api.tws.TwsHandler;
import arik.Arik;
import charts.myChart.MyTimeSeries;
import dataBase.DataBaseHandler;
import dataBase.mySql.MySqlService;
import dataBase.mySql.TablesHandler;
import dataBase.mySql.mySqlComps.TablesEnum;
import exp.Exps;
import lists.ListsService;
import locals.IJson;
import locals.L;
import locals.LocalHandler;
import logic.LogicService;
import myJson.MyJson;
import options.OptionsDataHandler;
import roll.RollEnum;
import roll.RollHandler;
import service.MyServiceHandler;
import threads.MyThread;

import javax.swing.table.DefaultTableModel;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class BASE_CLIENT_OBJECT implements IBaseClient, IJson {

    public static final int PRE = 0;
    public static final int CURRENT = 1;

    // Options
    protected Exps exps;
    protected TwsHandler twsHandler;
    protected ITwsRequester iTwsRequester;
    protected DDECells ddeCells;
    protected double strikeMargin = 0;

    protected double indBidMarginCounter = 0;
    protected double indAskMarginCounter = 0;

    // Roll
    protected RollHandler rollHandler;

    // Options handler
    protected OptionsDataHandler optionsDataHandler;

    // TablesHandler
    protected TablesHandler tablesHandler;
    protected LogicService logicService;

    // Basic
    protected double index = 0;

    // Table
    DefaultTableModel model = new DefaultTableModel( );
    DataBaseHandler dataBaseHandler;

    // Services
    ListsService listsService;
    MySqlService mySqlService;
    MyTimeSeries indexSeries;
    MyTimeSeries indexBidSeries;
    MyTimeSeries indexAskSeries;
    MyTimeSeries indexBidAskCounterSeries;
    private double startStrike;
    private double endStrike;
    private boolean loadFromDb = false;
    private boolean dbRunning = false;
    private LocalTime indexStartTime;
    private LocalTime indexEndTime;
    private LocalTime futureEndTime;

    // Base id
    private int baseId;

    // Position
    private ArrayList< MyThread > threads = new ArrayList<>( );
    private HashMap< String, Integer > ids = new HashMap<>( );
    private boolean started = false;

    // Lists map
    private String name = null;

    // DB
    private int dbId = 0;

    // MyService
    private MyServiceHandler myServiceHandler = new MyServiceHandler( this );
    private double indexBid = 0;
    private double indexAsk = 0;
    private double open = 0;
    private double high = 0;
    private double low = 0;
    private double base = 0;
    private double indexBidAskMargin = 0;
    private int indexBidAskCounter = 0;
    private int indexBidAskCounter2 = 0;

    // Races
    private double optimiPesimiMargin = 0;
    private int conUp = 0;
    private int conDown = 0;
    private int indexUp = 0;
    private int indexDown = 0;
    private boolean conUpChanged = false;
    private boolean conDownChanged = false;
    private boolean indUpChanged = false;
    private boolean indDownChanged = false;
    private double indexAskForCheck = 0;
    private double indexBidForCheck = 0;

    public BASE_CLIENT_OBJECT() {
        try {
            LocalHandler.clients.add( this );

            // Call subClasses abstract functions
            initBaseId( );
            initDDECells( );
            initSeries( );

            // MyServices
            listsService = new ListsService( this );
            mySqlService = new MySqlService( this );
            twsHandler = new TwsHandler( );
            dataBaseHandler = new DataBaseHandler( this );

        } catch ( Exception e ) {
            e.printStackTrace( );
        }
    }

    // Start all
    public void startAll() {
        // To start
        if ( isLoadFromDb( ) ) {
            myServiceHandler.getHandler( ).start( );
            openChartsOnStart( );
            setStarted( true );
        }
    }

    // Start all
    public void closeAll() {
        getMyServiceHandler( ).getHandler( ).close( );
        for ( MyThread myThread : getThreads( ) ) {
            myThread.getHandler( ).close( );
        }
        setStarted( false );
    }

    // ---------- basic functions ---------- //
    @Override
    public void initSeries() {
        indexSeries = new MyTimeSeries( "Index", this ) {
            @Override
            public double getData() {
                return client.getIndex( );
            }
        };
        indexBidSeries = new MyTimeSeries( "Index bid", this ) {
            @Override
            public double getData() {
                return client.getIndexBid( );
            }
        };
        indexAskSeries = new MyTimeSeries( "Index ask", this ) {
            @Override
            public double getData() {
                return client.getIndexAsk( );
            }
        };
        indexBidAskCounterSeries = new MyTimeSeries( "IndBidAskCounter", this ) {
            @Override
            public double getData() {
                return client.getIndexBidAskCounter( );
            }
        };
    }

    public void fullExport() {

        boolean sumLine = false, status = false, arrays = false;

        try {
            getTablesHandler( ).getTable( TablesEnum.SUM ).insert( );
            sumLine = true;
        } catch ( Exception e ) {
            e.printStackTrace( );
        }

        try {
            getTablesHandler( ).getTable( TablesEnum.STATUS ).reset( );
            status = true;
        } catch ( Exception e ) {
            e.printStackTrace( );
        }

        try {
            getTablesHandler( ).getTable( TablesEnum.ARRAYS ).reset( );
            arrays = true;
        } catch ( Exception e ) {
            e.printStackTrace( );
        }


        String text = getName( ).toUpperCase( ) + "\n" +
                "Export line: " + sumLine + "\n" +
                "Reset status: " + status + "\n" +
                "Reset array: " + arrays + "\n";

        Arik.getInstance( ).sendMessage( Arik.sagivID, text, null );
    }

    // ---------- Getters and Setters ---------- //

    public String str( Object o ) {
        return String.valueOf( o );
    }

    public double floor( double d, int zeros ) {
        return Math.floor( d * zeros ) / zeros;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted( boolean started ) {
        this.started = started;
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

    public double getRacesMargin() {
        return index * .0001;
    }

    public DefaultTableModel getModel() {
        return model;
    }

    public void setModel( DefaultTableModel model ) {
        this.model = model;
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

    public String getArikSumLine() throws UnknownHostException {
        String text = "";
        text += "***** " + getName( ).toUpperCase( ) + " *****" + "\n";
        text += "Date: " + LocalDate.now( ).minusDays( 1 ) + "\n";
        text += "Open: " + open + "\n";
        text += "High: " + high + "\n";
        text += "Low: " + low + "\n";
        text += "Close: " + index + "\n";
        text += "OP avg: " + L.format100( getExps( ).getMainExp( ).getOpAvgFut( ) ) + "\n";
        text += "Ind bidAskCounter: " + getIndexBidAskCounter( ) + "\n";
        try {
            text += "Roll: " + L.floor( getRollHandler( ).getRoll( RollEnum.E1_E2 ).getAvg( ), 100 ) + "\n";
        } catch ( Exception e ) {
            e.printStackTrace( );
        }
        return text;
    }

    public boolean isLoadFromDb() {

        if ( loadFromDb ) {
            return true;
        }

        if ( !Manifest.DB ) {
            setLoadFromDb( true );
            return true;
        }

        TablesHandler th = getTablesHandler( );
        return th.getTable( TablesEnum.STATUS ).isLoad( ) && th.getTable( TablesEnum.ARRAYS ).isLoad( ) && th.getTable( TablesEnum.TWS_CONTRACTS ).isLoad( );
    }

    public void setLoadFromDb( boolean loadFromDb ) {
        this.loadFromDb = loadFromDb;
    }

    public int getConUp() {
        return conUp;
    }

    public void setConUp( int conUp ) {
        this.conUp = conUp;
    }

    public void conUpPlus() {
        conUp++;
        setConUpChanged( true );
    }

    public void conDownPlus() {
        conDown++;
        setConDownChanged( true );
    }

    public void indUpPlus() {
        indexUp++;
        setIndUpChanged( true );
    }

    public void indDownPlus() {
        indexDown++;
        setIndDownChanged( true );
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

    public int getIndexSum() {
        return indexUp - indexDown;
    }

    public int getFutSum() {
        return conUp - conDown;
    }

    public double getIndexBidAskMargin() {
        return indexBidAskMargin;
    }

    public void setIndexBidAskMargin( double indexBidAskMargin ) {
        this.indexBidAskMargin = indexBidAskMargin;
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

    public abstract double getTheoAvgMargin();

    public MyServiceHandler getMyServiceHandler() {
        return myServiceHandler;
    }

    public double getIndex() {
        return index;
    }

    public void setIndex( double index ) {
        this.index = index;
    }

    public double getIndexBid() {
        return indexBid;
    }

    public void setIndexBid( double indexBid ) {

        if ( indexBid > this.indexBid ) {
            indexBidAskCounter2++;
        }

        // If increment state
        if ( indexBid > this.indexBid && indexAskForCheck == this.indexAsk ) {
            indexBidAskCounter++;
        }
        this.indexBid = indexBid;

        // Ask for bid change state
        indexBidForCheck = indexBid;
        indexAskForCheck = this.indexAsk;

    }


    public double getIndBidMarginCounter() {
        return indBidMarginCounter;
    }

    public double getIndAskMarginCounter() {
        return indAskMarginCounter;
    }

    public double getIndexAsk() {
        return indexAsk;
    }

    public void setIndexAsk( double indexAsk ) {

        if ( indexAsk < this.indexAsk ) {
            indexBidAskCounter2--;
        }

        // If increment state
        if ( indexAsk < this.indexAsk && indexBidForCheck == indexBid ) {
            indexBidAskCounter--;
        }
        this.indexAsk = indexAsk;

        // Handle state
        indexAskForCheck = indexAsk;
        indexBidForCheck = indexBid;

    }

    public double getOpen() {
        return open;
    }

    public void setOpen( double open ) {
        this.open = open;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh( double high ) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow( double low ) {
        this.low = low;
    }

    public double getBase() {
        return base;
    }

    public void setBase( double base ) {
        this.base = base;
    }

    public int getIndexSumRaces() {
        return indexUp - indexDown;
    }

    public double getStrikeMargin() {
        if ( strikeMargin == 0 ) throw new NullPointerException( getName( ) + " Strike margin not set" );
        return strikeMargin;
    }

    public void setStrikeMargin( double strikeMargin ) {
        this.strikeMargin = strikeMargin;
    }

    public ListsService getListsService() {
        return listsService;
    }

    public MySqlService getMySqlService() {
        return mySqlService;
    }

    public int getIndexBidAskCounter2() {
        return indexBidAskCounter2;
    }

    public void setIndexBidAskCounter2( int indexBidAskCounter2 ) {
        this.indexBidAskCounter2 = indexBidAskCounter2;
    }

    public Exps getExps() {
        if ( exps == null ) {
            initExpHandler( );
        }
        return exps;
    }

    public void setExps( Exps exps ) {
        this.exps = exps;
    }

    public LocalTime getIndexStartTime() {
        return indexStartTime;
    }

    public void setIndexStartTime( LocalTime indexStartTime ) {
        this.indexStartTime = indexStartTime;
    }

    public LocalTime getIndexEndTime() {
        return indexEndTime;
    }

    public void setIndexEndTime( LocalTime indexEndTime ) {
        this.indexEndTime = indexEndTime;
    }

    public LocalTime getFutureEndTime() {
        return futureEndTime;
    }

    public void setFutureEndTime( LocalTime futureEndTime ) {
        this.futureEndTime = futureEndTime;
    }

    public boolean isConUpChanged() {
        return conUpChanged;
    }

    public void setConUpChanged( boolean conUpChanged ) {
        this.conUpChanged = conUpChanged;
    }

    public boolean isConDownChanged() {
        return conDownChanged;
    }

    public void setConDownChanged( boolean conDownChanged ) {
        this.conDownChanged = conDownChanged;
    }

    public boolean isIndUpChanged() {
        return indUpChanged;
    }

    public void setIndUpChanged( boolean indUpChanged ) {
        this.indUpChanged = indUpChanged;
    }

    public boolean isIndDownChanged() {
        return indDownChanged;
    }

    public void setIndDownChanged( boolean indDownChanged ) {
        this.indDownChanged = indDownChanged;
    }

    public DDECells getDdeCells() {
        return ddeCells;
    }

    public void setDdeCells( DDECells ddeCells ) {
        this.ddeCells = ddeCells;
    }

    public TwsHandler getTwsHandler() {
        return twsHandler;
    }

    public void setTwsHandler( TwsHandler twsHandler ) {
        this.twsHandler = twsHandler;
    }

    public TablesHandler getTablesHandler() {
        if ( tablesHandler == null ) throw new NullPointerException( getName( ) + " Table handler didn't set" );
        return tablesHandler;
    }

    public void setTablesHandler( TablesHandler tablesHandler ) {
        this.tablesHandler = tablesHandler;
    }

    public int getIndexBidAskCounter() {
        return indexBidAskCounter;
    }

    public void setIndexBidAskCounter( int indexBidAskCounter ) {
        this.indexBidAskCounter = indexBidAskCounter;
    }

    public RollHandler getRollHandler() {
        if ( rollHandler == null ) throw new NullPointerException( getName( ) + " Roll inn't set" );
        return rollHandler;
    }

    public void setRollHandler( RollHandler rollHandler ) {
        this.rollHandler = rollHandler;
    }

    public ITwsRequester getiTwsRequester() {
        if ( iTwsRequester == null ) throw new NullPointerException( "Tws requester not set " );
        return iTwsRequester;
    }

    public void setiTwsRequester( ITwsRequester iTwsRequester ) {
        this.iTwsRequester = iTwsRequester;
        Downloader.getInstance( ).addRequester( iTwsRequester );
    }

    public MyTimeSeries getIndexSeries() {
        return indexSeries;
    }

    public MyTimeSeries getIndexBidAskCounterSeries() {
        return indexBidAskCounterSeries;
    }

    public MyTimeSeries getIndexAskSeries() {
        return indexAskSeries;
    }

    public MyTimeSeries getIndexBidSeries() {
        return indexBidSeries;
    }

    public LogicService getLogicService() {
        if ( logicService == null ) throw new NullPointerException( getName( ) + " Logic not set" );
        return logicService;
    }

    public void setLogicService( LogicService logicService ) {
        this.logicService = logicService;
    }

    @Override
    public MyJson getAsJson() {
        return null;
    }

    public DataBaseHandler getDataBaseHandler() {
        return dataBaseHandler;
    }

    @Override
    public void openChartsOnStart() {
    }

    @Override
    public String toString() {
        return "BASE_CLIENT_OBJECT{" +
                ", optionsHandler=" + exps.toString( ) +
                ", startOfIndexTrading=" + getIndexStartTime( ) +
                ", endOfIndexTrading=" + getIndexEndTime( ) +
                ", endFutureTrading=" + getFutureEndTime( ) +
                ", loadFromDb=" + loadFromDb +
                ", dbRunning=" + dbRunning +
                ", ids=" + ids +
                ", started=" + started +
                ", dbId=" + dbId +
                ", index=" + index +
                ", indexBidAskCounter=" + indexBidAskCounter +
                ", indexBid=" + indexBid +
                ", indexAsk=" + indexAsk +
                ", open=" + open +
                ", high=" + high +
                ", low=" + low +
                ", base=" + base +
                ", indexBidAskMargin=" + indexBidAskMargin +
                ", listsService=" + listsService +
                ", mySqlService=" + mySqlService +
                ", racesMargin=" + getRacesMargin( ) +
                ", optimiPesimiMargin=" + optimiPesimiMargin +
                ", conUp=" + conUp +
                ", conDown=" + conDown +
                ", indexUp=" + indexUp +
                ", indexDown=" + indexDown +
                ", indexList=" + indexSeries.getItemCount( ) +
                '}';
    }


}
