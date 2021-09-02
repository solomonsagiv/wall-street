package charts.myCharts;

import charts.myChart.*;
import charts.timeSeries.MyTimeSeries;
import charts.timeSeries.TimeSeriesFactory;
import exp.Exp;
import locals.Themes;
import org.jfree.chart.plot.ValueMarker;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;

import java.awt.*;

public class Full_Charts_3 extends MyChartCreator {


    public static void main(String[] args) {
        Full_Charts_3 full_charts_3 = new Full_Charts_3(Spx.getInstance());
        full_charts_3.createChart();
    }

    // Constructor
    public Full_Charts_3(BASE_CLIENT_OBJECT client) {
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
        props.setProp(ChartPropsEnum.IS_RANGE_GRID_VISIBLE, 1);
        props.setProp(ChartPropsEnum.IS_DOMAIN_GRID_VISIBLE, 1);
        props.setProp(ChartPropsEnum.IS_LOAD_DB, 1);
        props.setProp(ChartPropsEnum.IS_LIVE, -1);
        props.setProp(ChartPropsEnum.SLEEP, 1000);
        props.setProp(ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS, INFINITE);
        props.setProp(ChartPropsEnum.SECONDS_ON_MESS, INFINITE);
        props.setProp(ChartPropsEnum.INCLUDE_DOMAIN_AXIS, 1);
        props.setProp(ChartPropsEnum.INCLUDE_RANGE_AXIS, 1);

        ValueMarker marker = new ValueMarker(0);
        marker.setPaint(Color.BLACK);
        marker.setStroke(new BasicStroke(2f));

        Exp exp = client.getExps().getExp(0);

        if (exp == null) {
            exp = client.getExps().getExpList().get(0);
        }

        // --------- OpAvgFuture 1 ---------- //
        MyTimeSeries op_avg_60_serie = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_HOUR_SERIES, client, exp);
        op_avg_60_serie.setColor(Themes.PURPLE);
        op_avg_60_serie.setStokeSize(1.5f);

        series = new MyTimeSeries[1];
        series[0] = op_avg_60_serie;

        MyChart opAvgFutureChart = new MyChart(client, series, props);

        // -------- Index bid ask counter -------- //
        MyTimeSeries indBidAskCounterSeries = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.INDEX_BID_ASK_COUNTER_SERIES, client, null);
        indBidAskCounterSeries.setColor(Themes.BINANCE_ORANGE);
        indBidAskCounterSeries.setStokeSize(1.5f);

        series = new MyTimeSeries[1];
        series[0] = indBidAskCounterSeries;

        MyChart indexBidAskCounterChart = new MyChart(client, series, props);

        // -------------------- Chart -------------------- //

        // ----- Charts ----- //
        MyChart[] charts = {opAvgFutureChart, indexBidAskCounterChart};

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer(client, charts, getClass().getName());
        chartContainer.create();

    }

}
