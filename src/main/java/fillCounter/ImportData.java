package fillCounter;

import dataBase.mySql.MySql;
import exp.E;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ImportData {

    String date;

    String[] tables = {"spx500_index", "spx500_index_bid", "spx500_index_ask", "spx500_fut_day"};
    private ArrayList<ArrayList<MySerie>> arrays;

    public ImportData() {
        date = "2021-05-19";
        arrays = new ArrayList<>();

        // Import data
        import_data();

        // Research
        research();

        // Print 
        print();
    }

    private void print() {
        for (int row = 0; row < arrays.get(0).size(); row++) {
            for (ArrayList<MySerie> series: arrays) {
                MySerie serie = series.get(row);
                System.out.print(serie.dateTime + " " + serie.value + " , ");
            }
            System.out.println();
        }
    }

    private void research() {
        LocalDateTime min_time = null;
        int row = 0;

        boolean break_loop = false;

        // For each row
        while (true) {
            // For each series
            for (ArrayList<MySerie> series : arrays) {
                try {
                    min_time = find_min_time(row);

                    MySerie serie = series.get(row);
                    // If serie time equals min time
                    if (serie.dateTime.isAfter(min_time)) {
                        series.add(row, new MySerie(min_time, series.get(row - 1).value));
                    }
                } catch (Exception e) {
                    break_loop = true;
                    break;
                }
            }
            if (break_loop) {
                break;
            }
            row++;
        }
    }

    private LocalDateTime find_min_time(int row) {
        LocalDateTime dateTime = null;
        for (ArrayList<MySerie> series : arrays) {
            try {
                MySerie serie = series.get(row);
                if (dateTime == null) {
                    dateTime = serie.dateTime;
                } else if (serie.dateTime.isBefore(dateTime)) {
                    dateTime = serie.dateTime;
                }
            } catch (Exception e) {
                System.out.println("Find min time func ");
            }
        }
        return dateTime;
    }

    private void import_data() {
        String query = "SELECT * FROM data.%s WHERE time::date = date'%s' order by time;";
        for (String s : tables) {
            arrays.add(getSerieObject(String.format(query, s, date)));
        }
    }

    private ArrayList<MySerie> getSerieObject(String query) {
        ArrayList<MySerie> series_list = new ArrayList<>();
        ResultSet rs = MySql.select(query);

        while (true) {
            try {
                if (!rs.next()) break;
                LocalDateTime dateTime = rs.getTimestamp("time").toLocalDateTime();
                double value = rs.getDouble("value");
                series_list.add(new MySerie(dateTime, value));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return series_list;
    }

    public ArrayList<ArrayList<MySerie>> getArrays() {
        return arrays;
    }

}
