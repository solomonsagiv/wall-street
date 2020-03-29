package lists;

import java.util.ArrayList;

public class MyChartList extends ArrayList<MyChartPoint> {

    ArrayList<Double> values = new ArrayList<>();

    @Override
    public boolean add( MyChartPoint myChartPoint ) {
        values.add( myChartPoint.getValue() );
        return super.add( myChartPoint );
    }

    public MyChartPoint getLast() {
        return get( size() - 1 );
    }
}
