package charts.timeSeries;

import charts.myCharts.Chart_10;
import dataBase.mySql.MySql;
import dataBase.mySql.dataUpdaters.IDataBaseHandler;
import dataBase.props.Props;
import exp.Exp;
import exp.ExpStrings;
import locals.L;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Dax;

import java.sql.ResultSet;

public class TimeSeriesFactory {

    public static final String ROLL_WEEK_MONTH_3600 = "ROLL_WEEK_MONTH_3600";
    public static final String ROLL_WEEK_MONTH_900 = "ROLL_WEEK_MONTH_900";

    public static void main(String[] args) {
        Chart_10 chart_10 = new Chart_10(Dax.getInstance());
        chart_10.createChart();
    }

    public static final String INDEX = "INDEX";
    public static final String FUTURE_DAY_MULTIPLY_OP = "FUTURE_DAY_MULTIPLY_OP";
    public static final String INDEX_BID_SERIES = "INDEX_BID";
    public static final String INDEX_ASK_SERIES = "INDEX_ASK";
    public static final String STOCKS_DELTA_SERIES = "STOCKS_DELTA";
    public static final String OP_AVG_DAY_1 = "OP_AVG_DAY_1";
    public static final String OP_AVG_DAY_5 = "OP_AVG_DAY_5";
    public static final String OP_AVG_DAY_15 = "OP_AVG_DAY_15";
    public static final String OP_AVG_DAY_60 = "OP_AVG_DAY_60";

    public static final String OP_AVG_Q1_15 = "OP_AVG_Q1_15";
    public static final String OP_AVG_Q1_60 = "OP_AVG_Q1_60";

    public static final String OP_AVG_MONTH_15 = "OP_AVG_MONTH_15";
    public static final String OP_AVG_MONTH_60 = "OP_AVG_MONTH_60";

    public static final String BASKETS_CDF = "BASKETS_CDF";
    public static final String BASKETS_MONTH_CDF = "BASKETS_MONTH_CDF";
    public static final String OP_AVG_240_CONTINUE = "OP_AVG_240_CONTINUE";

    // DF 2
    public static final String DF_2_CDF = "DF_2_CDF";
    public static final String DF_2_ROLL_CDF = "DF_2_ROLL_CDF";

    // DF 7
    public static final String DF_7_CDF = "DF_7_CDF";

    // DF 8
    public static final String DF_9_CDF = "DF_8_CDF";
    public static final String DF_8_RAW_900 = "DF_8_RAW_900";
    public static final String DF_8_RAW_3600 = "DF_8_RAW_3600";

    public static final String OP_AVG_DAY = "OP_AVG_DAY";
    public static final String OP_AVG_WEEK = "OP_AVG_WEEK";
    public static final String OP_AVG_MONTH = "OP_AVG_MONTH";
    public static final String OP_AVG_Q1 = "OP_AVG_Q1";
    public static final String OP_AVG_Q2 = "OP_AVG_Q2";
    public static final String OP_AVG_Q2_15 = "OP_AVG_Q2_15";

    // Exp
    // Week
    public static final String DF_2_WEEK = "DF_2_WEEK";
    public static final String DF_2_ROLL_WEEK = "DF_2_ROLL_WEEK";
    public static final String BASKETS_WEEK = "BASKETS_WEEK";
    public static final String DF_8_WEEK = "DF_8_WEEK";
    public static final String DF_WEEK = "DF_WEEK";

    // Month
    public static final String DF_2_MONTH = "DF_2_MONTH";
    public static final String DF_2_ROLL_MONTH = "DF_2_ROLL_MONTH";
    public static final String DF_8_MONTH = "DF_8_MONTH";
    public static final String DF_MONTH = "DF_MONTH";

    public static final String DF_WEIGHTED = "DF_WEIGHTED";

    // Q1
    public static final String DF_2_Q1 = "DF_2_Q1";
    public static final String DF_7_Q1 = "DF_7_Q1";
    public static final String DF_8_Q1 = "DF_8_Q1";

    // EXP move
    public static final String EXP_WEEK_START = "MOVE_WEEK";
    public static final String EXP_MONTH_START = "MOVE_MONTH";
    public static final String EXP_Q1_START = "MOVE_Q1";

