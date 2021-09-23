package charts.myChart;

import serverObjects.BASE_CLIENT_OBJECT;

public abstract class MyChartCreator {

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

    public void createChart() {
        new Thread(this::init).start();
    }

    public abstract void init();

    public String getName() {
        return name;
    }

    public MyChart[] getCharts_arr() {
        return charts_arr;
    }
}
