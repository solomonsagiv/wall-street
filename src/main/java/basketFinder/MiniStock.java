package basketFinder;

public class MiniStock {

    // Variables
    private String name;
    private int id;
    private double ind;
    private double volume = 0;
    private double lastCheckVolume = 0;
    private double indBid = 0;
    private double indAsk = 0;
    private double preIndBid = 0;
    private double preIndAsk = 0;
    private boolean down = false;
    private boolean up = false;

    // Constructor
    public MiniStock(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public void setIndBid( double indBid ) {
        // Pre
        this.preIndBid = this.indBid;
        // Current
        this.indBid = indBid;

    }

    public void setIndAsk( double indAsk ) {
        // Pre
        this.preIndAsk = this.indAsk;
        // Current
        this.indAsk = indAsk;
    }

    public void updateLastData() {
        lastCheckVolume = volume;
        up = false;
        down = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getInd() {
        return ind;
    }

    public void setInd(double ind) {
        if (ind == indAsk ) {
            up = true;
        }
        if (ind == indBid ) {
            down = true;
        }
        this.ind = ind;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getLastCheckVolume() {
        return lastCheckVolume;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public double getPreIndBid() {
        return preIndBid;
    }

    public double getPreIndAsk() {
        return preIndAsk;
    }
}
