package dataBase.props;

import serverObjects.BASE_CLIENT_OBJECT;

import java.util.HashMap;
import java.util.Map;

public class Props {

    BASE_CLIENT_OBJECT client;
    Map<String, String> map;

    public static final String INDEX_START_TIME = "INDEX_START_TIME";
    public static final String INDEX_END_TIME = "INDEX_END_TIME";
    public static final String FUT_END_TIME = "FUT_END_TIME";
    public static final String CHARTS = "CHARTS";
    public static final String EXCEL_FILE_LOCATION = "EXCEL_FILE_LOCATION";
    public static final String MAIN_EXP = "MAIN_EXP";

    public Props(BASE_CLIENT_OBJECT client) {
        this.client = client;
        map = new HashMap<>();
        init();
    }

    private void init() {
        map.put(INDEX_START_TIME, INDEX_START_TIME);
        map.put(INDEX_END_TIME, INDEX_END_TIME);
        map.put(FUT_END_TIME, FUT_END_TIME);
        map.put(CHARTS, CHARTS);
        map.put(EXCEL_FILE_LOCATION, EXCEL_FILE_LOCATION);
        map.put(MAIN_EXP, MAIN_EXP);
    }

}
