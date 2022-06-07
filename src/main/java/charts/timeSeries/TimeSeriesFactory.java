package charts.timeSeries;

import dataBase.mySql.MySql;
import dataBase.mySql.dataUpdaters.IDataBaseHandler;
import serverObjects.BASE_CLIENT_OBJECT;

import java.sql.ResultSet;

public class TimeSeriesFactory {

    public static final String INDEX_SERIES = "INDEX";
    public static final String INDEX_BID_SERIES = "INDEX_BID";
    public static final String INDEX_ASK_SERIES = "INDEX_ASK";
    public static final String STOCKS_DELTA_SERIES = "STOCKS_DELTA";
    public static final String OP_AVG_DAY_5_SERIES = "OP_AVG_DAY_5";
    public static final String OP_AVG_DAY_60_SERIES = "OP_AVG_DAY_60";
    public static final String BASKETS_SERIES = "BASKETS";
    public static final String OP_AVG_240_CONTINUE = "OP_AVG_240_CONTINUE";
    public static final String DF_7 = "DF_7";
    public static final String DF_7_ROUND = "DF_7_ROUND";
    public static final String DF_2 = "DF_2";
    public static final String DF_2_ROUND = "DF_2_ROUND";
    public static final String DF_7_300 = "DF_7_300";
    public static final String DF_7_900 = "DF_7_900";
    public static final String DF_7_3600 = "DF_7_3600";


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

            case DF_2_ROUND:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getData() {
                        return (int) (super.getData() / 1000);
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_2);
                        setData(MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.CDF)));
                    }

                    @Override
                    public void load() {
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

            case DF_7_ROUND:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getData() {
                        return (int) (super.getData() / 1000);
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.DF_7);
                        setData(MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.CDF)));
                    }

                    @Override
                    public void load() {
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
                        setData(MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.CDF)));
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
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_240_CONITNUE_TABLE);
                        setData(MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW)));
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_240_CONITNUE_TABLE);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }
                };

            case OP_AVG_DAY_5_SERIES:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getData() {
                        return super.getData();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_DAY_5_TABLE);
                        setData(MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW)));
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_DAY_5_TABLE);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.RAW);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }
                };

            case OP_AVG_DAY_60_SERIES:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getData() {
                        return super.getData();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_DAY_60_TABLE);
                        setData(MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.RAW)));
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_DAY_60_TABLE);
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
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.INDEX_TABLE);
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
            case BASKETS_SERIES:
                return new MyTimeSeries(series_type, client) {

                    @Override
                    public double getData() {
                        return super.getData();
                    }

                    @Override
                    public void updateData() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.BASKETS_TABLE);
                        setData(MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(serie_id, MySql.CDF)));
                    }

                    @Override
                    public void load() {
                        int serie_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.BASKETS_TABLE);
                        ResultSet rs = MySql.Queries.get_serie_mega_table(serie_id, MySql.CDF);
                        IDataBaseHandler.loadSerieData(rs, this);
                    }
                };
            default:
                return null;
        }
    }
}
