package logic;

import gui.FuturePanel;
import gui.FuturePanelLine;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.SpxCLIENTObject;
import threads.MyThread;

import javax.swing.*;
import java.awt.*;

public class Logic {

	FuturePanel futurePanel;
	boolean futurePanelBool = false;
	boolean futurePanelLineBool = false;
	FuturePanelLine futurePanelLine;
	double margin;
	BASE_CLIENT_OBJECT client;
	private LogicRunner logicRunner;

	// Constructor
	public Logic( FuturePanel panel , BASE_CLIENT_OBJECT stock ) {
		this.futurePanel = panel;
		this.client = stock;
		this.margin = stock.getRacesMargin();
		this.futurePanelBool = true;
		logicRunner = new LogicRunner( stock );
	}

	// Constructor
	public Logic( FuturePanelLine panel , BASE_CLIENT_OBJECT stock ) {
		this.futurePanelLine = panel;
		this.client = stock;
		this.margin = stock.getRacesMargin();
		this.futurePanelLineBool = true;
		logicRunner = new LogicRunner( stock );
	}

	public LogicRunner getLogicRunner() {
		if ( logicRunner == null ) {
			logicRunner = new LogicRunner( client );
		}
		return logicRunner;
	}

	public class LogicRunner extends MyThread implements Runnable {

		Color light_grey_back = new Color( 248 , 248 , 255 );

		// regular count
		int runner1_up_count = 0;
		int runner1_down_count = 0;
		int runner2_up_count = 0;
		int runner2_down_count = 0;
		// local variables
		double runner1_0 = 0;
		double runner2_0 = 0;
		int competition_Number = 0;
		int runner1_up_down = 0;
		int runner2_up_down = 0;
		boolean runner1_Competition = false;
		boolean runner2_Competition = false;
		boolean first = false;
		boolean bool = true;
		boolean run = true;

		public LogicRunner( BASE_CLIENT_OBJECT client ) {
			super( client );
			setName( "Logic" );
		}


		@Override
		public void initRunnable () {
			setRunnable ( this );
		}

		public boolean isRun() {
			return run;
		}

		public void setRun( boolean run ) {
			this.run = run;
		}

