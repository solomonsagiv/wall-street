package lists;

import org.jfree.data.time.Millisecond;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class MyChartList extends ArrayList<MyChartPoint> {

    private ArrayList<Double> values = new ArrayList<>();
    private JSONArray jsonArray = new JSONArray();

    @Override
    public boolean add( MyChartPoint point ) {
        values.add( point.getValue() );
        jsonArray.put( new JSONObject(  ).put( "x", point.getTime() ).put( "y", point.getValue() ) );
        return super.add( point );
    }

    public ArrayList<Double> getValues() {
        return values;
    }

    public MyChartPoint getLast() {
        return get( size() - 1 );
    }

    public void setData( JSONArray jsonArray ) {
        for ( Object s: jsonArray) {
            System.out.println(s );
            JSONObject jsonObject = new JSONObject(s.toString());
            System.out.println(jsonObject );
            add( new MyChartPoint( jsonObject.getLong( "x" ) , ( Double ) jsonObject.get( "y" ) ) );
        }
    }

    @Override
    public String toString() {
        return jsonArray.toString();

    }
}
