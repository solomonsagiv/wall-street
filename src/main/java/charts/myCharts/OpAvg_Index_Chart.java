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
        props.setProp(ChartPropsEnum.SLEEP, 1000);
        props.setProp(ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS, INFINITE);
        props.setProp(ChartPropsEnum.SECONDS_ON_MESS, INFINITE);
        props.setProp(ChartPropsEnum.INCLUDE_DOMAIN_AXIS, 1);

        // Marker
        ValueMarker marker = new ValueMarker(0);
        marker.setPaint(Color.BLACK);
        marker.setStroke(new BasicStroke(2f));

        // Exp
        Exp day_exp = client.getExps().getExp(ExpStrings.day);

        // --------- Op avg ---------- //
        MyTimeSeries op_avg_1 = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_1_SERIES, client, day_exp);
        op_avg_1.setColor(Themes.PINK_LIGHT);
        op_avg_1.setStokeSize(1.2f);

        MyTimeSeries op_avg_5 = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_5_SERIES, client, day_exp);
        op_avg_5.setColor(Themes.RED_2);
        op_avg_5.setStokeSize(1.2f);

        MyTimeSeries op_avg_15 = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_15_SERIES, client, day_exp);
        op_avg_15.setColor(Themes.GREEN);
        op_avg_15.setStokeSize(1.2f);

        MyTimeSeries op_avg_60 = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_HOUR_SERIES, client, day_exp);
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
        MyTimeSeries indexSeries = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.INDEX_SERIES, client, null);
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
