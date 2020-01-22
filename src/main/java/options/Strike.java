package options;

public class Strike {

	private Option call;
	private Option put;
	private double strike;
	private double stDev = 0;

	// Constructor
	public Strike () {
	}

	public Strike ( Option call, Option put, double strike ) {
		this.call = call;
		this.put = put;
		this.strike = strike;

	}

	public Strike ( double strike, int callId, int putId ) {
		this.strike = strike;
		this.call = new Option ( "c", strike, callId );
		this.put = new Option ( "p", strike, putId );
	}

	// Getters and Setters
	public Option getCall () {
		return call;
	}

	public void setCall ( Option call ) {
		this.call = call;
	}

	public Option getPut () {
		return put;
	}

	public void setPut ( Option put ) {
		this.put = put;
	}

	public double getStrike () {
		return strike;
	}

	public void setStrike ( double strike ) {
		this.strike = strike;
	}

	@Override
	public String toString () {

		String call = "null";
		String put = "null";

		if ( getCall ( ) != null ) {
			call = getCall ( ).toString ( );
		}

		if ( getPut ( ) != null ) {
			put = getPut ( ).toString ( );
		}

		return strike + "\n" + call + ", \n" + put;
	}

	public double getStDev () {
		return stDev;
	}

	public void setStDev ( double stDev ) {
		this.stDev = stDev;

		try {
			this.call.setStDev ( stDev );
			this.put.setStDev ( stDev );
		} catch ( Exception e ) {
			e.printStackTrace ( );
		}

	}

}
