package charts.myCharts;

import charts.myChart.*;
import charts.timeSeries.MyTimeSeries;
import charts.timeSeries.TimeSeriesFactory;
import exp.Exp;
import locals.Themes;
import org.jfree.chart.plot.ValueMarker;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;

import java.awt.*;

public class FullCharts extends MyChartCreator {

    public static void main(String[] args) {
        FullCharts fullCharts = new FullCharts(Spx.getInstance());
        fullCharts.createChart();
    }

    // Constructor
    public FullCharts(BASE_CLIENT_OBJECT client) {
        super(client, null, null);
    }

    @Override
    public void createChart() {

        MyTimeSeries[] series;

        // Props
        props = new MyProps();
        props.setProp(ChartPropsEnum.SECONDS, INFINITE);
        props.setProp(ChartPropsEnum.IS_INCLUDE_TICKER, -1);
        props.setProp(ChartPropsEnum.MARGIN, 0.005);
        props.setProp(ChartPropsEnum.RANGE_MARGIN, 0.0);
        props.setProp(ChartPropsEnum.IS_GRID_VISIBLE, 1);
        props.setProp(ChartPropsEnum.IS_LOAD_DB, 1);
        props.setProp(ChartPropsEnum.IS_LIVE, -1);
        props.setProp(ChartPropsEnum.SLEEP, 1000);
        props.setProp(ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS, INFINITE);
        props.setProp(ChartPropsEnum.SECONDS_ON_MESS, INFINITE);
        props.setProp(ChartPropsEnum.INCLUDE_DOMAIN_AXIS, 1);

        // Marker
        ValueMarker marker = new ValueMarker(0);
        marker.setPaint(Color.BLACK);
        marker.setStroke(new BasicStroke(2f));

        // --------- OpAvgFuture 15 ---------- //


        int size = client.getExps().getExpList().size() * 2;
        series = new MyTimeSeries[size];

        int i = 0;
        // For each exp
        for (Exp exp : client.getExps().getExpList()) {
            // Index
            MyTimeSeries op_avg_60 = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_HOUR_SERIES, client, exp);
            op_avg_60.setColor(Themes.BROWN);
            op_avg_60.setStokeSize(1.5f);

            // Index
            MyTimeSeries op_avg_15 = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_15_SERIES, client, exp);
            op_avg_15.setColor(Themes.PURPLE);
            op_avg_15.setStokeSize(1.5f);

            series[i] = op_avg_15;
            i++;
            series[i] = op_avg_60;
            i++;

            // If main
            if (exp.getName().equals(client.getExps().getMainExp().getName())) {
                op_avg_60.setVisible(true);
                op_avg_15.setVisible(true);
            } else {
                op_avg_60.setVisible(false);
                op_avg_15.setVisible(false);
            }
        }


        // Chart
        MyChart op_avg_all_exps_chart = new MyChart(client, series, props);

        // --------- Index Bid Ask Counter ---------- //
        // Index
        MyTimeSeries indexBidAskCounterSeries = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.INDEX_BID_ASK_COUNTER_SERIES, client, null);
        indexBidAskCounterSeries.setColor(Themes.ORANGE);
        indexBidAskCounterSeries.setStokeSize(1.5f);

        series = new MyTimeSeries[1];
        series[0] = indexBidAskCounterSeries;

        // Chart
        MyChart indexBidAskCounterChart = new MyChart(client, series, props);

        // --------- Index 2 ---------- //

        // Index
        MyTimeSeries indexSeries = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.INDEX_SERIES, client, null);
        indexSeries.setColor(Color.BLACK);
        indexSeries.setStokeSize(1.5f);

        series = new MyTimeSeries[1];
        series[0] = indexSeries;

        // Chart
        MyChart indexChart = new MyChart(client, series, props);
        // -------------------- Chart -------------------- //

        // ----- Charts ----- //
        MyChart[] charts = {indexChart, op_avg_all_exps_chart, indexBidAskCounterChart};

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer(client, charts, getClass().getName());
        chartContainer.create();

    }

}
