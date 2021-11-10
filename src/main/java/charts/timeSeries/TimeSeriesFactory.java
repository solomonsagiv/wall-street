package charts.timeSeries;

import dataBase.mySql.MySql;
import dataBase.mySql.dataUpdaters.IDataBaseHandler;
import exp.E;
import exp.Exp;
import exp.ExpStrings;
import jibeDataGraber.DecisionsFuncFactory;
import serverObjects.BASE_CLIENT_OBJECT;
import java.net.UnknownHostException;
import java.sql.ResultSet;

public class TimeSeriesFactory {

    public static final String INDEX_SERIES = "INDEX";
    public static final String FUTURE_BID_ASK_COUNTER = "FUTURE_BID_ASK_COUNTER";
    public static final String INDEX_BID_SERIES = "INDEX_BID";
    public static final String INDEX_ASK_SERIES = "INDEX_ASK";
    public static final String INDEX_BID_ASK_COUNTER_SERIES = "INDEX_BID_ASK_COUNTER";
    public static final String INDEX_BID_ASK_COUNTER_SERIES_5 = "INDEX_BID_ASK_COUNTER_5";
    public static final String INDEX_BID_ASK_COUNTER_SERIES_15 = "INDEX_BID_ASK_COUNTER_15";
    public static final String INDEX_BID_ASK_COUNTER_SERIES_45 = "INDEX_BID_ASK_COUNTER_45";
    public static final String INDEX_BID_ASK_COUNTER_SERIES_DAY = "INDEX_BID_ASK_COUNTER_DAY";
    public static final String STOCKS_DELTA_SERIES = "STOCKS_DELTA";
    public static final String INDEX_RACES_SERIES = "INDEX_RACES";
    public static final String OP_AVG_SERIES = "OP_AVG";
    public static final String OP_AVG_15_SERIES = "OP_AVG_15";
    public static final String OP_AVG_HOUR_SERIES = "OP_AVG_HOUR";
    public static final String BASKETS_SERIES = "BASKETS";
    public static final String SPEED_900 = "SPEED_900";
    public static final String ACC_900 = "ACC_900";
    public static final String ACC_300 = "ACC_300";
    public static final String FUTURE_DELTA = "FUTURE_DELTA";
    public static final String SESSION_4_VERSION_601 = "SESSION_4_VERSION_601";
    public static final String DF_5 = "DF_5";
    public static final String DF_N_5 = "DF_N_5";


    public static MyTimeSeries getTimeSeries(String series_type, BASE_CLIENT_OBJECT client, Exp exp) {

        switch (series_type.toUpperCase()) {

            case SPEED_900:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public ResultSet load_last_x_time(int minuts) {
                        String table_location = DecisionsFuncFactory.get_decision_func(client, DecisionsFuncFactory.SPEED_900).getTable_location();
                        ResultSet rs = MySql.Queries.get_last_x_time_of_series(table_location, minuts);
                        return rs;
                    }

                    @Override
                    public double getData() {
                        return client.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.SPEED_900).getValue();
                    }

                    @Override
                    public void load() {

                    }
                };

            case ACC_900:
                return new MyTimeSeries(series_type, client) {
                    @Override
                    public ResultSet load_last_x_time(int minuts) {
                        String table_location = DecisionsFuncFactory.get_decision_func(client, DecisionsFuncFactory.ACC_900).getTable_location();
                        ResultSet rs = MySql.Queries.get_last_x_time_of_series(table_location, minuts);
                        return rs;
                    }

                    @Override
                    public double getData() {
                        return client.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.ACC_900).getValue();
                    }

