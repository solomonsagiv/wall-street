package charts.myCharts;

import charts.myChart.*;
import locals.L;
import locals.Themes;
import options.OptionsEnum;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;

import java.awt.*;
import java.util.Random;
import java.util.Scanner;

public class IndexVsQuarterQuarterFarLiveChart extends MyChartCreator {


    public static void main( String[] args ) throws InterruptedException {
        Spx spx = Spx.getInstance();
        IndexVsQuarterQuarterFarLiveChart testNewChart = new IndexVsQuarterQuarterFarLiveChart(spx);
        testNewChart.createChart();

        while (true) {

            System.out.println( "Enter future: " );
            String input = new Scanner( System.in ).nextLine();

            double d = new Random(  ).nextDouble() * 10;

            if ( !input.isEmpty() ) {
                d = L.dbl( input );
            }

            spx.setIndex( d );
            spx.setIndexBid( d - 2 );
            spx.setIndexAsk( d + 1 );
            spx.getOptionsHandler().getOptions( OptionsEnum.QUARTER ).setContract( d - 1 );
            spx.getOptionsHandler().getOptions( OptionsEnum.QUARTER_FAR ).setContract( d + 2 );

            Thread.sleep(200);
        }

    }

    // Constructor
    public IndexVsQuarterQuarterFarLiveChart( BASE_CLIENT_OBJECT client ) {
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
        props.setProp( ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS,(double) INFINITE );
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
        MyTimeSeries quarter = new MyTimeSeries( "Quarter", Themes.GREEN, 2.25f, props, null ) {
            @Override
            public double getData() {
                return client.getOptionsHandler().getOptions( OptionsEnum.QUARTER ).getContract();
            }
        };

        // Future
        MyTimeSeries quarterFar = new MyTimeSeries( "QuarterFar", Themes.VERY_LIGHT_BLUE, 2.25f, props, null ) {
            @Override
            public double getData() {
                return client.getOptionsHandler().getOptions( OptionsEnum.QUARTER_FAR ).getContract();
            }
        };

        MyTimeSeries[] series = {index, bid, ask, quarter, quarterFar};

        // Chart
        MyChart chart = new MyChart( client, series, props );

        // ----- Charts ----- //
        MyChart[] charts = { chart };

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer( client, charts, getClass().getName() );
        chartContainer.create();


    }

}
