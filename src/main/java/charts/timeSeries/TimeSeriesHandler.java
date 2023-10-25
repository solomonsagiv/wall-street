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

    public static final int DF_8_RELATIVE = 67;
    public static final int INDEX_BID_SYNTHETIC = 68;
    public static final int INDEX_ASK_SYNTHETIC = 69;
    public static final int WINDOW_SIZE = 70;

    public static final int DOW_DF_8_ID = 71;
    public static final int DOW_RELATIVE_ID = 72;

    public static final int STOXX_DF_8_ID = 73;
    public static final int STOXX_RELATIVE_ID = 74;

    public static final int CAC_DF_8_ID = 75;
    public static final int CAC_RELATIVE_ID = 76;

    public static final int OP_AVG_Q1_14400 = 77;

    public static final int CAC_OP_AVG_3600 = 78;
    public static final int CAC_OP_AVG_900 = 79;

    public static final int STOXX_OP_AVG_3600 = 80;
    public static final int STOXX_OP_AVG_900 = 81;

    public static final int ROLL_3600 = 82;
    public static final int ROLL_900 = 83;
    public static final int OP_AVG_Q2_15 = 84;
    public static final int ROLL_300 = 85;
    public static final int ROLL_60 = 86;
    public static final int OP_AVG_1 = 87;
    public static final int OP_AVG_MONTH_15 = 88;
    public static final int OP_AVG_MONTH_60 = 89;
    public static final int FUT_MONTH = 90;
    public static final int INDEX_AVG_3600 = 91;
    public static final int INDEX_AVG_900 = 92;

    public static final int ROLL_WEEK_MONTH_3600 = 93;
    public static final int ROLL_WEEK_MONTH_900 = 94;

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
