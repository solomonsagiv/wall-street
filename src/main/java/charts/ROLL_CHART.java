package charts;

import locals.Themes;
import options.OptionsEnum;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.data.xy.XYSeries;
import serverObjects.BASE_CLIENT_OBJECT;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ROLL_CHART implements IChartCreator {

    BASE_CLIENT_OBJECT client;
    MySingleFreeChart[] singleFreeCharts;
    MySingleFreeChart chart;
    XYSeries[] series;
    Color[] colors;
    ArrayList< ArrayList< Double > > lists;

    // Constructor
    public ROLL_CHART( BASE_CLIENT_OBJECT client ) {

        this.client = client;
        singleFreeCharts = new MySingleFreeChart[ 1 ];

    }

    @Override
    public void createChart() {

        // ---------- Index ---------- //
        // Params
        series = new XYSeries[ 2 ];
        series[ 0 ] = new XYSeries( "month" );
        series[ 1 ] = new XYSeries( "quarter" );

        colors = new Color[ 2 ];
        colors[ 0 ] = Themes.GREEN;
        colors[ 1 ] = Themes.GREEN_LIGHT;

        Map< String, List<Double> > map = new HashMap<>();
        map.put( "month", client.getOptionsHandler().getOptions( OptionsEnum.MONTH ).getOpAvgList() );
        map.put( "quarter", client.getOptionsHandler().getOptions( OptionsEnum.QUARTER ).getOpAvgList() );

        Marker marker = new ValueMarker(0);

        // Create chart
        chart = new MySingleFreeChart( client, series, colors, 1, map, 0, false, 0, 2.5f, true, true, true, marker );

        singleFreeCharts[ 0 ] = chart;

        // Display chart
        MyFreeChart myFreeChart = new MyFreeChart( singleFreeCharts, client, getClass( ).getName( ) );
        myFreeChart.pack( );
        myFreeChart.setVisible( true );

    }

}
