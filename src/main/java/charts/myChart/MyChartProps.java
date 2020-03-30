package charts.myChart;

import org.jfree.chart.plot.Marker;

public abstract class MyChartProps implements IChartProps {

}

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