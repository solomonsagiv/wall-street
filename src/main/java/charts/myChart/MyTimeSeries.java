package charts.myChart;

import lists.MyChartList;
import locals.L;
import myJson.MyJson;
import options.JsonStrings;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesDataItem;
import org.json.JSONArray;
import serverObjects.BASE_CLIENT_OBJECT;

import java.awt.*;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

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
    private MyChartList myChartList;

    // Constructor
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
        Second second = new Second( item.getPeriod( ).getStart( ) );
        Date date = L.toDate( second.toString( ) );
        LocalDate localDate = LocalDate.of( date.getYear( ) + 1900, date.getMonth( ), date.getDay( ) );
        LocalTime localTime = LocalTime.of( date.getHours( ), date.getMinutes( ), date.getSeconds( ) );
        LocalDateTime localDateTime = LocalDateTime.of( localDate, localTime );

        json.put( JsonStrings.x, localDateTime );
        json.put( JsonStrings.y, item.getValue( ) );
        return json;
    }

    public JSONArray getLastItemAsArray() throws ParseException {
        MyJson json = getLastJson( );

        JSONArray jsonArray = new JSONArray( );
        jsonArray.put( json.getString( JsonStrings.x ) );
        jsonArray.put( json.getDouble( JsonStrings.y ) );
        return jsonArray;
    }

    public void add( MyJson json ) {
        Date date;
        try {
            if ( !json.getString( JsonStrings.x ).isEmpty( ) ) {

                LocalDateTime dateTime = LocalDateTime.parse( json.getString( JsonStrings.x ) );

                lastSeconde = new Second( dateTime.getSecond( ), dateTime.getMinute( ), dateTime.getHour( ), dateTime.getDayOfMonth( ), dateTime.getMonthValue( ), dateTime.getYear( ) );

                addOrUpdate( getLastSeconde( ), json.getDouble( JsonStrings.y ) );
                lastSeconde = ( Second ) lastSeconde.next( );
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