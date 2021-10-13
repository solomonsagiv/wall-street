package dataBase.mySql.dataUpdaters;

import charts.timeSeries.MyTimeSeries;
import dataBase.MyTick;
import dataBase.MyTickTimeSerie;
import dataBase.mySql.MySql;
import dataBase.props.Prop;
import exp.E;
import exp.Exp;
import exp.ExpStrings;
import exp.Exps;
import serverObjects.BASE_CLIENT_OBJECT;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class IDataBaseHandler {

    public static final int INDEX_TABLE = 11;
    public static final int BASKETS_TABLE = 12;
    public static final int BID_ASK_COUNTER_TABLE = 13;
    public static final int INDEX_RACES_TABLE = 14;
    public static final int FUT_RACES_TABLE = 15;
    public static final int FUT_DAY_TABLE = 16;
    public static final int FUT_WEEK_TABLE = 17;
    public static final int FUT_MONTH_TABLE = 18;
    public static final int FUT_Q1_TABLE = 19;
    public static final int FUT_Q2_TABLE = 20;
    public static final int INDEX_BID_TABLE = 21;
    public static final int INDEX_ASK_TABLE = 22;
    public static final int OP_AVG_DAY_TABLE = 23;
    public static final int OP_AVG_15_DAY_TABLE = 24;
    public static final int INDEX_DELTA_TABLE = 25;
    public static final int FUT_E1_TICK_SPEED = 26;
    public static final int FUT_DELTA_TABLE = 27;


    protected Map<Integer, String> tablesNames = new HashMap<>();

    public static final int BID_ASK_COUNTER_TYPE = 1;
    public static final int INDEX_RACES_TYPE = 2;
    public static final int FUT_RACES_TYPE = 3;
    public static final int BASKETS_TYPE = 4;
    public static final int OP_AVG_TYPE = 5;
    public static final int INDEX_DELTA_TYPE = 6;
    public static final int FUT_DELTA_TYPE = 7;

    final String DATA_SCHEME = "data";
    final String SAGIV_SCHEME = "sagiv";

    protected BASE_CLIENT_OBJECT client;
    protected Exps exps;

    public IDataBaseHandler(BASE_CLIENT_OBJECT client) {
        this.client = client;
        exps = client.getExps();
    }

    public abstract void insertData(int sleep);

    public abstract void loadData();

    public abstract void initTablesNames();

    public String get_table_loc(int target_table) {
        return tablesNames.get(target_table);
    }

    public String get_table_loc(String target_table) {
        if (target_table.equals(ExpStrings.day)) {
            return tablesNames.get(FUT_DAY_TABLE);
        } else if (target_table.equals(ExpStrings.week)) {
            return tablesNames.get(FUT_WEEK_TABLE);
        } else if (target_table.equals(ExpStrings.month)) {
            return tablesNames.get(FUT_MONTH_TABLE);
        } else if (target_table.equals(ExpStrings.q1)) {
            return tablesNames.get(FUT_Q1_TABLE);
        } else if (target_table.equals(ExpStrings.q2)) {
            return tablesNames.get(FUT_Q2_TABLE);
        }
        return "No table call: " + target_table;
    }

    public void load_op_avg(Exp exp, ResultSet rs) {
        // Day
        try {
            if (rs.next()) {
                double sum = rs.getDouble(1);
                int sum_count = rs.getInt(2);
                exp.set_op_avg(sum, sum_count);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load_bid_ask_counter(ResultSet rs) {
        try {
            if (rs.next()) {
                int value = (int) rs.getDouble("value");
                client.setIndexBidAskCounter(value);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void load_fut_delta(Exp exp, ResultSet rs) {
        while (true) {
            try {
                if (!rs.next()) break;

                int delta = (int) rs.getDouble("value");
                E e = (E) exp;
                e.setDelta(delta);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }


    public void load_index_delta(ResultSet rs) {
        while (true) {
            try {
                if (!rs.next()) break;

                int value = rs.getInt(2);
                client.getStocksHandler().setDelta(value);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }


    public void load_baskets(ResultSet rs) {

        while (true) {
            try {
                if (!rs.next()) break;
                double value = rs.getDouble("value");
                if (value > 0) {
                    client.getBasketFinder().add_basket_up();
                } else if (value < 0) {
                    client.getBasketFinder().add_basket_down();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    void load_properties() {

        String q = "SELECT * FROM sagiv.props WHERE stock_id = '%s';";
        String query = String.format(q, client.getId_name());

        ResultSet rs = MySql.select(query);

        while (true) {
            try {
                if (!rs.next()) break;
                String props_name = rs.getString("prop");
                Object data = rs.getObject("data");

                client.getProps().getMap().get(props_name).setData(data);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    void insert_properties() {
        // Query
        String q = "INSERT INTO sagiv.props (stock_id, prop, data) VALUES ('%s', '%s', '%s');";

        // Get query map
        HashMap<String, Prop> map = (HashMap<String, Prop>) client.getProps().getMap();

        // For each prop
        for (Map.Entry<String, Prop> entry : map.entrySet()) {
            Prop prop = entry.getValue();
            String query = String.format(q, client.getId_name(), prop.getName(), prop.getData());
            System.out.println(query);
            MySql.insert(query);
        }
    }

    void update_properties() {
        // Query
        String q = "UPDATE sagiv.props SET data = '%s' WHERE stock_id LIKE '%s' AND prop LIKE '%s';";

        // Get query map
        HashMap<String, Prop> map = (HashMap<String, Prop>) client.getProps().getMap();

        // For each prop
        for (Map.Entry<String, Prop> entry : map.entrySet()) {
            Prop prop = entry.getValue();
            String query = String.format(q, prop.getData(), client.getId_name(), prop.getName());
            MySql.update(query);
        }
    }

    public static void loadSerieData(ResultSet rs, MyTimeSeries timeSeries) {
        while (true) {
            try {
                if (!rs.next()) break;
                Timestamp timestamp = rs.getTimestamp("time");
                double value = rs.getDouble("value");
                timeSeries.add(timestamp.toLocalDateTime(), value);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    
    public static MyTickTimeSerie load_data_to_tick_list(ResultSet rs) {
        MyTickTimeSerie list = new MyTickTimeSerie();
        while (true) {
            try {
                if (!rs.next()) break;
                Timestamp timestamp = rs.getTimestamp("time");
                double value = rs.getDouble("value");
                MyTick tick = new MyTick(timestamp.toLocalDateTime(), value);
                list.add(tick);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return list;
    }

    public static void insert_batch_data(ArrayList<MyTick> list, String table_location) {
        if (list.size() > 0) {

            // Create the query
            StringBuilder queryBuiler = new StringBuilder("INSERT INTO %s (time, value) VALUES ");
            int last_item_id = list.get(list.size() - 1).hashCode();
            for (MyTick row : list) {
                queryBuiler.append(String.format("(cast('%s' as timestamp with time zone), %s)", row.time, row.value));
                if (row.hashCode() != last_item_id) {
                    queryBuiler.append(",");
                }
            }
            queryBuiler.append(";");

            String q = String.format(queryBuiler.toString(), table_location);

            // Insert
            MySql.insert(q, true);

            // Clear the list
            list.clear();
        }
    }

    void insertListRetro(ArrayList<MyTimeStampObject> list, String table_location) {
        if (list.size() > 0) {

            // Create the query
            StringBuilder queryBuiler = new StringBuilder("INSERT INTO %s (time, value) VALUES ");
            int last_item_id = list.get(list.size() - 1).hashCode();
            for (MyTimeStampObject row : list) {
                queryBuiler.append(String.format("(cast('%s' as timestamp with time zone), %s)", row.getInstant(), row.getValue()));
                if (row.hashCode() != last_item_id) {
                    queryBuiler.append(",");
                }
            }
            queryBuiler.append(";");

            String q = String.format(queryBuiler.toString(), table_location);

            // Insert
            MySql.insert(q, true);

            // Clear the list
            list.clear();
        }
    }

    void update_props_to_db() {

        String query = String.format("SELECT * FROM data.props WHERE name = '%s';", client.getName());
        ResultSet rs = MySql.select(query);

        while (true) {
            try {
                if (!rs.next()) break;

                String prop = rs.getString("prop");


            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    protected ArrayList<LocalDateTime> load_uncalced_tick_speed_time(String fut_table_location, String tick_speed_table_location) {

        ArrayList<LocalDateTime> times = new ArrayList<>();

        String q = "select * " +
                "from %s " +
                "where time > (select time from %s order by time desc limit 1) order by time;";
        String query = String.format(q, fut_table_location, tick_speed_table_location);
        ResultSet rs = MySql.select(query);

        System.out.println(q);

        while (true) {
            try {
                if (!rs.next()) break;
                Timestamp timestamp = rs.getTimestamp("time");
                LocalDateTime dateTime = LocalDateTime.parse(timestamp.toLocalDateTime().toString());
                times.add(dateTime);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }

        return times;
    }

    private void insert_data(ArrayList<MyTick> myTicks, String speed_table_location) {
        IDataBaseHandler.insert_batch_data(myTicks, speed_table_location);
    }

    public static ArrayList<MyTick> tick_logic(ArrayList<LocalDateTime> times) {
        ArrayList<MyTick> myTicks = new ArrayList<>();

        for (int i = 1; i < times.size(); i++) {

            long pre_time = Timestamp.valueOf(times.get(i - 1)).getTime();
            long curr_time = Timestamp.valueOf(times.get(i)).getTime();
            long mill = curr_time - pre_time;

            // Add tick to tick list
            myTicks.add(new MyTick(times.get(i), mill));
        }
        return myTicks;
    }

    protected abstract void open_chart_on_start();


    public abstract void updateInterests();
}
