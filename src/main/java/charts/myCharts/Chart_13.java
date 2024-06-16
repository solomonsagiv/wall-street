package charts.myCharts;

import charts.myChart.*;
import charts.timeSeries.MyTimeSeries;
import charts.timeSeries.TimeSeriesFactory;
import locals.Themes;
import org.jfree.chart.plot.ValueMarker;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;

import java.awt.*;

public class Chart_13 extends MyChartCreator {

    public static void main(String[] args) {
        Chart_13 fullChart2 = new Chart_13(Spx.getInstance());
        fullChart2.createChart();
    }

    // Constructor
    public Chart_13(BASE_CLIENT_OBJECT client) {
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

        series = new MyTimeSeries[3];
        series[0] = index_serie;
        series[1] = index_avg_900_serie;
        series[2] = index_avg_3600_serie;

        // Chart
        MyChart indexChart = new MyChart(client, series, props);

        // --------- Op avg week ---------- //

        MyTimeSeries op_avg_15 = client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_WEEK_900);
        op_avg_15.setColor(Themes.LIGHT_RED);
        op_avg_15.setVisible(false);
        op_avg_15.setStokeSize(1.0f);

        MyTimeSeries op_avg_60 = client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_WEEK_3600);
        op_avg_60.setColor(Themes.BLUE);
        op_avg_60.setStokeSize(1.2f);

        series = new MyTimeSeries[2];
        series[0] = op_avg_15;
        series[1] = op_avg_60;

        // Chart
        MyChart op_avg_week_chart = new MyChart(client, series, props);


        // --------- Op avg q1 ---------- //
        MyTimeSeries op_avg_q1_15 = client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_Q1_900);
        op_avg_q1_15.setColor(Themes.LIGHT_RED);
        op_avg_q1_15.setVisible(false);
        op_avg_q1_15.setStokeSize(1.0f);

        MyTimeSeries op_avg_q1_60 = client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_Q1_3600);
        op_avg_q1_60.setColor(Themes.BROWN);
        op_avg_q1_60.setStokeSize(1.2f);

        MyTimeSeries op_avg_q1_14400 = client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_Q1_14400);
        op_avg_q1_14400.setColor(Themes.ORANGE);
        op_avg_q1_14400.setStokeSize(1.2f);


        MyTimeSeries yesterday_op_avg = new MyTimeSeries("Yesterday O/P", client) {
            @Override
            public void updateData() {
            }

            @Override
            public double getValue() {
                return client.getPre_day_avg();
            }

            @Override
            public void load() {

            }

            @Override
            public void load_exp_data() {

            }
        };

        series = new MyTimeSeries[4];
        series[0] = op_avg_q1_15;
        series[1] = op_avg_q1_60;
        series[2] = op_avg_q1_14400;
        series[3] = yesterday_op_avg;

        // Chart
        MyChart op_avg_q1_chart = new MyChart(client, series, props);
        ValueMarker yesterday_marker = new ValueMarker(client.getPre_day_avg());
        yesterday_marker.setPaint(Color.BLACK);
        yesterday_marker.setStroke(new BasicStroke(2f));
        op_avg_q1_chart.add_marker(yesterday_marker);

        // --------- Races ---------- //

        MyTimeSeries index_races = client.getTimeSeriesHandler().get(TimeSeriesFactory.INDEX_RACES);
        index_races.setColor(Themes.ORANGE);
        index_races.setStokeSize(1.2f);

        MyTimeSeries q1_races = client.getTimeSeriesHandler().get(TimeSeriesFactory.Q1_RACES);
        q1_races.setColor(Themes.PURPLE);
        q1_races.setStokeSize(1.2f);

        series = new MyTimeSeries[2];
        series[0] = index_races;
        series[1] = q1_races;

        // Chart
        MyChart index_q1_races_chart = new MyChart(client, series, props);

        // -------------------- Chart -------------------- //

        // ----- Charts ----- //
        MyChart[] charts = {indexChart, op_avg_week_chart, op_avg_q1_chart, index_q1_races_chart};

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer(client, charts, getClass().getName());
        chartContainer.create();
    }

}
