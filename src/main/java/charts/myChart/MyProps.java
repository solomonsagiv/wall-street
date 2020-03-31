package charts.myChart;

import org.jfree.chart.plot.Marker;
import java.util.Properties;

public class MyProps {

    private Properties properties = new Properties( );

    public void setProp( Enum e, Object o ) {
        properties.put( e, o );
    }

    public Object get( Enum e ) {
        return properties.get( e );
    }

    public double getDouble( Enum e ) {
        return ( double ) properties.get( e );
    }

    public int getInt( Enum e ) {
        return ( int ) properties.get( e );
    }

    public String getString( Enum e ) {
        return ( String ) properties.get( e );
    }

    public boolean getBool( Enum e ) {
        return ( boolean ) properties.get( e );
    }

    public float getFloat( Enum e ) {
        return ( float ) properties.get( e );
    }

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