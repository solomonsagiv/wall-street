package charts.myCharts;

import charts.myChart.*;
import charts.timeSeries.MyTimeSeries;
import exp.Exp;
import exp.ExpStrings;
import exp.Exps;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;
import java.sql.ResultSet;
import java.util.ArrayList;

public class OnlyFuturesChart extends MyChartCreator {

    // Constructor
    public OnlyFuturesChart(BASE_CLIENT_OBJECT client ) {
        super(client, null, null);
    }

    ArrayList<MyTimeSeries> myTimeSeries = new ArrayList<>();

    MyChart[] charts;

    @Override
    public void createChart() {

        Exps exps = client.getExps();
        Exp exp_day = exps.getExp(ExpStrings.day);
        Exp exp_q1 = exps.getExp(ExpStrings.q1);

        // Props
        props = new MyProps();
        props.setProp(ChartPropsEnum.SECONDS, 200);
        props.setProp(ChartPropsEnum.IS_INCLUDE_TICKER, -1);
        props.setProp(ChartPropsEnum.MARGIN, 0.003);
        props.setProp(ChartPropsEnum.RANGE_MARGIN, 0.0);
        props.setProp(ChartPropsEnum.IS_RANGE_GRID_VISIBLE, -1);
        props.setProp(ChartPropsEnum.IS_LOAD_DB, -1);
        props.setProp(ChartPropsEnum.IS_LIVE, 1);
        props.setProp(ChartPropsEnum.SLEEP, 100);
        props.setProp(ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS, INFINITE);
        props.setProp(ChartPropsEnum.SECONDS_ON_MESS, 10);

        // ----- Chart 1 ----- //
        // Fut
        MyTimeSeries future_day = new MyTimeSeries("Future day", client) {
            @Override
            public ResultSet load_last_x_time(int minuts) {
                return null;
            }

            @Override
            public double getData() {
                return exp_day.get_future();
            }

            @Override
            public void load() {

            }
        };
        future_day.setColor(Themes.PURPLE);
        future_day.setStokeSize(2.25f);
        myTimeSeries.add(future_day);

        // Future Q1
        MyTimeSeries future_q1 = new MyTimeSeries("Future q1", client) {
            @Override
            public ResultSet load_last_x_time(int minuts) {
                return null;
            }

            @Override
            public double getData() {
                return exp_q1.get_future();
            }

            @Override
            public void load() {

            }
        };
        future_q1.setColor(Themes.GREEN);
        future_q1.setStokeSize(2.25f);
        myTimeSeries.add(future_q1);

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
