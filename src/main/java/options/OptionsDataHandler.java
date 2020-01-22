package options;

import blackScholes.MyBlackScholes;
import gui.WallStreetWindow;
import serverObjects.BASE_CLIENT_OBJECT;
import threads.MyThread;

public class OptionsDataHandler {

	public Runner runner;
	String[] state = { "" , "" };
	BASE_CLIENT_OBJECT client;
	Options options;
	private int sleep = 300;
	private int sleepCount = 0;
	private double avgTheoMargin = .05;

	// Constructor
	public OptionsDataHandler( BASE_CLIENT_OBJECT client ) {
		this.client = client;
		this.options = client.getOptionsHandler().getMainOptions();
	}

	public Runner getRunner() {
		if ( runner == null ) {
			runner = new Runner( client );
		}
		return runner;
	}

	public void setRunner( Runner runner ) {
		this.runner = runner;
	}

	public double getAvgTheoMargin() {
		return avgTheoMargin;
	}

	public void setAvgTheoMargin( double avgTheoMargin ) {
		this.avgTheoMargin = avgTheoMargin;
	}

	public Options getOptionsFather() {
		return options;
	}

	public void setOptionsFather( Options options ) {

		if ( options.getStrikes().size() != 0 ) {
			this.options = options;
		} else {
			WallStreetWindow.popup( "No options found" , null );
		}
	}

	// ---------- Runner ---------- //
	public class Runner extends MyThread implements Runnable {

		private boolean run = true;

		public Runner( BASE_CLIENT_OBJECT client ) {
			super( client );
			setName( "OptionsData" );
		}

		@Override
		public void initRunnable () {
			setRunnable ( this );
		}

		@Override
		public void run() {

			while ( run ) {
				try {

					// Calculate Standard deviation
					handleStDev();

					// Sleep
					Thread.sleep( sleep );

					sleepCount += sleep;

				} catch ( InterruptedException e ) {
					close();
				} catch ( Exception e ) {
					WallStreetWindow.popup( "Options data handler: " + getClient().getName() , e );
					break;
				}
			}
		}

		Option call;
		Option put;

		// Vega, delta
		double[] greeks;
		double stDev;
		double contract;
		double daysLeft;
		double interest;

		private void handleStDev() {

			contract = options.getContractAbsolutDays();
			daysLeft = options.getAbsolutDays();
			interest = options.getInterestZero();

			for ( Strike strike : options.getStrikes() ) {
				try {

					call = strike.getCall();
					put = strike.getPut();

					if ( strike.getStDev() == 0 ) {

						if ( call.gotBidAsk() && put.gotBidAsk() ) {

							stDev = MyBlackScholes.findStDev( strike , contract , daysLeft , interest );
							strike.setStDev( stDev );

						}
					} else {
						if ( sleepCount % ( sleep * 20 ) == 0 ) {

							// Update stDev
							stDev = MyBlackScholes.updateStDev( contract , strike , daysLeft , interest);

							strike.setStDev( stDev );

							// ----- Call ----- //
							// Delta greeks
							greeks = MyBlackScholes.greek( call, contract, daysLeft, stDev / 10, interest );
							call.setDelta ( greeks[1] );

							// Vega greeks
							greeks = MyBlackScholes.greek( call, contract, daysLeft / 360.0, stDev, interest );
							call.setVega ( greeks[3] );

							// ----- Put ----- //
							// Delta greeks
							greeks = MyBlackScholes.greek( put, contract, daysLeft, stDev / 10, interest );
							put.setDelta ( greeks[1] );

							// Vega greeks
							greeks = MyBlackScholes.greek( put, contract, daysLeft / 360.0, stDev, interest );
							put.setVega ( greeks[3] );

						}

						// Update theoretic prices
						double[] theos = MyBlackScholes.findTheos( strike, contract, daysLeft, interest );
						call.setTheoreticPrice( theos[ 0 ] );
						put.setTheoreticPrice( theos[ 1 ] );

					}

				} catch ( Exception e ) {
					e.printStackTrace();
				}
			}
		}


		private void close() {
			run = false;
		}
	}

}
