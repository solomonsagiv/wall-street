package gui;

import charts.CONTRACT_IND_CHART;
import charts.CONTRACT_IND_CHART_LIVE;
import charts.INDEX_OPAVG_EQUALMOVE_CHART;
import charts.QUARTER_CONTRACT_IND_CHART_LIVE;
import locals.L;
import locals.Themes;
import options.fullOptions.FullOptionsWindow;
import options.Option;
import options.OptionsWindow;
import options.Strike;
import serverObjects.BASE_CLIENT_OBJECT;
import threads.MyThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

public class FuturePanel extends BaseFuturePanel {

	// Url
	String url = "";

	// Races
	JTextField future_sum;
	JTextField index_sum;

	// Ticker
	JPanel ticker;
	JTextField open;
	JTextField open_present;
	JTextField index;
	JTextField index_present;
	JTextField low;
	JTextField low_present;
	JTextField high;
	JTextField high_present;
	JTextField future;
	JTextField op;
	JTextField opAvg;
	JTextField equalMoveField;

	// Exp
	JPanel exp;
	JTextField optimipesimi;

	// Quarter
	JTextField opAvgQuarterField;
	JTextField opQuarterField;
	JTextField equalMoveQuarterField;
	JTextField contractQuarterField;

	// Races and roll
	JPanel racesAndRollPanel;
	JLabel conRacesLbl;
	JLabel indRacesLbl;
	JLabel rollLbl;

	JTextField conRacesField;
	JTextField indRacesField;
	JTextField rollField;

	int height = 200;

	Font font = Themes.VEDANA_12;

	Color green = Themes.GREEN;
	Color red = Themes.RED;


	Color backGround = Themes.GREY_LIGHT;

	BASE_CLIENT_OBJECT client;

	int listSleep = 1000;

	private Updater updater;

	public FuturePanel ( BASE_CLIENT_OBJECT base_CLIENT_OBJECT ) {
		init ( base_CLIENT_OBJECT );
	}

