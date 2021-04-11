package charts.myChart;

import charts.timeSeries.MyTimeSeries;
import myJson.MyJson;
import serverObjects.BASE_CLIENT_OBJECT;
import java.awt.*;
import java.net.UnknownHostException;
import java.util.Locale;
import java.util.Map;

public class ChartsHandler {

    public static final String SIZE_CHART = "size";
    public static final String COLOR_CHART = "color";
    public static final String R_CHART = "r";
    public static final String B_CHART = "b";
    public static final String G_CHART = "g";

    BASE_CLIENT_OBJECT client;
    Map<String, MyChartCreator> charts;

    public ChartsHandler(BASE_CLIENT_OBJECT client) {
        this.client = client;
    }

    public void load_charts(MyJson json) {



    }

    private MyChart[] get_my_chart_arr( MyJson json_charts ) {

        MyChart[] charts_arr = new MyChart[json_charts.keySet().size()];

        int i = 0;

        for (String chart_key : json_charts.keySet()) {
            try {

                MyJson json_chart = new MyJson(chart_key);
                MyProps props = new MyProps(new MyJson(json_chart.getMyJson("props")));

                // NOT PROPS
                if (!chart_key.toLowerCase().equals("props")) {
                    MyTimeSeries[] series = get_series_arr(new MyJson(chart_key));
                    charts_arr[i] = new MyChart(client, series, props);
                    i++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    private MyTimeSeries[] get_series_arr( MyJson series_json ) {

        MyTimeSeries[] timeSeries_arr = new MyTimeSeries[series_json.keySet().size()];

        int i = 0;
        for (String serie_key : series_json.keySet()) {
            MyJson serie_json = new MyJson(serie_key);
            MyJson color_json = new MyJson(serie_json.getJSONObject(COLOR_CHART));

            int r = color_json.getInt(R_CHART);
            int b = color_json.getInt(B_CHART);
            int g = color_json.getInt(G_CHART);

            Color color = new Color(r, b, g);
            double size = serie_json.getDouble(SIZE_CHART);

            // Create the time series
            timeSeries_arr[i] = create_time_timeserie(serie_key, color, size);
            i++;
        }
        return timeSeries_arr;
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

}
