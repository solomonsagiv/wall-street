package charts.timeSeries;

import dataBase.mySql.MySql;
import dataBase.mySql.dataUpdaters.IDataBaseHandler;
import exp.Exp;
import exp.ExpStrings;
import serverObjects.BASE_CLIENT_OBJECT;

import java.sql.ResultSet;

public class TimeSeriesFactory {

    public static final String INDEX = "INDEX";
    public static final String FUTURE_DAY_MULTIPLY_OP = "FUTURE_DAY_MULTIPLY_OP";
    public static final String INDEX_BID_SERIES = "INDEX_BID";
    public static final String INDEX_ASK_SERIES = "INDEX_ASK";
    public static final String STOCKS_DELTA_SERIES = "STOCKS_DELTA";
    public static final String OP_AVG_DAY_5 = "OP_AVG_DAY_5";
    public static final String OP_AVG_DAY_60 = "OP_AVG_DAY_60";
    public static final String BASKETS_CDF = "BASKETS_CDF";
    public static final String OP_AVG_240_CONTINUE = "OP_AVG_240_CONTINUE";

    // DF 2
    public static final String DF_2_RAW = "DF_2_RAW";
    public static final String DF_2_300_RAW = "DF_2_300_RAW";
    public static final String DF_2_3600_RAW = "DF_2_3600_RAW";
    public static final String DF_2_CDF = "DF_2_CDF";

    // DF 7
    public static final String DF_7_RAW = "DF_7_RAW";
    public static final String DF_7_300_RAW = "DF_7_300_RAW";
    public static final String DF_7_3600_RAW = "DF_7_3600_RAW";
    public static final String DF_7_CDF = "DF_7_CDF";

    public static final String OP_AVG_DAY = "OP_AVG_DAY";
    public static final String OP_AVG_WEEK = "OP_AVG_WEEK";
    public static final String OP_AVG_MONTH = "OP_AVG_MONTH";
    public static final String OP_AVG_Q1 = "OP_AVG_Q1";
    public static final String OP_AVG_Q2 = "OP_AVG_Q2";

    public static MyTimeSeries getTimeSeries(String series_type, BASE_CLIENT_OBJECT client) {
        switch (series_type.toUpperCase()) {

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
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.CDF);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }
                };


            case DF_2_RAW:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_2);
                        setValue(MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW)));
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_2);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }
                };

            case DF_2_300_RAW:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_2_300);
                        setValue(MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW)));
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_2_300);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }
                };


            case DF_2_3600_RAW:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_2_3600);
                        setValue(MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW)));
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_2_3600);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW);
                        IDataBaseHandler.loadSerieData(rs, this);
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
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.CDF);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }
                };


            case DF_7_RAW:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_7);
                        setValue(MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW)));
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_7);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }
                };

            case DF_7_300_RAW:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_7_300);
                        setValue(MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW)));
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_7_300);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }
                };

            case DF_7_3600_RAW:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        return super.getValue();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_7_3600);
                        setValue(MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW)));
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_7_3600);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW);
                        IDataBaseHandler.loadSerieData(rs, this);
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
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_240_CONITNUE);
                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW));
                        setValue(val);

                        Exp exp = client.getExps().getExp(ExpStrings.day);
                        exp.setOp_avg_240_continue(val);
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_240_CONITNUE);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW);
                        IDataBaseHandler.loadSerieData(rs, this);
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
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_DAY_5);
                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW));
                        setValue(val);

                        Exp exp = client.getExps().getExp(ExpStrings.day);
                        exp.setOp_avg_5(val);

                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_DAY_5);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW);
                        IDataBaseHandler.loadSerieData(rs, this);
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
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_DAY_60);
                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW));
                        setValue(val);

                        Exp exp = client.getExps().getExp(ExpStrings.day);
                        exp.setOp_avg_60(val);
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_DAY_60);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW);
                        IDataBaseHandler.loadSerieData(rs, this);
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
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW);
                        IDataBaseHandler.loadSerieData(rs, this);
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
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW);
                        IDataBaseHandler.loadSerieData(rs, this);
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
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW);
                        IDataBaseHandler.loadSerieData(rs, this);
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
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW);
                        IDataBaseHandler.loadSerieData(rs, this);
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
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW);
                        IDataBaseHandler.loadSerieData(rs, this);
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
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW);
                        IDataBaseHandler.loadSerieData(rs, this);
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
                };


            case FUTURE_DAY_MULTIPLY_OP:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getValue() {
                        double value = client.getIndex() + client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_DAY_5).getValue();
                        return (value);
                    }


                    @Override
                    public void updateData() {

                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.FUT_DAY);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW);
                        IDataBaseHandler.loadSerieData(rs, this);
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
                        setValue(MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.CDF)));
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.BASKETS);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.CDF);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }
                };
            default:
                return null;
        }
    }
}
