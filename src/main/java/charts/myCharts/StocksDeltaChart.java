package charts.myCharts;

import charts.myChart.*;
import charts.timeSeries.MyTimeSeries;
import charts.timeSeries.TimeSeriesFactory;
import locals.Themes;
import org.jfree.chart.plot.ValueMarker;
import serverObjects.BASE_CLIENT_OBJECT;

import java.awt.*;

public class StocksDeltaChart extends MyChartCreator {

    // Constructor
    public StocksDeltaChart(BASE_CLIENT_OBJECT client) {
        super(client, null, null);
    }
    
    @Override
    public void init() {

        MyTimeSeries[] series;

        // Props
        props = new MyProps();
        props.setProp(ChartPropsEnum.SECONDS, INFINITE);
        props.setProp(ChartPropsEnum.IS_INCLUDE_TICKER, -1);
        props.setProp(ChartPropsEnum.MARGIN, 0.005);
        props.setProp(ChartPropsEnum.IS_RANGE_GRID_VISIBLE, 1);
        props.setProp(ChartPropsEnum.IS_LOAD_DB, 1);
        props.setProp(ChartPropsEnum.IS_LIVE, -1);
        props.setProp(ChartPropsEnum.SLEEP, 1000);
        props.setProp(ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS, INFINITE);
        props.setProp(ChartPropsEnum.SECONDS_ON_MESS, INFINITE);
        props.setProp(ChartPropsEnum.INCLUDE_DOMAIN_AXIS, 1);

        ValueMarker marker = new ValueMarker(0);
        marker.setPaint(Color.BLACK);
        marker.setStroke(new BasicStroke(2f));

        // -------- Index bid ask counter -------- //
        MyTimeSeries stocks_delta_serie = TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.STOCKS_DELTA_SERIES, client, null);
        stocks_delta_serie.setColor(Themes.LIFGT_BLUE_2);
        stocks_delta_serie.setStokeSize(1.5f);

        series = new MyTimeSeries[1];
        series[0] = stocks_delta_serie;

        MyChart stocks_delta_chart = new MyChart(client, series, props);
        // -------------------- Chart -------------------- //

        // ----- Charts ----- //
        MyChart[] charts = {stocks_delta_chart};

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer(client, charts, getClass().getName());
        chartContainer.create();

    }

}
