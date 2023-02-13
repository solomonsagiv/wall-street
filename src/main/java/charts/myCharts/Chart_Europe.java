package charts.myCharts;

import charts.myChart.*;
import charts.timeSeries.MyTimeSeries;
import charts.timeSeries.TimeSeriesFactory;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Dax;
import java.awt.*;

public class Chart_Europe extends MyChartCreator {

    Dax dax;

    // Constructor
    public Chart_Europe(BASE_CLIENT_OBJECT client) {
        super(client, null, null);
        dax = Dax.getInstance();
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

        // --------- Op avg ---------- //

        MyTimeSeries dax_df_8 = dax.getTimeSeriesHandler().get(TimeSeriesFactory.DF_8_CDF);
        dax_df_8.setColor(Themes.RED);
        dax_df_8.setStokeSize(1.2f);

        MyTimeSeries cac_df_8 = dax.getTimeSeriesHandler().get(TimeSeriesFactory.CAC_DF_8_CDF);
        cac_df_8.setColor(Themes.GREEN);
        cac_df_8.setStokeSize(1.2f);

        MyTimeSeries stoxx_df_8 = dax.getTimeSeriesHandler().get(TimeSeriesFactory.STOXX_DF_8_CDF);
        stoxx_df_8.setColor(Themes.LIFGT_BLUE_2);
        stoxx_df_8.setStokeSize(1.2f);

        series = new MyTimeSeries[3];
        series[0] = dax_df_8;
        series[1] = cac_df_8;
        series[2] = stoxx_df_8;

        // Chart
        MyChart df_chart = new MyChart(client, series, props);
        
        // ------------------ DF ------------------- //

        MyTimeSeries dax_relative_change = dax.getTimeSeriesHandler().get(TimeSeriesFactory.DF_8_RELATIVE);
        dax_relative_change.setColor(Themes.RED);
        dax_relative_change.setStokeSize(1.2f);

        MyTimeSeries cac_relative_change = dax.getTimeSeriesHandler().get(TimeSeriesFactory.CAC_RELATIVE);
        cac_relative_change.setColor(Themes.GREEN);
        cac_relative_change.setStokeSize(1.2f);

        MyTimeSeries stoxx_relative_change = dax.getTimeSeriesHandler().get(TimeSeriesFactory.STOXX_RELATIVE);
        stoxx_relative_change.setColor(Themes.LIFGT_BLUE_2);
        stoxx_relative_change.setStokeSize(1.2f);

        series = new MyTimeSeries[3];
        series[0] = dax_relative_change;
        series[1] = cac_relative_change;
        series[2] = stoxx_relative_change;

        // Chart
        MyChart relative_chart = new MyChart(client, series, props);

////
        // -------------------- Chart -------------------- //

        // ----- Charts ----- //
        MyChart[] charts = {indexChart, relative_chart, df_chart};

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer(client, charts, getClass().getName());
        chartContainer.create();
    }

}