    public static final String STD_MOVE = "STD_MOVE";

    // Relative
    public static final String DF_8_RELATIVE = "DF_8_RELATIVE";
    public static final String WINDOW_SIZE = "WINDOW_SIZE";

    public static final String STOXX_DF_8_CDF = "STOCKX_DF_8_CDF";
    public static final String STOXX_RELATIVE = "STOCKX_RELATIVE";

    public static final String CAC_DF_8_CDF = "CAC_DF_8_CDF";
    public static final String CAC_RELATIVE = "CAC_RELATIVE";

    public static final String DOW_DF_8_CDF = "DOW_DF_8_CDF";
    public static final String DOW_RELATIVE = "DOW_RELATIVE";

    public static final String OP_AVG_Q1_14400 = "OP_AVG_Q1_14400";

    public static final String CAC_OP_AVG_900 = "CAC_OP_AVG_900";
    public static final String CAC_OP_AVG_3600 = "CAC_OP_AVG_3600";

    public static final String STOXX_OP_AVG_900 = "STOXX_OP_AVG_900";
    public static final String STOXX_OP_AVG_3600 = "STOXX_OP_AVG_3600";

    // Roll
    public static final String ROLL_3600 = "ROLL_3600";
    public static final String ROLL_900 = "ROLL_900";
    public static final String ROLL_60 = "ROLL_60";
    public static final String ROLL_300 = "ROLL_300";

