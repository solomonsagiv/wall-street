package charts.timeSeries;

import java.util.HashMap;
import java.util.Map;

public class TimeSeriesHandler {

    public static final int INDEX = 11;
    public static final int BASKETS = 12;
    public static final int FUT_DAY = 16;
    public static final int FUT_Q1 = 19;
    public static final int FUT_Q2 = 20;
    public static final int INDEX_BID = 21;
    public static final int INDEX_ASK = 22;
    public static final int OP_AVG_DAY = 23;
    public static final int OP_AVG_240_CONTINUE = 33;
    public static final int OP_AVG_60 = 35;
    public static final int OP_AVG_5 = 36;
    public static final int DF_7 = 48;
    public static final int DF_2 = 49;
    
    public static final int OP_AVG_WEEK = 50;
    public static final int OP_AVG_MONTH = 51;
    public static final int OP_AVG_Q1 = 52;
    public static final int OP_AVG_Q2 = 53;
    
    public static final int DF_8 = 56;
    public static final int DF_WEEK = 58;
    public static final int DF_MONTH = 59;
    public static final int DF_WEIGHTED = 60;
    public static final int STD_MOVE = 61;
    public static final int OP_AVG_15 = 62;

    public static final int DF_8_900 = 63;
    public static final int DF_8_3600 = 64;

    public static final int OP_AVG_Q1_15 = 65;
    public static final int OP_AVG_Q1_60 = 66;
    
    private Map<String, MyTimeSeries> series_map = new HashMap<>();
    public Map<String, MyTimeSeries> getSeries_map() {
        return series_map;
    }

    public MyTimeSeries get(String timeserie_type) {
        try {
            return series_map.get(timeserie_type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void put(String timeserie_type, MyTimeSeries timeSeries){
        series_map.put(timeserie_type, timeSeries);
    }

    public void setSeries_map(Map<String, MyTimeSeries> series_map) {
        this.series_map = series_map;
    }
}
