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

public class INDEX_RACES_CHART implements IChartCreator {

    BASE_CLIENT_OBJECT client;
    MySingleFreeChart[] singleFreeCharts;
    MySingleFreeChart chart;
    XYSeries[] series;
    Color[] colors;
    ArrayList< ArrayList< Double > > lists;

    // Constructor
    public INDEX_RACES_CHART( BASE_CLIENT_OBJECT client ) {

        this.client = client;
        singleFreeCharts = new MySingleFreeChart[ 1 ];

    }

    public static void main( String[] args ) throws InterruptedException {

        TestChartWindow window = new TestChartWindow( );
        window.frame.setVisible( true );

        SpxCLIENTObject dax = SpxCLIENTObject.getInstance( );

        CONTRACT_IND_CHART avg = new CONTRACT_IND_CHART( dax );
        avg.createChart( );

        ArrayList< Double > indexList = dax.getListMap( ).get( MyList.INDEX ).getAsDoubleList( );

        while ( true ) {
            try {

                if ( !window.indField.getText( ).isEmpty( ) ) {
                    double ind = Double.parseDouble( window.indField.getText( ) );
                    double fut = Double.parseDouble( window.futField.getText( ) );

//					double ind = new Random().nextDouble() * 10;
//					double fut = new Random().nextDouble() * 10;
                    indexList.add( ind );
                }
            } catch ( Exception e ) {
                e.printStackTrace( );
            }

            Thread.sleep( 1000 );

        }
    }

    @Override
    public void createChart() {

        // ---------- Index ---------- //
        // Params
        series = new XYSeries[ 1 ];
        series[ 0 ] = new XYSeries( "indexRaces" );

        colors = new Color[ 1 ];
        colors[ 0 ] = Themes.BLUE;

        // Zero marker
        Marker marker = new ValueMarker( 0 );
        marker.setPaint( Color.BLACK );

        Map< String, MyList > map = new HashMap< String, MyList >( );
        map = new HashMap< String, MyList >( );
        map.put( "indexRaces", client.getListMap( ).get( MyList.INDEX_RACES ) );

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

