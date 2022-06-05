package dataBase.mySql.dataUpdaters;

import charts.timeSeries.MyTimeSeries;
import charts.timeSeries.TimeSeriesHandler;
import dataBase.MyTick;
import dataBase.mySql.MySql;
import dataBase.props.Prop;
import exp.E;
import exp.Exp;
import exp.ExpStrings;
import exp.Exps;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class IDataBaseHandler {

    protected Map<Integer, Integer> serie_ids = new HashMap<>();

    protected BASE_CLIENT_OBJECT client;
    protected Exps exps;

    public IDataBaseHandler(BASE_CLIENT_OBJECT client) {
        this.client = client;
        exps = client.getExps();
    }

    public abstract void insertData(int sleep);

    public abstract void loadData();

    public abstract void initTablesNames();


    protected void load_exp_data() {
        try {
            // START
            int index_table = serie_ids.get(TimeSeriesHandler.INDEX_TABLE);
            double start_exp = MySql.Queries.handle_rs(MySql.Queries.get_start_exp_mega(index_table, client.getId_name()));
            client.getExps().getExp(ExpStrings.q1).setStart(start_exp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load_op_avg(Exp exp, ResultSet rs) {
        // Day
        try {
            if (rs.next()) {
                double value = rs.getDouble("value");
                exp.setOp_avg(value);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
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

    void load_properties() {

        String q = "SELECT * FROM sagiv.props WHERE stock_id = '%s';";
        String query = String.format(q, client.getId_name());

        ResultSet rs = MySql.select(query);

        while (true) {
            String props_name = "";
            Object data = null;

            try {
                if (!rs.next()) break;
                props_name = rs.getString("prop");
                data = rs.getObject("data");

                System.out.println(props_name + "  " + data);

                client.getProps().getMap().get(props_name).setData(data);

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(data.toString());
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

    public static void main(String[] args) {
        Spx.getInstance();
        IDataBaseHandler.insert_interes_rates(Spx.getInstance());
    }

    public static void insert_interes_rates(BASE_CLIENT_OBJECT client) {
        for (Exp exp : client.getExps().getExpList()) {
            MySql.Queries.insert_rates(client.getId_name(), exp.getInterest(), exp.getDividend(), exp.getDays_to_exp(), client.getBase(), exp.getName());
        }
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

    void insertListRetro(ArrayList<MyTimeStampObject> list, int timeseries_id) {
        if (list.size() > 0) {

            // Create the query
            StringBuilder queryBuiler = new StringBuilder("INSERT INTO %s (time, value, timeseries_id) VALUES ");
            int last_item_id = list.get(list.size() - 1).hashCode();
            for (MyTimeStampObject row : list) {
                queryBuiler.append(String.format("(cast('%s' as timestamp with time zone), %s)", row.getInstant(), row.getValue(), timeseries_id));
                if (row.hashCode() != last_item_id) {
                    queryBuiler.append(",");
                }
            }
            queryBuiler.append(";");

            String q = String.format(queryBuiler.toString(), "ts.timeseries_data", timeseries_id);

            // Insert
            MySql.insert(q);

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

    public static double handle_rs(ResultSet rs) {
        while (true) {
            try {
                if (!rs.next()) break;
                return rs.getDouble("value");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return 0;
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

    public Map<Integer, Integer> getSerie_ids() {
        return serie_ids;
    }
}
