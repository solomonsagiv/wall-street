package charts.timeSeries;

import dataBase.mySql.MySql;
import dataBase.mySql.dataUpdaters.IDataBaseHandler;
import exp.E;
import exp.Exp;
import exp.ExpStrings;
import jibeDataGraber.DecisionsFunc;
import jibeDataGraber.DecisionsFuncFactory;
import serverObjects.BASE_CLIENT_OBJECT;

import java.net.UnknownHostException;
import java.sql.ResultSet;

public class TimeSeriesFactory {

    public static final String INDEX_SERIES = "INDEX";
    public static final String E1_BID_ASK_COUNTER = "E1_BID_ASK_COUNTER";
    public static final String INDEX_BID_SERIES = "INDEX_BID";
    public static final String INDEX_ASK_SERIES = "INDEX_ASK";
    public static final String STOCKS_DELTA_SERIES = "STOCKS_DELTA";
    public static final String INDEX_RACES_SERIES = "INDEX_RACES";
    public static final String OP_AVG_SERIES = "OP_AVG";
    public static final String OP_AVG_DAY_5_SERIES = "OP_AVG_DAY_5";
    public static final String OP_AVG_DAY_15_SERIES = "OP_AVG_DAY_15";
    public static final String OP_AVG_DAY_60_SERIES = "OP_AVG_DAY_60";
    public static final String BASKETS_SERIES = "BASKETS";
    public static final String FUTURE_DELTA = "FUTURE_DELTA";
    public static final String SESSION_4_VERSION_601 = "SESSION_4_VERSION_601";
    public static final String DF_N_DAY = "DF_N_DAY";
    public static final String OP_AVG_240_CONTINUE = "OP_AVG_240_CONTINUE";
    public static final String CORR_15 = "CORR_15";
    public static final String CORR_60 = "CORR_60";
    public static final String DE_CORR_15 = "DE_CORR_15";
    public static final String DE_CORR_60 = "DE_CORR_60";
    public static final String CORR_MIX_CDF = "CORR_MIX_CDF";
    public static final String DE_CORR_MIX_CDF = "DE_CORR_MIX_CDF";
    public static final String CORR_MIX = "CORR_MIX";
    public static final String DE_CORR_MIX = "DE_CORR_MIX";
    public static final String DF_7 = "DF_7";
    public static final String DF_8 = "DF_8";
    public static final String DF_3 = "DF_3";

    public static MyTimeSeries getTimeSeries(String series_type, BASE_CLIENT_OBJECT client, Exp exp) {
        switch (series_type.toUpperCase()) {

            case DF_7:
                return new MyTimeSeries(series_type, client) {
                    @Override
                    public ResultSet load_last_x_time(int minuts) {
                        return null;
                    }

                    @Override
                    public double getData() {
                        return client.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_7).getValue();
                    }

                    @Override
                    public void load() {
                        DecisionsFunc df = client.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_7);
                        ResultSet rs = MySql.Queries.get_df_serie(df.getTable_location(), df.getSession_id(), df.getVersion());
                        IDataBaseHandler.loadSerieData(rs, this);
                    }
                };

            case DF_8:
                return new MyTimeSeries(series_type, client) {
                    @Override
                    public ResultSet load_last_x_time(int minuts) {
                        return null;
                    }

                    @Override
                    public double getData() {
                        return client.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_8).getValue();
                    }

                    @Override
                    public void load() {
                        DecisionsFunc df = client.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_8);
                        ResultSet rs = MySql.Queries.get_df_serie(df.getTable_location(), df.getSession_id(), df.getVersion());
                        IDataBaseHandler.loadSerieData(rs, this);
                    }
                };

            case DF_3:
                return new MyTimeSeries(series_type, client) {
                    @Override
                    public ResultSet load_last_x_time(int minuts) {
                        return null;
                    }

                    @Override
                    public double getData() {
                        return client.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_3).getValue();
                    }

                    @Override
                    public void load() {
                        DecisionsFunc df = client.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_3);
                        ResultSet rs = MySql.Queries.get_df_serie(df.getTable_location(), df.getSession_id(), df.getVersion());
                        IDataBaseHandler.loadSerieData(rs, this);
                    }
                };

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


            case OP_AVG_240_CONTINUE:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public ResultSet load_last_x_time(int minuts) {
                        return null;
                    }

                    @Override
                    public double getData() {
                        return client.getExps().getExp(ExpStrings.day).getOp_avg_240_continue();
                    }

                    @Override
                    public void load() {
                        String index_tabele = client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.INDEX_TABLE);
                        String fut_tabele = client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.FUT_DAY_TABLE);

                        ResultSet rs = MySql.Queries.op_avg_continues_by_rows_serie(index_tabele, fut_tabele, 7000);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }
                };

            case OP_AVG_DAY_5_SERIES:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public ResultSet load_last_x_time(int minuts) {
                        return null;
                    }

                    @Override
                    public double getData() {
                        return client.getExps().getExp(ExpStrings.day).getOp_avg_5();
                    }

                    @Override
                    public void load() {
                        String serie = client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.OP_AVG_DAY_5_TABLE);
                        ResultSet rs = MySql.Queries.get_serie(serie);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }
                };

            case OP_AVG_DAY_15_SERIES:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public ResultSet load_last_x_time(int minuts) {
                        return null;
                    }

                    @Override
                    public double getData() {
                        return client.getExps().getExp(ExpStrings.day).getOp_avg_15();
                    }

                    @Override
                    public void load() {
                        String serie = client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.OP_AVG_DAY_15_TABLE);
                        ResultSet rs = MySql.Queries.get_serie(serie);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }
                };

            case OP_AVG_DAY_60_SERIES:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public ResultSet load_last_x_time(int minuts) {
                        return null;
                    }

                    @Override
                    public double getData() {
                        return client.getExps().getExp(ExpStrings.day).getOp_avg_60();
                    }

                    @Override
                    public void load() {
                        String serie = client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.OP_AVG_DAY_60_TABLE);
                        ResultSet rs = MySql.Queries.get_serie(serie);
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
                        ResultSet rs = MySql.Queries.op_avg_cumulative(index_table, fut_table);
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
