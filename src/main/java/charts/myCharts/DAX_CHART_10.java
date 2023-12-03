package charts.myCharts;

import charts.myChart.*;
import charts.timeSeries.MyTimeSeries;
import charts.timeSeries.TimeSeriesFactory;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;

import java.awt.*;

public class DAX_CHART_10 extends MyChartCreator {

    public static void main(String[] args) {
        DAX_CHART_10 fullChart2 = new DAX_CHART_10(Spx.getInstance());
        fullChart2.createChart();
    }

    // Constructor
    public DAX_CHART_10(BASE_CLIENT_OBJECT client) {
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


        // Index avg 3600
        MyTimeSeries index_avg_3600_serie = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.INDEX_AVG_3600,client);

        index_avg_3600_serie.setColor(Themes.PURPLE);
        index_avg_3600_serie.setStokeSize(0.75f);

        // Index avg 900
        MyTimeSeries index_avg_900_serie = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.INDEX_AVG_900,client);

        index_avg_900_serie.setColor(Themes.RED);
        index_avg_900_serie.setStokeSize(0.75f);

        // Index
        series = new MyTimeSeries[3];
        series[0] = index_serie;
        series[1] = index_avg_900_serie;
        series[2] = index_avg_3600_serie;

        // Chart
        MyChart indexChart = new MyChart(client, series, props);

        // ------------------ Roll ------------------- //
        MyTimeSeries roll_900 = client.getTimeSeriesHandler().get(TimeSeriesFactory.ROLL_WEEK_MONTH_900);
        roll_900.setColor(Themes.LIGHT_RED);
        roll_900.setStokeSize(0.75f);

        MyTimeSeries roll_3600 = client.getTimeSeriesHandler().get(TimeSeriesFactory.ROLL_WEEK_MONTH_3600);
        roll_3600.setColor(Themes.PURPLE);
        roll_3600.setStokeSize(1.2f);

        series = new MyTimeSeries[2];
        series[0] = roll_900;
        series[1] = roll_3600;

        // Chart
        MyChart roll_chart = new MyChart(client, series, props);

        // ------------------ Op avg ------------------- //

        // --------- Dax Op avg ---------- //
        MyTimeSeries op_avg_day_15 = client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_WEEK_900);
        op_avg_day_15.setColor(Themes.LIGHT_RED);
        op_avg_day_15.setStokeSize(0.75f);

        MyTimeSeries op_avg_day_60 = client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_WEEK_3600);
        op_avg_day_60.setColor(Themes.BLUE);
        op_avg_day_60.setStokeSize(1.2f);

        series = new MyTimeSeries[2];
        series[0] = op_avg_day_15;
        series[1] = op_avg_day_60;

        // Chart
        MyChart dax_op_avg_day_chart = new MyChart(client, series, props);


        // --------- Dax DFs ---------- //

        MyTimeSeries df_2 = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_2_CDF);
        df_2.setColor(Themes.BINANCE_ORANGE);
        df_2.setStokeSize(1.0f);

        MyTimeSeries df_2_roll = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_2_ROLL_CDF);
        df_2_roll.setColor(Themes.LIFGT_BLUE_2);
        df_2_roll.setStokeSize(1.0f);

        series = new MyTimeSeries[2];
        series[0] = df_2;
        series[1] = df_2_roll;

        // Chart
        MyChart df_chart = new MyChart(client, series, props);

        // -------------------- Chart -------------------- //

        // ----- Charts ----- //
        MyChart[] charts = {indexChart, dax_op_avg_day_chart, roll_chart, df_chart};

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer(client, charts, getClass().getName());
        chartContainer.create();
    }

}
