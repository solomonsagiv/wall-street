package charts.myChart;

import locals.L;
import myJson.MyJson;
import org.jfree.chart.plot.Marker;

import java.util.*;

interface IChartProps {

    int getSeconds();

    boolean isIncludeTicker();

    double getMarginMaxMin();

    float getStrokeSize();

    double getRangeMargin();

    boolean isGridLineVisible();

    boolean isLoadFromDB();

    Marker getMarker();

    boolean isLive();

    int getSleep();

    double getChartHighInDots();

    int getSecondsOnMess();

}

public class MyProps implements Cloneable {

    public static final double p_null = -10000.0;

    Map<String , Double> map = new HashMap<>();

    public double getProp(String key) {
        return map.getOrDefault(key, p_null);
    }

    public boolean getBool(String key) {
        return getProp(key) == 1;
    }

    public void setProp(String key, double value) {
        map.put(key, value);
    }

}