		// logic goes here
		@Override
		public void run() {

			while ( isRun() ) {
				try {

					if ( client instanceof SpxCLIENTObject ) {
						client.getOptionsHandler().getOptionsDay().getContract();
					}

					double future = 0;
					double index = 0;

					future = client.getOptionsHandler().getMainOptions ().getContract();
					index = client.getIndex();
					margin = client.getRacesMargin();

					// set for the first time the hoze and stock 0
					if ( runner1_0 == 0.0 && runner2_0 == 0.0 && !first ) {
						first = true;
						runner1_0 = future;
						runner2_0 = index;
					}
					/**
					 * Searching for the first competition
					 **/
					if ( competition_Number == 0 ) {

						// Odd or Even
						if ( bool ) {

							bool = false;

							// hoze comes up
							if ( future >= runner1_0 + margin ) {
								competition_Number = 1;
								runner1_Competition = true;
								runner1_up_down = 1;
							}

							// hoze comes down
							if ( future <= runner1_0 - margin ) {
								competition_Number = 1;
								runner1_Competition = true;
								runner1_up_down = 2;
							}

							// stock comes up
							if ( index >= runner2_0 + margin ) {
								competition_Number = 1;
								runner2_Competition = true;
								runner2_up_down = 1;
							}

							// stock comes down
							if ( index <= runner2_0 - margin ) {
								competition_Number = 1;
								runner2_Competition = true;
								runner2_up_down = 2;
							}
						} else {

							bool = true;

							// stock comes up
							if ( index >= runner2_0 + margin ) {
								competition_Number = 1;
								runner2_Competition = true;
								runner2_up_down = 1;
							}

							// stock comes down
							if ( index <= runner2_0 - margin ) {
								competition_Number = 1;
								runner2_Competition = true;
								runner2_up_down = 2;
							}

							// hoze comes up
							if ( future >= runner1_0 + margin ) {
								competition_Number = 1;
								runner1_Competition = true;
								runner1_up_down = 1;
							}

							// hoze comes down
							if ( future <= runner1_0 - margin ) {
								competition_Number = 1;
								runner1_Competition = true;
								runner1_up_down = 2;
							}
						}
					}
					/**
					 * In one competition
					 **/
					if ( competition_Number == 1 ) {

						// hoze start the competition
						if ( runner1_Competition ) {

							// hoze is up
							if ( runner1_up_down == 1 ) {

								// Exit 1 : no winners
								if ( future <= runner1_0 ) {
									competition_Number = 0;
									runner1_Competition = false;
									runner1_up_down = 0;
								}

								// Exit 2 : hoze win
								if ( index >= runner2_0 + margin ) {
									competition_Number = 0;
									runner1_Competition = false;
									runner1_up_down = 0;

									if ( futurePanelBool ) {
										noisy( futurePanel.getFuture_sum () );
									} else {
										noisy( futurePanelLine.getFuture_up() );
									}
									runner1_up_count = 1;

									runner1_0 = future;
									runner2_0 = index;

								}

								// new Competition
								if ( index < runner2_0 - margin && !runner2_Competition ) {
									competition_Number = 2;
									runner2_Competition = true;
									runner2_up_down = 2;
								}
							}

							// hoze is down
							if ( runner1_up_down == 2 ) {

								// Exit 1 : no winners
								if ( future >= runner1_0 ) {
									competition_Number = 0;
									runner1_Competition = false;
									runner1_up_down = 0;
								}

								// Exit 2 : hoze win
								if ( index <= runner2_0 - margin ) {
									competition_Number = 0;
									runner1_Competition = false;
									runner1_up_down = 0;

									runner1_down_count = 1;
									if ( futurePanelBool ) {
										noisy( futurePanel.getFuture_sum () );
									} else {
										noisy( futurePanelLine.getFuture_down() );
									}

									runner1_0 = future;
									runner2_0 = index;
								}

								// Exit 3 : new Competition
								if ( index > runner2_0 + margin && !runner2_Competition ) {
									competition_Number = 2;
									runner2_Competition = true;
									runner2_up_down = 1;
								}
							}
						}
						// stock start the competition
						if ( runner2_Competition ) {

							// stock is up
							if ( runner2_up_down == 1 ) {

								// Exit 1 : no winners
								if ( index <= runner2_0 ) {
									competition_Number = 0;
									runner2_Competition = false;
									runner2_up_down = 0;
								}

								// Exit 2 : stock win
								if ( future >= runner1_0 + margin ) {
									competition_Number = 0;
									runner2_Competition = false;
									runner2_up_down = 0;

									runner2_up_count = 1;
									if ( futurePanelBool ) {
										noisy( futurePanel.getIndex_sum () );
									} else {
										noisy( futurePanelLine.getIndex_up() );
									}

									runner1_0 = future;
									runner2_0 = index;
								}

								// Exit 3 : new competition
								if ( future < runner1_0 - margin && !runner1_Competition ) {
									competition_Number = 2;
									runner1_Competition = true;
									runner1_up_down = 2;
								}
							}

							// stock is down
							if ( runner2_up_down == 2 ) {

								// Exit 1 : no winners
								if ( index >= runner2_0 ) {
									competition_Number = 0;
									runner2_Competition = false;
									runner2_up_down = 0;
								}

								// Exit 2 : stock win
								if ( future <= runner1_0 - margin ) {
									competition_Number = 0;
									runner2_Competition = false;
									runner2_up_down = 0;

									runner2_down_count = 1;

									if ( futurePanelBool ) {
										noisy( futurePanel.getIndex_sum () );
									} else {
										noisy( futurePanelLine.getIndex_down() );
									}

									runner1_0 = future;
									runner2_0 = index;
								}

								// Exit 3 : new competition
								if ( future > runner1_0 + margin && !runner1_Competition ) {
									competition_Number = 2;
									runner1_Competition = true;
									runner1_up_down = 1;
								}
							}
						}
					}
					/**
					 * In two competitions
					 **/
					if ( competition_Number == 2 ) {

						// hoze up stock down
						if ( runner1_up_down == 1 && runner2_up_down == 2 ) {

							// Exit 3 : hoze close his competition
							if ( future <= runner1_0 ) {
								competition_Number = 1;
								runner1_Competition = false;
								runner1_up_down = 0;
							}

							// Exit 4 : stock close his competition
							if ( index >= runner2_0 ) {
								competition_Number = 1;
								runner2_Competition = false;
								runner2_up_down = 0;
							}
						}

						// stock up hoze down
						if ( runner2_up_down == 1 && runner1_up_down == 2 ) {

							// Exit 1 : hoze close his competition
							if ( future >= runner1_0 ) {
								competition_Number = 1;
								runner1_Competition = false;
								runner1_up_down = 0;
							}

							// Exit 2 : stock close his competition
							if ( index <= runner2_0 ) {
								competition_Number = 1;
								runner2_Competition = false;
								runner2_up_down = 0;
							}
						}
					}

					// fix 1
					if ( competition_Number == 2 ) {
						if ( !runner1_Competition || !runner2_Competition ) {
							runner1_Competition = false;
							runner2_Competition = false;
							competition_Number = 0;
							runner1_0 = future;
							runner2_0 = index;
							runner1_up_down = 0;
							runner2_up_down = 0;
						}
					}

					// fix 2
					if ( runner1_Competition && !runner2_Competition ) {
						if ( competition_Number == 2 ) {
							runner1_Competition = false;
							runner2_Competition = false;
							competition_Number = 0;
							runner1_0 = future;
							runner2_0 = index;
							runner1_up_down = 0;
							runner2_up_down = 0;
						}
					}

					// fix 3
					if ( runner2_Competition && !runner1_Competition ) {
						if ( competition_Number == 2 ) {
							runner1_Competition = false;
							runner2_Competition = false;
							competition_Number = 0;
							runner1_0 = future;
							runner2_0 = index;
							runner1_up_down = 0;
							runner2_up_down = 0;
						}
					}

					// fix 4
					if ( !runner1_Competition && !runner2_Competition && competition_Number == 1 ) {
						runner1_Competition = false;
						runner2_Competition = false;
						competition_Number = 0;
						runner1_0 = future;
						runner2_0 = index;
						runner1_up_down = 0;
						runner2_up_down = 0;
					}

					// setText to the window
					updateRaces();

					Thread.sleep( 200 );
				} catch ( InterruptedException e ) {
					setRun( false );
				}
			}
		}

