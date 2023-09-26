package serverObjects.indexObjects;

import DataUpdater.DataUpdaterService;
import IDDE.DDEHandler;
import IDDE.DDEReader_Ndx;
import IDDE.DDEWriter_Ndx;
import api.Manifest;
import baskets.BasketFinder_by_stocks;
import charts.myCharts.Chart_10;
import charts.myCharts.Chart_9;
import charts.myCharts.FuturesChartLong_400;
import dataBase.mySql.MySqlService;
import dataBase.mySql.dataUpdaters.DataBaseHandler_Ndx;
import serverObjects.ApiEnum;
import serverObjects.BASE_CLIENT_OBJECT;

public class Ndx extends INDEX_CLIENT_OBJECT {

    static Ndx client = null;

    // Constructor
    public Ndx() {
        setName("ndx");
        setId_name("ndx");
        setMySqlService(new MySqlService(this, new DataBaseHandler_Ndx(this)));
        setBasketFinder_by_stocks(new BasketFinder_by_stocks(this, 80, 3));
        setDdeHandler(new DDEHandler(this, new DDEReader_Ndx(this), new DDEWriter_Ndx(this)));
        setDataUpdaterService(new DataUpdaterService(this));
    }

    // get instance
    public static Ndx getInstance() {
        if (client == null) {
            client = new Ndx();

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

                FuturesChartLong_400 chart = new FuturesChartLong_400(this);
                chart.createChart();

                Chart_9 chart_4 = new Chart_9(this);
                chart_4.createChart();

                Chart_10 chart_5 = new Chart_10(this);
                chart_5.createChart();
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