                    @Override
                    public void load() {

                    }
                };
            case ACC_300:
                return new MyTimeSeries(series_type, client) {
                    @Override
                    public ResultSet load_last_x_time(int minuts) {
                        String table_location = DecisionsFuncFactory.get_decision_func(client, DecisionsFuncFactory.ACC_300).getTable_location();
                        ResultSet rs = MySql.Queries.get_last_x_time_of_series(table_location, minuts);
                        return rs;
                    }

                    @Override
                    public double getData() {
                        return client.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.ACC_300).getValue();
                    }

                    @Override
                    public void load() {

                    }
                };

                // DF 5
            case DF_5:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public ResultSet load_last_x_time(int minuts) {
                        String table_location = DecisionsFuncFactory.get_decision_func(client, DecisionsFuncFactory.DF_5).getTable_location();
                        ResultSet rs = MySql.Queries.get_last_x_time_of_series_cumulative(table_location, minuts);
                        return rs;
                    }

                    @Override
                    public double getData() {
                        return client.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_5).getValue();
                    }

                    @Override
                    public void load() {

                    }
                };

                // DF N 5
            case DF_N_5:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public ResultSet load_last_x_time(int minuts) {
                        String table_location = DecisionsFuncFactory.get_decision_func(client, DecisionsFuncFactory.DF_N_5).getTable_location();
                        ResultSet rs = MySql.Queries.get_last_x_time_of_series_cumulative(table_location, minuts);
                        return rs;
                    }

                    @Override
                    public double getData() {
                        return client.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_N_5).getValue();
                    }

                    @Override
                    public void load() {

                    }
                };

            // BID ASK COUNTER AVG 5
            case INDEX_BID_ASK_COUNTER_SERIES_5:
                return new MyTimeSeries(series_type, client) {
                    @Override
                    public ResultSet load_last_x_time(int minuts) {
                        return null;
                    }

                    @Override
                    public double getData() {
                        return client.getBidAskCounterGrabberService().avg_5;
                    }

                    @Override
                    public void load() {

                        String table_location = client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.BID_ASK_COUNTER_TABLE);

                        ResultSet rs = MySql.Queries.cumulative_avg_from_cdf(table_location, 5);
                        IDataBaseHandler.loadSerieData(rs, this);

                    }
                };
            // BID ASK COUNTER AVG 15
            case INDEX_BID_ASK_COUNTER_SERIES_15:
                return new MyTimeSeries(series_type, client) {
                    @Override
                    public ResultSet load_last_x_time(int minuts) {
                        return null;
                    }

                    @Override
                    public double getData() {
                        return client.getBidAskCounterGrabberService().avg_15;
                    }

                    @Override
                    public void load() {
                        String table_location = client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.BID_ASK_COUNTER_TABLE);

                        ResultSet rs = MySql.Queries.cumulative_avg_from_cdf(table_location, 15);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }
                };
            // BID ASK COUNTER AVG 45
            case INDEX_BID_ASK_COUNTER_SERIES_45:
                return new MyTimeSeries(series_type, client) {
                    @Override
                    public ResultSet load_last_x_time(int minuts) {
                        return null;
                    }

                    @Override
                    public double getData() {
                        return client.getBidAskCounterGrabberService().avg_45;
                    }

                    @Override
                    public void load() {
                        String table_location = client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.BID_ASK_COUNTER_TABLE);

                        ResultSet rs = MySql.Queries.cumulative_avg_from_cdf(table_location, 45);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }
                };
            // BID ASK COUNTER AVG DAY
            case INDEX_BID_ASK_COUNTER_SERIES_DAY:
                return new MyTimeSeries(series_type, client) {
                    @Override
                    public ResultSet load_last_x_time(int minuts) {
                        return null;
                    }

                    @Override
                    public double getData() {
                        return client.getBidAskCounterGrabberService().avg_day;
                    }

                    @Override
                    public void load() {

                    }
                };
            // Q1 DELTA
            case FUTURE_DELTA:
                return new MyTimeSeries(series_type, client) {
                    @Override
                    public ResultSet load_last_x_time(int minuts) {
                        return null;
                    }

                    @Override
                    public double getData() {
                        E e = (E) exp;
                        return e.getDelta();
                    }

                    @Override
                    public void load() {
                        String table_location = client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.FUT_DELTA_TABLE);
                        ResultSet rs = MySql.Queries.cumulative_sum_query(table_location);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }
                };

            case INDEX_SERIES:
                return new MyTimeSeries(series_type, client) {
                    @Override
                    public ResultSet load_last_x_time(int minuts) {
                        return null;
                    }

                    @Override
                    public double getData() {
                        return client.getIndex();
                    }

                    @Override
                    public void load() {
                        ResultSet rs = MySql.Queries.get_serie(client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.INDEX_TABLE));
                        IDataBaseHandler.loadSerieData(rs, this);
                    }
                };
            case INDEX_BID_SERIES:
                return new MyTimeSeries(series_type, client) {
                    @Override
                    public ResultSet load_last_x_time(int minuts) {
                        return null;
                    }

                    @Override
                    public double getData() {
                        return client.getIndexBid();
                    }

                    @Override
                    public void load() {
                    }
                };
            case INDEX_ASK_SERIES:
                return new MyTimeSeries(series_type, client) {
                    @Override
                    public ResultSet load_last_x_time(int minuts) {
                        return null;
                    }

                    @Override
                    public double getData() {
                        return client.getIndexAsk();
                    }

                    @Override
                    public void load() {
                    }
                };
            case INDEX_BID_ASK_COUNTER_SERIES:
                return new MyTimeSeries(series_type, client) {
                    @Override
                    public ResultSet load_last_x_time(int minuts) {
                        return null;
                    }

                    @Override
                    public double getData() {
                        return client.getIndexBidAskCounter();
                    }

                    @Override
                    public void load() {
                        ResultSet rs = MySql.Queries.cumulative_sum_query(client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.BID_ASK_COUNTER_TABLE));
                        IDataBaseHandler.loadSerieData(rs, this);
                    }
                };
            case FUTURE_BID_ASK_COUNTER:
                return new MyTimeSeries(series_type, client) {
                    @Override
                    public ResultSet load_last_x_time(int minuts) {
                        return null;
                    }

                    @Override
                    public double getData() {
                        return client.getExps().getExp(ExpStrings.q1).getFut_bid_ask_counter();
                    }

                    @Override
                    public void load() {
                        // TODO
                        ResultSet rs = MySql.Queries.cumulative_sum_query(client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.BID_ASK_COUNTER_TABLE));
                        IDataBaseHandler.loadSerieData(rs, this);
                    }
                };
            case OP_AVG_SERIES:
                return new MyTimeSeries(series_type + "_" + exp.getName().toUpperCase(), client) {
                    @Override
                    public ResultSet load_last_x_time(int minuts) {
                        return null;
                    }

                    @Override
                    public double getData() throws UnknownHostException {
                        return exp.get_op_avg();
                    }

                    @Override
                    public void load() {
                        IDataBaseHandler dataBaseHandler = client.getMySqlService().getDataBaseHandler();

                        String index_table = dataBaseHandler.get_table_loc(IDataBaseHandler.INDEX_TABLE);
                        String fut_table = dataBaseHandler.get_table_loc(exp.getName());
                        ResultSet rs = MySql.Queries.op_avg_cumulative_query(index_table, fut_table);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }
                };

            case OP_AVG_15_SERIES:
                return new MyTimeSeries(series_type + "_" + exp.getName().toUpperCase(), client) {
                    @Override
                    public ResultSet load_last_x_time(int minuts) {
                        return null;
                    }

                    @Override
                    public double getData() throws UnknownHostException {
                        return exp.get_op_avg(900);
                    }

                    @Override
                    public void load() {
                        IDataBaseHandler dataBaseHandler = client.getMySqlService().getDataBaseHandler();

                        String index_table = dataBaseHandler.get_table_loc(IDataBaseHandler.INDEX_TABLE);
                        String fut_table = dataBaseHandler.get_table_loc(exp.getName());
                        ResultSet rs = MySql.Queries.op_avg_cumulative_query(index_table, fut_table, 15);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }
                };
            case OP_AVG_HOUR_SERIES:
                return new MyTimeSeries(series_type + "_" + exp.getName().toUpperCase(), client) {
                    @Override
                    public ResultSet load_last_x_time(int minuts) {
                        return null;
                    }

                    @Override
                    public double getData() throws UnknownHostException {
                        return exp.get_op_avg(3600);
                    }

                    @Override
                    public void load() {
                        IDataBaseHandler dataBaseHandler = client.getMySqlService().getDataBaseHandler();

                        String index_table = dataBaseHandler.get_table_loc(IDataBaseHandler.INDEX_TABLE);
                        String fut_table = dataBaseHandler.get_table_loc(exp.getName());
                        ResultSet rs = MySql.Queries.op_avg_cumulative_query(index_table, fut_table, 60);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }
                };

            case STOCKS_DELTA_SERIES:
                return new MyTimeSeries(series_type, client) {
                    @Override
                    public ResultSet load_last_x_time(int minuts) {
                        return null;
                    }

                    @Override
                    public double getData() {
                        return client.getStocksHandler().getDelta();
                    }

                    @Override
                    public void load() {
                        ResultSet rs = MySql.Queries.cumulative_sum_query(client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.INDEX_DELTA_TABLE));
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                };

            case BASKETS_SERIES:
                return new MyTimeSeries(series_type, client) {
                    @Override
                    public ResultSet load_last_x_time(int minuts) {
                        return null;
                    }

                    @Override
                    public double getData() throws UnknownHostException {
                        return client.getBasketFinder().getBaskets();
                    }

                    @Override
                    public void load() {
                        ResultSet rs = MySql.Queries.cumulative_sum_query(client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.BASKETS_TABLE));
                        IDataBaseHandler.loadSerieData(rs, this);
                    }
                };
            default:
                return null;
        }
    }
}
