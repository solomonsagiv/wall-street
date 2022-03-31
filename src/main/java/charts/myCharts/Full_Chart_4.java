package charts.myCharts;

import charts.myChart.*;
import charts.timeSeries.MyTimeSeries;
import charts.timeSeries.TimeSeriesFactory;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;
import java.awt.*;

public class Full_Chart_4 extends MyChartCreator {

    public static void main(String[] args) {
        Full_Chart_4 fullChart2 = new Full_Chart_4(Spx.getInstance());
        fullChart2.createChart();
    }
    
    // Constructor
    public Full_Chart_4(BASE_CLIENT_OBJECT client) {
        super(client, null, null);
    }

    @Override
    public void init() {
        
        MyTimeSeries[] series;

        // Props
        props = new MyProps();
        props.setProp(ChartPropsEnum.SECONDS, INFINITE);
        props.setProp(ChartPropsEnum.IS_INCLUDE_TICKER, -1);
        props.setProp(ChartPropsEnum.MARGIN, 0.005);
        props.setProp(ChartPropsEnum.IS_RANGE_GRID_VISIBLE, -1);
        props.setProp(ChartPropsEnum.IS_LOAD_DB, 1);
        props.setProp(ChartPropsEnum.IS_LIVE, -1);
        props.setProp(ChartPropsEnum.SLEEP, 1000);
        props.setProp(ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS, INFINITE);
        props.setProp(ChartPropsEnum.SECONDS_ON_MESS, INFINITE);
        props.setProp(ChartPropsEnum.INCLUDE_DOMAIN_AXIS, 1);
        props.setProp(ChartPropsEnum.MARKER, 0);

        // ------------------ Op avg ------------------- //

        // --------- Op avg ---------- //
        MyTimeSeries op_avg_5 = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_DAY_5_SERIES, client, null);
        op_avg_5.setColor(Themes.RED);
        op_avg_5.setStokeSize(1.2f);

        MyTimeSeries op_avg_15 = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_DAY_15_SERIES, client, null);
        op_avg_15.setColor(Themes.GREEN);
        op_avg_15.setStokeSize(1.2f);

        MyTimeSeries op_avg_60 = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_DAY_60_SERIES, client, null);
        op_avg_60.setColor(Themes.BLUE);
        op_avg_60.setStokeSize(1.2f);

        MyTimeSeries op_avg_240 = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_240_CONTINUE, client, null);
        op_avg_240.setColor(Themes.BINANCE_ORANGE);
        op_avg_240.setStokeSize(1.2f);

        series = new MyTimeSeries[4];
        series[0] = op_avg_5;
        series[1] = op_avg_15;
        series[2] = op_avg_60;
        series[3] = op_avg_240;

        // Chart
        MyChart op_avg_chart = new MyChart(client, series, props);

        // ------------------- Index 2 -------------------- //

        // Index
        MyTimeSeries indexSeries = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.INDEX_SERIES, client, null);
        indexSeries.setColor(Color.BLACK);
        indexSeries.setStokeSize(1.5f);

        series = new MyTimeSeries[1];
        series[0] = indexSeries;

        // Chart
        MyChart indexChart = new MyChart(client, series, props);

        // ------------------- DF N AVG 1 -------------------- //

        MyProps props_2 = new MyProps();
        props_2.setProp(ChartPropsEnum.SECONDS, 3600);
        props_2.setProp(ChartPropsEnum.IS_INCLUDE_TICKER, -1);
        props_2.setProp(ChartPropsEnum.MARGIN, 0.005);
        props_2.setProp(ChartPropsEnum.IS_RANGE_GRID_VISIBLE, -1);
        props_2.setProp(ChartPropsEnum.IS_LOAD_DB, 1);
        props_2.setProp(ChartPropsEnum.IS_LIVE, -1);
        props_2.setProp(ChartPropsEnum.SLEEP, 1000);
        props_2.setProp(ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS, INFINITE);
        props_2.setProp(ChartPropsEnum.SECONDS_ON_MESS, INFINITE);
        props_2.setProp(ChartPropsEnum.INCLUDE_DOMAIN_AXIS, 1);
        props_2.setProp(ChartPropsEnum.MARKER, 0);

        // DF N 1
        MyTimeSeries df_n_avg_1 = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_N_AVG_1, client, null);
        df_n_avg_1.setColor(Themes.BLUE_2);
        df_n_avg_1.setStokeSize(1.5f);

        series = new MyTimeSeries[1];
        series[0] = df_n_avg_1;

        MyChart df_n_avg_1_chart = new MyChart(client, series, props);

        // ------------------- DF N AVG 4 -------------------- //
        // DF N 4
        MyTimeSeries df_n_avg_4 = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_N_AVG_4, client, null);
        df_n_avg_4.setColor(Themes.ORANGE);
        df_n_avg_4.setStokeSize(1.5f);

        // DF 7
        MyTimeSeries df_7 = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_7, client, null);
        df_7.setColor(Themes.PURPLE);
        df_7.setStokeSize(1.5f);

        series = new MyTimeSeries[2];
        series[0] = df_n_avg_4;
        series[1] = df_7;

        MyChart df_n_avg_4_chart = new MyChart(client, series, props);

        // -------------------- Chart -------------------- //

        // ----- Charts ----- //
        MyChart[] charts = {indexChart, op_avg_chart, df_n_avg_1_chart, df_n_avg_4_chart};

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer(client, charts, getClass().getName());
        chartContainer.create();
    }

}
