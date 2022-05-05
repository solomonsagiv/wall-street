package dataBase.mySql;

import arik.Arik;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;

public class MySql {

    public static void main(String[] args) throws SQLException {

        System.out.println(Queries.get_serie("data.research", 3, 117));

//        MyDBConnections dbConnections = new MyDBConnections();
//        DBConnectionType connectionType = dbConnections.getConnectionType(MyDBConnections.JIBE_POSTGRES);
//        Connection jibe_conn = (Connection) ConnectionPool.create(connectionType, 5);
//        DBConnectionType sconnectionType = dbConnections.getConnectionType(MyDBConnections.SLO_POSTGRES);
//        Connection slo_conn = (Connection) ConnectionPool.create(sconnectionType, 5);
//
//        String query = "select * from data.ndx_decision_func\n" +
//                "where session_id = 4\n" +
//                "  and version = 603\n" +
//                "  and date_trunc('day', time) = date_trunc('day', date'2022-03-28');";
//
//        ResultSet rs = MySql.select(query, jibe_conn);
//
//
//        while (true) {
//            try {
//                if (!rs.next()) break;
//                MySql.insert();
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            }
//
//        }

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

    public static void insert(String query, Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            // Execute
            stmt.execute(query);
            System.out.println(LocalTime.now() + "  " + query);
        } catch (Exception e) {
            e.printStackTrace();
            Arik.getInstance().sendMessage(e.getMessage() + "\n" + e.getCause() + " \n" + "Insert");
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
    public static void update(String query, Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            // Execute
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
            Arik.getInstance().sendMessage(e.getMessage() + "\n" + e.getCause() + " \n" + "Update");
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

    public static ResultSet select(String query, Connection conn) {
        ResultSet rs = null;

        try {

            // create the java statement
            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            rs = st.executeQuery(query);

            System.out.println(query);

        } catch (Exception e) {
            e.printStackTrace();
            Arik.getInstance().sendMessage(e.getMessage() + "\n" + e.getCause() + " \n" + "Select");
        }
        return rs;
    }


    public static void trunticate(String tableName, String schema, Connection conn) {
        String query = "TRUNCATE TABLE " + schema + "." + tableName;
        try {
            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            st.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
            Arik.getInstance().sendMessage(e.getMessage() + "\n" + e.getCause() + " \n" + "Trunticate");
        }
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
        public static final int step_second = 5;

        public static ResultSet op_query(String index_table, String fut_table) {
            String query = String.format("select sum(f.value - i.value), count(f.value - i.value) as value " +
                    "from %s i " +
                    "inner join %s f " +
                    "on i.time = f.time " +
                    "where i.time between date_trunc('day', now()) and date_trunc('day', now() + interval '1' day);", index_table, fut_table);
            return MySql.select(query);
        }


        public static void delete_today_interest_rates() {
            String query = "delete \n" +
                    "from meta.interest_rates\n" +
                    "where start_date = now()::date;";
            MySql.update(query);
        }

        public static void delete_today_interest_rates(Connection conn) {
            String query = "delete \n" +
                    "from meta.interest_rates\n" +
                    "where start_date = now()::date;";
            MySql.update(query, conn);
        }


        public static ResultSet get_exp_data(String table_location, int session, int version, String stock_id) {
            String q = "select (\n" +
                    "           select sum(delta) as value\n" +
                    "           from %s\n" +
                    "           where session_id = %s\n" +
                    "             and version = %s\n" +
                    "             and time between date_trunc('day', a.d) and date_trunc('day', now())\n" +
                    "       )\n" +
                    "from (\n" +
                    "         select data::date as d\n" +
                    "         from sagiv.props\n" +
                    "         where stock_id = '%s'\n" +
                    "           and prop = 'EXP_Q1_START'\n" +
                    "     ) a;";
            String query = String.format(q, table_location, session, version, stock_id);
            return MySql.select(query);

        }

        public static ResultSet get_exp_start(String index_table_location, String stock_id) {
            String q = "select (select value\n" +
                    "        from %s\n" +
                    "        where date_trunc('day', time) = date_trunc('day', a.date)\n" +
                    "        order by time\n" +
                    "        limit 1)\n" +
                    "\n" +
                    "from (\n" +
                    "         select data::date as date\n" +
                    "         from sagiv.props\n" +
                    "         where stock_id = '%s'\n" +
                    "           and prop = 'EXP_Q1_START') a;";
            String query = String.format(q, index_table_location, stock_id);
            return MySql.select(query);
        }

        public static ResultSet op_avg_query(String index_table, String fut_table) {
            String query = String.format("select avg(f.value - i.value) as value " +
                    "from %s i " +
                    "inner join %s f " +
                    "on i.time = f.time " +
                    "where i.time between date_trunc('day', now()) and date_trunc('day', now() + interval '1' day);", index_table, fut_table);
            return MySql.select(query);
        }

        public static ResultSet op_avg_continues_by_rows_serie(String index_table, String fut_table, int rows) {
            String modulu = "%";
            String q = "select *\n" +
                    "from (\n" +
                    "         select time,\n" +
                    "                avg(op) over (ORDER BY row RANGE BETWEEN %s PRECEDING AND CURRENT ROW) as value,\n" +
                    "                row\n" +
                    "         from (\n" +
                    "                  select i.time                              as time,\n" +
                    "                         f.value - i.value                   as op,\n" +
                    "                         row_number() over (order by i.time) as row\n" +
                    "                  from %s i\n" +
                    "                           inner join %s f on i.time = f.time\n" +
                    "                  where i.time >= (select date_trunc('day', time)\n" +
                    "                                   from %s\n" +
                    "                                   where date_trunc('day', time) < date_trunc('day', now())\n" +
                    "                                   group by date_trunc('day', time)\n" +
                    "                                   order by date_trunc('day', time) desc\n" +
                    "                                   limit 1)\n" +
                    "              ) a\n" +
                    "     ) a\n" +
                    "where time between date_trunc('day', now()) and date_trunc('day', now() + interval '1' day)\n" +
                    "  and row %s %s = 0;";

            String query = String.format(q, rows, index_table, fut_table, index_table, modulu, step_second);
            return MySql.select(query);
        }

        public static ResultSet op_avg_by_rows(String index_table, String fut_table, int rows) {
            String q = "select avg(op) as value\n" +
                    "from (\n" +
                    "         select f.value - i.value as op\n" +
                    "         from %s i\n" +
                    "                  inner join %s f on i.time = f.time\n" +
                    "         order by i.time desc\n" +
                    "         limit %s) a;";

            String query = String.format(q, index_table, fut_table, rows);
            return MySql.select(query);
        }


        public static ResultSet op_avg_cumulative(String index_table, String fut_table) {
            String query = String.format("select time, avg(f.value - i.value) over (order by i.time) as value " +
                    "from %s i " +
                    "inner join %s f " +
                    "on i.time = f.time " +
                    "where i.time between date_trunc('day', now()) and date_trunc('day', now() + interval '1' day);", index_table, fut_table);
            return MySql.select(query);
        }

//        public static ResultSet get_df() {
//
//        }
//
//        public static ResultSet get_last_record_df() {
//
//        }


        public static ResultSet get_last_x_time_of_series(String table_name, int minuts) {
            String q = "select * from %s where time > now() - interval '%s min' %s;";
            String query = String.format(q, table_name, minuts, Filters.ORDER_BY_TIME);
            return MySql.select(query);
        }

        public static ResultSet get_last_x_time_of_series_cumulative(String table_name, int minuts) {
            String q = "select time, sum(value) over (ORDER BY time RANGE BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) as value " +
                    "from %s " +
                    "where time between date_trunc('day', now()) and date_trunc('day', now() + interval '1' day) and time > now() - interval '%s min';";
            String query = String.format(q, table_name, minuts, Filters.ORDER_BY_TIME);
            return MySql.select(query);
        }

        public static ResultSet get_last_x_time_from_dec_func_cumulative(String table_location, int min, int session_id, int version) {
            String q = "select time, sum(delta) over (ORDER BY time RANGE BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) as value " +
                    "from %s " +
                    "where time between date_trunc('day', now()) and date_trunc('day', now() + interval '1' day) and time > now() - interval '%s min'  and session_id = %s and version = %s;";
            String query = String.format(q, table_location, min, session_id, version);
            return MySql.select(query);
        }

        public static ResultSet get_df_serie(String table_location, int session_id, int version) {
            String q = "select time,\n" +
                    "       sum(delta) over (ORDER BY time RANGE BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) as value\n" +
                    "from %s\n" +
                    "where time between date_trunc('day', now()) and date_trunc('day', now() + interval '1' day)\n" +
                    "  and session_id = %s\n" +
                    "  and version = %s;";
            String query = String.format(q, table_location, session_id, version);
            return MySql.select(query);
        }

        public static ResultSet get_df_bar_serie(String table_location, int session_id, int version) {
            String q = "select *\n" +
                    "from %s\n" +
                    "where time between date_trunc('day', now()) and date_trunc('day', now() + interval '1' day)\n" +
                    "and session_id = %s\n" +
                    "and version = %s;";
            String query = String.format(q, table_location, session_id, version);
            return MySql.select(query);
        }


        public static ResultSet get_research_serie(String table_location, int session_id, int version) {
            String q = "select time,\n" +
                    "       sum(delta) over (ORDER BY time RANGE BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) as value\n" +
                    "from %s\n" +
                    "where time between date_trunc('day', now()) and date_trunc('day', now() + interval '1' day)\n" +
                    "  and session_id = %s\n" +
                    "  and version = %s;";
            String query = String.format(q, table_location, session_id, version);
            return MySql.select(query);
        }


        public static ResultSet get_sum_from_df(String table_location, int version, int session_id) {
            String q = "select sum(delta) as value " +
                    "from %s " +
                    "where time between date_trunc('day', now()) and date_trunc('day', now() + interval '1' day) and session_id = %s and version = %s;";
            String query = String.format(q, table_location, session_id, version);
            System.out.println(query);
            return MySql.select(query);
        }

        public static ResultSet op_avg_cumulative_query(String index_table, String fut_table, int min) {
            String modulu = "%";
            String q = "select * " +
                    "from ( " +
                    "select i.time, " +
                    "avg(f.value - i.value) " +
                    "over (ORDER BY i.time RANGE BETWEEN INTERVAL '%s min' PRECEDING AND CURRENT ROW) as value, " +
                    "row_number() over (order by i.time) as row " +
                    "from %s i " +
                    "inner join %s f on i.time = f.time " +
                    "where i.time between date_trunc('day', now()) and date_trunc('day', now() + interval '1' day)) a " +
                    "where row % 5 = 0;";
            String query = String.format(q, min, index_table, fut_table, modulu, step_second);
            return MySql.select(query);
        }


        public static ResultSet ta35_op_avg_with_bid_ask(String table_to_insert, String fut_table, int min, int step_seconds) {
            String modulu = "%";
            String q = "insert into %s" +
                    "select * " +
                    "from ( " +
                    "select i.time, " +
                    "avg(f.futures - ((i.bid + i.ask) / 2)) " +
                    "over (ORDER BY i.time RANGE BETWEEN INTERVAL '%s min' PRECEDING AND CURRENT ROW) as value, " +
                    "row_number() over (order by i.time)                                              as row " +
                    "from data.ta35_index i " +
                    "inner join %s f on i.time = f.time " +
                    "where i.time between date_trunc('day', now()) and date_trunc('day', now() + interval '1' day)) a " +
                    "where row %s %s = 0;";
            String query = String.format(q, table_to_insert, min, fut_table, modulu, step_seconds);
            return MySql.select(query);
        }


        public static ResultSet get_serie_cumulative_sum(String table_location, int step_second) {
            String modulu = "%";
            String q = "select * " +
                    "from ( " +
                    "select time, sum(value) over (ORDER BY time RANGE BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) as value, row_number() over ( ORDER BY time ) as row " +
                    "from %s " +
                    "where time between date_trunc('day', now()) and date_trunc('day', now() + interval '1' day)) a " +
                    "where row %s %s = 0;";
            String query = String.format(q, table_location, modulu, step_second);
            return MySql.select(query);
        }

        public static ResultSet get_serie(String table_location, int step_second) {
            String modulu = "%";
            String q = "select * " +
                    "from ( " +
                    "SELECT *, row_number() over (order by time) as row " +
                    "FROM %s " +
                    "where time between date_trunc('day', now()) and date_trunc('day', now() + interval '1' day) " +
                    ") a " +
                    "where row %s %s = 0;";
            String query = String.format(q, table_location, modulu, step_second);
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

        public static ResultSet cumulative_sum_query_step_sec(String table_loc) {
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
            // JIBE
            MySql.insert(query);


            // SLO
            try {
                Connection slo_conn = ConnectionPool.get_slo_single_connection();
                MySql.insert(query, slo_conn);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }

        public static ResultSet select_2_tables_on_same_time_no_millis(String index_table, String decsion_table, LocalDate date) {
            String q = "select * " +
                    "from %s df " +
                    "inner join %s i " +
                    "on date_trunc('second', df.time::timestamp) = date_trunc('second', i.time::timestamp) " +
                    "where df.time::date = date'%s';";
            String query = String.format(q, decsion_table, index_table, date);

            return MySql.select(query);
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

        public static ResultSet get_last_record(String table_location, int version, int session_id) {
            String q = "select value from\n" +
                    "%s\n" +
                    "where version = %s and session_id = %s\n" +
                    "and %s\n" +
                    "order by time desc limit 1;";

            String query = String.format(q, table_location, version, session_id, Filters.TODAY);
            return MySql.select(query);
        }

        public static ResultSet get_serie(String table_location, int session_id, int version) {
            String q = "select time, value from\n" +
                    "%s\n" +
                    "where version = %s and session_id = %s\n" +
                    "and %s;";

            String query = String.format(q, table_location, version, session_id, Filters.TODAY);
            System.out.println(query);
            return MySql.select(query);
        }
    }

    public static class Filters {

        public static final String ONE_OR_MINUS_ONE = "(value = 1 or value = -1)";
        public static final String BIGGER_OR_SMALLER_10K = "(value < 10000 or value > -10000)";
        public static final String TODAY = "time between date_trunc('day', now()) and date_trunc('day', now() + interval '1' day)";
        public static final String ORDER_BY_TIME = "order by time";
        public static final String ORDER_BY_TIME_DESC = "order by time desc";
        public static final String ORDER_BY_TIME_DESC_LIMIT_1 = "order by time desc limit 1";

    }
}