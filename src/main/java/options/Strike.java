package options;

public class Strike {

    private Call call;
    private Put put;
    private double strike;
    private double stDev = 0;

    public Strike() {}

    // Constructor
    public Strike( Call call, Put put, double strike ) {
        this.call = call;
        this.put = put;
        this.strike = strike;

    }

    // Getters and Setters
    public Call getCall() {
        return call;
    }

    public void setCall( Call call ) {
        this.call = call;
    }

    public Put getPut() {
        return put;
    }

    public void setPut( Put put ) {
        this.put = put;
    }

    public double getStrike() {
        return strike;
    }

    public void setStrike( double strike ) {
        this.strike = strike;
    }

    @Override
    public String toString() {

        String call = "null";
        String put = "null";

        if ( getCall( ) != null ) {
            call = getCall( ).toString( );
        }

        if ( getPut( ) != null ) {
            put = getPut( ).toString( );
        }

        return strike + "\n" + call + ", \n" + put;
    }

    public double getStDev() {
        return stDev;
    }

    public void setStDev( double stDev ) {
        this.stDev = stDev;

        try {
            this.call.setStDev( stDev );
            this.put.setStDev( stDev );
        } catch ( Exception e ) {
            e.printStackTrace( );
        }

    }

}
