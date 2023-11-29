package charts.myCharts;

import charts.myChart.*;
import charts.timeSeries.MyTimeSeries;
import charts.timeSeries.TimeSeriesFactory;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Ndx;
import serverObjects.indexObjects.Spx;

import java.awt.*;

public class Chart_wallstreet_2 extends MyChartCreator {

    Spx spx;
    Ndx ndx;

    // Constructor
    public Chart_wallstreet_2(BASE_CLIENT_OBJECT client) {
        super(client, null, null);
        spx = Spx.getInstance();
        ndx = Ndx.getInstance();
    }

    @Override
    public void init() {

        MyTimeSeries[] series;

        // Props
        props = new MyProps();
        props.setProp(ChartPropsEnum.SECONDS, INFINITE);
        props.setProp(ChartPropsEnum.IS_INCLUDE_TICKER, -1);
        props.setProp(ChartPropsEnum.MARGIN, 0.005);
        props.setProp(ChartPropsEnum.IS_RANGE_GRID_VISIBLE, -1);
        props.setProp(ChartPropsEnum.IS_LOAD_DB, 1);
        props.setProp(ChartPropsEnum.IS_LIVE, -1);
        props.setProp(ChartPropsEnum.SLEEP, 5000);
        props.setProp(ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS, INFINITE);
        props.setProp(ChartPropsEnum.SECONDS_ON_MESS, INFINITE);
        props.setProp(ChartPropsEnum.INCLUDE_DOMAIN_AXIS, 1);
        props.setProp(ChartPropsEnum.MARKER, 0);

        // ------------------- Index -------------------- //

        // Index
        MyTimeSeries index_serie = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.INDEX, client);
        index_serie.setColor(Color.BLACK);
        index_serie.setStokeSize(1f);

        // Index
        MyTimeSeries fut_day_serie = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.FUTURE_DAY_MULTIPLY_OP, client);
        fut_day_serie.setColor(Themes.GREEN);
        fut_day_serie.setStokeSize(1f);

        series = new MyTimeSeries[2];
        series[0] = index_serie;
        series[1] = fut_day_serie;

        // Chart
        MyChart indexChart = new MyChart(client, series, props);

        // ------------------ Op avg ------------------- //

        // --------- DF 9 ---------- //

        MyTimeSeries spx_df_8 = spx.getTimeSeriesHandler().get(TimeSeriesFactory.DF_9_CDF);
        spx_df_8.setColor(Themes.RED);
        spx_df_8.setStokeSize(1.2f);

        MyTimeSeries ndx_df_8 = ndx.getTimeSeriesHandler().get(TimeSeriesFactory.DF_9_CDF);
        ndx_df_8.setColor(Themes.GREEN);
        ndx_df_8.setStokeSize(1.2f);

        MyTimeSeries dow_df_8 = spx.getTimeSeriesHandler().get(TimeSeriesFactory.DOW_DF_8_CDF);
        dow_df_8.setColor(Themes.LIFGT_BLUE_2);
        dow_df_8.setStokeSize(1.2f);

        series = new MyTimeSeries[3];
        series[0] = spx_df_8;
        series[1] = ndx_df_8;
        series[2] = dow_df_8;

        // Chart
        MyChart df_chart_9 = new MyChart(client, series, props);
        
        // ------------------ Relative ------------------- //

        MyTimeSeries spx_relative_change = spx.getTimeSeriesHandler().get(TimeSeriesFactory.DF_8_RELATIVE);
        spx_relative_change.setColor(Themes.RED);
        spx_relative_change.setStokeSize(1.2f);

        MyTimeSeries ndx_relative_change = ndx.getTimeSeriesHandler().get(TimeSeriesFactory.DF_8_RELATIVE);
        ndx_relative_change.setColor(Themes.GREEN);
        ndx_relative_change.setStokeSize(1.2f);

        MyTimeSeries dow_relative_change = spx.getTimeSeriesHandler().get(TimeSeriesFactory.DOW_RELATIVE);
        dow_relative_change.setColor(Themes.LIFGT_BLUE_2);
        dow_relative_change.setStokeSize(1.2f);

        series = new MyTimeSeries[3];
        series[0] = spx_relative_change;
        series[1] = ndx_relative_change;
        series[2] = dow_relative_change;

        // Chart
        MyChart relative_chart = new MyChart(client, series, props);

        // -------------------- DF'S ---------------------- //

        MyTimeSeries df_2 = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_2_CDF);
        df_2.setColor(Themes.ORANGE);
        df_2.setStokeSize(1.2f);

        MyTimeSeries df_7 = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_7_CDF);
        df_7.setColor(Themes.PURPLE);
        df_7.setStokeSize(1.2f);

        MyTimeSeries df_8 = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_9_CDF);
        df_8.setColor(Themes.RED);
        df_8.setStokeSize(1.2f);

        series = new MyTimeSeries[3];
        series[0] = df_2;
        series[1] = df_7;
        series[2] = df_8;

        // Chart
        MyChart df_chart = new MyChart(client, series, props);

////
        // -------------------- Chart -------------------- //

        // ----- Charts ----- //
        MyChart[] charts = {indexChart, relative_chart, df_chart_9,  df_chart};

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer(client, charts, getClass().getName());
        chartContainer.create();
    }

}
