package lists;

import org.jfree.data.time.RegularTimePeriod;

public class MyChartPoint {

    private Long time;
    private double value;

    public MyChartPoint(Long time, double value) {
        this.time = time;
        this.value = value;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
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
