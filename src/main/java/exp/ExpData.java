package exp;

import serverObjects.BASE_CLIENT_OBJECT;

public class ExpData {

    // Variables
    String expName;
    BASE_CLIENT_OBJECT client;

    private double start = 0;
    private int indBidAskCounter = 0;

    // Constructor
    public ExpData(String expName, BASE_CLIENT_OBJECT client) {
        this.expName = expName;
        this.client = client;
    }

    private int getTotalIndBidAskCounter() {
        return indBidAskCounter + client.getIndexBidAskCounter();
    }

    public int getIndBidAskCounter() {
        return indBidAskCounter;
    }

    public void setIndBidAskCounter(int indBidAskCounter) {
        this.indBidAskCounter = indBidAskCounter;
    }

    public double getStart() {
        return start;
    }

    public void setStart(double start) {
        this.start = start;
    }

}
