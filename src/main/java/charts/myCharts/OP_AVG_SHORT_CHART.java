package charts.myCharts;

import charts.myChart.*;
import charts.timeSeries.MyTimeSeries;
import exp.Exp;
import exp.ExpStrings;
import locals.Themes;
import org.jfree.chart.plot.ValueMarker;
import serverObjects.BASE_CLIENT_OBJECT;
import java.awt.*;
import java.net.UnknownHostException;
import java.sql.ResultSet;

public class OP_AVG_SHORT_CHART extends MyChartCreator {

    Exp day;

    // Constructor
    public OP_AVG_SHORT_CHART(BASE_CLIENT_OBJECT client) {
        super(client, null, null);
        day = client.getExps().getExp(ExpStrings.day);
    }

    @Override
    public void init() {

        MyTimeSeries[] series;


        // Marker
        ValueMarker marker = new ValueMarker(0);
        marker.setPaint(Themes.GREY_2);
        marker.setStroke(new BasicStroke(2f));

        // Props
        props = new MyProps();
        props.setProp(ChartPropsEnum.SECONDS, 1800);
        props.setProp(ChartPropsEnum.IS_INCLUDE_TICKER, -1);
        props.setProp(ChartPropsEnum.MARGIN, 0.005);
        props.setProp(ChartPropsEnum.IS_RANGE_GRID_VISIBLE, -1);
        props.setProp(ChartPropsEnum.IS_LOAD_DB, 1);
        props.setProp(ChartPropsEnum.IS_LIVE, -1);
        props.setProp(ChartPropsEnum.SLEEP, 1000);
        props.setProp(ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS, INFINITE);
        props.setProp(ChartPropsEnum.SECONDS_ON_MESS, INFINITE);
        props.setProp(ChartPropsEnum.INCLUDE_DOMAIN_AXIS, 1);
        props.setProp(ChartPropsEnum.MARKER, 0);
        
        // --------------------------- Index -------------------------------- //

        // Index
        MyTimeSeries indexSeries = new MyTimeSeries("Index", client) {
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

        // ----------------------------- OP AVG ---------------------------- //

        // OP avg 1
        MyTimeSeries op_avg_1 = new MyTimeSeries("OP avg 1", client) {
            @Override
            public ResultSet load_last_x_time(int minuts) {
                return null;
            }

            @Override
            public double getData() throws UnknownHostException {
                return day.getOp_avg_1();
            }

            @Override
            public void load() {

            }
        };
        op_avg_1.setColor(Themes.PURPLE);
        op_avg_1.setStokeSize(1.5f);

        // OP avg 5
        MyTimeSeries op_avg_5 = new MyTimeSeries("OP avg 5", client) {
            @Override
            public ResultSet load_last_x_time(int minuts) {
                return null;
            }

            @Override
            public double getData() throws UnknownHostException {
                return day.getOp_avg_5();
            }

            @Override
            public void load() {

            }
        };
        op_avg_5.setColor(Themes.RED);
        op_avg_5.setStokeSize(1.5f);

        // OP avg 15
        MyTimeSeries op_avg_15 = new MyTimeSeries("OP avg 15", client) {
            @Override
            public ResultSet load_last_x_time(int minuts) {
                return null;
            }

            @Override
            public double getData() throws UnknownHostException {
                return day.getOp_avg_60_continue();
            }

            @Override
            public void load() {

            }
        };
        op_avg_15.setColor(Themes.GREEN);
        op_avg_15.setStokeSize(1.5f);


        // OP avg 1
        MyTimeSeries op_avg_60 = new MyTimeSeries("OP avg 60", client) {
            @Override
            public ResultSet load_last_x_time(int minuts) {
                return null;
            }

            @Override
            public double getData() throws UnknownHostException {
                return day.getOp_avg_60_continue();
            }

            @Override
            public void load() {

            }
        };
        op_avg_60.setColor(Themes.BLUE);
        op_avg_60.setStokeSize(1.5f);

        // OP avg 1
        MyTimeSeries op_avg_240 = new MyTimeSeries("OP avg 240", client) {
            @Override
            public ResultSet load_last_x_time(int minuts) {
                return null;
            }

            @Override
            public double getData() throws UnknownHostException {
                return day.getOp_avg_240_continue();
            }

            @Override
            public void load() {

            }
        };
        op_avg_240.setColor(Themes.ORANGE);
        op_avg_240.setStokeSize(1.5f);


        series = new MyTimeSeries[5];
        series[0] = op_avg_1;
        series[1] = op_avg_5;
        series[2] = op_avg_15;
        series[3] = op_avg_60;
        series[4] = op_avg_240;

        // Chart
        MyChart opavg_chart = new MyChart(client, series, props);

        // ------------------------------ Chart ----------------------------- //

        // ----- Charts ----- //
        MyChart[] charts = {indexChart, opavg_chart};

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer(client, charts, getClass().getName());
        chartContainer.create();
    }

}
