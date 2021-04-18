package charts.myChart;

import charts.myCharts.IndexCounter_Index_Chart;
import charts.timeSeries.MyTimeSeries;
import locals.L;
import myJson.MyJson;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;

import java.awt.*;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class ChartsHandler {


    public static void main(String[] args) {
        ChartsHandler chartsHandler = new ChartsHandler(Spx.getInstance());


        System.out.println(chartsHandler.getJsonData().toString(4));
    }

    public static final String SIZE_CHART = "size";
    public static final String COLOR_CHART = "color";
    public static final String R_CHART = "r";
    public static final String B_CHART = "b";
    public static final String G_CHART = "g";

    BASE_CLIENT_OBJECT client;
    Map<String, MyChartCreator> charts_map;

    public ChartsHandler(BASE_CLIENT_OBJECT client) {
        this.client = client;
        charts_map = new HashMap<>();

        charts_map.put("INDEX_COUNTER", new IndexCounter_Index_Chart(client));
    }

    private MyTimeSeries create_time_timeserie(String name, Color color, double size) {

        MyTimeSeries timeSeries = new MyTimeSeries(name, client) {
            @Override
            public double getData() throws UnknownHostException {
                return 0;
            }

            @Override
            public void load_data() {

            }
        };
        timeSeries.setColor(color);
        timeSeries.setStokeSize((float) size);

        return timeSeries;
    }

    public MyJson getJsonData() {

        MyJson creator_json = new MyJson();

        // Each Chart creator
        for (MyChartCreator chart_creator : charts_map.values()) {

            MyJson charts_json = new MyJson();

            // Each Chart
            for (MyChart chart: chart_creator.getCharts_arr()) {

                // Chart json
                MyJson chart_json = new MyJson();

                // Params
                MyJson props = chart.getProps().getAsJson();
                MyJson series_arr_json = get_series_arr_json(chart.getSeries());

                // Set data
                chart_json.put("props", props);
                chart_json.put("series", series_arr_json);

                // Set chart to charts
                charts_json.put(L.str(chart.hashCode()), chart_json);
            }

            // Set charts to creator
            creator_json.put("charts", charts_json);

        }
        return creator_json;

    }

    private MyJson get_series_arr_json( MyTimeSeries[] timeseries_arr ) {

        MyJson series_arr_json = new MyJson();

        // Each Series
        for ( MyTimeSeries timeseries : timeseries_arr ) {
            MyJson serie_json = new MyJson();
            String series_type = timeseries.getSeries_type();
            MyJson color_json = timeseries.getColorJson();
            float stroke_size = timeseries.getStokeSize();

            // Put series data
            serie_json.put("color", color_json);
            serie_json.put("stroke", stroke_size);

            // Put in series json
            series_arr_json.put(series_type, serie_json);
        }

        return series_arr_json;

    }


}
