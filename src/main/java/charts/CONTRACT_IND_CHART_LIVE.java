package charts;

import locals.Themes;
import org.jfree.data.xy.XYSeries;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.SpxCLIENTObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class CONTRACT_IND_CHART_LIVE implements IChartCreator {

    BASE_CLIENT_OBJECT client;
    MySingleFreeChartLive[] singleFreeChartsLive;
    MySingleFreeChartLive chart;
    XYSeries[] series;
    Color[] colors;
    ArrayList< String > list;

    // Constructor
    public CONTRACT_IND_CHART_LIVE( BASE_CLIENT_OBJECT client ) {

        this.client = client;
        singleFreeChartsLive = new MySingleFreeChartLive[ 1 ];

    }


    @Override
    public void createChart() {

        // ---------- Index ---------- //
        // Params
        series = new XYSeries[ 4 ];
        series[ 0 ] = new XYSeries( "index" );
        series[ 1 ] = new XYSeries( "contract" );
        series[ 2 ] = new XYSeries( "indexBid" );
        series[ 3 ] = new XYSeries( "indexAsk" );

        colors = new Color[ 4 ];
        colors[ 0 ] = Color.BLACK;
        colors[ 1 ] = Themes.GREEN;
        colors[ 2 ] = Themes.BLUE;
        colors[ 3 ] = Themes.RED;

        // Create map
        list = new ArrayList<>( );
        list.add( "index" );
        list.add( "contract" );
        list.add( "indexBid" );
        list.add( "indexAsk" );

        // Create chart
        chart = new MySingleFreeChartLive( client, series, colors, .2, list, 150, false, 0, 1.5f, false, null );

        singleFreeChartsLive[ 0 ] = chart;

        // Display chart
        MyFreeChartLive myFreeChart = new MyFreeChartLive( singleFreeChartsLive, client, getClass( ).getName( ) );
        myFreeChart.pack( );
        myFreeChart.setVisible( true );

    }

}
