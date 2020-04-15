package lists;

import java.time.LocalDateTime;

public class MyChartPoint {

    private LocalDateTime x;
    private double y;

    public MyChartPoint(LocalDateTime x, double y) {
        this.x = x;
        this.y = y;
    }

    public MyChartPoint(String x, double y) {
        this.x = LocalDateTime.parse(x);
        this.y = y;
    }

    public LocalDateTime getX() {
        return x;
    }

    public void setX(LocalDateTime x) {
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
