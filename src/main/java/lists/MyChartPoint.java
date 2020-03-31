package lists;

public class MyChartPoint {

    private Long x;
    private double y;

    public MyChartPoint(Long x, double y) {
        this.x = x;
        this.y = y;
    }

    public Long getX() {
        return x;
    }

    public void setX(Long x) {
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
