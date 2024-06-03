package serverObjects;

import DDE.DDECells;
import DDE.DDECellsBloomberg;
import DataUpdater.DataUpdaterService;
import IDDE.DDEHandler;
import api.Manifest;
import baskets.BasketFinder_by_stocks;
import charts.timeSeries.TimeSeriesHandler;
import dataBase.mySql.MySqlService;
import dataBase.props.Props;
import exp.E;
import exp.ExpReg;
import exp.ExpStrings;
import exp.Exps;
import locals.L;
import locals.LocalHandler;
import races.RacesService;
import service.MyServiceHandler;
import stocksHandler.StocksHandler;
import stocksHandler.stocksDelta.StocksDeltaService;
import threads.MyThread;
import javax.swing.table.DefaultTableModel;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class BASE_CLIENT_OBJECT implements IBaseClient {

    private int lastTick = -1;

    public static final int PRE = 0;
    public static final int CURRENT = 1;

    // Options
    protected Exps exps;
    protected DDECells ddeCells;
    protected double strikeMargin = 0;
    protected Props props;

    protected double indBidMarginCounter = 0;
    protected double indAskMarginCounter = 0;

    protected ArrayList<Integer> bid_ask_counter_list;

    public String excel_path = "";
    public String sapi_excel_path = "";

    // Stocks delta
    StocksDeltaService stocksDeltaService;

    // Basic
    protected double index = 0;
    protected double index_bid, index_ask;
    protected double index_bid_ask_synthetic_margin = 0;

    // INDEX manipulations
    protected double index_avg_3600;
    protected double index_avg_900;

    // Table
    DefaultTableModel model = new DefaultTableModel();

    // DDE Handler
    private DDEHandler ddeHandler;

    // Basket finder
    private BasketFinder_by_stocks basketFinder_by_stocks;

    // Services
    MySqlService mySqlService;
    DataUpdaterService dataUpdaterService;
    RacesService racesService;

    private boolean loadFromDb = false;
    private boolean dbRunning = false;
    private LocalTime index_pre_start_time;

    private LocalTime indexStartTime;
    private LocalTime indexEndTime;
    private LocalTime futureEndTime;

    private int chart_start_min;

    TimeSeriesHandler timeSeriesHandler;

    // Position
    private ArrayList<MyThread> threads = new ArrayList<>();
    private HashMap<String, Integer> ids = new HashMap<>();
    private boolean started = false;
    
    // Lists map
    private String name = null;
    private String id_name = null;

    // Stocks
    private StocksHandler stocksHandler;

    // MyService
    public MyServiceHandler myServiceHandler = new MyServiceHandler(this);
    private double indexBid = 0;
    private double indexAsk = 0;
    private double open = 0;
    private double high = 0;
    private double low = 0;
    private double base = 0;
    private double indexBidAskMargin = 0;

    private double vix = 0;
    private double vix_f_1 = 0;
    private double vix_f_2 = 0;

    // Pre day q1 op avg
    private double pre_day_avg = 0;

    // RACES
    private int r_one_up = 0;
    private int r_one_down = 0;
    private int r_two_up = 0;
    private int r_two_down = 0;

    // DB
    boolean live_db = true;
    
    public BASE_CLIENT_OBJECT() {
        try {
            bid_ask_counter_list = new ArrayList<>();
            LocalHandler.clients.add(this);

            // Call subClasses abstract functions
            initSeries(this);

            // MyServices
            stocksHandler = new StocksHandler(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Start all
    public void startAll() {
        // To start
        if (isLoadFromDb()) {
            myServiceHandler.getHandler().start();
            openChartsOnStart();
            setStarted(true);
        }
    }

    // Start all
    public void closeAll() {
        getMyServiceHandler().getHandler().close();
        for (MyThread myThread : getThreads()) {
            myThread.getHandler().close();
        }
        setStarted(false);
    }

    // ---------- BASIC FUNCTIONS ---------- //

    @Override
    public void initExpHandler() {
        // Add to
        Exps exps = new Exps(this);
        exps.addExp(new ExpReg(this, ExpStrings.day));
        exps.addExp(new E(this, ExpStrings.q1));
        exps.addExp(new E(this, ExpStrings.q2));
        exps.setMainExp(exps.getExp(ExpStrings.day));
        setExps(exps);
    }

    public double getBidAskMarginCounter() {
        return indBidMarginCounter - indAskMarginCounter;
    }

    public void fullExport() {
        // TODO
    }

    // ---------- Getters and Setters ---------- //

    public String str(Object o) {
        return String.valueOf(o);
    }

    public double floor(double d, int zeros) {
        return Math.floor(d * zeros) / zeros;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public void setId_name(String id_name) {
        this.id_name = id_name;
    }

    public String getId_name() {
        return id_name;
    }

    public double getRacesMargin() {
        return index * .00008;
    }

    public DefaultTableModel getModel() {
        return model;
    }

    public void setModel(DefaultTableModel model) {
        this.model = model;
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

    public String getArikSumLine() throws UnknownHostException {
        String text = "";
        text += "***** " + getName().toUpperCase() + " *****" + "\n";
        text += "Date: " + LocalDate.now().minusDays(1) + "\n";
        text += "Open: " + open + "\n";
        text += "High: " + high + "\n";
        text += "Low: " + low + "\n";
        text += "Close: " + index + "\n";
        text += "OP avg: " + L.format100(getExps().getMainExp().getOp_avg()) + "\n";
        return text;
    }

    public boolean isLoadFromDb() {

        if (loadFromDb) {
            return true;
        }

        if (!Manifest.DB) {
            setLoadFromDb(true);
            return true;
        }

        return true;
    }

    public void setLoadFromDb(boolean loadFromDb) {
        this.loadFromDb = loadFromDb;
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

    public double getIndexBidAskMargin() {
        return indexBidAskMargin;
    }

    public void setIndexBidAskMargin(double indexBidAskMargin) {
        this.indexBidAskMargin = indexBidAskMargin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract double getTheoAvgMargin();

    public MyServiceHandler getMyServiceHandler() {
        return myServiceHandler;
    }

    public double getIndex() {
        return index;
    }

    public void setIndex(double index) {
        if (index > 1) {
            this.index = index;
        }
    }

    public double getIndexBid() {
        return indexBid;
    }

    public void setIndexBid(double indexBid) {
            this.indexBid = indexBid;
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

    public void setIndexAsk(double indexAsk) {
        if (indexAsk > 1) {
            this.indexAsk = indexAsk;
        }
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        if (open > 1) {
            this.open = open;
        }
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        if (high > 1) {
            this.high = high;
        }
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        if (low > 1) {
            this.low = low;
        }
    }

    public double getBase() {
        return base;
    }

    public void setBase(double base) {
        if (base > 1) {
            this.base = base;
        }
    }

    public double getStrikeMargin() {
        if (strikeMargin == 0) throw new NullPointerException(getName() + " Strike margin not set");
        return strikeMargin;
    }

    public void setStrikeMargin(double strikeMargin) {
        this.strikeMargin = strikeMargin;
    }

    public void setMySqlService(MySqlService mySqlService) {
        this.mySqlService = mySqlService;
    }

    public MySqlService getMySqlService() {
        return mySqlService;
    }

    public Exps getExps() {
        if (exps == null) {
            initExpHandler();
        }
        return exps;
    }

    public void setExps(Exps exps) {
        this.exps = exps;
    }

    public LocalTime getIndexStartTime() {
        return indexStartTime;
    }

    public LocalTime getIndex_pre_start_time() {
        return index_pre_start_time;
    }

    public void setIndexStartTime(LocalTime indexStartTime) {
        this.indexStartTime = indexStartTime;
    }

    public LocalTime getIndexEndTime() {
        return indexEndTime;
    }

    public void setIndexEndTime(LocalTime indexEndTime) {
        this.indexEndTime = indexEndTime;
    }

    public LocalTime getFutureEndTime() {
        return futureEndTime;
    }

    public void setFutureEndTime(LocalTime futureEndTime) {
        this.futureEndTime = futureEndTime;
    }


    public DDECells getDdeCells() {
        if (ddeCells == null) {
            ddeCells = new DDECellsBloomberg();
        }
        return ddeCells;
    }

    public StocksDeltaService getStocksDeltaService() {
        return stocksDeltaService;
    }

    public void setStocksDeltaService(StocksDeltaService stocksDeltaService) {
        this.stocksDeltaService = stocksDeltaService;
    }

    public void setDdeCells(DDECells ddeCells) {
        this.ddeCells = ddeCells;
    }

    public static int getPRE() {
        return PRE;
    }

    @Override
    public void openChartsOnStart() {
    }

    public int getLastTick() {
        return lastTick;
    }

    public void setLastTick(int lastTick) {
        this.lastTick = lastTick;
    }

    public DDEHandler getDdeHandler() {
        return ddeHandler;
    }

    public StocksHandler getStocksHandler() {
        return stocksHandler;
    }

    public Props getProps() {
        if (props == null) {
            props = new Props(this);
        }
        return props;
    }

    public void setIndex_pre_start_time(LocalTime index_pre_start_time) {
        this.index_pre_start_time = index_pre_start_time;
    }

    public String getExcel_path() {
        return excel_path;
    }

    public String getSapi_excel_path() {
        return sapi_excel_path;
    }

    public void setSapi_excel_path(String sapi_excel_path) {
        this.sapi_excel_path = sapi_excel_path;
    }

    public void setExcel_path(String excel_path) {
        this.excel_path = excel_path;
    }

    public void setStocksHandler(StocksHandler stocksHandler) {
        this.stocksHandler = stocksHandler;
    }

    public void setDdeHandler(DDEHandler ddeHandler) {
        this.ddeHandler = ddeHandler;
    }

    public BasketFinder_by_stocks getBasketFinder_by_stocks() {
        return basketFinder_by_stocks;
    }

    public void setBasketFinder_by_stocks(BasketFinder_by_stocks basketFinder_by_stocks) {
        this.basketFinder_by_stocks = basketFinder_by_stocks;
    }

    public DataUpdaterService getDataUpdaterService() {
        return dataUpdaterService;
    }

    public void setDataUpdaterService(DataUpdaterService dataUpdaterService) {
        this.dataUpdaterService = dataUpdaterService;
    }

    public RacesService getRacesService() {
        return racesService;
    }

    public void setRacesService(RacesService racesService) {
        this.racesService = racesService;
    }

    public static int getCURRENT() {
        return CURRENT;
    }

    public void setProps(Props props) {
        this.props = props;
    }

    public void setIndBidMarginCounter(double indBidMarginCounter) {
        this.indBidMarginCounter = indBidMarginCounter;
    }

    public TimeSeriesHandler getTimeSeriesHandler() {
        if (timeSeriesHandler == null) {
            timeSeriesHandler = new TimeSeriesHandler();
        }
        return timeSeriesHandler;
    }

    public void setTimeSeriesHandler(TimeSeriesHandler timeSeriesHandler) {
        this.timeSeriesHandler = timeSeriesHandler;
    }

    public boolean isLive_db() {
        return live_db;
    }

    public void setLive_db(boolean live_db) {
        this.live_db = live_db;
    }


    public double getIndex_bid() {
        return indexBid + index_bid_ask_synthetic_margin;
    }

    public void setIndex_bid(double index_bid) {
        this.index_bid = index_bid;
    }

    public double getIndex_ask() {
        return indexAsk + index_bid_ask_synthetic_margin;
    }

    public void setIndex_ask(double index_ask) {
        this.index_ask = index_ask;
    }

    public double getIndex_bid_ask_synthetic_margin() {
        return index_bid_ask_synthetic_margin;
    }

    public void setIndex_bid_ask_synthetic_margin(double index_bid_ask_synthetic_margin) {
        this.index_bid_ask_synthetic_margin = index_bid_ask_synthetic_margin;
    }

    public int getChart_start_min() {
        return chart_start_min;
    }

    public void setChart_start_min(int chart_start_min) {
        this.chart_start_min = chart_start_min;
    }

    public double getIndex_avg_3600() {
        return index_avg_3600;
    }

    public void setIndex_avg_3600(double index_avg_3600) {
        this.index_avg_3600 = index_avg_3600;
    }

    public double getIndex_avg_900() {
        return index_avg_900;
    }

    public void setIndex_avg_900(double index_avg_900) {
        this.index_avg_900 = index_avg_900;
    }

    public double getPre_day_avg() {
        return pre_day_avg;
    }

    public void setPre_day_avg(double pre_day_avg) {
        this.pre_day_avg = pre_day_avg;
    }

    public double getVix() {
        return vix;
    }

    public void setVix(double vix) {
        this.vix = vix;
    }

    public double getVix_f_1() {
        return vix_f_1;
    }

    public void setVix_f_1(double vix_f_1) {
        this.vix_f_1 = vix_f_1;
    }

    public double getVix_f_2() {
        return vix_f_2;
    }

    public void setVix_f_2(double vix_f_2) {
        this.vix_f_2 = vix_f_2;
    }

    public int getR_one_up() {
        return r_one_up;
    }

    public void setR_one_up(int r_one_up) {
        this.r_one_up = r_one_up;
    }

    public int getR_one_down() {
        return r_one_down;
    }

    public void setR_one_down(int r_one_down) {
        this.r_one_down = r_one_down;
    }

    public int getR_two_up() {
        return r_two_up;
    }

    public void setR_two_up(int r_two_up) {
        this.r_two_up = r_two_up;
    }

    public int getR_two_down() {
        return r_two_down;
    }

    public void setR_two_down(int r_two_down) {
        this.r_two_down = r_two_down;
    }

    @Override
    public String toString() {
        return "BASE_CLIENT_OBJECT{" +
                "props=" + props +
                ", excel_path='" + excel_path + '\'' +
                ", sapi_excel_path='" + sapi_excel_path + '\'' +
                ", loadFromDb=" + loadFromDb +
                ", dbRunning=" + dbRunning +
                ", started=" + started +
                ", name='" + name + '\'' +
                ", id_name='" + id_name + '\'' +
                ", pre_day_avg=" + pre_day_avg +
                ", live_db=" + live_db +
                '}';
    }
}
