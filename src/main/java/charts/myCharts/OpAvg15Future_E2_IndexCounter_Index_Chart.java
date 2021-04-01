package charts.myCharts;

import charts.myChart.*;
import exp.Exp;
import exp.ExpStrings;
import locals.Themes;
import org.jfree.chart.plot.ValueMarker;
import serverObjects.BASE_CLIENT_OBJECT;

import java.awt.*;

public class OpAvg15Future_E2_IndexCounter_Index_Chart extends MyChartCreator {

    // Constructor
    public OpAvg15Future_E2_IndexCounter_Index_Chart(BASE_CLIENT_OBJECT client) {
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

        MyProps props_2 = (MyProps) props.clone();
        ValueMarker marker = new ValueMarker(0);
        marker.setPaint(Color.BLACK);
        marker.setStroke(new BasicStroke(2f));
        props_2.setProp(ChartPropsEnum.MARKER, marker);
        props_2.setProp(ChartPropsEnum.INCLUDE_DOMAIN_AXIS, false);

        MyProps props_3 = (MyProps) props.clone();
        props_3.setProp(ChartPropsEnum.INCLUDE_DOMAIN_AXIS, false);

        Exp e1 = client.getExps().getExp(ExpStrings.q1);

        // --------- OpAvgFuture 1 ---------- //
        MyTimeSeries opAvgFuture = e1.getOp_avg_15_serie();
        opAvgFuture.setColor(Themes.PURPLE);
        opAvgFuture.setStokeSize(1.5f);

        series = new MyTimeSeries[1];
        series[0] = opAvgFuture;

        MyChart opAvgFutureChart = new MyChart(client, series, props_2);

        // --------- Index ---------- //
        MyTimeSeries indexSeries = client.getIndexSeries();
        indexSeries.setColor(Color.BLACK);
        indexSeries.setStokeSize(1.5f);

        series = new MyTimeSeries[1];
        series[0] = indexSeries;

        MyChart indexChart = new MyChart(client, series, props_3);


        // --------- Index races counter ---------- //
        MyTimeSeries indRacesSeries = client.getIndexRacesSeries();
        indRacesSeries.setColor(Themes.BROWN);
        indRacesSeries.setStokeSize(1.5f);

        series = new MyTimeSeries[1];
        series[0] = indRacesSeries;

        MyChart indRacesChart = new MyChart(client, series, props_3);

        // -------- Index bid ask counter -------- //
        MyTimeSeries indBidAskCounterSeries = client.getIndexBidAskCounterSeries();
        indBidAskCounterSeries.setColor(Themes.BINANCE_ORANGE);
        indBidAskCounterSeries.setStokeSize(1.5f);

        series = new MyTimeSeries[1];
        series[0] = indBidAskCounterSeries;

        MyChart indexBidAskCounterChart = new MyChart(client, series, props);

        // -------------------- Chart -------------------- //

        // ----- Charts ----- //
        MyChart[] charts = {indexChart, opAvgFutureChart, indRacesChart, indexBidAskCounterChart,};

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer(client, charts, getClass().getName());
        chartContainer.create();

    }

}
