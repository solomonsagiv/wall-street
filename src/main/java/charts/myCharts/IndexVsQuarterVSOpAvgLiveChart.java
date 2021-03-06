package charts.myCharts;

import charts.myChart.*;
import locals.Themes;
import options.Options;
import options.OptionsEnum;
import serverObjects.BASE_CLIENT_OBJECT;

import java.awt.*;

public class IndexVsQuarterVSOpAvgLiveChart extends MyChartCreator {

    // Constructor
    public IndexVsQuarterVSOpAvgLiveChart( BASE_CLIENT_OBJECT client ) {
        super( client );
    }
    
    @Override
    public void createChart() {

        // Props
        props = new MyProps();
        props.setProp( ChartPropsEnum.SECONDS, 150 );
        props.setProp( ChartPropsEnum.IS_INCLUDE_TICKER, false );
        props.setProp( ChartPropsEnum.MARGIN, .17 );
        props.setProp( ChartPropsEnum.RANGE_MARGIN, 0.0 );
        props.setProp( ChartPropsEnum.IS_GRID_VISIBLE, false );
        props.setProp( ChartPropsEnum.IS_LOAD_DB, false );
        props.setProp( ChartPropsEnum.IS_LIVE, true );
        props.setProp( ChartPropsEnum.SLEEP, 200 );
        props.setProp( ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS, (double)INFINITE );
        props.setProp( ChartPropsEnum.SECONDS_ON_MESS, 10 );

        // ----- Chart 1 ----- //
        // Index
        MyTimeSeries index = new MyTimeSeries( "Index", Color.BLACK, 2.25f, props, client.getIndexList() ) {
            @Override
            public double getData() {
                return client.getIndex();
            }
        };

        // Index
        MyTimeSeries bid = new MyTimeSeries( "Bid", Themes.BLUE, 2.25f, props, client.getIndexBidList() ) {
            @Override
            public double getData() {
                return client.getIndexBid();
            }
        };

        // Index
        MyTimeSeries ask = new MyTimeSeries( "Ask", Themes.RED, 2.25f, props, client.getIndexAskList() ) {
            @Override
            public double getData() {
                return client.getIndexAsk();
            }
        };

        // OpAvg
        MyTimeSeries opAvg = new MyTimeSeries( "OpAvgFuture", Themes.BLUE_LIGHT_2, 2.25f, props, null ) {
            @Override
            public double getData() {
                try {
                    Options options = client.getOptionsHandler().getOptions(OptionsEnum.QUARTER);
                    return options.getFuture() - options.getOpAvgFuture();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        };

        // Future
        MyTimeSeries future = new MyTimeSeries( "Future", Themes.GREEN, 2.25f, props, null ) {
            @Override
            public double getData() {
                return client.getOptionsHandler().getOptions( OptionsEnum.QUARTER ).getFuture();
            }
        };

        MyTimeSeries[] series = { index, bid, ask, future, opAvg };

        // Chart
        MyChart chart = new MyChart( client, series, props );

        // ----- Charts ----- //
        MyChart[] charts = { chart };

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer( client, charts, getClass().getName() );
        chartContainer.create();


    }

}
