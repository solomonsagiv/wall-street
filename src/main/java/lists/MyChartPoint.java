package lists;

import locals.L;
import options.JsonStrings;
import org.json.JSONObject;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;

public class MyChartPoint {

    private LocalDateTime x;
    private double y;

    public MyChartPoint( LocalDateTime x, double y ) {
        this.x = x;
        this.y = y;
    }

    public MyChartPoint( String x, double y ) {
        this.x = LocalDateTime.parse( x );
        this.y = y;
    }

    public MyChartPoint( JSONObject json ) {
        try {
            Date date = L.toDate( json.getString( JsonStrings.x ) );
            this.x = LocalDateTime.of( date.getYear() + 1900, date.getMonth(), date.getDay(), date.getHours(), date.getMinutes(), date.getSeconds());
            this.y = json.getDouble( "y" );
        } catch ( ParseException e ) {
            e.printStackTrace( );
        }
    }

    public LocalDateTime getX() {
        return x;
    }

    public void setX( LocalDateTime x ) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY( double y ) {
        this.y = y;
    }

    public JSONObject getAsJson() {
        JSONObject object = new JSONObject( );
        object.put( "x", getX( ) );
        object.put( "y", getY( ) );
        return object;
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }
}
