package charts.myCharts;

import charts.myChart.*;
import charts.timeSeries.MyTimeSeries;
import charts.timeSeries.TimeSeriesFactory;
import org.jfree.chart.plot.ValueMarker;
import serverObjects.BASE_CLIENT_OBJECT;

import java.awt.*;

public class Curr_and_de_curr_Chart extends MyChartCreator {

    // Constructor
    public Curr_and_de_curr_Chart(BASE_CLIENT_OBJECT client) {
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
        MyTimeSeries curr_series = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.CURR_MIX_W, client, null);
        curr_series.setColor(Color.BLUE);
        curr_series.setStokeSize(1.5f);

        // DE Curr
        MyTimeSeries de_curr_series = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DE_CURR_MIX_W, client, null);
        de_curr_series.setColor(Color.ORANGE);
        de_curr_series.setStokeSize(1.5f);

        series = new MyTimeSeries[2];
        series[0] = curr_series;
        series[1] = de_curr_series;

        // Chart
        MyChart curr_and_de_curr_chart = new MyChart(client, series, props);


        // ----- Charts ----- //
        MyChart[] charts = {indexChart, curr_and_de_curr_chart};

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer(client, charts, getClass().getName());
        chartContainer.create();
    }

}
