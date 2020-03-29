package lists;

import java.util.ArrayList;

public class MyChartList extends ArrayList<MyChartPoint> {

    private ArrayList<Double> values = new ArrayList<>();

    @Override
    public boolean add( MyChartPoint myChartPoint ) {
        values.add( myChartPoint.getValue() );
        return super.add( myChartPoint );
    }

    public ArrayList<Double> getValues() {
        return values;
    }

    public MyChartPoint getLast() {
        return get( size() - 1 );
    }
}
