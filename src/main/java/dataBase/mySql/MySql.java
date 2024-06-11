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
    public static final String JIBE_PROD_CONNECTION = "JIBE_PROD_CONNECTION";
    public static final String JIBE_DEV_CONNECTION = "JIBE_DEV_CONNECTION";


    public static IConnectionPool getConnectionPool(String connection_type) {

        IConnectionPool connectionPool = null;

        // Prod
        if (connection_type.equals(MySql.JIBE_PROD_CONNECTION)) {
            connectionPool = ConnectionPoolJibeProd.getConnectionsPoolInstance();
        } else if (connection_type.equals(MySql.JIBE_DEV_CONNECTION)) {
            connectionPool = ConnectionPoolJibeDev.getConnectionsPoolInstance();
        }
        return connectionPool;
    }

    // Insert
    public static void insert(String query, String connection_type) {

        Connection conn = null;
        IConnectionPool connectionPool = null;
        try {
            connectionPool = getConnectionPool(connection_type);
            conn = connectionPool.getConnection();
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
                connectionPool.releaseConnection(conn);
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


    public static void insert(String query, boolean thread, String connection_type) {
        if (thread) {
            new Thread(() -> {
                insert(query, connection_type);
            }).start();
        } else {
            insert(query, connection_type);
        }
    }

    // Update
    public static void update(String query, String connection_type) {
        Connection conn = null;
        IConnectionPool connectionPool = null;
        try {
            connectionPool = getConnectionPool(connection_type);
            conn = connectionPool.getConnection();
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
                connectionPool.releaseConnection(conn);
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
    public static ResultSet select(String query, String connection_type) {
        ResultSet rs = null;
        Connection conn = null;
        IConnectionPool connectionPool = null;

        try {
            connectionPool = getConnectionPool(connection_type);
            conn = connectionPool.getConnection();
            Statement stmt = conn.createStatement();

            // execute the query, and get a java resultset
            rs = stmt.executeQuery(query);

            System.out.println(query);

        } catch (Exception e) {
            e.printStackTrace();
            Arik.getInstance().sendMessage(e.getMessage() + "\n" + e.getCause() + " \n" + "Select");
        } finally {
            // Release connection
            if (conn != null) {
                connectionPool.releaseConnection(conn);
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

    public static void trunticate(String tableName, String schema, String connection_type) {

        String query = "TRUNCATE TABLE " + schema + "." + tableName;
        Connection conn = null;
        IConnectionPool connectionPool = null;
        try {
            connectionPool = getConnectionPool(connection_type);
            conn = connectionPool.getConnection();
            Statement stmt = conn.createStatement();

            // execute the query, and get a java resultset
            stmt.executeUpdate(query);

        } catch (Exception e) {
            e.printStackTrace();
            Arik.getInstance().sendMessage(e.getMessage() + "\n" + e.getCause() + " \n" + "Trunticate");
        } finally {
            // Release connection
            if (conn != null) {
                connectionPool.releaseConnection(conn);
            }
        }
    }

    // Get connection pool
    public static Connection getConnection(String connection_type) {
        Connection conn = null;
        try {

            // Prod
            if (connection_type.equals(MySql.JIBE_PROD_CONNECTION)) {
                conn = ConnectionPoolJibeProd.getConnectionsPoolInstance().getConnection();
            } else if (connection_type.equals(MySql.JIBE_DEV_CONNECTION)) {
                conn = ConnectionPoolJibeDev.getConnectionsPoolInstance().getConnection();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Arik.getInstance().sendMessage(e.getMessage() + "\n" + e.getCause() + " \n" + "Trunticate");
        }
        return conn;
    }

    public static class Queries {
        public static final int step_second = 10;

//        public static ResultSet get_races(int serie_id) {
//
//        }

        public static ResultSet get_baskets_up_sum(int serie_id) {
            String q = "select sum(value) as value " +
                    "from ts.timeseries_data where timeseries_id = %s and value = 1 and time between date_trunc('day', now()) and date_trunc('day', now() + interval '1' day);";
            String query = String.format(q, serie_id);
            return MySql.select(query, MySql.JIBE_PROD_CONNECTION);
        }

        public static ResultSet get_baskets_down_sum(int serie_id) {
            String q = "select sum(value) as value " +
                    "from ts.timeseries_data where timeseries_id = %s and value = -1 and time between date_trunc('day', now()) and date_trunc('day', now() + interval '1' day);";
            String query = String.format(q, serie_id);
            return MySql.select(query, MySql.JIBE_PROD_CONNECTION);
        }

        public static void delete_today_rates(String connection_type) {
            String query = "delete \n" +
                    "from meta.interest_rates\n" +
                    "where start_date = now()::date;";
            MySql.update(query, connection_type);
        }

        public static void delete_today_rates(Connection conn) {
            String query = "delete \n" +
                    "from meta.interest_rates\n" +
                    "where start_date = now()::date;";
            MySql.update(query, conn);
        }

        public static ResultSet delete_today_rates(String stock_id, String connection_type) {
            String q = "delete \n" +
                    "from meta.interest_rates\n" +
                    "where start_date = now()::date\n" +
                    "and stock_id = '%s';";
            String query = String.format(q, stock_id);
            return MySql.select(query, connection_type);
        }

        public static ResultSet delete_today_rates(String stock_id, Connection conn) {
            String q = "delete \n" +
                    "from meta.interest_rates\n" +
                    "where start_date = now()::date\n" +
                    "and stock_id = '%s';";
            String query = String.format(q, stock_id);
            return MySql.select(query, conn);
        }

        public static String load_stocks_excel_file_location(String connection_type) {
            String query = "select data\n" +
                    "from sagiv.props\n" +
                    "where stock_id = 'stocks'\n" +
                    "and prop = 'SAPI_EXCEL_FILE_LOCATION';";

            String res = "";

            ResultSet rs = MySql.select(query, connection_type);
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


        public static void update_stock_rates(String stock_id, double interest, double div, int days_left, double base, String exp_type, String connection_type) throws SQLException {
            String q = "INSERT INTO meta.interest_rates (stock_id, rate, dividend, days_to_expired, base, start_date, end_date, item) VALUES " +
                    "('%s', %s, %s, %s, %s, '%s', '%s', '%s')";
            String query = String.format(q, stock_id, interest, div, days_left, base, LocalDate.now(), LocalDate.now(), exp_type);

            System.out.println(query);

            MySql.insert(query, connection_type);

//            Connection slo_conn = ConnectionPoolJibeProd.get_slo_single_connection();
//            MySql.insert(query, slo_conn);

        }

        public static ResultSet get_exp_data(BASE_CLIENT_OBJECT client, int serie_id, String exp_prop_name, String connection_type) {

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
            return MySql.select(query, connection_type);
        }

        public static ResultSet get_start_exp_mega(int index_id, String stock_id, String exp_prop_name, String connection_type) {

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
            return MySql.select(query, connection_type);
        }


        public static ResultSet get_accounts_data(String connection_type) {
            String query = "select * from sagiv.arik_accounts;";
            return MySql.select(query, connection_type);
        }

        public static ResultSet op_avg_mega_table(int index_id, int fut_id, String connection_type) {
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
            return MySql.select(query, connection_type);
        }


        public static ResultSet get_pre_day_op_avg(int serie_id, String connection_type) {
            String q = "with pre_date as (\n" +
                    "    SELECT date_trunc('day',time) date\n" +
                    "    FROM ts.timeseries_data\n" +
                    "    WHERE timeseries_id = %s\n" +
                    "      and date_trunc('day', time) < CURRENT_DATE\n" +
                    "      and value is not null\n" +
                    "    ORDER BY date_trunc('day', time) DESC\n" +
                    "    LIMIT 1\n" +
                    ")\n" +
                    "select *\n" +
                    "from ts.timeseries_data, pre_date\n" +
                    "where date_trunc('day', time) = pre_date.date\n" +
                    "and timeseries_data.timeseries_id = %s\n" +
                    "order by time desc limit 1;";

            String query = String.format(q, serie_id, serie_id);

            System.out.println(query);
            return MySql.select(query, connection_type);
        }



        public static ResultSet get_serie(String table_location, int step_second, String connection_type) {
            String modulu = "%";
            String q = "select * " +
                    "from ( " +
                    "SELECT *, row_number() over (order by time) as row " +
                    "FROM %s " +
                    "where time between date_trunc('day', now()) and date_trunc('day', now() + interval '1' day) " +
                    ") a " +
                    "where row %s %s = 0;";
            String query = String.format(q, table_location, modulu, step_second);
            return MySql.select(query, connection_type);
        }




        public static ResultSet get_serie_moving_avg(int serie_id, int min, String connection_type) {
            String q = "select avg(value) as value\n" +
                    "from ts.timeseries_data\n" +
                    "where timeseries_id = %s\n" +
                    "and time > now() - interval '%s min';";

            String query = String.format(q, serie_id, min);
            return MySql.select(query, connection_type);
        }


        public static ResultSet get_df_exp_sum(int serie_id, int index_id, String connection_type) {
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
            return MySql.select(query, connection_type);
        }


        public static ResultSet get_op_avg_mega(int index_id, int fut_id, String type, String connection_type) {
            switch (type) {
                case AVG_TODAY:
                    return op_avg_mega_table(index_id, fut_id, connection_type);
            }
            return null;
        }

        public static ResultSet get_last_record_mega(int serie_id, String type, String connection_type) {
            switch (type) {
                case RAW:
                    return get_last_raw_record_mega(serie_id, connection_type);
                case CDF:
                    return get_last_cdf_record_mega(serie_id, connection_type);
            }
            return null;
        }


        public static ResultSet get_transaction(int session_id, String connection_type) {
            String q = "select created_at, position_type, index_value_at_creation, index_value_at_close, close_reason, session_id\n" +
                    "from ts.transactions\n" +
                    "where session_id = %s\n" +
                    "order by created_at desc limit 1;";

            String query = String.format(q, session_id);

            return MySql.select(query, connection_type);
        }

        private static ResultSet get_last_raw_record_mega(int serie_id, String connection_type) {
            String q = "select *\n" +
                    "from ts.timeseries_data\n" +
                    "where timeseries_id = %s\n" +
                    "and %s %s;";

            String query = String.format(q, serie_id, Filters.TODAY, Filters.ORDER_BY_TIME_DESC_OFFSET_1_LIMIT_1);
            return MySql.select(query, connection_type);
        }

        private static ResultSet get_last_cdf_record_mega(int serie_id, String connection_type) {
            String q = "select sum(value) as value\n" +
                    "from ts.timeseries_data\n" +
                    "where timeseries_id = %s\n" +
                    "and %s;";

            String query = String.format(q, serie_id, Filters.TODAY);
            return MySql.select(query, connection_type);
        }

        public static ResultSet get_serie_mega_table(int serie_id, String type, int min_from_start, String connection_type) {
            switch (type) {
                case RAW:
                    return get_serie_raw_mega_table(serie_id, min_from_start, connection_type);
                case CDF:
                    return get_serie_cdf_mega_table(serie_id, min_from_start, connection_type);
            }
            return null;
        }

        public static ResultSet get_cumulative_avg_serie(int serie_id, int min, String connection_type) {

            String modulu = "%";

            String q = "select time, value\n" +
                    "from (\n" +
                    "         select time, avg(value) over (ORDER BY time RANGE BETWEEN '%s min' PRECEDING AND CURRENT ROW) as value, row_number() over (order by time) as row\n" +
                    "         from ts.timeseries_data\n" +
                    "         where timeseries_id = %s\n" +
                    "           and %s) a\n" +
                    "where row %s %s = 0;";

            String query = String.format(q, min, serie_id, Filters.TODAY, modulu, step_second);
            return MySql.select(query, connection_type);
        }

//        private static ResultSet get_serie_raw_mega_table(int serie_id, int min_from_start) {
//
//            String time_start = get_first_today_record_time(min_from_start, serie_id);
//            String query;
//
//            if (time_start.equals("none")) {
//                String modulu = "%";
//
//                String q = "select time, value\n" +
//                        "from (\n" +
//                        "         select time, value, row_number() over (order by time) as row\n" +
//                        "         from %s\n" +
//                        "         where timeseries_id = %s\n" +
//                        "           and %s) a\n" +
//                        "where row %s %s = 0;";
//
//                query = String.format(q, "ts.timeseries_data", serie_id, Filters.TODAY, modulu, step_second);
//            } else {
//                String modulu = "%";
//
//                String q = "select time, value\n" +
//                        "from (\n" +
//                        "         select time, value, row_number() over (order by time) as row\n" +
//                        "         from %s\n" +
//                        "         where timeseries_id = %s\n" +
//                        "           and %s and time::time > '%s'::time) a\n" +
//                        "where row %s %s = 0;";
//
//                query = String.format(q, "ts.timeseries_data", serie_id, Filters.TODAY, time_start, modulu, step_second);
//                System.out.println(query);
//            }
//
//            return MySql.select(query);
//        }


        private static ResultSet get_serie_raw_mega_table(int serie_id, int min_from_start, String connection_type) {

            String modulu = "%";

            String query;

            String q = "with data as (\n" +
                    "    select * , row_number() over (order by time) as row\n" +
                    "    from %s\n" +
                    "    where timeseries_id = %s\n" +
                    "      and time between date_trunc('day', now()) and date_trunc('day', now() + interval '1' day)\n" +
                    "    order by time\n" +
                    "),\n" +
                    "     first as (\n" +
                    "         select *\n" +
                    "         from %s\n" +
                    "         where timeseries_id = %s\n" +
                    "           and time between date_trunc('day', now()) and date_trunc('day', now() + interval '1' day)\n" +
                    "         order by time\n" +
                    "         limit 1\n" +
                    "     )\n" +
                    "select data.time as time, data.value as value\n" +
                    "from data, first\n" +
                    "where data.time > first.time + interval '%s min' and data.row %s %s = 0;\n";

            query = String.format(q, "ts.timeseries_data", serie_id, "ts.timeseries_data", serie_id, min_from_start, modulu, step_second);

            System.out.println(query);

            return MySql.select(query, connection_type);
        }


        private static ResultSet get_serie_cdf_mega_table(int serie_id, int min_from_start, String connection_type) {
            String modulu = "%";

            String query;

            String q = "\n" +
                    "with data as (\n" +
                    "    select time, sum(value) over (ORDER BY time RANGE BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) as value, row_number() over (order by time) as row\n" +
                    "    from %s\n" +
                    "    where timeseries_id = %s\n" +
                    "      and time between date_trunc('day', now()) and date_trunc('day', now() + interval '1' day)\n" +
                    "    order by time\n" +
                    "),\n" +
                    "     first as (\n" +
                    "         select *\n" +
                    "         from %s\n" +
                    "         where timeseries_id = %s\n" +
                    "           and time between date_trunc('day', now()) and date_trunc('day', now() + interval '1' day)\n" +
                    "         order by time\n" +
                    "         limit 1\n" +
                    "     )\n" +
                    "select data.time as time, data.value as value\n" +
                    "from data, first\n" +
                    "where data.time > first.time + interval '%s min' and data.row %s %s = 0;\n";

// ts.ca_timeseries_1min_candle
            query = String.format(q, "ts.timeseries_data", serie_id, "ts.timeseries_data", serie_id, min_from_start, modulu, step_second);
            System.out.println(query);

            return MySql.select(query, connection_type);
        }




        public static ResultSet get_last_record(String table_location, String connection_type) {
            String q = "select * from %s %s";
            String query = String.format(q, table_location, Filters.ORDER_BY_TIME_DESC_LIMIT_1);
            return MySql.select(query, connection_type);
        }


        public static void insert_rates(String id_name, double interest, double dividend, double days_to_exp, double base, double cof, String exp_name, double normalized_num, String connection_type) {
            String q = "INSERT INTO meta.interest_rates (stock_id, rate, dividend, days_to_expired, base, start_date, end_date, cof, item, normalized_num)" +
                    " VALUES ('%s', %s, %s, %s, %s, %s, %s, '%s', '%s', '%s')";
            String today_date = "now()::date";

            String query = String.format(q, id_name, interest, dividend, days_to_exp, base, today_date, today_date, cof, exp_name, normalized_num);
            // JIBE prod
            MySql.insert(query, connection_type);


            // JIbe dev
            try {
                Connection slo_conn = ConnectionPoolJibeProd.get_jibe_dev_single_connection();
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

        public static void update_prop(String client_name, String prop, String data, String connection_type) {
            String q = "update sagiv.props SET data = '%s' WHERE stock_id = '%s' AND prop = '%s';";
            String query = String.format(q, data, client_name, prop);
            MySql.update(query, connection_type);
        }

        public static ResultSet get_arik_sessions(String connection_type) {
            String query = "select * \n" +
                    "from sagiv.arik_sessions;";
           return MySql.select(query, connection_type);
        }

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