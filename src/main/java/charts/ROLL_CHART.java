package charts;

import charts.myChart.MyChart;
import charts.myChart.MyChartContainer;
import locals.Themes;
import options.OptionsEnum;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.data.time.TimeSeries;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ROLL_CHART implements IChartCreator {

    public static void main(String[] args) {
        ROLL_CHART roll_chart = new ROLL_CHART(Spx.getInstance());
        roll_chart.createChart();
    }

    BASE_CLIENT_OBJECT client;
    MyChart[] singleFreeCharts;
    MyChart chart;
    TimeSeries[] series;
    Color[] colors;
    ArrayList< ArrayList< Double > > lists;

    // Constructor
    public ROLL_CHART( BASE_CLIENT_OBJECT client ) {

        this.client = client;
//        singleFreeCharts = new MyChart[ 1 ];

    }

    @Override
    public void createChart() {
//
//        // ---------- Index ---------- //
//        // Params
//        series = new TimeSeries[ 2 ];
//        Comparable name;
//        series[ 0 ] = new TimeSeries( "quarter" );
//        series[ 1 ] = new TimeSeries( "quarterFar" );
//
//        colors = new Color[ 2 ];
//        colors[ 0 ] = Themes.GREEN;
//        colors[ 1 ] = Themes.GREEN_LIGHT;
//
//        Map< String, List<Double> > map = new HashMap<>();
//        map.put( "quarter", client.getOptionsHandler().getOptions( OptionsEnum.QUARTER ).getOpList() );
//        map.put( "quarterFar", client.getOptionsHandler().getOptions( OptionsEnum.QUARTER_FAR ).getOpList() );
//
//        Marker marker = new ValueMarker(0);
//        marker.setStroke( new BasicStroke(1.5f) );
//        marker.setPaint( Color.BLACK );
//
//        // Create chart
////        chart = new MyChart( client, series, colors, 1, map, 0, false, 0, 2.5f, true, true, false, marker );
//
//        singleFreeCharts[ 0 ] = chart;
//
//        // Display chart
//        MyChartContainer myFreeChart = new MyChartContainer( singleFreeCharts, client, getClass( ).getName( ) );
//        myFreeChart.pack( );
//        myFreeChart.setVisible( true );

    }

}
