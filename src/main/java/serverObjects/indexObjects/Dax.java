package serverObjects.indexObjects;

import DataUpdater.DataUpdaterService;
import IDDE.DDEHandler;
import IDDE.DDEReader_Dax;
import IDDE.DDEWriter_Dax;
import api.Manifest;
import baskets.BasketFinder_by_stocks;
import charts.myCharts.DAX_CHART_10;
import charts.myCharts.FuturesChartLong_400;
import dataBase.mySql.MySqlService;
import dataBase.mySql.dataUpdaters.DataBaseHandler_Dax;
import exp.E;
import exp.ExpStrings;
import exp.Exps;
import serverObjects.ApiEnum;
import serverObjects.BASE_CLIENT_OBJECT;

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
        setLive_db(false);
        setIndex_bid_ask_synthetic_margin(5);
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
        // Margin counter
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
                FuturesChartLong_400 chart = new FuturesChartLong_400(this);
                chart.createChart();

                DAX_CHART_10 chart_10 = new DAX_CHART_10(this);
                chart_10.createChart();

            }).start();
        }
    }

    @Override
    public double getTheoAvgMargin() {
        return 0.05;
    }
}
