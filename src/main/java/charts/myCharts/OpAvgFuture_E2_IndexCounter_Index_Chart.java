package charts.myCharts;

import charts.myChart.*;
import exp.Exp;
import exp.ExpStrings;
import locals.Themes;
import org.jfree.chart.plot.ValueMarker;
import serverObjects.BASE_CLIENT_OBJECT;

import java.awt.*;

public class OpAvgFuture_E2_IndexCounter_Index_Chart extends MyChartCreator {

    // Constructor
    public OpAvgFuture_E2_IndexCounter_Index_Chart(BASE_CLIENT_OBJECT client) {
        super(client);
    }

    @Override
    public void createChart() throws CloneNotSupportedException {

        MyTimeSeries[] series;

        // Props
        props = new MyProps();
        props.setProp(ChartPropsEnum.SECONDS, INFINITE);
        props.setProp(ChartPropsEnum.IS_INCLUDE_TICKER, false);
        props.setProp(ChartPropsEnum.MARGIN, 0.005);
        props.setProp(ChartPropsEnum.RANGE_MARGIN, 0.0);
        props.setProp(ChartPropsEnum.IS_GRID_VISIBLE, true);
        props.setProp(ChartPropsEnum.IS_LOAD_DB, true);
        props.setProp(ChartPropsEnum.IS_LIVE, false);
        props.setProp(ChartPropsEnum.SLEEP, 1000);
        props.setProp(ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS, (double) INFINITE);
        props.setProp(ChartPropsEnum.SECONDS_ON_MESS, (double) INFINITE);
        props.setProp(ChartPropsEnum.INCLUDE_DOMAIN_AXIS, true);

        Exp e1 = client.getExps().getExp(ExpStrings.e1);

        // --------- OpAvgFuture 1 ---------- //

        MyProps opAvgFutureProps = (MyProps) props.clone();
        ValueMarker marker = new ValueMarker(0);
        marker.setPaint(Color.BLACK);
        marker.setStroke(new BasicStroke(2f));
        opAvgFutureProps.setProp(ChartPropsEnum.MARKER, marker);
        opAvgFutureProps.setProp(ChartPropsEnum.INCLUDE_DOMAIN_AXIS, false);

        MyTimeSeries opAvgFuture = e1.getOpAvgFutSeries();
        opAvgFuture.setColor(Themes.BLUE_STRIKE);
        opAvgFuture.setStokeSize(1.5f);

        series = new MyTimeSeries[1];
        series[0] = opAvgFuture;

        // Chart
        MyChart opAvgFutureChart = new MyChart(client, series, opAvgFutureProps);


        MyProps newProps = (MyProps) props.clone();
        newProps.setProp(ChartPropsEnum.INCLUDE_DOMAIN_AXIS, false);

        // --------- Index 2 ---------- //
        newProps.setProp(ChartPropsEnum.INCLUDE_DOMAIN_AXIS, false);

        // Index
        MyTimeSeries index = client.getIndexSeries();
        index.setColor(Color.BLACK);
        index.setStokeSize(1.5f);

        series = new MyTimeSeries[1];
        series[0] = index;

        // Chart
        MyChart indexChart = new MyChart(client, series, newProps);

        // -------------------- Chart -------------------- //

        // ----- Charts ----- //
        MyChart[] charts = {indexChart, opAvgFutureChart};

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer(client, charts, getClass().getName());
        chartContainer.create();


    }

}
