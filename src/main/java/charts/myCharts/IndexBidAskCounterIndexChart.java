package charts.myCharts;

import charts.myChart.*;
import lists.MyChartPoint;
import locals.L;
import locals.Themes;
import org.jfree.data.time.Millisecond;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;

import java.awt.*;
import java.util.Random;
import java.util.Scanner;

public class IndexBidAskCounterIndexChart extends MyChartCreator {

    // Constructor
    public IndexBidAskCounterIndexChart( BASE_CLIENT_OBJECT client ) {
        super( client );
    }

    @Override
    public void createChart() {

        MyTimeSeries[] series;

        // Props
        props = new MyProps();
        props.setProp( ChartPropsEnum.SECONDS, INFINITE );
        props.setProp( ChartPropsEnum.IS_INCLUDE_TICKER, true );
        props.setProp( ChartPropsEnum.MARGIN, .17 );
        props.setProp( ChartPropsEnum.RANGE_MARGIN, 0.0 );
        props.setProp( ChartPropsEnum.IS_GRID_VISIBLE, true );
        props.setProp( ChartPropsEnum.IS_LOAD_DB, true );
        props.setProp( ChartPropsEnum.IS_LIVE, false );
        props.setProp( ChartPropsEnum.SLEEP, 1000 );
        props.setProp( ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS, (double) INFINITE);
        props.setProp( ChartPropsEnum.SECONDS_ON_MESS, 10 );

        // --------- Chart 1 ---------- //
        // Index
        MyTimeSeries index = new MyTimeSeries( "Index", Color.BLACK, 1.5f, props, client.getIndexList() ) {
            @Override
            public double getData() {
                return client.getIndex();
            }
        };

        series = new MyTimeSeries[1];
        series[0] = index;

        // Chart
        MyChart indexChart = new MyChart( client, series, props );


        // ---------- Chart 2 ---------- //
        // Index
        MyTimeSeries indexBidAskCounter = new MyTimeSeries( "Counter", Themes.ORANGE, 1.5f, props, client.getIndexBidAskCounterList() ) {
            @Override
            public double getData() {
                return client.getIndexBidAskCounter();
            }
        };

        series = new MyTimeSeries[1];
        series[0] = indexBidAskCounter;

        // -------------------- Chart -------------------- //
        MyChart indexBidAskCounterChart = new MyChart( client, series, props );

        // ----- Charts ----- //
        MyChart[] charts = { indexChart, indexBidAskCounterChart };

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer( client, charts, getClass().getName() );
        chartContainer.create();

    }

}
