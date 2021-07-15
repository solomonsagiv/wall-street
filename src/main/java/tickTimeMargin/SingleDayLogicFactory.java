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

    public static void one_hour_tick_speed_avg_logic(LocalDate date) {
        String q = "insert into data.spx500_tick_speed_avg_1_hour " +
                "select time, " +
                "avg(value) over (ORDER BY  time RANGE BETWEEN INTERVAL '1 hour' PRECEDING AND CURRENT ROW) " +
                "from data.spx500_fut_e1_tick_speed " +
                "where time::date = date'%s';";

        String query = String.format(q, date);

        MySql.insert(query);
    }

    public static void op_avg_ta35(LocalDate date) {
        String q = "insert into data.ta35_op_avg_month_60 " +
                "select i.time, avg(f.futures - i.index) over (ORDER BY i.time RANGE BETWEEN INTERVAL '60 min' PRECEDING AND CURRENT ROW) " +
                "from data.ta35_index i " +
                "         inner join data.ta35_futures f on i.time = f.time " +
                "where i.time::date = '%s';";

        String query = String.format(q, date);
        MySql.insert(query);
    }


    public static void op_avg(String table_to_insert, String fut_table_to_calc_op, int min, LocalDate date) {
        String q = "insert into %s " +
                "select i.time, avg(f.value - i.value) over (ORDER BY i.time RANGE BETWEEN INTERVAL '%s min' PRECEDING AND CURRENT ROW) " +
                "from data.spx500_index i " +
                "inner join %s f " +
                "on i.time = f.time " +
                "where i.time::date = '%s';";

        String query = String.format(q, table_to_insert, min, fut_table_to_calc_op, date);


        System.out.println(query);

        MySql.insert(query);
    }


}
