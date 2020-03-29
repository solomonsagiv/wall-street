package charts;

import charts.myChart.*;
import charts.myChart.MyChart;
import org.jfree.chart.plot.Marker;
import serverObjects.indexObjects.Spx;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TestNewChart {

    public static void main( String[] args ) {
        TestNewChart testNewChart = new TestNewChart();
        testNewChart.create();
    }

    public void create() {
        Spx spx = Spx.getInstance();

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
            public double getMarginMaxMain() {
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
        };

        // Index
        MyTimeSeries indexSerie = new MyTimeSeries( "Index", Color.BLACK, props.getStrokeSize(), props, spx.getIndexList() ) {
            @Override
            public double getData() {
                return 0;
            }
        };

        Map< MySeriesEnum, MyTimeSeries > seriesMap = new HashMap<>();
        seriesMap.put( MySeriesEnum.INDEX, indexSerie );

        MyChart myChart = new MyChart( spx, seriesMap, props );

        MyChart[] charts = {myChart};

        MyChartContainer myChartContainer = new MyChartContainer( spx, charts , "4Lines" );

        myChartContainer.pack();
        myChartContainer.setVisible( true );

    }



}