		public double floor( double d ) {
			return Math.floor( d * 100 ) / 100;
		}

		// noisy
		private void noisy( JTextField textField ) throws InterruptedException {
			Runnable r = new Runnable() {
				@Override
				public void run() {
					try {
						for ( int i = 0 ; i < 200 ; i++ ) {
							textField.setBackground( Color.YELLOW );
							Thread.sleep( 10 );
						}
						textField.setBackground( light_grey_back );
					} catch ( InterruptedException e ) {
						e.printStackTrace();
					}
				}

			};
			new Thread( r ).start();
		}

		public void close() {
			run = false;
		}

		// SetText
		private void updateRaces() {
			client.setConUp( client.getConUp() + runner1_up_count );
			client.setConDown( client.getConDown() + runner1_down_count );
			client.setIndexUp( client.getIndexUp() + runner2_up_count );
			client.setIndexDown( client.getIndexDown() + runner2_down_count );

			// regular count
			runner1_up_count = 0;
			runner1_down_count = 0;
			runner2_up_count = 0;
			runner2_down_count = 0;
		}


		// Refresh the logic variables
		public void refresh() {
			runner1_0 = 0;
			runner2_0 = 0;
			competition_Number = 0;

			runner1_up_down = 0;
			runner2_up_down = 0;

			runner1_Competition = false;
			runner2_Competition = false;

			first = false;
			bool = true;
		}
	}


}