package dataBase.props;

import myJson.MyJson;
import serverObjects.BASE_CLIENT_OBJECT;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Props {

    BASE_CLIENT_OBJECT client;
    Map<String, Prop> props;

    public Props( BASE_CLIENT_OBJECT client ) {
        this.client = client;
        props = new HashMap<>();
        init();
    }

    public void addProp( Prop prop ) {
        props.put(prop.getName(), prop);
    }

    private void init() {
        // INDEX_BID_ASK_MARGIN
        addProp(new Prop(client, "INDEX_BID_ASK_MARGIN") {
            @Override
            public void setData(String json_text) {
                client.setIndexBidAskMargin(new MyJson(json_text).getDouble("value"));
            }

            @Override
            public MyJson getData() {
                MyJson json = new MyJson();
                json.putValue(client.getIndexBidAskMargin());
                return new MyJson();
            }
        });

        // STRIKE MARGIN
        addProp(new Prop(client, "STRIKE_MARGIN") {
            @Override
            public void setData(String json_text) {
                client.setStrikeMargin(new MyJson(json_text).getDouble("value"));
            }

            @Override
            public MyJson getData() {
                MyJson json = new MyJson();
                json.putValue(client.getStrikeMargin());
                return new MyJson();
            }
        });

        // TIME
        addProp(new Prop(client, " TIME") {
            @Override
            public void setData(String json_text) {
                MyJson json = new MyJson(json_text);
                client.setIndexStartTime(LocalTime.parse(json.getString("index_start_time")));
                client.setIndexEndTime(LocalTime.parse(json.getString("index_end_time")));
                client.setFutureEndTime(LocalTime.parse(json.getString("fut_end_time")));
            }

            @Override
            public MyJson getData() {
                MyJson json = new MyJson();
                json.put("index_start_time", client.getIndexStartTime());
                json.put("index_end_time", client.getIndexEndTime());
                json.put("fut_end_time", client.getFutureEndTime());
                return new MyJson();
            }
        });

        // EXCEL FILE LOCATION
        addProp(new Prop(client, " EXCEL_FILE_LOCATION") {
            @Override
            public void setData(String json_text) {
                MyJson json = new MyJson(json_text);
                client.getDdeHandler().setPath(json.getString("value"));
            }

            @Override
            public MyJson getData() {
                MyJson json = new MyJson();
                json.putValue(client.getDdeHandler().getPath());
                return new MyJson();
            }
        });

        // CHARTS
        addProp(new Prop(client, " CHARTS") {s
            @Override
            public void setData(String json_text) {
                MyJson json = new MyJson(json_text);
                client.getDdeHandler().setPath(json.getString("value"));
            }

            @Override
            public MyJson getData() {
                MyJson json = new MyJson();
                json.putValue(client.getDdeHandler().getPath());
                return new MyJson();
            }
        });



    }




}
