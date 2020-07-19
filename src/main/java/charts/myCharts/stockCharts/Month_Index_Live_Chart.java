package charts.myCharts.stockCharts;

import charts.myChart.*;
import exp.ExpStrings;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;

import java.awt.*;
import java.net.UnknownHostException;

public class Month_Index_Live_Chart extends MyChartCreator {

    // Constructor
    public Month_Index_Live_Chart(BASE_CLIENT_OBJECT client) {
        super(client);
    }

    @Override
    public void createChart() {

        // Props
        props = new MyProps();
        props.setProp(ChartPropsEnum.SECONDS, 150);
        props.setProp(ChartPropsEnum.IS_INCLUDE_TICKER, false);
        props.setProp(ChartPropsEnum.MARGIN, .17);
        props.setProp(ChartPropsEnum.RANGE_MARGIN, 0.0);
        props.setProp(ChartPropsEnum.IS_GRID_VISIBLE, false);
        props.setProp(ChartPropsEnum.IS_LOAD_DB, false);
        props.setProp(ChartPropsEnum.IS_LIVE, true);
        props.setProp(ChartPropsEnum.SLEEP, 200);
        props.setProp(ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS, (double) INFINITE);
        props.setProp(ChartPropsEnum.SECONDS_ON_MESS, 10);

        // ----- Chart 1 ----- //
        // Index
        MyTimeSeries indexSeries = new MyTimeSeries("Index", client) {
            @Override
            public double getData() throws UnknownHostException {
                return client.getIndex();
            }
        };

        indexSeries.setColor(Color.BLACK);
        indexSeries.setStokeSize(2.25f);

        // Index bid
        MyTimeSeries indexBidSeries = new MyTimeSeries("Index bid", client) {
            @Override
            public double getData() throws UnknownHostException {
                return client.getIndexBid();
            }
        };
        indexBidSeries.setColor(Themes.BLUE);
        indexBidSeries.setStokeSize(2.25f);

        // Index ask
        MyTimeSeries indexAskSeries = new MyTimeSeries("Index ask", client) {
            @Override
            public double getData() throws UnknownHostException {
                return client.getIndexAsk();
            }
        };
        indexAskSeries.setColor(Themes.RED);
        indexAskSeries.setStokeSize(2.25f);

        // Contract
        MyTimeSeries contractSeries = new MyTimeSeries("Contract", client) {
            @Override
            public double getData() {
                return client.getExps().getExp(ExpStrings.month).getOptions().getContract();
            }
        };
        contractSeries.setColor(Themes.GREEN);
        contractSeries.setStokeSize(2.25f);

        MyTimeSeries[] series = {indexSeries, indexBidSeries, indexAskSeries, contractSeries};

        // Chart
        MyChart chart = new MyChart(client, series, props);

        // ----- Charts ----- //
        MyChart[] charts = {chart};

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer(client, charts, getClass().getName());
        chartContainer.create();


    }

}
