package lists;

import org.jfree.data.time.RegularTimePeriod;

public class MyChartPoint {

    private RegularTimePeriod time;
    private double value;

    public MyChartPoint(RegularTimePeriod time, double value) {
        this.time = time;
        this.value = value;
    }

    public RegularTimePeriod getTime() {
        return time;
    }

    public void setTime(RegularTimePeriod time) {
        this.time = time;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return  "[" + time + ", " + value + "]";
    }
}
