package charts;

import locals.Themes;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.xy.XYSeries;
import serverObjects.BASE_CLIENT_OBJECT;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class INDEX_RACES_CHART implements IChartCreator {

    BASE_CLIENT_OBJECT client;
    MySingleFreeChart[] singleFreeCharts;
    MySingleFreeChart chart;
    TimeSeries[] series;
    Color[] colors;
    ArrayList< ArrayList< Double > > lists;

    // Constructor
    public INDEX_RACES_CHART( BASE_CLIENT_OBJECT client ) {

        this.client = client;
        singleFreeCharts = new MySingleFreeChart[ 1 ];

    }

    @Override
    public void createChart() {

        // ---------- Index ---------- //
        // Params
        series = new TimeSeries[ 1 ];
        series[ 0 ] = new TimeSeries( "indexRaces" );

        colors = new Color[ 1 ];
        colors[ 0 ] = Themes.BLUE;

        // Zero marker
        Marker marker = new ValueMarker( 0 );
        marker.setPaint( Color.BLACK );

        Map< String, List<Double> > map = new HashMap< String, List<Double> >( );
        map.put( "indexRaces", client.getIndexRacesList() );

        // Create chart
        chart = new MySingleFreeChart( client, series, colors, 1, map, 0, true, 0, 1.5f, false, true
                , false, marker );

        singleFreeCharts[ 0 ] = chart;

        // Display chart
        MyFreeChart myFreeChart = new MyFreeChart( singleFreeCharts, client, getClass( ).getName( ) );
        myFreeChart.pack( );
        myFreeChart.setVisible( true );

    }

}

