package charts.myCharts;

import charts.myChart.*;
import charts.timeSeries.MyTimeSeries;
import charts.timeSeries.TimeSeriesFactory;
import org.jfree.chart.plot.ValueMarker;
import serverObjects.BASE_CLIENT_OBJECT;

import java.awt.*;

public class Corr_and_de_corr_Chart extends MyChartCreator {

    // Constructor
    public Corr_and_de_corr_Chart(BASE_CLIENT_OBJECT client) {
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

        // ------------------- Corr and de corr -------------------- //
        // Corr
        MyTimeSeries corr_series = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.CORR_MIX, client, null);
        corr_series.setColor(Color.BLUE);
        corr_series.setStokeSize(1.2f);

        // DE Curr
        MyTimeSeries de_corr_series = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DE_CORR_MIX, client, null);
        de_corr_series.setColor(Color.ORANGE);
        de_corr_series.setStokeSize(1.2f);

        series = new MyTimeSeries[2];
        series[0] = corr_series;
        series[1] = de_corr_series;

        // Chart
        MyChart curr_and_de_curr_chart = new MyChart(client, series, props);


        // ----- Charts ----- //
        MyChart[] charts = {curr_and_de_curr_chart};

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer(client, charts, getClass().getName());
        chartContainer.create();
    }

}
