package charts.myCharts.bitcoinCharts;

import charts.myChart.*;
import exp.ExpEnum;
import exp.ExpMonth;
import locals.Themes;
import serverObjects.bitcoinObjects.BITCOIN_CLIENT;

import java.awt.*;

public class BitcoinLiveChart extends MyChartCreator {

    MyTimeSeries index;
    MyTimeSeries future;

    // Constructor
    public BitcoinLiveChart( BITCOIN_CLIENT client ) {
        super( client );
    }

    @Override
    public void createChart() {

        // Props
        props = new MyProps( );
        props.setProp( ChartPropsEnum.SECONDS, 150 );
        props.setProp( ChartPropsEnum.IS_INCLUDE_TICKER, false );
        props.setProp( ChartPropsEnum.MARGIN, .17 );
        props.setProp( ChartPropsEnum.RANGE_MARGIN, 0.0 );
        props.setProp( ChartPropsEnum.IS_GRID_VISIBLE, false );
        props.setProp( ChartPropsEnum.IS_LOAD_DB, false );
        props.setProp( ChartPropsEnum.IS_LIVE, true );
        props.setProp( ChartPropsEnum.SLEEP, 200 );
        props.setProp( ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS, ( double ) INFINITE );
        props.setProp( ChartPropsEnum.SECONDS_ON_MESS, 10 );

        // ----- Chart 1 ----- //
        // Index
        index = new MyTimeSeries( "Index", client ) {
            @Override
            public double getData() {
                return client.getIndex( );
            }
        };
        index.setColor( Color.BLACK );
        index.setStokeSize( 2.25f );

        // Future
        future = new MyTimeSeries( "Future", client ) {
            @Override
            public double getData() {
                ExpMonth expMonth = ( ExpMonth ) client.getExps( ).getExp( ExpEnum.MONTH );

                double fut = (expMonth.getCalcFutBid() + expMonth.getCalcFutAsk()) / 2;
                return fut;
            }
        };

        future.setColor( Themes.GREEN );
        future.setStokeSize( 2.25f );

        MyTimeSeries[] series = { index, future };

        // Chart
        MyChart chart = new MyChart( client, series, props );

        // ----- Charts ----- //
        MyChart[] charts = { chart };

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer( client, charts, getClass( ).getName( ) );
        chartContainer.create( );

    }

    public MyTimeSeries getIndex() {
        return index;
    }

    public void setIndex( MyTimeSeries index ) {
        this.index = index;
    }

    public MyTimeSeries getFuture() {
        return future;
    }

    public void setFuture( MyTimeSeries future ) {
        this.future = future;
    }
}
