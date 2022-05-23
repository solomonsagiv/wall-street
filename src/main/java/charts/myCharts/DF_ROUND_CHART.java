package charts.myCharts;

import charts.myChart.*;
import charts.timeSeries.MyTimeSeries;
import charts.timeSeries.TimeSeriesFactory;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;

import java.awt.*;

public class DF_ROUND_CHART extends MyChartCreator {

    public static void main(String[] args) {
        DF_ROUND_CHART fullChart2 = new DF_ROUND_CHART(Spx.getInstance());
        fullChart2.createChart();
    }

    // Constructor
    public DF_ROUND_CHART(BASE_CLIENT_OBJECT client) {
        super(client, null, null);
    }

    @Override
    public void init() {
        
        MyTimeSeries[] series;

        // Props
        props = new MyProps();
        props.setProp(ChartPropsEnum.SECONDS, 1200);
        props.setProp(ChartPropsEnum.IS_INCLUDE_TICKER, -1);
        props.setProp(ChartPropsEnum.MARGIN, 0.005);
        props.setProp(ChartPropsEnum.IS_RANGE_GRID_VISIBLE, -1);
        props.setProp(ChartPropsEnum.IS_LOAD_DB, -1);
        props.setProp(ChartPropsEnum.IS_LIVE, -1);
        props.setProp(ChartPropsEnum.SLEEP, 1000);
        props.setProp(ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS, INFINITE);
        props.setProp(ChartPropsEnum.SECONDS_ON_MESS, INFINITE);
        props.setProp(ChartPropsEnum.INCLUDE_DOMAIN_AXIS, 1);
        props.setProp(ChartPropsEnum.MARKER, 0);

        // ------------------- Df 2 round -------------------- //

        // DF 2 round
        MyTimeSeries df_2_round = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_2_ROUND, client, null);

        Color color = Themes.PURPLE;

        df_2_round.setColor(color);
        df_2_round.setStokeSize(1.5f);

        series = new MyTimeSeries[1];
        series[0] = df_2_round;

        // Chart
        MyChart df_2_chart = new MyChart(client, series, props);


        // ------------------- Df 7 round -------------------- //

        // DF 2 round
        MyTimeSeries df_7_round = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_7_ROUND, client, null);
        df_7_round.setColor(Themes.BLUE_2);
        df_7_round.setStokeSize(1.5f);

        series = new MyTimeSeries[1];
        series[0] = df_7_round;

        // Chart
        MyChart df_7_chart = new MyChart(client, series, props);


        // -------------------- Chart -------------------- //

        // ----- Charts ----- //
        MyChart[] charts = {df_2_chart, df_7_chart};

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer(client, charts, getClass().getName());
        chartContainer.create();
    }

}