    // Index avg
    public static final String INDEX_AVG_3600 = "INDEX_AVG_3600";
    public static final String INDEX_AVG_900 = "INDEX_AVG_900";


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
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.INDEX_AVG_3600);

                        double val = MySql.Queries.handle_rs(MySql.Queries.get_serie_moving_avg(serie_id, 60));
                        setValue(val);
                        client.setIndex_avg_3600(val);

                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.INDEX_AVG_3600);

                        ResultSet rs = MySql.Queries.get_cumulative_avg_serie(serie_id, 60);
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
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.INDEX_AVG_900);

                        double val = MySql.Queries.handle_rs(MySql.Queries.get_serie_moving_avg(serie_id, 15));
                        setValue(val);
                        client.setIndex_avg_900(val);
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.INDEX_AVG_900);

                        ResultSet rs = MySql.Queries.get_cumulative_avg_serie(serie_id, 15);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };

            case ROLL_WEEK_MONTH_3600:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.ROLL_WEEK_MONTH_3600);

                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW));
                        setValue(val);
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.ROLL_WEEK_MONTH_3600);

                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min());
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };


            case ROLL_WEEK_MONTH_900:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.ROLL_WEEK_MONTH_900);

                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW));
                        setValue(val);
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.ROLL_WEEK_MONTH_900);

                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min());
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };

            case ROLL_60:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.ROLL_60);

                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW));
                        setValue(val);
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.ROLL_60);

                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min());
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };

            case ROLL_300:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.ROLL_300);

                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW));
                        setValue(val);
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.ROLL_300);

                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min());
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };


            case ROLL_900:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.ROLL_900);

                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW));
                        setValue(val);
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.ROLL_900);

                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min());
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };


            case ROLL_3600:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.ROLL_3600);

                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW));
                        setValue(val);
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.ROLL_3600);

                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min());
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };


            case OP_AVG_MONTH_15:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_MONTH_15);

                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW));
                        setValue(val);
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_MONTH_15);

                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min());
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };

            case OP_AVG_MONTH_60:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_MONTH_60);

                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW));
                        setValue(val);
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_MONTH_60);

                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min());
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };

            case STOXX_DF_8_CDF:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.STOXX_DF_8_ID);
                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.CDF));
                        setValue(val);
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.STOXX_DF_8_ID);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.CDF, client.getChart_start_min());
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };

            case STOXX_RELATIVE:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.STOXX_RELATIVE_ID);
                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW));
                        setValue(val);
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.STOXX_RELATIVE_ID);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min());
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };

            case CAC_DF_8_CDF:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.CAC_DF_8_ID);
                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.CDF));
                        setValue(val);
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.CAC_DF_8_ID);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.CDF, client.getChart_start_min());
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };

            case CAC_RELATIVE:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.CAC_RELATIVE_ID);

                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW));
                        setValue(val);
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.CAC_RELATIVE_ID);

                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min());
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };


            case CAC_OP_AVG_900:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.CAC_OP_AVG_900);

                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW));
                        setValue(val);
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.CAC_OP_AVG_900);

                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min());
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };

            case CAC_OP_AVG_3600:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.CAC_OP_AVG_3600);

                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW));
                        setValue(val);
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.CAC_OP_AVG_3600);

                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min());
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };

            case STOXX_OP_AVG_900:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.STOXX_OP_AVG_900);

                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW));
                        setValue(val);
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.STOXX_OP_AVG_900);

                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min());
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };


            case STOXX_OP_AVG_3600:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.STOXX_OP_AVG_3600);

                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW));
                        setValue(val);
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.STOXX_OP_AVG_3600);

                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min());
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };


            case DOW_DF_8_CDF:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DOW_DF_8_ID);

                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.CDF));
                        setValue(val);
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DOW_DF_8_ID);

                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.CDF, client.getChart_start_min());
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };

            case DOW_RELATIVE:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DOW_RELATIVE_ID);
                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW));
                        setValue(val);
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DOW_RELATIVE_ID);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min());
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
                        setValue(MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.CDF)));
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_2_ROLL);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.CDF, client.getChart_start_min());
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
                        setValue(MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.CDF)));
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_2);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.CDF, client.getChart_start_min());
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {
                    }
                };

            case DF_7_CDF:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_7);
                        setValue(MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.CDF)));
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_7);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.CDF, client.getChart_start_min());
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_7);
                        int index_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.INDEX);
                        double data = MySql.Queries.handle_rs(MySql.Queries.get_df_exp_sum(serie_id, index_id));
                        setExp_data(data);
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
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_8);
                        setValue(MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.CDF)));
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_8);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.CDF, client.getChart_start_min());
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_8);
                        int index_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.INDEX);
                        double data = MySql.Queries.handle_rs(MySql.Queries.get_df_exp_sum(serie_id, index_id));
                        setExp_data(data);
                    }
                };


            case DF_8_RAW_900:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_8);
                        setValue(MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW)));
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_8);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min());
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {
                    }
                };

            case DF_8_RAW_3600:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_8_3600);
                        setValue(MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW)));
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_8_3600);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min());
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {
                    }
                };


            case OP_AVG_240_CONTINUE:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_240_CONTINUE);
                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW));
                        setValue(val);

                        Exp exp = client.getExps().getExp(ExpStrings.day);
                        exp.setOp_avg_240_continue(val);
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_240_CONTINUE);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min());
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };

            case OP_AVG_DAY_1:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_1);
                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW));
                        setValue(val);

                        Exp exp = client.getExps().getExp(ExpStrings.day);
                        exp.setOp_avg_5(val);

                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_1);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min());
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };


            case OP_AVG_DAY_5:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_5);
                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW));
                        setValue(val);

                        Exp exp = client.getExps().getExp(ExpStrings.day);
                        exp.setOp_avg_5(val);

                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_5);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min());
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };


            case OP_AVG_DAY_15:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_15);
                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW));
                        setValue(val);

                        Exp exp = client.getExps().getExp(ExpStrings.day);
                        exp.setOp_avg_60(val);
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_15);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min());
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };

            case OP_AVG_DAY_60:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_60);
                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW));
                        setValue(val);

                        Exp exp = client.getExps().getExp(ExpStrings.day);
                        exp.setOp_avg_60(val);
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_60);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min());
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };


            case OP_AVG_DAY:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_DAY);
                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW));
                        setValue(val);

                        Exp exp = client.getExps().getExp(ExpStrings.day);
                        exp.setOp_avg(val);

                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_DAY);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min());
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };


            case OP_AVG_WEEK:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_WEEK);
                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW));
                        setValue(val);

                        Exp exp = client.getExps().getExp(ExpStrings.week);
                        exp.setOp_avg(val);

                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_WEEK);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min());
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };


            case OP_AVG_MONTH:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_MONTH);
                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW));
                        setValue(val);

                        Exp exp = client.getExps().getExp(ExpStrings.month);
                        exp.setOp_avg(val);

                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_MONTH);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min());
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };

            case OP_AVG_Q1:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_Q1);
                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW));
                        setValue(val);

                        Exp exp = client.getExps().getExp(ExpStrings.q1);
                        exp.setOp_avg(val);

                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_Q1);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min());
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };


            case OP_AVG_Q1_15:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_Q1_15);
                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW));
                        setValue(val);

                        Exp exp = client.getExps().getExp(ExpStrings.q1);
                        exp.setOp_avg_15(val);
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_Q1_15);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min());
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };


            case OP_AVG_Q1_60:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_Q1_60);
                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW));
                        setValue(val);

                        Exp exp = client.getExps().getExp(ExpStrings.q1);
                        exp.setOp_avg_60(val);
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_Q1_60);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min());
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
                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW));
                        setValue(val);

                        Exp exp = client.getExps().getExp(ExpStrings.q1);
                        exp.setOp_avg_240_continue(val);
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_Q1_14400);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min());
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };

            case OP_AVG_Q2:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_Q2);
                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW));
                        setValue(val);

                        Exp exp = client.getExps().getExp(ExpStrings.q2);
                        exp.setOp_avg(val);

                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_Q2);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min());
                        IDataBaseHandler.loadSerieData(rs, this);
                    }

                    @Override
                    public void load_exp_data() {

                    }
                };


            case OP_AVG_Q2_15:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_Q2_15);
                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW));
                        setValue(val);

                        Exp exp = client.getExps().getExp(ExpStrings.q2);
                        exp.setOp_avg(val);

                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_Q2_15);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min());
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
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.INDEX);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min());
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
                        double value = client.getIndex() + client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_DAY_15).getValue() * 6;
                        return (value);
                    }

                    @Override
                    public void updateData() {

                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.FUT_DAY);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW, client.getChart_start_min());
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
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.BASKETS);

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
                        double data = MySql.Queries.handle_rs(MySql.Queries.get_exp_data(client, serie_id, Props.EXP_WEEK_START));
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
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_2_ROLL);
                        double data = MySql.Queries.handle_rs(MySql.Queries.get_exp_data(client, serie_id, Props.EXP_WEEK_START));
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
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.BASKETS);
                        double data = MySql.Queries.handle_rs(MySql.Queries.get_exp_data(client, serie_id, Props.EXP_WEEK_START));
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
                        double data = MySql.Queries.handle_rs(MySql.Queries.get_exp_data(client, serie_id, Props.EXP_MONTH_START));
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
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_2_ROLL);
                        double data = MySql.Queries.handle_rs(MySql.Queries.get_exp_data(client, serie_id, Props.EXP_MONTH_START));
                        setExp_data(data);
                    }
                };


            // ---------------------- DF 7 exps -------------------- //
            case DF_7_Q1:
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
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_7);
                        double data = MySql.Queries.handle_rs(MySql.Queries.get_exp_data(client, serie_id, Props.EXP_Q1_START));
                        setExp_data(data);
                    }
                };

            // ---------------------- DF 8 exps -------------------- //
            case DF_8_WEEK:
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
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_8);
                        double data = MySql.Queries.handle_rs(MySql.Queries.get_exp_data(client, serie_id, Props.EXP_WEEK_START));
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
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_8);
                        double data = MySql.Queries.handle_rs(MySql.Queries.get_exp_data(client, serie_id, Props.EXP_MONTH_START));
                        setExp_data(data);
                    }
                };

            case DF_8_Q1:
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
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_8);
                        double data = MySql.Queries.handle_rs(MySql.Queries.get_exp_data(client, serie_id, Props.EXP_Q1_START));
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
                        int index_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.INDEX);
                        double data = MySql.Queries.handle_rs(MySql.Queries.get_start_exp_mega(index_id, client.getId_name(), Props.EXP_WEEK_START));
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
                        int index_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.INDEX);
                        double data = MySql.Queries.handle_rs(MySql.Queries.get_start_exp_mega(index_id, client.getId_name(), Props.EXP_MONTH_START));
                        setExp_data(data);
                    }
                };

            case EXP_Q1_START:
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
                        int index_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.INDEX);
                        double data = MySql.Queries.handle_rs(MySql.Queries.get_start_exp_mega(index_id, client.getId_name(), Props.EXP_Q1_START));
                        setExp_data(data);
                    }
                };

            default:
                return null;
        }
    }
}
