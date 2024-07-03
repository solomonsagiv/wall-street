package serverObjects.indexObjects;

import DataUpdater.DataUpdaterService;
import IDDE.DDEHandler;
import IDDE.DDEReader_Ndx;
import IDDE.DDEWriter_Ndx;
import api.Manifest;
import charts.myCharts.Races_Chart_index_roll_races;
import charts.myCharts.Realtime_Chart;
import dataBase.mySql.MySqlService;
import dataBase.mySql.dataUpdaters.DataBaseHandler_Ndx;
import races.Race_Logic;
import races.RacesService;
import serverObjects.ApiEnum;
import serverObjects.BASE_CLIENT_OBJECT;

import java.util.HashMap;

public class Ndx extends INDEX_CLIENT_OBJECT {

    static Ndx client = null;

    // Constructor
    public Ndx() {
        setName("ndx");
        setId_name("ndx");
        setMySqlService(new MySqlService(this, new DataBaseHandler_Ndx(this)));
//        setBasketFinder_by_stocks(new BasketFinder_by_stocks(this, 85, 3));
        setDdeHandler(new DDEHandler(this, new DDEReader_Ndx(this), new DDEWriter_Ndx(this)));
        setDataUpdaterService(new DataUpdaterService(this));
        setIndex_bid_ask_synthetic_margin(5);

        // Race logic
        init_races();
    }

    // get instance
    public static Ndx getInstance() {
        if (client == null) {
            client = new Ndx();

        }
        return client;
    }


    @Override
    public void init_races() {
        HashMap<Race_Logic.RACE_RUNNER_ENUM, Race_Logic> map = new HashMap<>();
        map.put(Race_Logic.RACE_RUNNER_ENUM.Q1_INDEX, new Race_Logic(this, Race_Logic.RACE_RUNNER_ENUM.Q1_INDEX, getRace_margin()));
        map.put(Race_Logic.RACE_RUNNER_ENUM.WEEK_Q1, new Race_Logic(this, Race_Logic.RACE_RUNNER_ENUM.WEEK_Q1, getRace_margin()));
        setRacesService(new RacesService(this, map));
    }


    @Override
    public void setIndexBid(double indexBid) {
        super.setIndexBid(indexBid);

        // Margin counter
        double bidMargin = index - indexBid;
        double askMargin = getIndexAsk() - index;
        double marginOfMarings = askMargin - bidMargin;

        if (marginOfMarings > 0) {
            indBidMarginCounter += marginOfMarings;
        }
    }

    @Override
    public void setIndexAsk(double indexAsk) {
        super.setIndexAsk(indexAsk);
        // Margin counter
        double bidMargin = index - getIndexBid();
        double askMargin = indexAsk - index;
        double marginOfMarings = bidMargin - askMargin;

        if (marginOfMarings > 0 && marginOfMarings < 5) {
            indAskMarginCounter += marginOfMarings;
        }
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
                Realtime_Chart realtime_chart = new Realtime_Chart(this);
                realtime_chart.createChart();

                Races_Chart_index_roll_races races_chart = new Races_Chart_index_roll_races(this);
                races_chart.createChart();
            }).start();
        }
    }

    @Override
    public double getTheoAvgMargin() {
        return 0.05;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(super.toString());
        str.append("Baskets 2= " + getBasketFinder_by_stocks().toString());
        return str.toString();
    }

}
