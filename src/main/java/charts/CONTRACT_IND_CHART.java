package charts;

import lists.MyList;
import locals.Themes;
import org.jfree.data.xy.XYSeries;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.SpxCLIENTObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CONTRACT_IND_CHART implements IChartCreator {

    BASE_CLIENT_OBJECT client;
    MySingleFreeChart[] singleFreeCharts;
    MySingleFreeChart chart;
    XYSeries[] series;
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

        Map< String, MyList > map = new HashMap< String, MyList >( );
        map = new HashMap< String, MyList >( );
        map.put( "index", ( MyList ) client.getIndexList().getList() );
        map.put( "contract", ( MyList ) client.getOptionsHandler().getMainOptions().getContractList().getList() );
        map.put( "indexBid", ( MyList ) client.getIndexBidList().getList() );
        map.put( "indexAsk", ( MyList ) client.getIndexAskList().getList() );

        // Create chart
        chart = new MySingleFreeChart( client, series, colors, 0.17, map, 180, false, 0, 2.5f, false, false, true, null );

        singleFreeCharts[ 0 ] = chart;

        // Display chart
        MyFreeChart myFreeChart = new MyFreeChart( singleFreeCharts, client, getClass( ).getName( ) );
        myFreeChart.pack( );
        myFreeChart.setVisible( true );

    }

}
