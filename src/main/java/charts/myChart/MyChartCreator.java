package charts.myChart;

import serverObjects.BASE_CLIENT_OBJECT;

public class MyChartCreator {

    public final int INFINITE = 10000000;
    protected BASE_CLIENT_OBJECT client;
    protected MyProps props;
    protected MyChartContainer_2 chartContainer;

    public MyChartCreator(BASE_CLIENT_OBJECT client, MyChartContainer_2 chartContainer) {
        this.client = client;
        this.chartContainer = chartContainer;
    }

    public MyProps getProps() {
        return props;
    }

    public void createChart() {
        chartContainer.create();
    }
}
