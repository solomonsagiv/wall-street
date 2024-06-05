package serverObjects.indexObjects;

import DataUpdater.DataUpdaterService;
import IDDE.DDEHandler;
import IDDE.DDEReader_Dax;
import IDDE.DDEWriter_Dax;
import api.Manifest;
import baskets.BasketFinder_by_stocks;
import charts.myCharts.Chart_13;
import charts.myCharts.Realtime_Chart;
import dataBase.mySql.MySqlService;
import dataBase.mySql.dataUpdaters.DataBaseHandler_Dax;
import exp.E;
import exp.ExpStrings;
import exp.Exps;
import races.Race_Logic;
import races.RacesService;
import serverObjects.ApiEnum;
import serverObjects.BASE_CLIENT_OBJECT;

import java.util.HashMap;

public class Dax extends INDEX_CLIENT_OBJECT {

    static Dax client = null;

    // Constructor
    public Dax() {
        setName("dax");
        setId_name("dax");
        initExpHandler();
        setMySqlService(new MySqlService(this, new DataBaseHandler_Dax(this)));
        setDdeHandler(new DDEHandler(this, new DDEReader_Dax(this), new DDEWriter_Dax(this)));
        setDataUpdaterService(new DataUpdaterService(this));

        setBasketFinder_by_stocks(new BasketFinder_by_stocks(this, 30, 3));
        setLive_db(true);
        setIndex_bid_ask_synthetic_margin(5);

        // Race logic
        HashMap<Race_Logic.RACE_RUNNER_ENUM, Race_Logic> map = new HashMap<>();
        map.put(Race_Logic.RACE_RUNNER_ENUM.Q1_INDEX, new Race_Logic(this, Race_Logic.RACE_RUNNER_ENUM.Q1_INDEX));
        setRacesService(new RacesService(this, map));
    }

    // get instance
    public static Dax getInstance() {
        if (client == null) {
            client = new Dax();
        }
        return client;
    }

    @Override
    public void setIndexBid(double indexBid) {
        super.setIndexBid(indexBid);
    }

    @Override
    public void setIndexAsk(double indexAsk) {
        super.setIndexAsk(indexAsk);
        // Margin counter‰
        double bidMargin = index - getIndexBid();
        double askMargin = indexAsk - index;
        double marginOfMarings = bidMargin - askMargin;

        if (marginOfMarings > 0 && marginOfMarings < 5) {
            indAskMarginCounter += marginOfMarings;
        }
    }

    @Override
    public void initExpHandler() {
        // Add to
        Exps exps = new Exps(this);
        exps.addExp(new E(this, ExpStrings.day));
        exps.addExp(new E(this, ExpStrings.month));
        exps.addExp(new E(this, ExpStrings.q1));
        exps.addExp(new E(this, ExpStrings.q2));
        exps.setMainExp(exps.getExp(ExpStrings.q1));
        setExps(exps);
    }

    @Override
    public void setIndex(double index) {
        super.setIndex(index);
    }

    @Override
    public ApiEnum getApi() {
        return ApiEnum.DDE;
    }

    @Override
    public void initSeries(BASE_CLIENT_OBJECT client) {

    }

    @Override
    public void openChartsOnStart() {
        if (Manifest.OPEN_CHARTS) {
            new Thread(() -> {
                Realtime_Chart chart = new Realtime_Chart(this);
                chart.createChart();

                Chart_13 chart_13 = new Chart_13(this);
                chart_13.createChart();

            }).start();
        }
    }

    @Override
    public double getTheoAvgMargin() {
        return 0.05;
    }
}
