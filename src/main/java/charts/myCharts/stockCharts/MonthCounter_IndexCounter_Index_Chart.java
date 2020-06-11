package charts.myCharts.stockCharts;

import charts.myChart.*;
import exp.ExpEnum;
import locals.Themes;
import options.Options;
import options.OptionsEnum;
import serverObjects.BASE_CLIENT_OBJECT;

import java.awt.*;

public class MonthCounter_IndexCounter_Index_Chart extends MyChartCreator {

    // Constructor
    public MonthCounter_IndexCounter_Index_Chart( BASE_CLIENT_OBJECT client ) {
        super( client );
    }

    @Override
    public void createChart() throws CloneNotSupportedException {

        MyTimeSeries[] series;

        // Props
        props = new MyProps();
        props.setProp( ChartPropsEnum.SECONDS, INFINITE );
        props.setProp( ChartPropsEnum.IS_INCLUDE_TICKER, false );
        props.setProp( ChartPropsEnum.MARGIN, .17 );
        props.setProp( ChartPropsEnum.RANGE_MARGIN, 0.0 );
        props.setProp( ChartPropsEnum.IS_GRID_VISIBLE, true );
        props.setProp( ChartPropsEnum.IS_LOAD_DB, true );
        props.setProp( ChartPropsEnum.IS_LIVE, false );
        props.setProp( ChartPropsEnum.SLEEP, 1000 );
        props.setProp( ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS, (double) INFINITE);
        props.setProp( ChartPropsEnum.SECONDS_ON_MESS, 10 );
        props.setProp( ChartPropsEnum.INCLUDE_DOMAIN_AXIS, true );

        // --------- Chart 1 ---------- //
        MyProps newProps = ( MyProps ) props.clone();
        newProps.setProp( ChartPropsEnum.INCLUDE_DOMAIN_AXIS, false );
        
        // Index
        MyTimeSeries index = new MyTimeSeries( "Index", Color.BLACK, 1.5f, newProps, client.getIndexList() ) {
            @Override
            public double getData() {
                return client.getIndex();
            }
        };

        series = new MyTimeSeries[1];
        series[0] = index;

        // Chart
        MyChart indexChart = new MyChart( client, series, newProps );

        // ---------- Chart 2 ---------- //

        Options options = client.getExps().getExp( ExpEnum.MONTH ).getOptions();

        // Index
        MyTimeSeries futureFarBidAskCounter = new MyTimeSeries( "Month B/A counter", Themes.BINANCE_RED, 1.5f, newProps, options.getConBidAskCounterList() ) {
            @Override
            public double getData() {
                return options.getConBidAskCounter();
            }
        };

        series = new MyTimeSeries[1];
        series[0] = futureFarBidAskCounter;

        MyChart futureFarBidAskCounterChart = new MyChart( client, series, newProps );

        // ---------- Chart 3 ---------- //
        // Index
        MyTimeSeries indexBidAskCounter = new MyTimeSeries( "Counter", Themes.ORANGE, 1.5f, props, client.getIndexBidAskCounterList() ) {
            @Override
            public double getData() {
                return client.getIndexBidAskCounter();
            }
        };

        series = new MyTimeSeries[1];
        series[0] = indexBidAskCounter;

        MyChart indexCounterChart = new MyChart( client, series, props );
        // -------------------- Chart -------------------- //

        // ----- Charts ----- //
        MyChart[] charts = { indexChart, futureFarBidAskCounterChart, indexCounterChart };

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer( client, charts, getClass().getName() );
        chartContainer.create();


    }

}
