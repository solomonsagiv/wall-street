package charts.myChart;

import serverObjects.BASE_CLIENT_OBJECT;

public class MyChartCreator {

    public final int INFINITE = 10000000;
    protected BASE_CLIENT_OBJECT client;
    protected MyProps props;
    protected MyChart[] charts_arr;
    protected String name;

    public MyChartCreator(BASE_CLIENT_OBJECT client, MyChart[] charts_arr, String name ) {
        this.client = client;
        this.charts_arr = charts_arr;
        this.name = name;
    }

    public MyProps getProps() {
        return props;
    }

    public void init() {

    }

    public void createChart() {
        MyChartContainer_2 chartContainer_2 = new MyChartContainer_2(client, charts_arr, name);
        chartContainer_2.create();
    }

    public String getName() {
        return name;
    }

    public MyChart[] getCharts_arr() {
        return charts_arr;
    }
}
