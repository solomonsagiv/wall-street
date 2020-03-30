package charts;

import charts.myChart.*;
import locals.L;
import locals.Themes;
import options.OptionsEnum;
import org.jfree.chart.plot.Marker;
import serverObjects.indexObjects.Spx;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class TestNewChart {


    public static void main( String[] args ) throws InterruptedException {
        Spx spx = Spx.getInstance();
        TestNewChart testNewChart = new TestNewChart(spx);
        testNewChart.create();

        while (true) {

            System.out.println( "Enter future: " );
            String input = new Scanner( System.in ).nextLine();

            double d = new Random(  ).nextDouble() * 10;

            if ( !input.isEmpty() ) {
                d = L.dbl( input );
            }

            spx.setIndex( d );
            spx.getOptionsHandler().getOptions( OptionsEnum.QUARTER ).setContract( d - 1 );
            Thread.sleep(200);
        }

    }

    Spx spx;

    public TestNewChart(Spx spx) {
        this.spx = spx;
    }

    public void create() {

        // Props
        MyChartProps props = new MyChartProps( ) {
            @Override
            public int getSeconds() {
                return 30;
            }

            @Override
            public boolean isIncludeTicker() {
                return false;
            }

            @Override
            public double getMarginMaxMin() {
                return 0.17;
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
                return 25;
            }

            @Override
            public int getSecondsOnMess() {
                return 10;
            }
        };

        // Index
        MyTimeSeries indexSerie = new MyTimeSeries( "Index", Color.BLACK, props.getStrokeSize(), props, spx.getIndexList() ) {
            @Override
            public double getData() {
                return spx.getIndex();
            }
        };

        // Index
        MyTimeSeries futureSerie = new MyTimeSeries( "Future", Themes.GREEN, props.getStrokeSize(), props, null ) {
            @Override
            public double getData() {
                return spx.getOptionsHandler().getOptions( OptionsEnum.QUARTER ).getContract();
            }
        };


        MyTimeSeries[] series = {};

        Map< MySeriesEnum, MyTimeSeries > seriesMap = new HashMap<>();
        seriesMap.put( MySeriesEnum.INDEX, indexSerie );
        seriesMap.put( MySeriesEnum.QUARTER_CONTRACT, futureSerie );

//        MyChart myChart = new MyChart( spx, seriesMap, props );

//        MyChart[] charts = {myChart};

//        MyChartContainer myChartContainer = new MyChartContainer( spx, charts , "4Lines" );

//        myChartContainer.pack();
//        myChartContainer.setVisible( true );

    }



}