	private void init ( BASE_CLIENT_OBJECT client ) {
		addMouseListener ( new MouseAdapter ( ) {
			@Override
			public void mouseClicked ( MouseEvent event ) {
				if ( event.getModifiers ( ) == MouseEvent.BUTTON3_MASK ) {
					// Main menu
					JPopupMenu menu = new JPopupMenu ( );

					// Charts menu
					JMenu charts = new JMenu ( "Charts" );

					JMenuItem fut_ind_chart = new JMenuItem ( "Contract vs Ind" );
					fut_ind_chart.addActionListener ( new ActionListener ( ) {
						@Override
						public void actionPerformed ( ActionEvent e ) {

							CONTRACT_IND_CHART chart = new CONTRACT_IND_CHART ( client );
							chart.createChart ( );

						}
					} );


					JMenuItem contractIndexRealTime = new JMenuItem ( "Contract vs Ind real time" );
					contractIndexRealTime.addActionListener ( new ActionListener ( ) {
						@Override
						public void actionPerformed ( ActionEvent e ) {

							CONTRACT_IND_CHART_LIVE chart = new CONTRACT_IND_CHART_LIVE ( client );
							chart.createChart ( );

						}
					} );

					JMenuItem quarterContractIndexRealTime = new JMenuItem ( "Quarter vs Ind real time" );
					quarterContractIndexRealTime.addActionListener ( new ActionListener ( ) {
						@Override
						public void actionPerformed ( ActionEvent e ) {

							QUARTER_CONTRACT_IND_CHART_LIVE chart = new QUARTER_CONTRACT_IND_CHART_LIVE ( client );
							chart.createChart ( );

						}
					} );

					JMenuItem index_opAvg_equalMove = new JMenuItem ( "Ind OpAvg Equal move" );
					index_opAvg_equalMove.addActionListener ( new ActionListener ( ) {
						@Override
						public void actionPerformed ( ActionEvent e ) {

							INDEX_OPAVG_EQUALMOVE_CHART chart = new INDEX_OPAVG_EQUALMOVE_CHART ( client );
							chart.createChart ( );

						}
					} );

					// Export menu
					JMenu export = new JMenu ( "Export" );

					JMenuItem exportSumLine = new JMenuItem ( "Export sum line" );
					exportSumLine.addActionListener ( new ActionListener ( ) {
						@Override
						public void actionPerformed ( ActionEvent e ) {
							client.getTablesHandler ( ).getSumHandler ( ).getHandler ( ).insertLine ( );
						}
					} );

					JMenuItem details = new JMenuItem ( "Details" );
					details.addActionListener ( new ActionListener ( ) {
						@Override
						public void actionPerformed ( ActionEvent e ) {
							DetailsWindow detailsWindow = new DetailsWindow ( client );
							detailsWindow.frame.setVisible ( true );
						}
					} );

					JMenuItem optionsCounter = new JMenuItem ( "Options counter table" );
					optionsCounter.addActionListener ( new ActionListener ( ) {
						@Override
						public void actionPerformed ( ActionEvent e ) {
							OptionsWindow window = new OptionsWindow ( client, client.getOptionsHandler ( ).getMainOptions ( ) );
							window.frame.setVisible ( true );
							window.startWindowUpdater ( );
						}
					} );

					JMenuItem fullOptionsTable = new JMenuItem ( "Full options table" );
					fullOptionsTable.addActionListener ( new ActionListener ( ) {
						@Override
						public void actionPerformed ( ActionEvent e ) {
							FullOptionsWindow fullOptionsWindow = new FullOptionsWindow ( client );
							fullOptionsWindow.frame.setVisible ( true );
						}
					} );

					export.add ( exportSumLine );

					charts.add ( quarterContractIndexRealTime );
					charts.add ( contractIndexRealTime );
					charts.add ( index_opAvg_equalMove );
					charts.add ( fut_ind_chart );

					menu.add ( details );
					menu.add ( export );
					menu.add ( charts );
					menu.add ( optionsCounter );
					menu.add ( fullOptionsTable );

					// Show the menu
					menu.show ( event.getComponent ( ), event.getX ( ), event.getY ( ) );
				}
			}
		} );

		this.client = client;
		setLayout ( null );
		setBounds ( 0, 0, 0, height );

		future_sum = racesTextField ( future_sum, 5, 64 );
		index_sum = racesTextField ( index_sum, 58, 64 );

		// Ticker section
		ticker = new JPanel ( null );
		ticker.setBounds ( 0, 0, 307, height );
		ticker.setBackground ( backGround );

		open = tickerTextField ( 5, 6 );
		ticker.add ( open );
		open_present = tickerPresent ( 5, 35 );
		ticker.add ( open_present );
		index = tickerTextField ( 80, 6 );
		ticker.add ( index );
		index_present = tickerPresent ( 80, 35 );
		ticker.add ( index_present );
		low = tickerTextField ( 155, 6 );
		ticker.add ( low );
		low_present = tickerPresent ( 155, 35 );
		ticker.add ( low_present );
		high = tickerTextField ( 230, 6 );
		ticker.add ( high );
		high_present = tickerPresent ( 230, 35 );
		ticker.add ( high_present );
		future = tickerTextField ( 5, 64 );
		ticker.add ( future );

		op = tickerPresent ( 80, 64 );
		ticker.add ( op );

		optimipesimi = tickerTextField ( 1000, 64 );
		ticker.add ( optimipesimi );

		equalMoveField = tickerTextField ( 230, 64 );
		ticker.add ( equalMoveField );

		opAvg = tickerTextField ( 155, 64 );
		ticker.add ( opAvg );

		// Quarter
		opAvgQuarterField = tickerTextField ( 155, 93 );
		ticker.add ( opAvgQuarterField );

		equalMoveQuarterField = tickerTextField ( 230, 93 );
		ticker.add ( equalMoveQuarterField );

		contractQuarterField = tickerTextField ( 5, 93 );
		ticker.add ( contractQuarterField );

		opQuarterField = tickerTextField ( 80, 93 );
		opQuarterField.setForeground ( Color.WHITE );
		ticker.add ( opQuarterField );

		add ( ticker );

		// Races and roll
		// Panel
		racesAndRollPanel = new JPanel ( null );
		racesAndRollPanel.setBackground ( backGround );
		racesAndRollPanel.setBounds ( 308, 0, 111, height );
		add ( racesAndRollPanel );

		// Con lbl
		conRacesLbl = new JLabel ( "Cont" );
		conRacesLbl.setHorizontalAlignment ( JLabel.CENTER );
		conRacesLbl.setBounds ( 5, 5, 50, 25 );
		conRacesLbl.setForeground ( Themes.BLUE );

		racesAndRollPanel.add ( conRacesLbl );

		// Con field
		conRacesField = tickerTextField ( 55, 7, 50, 25 );
		racesAndRollPanel.add ( conRacesField );

		// Ind lbl
		indRacesLbl = new JLabel ( "Ind" );
		indRacesLbl.setHorizontalAlignment ( JLabel.CENTER );
		indRacesLbl.setBounds ( 5, 35, 50, 25 );
		indRacesLbl.setForeground ( Themes.BLUE );

		racesAndRollPanel.add ( indRacesLbl );

		// Ind field
		indRacesField = tickerTextField ( 55, 37, 50, 25  );
		racesAndRollPanel.add ( indRacesField );

		rollLbl = new JLabel ( "Roll" );
		rollLbl.setHorizontalAlignment ( JLabel.CENTER );
		rollLbl.setBounds ( 5, 65, 50, 25 );
		rollLbl.setForeground ( Themes.BLUE );

		racesAndRollPanel.add ( rollLbl );

		// Roll field
		rollField = tickerTextField ( 55, 67, 50, 25 );
		racesAndRollPanel.add ( rollField );



	}


