package serverObjects;

import DDE.DDECells;
import DDE.DDECellsBloomberg;
import api.Manifest;
import charts.myChart.MyTimeSeries;
import dataBase.mySql.MySqlService;
import dataBase.mySql.dataUpdaters.DataBaseHandler_A;
import exp.E;
import exp.ExpReg;
import exp.ExpStrings;
import exp.Exps;
import lists.ListsService;
import locals.IJson;
import locals.L;
import locals.LocalHandler;
import logic.LogicService;
import myJson.MyJson;
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

    private int lastTick = -1;

    public static final int PRE = 0;
    public static final int CURRENT = 1;

    // Options
    protected Exps exps;
    protected DDECells ddeCells;
    protected double strikeMargin = 0;

    protected double indBidMarginCounter = 0;
    protected double indAskMarginCounter = 0;

    // Roll
    protected RollHandler rollHandler;

    // TablesHandler
    protected LogicService logicService;

    // Basic
    protected double index = 0;

    // Table
    DefaultTableModel model = new DefaultTableModel( );

    // Services
    ListsService listsService;
    MySqlService mySqlService;
    MyTimeSeries indexScaledSeries;
    MyTimeSeries indexSeries;
    MyTimeSeries indexBidSeries;
    MyTimeSeries indexAskSeries;
    MyTimeSeries indexBidAskCounterSeries;
    MyTimeSeries indBidAskMarginSeries;

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
            initSeries( );

            // MyServices
            listsService = new ListsService( this );
            mySqlService = new MySqlService( this, new DataBaseHandler_A( this ) );

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
        indexScaledSeries = new MyTimeSeries( "Index scaled", this, true ) {
            @Override
            public double getData() {
                return client.getIndex( );
            }
        };
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
        indBidAskMarginSeries = new MyTimeSeries( "Margin counter", this ) {
            @Override
            public double getData() throws UnknownHostException {
                return client.getBidAskMarginCounter( );
            }
        };
    }

    @Override
    public void initExpHandler() {
        // Add to
        Exps exps = new Exps( this );
        exps.addExp( new ExpReg( this, ExpStrings.day ) );
        exps.addExp( new ExpReg( this, ExpStrings.week ) );
        exps.addExp( new ExpReg( this, ExpStrings.month ) );
        exps.addExp( new E( this, ExpStrings.e1 ) );
        exps.addExp( new E( this, ExpStrings.e2 ) );
        exps.setMainExp( exps.getExp( ExpStrings.day ) );
        setExps( exps );

    }

    public double getBidAskMarginCounter() {
        return indBidMarginCounter - indAskMarginCounter;
    }

    public void fullExport() {

        // TODO

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
        return index * .00008;
    }

    public DefaultTableModel getModel() {
        return model;
    }

    public void setModel( DefaultTableModel model ) {
        this.model = model;
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

        return true;
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
        if ( index > 1 ) {
            this.index = index;
        }
    }

    public double getIndexBid() {
        return indexBid;
    }

    public void setIndexBid( double indexBid ) {
        if ( indexBid > 1 ) {
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
        if ( indexAsk > 1 ) {
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
    }

    public double getOpen() {
        return open;
    }

    public void setOpen( double open ) {
        if ( open > 1 ) {
            this.open = open;
        }
    }

    public double getHigh() {
        return high;
    }

    public void setHigh( double high ) {
        if ( high > 1 ) {
            this.high = high;
        }
    }

    public double getLow() {
        return low;
    }

    public void setLow( double low ) {
        if ( low > 1 ) {
            this.low = low;
        }
    }

    public double getBase() {
        return base;
    }

    public void setBase( double base ) {
        if ( base > 1 ) {
            this.base = base;
        }
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
        if ( ddeCells == null ) {
            ddeCells = new DDECellsBloomberg( );
        }
        return ddeCells;
    }

    public void setDdeCells( DDECells ddeCells ) {
        this.ddeCells = ddeCells;
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

    public MyTimeSeries getIndBidAskMarginSeries() {
        return indBidAskMarginSeries;
    }

    public MyTimeSeries getIndexScaledSeries() {
        return indexScaledSeries;
    }

    public LogicService getLogicService() {
        if ( logicService == null ) throw new NullPointerException( getName( ) + " Logic not set" );
        return logicService;
    }

    public static int getPRE() {
        return PRE;
    }

    public void setLogicService( LogicService logicService ) {
        this.logicService = logicService;
    }

    @Override
    public MyJson getAsJson() {
        return null;
    }

    @Override
    public void openChartsOnStart() {
    }

    public int getLastTick() {
        return lastTick;
    }

    public void setLastTick( int lastTick ) {
        this.lastTick = lastTick;
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
