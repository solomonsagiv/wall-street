package charts.timeSeries;

import dataBase.mySql.MySql;
import dataBase.mySql.dataUpdaters.IDataBaseHandler;
import exp.Exp;
import exp.ExpStrings;
import serverObjects.BASE_CLIENT_OBJECT;
import java.sql.ResultSet;

public class TimeSeriesFactory {

    public static final String INDEX_SERIES = "INDEX";
    public static final String INDEX_BID_SERIES = "INDEX_BID";
    public static final String INDEX_ASK_SERIES = "INDEX_ASK";
    public static final String STOCKS_DELTA_SERIES = "STOCKS_DELTA";
    public static final String OP_AVG_DAY_5 = "OP_AVG_DAY_5";
    public static final String OP_AVG_DAY_60 = "OP_AVG_DAY_60";
    public static final String BASKETS = "BASKETS";
    public static final String OP_AVG_240_CONTINUE = "OP_AVG_240_CONTINUE";
    public static final String DF_7 = "DF_7";
    public static final String DF_2 = "DF_2";
    public static final String DF_7_300 = "DF_7_300";
    public static final String DF_7_900 = "DF_7_900";
    public static final String DF_7_3600 = "DF_7_3600";

    public static final String OP_AVG_DAY = "OP_AVG_DAY";
    public static final String OP_AVG_WEEK = "OP_AVG_WEEK";
    public static final String OP_AVG_MONTH = "OP_AVG_MONTH";
    public static final String OP_AVG_Q1 = "OP_AVG_Q1";
    public static final String OP_AVG_Q2 = "OP_AVG_Q2";

    public static MyTimeSeries getTimeSeries(String series_type, BASE_CLIENT_OBJECT client) {
        switch (series_type.toUpperCase()) {

            case DF_2:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getData() {
                        return super.getData();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_2);
                        setData(MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.CDF)));
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_2);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.CDF);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }
                };

            case DF_7:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getData() {
                        return super.getData();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_7);
                        setData(MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.CDF)));
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_7);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.CDF);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }
                };

            case DF_7_300:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getData() {
                        return super.getData();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_7_300);
                        setData(MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.CDF)));
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_7_300);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.CDF);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }
                };
                
            case DF_7_900:
                return new MyTimeSeries(series_type, client) {

                    public double getData() {
                        return super.getData();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_7_900);
                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.CDF));
                        setData(val);
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_7_900);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.CDF);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }
                };

            case DF_7_3600:
                return new MyTimeSeries(series_type, client) {
                    public double getData() {
                        return super.getData();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_7_3600);
                        setData(MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.CDF)));
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_7_3600);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.CDF);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }
                };

            case OP_AVG_240_CONTINUE:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getData() {
                        return super.getData();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_240_CONITNUE);
                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW));
                        setData(val);

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
                    public double getData() {
                        return super.getData();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_DAY_5);
                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW));
                        setData(val);

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
                    public double getData() {
                        return super.getData();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_DAY_60);
                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW));
                        setData(val);

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
                    public double getData() {
                        Exp exp = client.getExps().getExp(ExpStrings.day);
                        return exp.getOp_avg();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_DAY);
                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW));
                        setData(val);

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
                    public double getData() {
                        return super.getData();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_WEEK);
                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW));
                        setData(val);

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
                    public double getData() {
                        return super.getData();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_MONTH);
                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW));
                        setData(val);

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
                    public double getData() {
                        return super.getData();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_Q1);
                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW));
                        setData(val);

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
                    public double getData() {
                        return super.getData();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_Q2);
                        double val = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW));
                        setData(val);

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

            case INDEX_SERIES:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getData() {
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
                    public double getData() {
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
                    public double getData() {
                        return client.getIndexAsk();
                    }

                    @Override
                    public void updateData() {

                    }

                    @Override
                    public void load() {
                    }
                };
            case BASKETS:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getData() {
                        return super.getData();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.BASKETS);
                        setData(MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.CDF)));
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
