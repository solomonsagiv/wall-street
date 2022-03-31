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


        // ------------------- Index -------------------- //

        // Index
        MyTimeSeries indexSeries = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.INDEX_SERIES, client, null);
        indexSeries.setColor(Color.BLACK);
        indexSeries.setStokeSize(1.5f);

        series = new MyTimeSeries[1];
        series[0] = indexSeries;

        // Chart
        MyChart indexChart = new MyChart(client, series, props);


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


        // ------------------- De corr -------------------- //

        // De corr 3600
        MyTimeSeries de_corr_3600 = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DE_CORR_60, client, null);
        de_corr_3600.setColor(Themes.BLUE);
        de_corr_3600.setStokeSize(1.5f);

        // De corr 3600
        MyTimeSeries de_corr_900 = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DE_CORR_15, client, null);
        de_corr_900.setColor(Themes.GREEN);
        de_corr_900.setStokeSize(1.5f);

        series = new MyTimeSeries[2];
        series[0] = de_corr_3600;
        series[1] = de_corr_900;

        // Chart
        MyChart de_corr_chart = new MyChart(client, series, props);

        
        // ------------------- DF N AVG 4 -------------------- //
        // DF 3
        MyTimeSeries df_3 = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_3, client, null);
        df_3.setColor(Themes.ORANGE);
        df_3.setStokeSize(1.5f);

        // DF 7
        MyTimeSeries df_7 = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_7, client, null);
        df_7.setColor(Themes.PURPLE);
        df_7.setStokeSize(1.5f);

        series = new MyTimeSeries[2];
        series[0] = df_3;
        series[1] = df_7;

        MyChart df_s_chart = new MyChart(client, series, props);

        // -------------------- Chart -------------------- //

        // ----- Charts ----- //
        MyChart[] charts = {indexChart, op_avg_chart, de_corr_chart, df_s_chart};

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer(client, charts, getClass().getName());
        chartContainer.create();
    }

}
