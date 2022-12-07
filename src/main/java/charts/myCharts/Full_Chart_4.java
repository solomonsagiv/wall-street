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
        props.setProp(ChartPropsEnum.SLEEP, 5000);
        props.setProp(ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS, INFINITE);
        props.setProp(ChartPropsEnum.SECONDS_ON_MESS, INFINITE);
        props.setProp(ChartPropsEnum.INCLUDE_DOMAIN_AXIS, 1);
        props.setProp(ChartPropsEnum.MARKER, 0);

        // ------------------- Index -------------------- //

        // Index
        MyTimeSeries index_serie = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.INDEX, client);
        index_serie.setColor(Color.BLACK);
        index_serie.setStokeSize(1f);

        // Index
        MyTimeSeries fut_day_serie = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.FUTURE_DAY_MULTIPLY_OP, client);
        fut_day_serie.setColor(Themes.GREEN);
        fut_day_serie.setStokeSize(1f);

        series = new MyTimeSeries[2];
        series[0] = index_serie;
        series[1] = fut_day_serie;

        // Chart
        MyChart indexChart = new MyChart(client, series, props);

        // ------------------ Op avg ------------------- //

        // --------- Op avg ---------- //
        MyTimeSeries op_avg_5 = client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_DAY_5);
        op_avg_5.setColor(Themes.RED);
        op_avg_5.setVisible(false);
        op_avg_5.setStokeSize(1.2f);

        MyTimeSeries op_avg_15 = client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_DAY_15);
        op_avg_15.setColor(Themes.GREEN);
        op_avg_15.setStokeSize(1.2f);

        MyTimeSeries op_avg_60 = client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_DAY_60);
        op_avg_60.setColor(Themes.BLUE);
        op_avg_60.setStokeSize(1.2f);

        MyTimeSeries op_avg_240 = client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_240_CONTINUE);
        op_avg_240.setColor(Themes.BINANCE_ORANGE);
        op_avg_240.setStokeSize(1.2f);

        series = new MyTimeSeries[4];
        series[0] = op_avg_5;
        series[1] = op_avg_15;
        series[2] = op_avg_60;
        series[3] = op_avg_240;
        
        // Chart
        MyChart op_avg_chart = new MyChart(client, series, props);

        // ------------------ DF ------------------- //

//        MyTimeSeries df_8_900 = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_8_RAW_900);
//        df_8_900.setColor(Themes.GREEN);
//        df_8_900.setStokeSize(1.2f);
//
//        MyTimeSeries df_8_3600 = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_8_RAW_3600);
//        df_8_3600.setColor(Themes.BLUE);
//        df_8_3600.setStokeSize(1.2f);
//
//        series = new MyTimeSeries[2];
//        series[0] = df_8_900;
//        series[1] = df_8_3600;
//
//        // Chart
//        MyChart df_chart = new MyChart(client, series, props);
//
        // -------------------- Chart -------------------- //

        // ----- Charts ----- //
        MyChart[] charts = {indexChart, op_avg_chart};

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer(client, charts, getClass().getName());
        chartContainer.create();
    }

}