	public JTextField getEqualMoveField () {
		return equalMoveField;
	}

	public class Updater extends MyThread implements Runnable {


		long mySleep = 0;

		public Updater ( BASE_CLIENT_OBJECT client ) {
			super ( client );

			setName ( "UPDATER" );
		}

		@Override
		public void initRunnable () {
			setRunnable ( this );
		}

		@Override
		public void run () {

			setRun ( true );

			while ( isRun ( ) ) {
				try {

					// Sleep
					Thread.sleep ( 1000 );

					updateLists ( );

					// ---------- Ticker ---------- //
					getOpen ( ).setText ( L.format100 ( client.getOpen ( ) ) );
					getHigh ( ).setText ( L.format100 ( client.getHigh ( ) ) );
					getLow ( ).setText ( L.format100 ( client.getLow ( ) ) );
					getIndex ( ).setText ( L.format100 ( client.getIndex ( ) ) );
					getFuture ( ).setText ( L.format100 ( client.getOptionsHandler ( ).getMainOptions ( ).getContract ( ) ) );

					// Ticker present
					colorBackPresent ( getOpen_present ( ), toPresent ( client.getOpen ( ), client.getBase ( ) ), L.format100 ( ) );
					colorBackPresent ( getHigh_present ( ), toPresent ( client.getHigh ( ), client.getBase ( ) ), L.format100 ( ) );
					colorBackPresent ( getLow_present ( ), toPresent ( client.getLow ( ), client.getBase ( ) ), L.format100 ( ) );
					colorBackPresent ( getIndex_present ( ), toPresent ( client.getIndex ( ), client.getBase ( ) ), L.format100 ( ) );

					colorForf ( getOpAvg ( ), client.getOptionsHandler ( ).getMainOptions ( ).getOpAvg ( ) );

					// OP
					double op = client.getOptionsHandler ( ).getMainOptions ( ).getOp ( );
					colorBack ( getOp ( ), op );

					// Equal move index
					colorForfInt ( getEqualMoveField ( ), ( int ) client.getOptionsHandler ( ).getMainOptions ( ).getEqualMoveCalculator ( ).getMoveIndex ( ) );

					// ---------- Races ---------- //
					int future_up = client.getConUp ( );
					int future_down = client.getConDown ( );
					int index_up = client.getIndexUp ( );
					int index_down = client.getIndexDown ( );

					int future_sum = future_up - future_down;
					int index_sum = index_up - index_down;

					// Sum races
					colorForfInt ( getFuture_sum ( ), future_sum );
					colorForfInt ( getIndex_sum ( ), index_sum );

					// Optimi pesimi
					colorForfInt ( getOptimipesimi ( ), client.getOptimiPesimiCount ( ) );

					// Quarter
					colorBack ( getOpQuarterField ( ), client.getOptionsHandler ( ).getOptionsQuarter ( ).getOp ( ), L.format100 ( ) );
					colorForf ( getOpAvgQuarterField ( ), client.getOptionsHandler ( ).getOptionsQuarter ( ).getOpAvg ( ), L.format100 ( ) );
					colorForfInt ( getEqualMoveQuarterField ( ), client.getOptionsHandler ( ).getOptionsQuarter ( ).getEqualMoveCalculator ( ).getMoveIndex ( ) );
					getContractQuarterField ( ).setText ( L.format100 ( client.getOptionsHandler ( ).getOptionsQuarter ( ).getContract ( ) ) );

					// Races and roll
					// Races
					colorForfInt ( getConRacesField ( ), client.getFutSum ( ) );
					colorForfInt ( getIndRacesField ( ), client.getIndexSum ( ) );

					// Roll
					double month = client.getOptionsHandler ( ).getOptionsMonth ( ).getContract ( );
					double quarter = client.getOptionsHandler ( ).getOptionsQuarter ( ).getContract ( );
					colorForf ( getRollField ( ), quarter - month, L.format100 ( ) );

					mySleep += 1000;
				} catch ( InterruptedException e ) {
					close ( );
				}
			}
		}

