package charts.myCharts;

import charts.myChart.*;
import charts.timeSeries.MyTimeSeries;
import charts.timeSeries.TimeSeriesFactory;
import exp.ExpStrings;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;

import java.util.ArrayList;

public class DecisionsFuncChart2 extends MyChartCreator {

    // Main
    public static void main(String[] args) {
        Spx client = Spx.getInstance();
        client.startAll();

        DecisionsFuncChart2 chart = new DecisionsFuncChart2(client);
        chart.createChart();
    }

    // Constructor
    public DecisionsFuncChart2(BASE_CLIENT_OBJECT client ) {
        super(client, null, null);
    }

    ArrayList<MyTimeSeries> myTimeSeries = new ArrayList<>();

    MyChart[] charts;
    
    @Override
    public void init() {

        // Props
        props = new MyProps();
        props.setProp(ChartPropsEnum.SECONDS, 36);
        props.setProp(ChartPropsEnum.IS_INCLUDE_TICKER, -1);
        props.setProp(ChartPropsEnum.MARGIN, 0.003);
        props.setProp(ChartPropsEnum.IS_RANGE_GRID_VISIBLE, -1);
        props.setProp(ChartPropsEnum.IS_LOAD_DB, -1);
        props.setProp(ChartPropsEnum.IS_LIVE, -1);
        props.setProp(ChartPropsEnum.SLEEP, 15000);
        props.setProp(ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS, INFINITE);
        props.setProp(ChartPropsEnum.SECONDS_ON_MESS, 10);
        props.setProp(ChartPropsEnum.MARKER, 0);
        props.setProp(ChartPropsEnum.INCLUDE_DOMAIN_AXIS, 1);
        props.setProp(ChartPropsEnum.RETRO_MINS, 15);

        // ----------------------------------------- Chart 1 ------------------------------------------- //
        // session_4_version_601
        MyTimeSeries session_4_version_601_serie = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.SESSION_4_VERSION_601, client, null);
        session_4_version_601_serie.setColor(Themes.BINANCE_RED);
        session_4_version_601_serie.setStokeSize(2.25f);

        myTimeSeries.add(session_4_version_601_serie);

        // Series
        MyTimeSeries[] series = toArray();

        // Chart
        MyChart session_func_chart = new MyChart(client, series, props);

        // ----------------------------------------- Chart 2 ----------------------------------------- //
        // Op avg
        MyTimeSeries op_avg_60_serie = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_HOUR_SERIES, client, client.getExps().getExp(ExpStrings.day));
        op_avg_60_serie.setColor(Themes.BINANCE_RED);
        op_avg_60_serie.setStokeSize(2.25f);

        myTimeSeries.add(op_avg_60_serie);

        // Series
        series = toArray();

        // Chart
        MyChart chart = new MyChart(client, series, props);


        // ----- Charts ----- //
        MyChart[] charts = {session_func_chart};

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
