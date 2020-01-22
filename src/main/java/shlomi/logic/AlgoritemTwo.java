package shlomi.logic;

import api.tws.MyTwsClient;
import serverObjects.BASE_CLIENT_OBJECT;

public class AlgoritemTwo extends Algoritem {

	double opAvgPlag = 0.01;
	int racesPlag = 0;
	double opAvg = 0;
	int conUp = 0;
	int conDown = 0;
	int indUp = 0;
	int indDown = 0;
	String opAvgStatus = "";
	String preOpAvgStatus = "";
	String optimiStatus = "optimi";
	String pesimiStatus = "pesimi";
	// Constructor
	public AlgoritemTwo( BASE_CLIENT_OBJECT client , MyTwsClient twsClient ) {
		super( client , twsClient );
	}

	@Override
	public String getType() {
		return "Two";
	}

	@Override
	public void doLogic() {

		// Calculates
		calculates();

		// No position
		if ( !isInPos() ) {

			// LONG
			if ( canDoLong() ) {

				LONG();

			}

			// SHORT
			if ( canDoShort() ) {

				SHORT();

			}

		} else

			// In position
			if ( isInPos() ) {

				// EXIT LONG
				if ( isExitLong() && isLONG() ) {

					EXIT_LONG();

				}

				// EXIT SHORT
				if ( isExitShort() && isSHORT() ) {

					EXIT_SHORT();

				}

			}

	}

	public int getConSum() {
		int conSum = ( getClient().getConUp() - conUp ) - ( getClient().getConDown() - conDown );
		return conSum;
	}

	public int getIndSum() {
		int indSum = ( getClient().getIndexUp() - indUp ) - ( getClient().getIndexDown() - indDown );
		return indSum;
	}

	// Calculates
	private void calculates() {

//		opAvg = Double.parseDouble(ShlomiWindow.opAvgField.getText());
		opAvg = getClient().getOpAvg15FromDb();

		// Change my races if opAvg changed
		// Optimi
		if ( !opAvgStatus.equals( "" ) && opAvgStatus.equals( optimiStatus ) ) {

			// Change to pesimi
			if ( opAvg < opo( opAvgPlag ) ) {
				conUp = getClient().getConUp();
				conDown = getClient().getConDown();
				indUp = getClient().getIndexUp();
				indDown = getClient().getIndexDown();
			}

		}

		if ( !opAvgStatus.equals( "" ) && opAvgStatus.equals( pesimiStatus ) ) {

			// Change to pesimi
			if ( opAvg > opAvgPlag ) {
				conUp = getClient().getConUp();
				conDown = getClient().getConDown();
				indUp = getClient().getIndexUp();
				indDown = getClient().getIndexDown();
			}

		}

		// Update opAvgStatus
		if ( opAvg > opAvgPlag ) {
			opAvgStatus = optimiStatus;
		}

		if ( opAvg < opo( opAvgPlag ) ) {
			opAvgStatus = pesimiStatus;
		}
	}

	private boolean isExitShort() {

		boolean bool = false;

		if ( opAvg < opo( opAvgPlag ) || getClient().getIndexSum() > racesPlag ) {
			bool = true;
		}

		return bool;
	}

	private boolean isExitLong() {

		boolean bool = false;

		if ( opAvg > opAvgPlag || getClient().getIndexSum() < opo( racesPlag ) ) {
			bool = true;
		}

		return bool;

	}

	// Can do long
	public boolean canDoLong() {

		boolean bool = false;

		// Pesimi && buy index
		if ( opAvg < opo( opAvgPlag ) && getClient().getIndexSum() > racesPlag ) {
			bool = true;
		}

		return bool;
	}

	// Can do short
	public boolean canDoShort() {

		boolean bool = false;

		// Optimi && Sell index
		if ( opAvg > opAvgPlag && getClient().getIndexSum() < opo( racesPlag ) ) {
			bool = true;
		}

		return bool;
	}

}