		private double toPresent ( double d, double base ) {
			return ( ( d - base ) / base ) * 100;
		}

		private void updateLists () {

			// Options lists
			for ( Strike strike : client.getOptionsHandler ( ).getMainOptions ( ).getStrikes ( ) ) {
				Option call = strike.getCall ( );
				call.getBidAskCounterList ( ).add ( call.getBidAskCounter ( ) );

				Option put = strike.getPut ( );
				put.getBidAskCounterList ( ).add ( put.getBidAskCounter ( ) );

			}
		}

		public void close () {
			setRun ( false );
		}

	}

	@Override
	public void colorRaces ( int runner1_up_down, int runner2_up_down, int competition_Number ) {
	}

	// Present
	public void colorBack ( JTextField field, double val ) {

		if ( val >= 0 ) {
			field.setText ( L.str ( val ) );
			field.setBackground ( green );
		} else {
			field.setText ( L.str ( val ) );
			field.setBackground ( red );
		}

	}


	// Present
	public void colorBack ( JTextField field, double val, DecimalFormat format ) {

		if ( val >= 0 ) {
			field.setText ( format.format ( val ) );
			field.setBackground ( green );
		} else {
			field.setText ( format.format ( val ) );
			field.setBackground ( red );
		}

	}

	// Present
	public void colorForf ( JTextField field, double val ) {

		if ( val > 0 ) {
			field.setText ( L.str ( val ) );
			field.setForeground ( green );
		} else {
			field.setText ( L.str ( val ) );
			field.setForeground ( red );
		}
	}

	// Present
	public void colorForf ( JTextField field, double val, DecimalFormat format ) {

		if ( val > 0 ) {
			field.setText ( format.format ( val ) );
			field.setForeground ( green );
		} else {
			field.setText ( format.format ( val ) );
			field.setForeground ( red );
		}
	}


	// Present
	public void colorForfInt ( JTextField field, double val ) {

		if ( val > 0 ) {
			field.setText ( L.str ( ( int ) val ) );
			field.setForeground ( green );
		} else {
			field.setText ( L.str ( ( int ) val ) );
			field.setForeground ( red );
		}
	}

	// Present
	public void colorBackPresent ( JTextField field, double val ) {

		if ( val >= 0 ) {
			field.setText ( L.str ( val ) + "%" );
			field.setBackground ( green );
		} else {
			field.setText ( L.str ( val ) + "%" );
			field.setBackground ( red );
		}

	}

	// Present
	public void colorBackPresent ( JTextField field, double val, DecimalFormat format ) {

		if ( val >= 0 ) {
			field.setText ( format.format ( val ) + "%" );
			field.setBackground ( green );
		} else {
			field.setText ( format.format ( val ) + "%" );
			field.setBackground ( red );
		}

	}

