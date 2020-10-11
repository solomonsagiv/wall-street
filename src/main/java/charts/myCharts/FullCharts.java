package charts.myCharts;

import charts.myChart.*;
import exp.E;
import exp.Exp;
import exp.ExpStrings;
import locals.Themes;
import org.jfree.chart.plot.ValueMarker;
import serverObjects.BASE_CLIENT_OBJECT;

import java.awt.*;

public class FullCharts extends MyChartCreator {

    // Constructor
    public FullCharts( BASE_CLIENT_OBJECT client) {
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

        Exp e1 = client.getExps().getExp(ExpStrings.e1);

        // ---------- EDelta ---------- //
        // Index
        MyTimeSeries deltaSerie = (( E )e1).getDeltaSerie();
        deltaSerie.setColor(Themes.GREEN);
        deltaSerie.setStokeSize(1.5f);

        series = new MyTimeSeries[1];
        series[0] = deltaSerie;

        // Chart
        MyChart deltaChart = new MyChart(client, series, propsWithMarker);

        // --------- OpAvgFuture 15 ---------- //
        MyProps opAvgFutureProps = (MyProps) props.clone();
        opAvgFutureProps.setProp(ChartPropsEnum.MARKER, marker);
        opAvgFutureProps.setProp(ChartPropsEnum.INCLUDE_DOMAIN_AXIS, false);

        // Index
        MyTimeSeries opAvgFuture15 = e1.getOpAvg15FutSeries();
        opAvgFuture15.setColor(Themes.PURPLE);
        opAvgFuture15.setStokeSize(1.5f);

        series = new MyTimeSeries[1];
        series[0] = opAvgFuture15;

        // Chart
        MyChart opAvgFuture15Chart = new MyChart(client, series, opAvgFutureProps);

        // --------- OpAvgFuture ---------- //
        // Index
        MyTimeSeries opAvgFuture = e1.getOpAvgFutSeries();
        opAvgFuture.setColor(Themes.BLUE);
        opAvgFuture.setStokeSize(1.5f);

        series = new MyTimeSeries[1];
        series[0] = opAvgFuture;

        // Chart
        MyChart opAvgFutureChart = new MyChart(client, series, opAvgFutureProps);

        // --------- Index Bid Ask Counter ---------- //
        // Index
        MyTimeSeries indexBidAskCounterSeries = client.getIndexBidAskCounterSeries();
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
        MyTimeSeries indexSeries = client.getIndexSeries();
        indexSeries.setColor(Color.BLACK);
        indexSeries.setStokeSize(1.5f);

        series = new MyTimeSeries[1];
        series[0] = indexSeries;

        // Chart
        MyChart indexChart = new MyChart(client, series, newProps);
        // -------------------- Chart -------------------- //

        // ----- Charts ----- //
        MyChart[] charts = {indexChart, deltaChart, opAvgFuture15Chart, opAvgFutureChart, indexBidAskCounterChart};

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer(client, charts, getClass().getName());
        chartContainer.create();

    }

}
