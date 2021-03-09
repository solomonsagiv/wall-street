package charts.myCharts;

import charts.myChart.*;
import exp.Exp;
import exp.Exps;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;
import java.awt.*;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class FuturesChart extends MyChartCreator {

    // Constructor
    public FuturesChart( BASE_CLIENT_OBJECT client ) {
        super( client );
    }

    ArrayList<MyTimeSeries> myTimeSeries = new ArrayList<>();

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
        
        // Futures
        Color green = Themes.DARK_GREEN;

        for ( Exp exp: exps.getExpList() ) {

            MyTimeSeries myTimeSerie = new MyTimeSeries( "Fut " + exp.getName(), client ) {
                @Override
                public double getData() throws UnknownHostException {
                    return exp.getFuture();
                }
            };

            myTimeSerie.setStokeSize( 2.25f );
            myTimeSerie.setColor( green );
            green = green.brighter();

            myTimeSeries.add( myTimeSerie );

        }

        myTimeSeries.add( index );
        myTimeSeries.add( bid );
        myTimeSeries.add( ask );

        // Series
        MyTimeSeries[] series = toArray();

        // Chart
        MyChart chart = new MyChart( client, series, props );

        // ----- Charts ----- //
        MyChart[] charts = { chart };

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer( client, charts, getClass( ).getName( ) );
        chartContainer.create( );
    }

    private MyTimeSeries[] toArray() {
        MyTimeSeries[] arr = new MyTimeSeries[myTimeSeries.size()];
        for ( int i = 0; i < arr.length; i++ ) {
            arr[i] = myTimeSeries.get( i );
        }
        return arr;
    }


}
