package charts.myCharts;

import charts.myChart.*;
import charts.timeSeries.MyTimeSeries;
import charts.timeSeries.TimeSeriesFactory;
import exp.Exp;
import exp.ExpStrings;
import locals.Themes;
import org.jfree.chart.plot.ValueMarker;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;
import java.awt.*;

public class Full_Chart_2 extends MyChartCreator {

    public static void main(String[] args) {
        Full_Chart_2 fullChart2 = new Full_Chart_2(Spx.getInstance());
        fullChart2.createChart();
    }

    // Constructor
    public Full_Chart_2(BASE_CLIENT_OBJECT client) {
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
        props.setProp(ChartPropsEnum.SLEEP, 1000);
        props.setProp(ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS, INFINITE);
        props.setProp(ChartPropsEnum.SECONDS_ON_MESS, INFINITE);
        props.setProp(ChartPropsEnum.INCLUDE_DOMAIN_AXIS, 1);

        // Marker
        ValueMarker marker = new ValueMarker(0);
        marker.setPaint(Color.BLACK);
        marker.setStroke(new BasicStroke(2f));

        // --------- Op avg ---------- //

        int size = client.getExps().getExpList().size() * 2;
        series = new MyTimeSeries[size];

        int i = 0;
        // For each exp
        for (Exp exp : client.getExps().getExpList()) {
            // Index
            MyTimeSeries op_avg_60 = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_HOUR_SERIES, client, exp);
            op_avg_60.setColor(Themes.BLUE);
            op_avg_60.setStokeSize(1.2f);

            // Index
            MyTimeSeries op_avg_15 = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_15_SERIES, client, exp);
            op_avg_15.setColor(Themes.PINK_LIGHT);
            op_avg_15.setStokeSize(1.2f);

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
        // 5
        MyTimeSeries counter_avg_5 = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.INDEX_BID_ASK_COUNTER_SERIES_5, client, null);
        counter_avg_5.setColor(Themes.PURPLE);
        counter_avg_5.setStokeSize(1f);

        // 15
        MyTimeSeries counter_avg_15 = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.INDEX_BID_ASK_COUNTER_SERIES_15, client, null);
        counter_avg_15.setColor(Themes.GREEN);
        counter_avg_15.setStokeSize(1f);

        // 45
        MyTimeSeries counter_avg_45 = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.INDEX_BID_ASK_COUNTER_SERIES_45, client, null);
        counter_avg_45.setColor(Themes.BROWN);
        counter_avg_45.setStokeSize(1f);

        // Day
        MyTimeSeries counter_avg_day = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.INDEX_BID_ASK_COUNTER_SERIES_DAY, client, null);
        counter_avg_day.setColor(Themes.RED_2);
        counter_avg_day.setStokeSize(1f);

        // Cunter
        MyTimeSeries counter = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.INDEX_BID_ASK_COUNTER_SERIES, client, null);
        counter.setColor(Themes.BINANCE_ORANGE);
        counter.setStokeSize(1f);

        series = new MyTimeSeries[5];
        series[0] = counter_avg_5;
        series[1] = counter_avg_15;
        series[2] = counter_avg_45;
        series[3] = counter_avg_day;
        series[4] = counter;

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

        // --------- Delta  ---------- //

        // Delta
        MyTimeSeries deltaSeries = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.FUTURE_DELTA, client, client.getExps().getExp(ExpStrings.q1));
        deltaSeries.setColor(Themes.LIFGT_BLUE_2);
        deltaSeries.setStokeSize(1.5f);

        series = new MyTimeSeries[1];
        series[0] = deltaSeries;

        // Chart
        MyChart deltaChart = new MyChart(client, series, props);

        // -------------------- Chart -------------------- //

        // ----- Charts ----- //
        MyChart[] charts = {indexChart, op_avg_all_exps_chart, deltaChart, indexBidAskCounterChart};

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer(client, charts, getClass().getName());
        chartContainer.create();
    }

}
