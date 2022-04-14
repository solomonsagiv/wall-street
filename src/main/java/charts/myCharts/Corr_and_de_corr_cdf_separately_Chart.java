package charts.myCharts;

import charts.myChart.*;
import charts.timeSeries.MyTimeSeries;
import charts.timeSeries.TimeSeriesFactory;
import org.jfree.chart.plot.ValueMarker;
import serverObjects.BASE_CLIENT_OBJECT;

import java.awt.*;

public class Corr_and_de_corr_cdf_separately_Chart extends MyChartCreator {

    // Constructor
    public Corr_and_de_corr_cdf_separately_Chart(BASE_CLIENT_OBJECT client) {
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
        props.setProp(ChartPropsEnum.SLEEP, 10000);
        props.setProp(ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS, INFINITE);
        props.setProp(ChartPropsEnum.SECONDS_ON_MESS, INFINITE);
        props.setProp(ChartPropsEnum.INCLUDE_DOMAIN_AXIS, 1);

        // Marker
        ValueMarker marker = new ValueMarker(0);
        marker.setPaint(Color.BLACK);
        marker.setStroke(new BasicStroke(2f));

        // ------------------- Index -------------------- //
        // Index
        MyTimeSeries indexSeries = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.INDEX_SERIES, client, null);
        indexSeries.setColor(Color.BLACK);
        indexSeries.setStokeSize(1.5f);

        series = new MyTimeSeries[1];
        series[0] = indexSeries;

        // Chart
        MyChart indexChart = new MyChart(client, series, props);

        // ------------------- Curr and de curr -------------------- //
        // Curr
        MyTimeSeries curr_series = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.CORR_15, client, null);
        curr_series.setColor(Color.BLUE);
        curr_series.setStokeSize(1.5f);

        series = new MyTimeSeries[1];
        series[0] = curr_series;

        // Chart
        MyChart curr_chart = new MyChart(client, series, props);

        // ------------------- DE curr -------------------- //
        // DE Curr
        MyTimeSeries de_curr_series = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DE_CORR_MIX_CDF, client, null);
        de_curr_series.setColor(Color.ORANGE);
        de_curr_series.setStokeSize(1.5f);

        series = new MyTimeSeries[1];
        series[0] = de_curr_series;

        // Chart
        MyChart de_curr_chart = new MyChart(client, series, props);


        // ----- Charts ----- //
        MyChart[] charts = {indexChart, curr_chart, de_curr_chart};

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer(client, charts, getClass().getName());
        chartContainer.create();
    }

}