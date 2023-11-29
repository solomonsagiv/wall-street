package charts.myCharts;

import charts.myChart.*;
import charts.timeSeries.MyTimeSeries;
import charts.timeSeries.TimeSeriesFactory;
import locals.Themes;
import org.jfree.chart.plot.ValueMarker;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;
import java.awt.*;

public class Chart_7 extends MyChartCreator {

    public static void main(String[] args) {
        Chart_7 fullChart2 = new Chart_7(Spx.getInstance());
        fullChart2.createChart();
    }

    // Constructor
    public Chart_7(BASE_CLIENT_OBJECT client) {
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

        // --------- Op avg ---------- //

        MyTimeSeries op_avg_15 = client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_DAY_15);
        op_avg_15.setColor(Themes.RED);
        op_avg_15.setStokeSize(1.2f);

        MyTimeSeries op_avg_60 = client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_DAY_60);
        op_avg_60.setColor(Themes.BLUE);
        op_avg_60.setStokeSize(1.2f);

        MyTimeSeries op_avg_240 = client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_240_CONTINUE);
        op_avg_240.setColor(Themes.BINANCE_ORANGE);
        op_avg_240.setStokeSize(1.2f);

        series = new MyTimeSeries[3];
        series[0] = op_avg_15;
        series[1] = op_avg_60;
        series[2] = op_avg_240;
        
        // Chart
        MyChart op_avg_chart = new MyChart(client, series, props);

        // -------------------- DF'S ---------------------- //

        MyTimeSeries df_2 = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_2_CDF);
        df_2.setColor(Themes.ORANGE);
        df_2.setStokeSize(1.2f);

        MyTimeSeries df_7 = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_7_CDF);
        df_7.setColor(Themes.PURPLE);
        df_7.setStokeSize(1.2f);

        MyTimeSeries df_8 = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_9_CDF);
        df_8.setColor(Themes.RED);
        df_8.setStokeSize(1.2f);

        series = new MyTimeSeries[3];
        series[0] = df_2;
        series[1] = df_7;
        series[2] = df_8;

        ValueMarker marker_30k = new ValueMarker(30000.0);
        marker_30k.setPaint(Themes.GREY_2);

        ValueMarker marker_minus_30k = new ValueMarker(-30000.0);
        marker_minus_30k.setPaint(Themes.GREY_2);

        // Chart
        MyChart df_chart = new MyChart(client, series, props);
//        df_chart.add_marker(marker_30k);
//        df_chart.add_marker(marker_minus_30k);
////
        // -------------------- Chart -------------------- //

        // ----- Charts ----- //
        MyChart[] charts = {indexChart, op_avg_chart, df_chart};

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer(client, charts, getClass().getName());
        chartContainer.create();
    }

}
