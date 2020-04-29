package roll;

import options.Options;
import options.OptionsEnum;
import serverObjects.BASE_CLIENT_OBJECT;

import java.util.ArrayList;
import java.util.List;

public class Roll {

    // Variables
    BASE_CLIENT_OBJECT client;
    OptionsEnum oe1, oe2;
    RollPriceEnum priceEnum;
    Options o1, o2;
    private List rollList = new ArrayList<Double>();
    double rollSum = 0;

    // Constructor
    public Roll( BASE_CLIENT_OBJECT client, OptionsEnum oe1, OptionsEnum oe2, RollPriceEnum priceEnum ) {
        this.client = client;
        this.oe1 = oe1;
        this.oe2 = oe2;
        this.priceEnum = priceEnum;
    }

    public void addRoll() {
        try {
            // If options not set
            if ( o1 == null || o2 == null ) {
                o1 = client.getOptionsHandler( ).getOptions( oe1 );
                o2 = client.getOptionsHandler( ).getOptions( oe2 );
            }

            double roll = getRoll();
            rollList.add( roll );
            rollSum += roll;
        } catch ( NullPointerException e ) {
            e.printStackTrace();
        }
    }

    private double price(Options o) {
        double price = 0;
        try {
            if ( priceEnum == RollPriceEnum.CONTRACT ) {
                price = o.getContract( );
            }

            if ( priceEnum == RollPriceEnum.FUTURE ) {
                price = o.getFuture( );
            }
            return price;
        } catch ( Exception e ) {
            return price;
        }
    }

    public List getRollList() {
        return rollList;
    }

    public double getAvg() {
        if (rollSum == 0) {
            return 0;
        }
        return rollSum / rollList.size();
    }

    public double getRoll() {
        return price( o2 ) - price( o1 );
    }

}