package options;

public class Call extends Option {

    private String name;
    private String intName;
    private String side = "c";

    public Call( double strike, int id ) {
        super( strike, id );

        name = side + strike;
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
