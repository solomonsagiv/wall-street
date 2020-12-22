package charts.myCharts;

import charts.myChart.*;
import exp.ExpStrings;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;

import java.awt.*;

public class ThreeFutLineChart extends MyChartCreator {

    // Constructor
    public ThreeFutLineChart(BASE_CLIENT_OBJECT client) {
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
        props.setProp(ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS, INFINITE);
        props.setProp(ChartPropsEnum.SECONDS_ON_MESS, 10);

        // ----- Chart 1 ----- //
        // Index
        MyTimeSeries index = new MyTimeSeries("Index", client) {
            @Override
            public double getData() {
                return client.getIndex();
            }
        };
        index.setColor(Color.BLACK);
        index.setStokeSize(2.25f);

        // Bid
        MyTimeSeries bid = new MyTimeSeries("Bid", client) {
            @Override
            public double getData() {
                return client.getIndexBid();
            }
        };
        bid.setColor(Themes.BLUE);
        bid.setStokeSize(2.25f);

        // Ask
        MyTimeSeries ask = new MyTimeSeries("Ask", client) {
            @Override
            public double getData() {
                return client.getIndexAsk();
            }
        };
        ask.setColor(Themes.RED);
        ask.setStokeSize(2.25f);

        // Future
        MyTimeSeries futureQuarter = new MyTimeSeries("Future quarter", client) {
            @Override
            public double getData() {
                return client.getExps().getExp(ExpStrings.e1).getCalcFut();
            }
        };

        futureQuarter.setColor(Themes.GREEN);
        futureQuarter.setStokeSize(2.25f);

        // Future
        MyTimeSeries futureWeek = new MyTimeSeries("Future week", client) {
            @Override
            public double getData() {
                return client.getFutWeek();
            }
        };

        futureWeek.setColor(Themes.GREEN_LIGHT_2);
        futureWeek.setStokeSize(2.25f);

        // Future
        MyTimeSeries futureMonth = new MyTimeSeries("Future month", client) {
            @Override
            public double getData() {
                return client.getFutMonth();
            }
        };

        futureMonth.setColor(Themes.GREEN_LIGHT);
        futureMonth.setStokeSize(2.25f);


        MyTimeSeries[] series = {index, bid, ask, futureQuarter, futureWeek, futureMonth};

        // Chart
        MyChart chart = new MyChart(client, series, props);

        // ----- Charts ----- //
        MyChart[] charts = {chart};

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer(client, charts, getClass().getName());
        chartContainer.create();
    }

}
