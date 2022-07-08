package charts.myCharts;

import charts.myChart.*;
import charts.timeSeries.MyTimeSeries;
import charts.timeSeries.TimeSeriesFactory;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Ndx;
import serverObjects.indexObjects.Spx;

import java.awt.*;

public class Index_baskets_short_chart extends MyChartCreator {

    public static void main(String[] args) {
        Index_baskets_short_chart index_baskets_short_chart = new Index_baskets_short_chart(Spx.getInstance());
        index_baskets_short_chart.createChart();
    }

    // Constructor
    public Index_baskets_short_chart(BASE_CLIENT_OBJECT client) {
        super(client, null, null);
    }

    @Override
    public void init() {

        MyTimeSeries[] series;

        // Props
        props = new MyProps();
        props.setProp(ChartPropsEnum.SECONDS, 1800);
        props.setProp(ChartPropsEnum.IS_INCLUDE_TICKER, -1);
        props.setProp(ChartPropsEnum.MARGIN, .17);
        props.setProp(ChartPropsEnum.IS_RANGE_GRID_VISIBLE, 1);
        props.setProp(ChartPropsEnum.IS_LOAD_DB, -1);
        props.setProp(ChartPropsEnum.IS_LIVE, -1);
        props.setProp(ChartPropsEnum.SLEEP, 1000);
        props.setProp(ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS, INFINITE);
        props.setProp(ChartPropsEnum.SECONDS_ON_MESS, INFINITE);
        props.setProp(ChartPropsEnum.INCLUDE_DOMAIN_AXIS, 1);
        props.setProp(ChartPropsEnum.MARKER, 0);

        // --------- Index ---------- //
        // Spx df 7 raw
        MyTimeSeries ndx_index = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.INDEX, Ndx.getInstance());
        ndx_index.setStokeSize(1.5f);
        ndx_index.setColor(Color.BLACK);

        series = new MyTimeSeries[1];
        series[0] = ndx_index;
        // Chart
        MyChart indexChart = new MyChart(client, series, props);

        // Spx df 7 raw
        MyTimeSeries spx_df_7 = Spx.getInstance().getTimeSeriesHandler().get(TimeSeriesFactory.DF_7_RAW);
        spx_df_7.setStokeSize(1.5f);
        spx_df_7.setColor(Themes.BINANCE_ORANGE);

        // Ndx df 7 raw
        MyTimeSeries ndx_df_7 = Ndx.getInstance().getTimeSeriesHandler().get(TimeSeriesFactory.DF_7_RAW);
        ndx_df_7.setStokeSize(1.5f);
        ndx_df_7.setColor(Themes.BLUE);

        series = new MyTimeSeries[2];
        series[0] = ndx_df_7;
        series[1] = spx_df_7;

        // Chart
        MyChart df_chart= new MyChart(client, series, props);

        // ---------- Baskets ---------- //
        // Index
        MyTimeSeries baskets = client.getTimeSeriesHandler().get(TimeSeriesFactory.BASKETS_CDF);
        baskets.setColor(Themes.PURPLE);
        baskets.setStokeSize(1.5f);

        series = new MyTimeSeries[1];
        series[0] = baskets;

        MyChart basketsChart = new MyChart(client, series, props);

        // ----- Charts ----- //
        MyChart[] charts = {indexChart, basketsChart, df_chart};

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer(client, charts, getClass().getName());
        chartContainer.create();

    }

}
