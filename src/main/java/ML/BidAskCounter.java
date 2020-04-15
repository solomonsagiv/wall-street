package ML;

public class BidAskCounter {

    double bid = 0;
    double ask = 0;
    int bidAskCounter = 0;

    private double askCheck = 0;

    public void setBid(double newBid ) {

        // If new bid is bigger than pre bid && ask stay the same
        if ( newBid > this.bid && askCheck == this.ask ) {
            bidAskCounter++;
        }
        this.bid = newBid;

        // Ask for bid change state
        askCheck = this.ask;

    }

    private double bidCheck = 0;

    public void setAsk(double newAsk ) {

        // If new ask is lower than pre ask && bid stay the same
        if ( newAsk < this.ask && bidCheck == this.bid ) {
            bidAskCounter--;
        }
        this.ask = newAsk;

        // Set bid check
        bidCheck = this.bid;

    }

}
