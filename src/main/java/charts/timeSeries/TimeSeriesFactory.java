package charts.timeSeries;

import charts.myCharts.Chart_10;
import dataBase.mySql.MySql;
import dataBase.mySql.dataUpdaters.IDataBaseHandler;
import dataBase.props.Props;
import exp.Exp;
import exp.ExpStrings;
import locals.L;
import races.Race_Logic;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Dax;

import java.sql.ResultSet;

public class TimeSeriesFactory {

    public static void main(String[] args) {
        Chart_10 chart_10 = new Chart_10(Dax.getInstance());
        chart_10.createChart();
    }

    // Index
    public static final String INDEX = "INDEX";
    public static final String INDEX_CALC = "INDEX_CALC";
    public static final String INDEX_AVG_3600 = "INDEX_AVG_3600";
    public static final String INDEX_AVG_900 = "INDEX_AVG_900";
    public static final String FUTURE_DAY_MULTIPLY_OP = "FUTURE_DAY_MULTIPLY_OP";
    public static final String INDEX_BID_SERIES = "INDEX_BID_SERIES";
    public static final String INDEX_ASK_SERIES = "INDEX_ASK_SERIES";
    public static final String FUTURE_CALC = "FUTURE_CALC";

    // Baskets
    public static final String BASKETS_CDF = "BASKETS_CDF";
    public static final String BASKETS_MONTH_CDF = "BASKETS_MONTH_CDF";

    // DF
    public static final String DF_2_CDF = "DF_2_CDF";
    public static final String DF_2_ROLL_CDF = "DF_2_ROLL_CDF";
    public static final String DF_7_CDF = "DF_7_CDF";
    public static final String DF_9_CDF = "DF_9_CDF";

    // Week
    public static final String OP_AVG_WEEK_900 = "OP_AVG_WEEK_900";
    public static final String OP_AVG_WEEK_3600 = "OP_AVG_WEEK_3600";
    public static final String OP_AVG_WEEK_240_CONTINUE = "OP_AVG_WEEK_240_CONTINUE";
    public static final String OP_AVG_WEEK_DAILY = "OP_AVG_WEEK_DAILY";

    // Q1
    public static final String OP_AVG_Q1_900 = "OP_AVG_Q1_900";
    public static final String OP_AVG_Q1_3600 = "OP_AVG_Q1_3600";
    public static final String OP_AVG_Q1_14400 = "OP_AVG_Q1_14400";
    public static final String OP_AVG_Q1_DAILY = "OP_AVG_Q1_DAILY";

    // Q2
    public static final String OP_AVG_Q2_900 = "OP_AVG_Q2_900";
    public static final String OP_AVG_Q2_3600 = "OP_AVG_Q2_3600";
    public static final String OP_AVG_Q2_DAILY = "OP_AVG_Q2_DAILY";

    // Exp Week
    public static final String EXP_WEEK_START = "EXP_WEEK_START";
    public static final String DF_2_WEEK = "DF_2_WEEK";
    public static final String DF_2_ROLL_WEEK = "DF_2_ROLL_WEEK";
    public static final String BASKETS_WEEK = "BASKETS_WEEK";
    public static final String DF_9_WEEK = "DF_9_WEEK";
    public static final String DF_WEEK = "DF_WEEK";

    // Exp Month
    public static final String EXP_MONTH_START = "EXP_MONTH_START";
    public static final String DF_2_MONTH = "DF_2_MONTH";
    public static final String DF_2_ROLL_MONTH = "DF_2_ROLL_MONTH";
    public static final String DF_8_MONTH = "DF_8_MONTH";
    public static final String DF_MONTH = "DF_MONTH";

    // Roll Q1 Q2
    public static final String ROLL_Q1_Q2_DAILY = "ROLL_Q1_Q2_DAILY";
    public static final String ROLL_Q1_Q2_900 = "ROLL_Q1_Q2_900";
    public static final String ROLL_Q1_Q2_3600 = "ROLL_Q1_Q2_3600";

    // Roll Week Q1
    public static final String ROLL_WEEK_Q1_DAILY = "ROLL_WEEK_Q1_DAILY";
    public static final String ROLL_WEEK_Q1_900 = "ROLL_WEEK_Q1_900";
    public static final String ROLL_WEEK_Q1_3600 = "ROLL_WEEK_Q1_3600";

    public static final String PRE_DAY_OP_AVG = "PRE_DAY_OP_AVG";

