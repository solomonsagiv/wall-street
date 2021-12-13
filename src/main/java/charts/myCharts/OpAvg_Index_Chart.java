package charts.myCharts;

import charts.myChart.*;
import charts.timeSeries.MyTimeSeries;
import charts.timeSeries.TimeSeriesFactory;
import dataBase.mySql.MySql;
import dataBase.mySql.dataUpdaters.IDataBaseHandler;
import exp.Exp;
import exp.ExpStrings;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;

import java.awt.*;
import java.net.UnknownHostException;
import java.sql.ResultSet;

public class OpAvg_Index_Chart extends MyChartCreator {

    public static void main(String[] args) {
        OpAvg_Index_Chart fullChart2 = new OpAvg_Index_Chart(Spx.getInstance());
        fullChart2.createChart();
    }

    // Constructor
    public OpAvg_Index_Chart(BASE_CLIENT_OBJECT client) {
        super(client, null, null);
    }

    @Override
    public void init() {

        MyTimeSeries[] series;

        // Props
        props = new MyProps();
        props.setProp(ChartPropsEnum.SECONDS, INFINITE);
        props.setProp(ChartPropsEnum.IS_INCLUDE_TICKER, -1);
        props.setProp(ChartPropsEnum.MARGIN, 0.005);
        props.setProp(ChartPropsEnum.IS_RANGE_GRID_VISIBLE, -1);
        props.setProp(ChartPropsEnum.IS_LOAD_DB, 1);
        props.setProp(ChartPropsEnum.IS_LIVE, -1);
        props.setProp(ChartPropsEnum.SLEEP, 5000);
        props.setProp(ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS, INFINITE);
        props.setProp(ChartPropsEnum.SECONDS_ON_MESS, INFINITE);
        props.setProp(ChartPropsEnum.INCLUDE_DOMAIN_AXIS, 1);
        props.setProp(ChartPropsEnum.MARKER, 0);

        // Exp
        Exp day_exp = client.getExps().getExp(ExpStrings.day);

        // --------- Op avg ---------- //

        MyTimeSeries op_avg_1 = new MyTimeSeries("O/P Avg Day 1", client) {
            @Override
            public ResultSet load_last_x_time(int minuts) {
                return null;
            }

            @Override
            public double getData() throws UnknownHostException {
                return day_exp.get_op_avg(60);
            }
            @Override
            public void load() {
            }
        };
        op_avg_1.setColor(Themes.PINK_LIGHT);
        op_avg_1.setStokeSize(1.2f);

        MyTimeSeries op_avg_5 = new MyTimeSeries("O/P Avg Day 5", client) {
            @Override
            public ResultSet load_last_x_time(int minuts) {
                return null;
            }

            @Override
            public double getData() throws UnknownHostException {
                return day_exp.get_op_avg(300);
            }
            @Override
            public void load() {
            }
        };
        op_avg_5.setColor(Themes.RED_2);
        op_avg_5.setStokeSize(1.2f);

        MyTimeSeries op_avg_15 = new MyTimeSeries("O/P Avg Day 15", client) {
            @Override
            public ResultSet load_last_x_time(int minuts) {
                return null;
            }

            @Override
            public double getData() throws UnknownHostException {
                return day_exp.get_op_avg(900);
            }
            @Override
            public void load() {
            }
        };
        op_avg_15.setColor(Themes.GREEN);
        op_avg_15.setStokeSize(1.2f);

        MyTimeSeries op_avg_60 = new MyTimeSeries("O/P Avg Day 60", client) {
            @Override
            public ResultSet load_last_x_time(int minuts) {
                return null;
            }

            @Override
            public double getData() throws UnknownHostException {
                return day_exp.get_op_avg(3600);
            }
            @Override
            public void load() {
            }
        };
        op_avg_60.setColor(Themes.BLUE);
        op_avg_60.setStokeSize(1.2f);

        series = new MyTimeSeries[4];
        series[0] = op_avg_1;
        series[1] = op_avg_5;
        series[2] = op_avg_15;
        series[3] = op_avg_60;

        // Chart
        MyChart op_avg_chart = new MyChart(client, series, props);

        // --------- Index 2 ---------- //

        // Index
        MyTimeSeries indexSeries = new MyTimeSeries("Index " ,client) {
            @Override
            public ResultSet load_last_x_time(int minuts) {
                return null;
            }

            @Override
            public double getData() throws UnknownHostException {
                return client.getIndex();
            }

            @Override
            public void load() {

            }
        };
        indexSeries.setColor(Color.BLACK);
        indexSeries.setStokeSize(1.5f);

        series = new MyTimeSeries[1];
        series[0] = indexSeries;

        // Chart
        MyChart indexChart = new MyChart(client, series, props);


        // --------------- Charts --------------- //
        MyChart[] charts = {indexChart, op_avg_chart};

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer(client, charts, getClass().getName());
        chartContainer.create();
    }

}
