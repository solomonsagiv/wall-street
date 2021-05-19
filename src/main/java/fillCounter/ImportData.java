package fillCounter;

import dataBase.mySql.MySql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ImportData {

    String[] tables = {"spx500_index", "spx500_index_bid", "spx500_index_ask", "spx500_fut_day"};
    ArrayList<ArrayList<MySerie>> arrays;

    public ImportData() {
        arrays = new ArrayList<>();

        // Import data
        import_data();

        // Research
        research();
    }

    private void research() {



        for ( ArrayList<MySerie> serie : arrays ) {



        }

    }

    private void import_data() {
        String query = "SELECT * FROM data.%s WHERE time::date = now()::date order by time;";
        for (String s : tables) {
            arrays.add(getSerieObject(String.format(query, s)));
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

    private class MySerie {

        LocalDateTime dateTime;
        double value;

        public MySerie(LocalDateTime dateTime, double value) {
            this.dateTime = dateTime;
            this.value = value;
        }

    }

}
