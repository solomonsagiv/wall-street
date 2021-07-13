package charts.myCharts;

import charts.myChart.*;
import charts.timeSeries.MyTimeSeries;
import charts.timeSeries.TimeSeriesFactory;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;

import java.awt.*;

public class IndexCounter_Index_Chart extends MyChartCreator {

    // Constructor
    public IndexCounter_Index_Chart(BASE_CLIENT_OBJECT client) {
        super(client, null, "Index_counter");
        init();
    }

    @Override
    public void init() {

        MyTimeSeries[] series;

        // Props
        props = new MyProps();
        props.setProp(ChartPropsEnum.SECONDS, INFINITE);
        props.setProp(ChartPropsEnum.IS_INCLUDE_TICKER, -1);
        props.setProp(ChartPropsEnum.MARGIN, .17);
        props.setProp(ChartPropsEnum.RANGE_MARGIN, 0.0);
        props.setProp(ChartPropsEnum.IS_RANGE_GRID_VISIBLE, 1);
        props.setProp(ChartPropsEnum.IS_LOAD_DB, 1);
        props.setProp(ChartPropsEnum.IS_LIVE, -1);
        props.setProp(ChartPropsEnum.SLEEP, 1000);
        props.setProp(ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS, INFINITE);
        props.setProp(ChartPropsEnum.SECONDS_ON_MESS, INFINITE);
        props.setProp(ChartPropsEnum.INCLUDE_DOMAIN_AXIS, 1);

        // --------- Chart 1 ---------- //
//        MyProps newProps = (MyProps) props.clone();
//        newProps.setProp(ChartPropsEnum.INCLUDE_DOMAIN_AXIS, -1);

        // Index
        MyTimeSeries index = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.INDEX_SERIES, client, null);
        index.setStokeSize(1.5f);
        index.setColor(Color.BLACK);

        series = new MyTimeSeries[1];
        series[0] = index;

        // Chart
        MyChart indexChart = new MyChart(client, series, props);

        // ---------- Chart 3 ---------- //
        // Index
        MyTimeSeries indexBidAskCounter = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.INDEX_BID_ASK_COUNTER_SERIES, client, null);
        indexBidAskCounter.setColor(Themes.ORANGE);
        indexBidAskCounter.setStokeSize(1.5f);

        series = new MyTimeSeries[1];
        series[0] = indexBidAskCounter;

        MyChart indexCounterChart = new MyChart(client, series, props);

        // ----- Charts ----- //
        charts_arr = new MyChart[]{indexChart, indexCounterChart};
    }

}
