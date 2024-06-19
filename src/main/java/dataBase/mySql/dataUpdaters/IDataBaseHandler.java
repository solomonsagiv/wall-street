package dataBase.mySql.dataUpdaters;

import charts.timeSeries.MyTimeSeries;
import dataBase.MyTick;
import dataBase.mySql.MySql;
import dataBase.props.Prop;
import exp.Exp;
import exp.Exps;
import locals.L;
import races.Race_Logic;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Dax;
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


    protected void insert_dev_prod(ArrayList<MyTimeStampObject> list, int dev_id, int prod_id) {
        System.out.println("------------------------ Insert start ----------------------------");
        if (dev_id != 0) {
            insertListRetro(list, dev_id, MySql.JIBE_DEV_CONNECTION);
        }
        if (prod_id != 0) {
            insertListRetro(list, prod_id, MySql.JIBE_PROD_CONNECTION);
        }
        System.out.println("------------------------ Insert End ----------------------------");
        list.clear();
    }

    protected void load_exp_data() {
        // Load exp data for each timeserie
        for (Map.Entry<String, MyTimeSeries> entry : client.getTimeSeriesHandler().getSeries_map().entrySet()) {
            try {

                entry.getValue().load_exp_data();
                String name = entry.getKey();

                if (name.equals("PRE_DAY_OP_AVG") && client instanceof Dax) {
                    double val = entry.getValue().get_value_with_exp();
                    System.out.println(val);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void load_properties() {

        String q = "SELECT * FROM sagiv.props WHERE stock_id = '%s';";
        String query = String.format(q, client.getId_name());

        ResultSet rs = MySql.select(query, MySql.JIBE_PROD_CONNECTION);

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
//                e.printStackTrace();
//                System.out.println(data.toString() + " Prop name= " + props_name);
            }
        }
    }

//    void insert_properties() {
//        // Query
//        String q = "INSERT INTO sagiv.props (stock_id, prop, data) VALUES ('%s', '%s', '%s');";
//
//        // Get query map
//        HashMap<String, Prop> map = (HashMap<String, Prop>) client.getProps().getMap();
//
//        // For each prop
//        for (Map.Entry<String, Prop> entry : map.entrySet()) {
//            Prop prop = entry.getValue();
//            String query = String.format(q, client.getId_name(), prop.getName(), prop.getData());
//            System.out.println(query);
//            MySql.insert(query);
//        }
//    }

    void update_properties() {
        // Query
        String q = "UPDATE sagiv.props SET data = '%s' WHERE stock_id LIKE '%s' AND prop LIKE '%s';";

        // Get query map
        HashMap<String, Prop> map = (HashMap<String, Prop>) client.getProps().getMap();

        // For each prop
        for (Map.Entry<String, Prop> entry : map.entrySet()) {
            Prop prop = entry.getValue();
            String query = String.format(q, prop.getData(), client.getId_name(), prop.getName());
            MySql.update(query, MySql.JIBE_PROD_CONNECTION);
        }
    }

    public static void loadSerieData(ResultSet rs, MyTimeSeries timeSeries) {
        while (true) {
            try {
                if (!rs.next()) {
                    timeSeries.setLoad(true);
                    break;
                }
                Timestamp timestamp = rs.getTimestamp("time");
                double value = rs.getDouble("value");
                System.out.println(value + "  " + timeSeries.getName());
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
            MySql.Queries.insert_rates(client.getId_name(), exp.getInterest(), exp.getDividend(), exp.getDays_to_exp(), client.getBase(), exp.getCof(), exp.getName(), exp.getNormalized_num(), MySql.JIBE_PROD_CONNECTION);
        }
    }

    public static void insert_batch_data(ArrayList<MyTick> list, String table_location, String connection_type) {
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
            MySql.insert(q, true, connection_type);

            // Clear the list
            list.clear();
        }
    }


    public String convert_list_to_query(ArrayList<MyTimeStampObject> list, int timeseries_id) {
        // Create the query
        StringBuilder queryBuiler = new StringBuilder("INSERT INTO %s (time, value, timeseries_id) VALUES ");
        int last_item_id = list.get(list.size() - 1).hashCode();
        for (MyTimeStampObject row : list) {
            queryBuiler.append(String.format("(cast('%s' as timestamp with time zone), %s, %s)", row.getInstant(), row.getValue(), timeseries_id));
            if (row.hashCode() != last_item_id) {
                queryBuiler.append(",");
            }
        }
        queryBuiler.append(";");

        String q = String.format(queryBuiler.toString(), "ts.timeseries_data");
        return q;
    }

    void insertListRetro(ArrayList<MyTimeStampObject> list, int timseries_id, String connection_type) {
        if (list.size() > 0) {
            // Insert
            MySql.insert(convert_list_to_query(list, timseries_id), connection_type);
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

    protected void load_races(Race_Logic.RACE_RUNNER_ENUM race_runner_enum, int serie_id, boolean r_one_or_two) {
        System.out.println(client);
        load_race_points(race_runner_enum, serie_id, true, r_one_or_two);
        load_race_points(race_runner_enum, serie_id, false, r_one_or_two);
    }

    private void load_race_points(Race_Logic.RACE_RUNNER_ENUM race_runner_enum, int serie_id, boolean up_down, boolean r_one_or_two) {
        ResultSet rs;
        if (up_down) {
            rs = MySql.Queries.get_races_up_sum(serie_id, MySql.JIBE_PROD_CONNECTION);
        } else {
            rs = MySql.Queries.get_races_down_sum(serie_id, MySql.JIBE_PROD_CONNECTION);
        }

        while (true) {
            try {
                if (!rs.next()) break;
                double value = rs.getDouble("value");

                if (up_down) {
                    if (r_one_or_two) {
                        client.getRacesService().get_race_logic(race_runner_enum).setR_one_up_points(value);
                    } else {
                        client.getRacesService().get_race_logic(race_runner_enum).setR_two_up_points(value);
                    }
                } else {
                    if (r_one_or_two) {
                        client.getRacesService().get_race_logic(race_runner_enum).setR_one_down_points(L.abs(value));
                    } else {
                        client.getRacesService().get_race_logic(race_runner_enum).setR_two_down_points(L.abs(value));
                    }

                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    private void insert_data(ArrayList<MyTick> myTicks, String speed_table_location, String connection_type) {
        IDataBaseHandler.insert_batch_data(myTicks, speed_table_location, connection_type);
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

    public Map<Integer, Integer> getSerie_ids() {
        return serie_ids;
    }
}
