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

public class Full_Charts extends MyChartCreator {

    // Constructor
    public Full_Charts(BASE_CLIENT_OBJECT client) {
        super(client, null, null);
    }

    @Override
    public void createChart() {

        MyTimeSeries[] series;

        // Props
        props = new MyProps();
        props.setProp(ChartPropsEnum.SECONDS, INFINITE);
        props.setProp(ChartPropsEnum.IS_INCLUDE_TICKER, -1);
        props.setProp(ChartPropsEnum.MARGIN, 0.005);
        props.setProp(ChartPropsEnum.RANGE_MARGIN, 0.0);
        props.setProp(ChartPropsEnum.IS_GRID_VISIBLE, 1);
        props.setProp(ChartPropsEnum.IS_LOAD_DB, 1);
        props.setProp(ChartPropsEnum.IS_LIVE, -1);
        props.setProp(ChartPropsEnum.SLEEP, 1000);
        props.setProp(ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS, INFINITE);
        props.setProp(ChartPropsEnum.SECONDS_ON_MESS, INFINITE);
        props.setProp(ChartPropsEnum.INCLUDE_DOMAIN_AXIS, 1);

        ValueMarker marker = new ValueMarker(0);
        marker.setPaint(Color.BLACK);
        marker.setStroke(new BasicStroke(2f));

        Exp e1 = client.getExps().getExp(ExpStrings.q1);

        // --------- OpAvgFuture 1 ---------- //
        MyTimeSeries op_avg_15_serie = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_15_SERIES, client, e1);
        op_avg_15_serie.setColor(Themes.PURPLE);
        op_avg_15_serie.setStokeSize(1.5f);

        series = new MyTimeSeries[1];
        series[0] = op_avg_15_serie;

        MyChart opAvgFutureChart = new MyChart(client, series, props);

        // --------- Index ---------- //
        MyTimeSeries indexSeries = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.INDEX_SERIES, client, null);
        indexSeries.setColor(Color.BLACK);
        indexSeries.setStokeSize(1.5f);

        series = new MyTimeSeries[1];
        series[0] = indexSeries;

        MyChart indexChart = new MyChart(client, series, props);

        // --------- Future bid ask counter ---------- //
        MyTimeSeries future_counter_series = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.FUTURE_BID_ASK_COUNTER, client, null);
        indexSeries.setColor(Themes.GREEN);
        indexSeries.setStokeSize(1.5f);

        series = new MyTimeSeries[1];
        series[0] = future_counter_series;

        MyChart future_counter_Chart = new MyChart(client, series, props);

        // -------- Index bid ask counter -------- //
        MyTimeSeries indBidAskCounterSeries = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.INDEX_BID_ASK_COUNTER_SERIES, client, null);
        indBidAskCounterSeries.setColor(Themes.BINANCE_ORANGE);
        indBidAskCounterSeries.setStokeSize(1.5f);

        series = new MyTimeSeries[1];
        series[0] = indBidAskCounterSeries;

        MyChart indexBidAskCounterChart = new MyChart(client, series, props);

        // -------------------- Chart -------------------- //

        // ----- Charts ----- //
        MyChart[] charts = {indexChart, opAvgFutureChart, future_counter_Chart, indexBidAskCounterChart};

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer(client, charts, getClass().getName());
        chartContainer.create();


    }

}
