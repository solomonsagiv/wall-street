package basketFinder;

public class MiniStock {

    // Variables
    private String name;
    private int id;
    private double ind;
    private double volume = 0;
    private double lastCheckVolume = 0;
    private int indexBidAskCounter = 0;
    private double indexBid = 0;
    private double indexAsk = 0;

    // Constructor
    public MiniStock( String name, int id ) {
        this.name = name;
        this.id = id;
    }

    private double indexAskForCheck = 0;
    public void setIndexBid( double indexBid ) {

        // If increment state
        if ( indexBid > this.indexBid && indexAskForCheck == this.indexAsk ) {
            indexBidAskCounter++;
        }
        this.indexBid = indexBid;

        // Ask for bid change state
        indexBidForCheck = indexBid;
        indexAskForCheck = this.indexAsk;

    }
    private double indexBidForCheck = 0;
    public void setIndexAsk( double indexAsk ) {
        // If increment state
        if ( indexAsk < this.indexAsk && indexBidForCheck == indexBid ) {
            indexBidAskCounter--;
        }
        this.indexAsk = indexAsk;

        // Handle state
        indexAskForCheck = indexAsk;
        indexBidForCheck = indexBid;

    }
    public void updateLastCheckVolume() {
        lastCheckVolume = volume;
    }
    public String getName() {
        return name;
    }
    public void setName( String name ) {
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public void setId( int id ) {
        this.id = id;
    }
    public double getInd() {
        return ind;
    }
    public void setInd( double ind ) {
        this.ind = ind;
    }
    public double getVolume() {
        return volume;
    }
    public void setVolume( double volume ) {
        this.volume = volume;
    }
    public double getLastCheckVolume() {
        return lastCheckVolume;
    }
}
