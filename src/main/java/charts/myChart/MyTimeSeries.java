package charts.myChart;

import lists.MyChartList;
import locals.L;
import myJson.MyJson;
import options.JsonStrings;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesDataItem;
import serverObjects.BASE_CLIENT_OBJECT;

import java.awt.*;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Date;

public abstract class MyTimeSeries extends TimeSeries implements ITimeSeries {

    public static final int TIME = 0;
    public static final int VALUE = 1;

    private Color color;
    private float stokeSize;
    private MyChartList myChartList;
    MyProps props;
    String name;
    Second lastSeconde;
    protected BASE_CLIENT_OBJECT client;

//    public MyTimeSeries( Comparable name ) {
//        super( name );
//        this.name = name.toString( );
//    }

    public MyTimeSeries( Comparable name, BASE_CLIENT_OBJECT client ) {
        super( name );
        this.client = client;
    }

    public TimeSeriesDataItem getLastItem() {
        return getDataItem( getItemCount( ) - 1 );
    }


    public MyJson getLastJson() throws ParseException {
        TimeSeriesDataItem item = getLastItem( );
        MyJson json = new MyJson( );
        json.put( JsonStrings.x, item.getPeriod( ).toString( ) );
        json.put( JsonStrings.y, item.getValue( ) );
        return json;
    }

//    public void loadData( ArrayList< Double > dots ) {
//        try {
//            LocalDateTime time = myChartList.get( 0 ).getX( );
//
//            lastSeconde = new Second( time.getSecond( ), time.getMinute( ), time.getHour( ), time.getDayOfMonth( ), time.getMonth( ).getValue( ), time.getYear( ) );
//
//            for ( int i = 0; i < myChartList.size( ); i++ ) {
//
//                add( lastSeconde.next( ), myChartList.get( i ).getY( ) );
//                dots.add( myChartList.get( i ).getY( ) );
//
//                lastSeconde = ( Second ) lastSeconde.next( );
//
//            }
//            return;
//        } catch ( IndexOutOfBoundsException e ) {
//            e.printStackTrace( );
//        } catch ( Exception e ) {
//            e.printStackTrace( );
//        }
//        lastSeconde = new Second( );
//    }

    public void add( MyJson json ) {
        Date date;
        try {
            if ( !json.getString( JsonStrings.x ).isEmpty() ) {

                date = L.toDate( json.getString( JsonStrings.x ) );
                lastSeconde = new Second( date );

                addOrUpdate( getLastSeconde( ), json.getDouble( JsonStrings.y ) );
                lastSeconde = ( Second ) lastSeconde.next( );
            }
        } catch ( Exception e ) {
            e.printStackTrace( );
        }
    }

//    public double add() {
//        double data = 0;
//        // live data
//        if ( props.getBool( ChartPropsEnum.IS_LIVE ) ) {
//            try {
//                data = getData( );
//                addOrUpdate( getLastSeconde( ), data );
//            } catch ( Exception e ) {
//                e.printStackTrace( );
//            }
//        } else {
//            try {
//                MyChartPoint point = myChartList.getLast( );
//                data = point.getY( );
//                addOrUpdate( getLastSeconde( ), data );
//            } catch ( Exception e ) {
//                e.printStackTrace( );
//            }
//        }
//        lastSeconde = ( Second ) lastSeconde.next( );
//        return data;
//    }

    public double add() {
        double data = 0;
        // live data
        try {
            data = getData( );
            addOrUpdate( getLastSeconde( ), data );
        } catch ( Exception e ) {
            e.printStackTrace( );
        }
        lastSeconde = ( Second ) lastSeconde.next( );
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
            lastSeconde = new Second( );
        }
        return lastSeconde;
    }
}

interface ITimeSeries {
    double getData() throws UnknownHostException;
}