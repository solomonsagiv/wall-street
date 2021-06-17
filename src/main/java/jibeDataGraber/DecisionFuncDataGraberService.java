package jibeDataGraber;

import dataBase.mySql.MySql;
import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Map;

public class DecisionFuncDataGraberService extends MyBaseService {

    Map<String, DecisionsFunc> map;

    public DecisionFuncDataGraberService(BASE_CLIENT_OBJECT client) {
        super(client);
    }

    @Override
    public void go() {

        this.map = client.getDecisionsFuncHandler().getMap();

        String q = "SELECT time, value FROM %s ORDER BY time DESC LIMIT 1;";

        for (Map.Entry<String, DecisionsFunc> entry : map.entrySet()) {
            DecisionsFunc func = entry.getValue();

            String query = String.format(q, func.getTable_location());
            ResultSet rs = MySql.select(query);

            while (true) {
                try {
                    if (!rs.next()) break;
                    LocalDateTime dateTime = rs.getTimestamp("time").toLocalDateTime();
                    double value = rs.getDouble("value");

                    func.setValue(value);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    @Override
    public String getName() {
        return "Data graber service";
    }

    @Override
    public int getSleep() {
        return 10000;
    }
}
