package charts.myChart;

import myJson.MyJson;
import options.JsonStrings;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesDataItem;
import serverObjects.BASE_CLIENT_OBJECT;

import java.awt.*;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

interface ITimeSeries {
    double getData() throws UnknownHostException;
}

public abstract class MyTimeSeries extends TimeSeries implements ITimeSeries {

    public static final int TIME = 0;
    public static final int VALUE = 1;
    protected BASE_CLIENT_OBJECT client;
    MyProps props;
    String name;
    Second lastSeconde;
    private Color color;
    private float stokeSize;

    ArrayList<Double> values;

    // Constructor
    public MyTimeSeries( Comparable name, BASE_CLIENT_OBJECT client ) {
        super( name );
        this.client = client;
        values = new ArrayList<>();
    }

    public TimeSeriesDataItem getLastItem() {
        return getDataItem( getItemCount( ) - 1 );
    }

    public void add( LocalDateTime time ) {
        try {
            Second second = new Second( time.getSecond( ), time.getMinute( ), time.getHour( ), time.getDayOfMonth( ), time.getMonthValue( ), time.getYear( ) );
            double data = getData( );
            values.add( data );
            addOrUpdate( second, data );
        } catch ( UnknownHostException e ) {
            e.printStackTrace( );
        }
    }

    public ArrayList< Double > getValues() {
        return values;
    }

    public MyJson getLastJson() throws ParseException {
        TimeSeriesDataItem item = getLastItem( );
        MyJson json = new MyJson( );
        LocalDateTime localDateTime = LocalDateTime.now( );

        json.put( JsonStrings.x, localDateTime );
        json.put( JsonStrings.y, item.getValue( ) );
        return json;
    }

    public void add( MyJson json ) {
        try {
            if ( !json.getString( JsonStrings.x ).isEmpty( ) ) {

                LocalDateTime dateTime = LocalDateTime.parse( json.getString( JsonStrings.x ) );

                lastSeconde = new Second( dateTime.getSecond( ), dateTime.getMinute( ), dateTime.getHour( ), dateTime.getDayOfMonth( ), dateTime.getMonthValue( ), dateTime.getYear( ) );

                double data = json.getDouble( JsonStrings.y );
                values.add( data );
                addOrUpdate( lastSeconde, data );
            }
        } catch ( Exception e ) {
            System.out.println( client.getName( ) + " " + json );
            e.printStackTrace( );
        }
    }

    public double add() {
        double data = 0;
        // live data
        try {
            data = getData( );
            values.add( data );
            addOrUpdate( getLastSeconde( ), data );
        } catch ( Exception e ) {
            e.printStackTrace( );
        }
        lastSeconde = ( Second ) lastSeconde.next( );
        return data;
    }

    public void remove( int index ) {
        delete( index, index );
        values.remove( index );
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