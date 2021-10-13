package jibeDataGraber;

import dataBase.mySql.MySql;
import dataBase.mySql.dataUpdaters.IDataBaseHandler;
import locals.L;
import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BidAskCounterGrabberService extends MyBaseService {

    public double avg_5 = 0;
    public double avg_15 = 0;
    public double avg_45 = 0;
    public double avg_day = 0;

    String table_location;
    
    public BidAskCounterGrabberService(BASE_CLIENT_OBJECT client) {
        super(client);
        this.table_location = client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.BID_ASK_COUNTER_TABLE);
    }

    @Override
    public void go() {
        avg_5 = get_avg(5);
        avg_15 = get_avg(15);
        avg_45 = get_avg(45);
        avg_day = get_avg(0);
    }

    private double get_avg(int min) {

        String q;
        String query;

        if (min == 0) {
            q = "select avg(sum.sum) as value " +
                    "from (" +
                    "         select sum(counter.value) over (ORDER BY time RANGE BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) " +
                    "         from %s counter " +
                    "         where time::date = now()::date) sum;";

            query = String.format(q, table_location);

        } else {
            q = "select avg(sum.sum) as value " +
                    "from ( " +
                    "         select time, sum(counter.value) over (ORDER BY time RANGE BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) " +
                    "         from %s counter " +
                    "         where time::date = now()::date) sum " +
                    "where time > now() - interval '%s min';";

            query = String.format(q, table_location, min);
        }

        ResultSet rs = MySql.select(query);


        while (true) {
            try {
                if (!rs.next()) break;

                double value = rs.getDouble("value");
                value = L.floor(value, 10);
                return value;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public String getName() {
        return "Bid ask counter grabber service";
    }

    @Override
    public int getSleep() {
        return 15000;
    }
}
