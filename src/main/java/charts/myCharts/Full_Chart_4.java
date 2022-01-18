package charts.myCharts;

import charts.myChart.*;
import charts.timeSeries.MyTimeSeries;
import charts.timeSeries.TimeSeriesFactory;
import dataBase.mySql.MySql;
import dataBase.mySql.dataUpdaters.IDataBaseHandler;
import exp.Exp;
import exp.ExpStrings;
import locals.Themes;
import org.jfree.chart.plot.ValueMarker;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;

import java.awt.*;
import java.net.UnknownHostException;
import java.sql.ResultSet;

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

        // Marker
        ValueMarker marker = new ValueMarker(0);
        marker.setPaint(Color.BLACK);
        marker.setStroke(new BasicStroke(2f));

        // ------------------ Op avg ------------------- //

        // Exp
        Exp day_exp = client.getExps().getExp(ExpStrings.day);

        // --------- Op avg ---------- //
        MyTimeSeries op_avg_15 = new MyTimeSeries("O/P Avg Day 15", client) {
            @Override
            public ResultSet load_last_x_time(int minuts) {
                return null;
            }

            @Override
            public double getData() throws UnknownHostException {
                return day_exp.get_op_avg(900);
            }
            @Override
            public void load() {
                IDataBaseHandler dataBaseHandler = client.getMySqlService().getDataBaseHandler();

                String index_table = dataBaseHandler.get_table_loc(IDataBaseHandler.INDEX_TABLE);
                String fut_table = dataBaseHandler.get_table_loc(day_exp.getName());
                ResultSet rs = MySql.Queries.op_avg_cumulative_query(index_table, fut_table, 15);
                IDataBaseHandler.loadSerieData(rs, this);
            }
        };
        op_avg_15.setColor(Themes.GREEN);
        op_avg_15.setStokeSize(1.2f);

        MyTimeSeries op_avg_60 = new MyTimeSeries("O/P Avg Day 60", client) {
            @Override
            public ResultSet load_last_x_time(int minuts) {
                return null;
            }

            @Override
            public double getData() throws UnknownHostException {
                return 0;
            }
            @Override
            public void load() {
                IDataBaseHandler dataBaseHandler = client.getMySqlService().getDataBaseHandler();

                String index_table = dataBaseHandler.get_table_loc(IDataBaseHandler.INDEX_TABLE);
                String fut_table = dataBaseHandler.get_table_loc(day_exp.getName());
                ResultSet rs = MySql.Queries.op_avg_cumulative_query(index_table, fut_table, 60);
                IDataBaseHandler.loadSerieData(rs, this);
            }
        };
        op_avg_60.setColor(Themes.BLUE);
        op_avg_60.setStokeSize(1.2f);

        series = new MyTimeSeries[2];
        series[0] = op_avg_15;
        series[1] = op_avg_60;

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

        // ------------------- Delta  -------------------- //

        // Delta
        MyTimeSeries deltaSeries = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.FUTURE_DELTA, client, client.getExps().getExp(ExpStrings.q1));
        deltaSeries.setColor(Themes.LIFGT_BLUE_2);
        deltaSeries.setStokeSize(1.5f);

        series = new MyTimeSeries[1];
        series[0] = deltaSeries;

        // Chart
        MyChart deltaChart = new MyChart(client, series, props);

        // --------- Q1 bid ask counter ---------- //

        // Delta
        MyTimeSeries q1_bid_ask_counter_serie = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.E1_BID_ASK_COUNTER, client, client.getExps().getExp(ExpStrings.q1));
        q1_bid_ask_counter_serie.setColor(Themes.GREEN);
        q1_bid_ask_counter_serie.setStokeSize(1.5f);

        series = new MyTimeSeries[1];
        series[0] = q1_bid_ask_counter_serie;

        // Chart
        MyChart q1_bid_ask_counterChart = new MyChart(client, series, props);

        // -------------------- Chart -------------------- //

        // ----- Charts ----- //
        MyChart[] charts = {indexChart, op_avg_chart, q1_bid_ask_counterChart, deltaChart};

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer(client, charts, getClass().getName());
        chartContainer.create();
    }

}
