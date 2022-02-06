package tickTimeMargin;

import dataBase.mySql.MySql;
import java.time.LocalDate;

public class SingleDayLogicFactory {

    public static void bid_ask_counter_avg(String table_location, int min, LocalDate date) {
        String q = "insert into %s " +
                "select  time, avg(sum.sum) over (ORDER BY time RANGE BETWEEN INTERVAL '%s min' PRECEDING AND CURRENT ROW) " +
                "from ( " +
                "         select time, sum(counter.value) over (ORDER BY time RANGE BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) " +
                "         from data.spx500_index_bid_ask_counter_cdf counter " +
                "         where time::date = '%s') sum;";

        String query = String.format(q, table_location, min, date);

        MySql.insert(query);
    }

    public static void bid_ask_counter_avg(String table_location, LocalDate date) {
        String q = "insert into %s " +
                "select  time, avg(sum.sum) over (ORDER BY time RANGE BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) " +
                "from ( " +
                "         select time, sum(counter.value) over (ORDER BY time RANGE BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) " +
                "         from data.spx500_index_bid_ask_counter_cdf counter " +
                "         where time::date = '%s') sum;";

        String query = String.format(q, table_location, date);

        MySql.insert(query);
    }

    public static void cumulative_avg_timeserie(String table_to_insert, String table_to_take_from, int min, LocalDate date) {

        String q = "insert into %s " +
                "select time, avg(value) over (ORDER BY time RANGE BETWEEN INTERVAL '%s min' PRECEDING AND CURRENT ROW) as value " +
                "from %s " +
                "where time::date = date'%s';";

        String query = String.format(q, table_to_insert, min, table_to_take_from, date);
        MySql.insert(query);

    }


    public static void wallstreet_op_avg_insert(String table_to_insert, int rows, String index_table, String fut_table, LocalDate pre_date, LocalDate start_date, LocalDate end_date) {
        String q = "insert into %s\n" +
                "select *\n" +
                "from (\n" +
                "         select time, avg(op) over (ORDER BY row RANGE BETWEEN rows PRECEDING AND CURRENT ROW) as value\n" +
                "         from (\n" +
                "                  select i.time                              as time,\n" +
                "                         f.value - i.value                   as op,\n" +
                "                         row_number() over (order by i.time) as row\n" +
                "                  from %s i\n" +
                "                           inner join %s f on i.time = f.time\n" +
                "                  where i.time >= date_trunc('day', date'%s')) a) b\n" +
                "where time between date_trunc('day', date'%s') and date_trunc('day', date'%s')\n" +
                "order by time;";

        String query = String.format(q, table_to_insert, rows, index_table, fut_table, pre_date, start_date, end_date);
        MySql.insert(query);

    }


    public static void ta35_op_avg_insert(String table_to_insert, int rows, String index_table, String fut_table, LocalDate pre_date, LocalDate start_date, LocalDate end_date) {
        String q = "insert into %s\n" +
                "select *\n" +
                "from (\n" +
                "         select time, avg(op) over (ORDER BY row RANGE BETWEEN %s PRECEDING AND CURRENT ROW) as value\n" +
                "         from (\n" +
                "                  select i.time                              as time,\n" +
                "                         f.futures - ((i.bid + i.ask) / 2)     as op,\n" +
                "                         row_number() over (order by i.time) as row\n" +
                "                  from %s i\n" +
                "                           inner join %s f on i.time = f.time\n" +
                "                  where i.time >= date_trunc('day', date'%s')) a) b\n" +
                "where time between date_trunc('day', date'%s') and date_trunc('day', date'%s')\n" +
                "order by time;";

        String query = String.format(q, table_to_insert, rows, index_table, fut_table, pre_date, start_date, end_date);
        MySql.insert(query);

    }



    public static void cumulative_avg_timeserie_cdf(String table_to_insert, String table_to_take_from, int min, LocalDate date) {
        String q = "insert into %s " +
                "select  time, avg(sum.sum) over (ORDER BY time RANGE BETWEEN INTERVAL '%s min' PRECEDING AND CURRENT ROW) " +
                "from ( " +
                "         select time, sum(counter.value) over (ORDER BY time RANGE BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) " +
                "         from %s counter " +
                "         where time::date = '%s') sum;";

        String query = String.format(q, table_to_insert, min, table_to_take_from, date);
        MySql.insert(query);

    }

