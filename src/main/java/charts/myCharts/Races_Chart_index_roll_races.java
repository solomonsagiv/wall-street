package charts.myCharts;

import charts.myChart.*;
import charts.timeSeries.MyTimeSeries;
import charts.timeSeries.TimeSeriesFactory;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;

import java.awt.*;

public class Races_Chart_index_roll_races extends MyChartCreator {

    public static void main(String[] args) {
        Races_Chart_index_roll_races fullChart2 = new Races_Chart_index_roll_races(Spx.getInstance());
        fullChart2.createChart();
    }

    // Constructor
    public Races_Chart_index_roll_races(BASE_CLIENT_OBJECT client) {
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
        MyTimeSeries index_avg_3600_serie = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.INDEX_AVG_3600, client);
        index_avg_3600_serie.setColor(Themes.PURPLE);
        index_avg_3600_serie.setStokeSize(0.75f);

        // Index avg 900
        MyTimeSeries index_avg_900_serie = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.INDEX_AVG_900, client);
        index_avg_900_serie.setColor(Themes.RED);
        index_avg_900_serie.setStokeSize(0.75f);

        series = new MyTimeSeries[3];
        series[0] = index_serie;
        series[1] = index_avg_900_serie;
        series[2] = index_avg_3600_serie;

        // Chart
        MyChart indexChart = new MyChart(client, series, props);

        // ------------------ Races ------------------- //

        // Index races
        MyTimeSeries index_races = client.getTimeSeriesHandler().get(TimeSeriesFactory.INDEX_RACES);
        index_races.setColor(Themes.ORANGE);
        index_races.setStokeSize(1.2f);

        // Q1 races
        MyTimeSeries q1_races = client.getTimeSeriesHandler().get(TimeSeriesFactory.Q1_RACES);
        q1_races.setColor(Themes.PURPLE);
        q1_races.setStokeSize(1.2f);

        series = new MyTimeSeries[2];
        series[0] = index_races;
        series[1] = q1_races;

        // Chart
        MyChart index_q1_races_chart = new MyChart(client, series, props);


        // ------------------ Races q1 q2 ------------------- //

        // Index races
        MyTimeSeries q1_qua_races = client.getTimeSeriesHandler().get(TimeSeriesFactory.Q1_QW_RACES);
        q1_qua_races.setColor(Themes.PURPLE);
        q1_qua_races.setStokeSize(1.2f);

        // Q1 races
        MyTimeSeries q2_qua_races = client.getTimeSeriesHandler().get(TimeSeriesFactory.WEEK_QW_RACES);
        q2_qua_races.setColor(Themes.GREEN);
        q2_qua_races.setStokeSize(1.2f);

        series = new MyTimeSeries[2];
        series[0] = q1_qua_races;
        series[1] = q2_qua_races;

        // Chart
        MyChart qua_races_chart = new MyChart(client, series, props);

        // -------------------- Chart -------------------- //

        // ----- Charts ----- //
        MyChart[] charts = {indexChart, index_q1_races_chart, qua_races_chart};

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer(client, charts, getClass().getName());
        chartContainer.create();
    }

}
