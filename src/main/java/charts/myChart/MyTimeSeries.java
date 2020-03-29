package charts.myChart;

import lists.MyChartList;
import lists.MyChartPoint;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import java.awt.*;
import java.util.ArrayList;

public abstract class MyTimeSeries extends TimeSeries implements ITimeSeries {

    public static final int TIME = 0;
    public static final int VALUE = 1;

    private Color color;
    private float stokeSize;
    private MyChartList myChartList;
    MyChartProps props;

    public MyTimeSeries( Comparable name, Color color, float strokeSize, MyChartProps props, MyChartList myChartList ) {
        super( name );
        this.color = color;
        this.stokeSize = strokeSize;
        this.props = props;
        this.myChartList = myChartList;
    }

    @Override
    public void add( RegularTimePeriod period, double value ) {

    }

    public double add() {
        double data;
        // live data
        if ( props.isLive() ) {
            data = getData();
            add( new Second( ), data );

        } else {
            MyChartPoint point = myChartList.getLast();
            data = point.getValue();
            add( point.getTime(), point.getValue() );
        }
        return data;
    }

    public Color getColor() {
        return color;
    }

    public void setColor( Color color ) {
        this.color = color;
    }

    public float getStokeSize() {
        return stokeSize;
    }

    public void setStokeSize( float stokeSize ) {
        this.stokeSize = stokeSize;
    }

    public MyChartList getMyChartList() {
        return myChartList;
    }

    public void setMyChartList( MyChartList myChartList ) {
        this.myChartList = myChartList;
    }
}

interface ITimeSeries {
    double getData();
}