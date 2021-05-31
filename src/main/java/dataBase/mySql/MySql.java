package dataBase.mySql;

import arik.Arik;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalTime;

public class MySql {

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

        public static ResultSet op_avg_cumulative_query(String index_table, String fut_table) {
            String query = String.format("select time, avg(f.value - i.value) over (order by i.time) as cumu " +
                    "from %s i " +
                    "         inner join %s f " +
                    "                    on i.time = f.time " +
                    "where i.time::date = now()::date;", index_table, fut_table);
            return MySql.select(query);
        }

        public static ResultSet op_avg_cumulative_query(String index_table, String fut_table, int min) {
            String query = String.format("select i.time, f.value - i.value,avg(f.value - i.value) over (order by i.time range between '%s min' preceding and current row ) as cumu " +
                    "        from %s i " +
                    "        inner join %s f on i.time = f.time " +
                    "        where i.time::date = now()::date;", min, index_table, fut_table);
            return MySql.select(query);
        }

        public static ResultSet cumulative_query(String table_loc, String cumulative_type) {
            String query = String.format("select * ,%s(value) over (order by time) as cumu " +
                    "from %s " +
                    "where time::date = now()::date and (value = 1 or value = -1);", cumulative_type, table_loc);
            return MySql.select(query);
        }

        public static ResultSet get_serie(String table_loc) {
            String query = String.format("SELECT * FROM %s WHERE time::date = now()::date order by time;", table_loc);
            return MySql.select(query);
        }

        public static ResultSet update_rates_query(String id_name, String exp_name, double interest, double dividend, double day_to_exp, double base) {
            String query = String.format("select data.update_spx500_interest_rates(now()::date, '%s', '%s',%s, %s,%s , %s);",
                    id_name, exp_name, interest, dividend, day_to_exp, base);
            return MySql.select(query);
        }


    }

}