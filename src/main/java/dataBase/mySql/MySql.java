package dataBase.mySql;

import arik.Arik;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;

public class MySql {

    public static void main(String[] args) {
        MySql.Queries.cumulative_avg_from_cdf("data.spx500_index_bid_ask_counter_cdf", 15);
    }

    private static ConnectionPool pool;

    // Insert
    public static void insert(String query) {
        Connection conn = null;
        try {

            conn = getPool().getConnection();
            Statement stmt = conn.createStatement();

            // Execute
            stmt.execute(query);

            System.out.println(LocalTime.now() + "  " + query);

        } catch (Exception e) {
            e.printStackTrace();
            Arik.getInstance().sendMessage(e.getMessage() + "\n" + e.getCause() + " \n" + "Insert");
        } finally {
            // Release connection
            if (conn != null) {
                getPool().releaseConnection(conn);
            }
        }
    }

    public static void insert(String query, boolean thread) {
        if (thread) {
            new Thread(() -> {
                insert(query);
            }).start();
        } else {
            insert(query);
        }
    }

    // Update
    public static void update(String query) {
        Connection conn = null;
        try {
            conn = getPool().getConnection();
            Statement stmt = conn.createStatement();

            // Execute
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
            Arik.getInstance().sendMessage(e.getMessage() + "\n" + e.getCause() + " \n" + "Update");
        } finally {
            // Release connection
            if (conn != null) {
                getPool().releaseConnection(conn);
            }
        }
    }

    // Update
    public static ResultSet select(String query) {
        ResultSet rs = null;
        Connection conn = null;

        try {
            conn = getPool().getConnection();

            // create the java statement
            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            rs = st.executeQuery(query);

            System.out.println(query);

        } catch (Exception e) {
            e.printStackTrace();
            Arik.getInstance().sendMessage(e.getMessage() + "\n" + e.getCause() + " \n" + "Select");
        } finally {
            // Release connection
            if (conn != null) {
                getPool().releaseConnection(conn);
            }
        }
        return rs;
    }

    public static void trunticate(String tableName, String schema) {

        String query = "TRUNCATE TABLE " + schema + "." + tableName;
        Connection conn = null;
        try {

            conn = getPool().getConnection();
            // create the java statement
            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            st.executeUpdate(query);

        } catch (Exception e) {
            e.printStackTrace();
            Arik.getInstance().sendMessage(e.getMessage() + "\n" + e.getCause() + " \n" + "Trunticate");
        } finally {
            // Release connection
            if (conn != null) {
                getPool().releaseConnection(conn);
            }
        }
    }

    // Get connection pool
    public static ConnectionPool getPool() {
        if (pool == null) {
            pool = ConnectionPool.getConnectionsPoolInstance();
        }
        return pool;
    }

    public static class Queries {

        public static ResultSet op_query(String index_table, String fut_table) {
            String query = String.format("select sum(f.value - i.value), count(f.value - i.value) as value " +
                    "from %s i " +
                    "inner join %s f " +
                    "on i.time = f.time " +
                    "where i.time::date = now()::date;", index_table, fut_table);
            return MySql.select(query);
        }

        public static ResultSet op_avg_query(String index_table, String fut_table) {
            String query = String.format("select avg(f.value - i.value) as value " +
                    "from %s i " +
                    "inner join %s f " +
                    "on i.time = f.time " +
                    "where i.time::date = now()::date;", index_table, fut_table);
            return MySql.select(query);
        }

        public static ResultSet op_avg_cumulative_query(String index_table, String fut_table) {
            String query = String.format("select time, avg(f.value - i.value) over (order by i.time) as value " +
                    "from %s i " +
                    "inner join %s f " +
                    "on i.time = f.time " +
                    "where i.time::date = now()::date;", index_table, fut_table);
            return MySql.select(query);
        }

        public static ResultSet get_last_x_time_of_series(String table_name, int minuts) {
            String q = "select * from %s where time > now() - interval '%s min' %s;";
            String query = String.format(q, table_name, minuts, Filters.ORDER_BY_TIME);
            return MySql.select(query);
        }

        public static ResultSet get_last_x_time_of_series_cumulative(String table_name, int minuts) {
            String q = "select time, sum(value) over (ORDER BY time RANGE BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) as value " +
                    "from %s " +
                    "where time::date = now()::date and time > now() - interval '%s min';";
            String query = String.format(q, table_name, minuts, Filters.ORDER_BY_TIME);
            return MySql.select(query);
        }

