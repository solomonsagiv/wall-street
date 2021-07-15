package tickTimeMargin;

import dataBase.MyTick;
import dataBase.mySql.MySql;
import dataBase.mySql.dataUpdaters.IDataBaseHandler;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class MainTickTimeMargin {

    public static void main(String[] args) {
        MainTickTimeMargin mainTickTimeMargin = new MainTickTimeMargin();
        mainTickTimeMargin.run_muilty_days();
    }
    
    public void run_muilty_days() {

        LocalDate date = LocalDate.of(2021, 7, 14);
        LocalDate end_date = LocalDate.of(2021, 7, 15);

        while (date.isBefore(end_date)) {
            // NOT SATURDAY OR SUNDAY
            if (date.getDayOfWeek().getValue() != 6 && date.getDayOfWeek().getValue() != 7) {
                System.out.println();
                System.out.println("---------- " + date + " -----------");
                run_single_day(date);
            }
            date = date.plusDays(1);
        }
    }

    public void run_single_day(LocalDate date) {

        String table_to_insert  = "data.spx500_op_avg_day_15";
        String fut_table_to_calc_op = "data.spx500_fut_day";
        int min = 15;

        SingleDayLogicFactory.op_avg(table_to_insert,fut_table_to_calc_op, min, date);




//        SingleDayLogicFactory.bid_ask_counter_avg("data.spx500_bid_ask_counter_avg_5",5, date);

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

