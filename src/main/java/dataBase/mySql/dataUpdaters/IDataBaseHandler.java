package dataBase.mySql.dataUpdaters;

import charts.timeSeries.MyTimeSeries;
import dataBase.mySql.MySql;
import exp.Exp;
import exp.ExpStrings;
import serverObjects.BASE_CLIENT_OBJECT;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class IDataBaseHandler {

    // Props
    protected Map<String, String> propsMap = new HashMap<>();

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

    protected Map<Integer, String> tablesNames = new HashMap<>();

    public static final int BID_ASK_COUNTER_TYPE = 1;
    public static final int INDEX_RACES_TYPE = 2;
    public static final int FUT_RACES_TYPE = 3;
    public static final int BASKETS_TYPE = 4;
    public static final int OP_AVG_TYPE = 5;

    final String DATA_SCHEME = "data";
    final String SAGIV_SCHEME = "sagiv";

    BASE_CLIENT_OBJECT client;

    public IDataBaseHandler(BASE_CLIENT_OBJECT client) {
        this.client = client;
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


    protected void load_data_agg(ResultSet rs, BASE_CLIENT_OBJECT client, Exp exp, int type) {

        // BASKETS
        if (type == BASKETS_TYPE) {
            while (true) {
                try {
                    if (!rs.next()) break;

                    int value = rs.getInt("value");

                    if (value > 0) {
                        client.getBasketFinder().add_basket_up();
                    }

                    if (value < 0) {
                        client.getBasketFinder().add_basket_down();
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

        // BID ASK COUNTER
        if (type == BID_ASK_COUNTER_TYPE) {
            try {
                if (rs.next()) {
                    int value = rs.getInt(2);
                    client.setIndexBidAskCounter(value);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        // INDEX RACES
        if (type == INDEX_RACES_TYPE) {
            while (true) {
                try {
                    if (!rs.next()) break;

                    int value = rs.getInt("value");

                    if (value > 0) {
                        client.setIndexUp(client.getIndexUp() + 1);
                    }

                    if (value < 0) {
                        client.setIndexDown(client.getIndexDown() + 1);
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

        // FUT RACES
        if (type == FUT_RACES_TYPE) {
            while (true) {
                try {
                    if (!rs.next()) break;

                    int value = rs.getInt("value");

                    if (value == 1) {
                        client.setConUp(client.getConUp() + 1);
                    }

                    if (value == -1) {
                        client.setConDown(client.getConDown() + 1);
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

        // OP AVG
        if (type == OP_AVG_TYPE) {
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
    }

    public static void loadSerieData(ResultSet rs, MyTimeSeries timeSeries, String value_col_name) {
        while (true) {
            try {
                if (!rs.next()) break;
                Timestamp timestamp = rs.getTimestamp(1);
                double value = rs.getDouble(value_col_name);
                timeSeries.add(timestamp.toLocalDateTime(), value);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }


    protected void insertListRetro(ArrayList<MyTimeStampObject> list, String scheme, String tableName) {
        if (list.size() > 0) {

            // Create the query
            StringBuilder queryBuiler = new StringBuilder("INSERT INTO %s.%s (time, value) VALUES ");
            int last_item_id = list.get(list.size() - 1).hashCode();
            for (MyTimeStampObject row : list) {
                queryBuiler.append(String.format("(cast('%s' as timestamp with time zone), %s)", row.getInstant(), row.getValue()));
                if (row.hashCode() != last_item_id) {
                    queryBuiler.append(",");
                }
            }
            queryBuiler.append(";");

            String q = String.format(queryBuiler.toString(), scheme, tableName);

            // Insert
            MySql.insert(q, true);

            // Clear the list
            list.clear();
        }

    }

    protected void update_props_to_db() {

        String query = String.format("SELECT * FROM data.props WHERE name = '%s';", client.getName());
        ResultSet rs = MySql.select(query);

        while (true) {
            try {
                if (!rs.next()) break;

                String prop = rs.getString("prop");
                String data = rs.getString("data");

//                if (  ) {
//
//                }



            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

    protected abstract void open_chart_on_start();


}
