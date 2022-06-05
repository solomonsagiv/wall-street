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
        time_series.add(TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_7_900, client));
        time_series.add(TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_7_3600, client));

        time_series.add(TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_2_ROUND, client));
        time_series.add(TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_7_ROUND, client));
    }

    @Override
    public void go() {

        int op_avg_5_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_DAY_5_TABLE);
        int op_avg_60_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_DAY_60_TABLE);
        int op_avg_240_continue_id = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_240_CONITNUE_TABLE);
        int op_avg_day = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_DAY_TABLE);
        int op_avg_week = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_WEEK_TABLE);
        int op_avg_month = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_MONTH_TABLE);
        int op_avg_q1 = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_Q1_TABLE);
        int op_avg_q2 = client.getMySqlService().getDataBaseHandler().getSerie_ids().get(TimeSeriesHandler.OP_AVG_Q2_TABLE);


        // Day
        Exp day = getClient().getExps().getExp(ExpStrings.day);
        day.setOp_avg_5(MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(op_avg_5_id, MySql.RAW)));
        day.setOp_avg_60(MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(op_avg_60_id, MySql.RAW)));
        day.setOp_avg_240_continue(MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(op_avg_240_continue_id, MySql.RAW)));
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

        // Q2
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
        return 20000;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        return str.toString();
    }
}
