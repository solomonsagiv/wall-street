package charts.myCharts;

import charts.myChart.*;
import exp.Exp;
import exp.ExpEnum;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;

import java.awt.*;

public class FutureFarBidAskCounterIndexChart extends MyChartCreator {

    // Constructor
    public FutureFarBidAskCounterIndexChart( BASE_CLIENT_OBJECT client ) {
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
        Exp e2 = client.getExps().getExp( ExpEnum.E2 );

        // Index
        MyTimeSeries futureFarBidAskCounter = new MyTimeSeries( "Future far B/A counter", Themes.BINANCE_RED, 1.5f, props, e2.getFutBidAskCounterList() ) {
            @Override
            public double getData() {
                return e2.getFutureBidAskCounter();
            }
        };

        series = new MyTimeSeries[1];
        series[0] = futureFarBidAskCounter;

        // -------------------- Chart -------------------- //
        MyChart futureFarBidAskCounterChart = new MyChart( client, series, props );

        // ----- Charts ----- //
        MyChart[] charts = { indexChart, futureFarBidAskCounterChart };

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer( client, charts, getClass().getName() );
        chartContainer.create();


    }

}
