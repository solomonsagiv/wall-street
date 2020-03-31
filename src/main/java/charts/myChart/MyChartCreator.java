package charts.myChart;

import charts.IChartCreator;
import serverObjects.BASE_CLIENT_OBJECT;

public abstract class MyChartCreator implements IChartCreator {

    public final int INFINITE = 1000000000;

    protected BASE_CLIENT_OBJECT client;

    protected MyProps props;

    public MyChartCreator( BASE_CLIENT_OBJECT client ) {
        this.client = client;
    }

}
