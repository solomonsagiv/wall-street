package charts.myCharts;

import charts.myChart.*;
import charts.timeSeries.MyTimeSeries;
import charts.timeSeries.TimeSeriesFactory;
import exp.ExpStrings;
import locals.Themes;
import org.jfree.chart.plot.ValueMarker;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;

import java.awt.*;

public class Delta_Index_1800_Chart extends MyChartCreator {

    // Constructor
    public Delta_Index_1800_Chart(BASE_CLIENT_OBJECT client) {
        super(client, null, null);
    }

    @Override
    public void init() {

        MyTimeSeries[] series;

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

        // Marker
        ValueMarker marker = new ValueMarker(0);
        marker.setPaint(Themes.GREY_2);
        marker.setStroke(new BasicStroke(2f));

        // --------------------------- Index -------------------------------- //

        // Index
        MyTimeSeries indexSeries = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.INDEX_SERIES, client, null);
        indexSeries.setColor(Color.BLACK);
        indexSeries.setStokeSize(1.5f);

        series = new MyTimeSeries[1];
        series[0] = indexSeries;

        // Chart
        MyChart indexChart = new MyChart(client, series, props);

        // ----------------------------- Delta  ---------------------------- //

        // Delta
        MyTimeSeries deltaSeries = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.FUTURE_DELTA, client, client.getExps().getExp(ExpStrings.q1));
        deltaSeries.setColor(Themes.LIFGT_BLUE_2);
        deltaSeries.setStokeSize(1.5f);

        series = new MyTimeSeries[1];
        series[0] = deltaSeries;

        // Chart
        MyChart deltaChart = new MyChart(client, series, props);

        // ------------------------------ Chart ----------------------------- //

        // ----- Charts ----- //
        MyChart[] charts = {indexChart, deltaChart};

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer(client, charts, getClass().getName());
        chartContainer.create();
    }

}
