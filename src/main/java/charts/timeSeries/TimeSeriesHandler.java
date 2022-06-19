package charts.timeSeries;

import java.util.HashMap;
import java.util.Map;

public class TimeSeriesHandler {

    public static final int INDEX = 11;
    public static final int BASKETS = 12;
    public static final int BID_ASK_COUNTER = 13;
    public static final int FUT_DAY = 16;
    public static final int FUT_WEEK = 17;
    public static final int FUT_MONTH = 18;
    public static final int FUT_Q1 = 19;
    public static final int FUT_Q2 = 20;
    public static final int INDEX_BID = 21;
    public static final int INDEX_ASK = 22;
    public static final int OP_AVG_DAY = 23;
    public static final int OP_AVG_240_CONITNUE = 33;
    public static final int OP_AVG_DAY_60 = 35;
    public static final int OP_AVG_DAY_5 = 36;
    public static final int DF_7_300 = 45;
    public static final int DF_7_3600 = 47;
    public static final int DF_7 = 48;
    public static final int DF_2 = 49;


    public static final int OP_AVG_WEEK = 50;
    public static final int OP_AVG_MONTH = 51;
    public static final int OP_AVG_Q1 = 52;
    public static final int OP_AVG_Q2 = 53;

    public static final int DF_2_300 = 54;
    public static final int DF_2_3600 = 55;

    private Map<String, MyTimeSeries> series_map = new HashMap<>();

    private Map<String, MyTimeSeries> getSeries_map() {
        return series_map;
    }

    public MyTimeSeries get(String timeserie_type) {
        return series_map.get(timeserie_type);
    }

    public void put(String timeserie_type, MyTimeSeries timeSeries){
        series_map.put(timeserie_type, timeSeries);
    }

    public void setSeries_map(Map<String, MyTimeSeries> series_map) {
        this.series_map = series_map;
    }
}
