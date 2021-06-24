package charts.myCharts;

import charts.myChart.*;
import charts.timeSeries.MyTimeSeries;
import charts.timeSeries.TimeSeriesFactory;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;

import java.util.ArrayList;

public class DecisionsFuncChart extends MyChartCreator {

    // Main
    public static void main(String[] args) {
        Spx client = Spx.getInstance();
        client.startAll();

        DecisionsFuncChart chart = new DecisionsFuncChart(client);
        chart.createChart();
    }

    // Constructor
    public DecisionsFuncChart(BASE_CLIENT_OBJECT client ) {
        super(client, null, null);
    }

    ArrayList<MyTimeSeries> myTimeSeries = new ArrayList<>();

    MyChart[] charts;

    @Override
    public void createChart() {

        // Props
        props = new MyProps();
        props.setProp(ChartPropsEnum.SECONDS, 36);
        props.setProp(ChartPropsEnum.IS_INCLUDE_TICKER, -1);
        props.setProp(ChartPropsEnum.MARGIN, 0.003);
        props.setProp(ChartPropsEnum.RANGE_MARGIN, 0.0);
        props.setProp(ChartPropsEnum.IS_GRID_VISIBLE, -1);
        props.setProp(ChartPropsEnum.IS_LOAD_DB, -1);
        props.setProp(ChartPropsEnum.IS_LIVE, -1);
        props.setProp(ChartPropsEnum.SLEEP, 20000);
        props.setProp(ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS, INFINITE);
        props.setProp(ChartPropsEnum.SECONDS_ON_MESS, 10);
        props.setProp(ChartPropsEnum.MARKER, 0);
        props.setProp(ChartPropsEnum.INCLUDE_DOMAIN_AXIS, 1);
        props.setProp(ChartPropsEnum.RETRO_MINS, 20);

        // ----- Chart 1 ----- //
        // Speed 900
        MyTimeSeries speed_900 = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.SPEED_900, client, null);
        speed_900.setColor(Themes.BINANCE_GREEN);
        speed_900.setStokeSize(2.25f);

        // Acc 900
        MyTimeSeries acc_900 = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.ACC_900, client, null);
        acc_900.setColor(Themes.RED);
        acc_900.setStokeSize(2.25f);

        // Acc 300
        MyTimeSeries acc_300 = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.ACC_300, client, null);
        acc_300.setColor(Themes.PINK_LIGHT);
        acc_300.setStokeSize(2.25f);

        myTimeSeries.add(speed_900);
        myTimeSeries.add(acc_900);
        myTimeSeries.add(acc_300);

        // Series
        MyTimeSeries[] series = toArray();

        // Chart
        MyChart chart = new MyChart(client, series, props);

        // ----- Charts ----- //
        MyChart[] charts = {chart};

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer(client, charts, getClass().getName());
        chartContainer.create();
    }

    private MyChart[] getCharts() {
        return charts;
    }

    private MyTimeSeries[] toArray() {
        MyTimeSeries[] arr = new MyTimeSeries[myTimeSeries.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = myTimeSeries.get(i);
        }
        return arr;
    }

}
