package charts.myCharts;

import charts.myChart.*;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;

import java.awt.*;

public class IndexCounter_Index_Chart extends MyChartCreator {

    // Constructor
    public IndexCounter_Index_Chart(BASE_CLIENT_OBJECT client) {
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

        // --------- Chart 1 ---------- //
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

        // ---------- Chart 3 ---------- //
        // Index
        MyTimeSeries indexBidAskCounter = client.getIndexBidAskCounterSeries();
        indexBidAskCounter.setColor(Themes.ORANGE);
        indexBidAskCounter.setStokeSize(1.5f);

        series = new MyTimeSeries[1];
        series[0] = indexBidAskCounter;

        MyChart indexCounterChart = new MyChart(client, series, props);

        // ----- Charts ----- //
        MyChart[] charts = {indexChart, indexCounterChart};

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer(client, charts, getClass().getName());
        chartContainer.create();

    }

}
