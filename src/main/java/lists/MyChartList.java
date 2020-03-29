package lists;

import java.util.ArrayList;

public class MyChartList extends ArrayList<MyChartPoint> {

    public static final int TIME = 0;
    public static final int VALUE = 1;

    ArrayList<Double> values = new ArrayList<>();

    @Override
    public boolean add(MyChartPoint myChartPoint) {
        values.add(myChartPoint.getValue());
        return super.add(myChartPoint);
    }

    public ArrayList<Double> getValues() {
        return values;
    }
}
