package ML;

public class StrikeTest {

    /*
     * Each strike has two 'Options'
     * 1: Call
     * 2: Put
     * and standard division
     * */

    private OptionTest call;
    private OptionTest put;
    private double strike;
    private double stDev = 0;

    // Constructor
    public StrikeTest() {
    }

    public StrikeTest(OptionTest call, OptionTest put, double strike) {
        this.call = call;
        this.put = put;
        this.strike = strike;

    }

    public StrikeTest(double strike, int callId, int putId) {
        this.strike = strike;
        this.call = new OptionTest("c", strike, callId);
        this.put = new OptionTest("p", strike, putId);
    }

    // Getters and Setters
    public OptionTest getCall() {
        return call;
    }

    public void setCall(OptionTest call) {
        this.call = call;
    }

    public OptionTest getPut() {
        return put;
    }

    public void setPut(OptionTest put) {
        this.put = put;
    }

    public double getStrike() {
        return strike;
    }

    public void setStrike(double strike) {
        this.strike = strike;
    }

    @Override
    public String toString() {

        String call = "null";
        String put = "null";

        if (getCall() != null) {
            call = getCall().toString();
        }

        if (getPut() != null) {
            put = getPut().toString();
        }

        return strike + "\n" + call + ", \n" + put;
    }

    public double getStDev() {
        return stDev;
    }

    public void setStDev(double stDev) {
        this.stDev = stDev;

        try {
            this.call.setStDev(stDev);
            this.put.setStDev(stDev);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
