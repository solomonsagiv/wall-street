package charts.myCharts.bitcoinCharts;

import charts.myChart.*;
import exp.ExpEnum;
import locals.Themes;
import serverObjects.bitcoinObjects.BITCOIN_CLIENT;

import java.awt.*;

public class BitcoinLiveChart extends MyChartCreator {

    // Constructor
    public BitcoinLiveChart( BITCOIN_CLIENT client ) {
        super(client);
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
        MyTimeSeries index = new MyTimeSeries( "Index", null ) {
            @Override
            public double getData() {
                return client.getIndex();
            }
        };
        index.setColor( Color.BLACK );
        index.setStokeSize( 2.25f );

        // Bid
        MyTimeSeries bid = new MyTimeSeries( "Bid", null ) {
            @Override
            public double getData() {
                return client.getIndexBid();
            }
        };
        bid.setColor( Themes.BLUE );
        bid.setStokeSize( 2.25f );

        // Ask
        MyTimeSeries ask = new MyTimeSeries( "Ask", null ) {
            @Override
            public double getData() {
                return client.getIndexAsk();
            }
        };

        ask.setColor( Themes.RED );
        ask.setStokeSize( 2.25f );

        // Future
        MyTimeSeries future = new MyTimeSeries( "Future", null ) {
            @Override
            public double getData() {
                return client.getExps().getExp( ExpEnum.MONTH ).getCalcFut();
            }
        };

        future.setColor( Themes.GREEN );
        future.setStokeSize( 2.25f );

        MyTimeSeries[] series = {index, bid, ask, future };

        // Chart
        MyChart chart = new MyChart( client, series, props );

        // ----- Charts ----- //
        MyChart[] charts = { chart };

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer( client, charts, getClass().getName() );
        chartContainer.create();


    }

}
