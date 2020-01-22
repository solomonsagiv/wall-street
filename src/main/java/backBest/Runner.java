package backBest;

import dataBase.HB;
import options.Option;
import options.Strike;
import serverObjects.BASE_CLIENT_OBJECT;
import tables.IndexTableDay;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Runner extends Thread {

	Color green = new Color( 0 , 128 , 0 );
	Color red = new Color( 229 , 19 , 0 );

	BazkTestDailyWindow window;

	ArrayList < IndexTableDay > lines;
	BASE_CLIENT_OBJECT client;

	public Runner( BazkTestDailyWindow window , BASE_CLIENT_OBJECT client ) {
		this.window = window;
		lines = new ArrayList <>();
		this.client = client;
		// chart = new BackTestChart();
		// chart.pack();
		// chart.setVisible(true);
	}

	@Override
	public void run() {

		int id = 0;

		String query = String.format( "from %s where date='%s'" , client.getTables().getTableDay().getClass().getName() ,
				window.getSourceFile().getText() );

		lines = ( ArrayList < IndexTableDay > ) HB.getTableList( client.getSessionfactory() , query );

		for ( int row = 0 ; row < lines.size() ; row++ ) {

			IndexTableDay line = lines.get( row );

			id = line.getId();

			try {

				// Update data client
				updateDataToClientFromDB( line );

				// Update text to window
				updateTextToWindow();

				// Sleep
				sleep( Integer.parseInt( window.getSleep().getText() ) );
			} catch ( NumberFormatException e ) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch ( InterruptedException e ) {
				// TODO Auto-generated catch block
			}

		}

	}

	private void updateTextToWindow() {

		// Ticker
		window.getIndex().setText( str( client.getIndex() ) );
		window.getFuture().setText( str( client.getDbContract() ) );
		window.getHigh().setText( str( client.getHigh() ) );
		window.getLow().setText( str( client.getLow() ) );
		window.getOpen().setText( str( client.getOpen() ) );

		// Races
		window.getFuture_up().setText( str( client.getConUp() ) );
		window.getFuture_down().setText( str( client.getConDown() ) );
		window.getIndex_up().setText( str( client.getIndexUp() ) );
		window.getIndex_down().setText( str( client.getIndexDown() ) );

		window.getIndex_sum().setText( str( client.getIndexSum() ) );
		window.getFuture_sum().setText( str( client.getFutSum() ) );

		// Calc data
		double openPresent = floor( ( ( client.getOpen() - client.getBase() ) / client.getBase() ) * 100 );
		double highPresent = floor( ( ( client.getHigh() - client.getBase() ) / client.getBase() ) * 100 );
		double lowPresent = floor( ( ( client.getLow() - client.getBase() ) / client.getBase() ) * 100 );
		double lastPresent = floor( ( ( client.getIndex() - client.getBase() ) / client.getBase() ) * 100 );

		setTextWithForfColorDouble( window.getOpen_present() , openPresent , "%" );
		setTextWithForfColorDouble( window.getHigh_present() , highPresent , "%" );
		setTextWithForfColorDouble( window.getLow_present() , lowPresent , "%" );
		setTextWithForfColorDouble( window.getIndex_present() , lastPresent , "%" );

		// Op
		setTextWithForfColorDouble( window.getOpAvgField() , client.getOpAvgFromDb() , "" );
//		setTextWithForfColorDouble( window.getOpField() , client.getMainOptions ().getOp() , "" );
	}

	private void updateOptionsToWindow() {

		try {

			ArrayList < Strike > strikes = ( ArrayList < Strike > ) client.getOptionsHandler().getOptionsDay().getStrikes();

			System.out.println( strikes.size() );

			for ( int row = 0 ; row < window.getTable().getRowCount() ; row++ ) {

				Strike strike = strikes.get( row );
				Option call = strikes.get( row ).getCall();
				Option put = strikes.get( row ).getPut();

				window.getTable().setValueAt( call.getBidAskCounter() , row , 0 );
				window.getTable().setValueAt( strike.getStrike() , row , 0 );
				window.getTable().setValueAt( put.getBidAskCounter() , row , 0 );

			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}

	}

	private void updateDataToClientFromDB( IndexTableDay table ) {

		// Open
		if ( client.getOpen() == 0 ) {
			client.setOpen( table.getIndex() );
		}

		// High
		if ( client.getHigh() == 0 ) {
			client.setHigh( table.getIndex() );
		} else if ( table.getIndex() > client.getHigh() ) {
			client.setHigh( table.getIndex() );
		}

		// Low
		if ( client.getLow() == 0 ) {
			client.setLow( table.getIndex() );
		} else if ( table.getIndex() < client.getLow() ) {
			client.setLow( table.getIndex() );
		}

		client.setIndex( table.getIndex() );
		client.setDbContract( table.getCon() );
		client.setConUp( table.getCon_up() );
		client.setConDown( table.getCon_down() );
		client.setIndexUp( table.getIndex_up() );
		client.setIndexDown( table.getIndex_down() );
		client.setBase( table.getBase() );

		// Op
		client.setOpAvgFromDb( table.getOpAvg() );

		// Options
		client.setOpAvgFromDb( table.getOpAvg() );
		client.getOptionsHandler().getOptionsDay().setContractBidAskCounter( table.getCon_bid_ask_counter() );
		client.setFutureBidAskCounter( table.getFuture_bid_ask_counter() );

	}

	private void colorRaces( JTextField field ) {
		window.getFuture_up().setBackground( Color.WHITE );
		window.getFuture_down().setBackground( Color.WHITE );
		window.getIndex_up().setBackground( Color.WHITE );
		window.getIndex_down().setBackground( Color.WHITE );
		field.setBackground( Color.YELLOW );
	}

	private void setTextWithForfColorInt( JTextField field , double num , String textToAdd ) {
		if ( num > 0 ) {
			field.setForeground( green );
		} else {
			field.setForeground( red );
		}
		field.setText( str( ( int ) num ) + textToAdd );
	}

	private void setTextWithForfColorDouble( JTextField field , double num , String textToAdd ) {
		if ( num > 0 ) {
			field.setForeground( green );
		} else {
			field.setForeground( red );
		}
		field.setText( str( num ) + textToAdd );
	}

	private void setTextWithBackColor( JTextField field , double num , String textToAdd ) {
		if ( num > 0 ) {
			field.setBackground( green );
		} else {
			field.setBackground( red );
		}
		field.setText( str( num ) + textToAdd );
	}

	public double getAvgFromList( ArrayList < Double > list ) {
		double sum = 0;
		for ( Double d : list ) {
			sum += d;
		}
		return floor( sum / list.size() );
	}

	public void update() {

	}

	public int INT( String s ) {
		try {
			return Integer.parseInt( s );
		} catch ( NumberFormatException e ) {
			return 0;
		}
	}

	public String str( Object o ) {
		return String.valueOf( o );
	}

	public double dbl( String s ) {
		return Double.parseDouble( s );
	}

	public double floor( double d ) {
		return Math.floor( d * 100 ) / 100;
	}

}
