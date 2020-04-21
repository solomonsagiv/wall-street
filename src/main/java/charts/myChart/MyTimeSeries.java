package charts.myChart;

import lists.MyChartList;
import lists.MyChartPoint;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class MyTimeSeries extends TimeSeries implements ITimeSeries {

    public static final int TIME = 0;
    public static final int VALUE = 1;

    private Color color;
    private float stokeSize;
    private MyChartList myChartList;
    MyProps props;
    String name;
    Second lastSeconde;

    public MyTimeSeries( Comparable name, Color color, float strokeSize, MyProps props, MyChartList myChartList ) {
        super( name );
        this.color = color;
        this.stokeSize = strokeSize;
        this.props = props;
        this.myChartList = myChartList;
        this.name = name.toString();
    }

    public void loadData( ArrayList<Double> dots ) {
        try {
            System.out.println(name + " " + myChartList );

            LocalDateTime time = myChartList.get( 0 ).getX( );

            lastSeconde = new Second( time.getSecond( ), time.getMinute( ), time.getHour( ), time.getDayOfMonth( ), time.getMonth( ).getValue( ), time.getYear( ) );

            for ( int i = 0; i < myChartList.size( ); i++ ) {

                add( lastSeconde.next( ), myChartList.get( i ).getY( ) );
                dots.add( myChartList.get( i ).getY( ) );

                lastSeconde = ( Second ) lastSeconde.next( );

            }
            return;
        } catch ( IndexOutOfBoundsException e ) {
            e.printStackTrace();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        lastSeconde = new Second(  );
    }

    public double add() {
        double data;
        // live data
        if ( props.getBool( ChartPropsEnum.IS_LIVE ) ) {
            data = getData();
            addOrUpdate( getLastSeconde(), data );
        } else {
            MyChartPoint point = myChartList.getLast();
            data = point.getY();
            addOrUpdate( getLastSeconde(), data );
        }
        lastSeconde = ( Second ) lastSeconde.next();
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

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }


    public Second getLastSeconde() {
        if ( lastSeconde == null ) {
            lastSeconde = new Second(  );
        }
        return lastSeconde;
    }
}

interface ITimeSeries {
    double getData();
}