    public static void ta35_bid_ask_counter_avg(String insert_to_table, String table_to_calc_from, LocalDate date, int min) {
        String q = "insert into %s " +
                "select  time, avg(sum.sum) over (ORDER BY time RANGE BETWEEN INTERVAL '%s min' PRECEDING AND CURRENT ROW) " +
                "from ( " +
                "         select time, sum(counter.value) over (ORDER BY time RANGE BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) " +
                "         from %s counter " +
                "         where time::date = '%s') sum;";

        String query = String.format(q, insert_to_table, min, table_to_calc_from, date);

        MySql.insert(query);
    }

    public static void ta35_bid_ask_counter_avg(String insert_to_table, String table_to_calc_from, LocalDate date) {
        String q = "insert into %s " +
                "select  time, avg(sum.sum) over (ORDER BY time RANGE BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) " +
                "from ( " +
                "         select time, sum(counter.value) over (ORDER BY time RANGE BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) " +
                "         from %s counter " +
                "         where time::date = '%s') sum;";

        String query = String.format(q, insert_to_table, table_to_calc_from, date);

        MySql.insert(query);
    }

    public static void one_hour_tick_speed_avg_logic(LocalDate date) {
        String q = "insert into data.spx500_tick_speed_avg_1_hour " +
                "select time, " +
                "avg(value) over (ORDER BY  time RANGE BETWEEN INTERVAL '1 hour' PRECEDING AND CURRENT ROW) " +
                "from data.spx500_fut_e1_tick_speed " +
                "where time::date = date'%s';";

        String query = String.format(q, date);

        MySql.insert(query);
    }

    public static void op_avg_ta35(String table_to_insert, String table_to_calc, LocalDate date, int min) {
        String q = "insert into %s " +
                "select i.time, avg(f.futures - i.index) over (ORDER BY i.time RANGE BETWEEN INTERVAL '%s min' PRECEDING AND CURRENT ROW) " +
                "from data.ta35_index i " +
                "         inner join %s f on i.time = f.time " +
                "where i.time::date = '%s';";

        String query = String.format(q, table_to_insert, min, table_to_calc, date);
        MySql.insert(query);
    }

    public static void op_avg_ta35_with_bid_ask(String table_to_insert, String table_to_calc, LocalDate date, int min) {
        String q = "insert into %s " +
                "select i.time, avg(f.futures - ((i.ask + i.bid) / 2)) over (ORDER BY i.time RANGE BETWEEN INTERVAL '%s min' PRECEDING AND CURRENT ROW) as value " +
                "from data.ta35_index i " +
                "inner join %s f on i.time = f.time " +
                "where i.time between date_trunc('day', date'%s') and date_trunc('day', date'%s' + interval '1' day)" +
                "and bid is not null and ask is not null;";

        String query = String.format(q, table_to_insert, min, table_to_calc, date, date);
        MySql.insert(query);
    }


    public static void op_avg_ta35(String table_to_insert, String table_to_calc, LocalDate date) {
        String q = "insert into %s " +
                "select i.time, avg(f.futures - i.index) over (ORDER BY i.time RANGE BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) " +
                "from data.ta35_index i " +
                "         inner join %s f on i.time = f.time " +
                "where i.time::date = '%s';";

        String query = String.format(q, table_to_insert, table_to_calc, date);
        MySql.insert(query);
    }

    public static void op_avg(String table_to_insert, String index_table, String fut_table_to_calc_op, int min, LocalDate date) {
        String q = "insert into %s " +
                "select i.time, avg(f.value - i.value) over (ORDER BY i.time RANGE BETWEEN INTERVAL '%s min' PRECEDING AND CURRENT ROW) " +
                "from %s i " +
                "inner join %s f " +
                "on i.time = f.time " +
                "where i.time between date_trunc('day', date'%s') and date_trunc('day', date'%s' + interval '1' day);";

        String query = String.format(q, table_to_insert, min, index_table, fut_table_to_calc_op, date, date);
        MySql.insert(query);
    }

    public static void op_avg(String table_to_insert, String fut_table_to_calc_op, String index_table, LocalDate date) {
        String q = "insert into %s" +
                " select i.time,  avg(f.value - i.value) over (ORDER BY i.time RANGE BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) " +
                "    from %s f " +
                "    inner join %s i on f.time = i.time " +
                "    where i.time::date = date'%s';";

        String query = String.format(q, table_to_insert, fut_table_to_calc_op, index_table, date);
        MySql.insert(query);
    }


}