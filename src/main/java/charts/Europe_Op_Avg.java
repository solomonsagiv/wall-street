package charts;

import charts.myChart.*;
import charts.timeSeries.MyTimeSeries;
import charts.timeSeries.TimeSeriesFactory;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;

import java.awt.*;

public class Europe_Op_Avg extends MyChartCreator {

    public static void main(String[] args) {
        Europe_Op_Avg fullChart2 = new Europe_Op_Avg(Spx.getInstance());
        fullChart2.createChart();
    }

    // Constructor
    public Europe_Op_Avg(BASE_CLIENT_OBJECT client) {
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
        series = new MyTimeSeries[1];
        series[0] = index_serie;

        // Chart
        MyChart indexChart = new MyChart(client, series, props);

        // ------------------ Op avg ------------------- //

        // --------- Dax Op avg ---------- //

        MyTimeSeries op_avg_15 = client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_DAY_15);
        op_avg_15.setColor(Themes.RED);
        op_avg_15.setStokeSize(1.2f);

        MyTimeSeries op_avg_60 = client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_DAY_60);
        op_avg_60.setColor(Themes.BLUE);
        op_avg_60.setStokeSize(1.2f);

        series = new MyTimeSeries[2];
        series[0] = op_avg_15;
        series[1] = op_avg_60;

        // Chart
        MyChart dax_op_avg_chart = new MyChart(client, series, props);

        // --------- Cac Op avg ---------- //

        MyTimeSeries cac_op_avg_15 = client.getTimeSeriesHandler().get(TimeSeriesFactory.CAC_OP_AVG_900);
        cac_op_avg_15.setColor(Themes.RED);
        cac_op_avg_15.setStokeSize(1.2f);

        MyTimeSeries cac_op_avg_60 = client.getTimeSeriesHandler().get(TimeSeriesFactory.CAC_OP_AVG_3600);
        cac_op_avg_60.setColor(Themes.BLUE);
        cac_op_avg_60.setStokeSize(1.2f);

        series = new MyTimeSeries[2];
        series[0] = cac_op_avg_15;
        series[1] = cac_op_avg_60;

        // Chart
        MyChart cac_op_avg_chart = new MyChart(client, series, props);


        // --------- Stoxx Op avg ---------- //

        MyTimeSeries stoxx_op_avg_15 = client.getTimeSeriesHandler().get(TimeSeriesFactory.STOXX_OP_AVG_900);
        stoxx_op_avg_15.setColor(Themes.RED);
        stoxx_op_avg_15.setStokeSize(1.2f);

        MyTimeSeries stoxx_op_avg_60 = client.getTimeSeriesHandler().get(TimeSeriesFactory.STOXX_OP_AVG_3600);
        stoxx_op_avg_60.setColor(Themes.BLUE);
        stoxx_op_avg_60.setStokeSize(1.2f);

        series = new MyTimeSeries[2];
        series[0] = stoxx_op_avg_15;
        series[1] = stoxx_op_avg_60;

        // Chart
        MyChart stoxx_op_avg_chart = new MyChart(client, series, props);

        // -------------------- Chart -------------------- //

        // ----- Charts ----- //
        MyChart[] charts = {indexChart, dax_op_avg_chart, cac_op_avg_chart, stoxx_op_avg_chart};

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer(client, charts, getClass().getName());
        chartContainer.create();
    }

}
