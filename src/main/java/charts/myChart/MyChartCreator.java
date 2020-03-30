package charts.myChart;

import charts.IChartCreator;
import serverObjects.BASE_CLIENT_OBJECT;

public abstract class MyChartCreator implements IChartCreator {

    protected BASE_CLIENT_OBJECT client;

    public MyChartCreator( BASE_CLIENT_OBJECT client ) {
        this.client = client;
    }

}
