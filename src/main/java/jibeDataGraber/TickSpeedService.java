package jibeDataGraber;

import dataBase.mySql.MySql;
import locals.L;
import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TickSpeedService extends MyBaseService {

    String table_location;
    public double tick_speed_hour = 0;

    public TickSpeedService(BASE_CLIENT_OBJECT client, String table_location) {
        super(client);
        this.table_location = table_location;
    }
    
    @Override
    public void go() {
        String q = "select avg(value) as value " +
                "from %s " +
                "where time > now() - interval '1 hour';";
        String query = String.format(q, table_location);
        ResultSet rs = MySql.select(query);

        while (true) {
            try {
                if (!rs.next()) break;

                double value = rs.getDouble("value");
                tick_speed_hour = L.floor(value, 10);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public String getName() {
        return "Tick speed service";
    }
    
    @Override
    public int getSleep() {
        return 60000;
    }
}
