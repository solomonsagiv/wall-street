package charts.myCharts;

import charts.myChart.*;
import exp.ExpStrings;
import exp.Exps;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;

import java.awt.*;

public class FuturesChart extends MyChartCreator {

    // Constructor
    public FuturesChart( BASE_CLIENT_OBJECT client ) {
        super( client );
    }

    @Override
    public void createChart() {

        Exps exps = client.getExps();

        // Props
        props = new MyProps( );
        props.setProp( ChartPropsEnum.SECONDS, 150 );
        props.setProp( ChartPropsEnum.IS_INCLUDE_TICKER, false );
        props.setProp( ChartPropsEnum.MARGIN, .17 );
        props.setProp( ChartPropsEnum.RANGE_MARGIN, 0.0 );
        props.setProp( ChartPropsEnum.IS_GRID_VISIBLE, false );
        props.setProp( ChartPropsEnum.IS_LOAD_DB, false );
        props.setProp( ChartPropsEnum.IS_LIVE, true );
        props.setProp( ChartPropsEnum.SLEEP, 100 );
        props.setProp( ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS, INFINITE );
        props.setProp( ChartPropsEnum.SECONDS_ON_MESS, 10 );

        // ----- Chart 1 ----- //
        // Index
        MyTimeSeries index = new MyTimeSeries( "Ind", client ) {
            @Override
            public double getData() {
                return client.getIndex( );
            }
        };
        index.setColor( Color.BLACK );
        index.setStokeSize( 2.25f );

        // Bid
        MyTimeSeries bid = new MyTimeSeries( "Ind bid", client ) {
            @Override
            public double getData() {
                return client.getIndexBid( );
            }
        };
        bid.setColor( Themes.BLUE );
        bid.setStokeSize( 2.25f );

        // Ask
        MyTimeSeries ask = new MyTimeSeries( "Ind ask", client ) {
            @Override
            public double getData() {
                return client.getIndexAsk( );
            }
        };
        ask.setColor( Themes.RED );
        ask.setStokeSize( 2.25f );


        // Day fut
        MyTimeSeries futureDay = new MyTimeSeries( "Fut day", client ) {
            @Override
            public double getData() {
                return exps.getExp( ExpStrings.day ).getFuture();
            }
        };

        futureDay.setColor( Themes.GREEN_LIGHT_3 );
        futureDay.setStokeSize( 2.25f );

        // Week fut
        MyTimeSeries futureWeek = new MyTimeSeries( "Fut week", client ) {
            @Override
            public double getData() {
                return exps.getExp( ExpStrings.week ).getFuture();
            }
        };

        futureWeek.setColor( Themes.GREEN_LIGHT_2 );
        futureWeek.setStokeSize( 2.25f );

        // Month fut
        MyTimeSeries futureMonth = new MyTimeSeries( "Fut month", client ) {
            @Override
            public double getData() {
                return exps.getExp( ExpStrings.month ).getFuture();
            }
        };

        futureMonth.setColor( Themes.GREEN_LIGHT );
        futureMonth.setStokeSize( 2.25f );

        // E1 fut
        MyTimeSeries futureQuarter = new MyTimeSeries( "Fut E1", client ) {
            @Override
            public double getData() {
                return exps.getExp( ExpStrings.e1 ).getFuture();
            }
        };

        futureQuarter.setColor( Themes.GREEN );
        futureQuarter.setStokeSize( 2.25f );

        // E2 fut
        MyTimeSeries futureQuarterFar = new MyTimeSeries( "Fut E2", client ) {
            @Override
            public double getData() {
                return exps.getExp( ExpStrings.e2 ).getFuture();
            }
        };

        futureQuarterFar.setColor( Themes.DARK_GREEN );
        futureQuarterFar.setStokeSize( 2.25f );


        // Series
        MyTimeSeries[] series = { index, bid, ask, futureDay, futureWeek, futureMonth, futureQuarter, futureQuarterFar };

        // Chart
        MyChart chart = new MyChart( client, series, props );

        // ----- Charts ----- //
        MyChart[] charts = { chart };

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer( client, charts, getClass( ).getName( ) );
        chartContainer.create( );
    }

}
