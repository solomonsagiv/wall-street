package lists;

import java.time.LocalTime;

public class MyChartPoint {

    private LocalTime x;
    private double y;

    public MyChartPoint(String x, double y) {
        this.x = LocalTime.parse( x );
        this.y = y;
    }

    public MyChartPoint(LocalTime x, double y) {
        this.x = x;
        this.y = y;
    }

    public LocalTime getX() {
        return x;
    }

    public void setX(LocalTime x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return  "[" + x + ", " + y + "]";
    }
}
