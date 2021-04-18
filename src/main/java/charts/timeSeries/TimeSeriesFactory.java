package charts.timeSeries;

import dataBase.mySql.MySql;
import dataBase.mySql.dataUpdaters.IDataBaseHandler;
import exp.Exp;
import exp.ExpStrings;
import serverObjects.BASE_CLIENT_OBJECT;

import java.net.UnknownHostException;
import java.sql.ResultSet;

public class TimeSeriesFactory {

    public static final String INDEX_SERIES = "INDEX";
    public static final String FUTURE_BID_ASK_COUNTER = "FUTURE_BID_ASK_COUNTER";
    public static final String INDEX_BID_SERIES = "INDEX_BID";
    public static final String INDEX_ASK_SERIES = "INDEX_ASK";
    public static final String INDEX_BID_ASK_COUNTER_SERIES = "INDEX_BID_ASK_COUNTER";
    public static final String INDEX_RACES_SERIES = "INDEX_RACES";
    public static final String OP_AVG_SERIES = "OP_AVG";
    public static final String OP_AVG_15_SERIES = "OP_AVG_15";
    public static final String BASKETS_SERIES = "BASKETS";

    public static MyTimeSeries getTimeSeries(String series_type, BASE_CLIENT_OBJECT client, Exp exp) {
        switch (series_type.toUpperCase()) {

            case "INDEX":
                return new MyTimeSeries(series_type, client) {
                    @Override
                    public double getData() {
                        return client.getIndex();
                    }

                    @Override
                    public void load_data() {
                        ResultSet rs = MySql.Queries.get_serie(client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.INDEX_TABLE));
                        IDataBaseHandler.loadSerieData(rs, this, "value");
                    }

                };
            case "INDEX_BID":
                return new MyTimeSeries(series_type, client) {
                    @Override
                    public double getData() {
                        return client.getIndexBid();
                    }

                    @Override
                    public void load_data() {
                    }

                };
            case "INDEX_ASK":
                return new MyTimeSeries(series_type, client) {
                    @Override
                    public double getData() {
                        return client.getIndexAsk();
                    }

                    @Override
                    public void load_data() {
                    }

                };

            case "INDEX_BID_ASK_COUNTER":
                return new MyTimeSeries(series_type, client) {
                    @Override
                    public double getData() {
                        return client.getIndexBidAskCounter();
                    }

                    @Override
                    public void load_data() {
                        ResultSet rs = MySql.Queries.cumulative_query(client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.BID_ASK_COUNTER_TABLE), "sum");
                        IDataBaseHandler.loadSerieData(rs, this, "cumu");
                    }
                };
            case "FUTURE_BID_ASK_COUNTER":
                return new MyTimeSeries(series_type, client) {
                    @Override
                    public double getData() {
                        return client.getExps().getExp(ExpStrings.q1).getFutBidAskCounter();
                    }

                    @Override
                    public void load_data() {
                        // TODO
//                        ResultSet rs = MySql.Queries.cumulative_query(client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.BID_ASK_COUNTER_TABLE), "sum");
//                        IDataBaseHandler.loadSerieData(rs, this, "cumu");
                    }
                };
            case "INDEX_RACES":
                return new MyTimeSeries(series_type, client) {
                    @Override
                    public double getData() throws UnknownHostException {
                        return client.getIndexSum();
                    }

                    @Override
                    public void load_data() {
                        ResultSet rs = MySql.Queries.cumulative_query(client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.INDEX_RACES_TABLE), "sum");
                        IDataBaseHandler.loadSerieData(rs, this, "cumu");
                    }
                };

            case "OP_AVG":
                return new MyTimeSeries(series_type, client) {
                    @Override
                    public double getData() throws UnknownHostException {
                        return exp.get_op_avg();
                    }

                    @Override
                    public void load_data() {
                        IDataBaseHandler dataBaseHandler = client.getMySqlService().getDataBaseHandler();

                        String index_table = dataBaseHandler.get_table_loc(IDataBaseHandler.INDEX_TABLE);
                        String fut_table = dataBaseHandler.get_table_loc(exp.getName());
                        ResultSet rs = MySql.Queries.op_avg_cumulative_query(index_table, fut_table);
                        IDataBaseHandler.loadSerieData(rs, this, "cumu");
                    }
                };

            case "OP_AVG_15":
                return new MyTimeSeries(series_type, client) {
                    @Override
                    public double getData() throws UnknownHostException {
                        return exp.get_op_avg(900);
                    }

                    @Override
                    public void load_data() {
                        IDataBaseHandler dataBaseHandler = client.getMySqlService().getDataBaseHandler();

                        String index_table = dataBaseHandler.get_table_loc(IDataBaseHandler.INDEX_TABLE);
                        String fut_table = dataBaseHandler.get_table_loc(exp.getName());
                        ResultSet rs = MySql.Queries.op_avg_cumulative_query(index_table, fut_table, 15);
                        IDataBaseHandler.loadSerieData(rs, this, "cumu");
                    }
                };
            case "BASKETS":
                return new MyTimeSeries(series_type, client) {
                    @Override
                    public double getData() throws UnknownHostException {
                        return client.getBasketFinder().getBaskets();
                    }

                    @Override
                    public void load_data() {
                        ResultSet rs = MySql.Queries.cumulative_query(client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.BASKETS_TABLE), "sum");
                        IDataBaseHandler.loadSerieData(rs, this, "cumu");
                    }
                };
            default:
                return null;
        }

    }
}
