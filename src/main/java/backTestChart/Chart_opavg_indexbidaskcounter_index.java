package backTestChart;

import charts.myChart.*;
import exp.ExpStrings;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Chart_opavg_indexbidaskcounter_index extends MyChartCreator {

    Map< String, MyTimeSeries > seriesMap;

    // Constructor
    public Chart_opavg_indexbidaskcounter_index( BASE_CLIENT_OBJECT client ) {
        super( client );
        seriesMap = new HashMap<>( );
    }

    @Override
    public void createChart() throws CloneNotSupportedException {
        MyTimeSeries[] series;

        // Props
        props = new MyProps( );
        props.setProp( ChartPropsEnum.SECONDS, INFINITE );
        props.setProp( ChartPropsEnum.IS_INCLUDE_TICKER, false );
        props.setProp( ChartPropsEnum.MARGIN, .17 );
        props.setProp( ChartPropsEnum.RANGE_MARGIN, 0.0 );
        props.setProp( ChartPropsEnum.IS_GRID_VISIBLE, true );
        props.setProp( ChartPropsEnum.IS_LOAD_DB, true );
        props.setProp( ChartPropsEnum.IS_LIVE, false );
        props.setProp( ChartPropsEnum.SLEEP, 1000 );
        props.setProp( ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS, INFINITE );
        props.setProp( ChartPropsEnum.SECONDS_ON_MESS, INFINITE );
        props.setProp( ChartPropsEnum.INCLUDE_DOMAIN_AXIS, true );

        // --------- Chart 1 ---------- //
        // Index
        MyTimeSeries index = client.getIndexSeries( );
        index.setStokeSize( 1.5f );
        index.setColor( Color.BLACK );

        series = new MyTimeSeries[ 1 ];
        series[ 0 ] = index;

        // Chart
        MyChart indexChart = new MyChart( client, series, props );

        // ---------- Chart 2 ---------- //
        // Index
        MyTimeSeries indexBidAskCounter = client.getIndexBidAskCounterSeries( );
        indexBidAskCounter.setColor( Themes.ORANGE );
        indexBidAskCounter.setStokeSize( 1.5f );

        series = new MyTimeSeries[ 1 ];
        series[ 0 ] = indexBidAskCounter;

        MyChart indexCounterChart = new MyChart( client, series, props );

        // ---------- Chart 3 ---------- //
        MyTimeSeries opavg15_series = client.getExps( ).getExp( ExpStrings.day ).getOpAvg15FutSeries( );
        opavg15_series.setColor( Themes.PURPLE );
        opavg15_series.setStokeSize( 1.5f );

        series = new MyTimeSeries[ 1 ];
        series[ 0 ] = opavg15_series;

        MyChart opavg15_chart = new MyChart( client, series, props );

        // ----- Charts ----- //
        MyChart[] charts = { indexChart, indexCounterChart, opavg15_chart };

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer( client, charts, getClass( ).getName( ) );
        chartContainer.create( );

    }

}
