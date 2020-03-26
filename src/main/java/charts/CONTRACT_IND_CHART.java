package charts;

import locals.Themes;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.xy.XYSeries;
import serverObjects.BASE_CLIENT_OBJECT;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CONTRACT_IND_CHART implements IChartCreator {

    BASE_CLIENT_OBJECT client;
    MySingleFreeChart[] singleFreeCharts;
    MySingleFreeChart chart;
    TimeSeries[] series;
    Color[] colors;
    ArrayList< ArrayList< Double > > lists;

    // Constructor
    public CONTRACT_IND_CHART( BASE_CLIENT_OBJECT client ) {

        this.client = client;
        singleFreeCharts = new MySingleFreeChart[ 1 ];

    }

    @Override
    public void createChart() {

        // ---------- Index ---------- //
        // Params
        series = new TimeSeries[ 4 ];
        series[ 0 ] = new TimeSeries( "index" );
        series[ 1 ] = new TimeSeries( "contract" );
        series[ 2 ] = new TimeSeries( "indexBid" );
        series[ 3 ] = new TimeSeries( "indexAsk" );

        colors = new Color[ 4 ];
        colors[ 0 ] = Color.BLACK;
        colors[ 1 ] = Themes.GREEN;
        colors[ 2 ] = Themes.BLUE;
        colors[ 3 ] = Themes.RED;

        Map< String, List<Double> > map = new HashMap<>();
        map.put( "index", client.getIndexList() );
        map.put( "contract",  client.getOptionsHandler().getMainOptions().getConList() );
        map.put( "indexBid", client.getIndexBidList() );
        map.put( "indexAsk", client.getIndexAskList() );

        // Create chart
        chart = new MySingleFreeChart( client, series, colors, 0.17, map, 180, false, 0, 2.5f, false, false, false, null );

        singleFreeCharts[ 0 ] = chart;

        // Display chart
        MyFreeChart myFreeChart = new MyFreeChart( singleFreeCharts, client, getClass( ).getName( ) );
        myFreeChart.pack( );
        myFreeChart.setVisible( true );

    }

}
