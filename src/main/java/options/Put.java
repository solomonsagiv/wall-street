package options;

public class Put extends Option {

    private String name;
    private String side = "p";
    private String intName;

    public Put( double strike, int id ) {
        super( strike, id );

        name = side + strike;
        setCallOrPut(false);
        intName = side + (int) strike;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getIntName() {
        return intName;
    }

    public String getSide() {
        return side;
    }

    @Override
    public String toString() {
        return getName() + ": " + "Bid: " + getBid() + ", Ask: " + getAsk() + ", Last: " + getLast();
    }
}
