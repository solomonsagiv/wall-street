package serverObjects.indexObjects;

import DataUpdater.DataUpdaterService;
import IDDE.DDEHandler;
import IDDE.DDEReader_Spx;
import IDDE.DDEWriter_Spx;
import api.Manifest;
import charts.myCharts.Chart_9;
import charts.myCharts.Chart_wallstreet;
import charts.myCharts.FuturesChart;
import dataBase.mySql.MySqlService;
import dataBase.mySql.dataUpdaters.DataBaseHandler_Spx;
import serverObjects.ApiEnum;
import serverObjects.BASE_CLIENT_OBJECT;

public class Spx extends INDEX_CLIENT_OBJECT {

    static Spx client = null;

    // Constructor
    public Spx() {
        setName("spx");
        setId_name("spx500");
        setMySqlService(new MySqlService(this, new DataBaseHandler_Spx(this)));
        setDdeHandler(new DDEHandler(this, new DDEReader_Spx(this), new DDEWriter_Spx(this)));
        setDataUpdaterService(new DataUpdaterService(this));

    }

    // get instance
    public static Spx getInstance() {
        if (client == null) {
            client = new Spx();
        }
        return client;
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

                FuturesChart chart = new FuturesChart(this);
                chart.createChart();

                Chart_wallstreet Chart_wallstreet = new Chart_wallstreet(this);
                Chart_wallstreet.createChart();

                Chart_9 chart_4 = new Chart_9(this);
                chart_4.createChart();

            }).start();
        }
    }

    @Override
    public double getTheoAvgMargin() {
        return 0.05;
    }
}
