package charts.myCharts;

import charts.myChart.*;
import charts.timeSeries.MyTimeSeries;
import charts.timeSeries.TimeSeriesFactory;
import exp.Exp;
import exp.ExpStrings;
import locals.Themes;
import org.jfree.chart.plot.ValueMarker;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;

import java.awt.*;

public class Full_Chart_3 extends MyChartCreator {

    public static void main(String[] args) {
        Full_Chart_3 fullChart2 = new Full_Chart_3(Spx.getInstance());
        fullChart2.createChart();
    }

    // Constructor
    public Full_Chart_3(BASE_CLIENT_OBJECT client) {
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
        marker.setPaint(Themes.GREY_2);
        marker.setStroke(new BasicStroke(2f));
        
        // --------------------------- Index -------------------------------- //

        // Index
        MyTimeSeries indexSeries = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.INDEX_SERIES, client, null);
        indexSeries.setColor(Color.BLACK);
        indexSeries.setStokeSize(1.5f);

        series = new MyTimeSeries[1];
        series[0] = indexSeries;

        // Chart
        MyChart indexChart = new MyChart(client, series, props);

        // ------------------------- Op avg --------------------------------- //
        int size;
        if (client.getExps().contains_exp(ExpStrings.day)) {
            size = 4;
        } else {
            size = 2;
        }
        series = new MyTimeSeries[size];

        int i = 0;
        // For each exp
        for (Exp exp : client.getExps().getExpList()) {

            // Filter fut day / q1
            if (exp.getName().equals(ExpStrings.day) || exp.getName().equals(ExpStrings.q1)) {
                // Index
                MyTimeSeries op_avg_60 = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_HOUR_SERIES, client, exp);
                op_avg_60.setColor(Themes.BLUE);
                op_avg_60.setStokeSize(1.2f);

                // Index
                MyTimeSeries op_avg_15 = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_15_SERIES, client, exp);
                op_avg_15.setColor(Themes.PINK_LIGHT);
                op_avg_15.setStokeSize(1.2f);

                series[i] = op_avg_15;
                i++;
                series[i] = op_avg_60;
                i++;

                // If main
                if (exp.getName().equals(client.getExps().getMainExp().getName())) {
                    op_avg_60.setVisible(true);
                    op_avg_15.setVisible(true);
                } else {
                    op_avg_60.setVisible(false);
                    op_avg_15.setVisible(false);
                }
            }
        }

        // Chart
        MyChart op_avg_all_exps_chart = new MyChart(client, series, props);

        // ----------------------------- Delta  ---------------------------- //

        // Delta
        MyTimeSeries deltaSeries = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.FUTURE_DELTA, client, client.getExps().getExp(ExpStrings.q1));
        deltaSeries.setColor(Themes.LIFGT_BLUE_2);
        deltaSeries.setStokeSize(1.5f);

        series = new MyTimeSeries[1];
        series[0] = deltaSeries;

        // Chart
        MyChart deltaChart = new MyChart(client, series, props);

        // ------------------------------ Chart ----------------------------- //

        // ----- Charts ----- //
        MyChart[] charts = {indexChart, deltaChart, op_avg_all_exps_chart};

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer(client, charts, getClass().getName());
        chartContainer.create();
    }

}
