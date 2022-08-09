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
        op_avg_5.setStokeSize(1.2f);

        MyTimeSeries op_avg_60 = client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_DAY_60);
        op_avg_60.setColor(Themes.BLUE);
        op_avg_60.setStokeSize(1.2f);

        MyTimeSeries op_avg_240 = client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_240_CONTINUE);
        op_avg_240.setColor(Themes.BINANCE_ORANGE);
        op_avg_240.setStokeSize(1.2f);

        series = new MyTimeSeries[3];
        series[0] = op_avg_5;
        series[1] = op_avg_60;
        series[2] = op_avg_240;

        // Chart
        MyChart op_avg_chart = new MyChart(client, series, props);

        // ------------------- DF -------------------- //
        // DF 2
        MyTimeSeries df_2_raw = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_2_RAW);
        df_2_raw.setColor(Themes.BINANCE_ORANGE);
        df_2_raw.setStokeSize(1.5f);

        // DF 7
        MyTimeSeries df_7_raw = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_7_RAW);
        df_7_raw.setColor(Themes.PURPLE);
        df_7_raw.setStokeSize(1.5f);

        // DF 8
        MyTimeSeries df_8_raw = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_8_RAW);
        df_8_raw.setColor(Themes.RED);
        df_8_raw.setStokeSize(1.5f);

        MyTimeSeries df_8_raw_900 = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_8_RAW_900);
        df_8_raw_900.setColor(Themes.BLUE_LIGHT_2);


        series = new MyTimeSeries[4];
        series[0] = df_2_raw;
        series[1] = df_7_raw;
        series[2] = df_8_raw;
        series[3] = df_8_raw_900;

        MyChart df_s_chart = new MyChart(client, series, props);

        // -------------------- Chart -------------------- //

        // ----- Charts ----- //
        MyChart[] charts = {indexChart, op_avg_chart, df_s_chart};

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer(client, charts, getClass().getName());
        chartContainer.create();
    }

}
