package backTestChart;

import charts.myChart.*;
import charts.timeSeries.MyTimeSeries;
import org.jfree.data.time.Second;
import serverObjects.BASE_CLIENT_OBJECT;

import java.awt.*;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DeltaChartBackTest extends MyChartCreator {

    int year;
    int month;
    int day;

    Map<String, MyTimeSeries> seriesMap;
    Map<String, ArrayList<Double>> dataMap;
    ArrayList<LocalTime> dateTimeArray;
    String[] chartsNames;
    Color[] colors;

    // Constructor
    public DeltaChartBackTest(BASE_CLIENT_OBJECT client, int year, int month, int day, Map<String, ArrayList<Double>> dataMap, Color[] colors, ArrayList<LocalTime> dateTimeArray, String[] chartsNames) {
        super(client);
        this.dataMap = dataMap;
        this.year = year;
        this.month = month;
        this.day = day;
        seriesMap = new HashMap<>();
        this.dateTimeArray = dateTimeArray;
        this.chartsNames = chartsNames;
        this.colors = colors;
        crateSeries();
    }

    private void crateSeries() {

        // For each dataMap from DB create new serie and fill with data

        for (int i = 0; i < chartsNames.length; i++) {
            String chartName = chartsNames[i];
            Color color = colors[i];
            ArrayList<Double> list = dataMap.get(chartName);

            MyTimeSeries serie = new MyTimeSeries(chartName, null) {
                @Override
                public double getData() throws UnknownHostException {
                    return 0;
                }

                @Override
                public void load_data() {

                }
            };
            serie.setColor(color);
            serie.setStokeSize(1.5f);

            // Append serie to seriesMap
            seriesMap.put(chartName, serie);

            // Append data
            for (int j = 0; j < list.size(); j++) {
                LocalTime time = dateTimeArray.get(j);
                Second second = new Second(time.getSecond(), time.getMinute(), time.getHour(), day, month, year);
                serie.addOrUpdate(second, list.get(j));
            }

        }

    }

    @Override
    public void createChart() throws CloneNotSupportedException {

        // Props
        props = new MyProps();
        props.setProp(ChartPropsEnum.SECONDS, INFINITE);
        props.setProp(ChartPropsEnum.IS_INCLUDE_TICKER, -1);
        props.setProp(ChartPropsEnum.MARGIN, .17);
        props.setProp(ChartPropsEnum.RANGE_MARGIN, 0.0);
        props.setProp(ChartPropsEnum.IS_GRID_VISIBLE, -1);
        props.setProp(ChartPropsEnum.IS_LOAD_DB, 1);
        props.setProp(ChartPropsEnum.IS_LIVE, -1);
        props.setProp(ChartPropsEnum.SLEEP, INFINITE);
        props.setProp(ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS, INFINITE);
        props.setProp(ChartPropsEnum.SECONDS_ON_MESS, INFINITE);
        props.setProp(ChartPropsEnum.INCLUDE_DOMAIN_AXIS, 1);

        MyChart[] charts = new MyChart[chartsNames.length];

        // Create each chart

        for (int i = 0; i < charts.length; i++) {

            String chartName = chartsNames[i];

            // Series
            MyTimeSeries[] series = {seriesMap.get(chartName)};

            // Chart
            MyChart chart = new MyChart(client, series, props);

            // Append to all charts
            charts[i] = chart;
        }

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer(client, charts, getClass().getName());
        chartContainer.create();

    }

}
