package charts.myCharts;

import charts.myChart.*;
import charts.timeSeries.MyTimeSeries;
import charts.timeSeries.TimeSeriesFactory;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;

import java.awt.*;

public class Index_baskets_chart extends MyChartCreator {

    // Constructor
    public Index_baskets_chart(BASE_CLIENT_OBJECT client) {
        super(client, null, null);
    }

    @Override
    public void init() {

        MyTimeSeries[] series;

        // Props
        props = new MyProps();
        props.setProp(ChartPropsEnum.SECONDS, INFINITE);
        props.setProp(ChartPropsEnum.IS_INCLUDE_TICKER, -1);
        props.setProp(ChartPropsEnum.MARGIN, .17);
        props.setProp(ChartPropsEnum.IS_RANGE_GRID_VISIBLE, 1);
        props.setProp(ChartPropsEnum.IS_LOAD_DB, 1);
        props.setProp(ChartPropsEnum.IS_LIVE, -1);
        props.setProp(ChartPropsEnum.SLEEP, 1000);
        props.setProp(ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS, INFINITE);
        props.setProp(ChartPropsEnum.SECONDS_ON_MESS, INFINITE);
        props.setProp(ChartPropsEnum.INCLUDE_DOMAIN_AXIS, 1);

        // --------- Index ---------- //

        // Index
        MyTimeSeries index = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.INDEX, client);
        index.setStokeSize(1.5f);
        index.setColor(Color.BLACK);

        series = new MyTimeSeries[1];
        series[0] = index;

        // Chart
        MyChart indexChart = new MyChart(client, series, props);

        // ---------- Baskets ---------- //
        // Index
        MyTimeSeries baskets = client.getTimeSeriesHandler().get(TimeSeriesFactory.BASKETS_CDF);
        baskets.setColor(Themes.PURPLE);
        baskets.setStokeSize(1.5f);

        series = new MyTimeSeries[1];
        series[0] = baskets;

        MyChart basketsChart = new MyChart(client, series, props);

        // ----- Charts ----- //
        MyChart[] charts = {indexChart, basketsChart};

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer(client, charts, getClass().getName());
        chartContainer.create();

    }

}
