package charts.myCharts;

import charts.myChart.*;
import charts.timeSeries.MyTimeSeries;
import charts.timeSeries.TimeSeriesFactory;
import exp.Exp;
import exp.ExpStrings;
import locals.Themes;
import org.jfree.chart.plot.ValueMarker;
import serverObjects.BASE_CLIENT_OBJECT;

import java.awt.*;

public class FullCharts extends MyChartCreator {

    // Constructor
    public FullCharts(BASE_CLIENT_OBJECT client) {
        super(client);
    }

    @Override
    public void createChart() throws CloneNotSupportedException {

        MyTimeSeries[] series;

        // Props
        props = new MyProps();
        props.setProp(ChartPropsEnum.SECONDS, INFINITE);
        props.setProp(ChartPropsEnum.IS_INCLUDE_TICKER, false);
        props.setProp(ChartPropsEnum.MARGIN, 0.005);
        props.setProp(ChartPropsEnum.RANGE_MARGIN, 0.0);
        props.setProp(ChartPropsEnum.IS_GRID_VISIBLE, true);
        props.setProp(ChartPropsEnum.IS_LOAD_DB, true);
        props.setProp(ChartPropsEnum.IS_LIVE, false);
        props.setProp(ChartPropsEnum.SLEEP, 1000);
        props.setProp(ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS, INFINITE);
        props.setProp(ChartPropsEnum.SECONDS_ON_MESS, INFINITE);
        props.setProp(ChartPropsEnum.INCLUDE_DOMAIN_AXIS, true);

        // Marker
        ValueMarker marker = new ValueMarker(0);
        marker.setPaint(Color.BLACK);
        marker.setStroke(new BasicStroke(2f));

        MyProps propsWithMarker = (MyProps) props.clone();
        propsWithMarker.setProp(ChartPropsEnum.MARKER, marker);
        propsWithMarker.setProp(ChartPropsEnum.INCLUDE_DOMAIN_AXIS, false);

        Exp e1 = client.getExps().getExp(ExpStrings.q1);

        // --------- OpAvgFuture 15 ---------- //
        MyProps opAvgFutureProps = (MyProps) props.clone();
        opAvgFutureProps.setProp(ChartPropsEnum.MARKER, marker);
        opAvgFutureProps.setProp(ChartPropsEnum.INCLUDE_DOMAIN_AXIS, false);

        // Index
        MyTimeSeries opAvgFuture15 = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_15_SERIES, client, e1);
        opAvgFuture15.setColor(Themes.PURPLE);
        opAvgFuture15.setStokeSize(1.5f);

        series = new MyTimeSeries[1];
        series[0] = opAvgFuture15;

        // Chart
        MyChart opAvgFuture15Chart = new MyChart(client, series, opAvgFutureProps);

        // --------- OpAvgFuture ---------- //
        // Index
        MyTimeSeries opAvgFuture = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_SERIES, client, e1);;
        opAvgFuture.setColor(Themes.BLUE);
        opAvgFuture.setStokeSize(1.5f);

        series = new MyTimeSeries[1];
        series[0] = opAvgFuture;

        // Chart
        MyChart opAvgFutureChart = new MyChart(client, series, opAvgFutureProps);

        // --------- Index Bid Ask Counter ---------- //
        // Index
        MyTimeSeries indexBidAskCounterSeries = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.INDEX_BID_ASK_COUNTER_SERIES, client, null);
        indexBidAskCounterSeries.setColor(Themes.ORANGE);
        indexBidAskCounterSeries.setStokeSize(1.5f);

        series = new MyTimeSeries[1];
        series[0] = indexBidAskCounterSeries;

        // Chart
        MyChart indexBidAskCounterChart = new MyChart(client, series, props);

        // --------- Index 2 ---------- //
        MyProps newProps = (MyProps) props.clone();
        newProps.setProp(ChartPropsEnum.INCLUDE_DOMAIN_AXIS, false);

        // Index
        MyTimeSeries indexSeries = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.INDEX_SERIES, client, null);
        indexSeries.setColor(Color.BLACK);
        indexSeries.setStokeSize(1.5f);

        series = new MyTimeSeries[1];
        series[0] = indexSeries;

        // Chart
        MyChart indexChart = new MyChart(client, series, newProps);
        // -------------------- Chart -------------------- //

        // ----- Charts ----- //
        MyChart[] charts = {indexChart, opAvgFuture15Chart, opAvgFutureChart, indexBidAskCounterChart};

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer(client, charts, getClass().getName());
        chartContainer.create();

    }

}
