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
	ArrayList < ArrayList < Double > > lists;

	// Constructor
	public CONTRACT_IND_CHART( BASE_CLIENT_OBJECT client ) {

		this.client = client;
		singleFreeCharts = new MySingleFreeChart[ 1 ];

	}

	public static void main( String[] args ) throws InterruptedException {

		TestChartWindow window = new TestChartWindow();
		window.frame.setVisible( true );

		SpxCLIENTObject dax = SpxCLIENTObject.getInstance();

		CONTRACT_IND_CHART avg = new CONTRACT_IND_CHART( dax );
		avg.createChart();

		ArrayList < Double > indexList = dax.getListMap().get( "index" ).getAsDoubleList();
		ArrayList < Double > contractList = dax.getListMap().get( "contract" ).getAsDoubleList();
		ArrayList < Double > indexBidList = dax.getListMap().get( "indexBid" ).getAsDoubleList();
		ArrayList < Double > indexAskList = dax.getListMap().get( "indexAsk" ).getAsDoubleList();

		while ( true ) {
			try {

				if ( !window.indField.getText().isEmpty() ) {
					double ind = Double.parseDouble( window.indField.getText() );
					double fut = Double.parseDouble( window.futField.getText() );

//					double ind = new Random().nextDouble() * 10;
//					double fut = new Random().nextDouble() * 10;

					indexList.add( ind );
					contractList.add( fut );
					indexBidList.add( ind - 1.2 );
					indexAskList.add( ind + 1.2 );
				}
			} catch ( Exception e ) {
				e.printStackTrace();
			}

			Thread.sleep( 1000 );

		}
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

		Map < String, MyList > map = new HashMap < String, MyList >();
		map = new HashMap < String, MyList >();
		map.put( "index" , client.getListMap().get( "index" ) );
		map.put( "contract" , client.getListMap().get( "contract" ) );
		map.put( "indexBid" , client.getListMap().get( "indexBid" ) );
		map.put( "indexAsk" , client.getListMap().get( "indexAsk" ) );

		// Create chart
		chart = new MySingleFreeChart( client , series , colors , 0.17 , map , 180 , false , 0 , 2.5f , false , false , null );

		singleFreeCharts[ 0 ] = chart;

		// Display chart
		MyFreeChart myFreeChart = new MyFreeChart( singleFreeCharts , client , getClass().getName() );
		myFreeChart.pack();
		myFreeChart.setVisible( true );

	}

}