	// JText filed in races type
	public JTextField racesTextField ( JTextField field, int x, int y ) {
		field = new JTextField ( );
		field.setBounds ( x, y, 48, 25 );
		field.setFont ( font );
		field.setHorizontalAlignment ( JTextField.CENTER );
		field.setBackground ( Themes.GREY_VERY_LIGHT );
		field.setBorder ( null );
		return field;
	}

	// JText filed in races type
	public JTextField tickerTextField ( int x, int y ) {
		JTextField field = new JTextField ( );
		field.setBounds ( x, y, 70, 25 );
		field.setFont ( font );
		field.setHorizontalAlignment ( JTextField.CENTER );
		field.setBackground ( Themes.GREY_VERY_LIGHT );
		field.setBorder ( null );
		return field;
	}

	// JText filed in races type
	public JTextField tickerTextField ( int x, int y, int width, int height ) {
		JTextField field = new JTextField ( );
		field.setBounds ( x, y, width, height );
		field.setFont ( font );
		field.setHorizontalAlignment ( JTextField.CENTER );
		field.setBackground ( Themes.GREY_VERY_LIGHT );
		field.setBorder ( null );
		return field;
	}

	// JText filed in races type
	public JTextField tickerPresent ( int x, int y ) {
		JTextField field = new JTextField ( );
		field.setBounds ( x, y, 70, 25 );
		field.setFont ( font.deriveFont ( Font.BOLD ) );
		field.setForeground ( Color.WHITE );
		field.setHorizontalAlignment ( JTextField.CENTER );
		field.setBackground ( Themes.GREY_VERY_LIGHT );
		field.setBorder ( null );
		return field;
	}

	public JTextField getOpAvg () {
		return opAvg;
	}

	public void setOpAvg ( JTextField opAvg ) {
		this.opAvg = opAvg;
	}

	public String getUrl () {
		return url;
	}

	public JTextField getFuture_sum () {
		return future_sum;
	}

	public JTextField getIndex_sum () {
		return index_sum;
	}

	public JPanel getTicker () {
		return ticker;
	}

	public JTextField getOpen () {
		return open;
	}

	public JTextField getOpen_present () {
		return open_present;
	}

	public JTextField getIndex () {
		return index;
	}

	public JTextField getIndex_present () {
		return index_present;
	}

	public JTextField getLow () {
		return low;
	}

	public JTextField getLow_present () {
		return low_present;
	}

	public JTextField getHigh () {
		return high;
	}

	public JTextField getHigh_present () {
		return high_present;
	}

	public JTextField getFuture () {
		return future;
	}

	public JTextField getOp () {
		return op;
	}

	public JPanel getExp () {
		return exp;
	}

	public JTextField getOptimipesimi () {
		return optimipesimi;
	}


	public Font getFont () {
		return font;
	}

	public Color getGreen () {
		return green;
	}

	public Color getRed () {
		return red;
	}

	public BASE_CLIENT_OBJECT getStock () {
		return client;
	}

	public JTextField getOpQuarterField () {
		return opQuarterField;
	}

	public JTextField getOpAvgQuarterField () {
		return opAvgQuarterField;
	}

	public JTextField getEqualMoveQuarterField () {
		return equalMoveQuarterField;
	}

	public JTextField getContractQuarterField () {
		return contractQuarterField;
	}

	public void setUrl ( String url ) {
		this.url = url;
	}

	public JTextField getConRacesField () {
		return conRacesField;
	}

	public void setConRacesField ( JTextField conRacesField ) {
		this.conRacesField = conRacesField;
	}

	public JTextField getIndRacesField () {
		return indRacesField;
	}

	public void setIndRacesField ( JTextField indRacesField ) {
		this.indRacesField = indRacesField;
	}

	public JTextField getRollField () {
		return rollField;
	}

	public void setRollField ( JTextField rollField ) {
		this.rollField = rollField;
	}

	public Updater getUpdater () {
		if ( updater == null ) {
			updater = new Updater ( client );
		}
		return updater;
	}


}
