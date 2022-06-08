package DataUpdater;

import charts.timeSeries.MyTimeSeries;
import charts.timeSeries.TimeSeriesFactory;
import charts.timeSeries.TimeSeriesHandler;
import dataBase.mySql.MySql;
import exp.Exp;
import exp.ExpStrings;
import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;
import java.util.ArrayList;

public class DataUpdaterService extends MyBaseService {

    ArrayList<MyTimeSeries> time_series;

    public DataUpdaterService(BASE_CLIENT_OBJECT client) {
        super(client);
        time_series = new ArrayList<>();
        time_series.add(TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_2, client));
        time_series.add(TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_7, client));
        time_series.add(TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_7_300, client));
        time_series.add(TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.BASKETS_SERIES, client));
    }

    @Override
    public void go() {

        int op_avg_5_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_DAY_5_TABLE);
        int op_avg_60_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_DAY_60_TABLE);
        int op_avg_240_continue_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_240_CONITNUE_TABLE);
        int day_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.FUT_DAY_TABLE);
        int week_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.FUT_WEEK_TABLE);
        int month_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.FUT_MONTH_TABLE);
        int q1_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.FUT_Q1_TABLE);
        int q2_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.FUT_Q2_TABLE);
        int index_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.INDEX_TABLE);

        double op_avg_day_5 = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(op_avg_5_id, MySql.RAW));
        double op_avg_day_60 = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(op_avg_60_id, MySql.RAW));
        double op_avg_day_240_continue = MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(op_avg_240_continue_id, MySql.RAW));
        double op_avg_day = MySql.Queries.handle_rs(MySql.Queries.get_op_avg_mega(index_id, day_id, MySql.AVG_TODAY));
        double op_avg_week = MySql.Queries.handle_rs(MySql.Queries.get_op_avg_mega(index_id, week_id, MySql.AVG_TODAY));
        double op_avg_month = MySql.Queries.handle_rs(MySql.Queries.get_op_avg_mega(index_id, month_id, MySql.AVG_TODAY));
        double op_avg_q1 = MySql.Queries.handle_rs(MySql.Queries.get_op_avg_mega(index_id, q1_id, MySql.AVG_TODAY));
        double op_avg_q2 = MySql.Queries.handle_rs(MySql.Queries.get_op_avg_mega(index_id, q2_id, MySql.AVG_TODAY));

        // Day
        Exp day = getClient().getExps().getExp(ExpStrings.day);
        day.setOp_avg_5(op_avg_day_5);
        day.setOp_avg_60(op_avg_day_60);
        day.setOp_avg_240_continue(op_avg_day_240_continue);
        day.setOp_avg(op_avg_day);

        // Week
        Exp week = getClient().getExps().getExp(ExpStrings.week);
        week.setOp_avg(op_avg_week);

        // Month
        Exp month = getClient().getExps().getExp(ExpStrings.month);
        month.setOp_avg(op_avg_month);

        // Q1
        Exp q1 = getClient().getExps().getExp(ExpStrings.q1);
        q1.setOp_avg(op_avg_q1);

        // Q1
        Exp q2 = getClient().getExps().getExp(ExpStrings.q2);
        q2.setOp_avg(op_avg_q2);

        // DF N AVG
        df_n_avg();
    }

    private void df_n_avg() {
        for (MyTimeSeries ts : time_series) {
            try {
                ts.updateData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getName() {
        return client.getName() + " " + "Data updater service";
    }

    @Override
    public int getSleep() {
        return 15000;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        return str.toString();
    }
}
