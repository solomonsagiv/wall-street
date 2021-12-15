package charts.myCharts;

import charts.myChart.*;
import charts.timeSeries.MyTimeSeries;
import exp.Exp;
import exp.ExpStrings;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;

import java.awt.*;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Index_plus_opAvg_Chart extends MyChartCreator {

    // Constructor
    public Index_plus_opAvg_Chart(BASE_CLIENT_OBJECT client ) {
        super(client, null, null);
    }

    ArrayList<MyTimeSeries> myTimeSeries = new ArrayList<>();

    MyChart[] charts;

    @Override
    public void init() {

        Exp day = client.getExps().getExp(ExpStrings.day);

        // Props
        props = new MyProps();
        props.setProp(ChartPropsEnum.SECONDS, 300);
        props.setProp(ChartPropsEnum.IS_INCLUDE_TICKER, -1);
        props.setProp(ChartPropsEnum.MARGIN, 0.003);
        props.setProp(ChartPropsEnum.IS_RANGE_GRID_VISIBLE, -1);
        props.setProp(ChartPropsEnum.IS_LOAD_DB, -1);
        props.setProp(ChartPropsEnum.IS_LIVE, 1);
        props.setProp(ChartPropsEnum.SLEEP, 100);
        props.setProp(ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS, INFINITE);
        props.setProp(ChartPropsEnum.SECONDS_ON_MESS, 10);

        // ----- Chart 1 ----- //
        // Index
        MyTimeSeries index = new MyTimeSeries("Index", client) {
            @Override
            public ResultSet load_last_x_time(int minuts) {
                return null;
            }

            @Override
            public double getData() {
                return client.getIndex();
            }

            @Override
            public void load() {

            }
        };
        index.setColor(Color.BLACK);
        index.setStokeSize(2.25f);

        // Index
        MyTimeSeries index_min_avg_1 = new MyTimeSeries("Index avg 1", client) {
            @Override
            public ResultSet load_last_x_time(int minuts) {
                return null;
            }

            @Override
            public double getData() {
                return client.getIndex() + day.getOp_avg_1();
            }

            @Override
            public void load() {

            }
        };
        index_min_avg_1.setColor(Themes.GREEN);
        index_min_avg_1.setStokeSize(2.25f);

        // Index avg 5
        MyTimeSeries index_min_avg_5 = new MyTimeSeries("Index avg 5", client) {
            @Override
            public ResultSet load_last_x_time(int minuts) {
                return null;
            }

            @Override
            public double getData() {
                return client.getIndex() + day.getOp_avg_5();
            }

            @Override
            public void load() {

            }
        };
        index_min_avg_5.setColor(Themes.GREEN);
        index_min_avg_5.setStokeSize(2.25f);
        index_min_avg_5.setVisible(false);

        // Index avg 5
        MyTimeSeries index_min_avg_15 = new MyTimeSeries("Index avg 15", client) {
            @Override
            public ResultSet load_last_x_time(int minuts) {
                return null;
            }

            @Override
            public double getData() {
                return client.getIndex() + day.getOp_avg_15();
            }

            @Override
            public void load() {

            }
        };
        index_min_avg_15.setColor(Themes.GREEN);
        index_min_avg_15.setStokeSize(2.25f);
        index_min_avg_15.setVisible(false);

        // Index avg 60
        MyTimeSeries index_min_avg_60 = new MyTimeSeries("Index avg 60", client) {
            @Override
            public ResultSet load_last_x_time(int minuts) {
                return null;
            }

            @Override
            public double getData() {
                return client.getIndex() + day.getOp_avg_60();
            }

            @Override
            public void load() {

            }
        };
        index_min_avg_60.setColor(Themes.GREEN);
        index_min_avg_60.setStokeSize(2.25f);
        index_min_avg_60.setVisible(false);

        // Bid
        MyTimeSeries bid = new MyTimeSeries("Index bid", client) {
            @Override
            public ResultSet load_last_x_time(int minuts) {
                return null;
            }

            @Override
            public double getData() {
                return client.getIndexBid();
            }

            @Override
            public void load() {

            }
        };
        bid.setColor(Themes.BLUE);
        bid.setStokeSize(2.25f);

        // Ask
        MyTimeSeries ask = new MyTimeSeries("Index ask", client) {
            @Override
            public ResultSet load_last_x_time(int minuts) {
                return null;
            }

            @Override
            public double getData() {
                return client.getIndexAsk();
            }

            @Override
            public void load() {

            }
        };
        ask.setColor(Themes.RED);
        ask.setStokeSize(2.25f);

        myTimeSeries.add(index_min_avg_1);
        myTimeSeries.add(index_min_avg_5);
        myTimeSeries.add(index_min_avg_15);
        myTimeSeries.add(index_min_avg_60);
        myTimeSeries.add(index);
        myTimeSeries.add(bid);
        myTimeSeries.add(ask);

        // Series
        MyTimeSeries[] series = toArray();

        // Chart
        MyChart chart = new MyChart(client, series, props);

        // ----- Charts ----- //
        MyChart[] charts = {chart};

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer(client, charts, getClass().getName());
        chartContainer.create();
    }

    private MyChart[] getCharts() {
        return charts;
    }

    private MyTimeSeries[] toArray() {
        MyTimeSeries[] arr = new MyTimeSeries[myTimeSeries.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = myTimeSeries.get(i);
        }
        return arr;
    }

}
