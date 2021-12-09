package tickTimeMargin;

import dataBase.MyTick;
import dataBase.MyTickTimeSerie;
import dataBase.mySql.MySql;
import dataBase.mySql.dataUpdaters.IDataBaseHandler;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class MainTickTimeMargin {

    static int SUNDAY = 7;
    static int MONDAY = 1;
    static int TUESDAY = 2;
    static int WEDNESDAY = 3;
    static int THURSDAY = 4;
    static int FRIDAY = 5;
    static int SATURDAY = 6;

    public static void main(String[] args) {
        MainTickTimeMargin mainTickTimeMargin = new MainTickTimeMargin();
        mainTickTimeMargin.run_muilty_days();
    }

    public void run_muilty_days() {

        LocalDate date = LocalDate.of(2021, 4, 17);
        LocalDate end_date = LocalDate.of(2021, 11, 24);

        while (date.isBefore(end_date)) {
            // NOT SATURDAY OR SUNDAY
            if (date.getDayOfWeek().getValue() != SATURDAY && date.getDayOfWeek().getValue() != SUNDAY) {
                System.out.println();
                System.out.println("---------- " + date + " -----------");
                run_single_day(date);
            }
            date = date.plusDays(1);
        }
    }
    
    // Single day runner
    public void run_single_day(LocalDate date) {
        ndx(date);
    }
    
    private void ta35(LocalDate date) {
        int min = 60;

        SingleDayLogicFactory.op_avg_ta35("data.ta35_op_avg_month", "data.ta35_futures", date);
        SingleDayLogicFactory.op_avg_ta35("data.ta35_op_avg_week", "data.ta35_futures_week", date);
        SingleDayLogicFactory.op_avg_ta35("data.ta35_op_avg_month_60", "data.ta35_futures", date, min);
        SingleDayLogicFactory.op_avg_ta35("data.ta35_op_avg_week_60", "data.ta35_futures_week", date, min);

        SingleDayLogicFactory.ta35_bid_ask_counter_avg("data.ta35_delta_month_avg", "data.ta35_delta_month", date);
        SingleDayLogicFactory.ta35_bid_ask_counter_avg("data.ta35_delta_week_avg", "data.ta35_delta_week", date);
        SingleDayLogicFactory.ta35_bid_ask_counter_avg("data.ta35_delta_month_avg_60", "data.ta35_delta_month", date, min);
        SingleDayLogicFactory.ta35_bid_ask_counter_avg("data.ta35_delta_week_avg_60", "data.ta35_delta_week", date, min);

    }

    private void spx500(LocalDate date) {
//        SingleDayLogicFactory.op_avg("data.spx500_op_avg_day", "data.spx500_fut_day", date);
        SingleDayLogicFactory.op_avg("data.spx500_op_avg_day_15","data.spx500_index", "data.spx500_fut_day", 15, date);
        SingleDayLogicFactory.op_avg("data.spx500_op_avg_week_60","data.spx500_index", "data.spx500_fut_day", 60, date);

        SingleDayLogicFactory.bid_ask_counter_avg("data.spx500_bid_ask_counter_avg", date);
//        SingleDayLogicFactory.bid_ask_counter_avg("data.spx500_bid_ask_counter_avg_5", 5, date);
//        SingleDayLogicFactory.bid_ask_counter_avg("data.spx500_bid_ask_counter_avg_15", 15, date);
//        SingleDayLogicFactory.bid_ask_counter_avg("data.spx500_bid_ask_counter_avg_45", 45, date);
    }

    private void ndx(LocalDate date) {
        SingleDayLogicFactory.op_avg("data.ndx_op_avg_day", "data.ndx_fut_day", "data.ndx_index", date);
//        SingleDayLogicFactory.op_avg("data.ndx_op_avg_day_15","data.ndx_index", "data.ndx_fut_day", 15, date);
//        SingleDayLogicFactory.op_avg("data.ndx_op_avg_day_60","data.ndx_index", "data.ndx_fut_day", 60, date);
    }



    private void correltion(String index_table, String decision_table) {





    }


    private void spx_avg_delta_index(LocalDate date) {

        SingleDayLogicFactory.cumulative_avg_timeserie("data.spx500_index_avg_5", "data.spx500_index", 5, date);
        SingleDayLogicFactory.cumulative_avg_timeserie("data.spx500_index_avg_15", "data.spx500_index", 15, date);
        SingleDayLogicFactory.cumulative_avg_timeserie("data.spx500_index_avg_30", "data.spx500_index", 30, date);

        SingleDayLogicFactory.cumulative_avg_timeserie_cdf("data.spx500_fut_delta_avg_5", "data.spx500_fut_delta_cdf", 5, date);
        SingleDayLogicFactory.cumulative_avg_timeserie_cdf("data.spx500_fut_delta_avg_15", "data.spx500_fut_delta_cdf", 15, date);
        SingleDayLogicFactory.cumulative_avg_timeserie_cdf("data.spx500_fut_delta_avg_30", "data.spx500_fut_delta_cdf", 15, date);

    }

    private void ndx_avg_delta_index(LocalDate date) {

        SingleDayLogicFactory.cumulative_avg_timeserie("data.ndx_index_avg_5", "data.ndx_index", 5, date);
        SingleDayLogicFactory.cumulative_avg_timeserie("data.ndx_index_avg_15", "data.ndx_index", 15, date);
        SingleDayLogicFactory.cumulative_avg_timeserie("data.ndx_index_avg_30", "data.ndx_index", 30, date);

        SingleDayLogicFactory.cumulative_avg_timeserie_cdf("data.ndx_fut_delta_avg_5", "data.ndx_fut_delta_cdf", 5, date);
        SingleDayLogicFactory.cumulative_avg_timeserie_cdf("data.ndx_fut_delta_avg_15", "data.ndx_fut_delta_cdf", 15, date);
        SingleDayLogicFactory.cumulative_avg_timeserie_cdf("data.ndx_fut_delta_avg_30", "data.ndx_fut_delta_cdf", 15, date);

    }


    private void import_series_time_sorted(ArrayList<String> tables_location_list, LocalDate date) {

        ArrayList<MyTickTimeSerie> series_list = new ArrayList<>();

        for (String table : tables_location_list) {
//            IDataBaseHandler.loadSerieData(MySql.Queries.get_serie(table, date)) {
        }

    }




    private void tick_logic(LocalDate date) {

        String table_location = "data.ndx_fut_e1";
        String speed_table_location = "data.ndx_fut_e1_tick_speed";

        // Import data
        ArrayList<LocalDateTime> times = import_data(table_location, date);

        System.out.println("Got data");

        // Logic
        ArrayList<MyTick> myTicks = logic(times);

        System.out.println("Calced");

        // Insert
        insert_data(myTicks, speed_table_location);

        System.out.println("Done");
    }

    private void insert_data(ArrayList<MyTick> myTicks, String speed_table_location) {
        IDataBaseHandler.insert_batch_data(myTicks, speed_table_location);
    }

    private ArrayList<MyTick> logic(ArrayList<LocalDateTime> times) {
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

    private ArrayList<LocalDateTime> import_data(String table_location, LocalDate date) {
        String q = "SELECT * FROM %s WHERE time::date = date'%s' ORDER BY time;";
        String query = String.format(q, table_location, date.toString());

        ResultSet rs = MySql.select(query);

        ArrayList<LocalDateTime> times = new ArrayList<>();

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

}

