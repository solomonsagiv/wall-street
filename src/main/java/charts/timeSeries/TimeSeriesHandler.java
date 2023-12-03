package charts.timeSeries;

import java.util.HashMap;
import java.util.Map;

public class TimeSeriesHandler {

    public static final int INDEX = 11;
    public static final int BASKETS = 12;
    public static final int FUT_WEEK = 16;
    public static final int FUT_Q1 = 19;
    public static final int FUT_Q2 = 20;
    public static final int INDEX_BID = 21;
    public static final int INDEX_ASK = 22;
    public static final int FUT_MONTH = 90;
    public static final int INDEX_AVG_3600 = 91;
    public static final int INDEX_AVG_900 = 92;
    public static final int INDEX_BID_SYNTHETIC = 68;
    public static final int INDEX_ASK_SYNTHETIC = 69;

    public static final int DF_7 = 48;
    public static final int DF_2 = 49;
    public static final int DF_2_ROLL = 103;
    public static final int DF_9 = 56;
    public static final int DF_8_900 = 63;
    public static final int DF_8_3600 = 64;

    public static final int OP_AVG_WEEK_DAILY = 23;
    public static final int OP_AVG_WEEK_240_CONTINUE = 33;
    public static final int OP_AVG_WEEK_3600 = 35;
    public static final int OP_AVG_WEEK_900 = 62;

    public static final int OP_AVG_Q1_900 = 65;
    public static final int OP_AVG_Q1_3600 = 66;
    public static final int OP_AVG_Q1_14400 = 77;
    public static final int OP_AVG_Q1_DAILY = 52;

    public static final int OP_AVG_Q2_DAILY = 53;

    public static final int OP_AVG_Q2_900 = 84;
    public static final int OP_AVG_Q2_3600 = 96;

    public static final int ROLL_WEEK_Q1_DAILY = 100;
    public static final int ROLL_WEEK_Q1_3600 = 82;
    public static final int ROLL_WEEK_Q1_900 = 83;

    public static final int ROLL_Q1_Q2_3600 = 97;
    public static final int ROLL_Q1_Q2_900 = 98;
    public static final int ROLL_Q1_Q2_DAILY = 99;

    public static final int DF_2_ROLL_WEEK = 101;

    public static final int DF_2_ROLL_MONTH = 102;

    // Last price = 103


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
