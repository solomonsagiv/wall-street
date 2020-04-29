package basketFinder;

public class MiniStock {

    // Variables
    private String name;
    private int id;
    private double ind;
    private double volume = 0;
    private double lastCheckVolume = 0;

    // Constructor
    public MiniStock( String name, int id ) {
        this.name = name;
        this.id = id;
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
