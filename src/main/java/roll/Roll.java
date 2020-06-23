package roll;

import exp.Exp;
import exp.ExpEnum;
import locals.IJson;
import myJson.MyJson;
import options.JsonStrings;
import serverObjects.BASE_CLIENT_OBJECT;

import java.util.ArrayList;
import java.util.List;

public class Roll implements IJson {

    // Variables
    BASE_CLIENT_OBJECT client;
    ExpEnum oe1, oe2;
    RollPriceEnum priceEnum;
    Exp e1, e2;
    private List rollList = new ArrayList<Double>();
    double rollSum = 0;

    // Constructor
    public Roll( BASE_CLIENT_OBJECT client, ExpEnum oe1, ExpEnum oe2, RollPriceEnum priceEnum ) {
        this.client = client;
        this.oe1 = oe1;
        this.oe2 = oe2;
        this.priceEnum = priceEnum;
    }

    public void addRoll() {
        try {
            // If options not set
            if ( e1 == null || e2 == null ) {
                e1 = client.getExps( ).getExp( oe1 );
                e2 = client.getExps( ).getExp( oe2 );
            }

            double roll = getRoll();
            rollList.add( roll );
            rollSum += roll;
        } catch ( NullPointerException e ) {
            e.printStackTrace();
        }
    }

    private double price( Exp exp ) {
        double price = 0;
        try {
            if ( priceEnum == RollPriceEnum.CONTRACT ) {
                price = exp.getOptions().getContract( );
            }

            if ( priceEnum == RollPriceEnum.FUTURE ) {
                price = exp.getFut( );
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
        return price( e2 ) - price( e1 );
    }

    @Override
    public MyJson getAsJson() {
        MyJson json = new MyJson(  );
        json.put( JsonStrings.roll, getRoll() );
        json.put( JsonStrings.rollAvg, getAvg() );
        return json;
    }

    @Override
    public void loadFromJson( MyJson json ) {}

    @Override
    public MyJson getResetJson() {
        return new MyJson();
    }
}