package charts.myCharts;

import charts.myChart.*;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;

import java.awt.*;

public class Index_baskets_chart extends MyChartCreator {

    // Constructor
    public Index_baskets_chart(BASE_CLIENT_OBJECT client) {
        super(client);
    }

    @Override
    public void createChart() throws CloneNotSupportedException {

        MyTimeSeries[] series;

        // Props
        props = new MyProps();
        props.setProp(ChartPropsEnum.SECONDS, INFINITE);
        props.setProp(ChartPropsEnum.IS_INCLUDE_TICKER, false);
        props.setProp(ChartPropsEnum.MARGIN, .17);
        props.setProp(ChartPropsEnum.RANGE_MARGIN, 0.0);
        props.setProp(ChartPropsEnum.IS_GRID_VISIBLE, true);
        props.setProp(ChartPropsEnum.IS_LOAD_DB, true);
        props.setProp(ChartPropsEnum.IS_LIVE, false);
        props.setProp(ChartPropsEnum.SLEEP, 1000);
        props.setProp(ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS, INFINITE);
        props.setProp(ChartPropsEnum.SECONDS_ON_MESS, INFINITE);
        props.setProp(ChartPropsEnum.INCLUDE_DOMAIN_AXIS, true);

        // --------- Index ---------- //
        MyProps newProps = (MyProps) props.clone();
        newProps.setProp(ChartPropsEnum.INCLUDE_DOMAIN_AXIS, false);

        // Index
        MyTimeSeries index = client.getIndexSeries();
        index.setStokeSize(1.5f);
        index.setColor(Color.BLACK);

        series = new MyTimeSeries[1];
        series[0] = index;

        // Chart
        MyChart indexChart = new MyChart(client, series, newProps);

        // ---------- Baskets ---------- //
        // Index
        MyTimeSeries baskets = client.getBasketFinder().getBasketsSeries();
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
