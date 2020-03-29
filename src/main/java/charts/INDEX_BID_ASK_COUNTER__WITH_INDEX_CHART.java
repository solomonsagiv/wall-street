package charts;

import lists.MyChartList;
import locals.Themes;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.data.time.TimeSeries;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;

import java.awt.*;
import java.util.*;
import java.util.List;

public class INDEX_BID_ASK_COUNTER__WITH_INDEX_CHART implements IChartCreator {

    public static void main(String[] args) throws InterruptedException {
        INDEX_BID_ASK_COUNTER__WITH_INDEX_CHART chart = new INDEX_BID_ASK_COUNTER__WITH_INDEX_CHART(Spx.getInstance());
        chart.createChart();

        while (true) {
            Thread.sleep(1000);
            Spx.getInstance().getIndexList().add(new Random().nextDouble()*100);
            Spx.getInstance().getIndexBidAskCounterList().add(new Random().nextDouble()*100);
        }
    }


    BASE_CLIENT_OBJECT client;
    MySingleFreeChart[] singleFreeCharts;
    MySingleFreeChart chart;
    TimeSeries[] series;
    Color[] colors;
    ArrayList< ArrayList< Double > > lists;

    // Constructor
    public INDEX_BID_ASK_COUNTER__WITH_INDEX_CHART( BASE_CLIENT_OBJECT client ) {

        this.client = client;
        singleFreeCharts = new MySingleFreeChart[ 2 ];

    }

    @Override
    public void createChart() {

        // ---------- Index ---------- //
        // Params
        series = new TimeSeries[ 1 ];
        series[ 0 ] = new TimeSeries( "Index" );

        colors = new Color[ 1 ];
        colors[ 0 ] = Color.BLACK;

        // Zero marker
        Marker marker = new ValueMarker( 0 );
        marker.setPaint( Color.BLACK );

        Map< String, MyChartList> map = new HashMap<>( );
        map.put( "Index", client.getIndexList() );

        // Create chart
        chart = new MySingleFreeChart( client, series, colors, 1, map, 0, true, 0, 1.5f, false, true
                , false, marker );

        singleFreeCharts[ 0 ] = chart;

        // ---------- Index bid ask counter ---------- //
        // Params
        series = new TimeSeries[ 1 ];
        series[ 0 ] = new TimeSeries( "IndexBidAskCounter" );

        colors = new Color[ 1 ];
        colors[ 0 ] = Themes.ORANGE;

        map = new HashMap<>( );
        map.put( "IndexBidAskCounter", client.getIndexBidAskCounterList() );

        // Create chart
        chart = new MySingleFreeChart( client, series, colors, 1, map, 0, true, 0, 1.5f, false, true
                , false, marker );

        singleFreeCharts[ 1 ] = chart;

        // Display chart
        MyFreeChart myFreeChart = new MyFreeChart( singleFreeCharts, client, getClass( ).getName( ) );
        myFreeChart.pack( );
        myFreeChart.setVisible( true );

    }

}

