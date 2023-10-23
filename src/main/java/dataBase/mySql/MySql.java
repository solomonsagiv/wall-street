package dataBase.mySql;

import arik.Arik;
import serverObjects.BASE_CLIENT_OBJECT;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;

public class MySql {

    public static final String RAW = "RAW";
    public static final String AVG_TODAY = "AVG_TODAY";
    public static final String CDF = "CDF";
    public static final String BY_ROWS = "BY_ROWS";
    public static final String BY_TIME = "BY_TIME";
    public static final String FROM_TODAY = "FROM_TODAY";

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
            System.out.println(query);
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
        public static final int step_second = 10;

        public static ResultSet op_query(String index_table, String fut_table) {
            String query = String.format("select sum(f.value - i.value), count(f.value - i.value) as value " +
                    "from %s i " +
                    "inner join %s f " +
                    "on i.time = f.time " +
                    "where i.time between date_trunc('day', now()) and date_trunc('day', now() + interval '1' day);", index_table, fut_table);
            return MySql.select(query);
        }


        public static void delete_today_rates() {
            String query = "delete \n" +
                    "from meta.interest_rates\n" +
                    "where start_date = now()::date;";
            MySql.update(query);
        }

        public static void delete_today_rates(Connection conn) {
            String query = "delete \n" +
                    "from meta.interest_rates\n" +
                    "where start_date = now()::date;";
            MySql.update(query, conn);
        }

        public static ResultSet delete_today_rates(String stock_id) {
            String q = "delete \n" +
                    "from meta.interest_rates\n" +
                    "where start_date = now()::date\n" +
                    "and stock_id = '%s';";
            String query = String.format(q, stock_id);
            return MySql.select(query);
        }

        public static ResultSet delete_today_rates(String stock_id, Connection conn) {
            String q = "delete \n" +
                    "from meta.interest_rates\n" +
                    "where start_date = now()::date\n" +
                    "and stock_id = '%s';";
            String query = String.format(q, stock_id);
            return MySql.select(query, conn);
        }