    // Races
    public static final String INDEX_RACES = "INDEX_RACES";
    public static final String Q1_RACES = "Q1_RACES";
    public static final String INDEX_Q1_RACES = "INDEX_Q1_RACES";
    public static final String Q1_QUA_RACES = "Q1_QUA_RACES";
    public static final String Q2_QUA_RACES = "Q2_QUA_RACES";


    public static MyTimeSeries getTimeSeries(String series_type, BASE_CLIENT_OBJECT client) {
        switch (series_type.toUpperCase()) {

            case INDEX_AVG_3600:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return client.getIndex_avg_3600();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.INDEX_AVG_3600_PROD);

                        double val = MySql.Queries.handle_rs(MySql.Queries.get_serie_moving_avg(serie_id, 60, MySql.JIBE_PROD_CONNECTION));
                        setValue(val);
                        client.setIndex_avg_3600(val);
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.INDEX_AVG_3600_PROD);

                        ResultSet rs = MySql.Queries.get_cumulative_avg_serie(serie_id, 60, MySql.JIBE_PROD_CONNECTION);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };

            case INDEX_AVG_900:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return client.getIndex_avg_900();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.INDEX_AVG_900_PROD);

                        double val = MySql.Queries.handle_rs(MySql.Queries.get_serie_moving_avg(serie_id, 15, MySql.JIBE_PROD_CONNECTION));
                        setValue(val);
                        client.setIndex_avg_900(val);
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.INDEX_AVG_900_PROD);

                        ResultSet rs = MySql.Queries.get_cumulative_avg_serie(serie_id, 15, MySql.JIBE_PROD_CONNECTION);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };


            case ROLL_WEEK_Q1_900:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.ROLL_WEEK_Q1_900);

                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW, MySql.JIBE_PROD_CONNECTION));
                        setValue(val);
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.ROLL_WEEK_Q1_900);

                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min(), MySql.JIBE_PROD_CONNECTION);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };


            case ROLL_WEEK_Q1_3600:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.ROLL_WEEK_Q1_3600);

                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW, MySql.JIBE_PROD_CONNECTION));
                        setValue(val);
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.ROLL_WEEK_Q1_3600);

                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min(), MySql.JIBE_PROD_CONNECTION);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };

            case ROLL_WEEK_Q1_DAILY:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.ROLL_WEEK_Q1_DAILY);

                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW, MySql.JIBE_PROD_CONNECTION));
                        setValue(val);
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.ROLL_WEEK_Q1_DAILY);

                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min(), MySql.JIBE_PROD_CONNECTION);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };


            case ROLL_Q1_Q2_900:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.ROLL_Q1_Q2_900);

                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW, MySql.JIBE_PROD_CONNECTION));
                        setValue(val);
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.ROLL_Q1_Q2_900);

                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min(), MySql.JIBE_PROD_CONNECTION);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };

            case ROLL_Q1_Q2_3600:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.ROLL_Q1_Q2_3600);

                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW, MySql.JIBE_PROD_CONNECTION));
                        setValue(val);
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.ROLL_Q1_Q2_3600);

                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min(), MySql.JIBE_PROD_CONNECTION);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };

            case ROLL_Q1_Q2_DAILY:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.ROLL_Q1_Q2_DAILY);

                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW, MySql.JIBE_PROD_CONNECTION));
                        setValue(val);
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.ROLL_Q1_Q2_DAILY);

                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min(), MySql.JIBE_PROD_CONNECTION);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };

            case DF_2_CDF:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_2);
                        setValue(MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.CDF, MySql.JIBE_PROD_CONNECTION)));
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_2);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.CDF, client.getChart_start_min(), MySql.JIBE_PROD_CONNECTION);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {
                    }
                };

            case DF_2_ROLL_CDF:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_2_ROLL);
                        setValue(MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.CDF, MySql.JIBE_PROD_CONNECTION)));
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_2_ROLL);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.CDF, client.getChart_start_min(), MySql.JIBE_PROD_CONNECTION);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {
                    }
                };

            case DF_9_CDF:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_9);
                        setValue(MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.CDF, MySql.JIBE_PROD_CONNECTION)));
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_9);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.CDF, client.getChart_start_min(), MySql.JIBE_PROD_CONNECTION);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_9);
                        int index_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.INDEX_DEV);
                        double data = MySql.Queries.handle_rs(MySql.Queries.get_df_exp_sum(serie_id, index_id, MySql.JIBE_PROD_CONNECTION));
                        setExp_data(data);
                    }
                };

            case OP_AVG_WEEK_240_CONTINUE:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_WEEK_240_CONTINUE);
                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW, MySql.JIBE_PROD_CONNECTION));
                        setValue(val);

                        Exp exp = client.getExps().getExp(ExpStrings.day);
                        exp.setOp_avg_240_continue(val);
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_WEEK_240_CONTINUE);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min(), MySql.JIBE_PROD_CONNECTION);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };

            case OP_AVG_WEEK_900:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_WEEK_900);
                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW, MySql.JIBE_PROD_CONNECTION));
                        setValue(val);

                        Exp exp = client.getExps().getExp(ExpStrings.day);
                        exp.setOp_avg_60(val);
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_WEEK_900);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min(), MySql.JIBE_PROD_CONNECTION);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };

            case OP_AVG_WEEK_3600:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_WEEK_3600);
                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW, MySql.JIBE_PROD_CONNECTION));
                        setValue(val);

                        Exp exp = client.getExps().getExp(ExpStrings.day);
                        exp.setOp_avg_60(val);
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_WEEK_3600);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min(), MySql.JIBE_PROD_CONNECTION);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };


            case OP_AVG_WEEK_DAILY:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_WEEK_DAILY);
                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW, MySql.JIBE_PROD_CONNECTION));
                        setValue(val);

                        Exp exp = client.getExps().getExp(ExpStrings.day);
                        exp.setOp_avg(val);

                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_WEEK_DAILY);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min(), MySql.JIBE_PROD_CONNECTION);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };


            case OP_AVG_Q1_DAILY:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_Q1_DAILY);
                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW, MySql.JIBE_PROD_CONNECTION));
                        setValue(val);

                        Exp exp = client.getExps().getExp(ExpStrings.q1);
                        exp.setOp_avg(val);

                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_Q1_DAILY);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min(), MySql.JIBE_PROD_CONNECTION);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };


            case OP_AVG_Q1_900:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_Q1_900);
                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW, MySql.JIBE_PROD_CONNECTION));
                        setValue(val);

                        Exp exp = client.getExps().getExp(ExpStrings.q1);
                        exp.setOp_avg_15(val);
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_Q1_900);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min(), MySql.JIBE_PROD_CONNECTION);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };


            case OP_AVG_Q1_3600:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_Q1_3600);
                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW, MySql.JIBE_PROD_CONNECTION));
                        setValue(val);

                        Exp exp = client.getExps().getExp(ExpStrings.q1);
                        exp.setOp_avg_60(val);
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_Q1_3600);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min(), MySql.JIBE_PROD_CONNECTION);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };


            case OP_AVG_Q1_14400:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_Q1_14400);
                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW, MySql.JIBE_PROD_CONNECTION));
                        setValue(val);

                        Exp exp = client.getExps().getExp(ExpStrings.q1);
                        exp.setOp_avg_240_continue(val);
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_Q1_14400);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min(), MySql.JIBE_PROD_CONNECTION);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };

            case OP_AVG_Q2_DAILY:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_Q2_DAILY);
                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW, MySql.JIBE_PROD_CONNECTION));
                        setValue(val);

                        Exp exp = client.getExps().getExp(ExpStrings.q2);
                        exp.setOp_avg(val);

                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_Q2_DAILY);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min(), MySql.JIBE_PROD_CONNECTION);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };


            case OP_AVG_Q2_900:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_Q2_900);
                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW, MySql.JIBE_PROD_CONNECTION));
                        setValue(val);

                        Exp exp = client.getExps().getExp(ExpStrings.q2);
                        exp.setOp_avg(val);

                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_Q2_900);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min(), MySql.JIBE_PROD_CONNECTION);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };

            case OP_AVG_Q2_3600:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_Q2_3600);
                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW, MySql.JIBE_PROD_CONNECTION));
                        setValue(val);

                        Exp exp = client.getExps().getExp(ExpStrings.q2);
                        exp.setOp_avg(val);

                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_Q2_3600);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min(), MySql.JIBE_PROD_CONNECTION);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };

            case INDEX:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return client.getIndex();
                    }

                    @Override
                    public void updateData() {

                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.INDEX_DEV);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min(), MySql.JIBE_PROD_CONNECTION);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };
            case INDEX_BID_SERIES:
                return new MyTimeSeries(series_type, client) {
                    @Override
                    public double getValue() {
                        return client.getIndexBid();
                    }

                    @Override
                    public void updateData() {

                    }

                    @Override
                    public void load() {
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };
            case INDEX_ASK_SERIES:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return client.getIndexAsk();
                    }

                    @Override
                    public void updateData() {

                    }

                    @Override
                    public void load() {
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };

            case FUTURE_DAY_MULTIPLY_OP:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        double value = client.getIndex() + client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_WEEK_900).getValue() * 6;
                        return (value);
                    }

                    @Override
                    public void updateData() {

                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.FUT_WEEK_DEV);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min(), MySql.JIBE_PROD_CONNECTION);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };

            case BASKETS_CDF:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.BASKETS_DEV);

                        int basket_up = (int) MySql.Queries.handle_rs(MySql.Queries.get_baskets_up_sum(serie_id));
                        int basket_down = (int) MySql.Queries.handle_rs(MySql.Queries.get_baskets_down_sum(serie_id));
                        client.getBasketFinder_by_stocks().setBasketUp(basket_up);
                        client.getBasketFinder_by_stocks().setBasketDown((int) L.abs(basket_down));
                    }
                    
                    @Override
                    public void load() {
                    }

                    @Override
                    public void load_exp_data() {
                    }
                };

            case INDEX_RACES:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return client.getRacesService().get_race_logic(Race_Logic.RACE_RUNNER_ENUM.Q1_INDEX).get_r_one_points();
                    }


                    @Override
                    public void updateData() {
//                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.INDEX_RACES_PROD);
//                        setValue(MySql.Queries.handle_rs(Objects.requireNonNull(MySql.Queries.get_last_record_mega(serie_id, MySql.CDF, MySql.JIBE_PROD_CONNECTION))));
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.INDEX_RACES_PROD);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.CDF, client.getChart_start_min(), MySql.JIBE_PROD_CONNECTION);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {
                    }
                };

            case Q1_RACES:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return client.getRacesService().get_race_logic(Race_Logic.RACE_RUNNER_ENUM.Q1_INDEX).get_r_two_points();
                    }

                    @Override
                    public void updateData() {
//                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.Q1_RACES_PROD);
//                        setValue(MySql.Queries.handle_rs(Objects.requireNonNull(MySql.Queries.get_last_record_mega(serie_id, MySql.CDF, MySql.JIBE_PROD_CONNECTION))));
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.Q1_RACES_PROD);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.CDF, client.getChart_start_min(), MySql.JIBE_PROD_CONNECTION);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {
                    }
                };

            case INDEX_Q1_RACES:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return client.getRacesService().get_race_logic(Race_Logic.RACE_RUNNER_ENUM.Q1_INDEX).get_sum_points();
                    }


                    @Override
                    public void updateData() {
//                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.INDEX_Q1_RACES_PROD);
//                        setValue(MySql.Queries.handle_rs(Objects.requireNonNull(MySql.Queries.get_last_record_mega(serie_id, MySql.CDF, MySql.JIBE_PROD_CONNECTION))));
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.INDEX_Q1_RACES_PROD);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.CDF, client.getChart_start_min(), MySql.JIBE_PROD_CONNECTION);
                        IDataBaseHandler.loadSerieData(rs, this);

                    }

                    @Override
                    public void load_exp_data() {
                    }
                };

            case Q1_QUA_RACES:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return client.getRacesService().get_race_logic(Race_Logic.RACE_RUNNER_ENUM.Q1_Q2).get_r_one_points();
                    }

                    @Override
                    public void updateData() {
//                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.Q1_QUA_RACES);
//                        setValue(MySql.Queries.handle_rs(Objects.requireNonNull(MySql.Queries.get_last_record_mega(serie_id, MySql.CDF, MySql.JIBE_PROD_CONNECTION))));
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.Q1_QUA_RACES_PROD);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.CDF, client.getChart_start_min(), MySql.JIBE_PROD_CONNECTION);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {
                    }
                };

            case Q2_QUA_RACES:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return client.getRacesService().get_race_logic(Race_Logic.RACE_RUNNER_ENUM.Q1_Q2).get_r_two_points();
                    }

                    @Override
                    public void updateData() {
//                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.Q2_QUA_RACES);
//                        setValue(MySql.Queries.handle_rs(Objects.requireNonNull(MySql.Queries.get_last_record_mega(serie_id, MySql.CDF, MySql.JIBE_PROD_CONNECTION))));
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.Q2_QUA_RACES_PROD);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.CDF, client.getChart_start_min(), MySql.JIBE_PROD_CONNECTION);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {
                    }
                };

            // ---------------------- DF 2 exps -------------------- //
            case DF_2_WEEK:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {

                    }

                    @Override
                    public void load() {
                    }

                    @Override
                    public void load_exp_data() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_2);
                        double data = MySql.Queries.handle_rs(MySql.Queries.get_exp_data(client, serie_id, Props.EXP_WEEK_START, MySql.JIBE_PROD_CONNECTION));
                        setExp_data(data);
                    }
                };


            case DF_2_ROLL_WEEK:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                    }

                    @Override
                    public void load() {
                    }

                    @Override
                    public void load_exp_data() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_2_ROLL_WEEK);
                        double data = MySql.Queries.handle_rs(MySql.Queries.get_exp_data(client, serie_id, Props.EXP_WEEK_START, MySql.JIBE_PROD_CONNECTION));
                        setExp_data(data);
                    }
                };

            case BASKETS_WEEK:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                    }

                    @Override
                    public void load() {
                    }

                    @Override
                    public void load_exp_data() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.BASKETS_DEV);
                        double data = MySql.Queries.handle_rs(MySql.Queries.get_exp_data(client, serie_id, Props.EXP_WEEK_START, MySql.JIBE_PROD_CONNECTION));
                        setExp_data(data);
                    }
                };

            case DF_2_MONTH:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                    }

                    @Override
                    public void load() {
                    }

                    @Override
                    public void load_exp_data() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_2);
                        double data = MySql.Queries.handle_rs(MySql.Queries.get_exp_data(client, serie_id, Props.EXP_MONTH_START, MySql.JIBE_PROD_CONNECTION));
                        setExp_data(data);
                    }
                };

            case DF_2_ROLL_MONTH:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                    }

                    @Override
                    public void load() {
                    }

                    @Override
                    public void load_exp_data() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_2_ROLL_MONTH);
                        double data = MySql.Queries.handle_rs(MySql.Queries.get_exp_data(client, serie_id, Props.EXP_MONTH_START, MySql.JIBE_PROD_CONNECTION));
                        setExp_data(data);
                    }
                };


            // ---------------------- DF 8 exps -------------------- //
            case DF_9_WEEK:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                    }

                    @Override
                    public void load() {
                    }

                    @Override
                    public void load_exp_data() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_9);
                        double data = MySql.Queries.handle_rs(MySql.Queries.get_exp_data(client, serie_id, Props.EXP_WEEK_START, MySql.JIBE_PROD_CONNECTION));
                        setExp_data(data);
                    }
                };

            case DF_8_MONTH:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                    }

                    @Override
                    public void load() {
                    }

                    @Override
                    public void load_exp_data() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_9);
                        double data = MySql.Queries.handle_rs(MySql.Queries.get_exp_data(client, serie_id, Props.EXP_MONTH_START, MySql.JIBE_PROD_CONNECTION));
                        setExp_data(data);
                    }
                };

            case EXP_WEEK_START:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                    }

                    @Override
                    public void load() {
                    }

                    @Override
                    public void load_exp_data() {
                        int index_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.INDEX_DEV);
                        double data = MySql.Queries.handle_rs(MySql.Queries.get_start_exp_mega(index_id, client.getId_name(), Props.EXP_WEEK_START, MySql.JIBE_PROD_CONNECTION));
                        setExp_data(data);
                    }
                };

            case EXP_MONTH_START:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                    }

                    @Override
                    public void load() {
                    }

                    @Override
                    public void load_exp_data() {
                        int index_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.INDEX_DEV);
                        double data = MySql.Queries.handle_rs(MySql.Queries.get_start_exp_mega(index_id, client.getId_name(), Props.EXP_MONTH_START, MySql.JIBE_PROD_CONNECTION));
                        setExp_data(data);
                    }
                };

            case PRE_DAY_OP_AVG:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                    }

                    @Override
                    public void load() {

                    }

                    @Override
                    public void load_exp_data() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.PRE_DAY_OP_AVG);

                        double data = MySql.Queries.handle_rs(MySql.Queries.get_pre_day_op_avg(serie_id, MySql.JIBE_PROD_CONNECTION));
                        client.setPre_day_avg(data);
                        setValue(data);
                    }
                };

            default:
                return null;
        }
    }
}