        public static ResultSet get_last_x_time_from_dec_func_cumulative(String table_location, int min, int session_id, int version) {
            String q = "select time, sum(delta) over (ORDER BY time RANGE BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) as value " +
                    "from %s " +
                    "where time::date = now()::date and time > now() - interval '%s min'  and session_id = %s and version = %s;";
            String query = String.format(q, table_location, min, session_id, version);
            return MySql.select(query);
        }

        public static ResultSet op_avg_cumulative_query(String index_table, String fut_table, int min) {
            String query = String.format("select i.time as time, avg(f.value - i.value) over (order by i.time range between '%s min' preceding and current row ) as value " +
                    "from %s i " +
                    "inner join %s f on i.time = f.time " +
                    "where i.time::date = now()::date;", min, index_table, fut_table);
            return MySql.select(query);
        }

        public static ResultSet cumulative_avg_query(String table_loc) {
            String q = "select time, avg(value) over (order by time) as value from %s " +
                    " where %s;";
            String query = String.format(q, table_loc, Filters.TODAY);
            return MySql.select(query);
        }

        public static ResultSet cumulative_sum_query(String table_loc) {
            String query = String.format("select time, sum(value) over (order by time) as value from %s " +
                    "where %s;", table_loc, Filters.TODAY);
            return MySql.select(query);
        }

        public static ResultSet get_serie(String table_loc) {
            String q = "select * from %s where %s %s;";
            String query = String.format(q, table_loc, Filters.TODAY, Filters.ORDER_BY_TIME);
            return MySql.select(query);
        }

        public static ResultSet get_serie(String table_loc, LocalDate date) {
            String q = "select * from %s where %s %s;";
            String query = String.format(q, table_loc, date, Filters.ORDER_BY_TIME);
            return MySql.select(query);
        }

        public static ResultSet get_last_record(String table_location) {
            String q = "select * from %s %s";
            String query = String.format(q, table_location, Filters.ORDER_BY_TIME_DESC_LIMIT_1);
            return MySql.select(query);
        }

        public static ResultSet update_rates_query(String id_name, String exp_name, double interest, double dividend, double day_to_exp, double base) {
            String query = String.format("select data.update_spx500_interest_rates(now()::date, '%s', '%s',%s, %s,%s , %s);",
                    id_name, exp_name, interest, dividend, day_to_exp, base);
            return MySql.select(query);
        }

        public static ResultSet get_sum(String table_location) {
            String q = "select sum(value) as value from %s where %s;";
            String query = String.format(q, table_location, Filters.TODAY);
            return MySql.select(query);
        }

        public static ResultSet cumulative_avg_from_cdf(String counter_table_location, int min) {
            String q = "select sum.time as time, avg(sum.sum) over (ORDER BY time RANGE BETWEEN INTERVAL '%s min' PRECEDING AND CURRENT ROW) as value " +
                    "from (" +
                    "select time, sum(t.value) over (ORDER BY time RANGE BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) " +
                    "from %s t " +
                    "where %s) sum;";
            String query = String.format(q, min, counter_table_location, Filters.TODAY);
            return MySql.select(query);
        }

        public static void insert_rates(String id_name, double interest, double dividend, double days_to_exp, double base, String exp_name) {
            String q = "INSERT INTO meta.interest_rates (stock_id, rate, dividend, days_to_expired, base, start_date, end_date, item)" +
                    " VALUES ('%s', %s, %s, %s, %s, %s, %s, '%s')";
            String today_date = "now()::date";

            String query = String.format(q, id_name, interest, dividend, days_to_exp, base, today_date, today_date, exp_name);

            System.out.println(query);
            MySql.insert(query);
        }
    }


    public static class Filters {

        public static final String ONE_OR_MINUS_ONE = "(value = 1 or value = -1)";
        public static final String BIGGER_OR_SMALLER_10K = "(value < 10000 or value > -10000)";
        public static final String TODAY = "time::date = now()::date";
        public static final String ORDER_BY_TIME = "order by time";
        public static final String ORDER_BY_TIME_DESC = "order by time desc";
        public static final String ORDER_BY_TIME_DESC_LIMIT_1 = "order by time desc limit 1";

    }
}