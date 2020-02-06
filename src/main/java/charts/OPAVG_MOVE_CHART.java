package charts;

import lists.MyList;
import locals.Themes;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.data.xy.XYSeries;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.SpxCLIENTObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OPAVG_MOVE_CHART implements IChartCreator {

    BASE_CLIENT_OBJECT client;
    MySingleFreeChart[] singleFreeCharts;
    MySingleFreeChart chart;
    XYSeries[] series;
    Color[] colors;
    ArrayList< ArrayList< Double > > lists;

    // Constructor
    public OPAVG_MOVE_CHART( BASE_CLIENT_OBJECT client ) {

        this.client = client;
        singleFreeCharts = new MySingleFreeChart[ 1 ];

    }

    @Override
    public void createChart() {
        
        // ---------- Index ---------- //
        // Params
        series = new XYSeries[ 1 ];
        series[ 0 ] = new XYSeries( "opAvgMove" );

        colors = new Color[ 1 ];
        colors[ 0 ] = Themes.PURPLE;

        // Zero marker
        Marker marker = new ValueMarker( 0 );
        marker.setPaint( Color.BLACK );

        Map< String, MyList > map = new HashMap< String, MyList >( );
        map.put( "opAvgMove", client.getOptionsHandler().getMainOptions().getOpAvgEqualMoveCalculator().getMoveList() );

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