        public static String load_stocks_excel_file_location() {
            String query = "select data\n" +
                    "from sagiv.props\n" +
                    "where stock_id = 'stocks'\n" +
                    "and prop = 'SAPI_EXCEL_FILE_LOCATION';";

            String res = "";

            ResultSet rs = MySql.select(query);
            while (true) {
                try {
                    if (!rs.next()) break;
                    res = rs.getString("data");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            return res;
        }


        public static void update_stock_rates(String stock_id, double interest, double div, int days_left, double base, String exp_type) throws SQLException {
            String q = "INSERT INTO meta.interest_rates (stock_id, rate, dividend, days_to_expired, base, start_date, end_date, item) VALUES " +
                    "('%s', %s, %s, %s, %s, '%s', '%s', '%s')";
            String query = String.format(q, stock_id, interest, div, days_left, base, LocalDate.now(), LocalDate.now(), exp_type);

            System.out.println(query);

            MySql.insert(query);

            Connection slo_conn = ConnectionPool.get_slo_single_connection();
            MySql.insert(query, slo_conn);

        }

        public static ResultSet get_exp_data(BASE_CLIENT_OBJECT client, int serie_id, String exp_prop_name) {

            String q = "";

            if (exp_prop_name.toLowerCase().contains("week")) {

                q = "select sum(sum) as value\n" +
                        "from ts.ca_timeseries_1day_candle\n" +
                        "where date_trunc('day', time) > (select data::date as date\n" +
                        "                                  from props\n" +
                        "                                  where stock_id = '%s'\n" +
                        "                                    and prop = '%s')\n" +
                        " and date_trunc('day', time) < date_trunc('day', now())\n" +
                        "  and timeseries_id = %s;";

            } else {
                q = "select sum(sum) as value\n" +
                        "from ts.ca_timeseries_1day_candle\n" +
                        "where date_trunc('day', time) >= (select data::date as date\n" +
                        "                                  from props\n" +
                        "                                  where stock_id = '%s'\n" +
                        "                                    and prop = '%s')\n" +
                        " and date_trunc('day', time) < date_trunc('day', now())\n" +
                        "  and timeseries_id = %s;";
            }


            String query = String.format(q, client.getId_name(), exp_prop_name, serie_id);
            return MySql.select(query);
        }

        public static ResultSet get_df_cdf_by_frame(int serie_id, int frame_in_secondes) {
            String q = "\n" +
                    "select sum(a.value) as value\n" +
                    "from (\n" +
                    "select *\n" +
                    "from ts.timeseries_data\n" +
                    "where timeseries_id = %s\n" +
                    "order by time desc limit %s) a;";

            String query = String.format(q, serie_id, frame_in_secondes);
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

        public static ResultSet get_start_exp_mega(int index_id, String stock_id, String exp_prop_name) {

            String q = "";

            if (exp_prop_name.toLowerCase().contains("week")) {
                q = "select value\n" +
                        "from ts.timeseries_data\n" +
                        "where timeseries_id = %s\n" +
                        "  and date_trunc('day', time) = (select data::date\n" +
                        "                                 from sagiv.props\n" +
                        "                                 where stock_id = '%s'\n" +
                        "                                   and prop = '%s')\n" +
                        "order by time desc limit 1;\n";
            } else {

                q = "select value\n" +
                        "from ts.timeseries_data\n" +
                        "where timeseries_id = %s\n" +
                        "  and date_trunc('day', time) = (select data::date\n" +
                        "                                 from sagiv.props\n" +
                        "                                 where stock_id = '%s'\n" +
                        "                                   and prop = '%s')\n" +
                        "order by time limit 1;\n";
            }

            String query = String.format(q, index_id, stock_id, exp_prop_name);
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


        public static ResultSet get_accounts_data() {
            String query = "select * from sagiv.arik_accounts;";
            return MySql.select(query);
        }

        public static ResultSet op_avg_mega_table(int index_id, int fut_id) {
            String q = "select avg(f.value - i.value) as value\n" +
                    "from (\n" +
                    "         select *\n" +
                    "         from %s\n" +
                    "         where timeseries_id = %s\n" +
                    "     ) i\n" +
                    "         inner join (select * from %s where timeseries_id = %s) f on i.time = f.time\n" +
                    "where i.%s;";

            String query = String.format(q, "ts.timeseries_data", index_id, "ts.timeseries_data", fut_id, Filters.TODAY);
            System.out.println(query);
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

        public static ResultSet get_df_serie(String table_location, int session_id, int version) {
            String val = table_location.contains("func") ? "delta" : "value";

            String modulu = "%";
            String q = "select * from (\n" +
                    "select time,\n" +
                    "       sum(%s) over (ORDER BY time RANGE BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) as value, row_number() over (order by time) as row\n" +
                    "from %s\n" +
                    "where time between date_trunc('day', now()) and date_trunc('day', now() + interval '1' day)\n" +
                    "  and session_id = %s\n" +
                    "  and version = %s) a\n" +
                    "where row %s %s = 0;";
            String query = String.format(q, val, table_location, session_id, version, modulu, step_second);
            return MySql.select(query);
        }

        public static ResultSet get_sum_from_df(String table_location, int version, int session_id) {

            String val = table_location.contains("func") ? "delta" : "value";

            String q = "select sum(%s) as value " +
                    "from %s " +
                    "where time between date_trunc('day', now()) and date_trunc('day', now() + interval '1' day) and session_id = %s and version = %s;";
            String query = String.format(q, val, table_location, session_id, version);
            System.out.println(query);
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

        private static ResultSet op_avg_by_time_cdf_mega_table(int index_id, int fut_id, int min) {
            String modulu = "%";

            String q = "select time, value\n" +
                    "from (\n" +
                    "select time,\n" +
                    "       avg(fut - ind) over (ORDER BY time RANGE BETWEEN INTERVAL '%s min' PRECEDING AND CURRENT ROW) as value,\n" +
                    "       row_number() over (order by time) as row\n" +
                    "from (\n" +
                    "         select i.time as time, i.value as ind, f.value as fut\n" +
                    "         from (\n" +
                    "                  select *\n" +
                    "                  from %s\n" +
                    "                  where timeseries_id = %s\n" +
                    "              ) i\n" +
                    "                  inner join (select * from %s where timeseries_id = %s) f on i.time = f.time\n" +
                    "         where %s) a) b\n" +
                    "where row %s %s = 0;";

            String query = String.format(q, min, "ts.timeseries_data", index_id, "ts.timeseries_data", fut_id, Filters.TODAY, modulu, step_second);
            return MySql.select(query);
        }

        private static ResultSet op_avg_by_row_cdf_mega_table(int index_id, int fut_id, int rows) {
            String modulu = "%";

            String q = "select time, value\n" +
                    "from (\n" +
                    "         select time, avg(op) over (ORDER BY row RANGE BETWEEN %s PRECEDING AND CURRENT ROW) as value, row\n" +
                    "         from (\n" +
                    "                  select i.time                              as time,\n" +
                    "                         f.value - i.value                   as op,\n" +
                    "                         row_number() over (order by i.time) as row\n" +
                    "                  from (select * from %s where timeseries_id = %s) i\n" +
                    "                           inner join (select * from %s where timeseries_id = %s) f on i.time = f.time\n" +
                    "                  where i.time >= date_trunc('day', (select time::date\n" +
                    "                                                     from %s\n" +
                    "                                                     where timeseries_id = %s\n" +
                    "                                                     group by time::date\n" +
                    "                                                     order by time desc\n" +
                    "                                                     limit 1))) a) b\n" +
                    "where %s\n" +
                    "  and row %s %s = 0\n" +
                    "order by time;";

            String query = String.format(q, rows, "ts.timeseries_data", index_id, "ts.timeseries_data", fut_id, "ts.timeseries_data", index_id, Filters.TODAY, modulu, step_second);
            return MySql.select(query);
        }

        public static ResultSet get_serie_moving_avg(int serie_id, int min) {
            String q = "select avg(value) as value\n" +
                    "from ts.timeseries_data\n" +
                    "where timeseries_id = %s\n" +
                    "and time > now() - interval '%s min';";

            String query = String.format(q, serie_id, min);
            return MySql.select(query);
        }


        public static ResultSet get_df_exp_sum(int serie_id, int index_id) {
            String q = "select sum(sum) as value\n" +
                    "from ts.ca_timeseries_1day_candle\n" +
                    "where timeseries_id = %s\n" +
                    "  and date_trunc('day', time) >= (select date_trunc('day', time)::date\n" +
                    "       from ts.timeseries_data\n" +
                    "       where timeseries_id = %s\n" +
                    "         and date_trunc('day', time) < date_trunc('day', now())\n" +
                    "       group by date_trunc('day', time)\n" +
                    "       order by date_trunc('day', time) desc\n" +
                    "       limit 1);";

            String query = String.format(q, serie_id, index_id);
            return MySql.select(query);
        }


        public static ResultSet get_op_avg_mega(int index_id, int fut_id, String type) {
            switch (type) {
                case AVG_TODAY:
                    return op_avg_mega_table(index_id, fut_id);
            }
            return null;
        }

        public static ResultSet get_last_record_mega(int serie_id, String type) {
            switch (type) {
                case RAW:
                    return get_last_raw_record_mega(serie_id);
                case CDF:
                    return get_last_cdf_record_mega(serie_id);
            }
            return null;
        }

        public static ResultSet get_transaction(int session_id) {
            String q = "select created_at, position_type, index_value_at_creation, index_value_at_close, close_reason\n" +
                    "from ts.transactions\n" +
                    "where session_id = %s\n" +
                    "and date_trunc('day', created_at) = date_trunc('day', now())\n" +
                    "order by created_at desc limit 1;";

            String query = String.format(q, session_id);

            return MySql.select(query);
        }

        private static ResultSet get_last_raw_record_mega(int serie_id) {
            String q = "select *\n" +
                    "from ts.timeseries_data\n" +
                    "where timeseries_id = %s\n" +
                    "and %s %s;";

            String query = String.format(q, serie_id, Filters.TODAY, Filters.ORDER_BY_TIME_DESC_OFFSET_1_LIMIT_1);
            return MySql.select(query);
        }

        private static ResultSet get_last_cdf_record_mega(int serie_id) {
            String q = "select sum(value) as value\n" +
                    "from ts.timeseries_data\n" +
                    "where timeseries_id = %s\n" +
                    "and %s;";

            String query = String.format(q, serie_id, Filters.TODAY);
            return MySql.select(query);
        }

        public static ResultSet get_serie_mega_table(int serie_id, String type, int min_from_start) {
            switch (type) {
                case RAW:
                    return get_serie_raw_mega_table(serie_id, min_from_start);
                case CDF:
                    return get_serie_cdf_mega_table(serie_id, min_from_start);
            }
            return null;
        }

        public static ResultSet get_cumulative_avg_serie(int serie_id, int min) {

            String modulu = "%";

            String q = "select time, value\n" +
                    "from (\n" +
                    "         select time, avg(value) over (ORDER BY time RANGE BETWEEN '%s min' PRECEDING AND CURRENT ROW) as value, row_number() over (order by time) as row\n" +
                    "         from ts.timeseries_data\n" +
                    "         where timeseries_id = %s\n" +
                    "           and %s) a\n" +
                    "where row %s %s = 0;";

            String query = String.format(q, min, serie_id, Filters.TODAY, modulu, step_second);
            return MySql.select(query);
        }


        private static ResultSet get_serie_raw_mega_table(int serie_id, int min_from_start) {

            String time_start = get_first_today_record_time(min_from_start, serie_id);
            String query;

            if (time_start.equals("none")) {
                String modulu = "%";

                String q = "select time, value\n" +
                        "from (\n" +
                        "         select time, value, row_number() over (order by time) as row\n" +
                        "         from %s\n" +
                        "         where timeseries_id = %s\n" +
                        "           and %s) a\n" +
                        "where row %s %s = 0;";

                query = String.format(q, "ts.timeseries_data", serie_id, Filters.TODAY, modulu, step_second);
            } else {
                String modulu = "%";

                String q = "select time, value\n" +
                        "from (\n" +
                        "         select time, value, row_number() over (order by time) as row\n" +
                        "         from %s\n" +
                        "         where timeseries_id = %s\n" +
                        "           and %s and time::time > '%s'::time) a\n" +
                        "where row %s %s = 0;";

                query = String.format(q, "ts.timeseries_data", serie_id, Filters.TODAY, time_start, modulu, step_second);
                System.out.println(query);
            }

            return MySql.select(query);
        }


        private static ResultSet get_serie_cdf_mega_table(int serie_id, int min_from_start) {
            String modulu = "%";

            String time_start = get_first_today_record_time(min_from_start, serie_id);

            String query;

            if (time_start.equals("none")) {
                String q = "select * from (\n" +
                        "select time, sum(sum) over (ORDER BY time RANGE BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) as value, row_number() over (order by time) as row\n" +
                        "from %s\n" +
                        "where timeseries_id = %s\n" +
                        "and %s) a\n" +
                        "where row %s %s = 0;\n" +
                        "\n";
                query = String.format(q, "ts.ca_timeseries_1min_candle", serie_id, Filters.TODAY, modulu, step_second);
            } else {
                String q = "select * from (\n" +
                        "select time, sum(sum) over (ORDER BY time RANGE BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) as value, row_number() over (order by time) as row\n" +
                        "from %s\n" +
                        "where timeseries_id = %s\n" +
                        "and %s and time::time > '%s'::time) a\n" +
                        "where row %s %s = 0;\n" +
                        "\n";

                query = String.format(q, "ts.ca_timeseries_1min_candle", serie_id, Filters.TODAY, time_start, modulu, step_second);
                System.out.println(query);
            }
            return MySql.select(query);
        }

        public static ResultSet cumulative_sum_query(String table_loc) {
            String query = String.format("select time, sum(value) over (order by time) as value from %s " +
                    "where %s;", table_loc, Filters.TODAY);
            return MySql.select(query);
        }

        public static ResultSet get_serie(String table_loc) {
            String modulu = "%";
            String q = "select * from(" +
                    "select *, row_number() over (order by time) as row" +
                    " from %s where %s)a " +
                    "where row %s %s = 0;";
            String query = String.format(q, table_loc, Filters.TODAY, modulu, step_second);
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

        public static ResultSet get_sum(String table_location) {
            String q = "select sum(value) as value from %s where %s;";
            String query = String.format(q, table_location, Filters.TODAY);
            return MySql.select(query);
        }

        public static void insert_rates(String id_name, double interest, double dividend, double days_to_exp, double base, double cof, String exp_name, double normalized_num) {
            String q = "INSERT INTO meta.interest_rates (stock_id, rate, dividend, days_to_expired, base, start_date, end_date, cof, item, normalized_num)" +
                    " VALUES ('%s', %s, %s, %s, %s, %s, %s, '%s', '%s', '%s')";
            String today_date = "now()::date";

            String query = String.format(q, id_name, interest, dividend, days_to_exp, base, today_date, today_date, cof, exp_name, normalized_num);
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

        public static void update_prop(String client_name, String prop, String data) {
            String q = "update sagiv.props SET data = '%s' WHERE stock_id = '%s' AND prop = '%s';";
            String query = String.format(q, data, client_name, prop);
            MySql.update(query);
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

        public static ResultSet get_serie(String table_location, int session_id, int version, int step_second) {

            String modulu = "%";

            String q = "select time, value " +
                    "from (\n" +
                    "select *, row_number() over (order by time) as row\n" +
                    "from %s\n" +
                    "where version = %s\n" +
                    "  and session_id = %s\n" +
                    "  and %s) a\n" +
                    "where row %s %s = 0;";

            String query = String.format(q, table_location, version, session_id, Filters.TODAY, modulu, step_second);
            System.out.println(query);
            return MySql.select(query);
        }
    }

    public static String get_first_today_record_time(int min, int timeseries_id) {
        String q = "select time::time + interval '%s min' as time\n" +
                "from ts.timeseries_data\n" +
                "where timeseries_id = %s\n" +
                "and date_trunc('day', time) = now()::date order by time limit 1;";
        String query = String.format(q, min, timeseries_id);

        ResultSet rs = MySql.select(query);
        while (true) {
            try {
                if (!rs.next()) break;
                String time = rs.getString("time");
                return time;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        return "none";
    }

    public static class Filters {
        public static final String ONE_OR_MINUS_ONE = "(value = 1 or value = -1)";
        public static final String BIGGER_OR_SMALLER_10K = "(value < 10000 or value > -10000)";
        public static final String TODAY = "time between date_trunc('day', now()) and date_trunc('day', now() + interval '1' day)";
        public static final String ORDER_BY_TIME = "order by time";
        public static final String ORDER_BY_TIME_DESC = "order by time desc";
        public static final String ORDER_BY_TIME_DESC_LIMIT_1 = "order by time desc limit 1";
        public static final String ORDER_BY_TIME_DESC_OFFSET_1_LIMIT_1 = "order by time desc offset 1 limit 1";
    }
}