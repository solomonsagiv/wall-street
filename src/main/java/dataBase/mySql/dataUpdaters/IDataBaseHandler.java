package dataBase.mySql.dataUpdaters;

import charts.myChart.MyTimeSeries;
import dataBase.mySql.MySql;
import exp.Exp;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.INDEX_CLIENT_OBJECT;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public abstract class IDataBaseHandler {

    final String DATA_SCHEME = "data";
    final String SAGIV_SCHEME = "sagiv";

    BASE_CLIENT_OBJECT client;

    public IDataBaseHandler(BASE_CLIENT_OBJECT client) {
        this.client = client;
    }

    public abstract void insertData(int sleep);

    public abstract void loadData();

    protected void loadSerieDataAgg(String scheme, String table, MyTimeSeries timeSeries) {
        String query = String.format("SELECT * FROM %s.%s WHERE time::date = now()::date;", scheme, table);
        ResultSet rs = MySql.select(query);

        double valAgg = 0;

        while (true) {
            try {
                if (!rs.next()) break;
                Timestamp timestamp = rs.getTimestamp(1);
                double value = rs.getDouble(2);
                valAgg += value;

                timeSeries.add(timestamp.toLocalDateTime(), valAgg);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    protected void loadSerieData(String scheme, String table, MyTimeSeries timeSeries) {
        String query = String.format("SELECT * FROM %s.%s WHERE time::date = now()::date;", scheme, table);

        System.out.println(query);

        ResultSet rs = MySql.select(query);
        while (true) {
            try {
                if (!rs.next()) break;
                Timestamp timestamp = rs.getTimestamp(1);
                double value = rs.getDouble(2);
                timeSeries.add(timestamp.toLocalDateTime(), value);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    protected void load_op(ResultSet rs, Exp exp) {
        try {
            double sum = rs.getDouble("avg");
            int sum_count = rs.getInt("count");

            exp.set_op_avg(sum, sum_count);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    protected void load_

    protected void load_baskets( ResultSet rs, INDEX_CLIENT_OBJECT client ) {

        while (true) {
            try {
                if (!rs.next()) break;

                int value = rs.getInt("value");

                if (value > 0) {
                    client.getBasketFinder().add_basket_up();
                }

                if (value < 0) {
                    client.getBasketFinder().add_basket_down();
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

    protected void insertListRetro(ArrayList<MyTimeStampObject> list, String scheme, String tableName) {
        if (list.size() > 0) {

            // Create the query
            StringBuilder queryBuiler = new StringBuilder("INSERT INTO %s.%s (time, value) VALUES ");
            int last_item_id = list.get(list.size() - 1).hashCode();
            for (MyTimeStampObject row : list) {
                queryBuiler.append(String.format("(cast('%s' as timestamp with time zone), %s)", row.getInstant(), row.getValue()));
                if (row.hashCode() != last_item_id) {
                    queryBuiler.append(",");
                }
            }
            queryBuiler.append(";");

            String q = String.format(queryBuiler.toString(), scheme, tableName);

            // Insert
            MySql.insert(q, true);

            // Clear the list
            list.clear();
        }
    }

}
