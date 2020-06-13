package charts.myCharts.stockCharts;

import charts.myChart.*;
import exp.ExpEnum;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;

import java.awt.*;

public class Month_Index_Live_Chart extends MyChartCreator {

    // Constructor
    public Month_Index_Live_Chart( BASE_CLIENT_OBJECT client ) {
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
        props.setProp( ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS, (double) INFINITE );
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

        // Future
        MyTimeSeries future = new MyTimeSeries( "Contract", Themes.GREEN, 2.25f, props, null ) {
            @Override
            public double getData() {
                return client.getExps().getExp( ExpEnum.MONTH.MONTH ).getOptions().getContract();
            }
        };

        MyTimeSeries[] series = {index, bid, ask, future};

        // Chart
        MyChart chart = new MyChart( client, series, props );

        // ----- Charts ----- //
        MyChart[] charts = { chart };

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer( client, charts, getClass().getName() );
        chartContainer.create();


    }

}
