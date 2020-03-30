package charts;

import charts.myChart.*;
import locals.L;
import locals.Themes;
import options.OptionsEnum;
import org.jfree.chart.plot.Marker;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;

import java.awt.*;
import java.util.Random;
import java.util.Scanner;

public class IndexVsContractLive extends MyChartCreator {


    public static void main( String[] args ) throws InterruptedException {
        Spx spx = Spx.getInstance();
        IndexVsContractLive testNewChart = new IndexVsContractLive(spx);
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
            Thread.sleep(200);
        }

    }


    // Constructor
    public IndexVsContractLive( BASE_CLIENT_OBJECT client ) {
        super( client );
    }

    @Override
    public void createChart() {



        // ----- Chart 1 ----- //
        // Index
        MyTimeSeries index = new MyTimeSeries( "Index", Color.BLACK, props.getStrokeSize(), props, client.getIndexList() ) {
            @Override
            public double getData() {
                return client.getIndex();
            }
        };

        // Index
        MyTimeSeries bid = new MyTimeSeries( "Bid", Themes.BLUE, props.getStrokeSize(), props, client.getIndexBidList() ) {
            @Override
            public double getData() {
                return client.getIndexBid();
            }
        };

        // Index
        MyTimeSeries ask = new MyTimeSeries( "Ask", Themes.RED, props.getStrokeSize(), props, client.getIndexAskList() ) {
            @Override
            public double getData() {
                return client.getIndexAsk();
            }
        };

        // Future
        MyTimeSeries future = new MyTimeSeries( "Index", Themes.GREEN, props.getStrokeSize(), props, null ) {
            @Override
            public double getData() {
                return client.getOptionsHandler().getOptions( OptionsEnum.QUARTER ).getContract();
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


    // Props
    MyChartProps props = new MyChartProps( ) {
        @Override
        public int getSeconds() {
            return 150;
        }

        @Override
        public boolean isIncludeTicker() {
            return false;
        }

        @Override
        public double getMarginMaxMin() {
            return .17;
        }

        @Override
        public float getStrokeSize() {
            return 2.25f;
        }

        @Override
        public double getRangeMargin() {
            return 0;
        }

        @Override
        public boolean isGridLineVisible() {
            return false;
        }

        @Override
        public boolean isLoadFromDB() {
            return false;
        }

        @Override
        public Marker getMarker() {
            return null;
        }

        @Override
        public boolean isLive() {
            return true;
        }

        @Override
        public int getSleep() {
            return 200;
        }

        @Override
        public double getChartHighInDots() {
            return client.getIndex() * 0.006;
        }

        @Override
        public int getSecondsOnMess() {
            return 10;
        }
    };

}
