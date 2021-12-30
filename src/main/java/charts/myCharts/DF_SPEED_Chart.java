package charts.myCharts;

import charts.myChart.*;
import charts.timeSeries.MyTimeSeries;
import charts.timeSeries.TimeSeriesFactory;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;

import java.util.ArrayList;

public class DF_SPEED_Chart extends MyChartCreator {

    // Main
    public static void main(String[] args) {
        DF_SPEED_Chart chart = new DF_SPEED_Chart(Spx.getInstance());
        chart.createChart();
    }

    // Constructor
    public DF_SPEED_Chart(BASE_CLIENT_OBJECT client ) {
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
        props.setProp(ChartPropsEnum.IS_RANGE_GRID_VISIBLE, -1);
        props.setProp(ChartPropsEnum.IS_LOAD_DB, -1);
        props.setProp(ChartPropsEnum.IS_DOMAIN_GRID_VISIBLE, 1);
        props.setProp(ChartPropsEnum.IS_LIVE, -1);
        props.setProp(ChartPropsEnum.SLEEP, 20000);
        props.setProp(ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS, INFINITE);
        props.setProp(ChartPropsEnum.SECONDS_ON_MESS, 10);
        props.setProp(ChartPropsEnum.MARKER, 0);
        props.setProp(ChartPropsEnum.INCLUDE_DOMAIN_AXIS, 1);
        props.setProp(ChartPropsEnum.RETRO_MINS, 30);

        // ----------------------------------------- DF ------------------------------------------- //
        // Df 5
        MyTimeSeries df_n_serie = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_N_DAY, client, null);
        df_n_serie.setColor(Themes.BLUE_2);
        df_n_serie.setStokeSize(2.25f);
        myTimeSeries.add(df_n_serie);

        // Df n 5
        MyTimeSeries df_serie = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_DAY, client, null);
        df_serie.setColor(Themes.GREEN);
        df_serie.setStokeSize(2.25f);
        myTimeSeries.add(df_serie);

        // Series
        MyTimeSeries[] series = toArray();

        // Chart
        MyChart dfs_chart = new MyChart(client, series, props);

        // ----------------------------------------- Speeds ------------------------------------------- //
        // Df 5
        MyTimeSeries df_n_speed_serie = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_N_DAY_SPEED, client, null);
        df_n_speed_serie.setColor(Themes.BLUE_2);
        df_n_speed_serie.setStokeSize(2.25f);
        myTimeSeries.add(df_n_speed_serie);

        // Df n 5
        MyTimeSeries df_speed_serie = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_DAY_SPEED, client, null);
        df_speed_serie.setColor(Themes.GREEN);
        df_speed_serie.setStokeSize(2.25f);
        myTimeSeries.add(df_speed_serie);

        // Series
        series = toArray();

        // Chart
        MyChart speeds_chart = new MyChart(client, series, props);


        // ----- Charts ----- //
        MyChart[] charts = {dfs_chart, speeds_chart};

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
