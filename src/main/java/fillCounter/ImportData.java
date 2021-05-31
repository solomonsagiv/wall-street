package fillCounter;

import dataBase.mySql.MySql;
import grades.GRADES;
import grades.Move_cumu_1;
import grades.OP_cumu_1;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ImportData {

    public static final int INDEX_I = 0;
    public static final int INDEX_BID_I = 1;
    public static final int INDEX_ASK_I = 2;
    public static final int FUT_I = 3;

    String[] tables = {"spx500_index", "spx500_index_bid", "spx500_index_ask", "spx500_fut_day"};
    private ArrayList<ArrayList<MySerie>> arrays;

    String date;
    String time;

    public ImportData(String date, String time) {
        this.date = date;
        this.time = time;
        arrays = new ArrayList<>();

        // Import data
        import_data();

        // Research
        research();

        // Print 
//        print();
    }

    public void print() {
        try {
            for (int row = 0; row < arrays.get(0).size(); row++) {
                for (ArrayList<MySerie> series : arrays) {
                    MySerie serie = series.get(row);
                    System.out.print(serie.dateTime + " " + serie.value + " , ");
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void research() {
        LocalDateTime min_time = null;
        int row = 0;

        // For each row
        while (true) {

            if (row >= arrays.get(0).size()) {
                break;
            }

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
                    break;
                }
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
//                System.out.println("Find min time func ");
            }
        }
        return dateTime;
    }

    private void import_data() {
        String query = "SELECT * FROM data.%s WHERE time::date = date'%s' and time::time < time'%s' order by time;";
        for (String s : tables) {
            arrays.add(getSerieObject(String.format(query, s, date, time)));
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

    protected void insertListRetro(ArrayList<Race> races, String table_location, GRADES grades) {
        if (races.size() > 0) {
            // Create the query
            StringBuilder queryBuiler = new StringBuilder("INSERT INTO %s (time, value) VALUES ");
            int last_item_id = races.get(races.size() - 1).hashCode();
            for (Race race : races) {

                // Witch grade giver use
                double value = 0;
                if (grades instanceof Move_cumu_1) {
                    value = race.move_grade;
                } else if (grades instanceof OP_cumu_1) {
                    value = race.op_grade;
                }
                queryBuiler.append(String.format("(cast('%s' as timestamp with time zone), %s)", race.dateTime, value));
                if (race.hashCode() != last_item_id) {
                    queryBuiler.append(",");
                }
            }
            queryBuiler.append(";");

            String q = String.format(queryBuiler.toString(), table_location);

            // Insert
            MySql.insert(q, true);

        }

    }

    public ArrayList<ArrayList<MySerie>> getArrays() {
        return arrays;
    }
}
