package roll;

import options.Options;

import java.util.ArrayList;
import java.util.List;

public class Roll {

    // Variables
    Options closeOptions, farOptions;
    private List rollList = new ArrayList<Double>();
    double rollSum = 0;

    // Constructor
    public Roll( Options closeOptions, Options farOptions ) {
        this.closeOptions = closeOptions;
        this.farOptions = farOptions;
    }

    public void addRoll() {
        double roll = farOptions.getFuture() - closeOptions.getFuture();
        rollList.add( roll );
        rollSum += roll;
    }

    public List getRollList() {
        return rollList;
    }

    public double getAvg() {
        return rollSum / rollList.size();
    }

    public double getRoll() {
        return farOptions.getFuture() - closeOptions.getFuture();
    }

}