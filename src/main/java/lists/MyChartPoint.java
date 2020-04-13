package lists;

import org.jfree.data.time.Millisecond;
import org.jfree.data.time.Second;

import java.util.Date;

public class MyChartPoint {

    private Second x;
    private double y;

    public MyChartPoint(Second x, double y) {
        this.x = x;
        this.y = y;
    }

    public MyChartPoint(Long x, double y) {
        this.x = new Second( new Date( x ) );
        this.y = y;
    }

    public Second getX() {
        return x;
    }

    public void setX(Second x) {
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
