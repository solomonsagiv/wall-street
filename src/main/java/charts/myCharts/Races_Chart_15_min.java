package charts.myCharts;

import charts.myChart.*;
import charts.timeSeries.MyTimeSeries;
import charts.timeSeries.TimeSeriesFactory;
import locals.Themes;
import races.Race_Logic;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;

import java.awt.*;

public class Races_Chart_15_min extends MyChartCreator {

    public static void main(String[] args) {
        Races_Chart_15_min fullChart2 = new Races_Chart_15_min(Spx.getInstance());
        fullChart2.createChart();
    }

    // Constructor
    public Races_Chart_15_min(BASE_CLIENT_OBJECT client) {
        super(client, null, null);
    }

    @Override
    public void init() {

        MyTimeSeries[] series;

        // Props
        props = new MyProps();
        props.setProp(ChartPropsEnum.SECONDS, 900);
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

        // ------------------- Index -------------------- //

        // Index
        MyTimeSeries index = new MyTimeSeries("Index", client) {

            @Override
            public double getValue() {
                return client.getIndex();
            }

            @Override
            public void updateData() {

            }

            @Override
            public void load() {

            }

            @Override
            public void load_exp_data() {

            }
        };
        index.setColor(Color.BLACK);
        index.setVisible(true);
        index.setStokeSize(1.2f);


        series = new MyTimeSeries[1];
        series[0] = index;

        // Chart
        MyChart indexChart = new MyChart(client, series, props);

        // ------------------ Races ------------------- //

        // Index races
        MyTimeSeries index_races = new MyTimeSeries("Index races",client) {

            @Override
            public double getValue() {
                return client.getRacesService().get_race_logic(Race_Logic.RACE_RUNNER_ENUM.Q1_INDEX).get_r_one_points();
            }

            @Override
            public void updateData() {
//                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.INDEX_RACES_PROD);
//                        setValue(MySql.Queries.handle_rs(Objects.requireNonNull(MySql.Queries.get_last_record_mega(serie_id, MySql.CDF, MySql.JIBE_PROD_CONNECTION))));
            }

            @Override
            public void load() {
            }

            @Override
            public void load_exp_data() {
            }
        };
        index_races.setColor(Themes.ORANGE);
        index_races.setStokeSize(1.2f);

        // Q1 races
        MyTimeSeries q1_races = new MyTimeSeries("Q1 races",client) {

            @Override
            public double getValue() {
                return client.getRacesService().get_race_logic(Race_Logic.RACE_RUNNER_ENUM.Q1_INDEX).get_r_two_points();
            }

            @Override
            public void updateData() {
//                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.INDEX_RACES_PROD);
//                        setValue(MySql.Queries.handle_rs(Objects.requireNonNull(MySql.Queries.get_last_record_mega(serie_id, MySql.CDF, MySql.JIBE_PROD_CONNECTION))));
            }

            @Override
            public void load() {
            }

            @Override
            public void load_exp_data() {
            }
        };
        q1_races.setColor(Themes.PURPLE);
        q1_races.setStokeSize(1.2f);

        series = new MyTimeSeries[2];
        series[0] = index_races;
        series[1] = q1_races;

        // Chart
        MyChart index_q1_races_chart = new MyChart(client, series, props);

        // ------------------ Races one minus two ------------------- //

        // Index races
        MyTimeSeries r1_minus_r2 = client.getTimeSeriesHandler().get(TimeSeriesFactory.R1_PLUS_R2_IQ);
        r1_minus_r2.setColor(Themes.RED);
        r1_minus_r2.setStokeSize(1.2f);

        series = new MyTimeSeries[1];
        series[0] = r1_minus_r2;

        // Chart
        MyChart r1_minus_r2_chart = new MyChart(client, series, props);

        // -------------------- Chart -------------------- //

        // ----- Charts ----- //
        MyChart[] charts = {indexChart, index_q1_races_chart, r1_minus_r2_chart};

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer(client, charts, getClass().getName());
        chartContainer.create();
    }

}
