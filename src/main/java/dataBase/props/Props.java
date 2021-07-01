package dataBase.props;

import exp.Exp;
import serverObjects.BASE_CLIENT_OBJECT;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Props {

    BASE_CLIENT_OBJECT client;
    private Map<String, Prop> map;
    Prop index_start_time;
    Prop index_end_time;
    Prop future_end_time;
    Prop excel_path;
    Prop main_exp;
    
    public static final String INDEX_START_TIME = "INDEX_START_TIME";
    public static final String INDEX_END_TIME = "INDEX_END_TIME";
    public static final String FUT_END_TIME = "FUT_END_TIME";
    public static final String CHARTS = "CHARTS";
    public static final String EXCEL_FILE_LOCATION = "EXCEL_FILE_LOCATION";
    public static final String MAIN_EXP = "MAIN_EXP";

    public Props(BASE_CLIENT_OBJECT client) {
        this.client = client;
        map = new HashMap<>();
        init_props();
        init();
    }

    private void init_props() {
        // ------------ INDEX START TIME -------------- //
        index_start_time = new Prop(client, INDEX_START_TIME) {
            @Override
            public void setData(Object data) {
                LocalTime time = LocalTime.parse(data.toString());
                client.setIndexStartTime(time);
            }

            @Override
            public Object getData() {
                LocalTime time = null;
                try {
                    time = client.getIndexStartTime();
                } catch (Exception e) {
                }
                return time;
            }
        };

        // ------------ INDEX END TIME -------------- //
        index_end_time = new Prop(client, INDEX_END_TIME) {
            @Override
            public void setData(Object data) {
                LocalTime time = LocalTime.parse(data.toString());
                client.setIndexEndTime(time);
            }

            @Override
            public Object getData() {
                LocalTime time = null;
                try {
                    time = client.getIndexEndTime();
                } catch (Exception e) {
                }
                return time;
            }
        };

        // ------------ FUTURE END TIME -------------- //
        future_end_time = new Prop(client, FUT_END_TIME) {
            @Override
            public void setData(Object data) {
                LocalTime time = LocalTime.parse(data.toString());
                client.setFutureEndTime(time);
            }

            @Override
            public Object getData() {
                LocalTime time = null;
                try {
                    time = client.getFutureEndTime();
                } catch (Exception e) {
                }
                return time;
            }
        };

        // ------------ EXCEL PATH -------------- //
        excel_path = new Prop(client, EXCEL_FILE_LOCATION) {
            @Override
            public void setData(Object data) {
                String path = data.toString();
                client.getDdeHandler().setPath(path);
            }

            @Override
            public Object getData() {
                String path = "";
                try {
                    path = client.getDdeHandler().getPath();
                } catch (Exception e) {
                }
                return path;
            }
        };

        // ------------ MAIN EXP -------------- //
        main_exp = new Prop(client, MAIN_EXP) {
            @Override
            public void setData(Object data) {
                Exp exp = client.getExps().getExp(data.toString());
                client.getExps().setMainExp(exp);
            }

            @Override
            public Object getData() {
                String exp = null;
                try {
                    exp = client.getExps().getMainExp().getName();
                } catch (Exception e) {
                }
                return exp;
            }
        };
    }

    private void init() {
        map.put(INDEX_START_TIME, index_start_time);
        map.put(INDEX_END_TIME, index_end_time);
        map.put(FUT_END_TIME, future_end_time);
//        map.put(CHARTS, CHARTS);
        map.put(EXCEL_FILE_LOCATION, excel_path);
        map.put(MAIN_EXP, main_exp);
    }

    public Map<String, Prop> getMap() {
        return map;
    }


}

