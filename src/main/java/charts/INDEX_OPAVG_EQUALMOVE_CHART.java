package charts;

import lists.MyList;
import locals.Themes;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.data.xy.XYSeries;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.DaxCLIENTObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class INDEX_OPAVG_EQUALMOVE_CHART implements IChartCreator {

	BASE_CLIENT_OBJECT client;
	MySingleFreeChart[] singleFreeCharts;
	MySingleFreeChart chart;
	XYSeries[] series;
	Color[] colors;
	ArrayList < ArrayList < Double > > lists;

	// Constructor
	public INDEX_OPAVG_EQUALMOVE_CHART( BASE_CLIENT_OBJECT client ) {

		this.client = client;
		singleFreeCharts = new MySingleFreeChart[ 3 ];

	}

	public static void main( String[] args ) throws InterruptedException {

		TestChartWindow window = new TestChartWindow();
		window.frame.setVisible( true );

		DaxCLIENTObject client = DaxCLIENTObject.getInstance();

		INDEX_OPAVG_EQUALMOVE_CHART avg = new INDEX_OPAVG_EQUALMOVE_CHART( client );
		avg.createChart();

		ArrayList < Double > indexList = client.getListMap().get( "index" ).getAsDoubleList();
		ArrayList < Double > opList = client.getListMap().get( "op" ).getAsDoubleList();
		ArrayList < Double > equalMoveList = client.getListMap().get( "equalMove" ).getAsDoubleList();

		while ( true ) {
			try {

				if ( !window.indField.getText().isEmpty() ) {
					double ind = Double.parseDouble( window.indField.getText() );
					double fut = Double.parseDouble( window.futField.getText() );
					indexList.add( ind );
					opList.add( fut );
					equalMoveList.add( ind - 1.2 );
				}
			} catch ( Exception e ) {
				e.printStackTrace();
			}

			Thread.sleep( 1000 );

		}
	}

	@Override
	public void createChart() {

		// Zero marker
		Marker marker = new ValueMarker( 0 );
		marker.setPaint( Color.BLACK );

		// ---------- Index ---------- //
		// Params
		series = new XYSeries[ 1 ];
		series[ 0 ] = new XYSeries( "index" );

		colors = new Color[ 1 ];
		colors[ 0 ] = Color.BLACK;

		Map < String, MyList > map = new HashMap < String, MyList >();
		map.put( "index" , client.getListMap().get( "index" ) );

		// Create chart
		chart = new MySingleFreeChart( client , series , colors , 1 , map , 0 , true , 0 , 1.5f , true , false , null );

		singleFreeCharts[ 0 ] = chart;

		// ---------- Op avg ---------- //
		// Params
		series = new XYSeries[ 1 ];
		series[ 0 ] = new XYSeries( "opAvg" );

		colors = new Color[ 1 ];
		colors[ 0 ] = Themes.BLUE;

		map = new HashMap < String, MyList >();
		map.put( "index" , client.getListMap().get( "opAvg" ) );

		// Create chart
		chart = new MySingleFreeChart( client , series , colors , .05 , map , 0 , true , 0 , 1.5f , true , false , marker );

		singleFreeCharts[ 1 ] = chart;

		// ---------- Equal move ---------- //
		// Params
		series = new XYSeries[ 1 ];
		series[ 0 ] = new XYSeries( "equalMove" );

		colors = new Color[ 1 ];
		colors[ 0 ] = Themes.GREEN;

		map = new HashMap < String, MyList >();
		map.put( "equalMove" , client.getListMap().get( "equalMove" ) );

		// Create chart
		chart = new MySingleFreeChart( client , series , colors , .5 , map , 0 , true , 0 , 1.5f , true , false , marker );

		singleFreeCharts[ 2 ] = chart;

		// Display chart
		MyFreeChart myFreeChart = new MyFreeChart( singleFreeCharts , client , getClass().getName() );
		myFreeChart.pack();
		myFreeChart.setVisible( true );
	}

}
