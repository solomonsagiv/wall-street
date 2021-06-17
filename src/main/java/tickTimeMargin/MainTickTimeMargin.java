package tickTimeMargin;

import dataBase.mySql.MySql;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class MainTickTimeMargin {

    public static void main(String[] args) {
        MainTickTimeMargin mainTickTimeMargin = new MainTickTimeMargin();
        mainTickTimeMargin.run();
    }

    String table_location = "data.spx500_fut_e1";
    LocalDate date = LocalDate.of(2021, 5, 1);

    ArrayList<LocalDateTime> times = new ArrayList<>();
    ArrayList<Long> millis = new ArrayList<>();

    public void run() {
        // Import data
        import_data();

        // Logic
        logic();

        // Print
        print();
    }

    private void print() {
        double sum = 0;
        for (Long l: millis) {
            sum += l;
        }
        System.out.println(sum / millis.size());
    }

    private void logic() {
        for (int i = 1; i < 2; i++) {
            long pre_time = Timestamp.valueOf(times.get(i -1)).getTime();
            long curr_time = Timestamp.valueOf(times.get(i)).getTime();
            long mill = curr_time - pre_time;
            millis.add(mill);
        }
    }

    private void import_data() {
        String q = "SELECT * FROM %s WHERE time::date = date'%s' ORDER BY time;";
        String query = String.format(q, table_location, date.toString());

        System.out.println(query);

        ResultSet rs  = MySql.select(query);

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
    }



}
