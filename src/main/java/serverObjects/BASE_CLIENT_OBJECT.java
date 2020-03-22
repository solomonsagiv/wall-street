package serverObjects;

import DDE.DDECells;
import api.Manifest;
import api.tws.TwsHandler;
import arik.Arik;
import arik.locals.Emojis;
import backGround.BackRunner;
import dataBase.mySql.MySqlService;
import dataBase.mySql.TablesHandler;
import dataBase.mySql.myTables.TablesEnum;
import lists.ListsService;
import locals.L;
import locals.LocalHandler;
import options.OptionsDataHandler;
import options.OptionsHandler;
import service.MyServiceHandler;
import threads.MyThread;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class BASE_CLIENT_OBJECT implements IBaseClient {

    public static final int PRE = 0;
    public static final int CURRENT = 1;

    // Table
    DefaultTableModel model = new DefaultTableModel();
    // Options
    protected OptionsHandler optionsHandler;
    private double startStrike;
    private double endStrike;
    private LocalTime startOfIndexTrading;
    private LocalTime endOfIndexTrading;
    private LocalTime endFutureTrading;
    private boolean loadFromDb = false;
    private boolean dbRunning = false;
    protected TwsHandler twsHandler;
    protected DDECells ddeCells;
    // Base id
    private int baseId;
    // Position
    private ArrayList<MyThread> threads = new ArrayList<>();
    private HashMap<String, Integer> ids = new HashMap<>();
    private boolean started = false;
    private boolean loadStatusFromHB = false;
    private boolean loadArraysFromHB = false;

    // Lists map
    private String name = null;
    private BackRunner backRunner;

    // DB
    private int dbId = 0;

    // Options handler
    protected OptionsDataHandler optionsDataHandler;

    // TablesHandler
    TablesHandler tablesHandler;

    // MyService
    private MyServiceHandler myServiceHandler = new MyServiceHandler(this);

    // OpMove
    private double equalMovePlag = 0;

    // Basic
    protected double index = 0;
    private double indexBid = 0;
    private double indexAsk = 0;
    private double futureBid = 0;
    private double futureAsk = 0;
    private double open = 0;
    private double high = 0;
    private double low = 0;
    private double base = 0;
    private int futureBidAskCounter = 0;
    private double indexBidAskMargin = 0;
    private int indexBidAskCounter = 0;

    private double[] indexBidState = new double[2];
    private double[] indexAskState = new double[2];
    private double[] futureBidState = new double[2];
    private double[] futureAskState = new double[2];

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

    private boolean conUpChanged = false;
    private boolean conDownChanged = false;
    private boolean indUpChanged = false;
    private boolean indDownChanged = false;

    List indexList = new ArrayList<Double>();
    List indexBidList = new ArrayList<Double>();
    List indexAskList = new ArrayList<Double>();
    List indexRacesList = new ArrayList<Double>();

    public BASE_CLIENT_OBJECT() {
        try {
            initIds();
            initTwsHandler();
            LocalHandler.clients.add(this);

            // Call subClasses abstract functions
            initName();
            initRacesMargin();
            initStartOfIndexTrading();
            initEndOfIndexTrading();
            initEndOfFutureTrading();
            initDbId();
            initTablesHandlers();
            initOptionsHandler();
            initDDECells();

            // MyServices
            listsService = new ListsService(this);
            mySqlService = new MySqlService(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Start all
    public void startAll() {

        if (!Manifest.DB) {
            setLoadStatusFromHB(true);
            setLoadArraysFromHB(true);
            setLoadFromDb(true);
        }

        // To start
        if (isLoadFromDb()) {
            myServiceHandler.getHandler().start();
            setStarted(true);
        }

    }

    public LocalDate convertStringToDate(String dateString) {

        if (dateString.length() == 8) {

            String year = dateString.substring(0, 4);
            String month = dateString.substring(4, 6);
            String day = dateString.substring(6, 8);

            String fullDate = year + "-" + month + "-" + day;
            return LocalDate.parse(fullDate);
        }

        return null;
    }

    // Start all
    public void closeAll() {
        getMyServiceHandler().getHandler().close();
        for (MyThread myThread : getThreads()) {
            myThread.getHandler().close();
        }
        setStarted(false);
    }

    public void fullExport() {
        try {
            getTablesHandler().getTable(TablesEnum.SUM).insert();
            getTablesHandler().getTable(TablesEnum.STATUS).reset();
            getTablesHandler().getTable(TablesEnum.ARRAYS).reset();

            // Notify
            Arik.getInstance().sendMessage(Arik.sagivID, getName() + " Export success " + Emojis.check_mark, null);

        } catch (Exception e) {
            // Notify
            Arik.getInstance().sendMessage(Arik.sagivID,
                    getName() + " Export faild " + Emojis.stop + "\n" + e.getStackTrace().toString(), null);
        }

    }

    // ---------- basic functions ---------- //

    public String str(Object o) {
        return String.valueOf(o);
    }

    public double floor(double d, int zeros) {
        return Math.floor(d * zeros) / zeros;
    }

    // ---------- Getters and Setters ---------- //

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public void setIndex(double index) {
        this.index = index;
    }

    public int getConDown() {
        return conDown;
    }

    public void setConDown(int future_down) {
        this.conDown = future_down;
    }

    public int getIndexUp() {
        return indexUp;
    }

    public void setIndexUp(int index_up) {
        this.indexUp = index_up;
    }

    public int getIndexDown() {
        return indexDown;
    }

    public void setIndexDown(int index_down) {
        this.indexDown = index_down;
    }

    public double getStartStrike() {
        return startStrike;
    }

    public void setStartStrike(double startStrike) {
        this.startStrike = startStrike;
    }

    public double getEndStrike() {
        return endStrike;
    }

    public void setEndStrike(double endStrike) {
        this.endStrike = endStrike;
    }

    public double getRacesMargin() {
        return racesMargin;
    }

    public void setRacesMargin(double racesMargin) {
        this.racesMargin = racesMargin;
    }

    public DefaultTableModel getModel() {
        return model;
    }

    public void setModel(DefaultTableModel model) {
        this.model = model;
    }

    public LocalTime getStartOfIndexTrading() {
        return startOfIndexTrading;
    }

    public void setStartOfIndexTrading(LocalTime startOfIndexTrading) {
        this.startOfIndexTrading = startOfIndexTrading;
    }

    public LocalTime getEndOfIndexTrading() {
        return endOfIndexTrading;
    }

    public void setEndOfIndexTrading(LocalTime endOfIndexTrading) {
        this.endOfIndexTrading = endOfIndexTrading;
    }

    public BackRunner getBackRunner() {
        if (backRunner == null) {
            backRunner = new BackRunner(this);
        }
        return backRunner;
    }

    public String getExportLocation() {
        return "C:/Users/user/Desktop/Work/Data history/" + getName() + "/2019/May/";
    }

    public OptionsDataHandler getOptionsDataHandler() {
        if (optionsDataHandler == null) {
            optionsDataHandler = new OptionsDataHandler(this);
        }
        return optionsDataHandler;
    }

    public HashMap<String, Integer> getIds() {
        return ids;
    }

    public void setIds(HashMap<String, Integer> ids) {
        this.ids = ids;
    }

    public String toStringPretty() {
        String originalToString = toString();
        String newTostring = originalToString.replaceAll(", ", "\n");
        return newTostring;
    }

    public String getArikSumLine() {

        String text = "";
        text += "***** " + getName().toUpperCase() + " *****" + "\n";
        text += "Date: " + LocalDate.now().minusDays(1) + "\n";
        text += "Open: " + open + "\n";
        text += "High: " + high + "\n";
        text += "Low: " + low + "\n";
        text += "Close: " + index + "\n";
        text += "OP avg: " + L.format100(getOptionsHandler().getMainOptions().getContract()) + "\n";
        text += "Ind races: " + getIndexSum() + "\n";
        text += "Contract counter: " + getOptionsHandler().getMainOptions().getContractBidAskCounter() + "\n";

        return text;
    }

    public boolean isLoadFromDb() {
        return loadStatusFromHB && loadArraysFromHB;
    }

    public void setLoadFromDb(boolean loadFromDb) {
        this.loadFromDb = loadFromDb;
    }


    private double futureAskForCheck = 0;
    public void setFutureBid(double futureBid) {

        // If increment state
        if ( futureBid > this.futureBid && futureAskForCheck == this.futureAsk ) {
            indexBidAskCounter++;
        }
        this.futureBid = futureBid;

        // Ask for bid change state
        futureAskForCheck = this.futureAsk;

    }

    private double futureBidForCheck = 0;
    public void setFutureAsk(double futureAsk) {

        // If increment state
        if ( futureAsk < this.futureAsk && futureBidForCheck == this.futureBid ) {
            indexBidAskCounter--;
        }
        this.futureAsk = futureAsk;

        // Ask for bid change state
        futureBidForCheck = this.futureBid;

    }

    public int getConUp() {
        return conUp;
    }

    public void setConUp(int conUp) {
        this.conUp = conUp;
    }

    public void conUpPlus() {
        conUp++;
        setConUpChanged(true);
    }

    public void conDownPlus() {
        conDown++;
        setConDownChanged(true);
    }

    public void indUpPlus() {
        indexUp++;
        setIndUpChanged(true);
    }

    public void indDownPlus() {
        indexDown++;
        setIndDownChanged(true);
    }

    public boolean isDbRunning() {
        return dbRunning;
    }

    public void setDbRunning(boolean dbRunning) {
        this.dbRunning = dbRunning;
    }

    public ArrayList<MyThread> getThreads() {
        return threads;
    }

    public void setThreads(ArrayList<MyThread> threads) {
        this.threads = threads;
    }

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public void setLoadStatusFromHB(boolean loadStatusFromHB) {
        this.loadStatusFromHB = loadStatusFromHB;
    }

    public void setLoadArraysFromHB(boolean loadArraysFromHB) {
        this.loadArraysFromHB = loadArraysFromHB;
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

    public void setEqualMovePlag(double equalMovePlag) {
        this.equalMovePlag = equalMovePlag;
    }

    public double getIndexBidAskMargin() {
        return indexBidAskMargin;
    }

    public void setIndexBidAskMargin(double indexBidAskMargin) {
        this.indexBidAskMargin = indexBidAskMargin;
    }

    public LocalTime getEndFutureTrading() {
        return endFutureTrading;
    }

    public void setEndFutureTrading(LocalTime endFutureTrading) {
        this.endFutureTrading = endFutureTrading;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBaseId() {
        return baseId;
    }

    public void setBaseId(int baseId) {
        this.baseId = baseId;
    }

    public abstract double getTheoAvgMargin();

    public MyServiceHandler getMyServiceHandler() {
        return myServiceHandler;
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

    private double indexAskForCheck = 0;
    public void setIndexBid(double indexBid) {

        // If increment state
        if ( indexBid > this.indexBid && indexAskForCheck == this.indexAsk ) {
            indexBidAskCounter++;
        }
        this.indexBid = indexBid;

        // Ask for bid change state
        indexBidForCheck = indexBid;
        indexAskForCheck = this.indexAsk;

    }

    private double indexBidForCheck = 0;
    public void setIndexAsk(double indexAsk) {
        // If increment state
        if ( indexAsk < this.indexAsk && indexBidForCheck == indexBid ) {
            indexBidAskCounter--;
        }
        this.indexAsk = indexAsk;

        // Handle state
        indexAskForCheck = indexAsk;
        indexBidForCheck = indexBid;

    }

    public void setOpen(double open) {
        this.open = open;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public void setBase(double base) {
        this.base = base;
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

    public MySqlService getMySqlService() {
        return mySqlService;
    }

    public void setOptionsHandler(OptionsHandler optionsHandler) {
        this.optionsHandler = optionsHandler;
    }

    public OptionsHandler getOptionsHandler() {
        if (optionsHandler == null) throw new NullPointerException();
        return optionsHandler;
    }

    public boolean isConUpChanged() {
        return conUpChanged;
    }

    public void setConUpChanged(boolean conUpChanged) {
        this.conUpChanged = conUpChanged;
    }

    public boolean isConDownChanged() {
        return conDownChanged;
    }

    public void setConDownChanged(boolean conDownChanged) {
        this.conDownChanged = conDownChanged;
    }

    public boolean isIndUpChanged() {
        return indUpChanged;
    }

    public void setIndUpChanged(boolean indUpChanged) {
        this.indUpChanged = indUpChanged;
    }

    public boolean isIndDownChanged() {
        return indDownChanged;
    }

    public void setIndDownChanged(boolean indDownChanged) {
        this.indDownChanged = indDownChanged;
    }

    public DDECells getDdeCells() {
        return ddeCells;
    }

    public void setDdeCells(DDECells ddeCells) {
        this.ddeCells = ddeCells;
    }

    public TwsHandler getTwsHandler() {
        return twsHandler;
    }

    public void setTwsHandler(TwsHandler twsHandler) {
        this.twsHandler = twsHandler;
    }

    public TablesHandler getTablesHandler() {
        return tablesHandler;
    }

    public void setTablesHandler(TablesHandler tablesHandler) {
        this.tablesHandler = tablesHandler;
    }

    public int getIndexBidAskCounter() {
        return indexBidAskCounter;
    }

    @Override
    public String toString() {
        return "BASE_CLIENT_OBJECT{" +
                ", optionsHandler=" + optionsHandler.toString() +
                ", startOfIndexTrading=" + startOfIndexTrading +
                ", endOfIndexTrading=" + endOfIndexTrading +
                ", endFutureTrading=" + endFutureTrading +
                ", loadFromDb=" + loadFromDb +
                ", dbRunning=" + dbRunning +
                ", ids=" + ids +
                ", started=" + started +
                ", dbId=" + dbId +
                ", index=" + index +
                ", indexBid=" + indexBid +
                ", indexAsk=" + indexAsk +
                ", futureBid=" + futureBid +
                ", futureAsk=" + futureAsk +
                ", open=" + open +
                ", high=" + high +
                ", low=" + low +
                ", base=" + base +
                ", futureBidAskCounter=" + futureBidAskCounter +
                ", indexBidAskMargin=" + indexBidAskMargin +
                ", listsService=" + listsService +
                ", mySqlService=" + mySqlService +
                ", racesMargin=" + racesMargin +
                ", optimiPesimiMargin=" + optimiPesimiMargin +
                ", conUp=" + conUp +
                ", conDown=" + conDown +
                ", indexUp=" + indexUp +
                ", indexDown=" + indexDown +
                ", conUpChanged=" + conUpChanged +
                ", conDownChanged=" + conDownChanged +
                ", indUpChanged=" + indUpChanged +
                ", indDownChanged=" + indDownChanged +
                ", indexList=" + indexList.size() +
                ", indexBidList=" + indexBidList.size() +
                ", indexAskList=" + indexAskList.size() +
                ", indexRacesList=" + indexRacesList.size() +
                '}';
    }
}
