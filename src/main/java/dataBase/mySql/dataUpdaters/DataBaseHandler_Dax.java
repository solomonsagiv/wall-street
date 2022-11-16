package dataBase.mySql.dataUpdaters;

import charts.timeSeries.TimeSeriesFactory;
import charts.timeSeries.TimeSeriesHandler;
import exp.E;
import exp.Exp;
import exp.ExpStrings;
import serverObjects.BASE_CLIENT_OBJECT;

public class DataBaseHandler_Dax extends IDataBaseHandler {

    Exp day;
    E q1, q2;

    public DataBaseHandler_Dax(BASE_CLIENT_OBJECT client) {
        super(client);
        initTablesNames();
        day = exps.getExp(ExpStrings.day);
        q1 = (E) exps.getExp(ExpStrings.q1);
        q2 = (E) exps.getExp(ExpStrings.q2);
    }

    int sleep_count = 100;

    @Override
    public void insertData(int sleep) {

        if (this.exps == null) {
            this.exps = client.getExps();
        }

        // Update lists retro
        if (sleep_count % 15000 == 0) {
            updateListsRetro();
        }

        // On changed data
        on_change_data();

        // Update count
        sleep_count += sleep;
    }

    private void on_change_data() {

    }

    @Override
    public void loadData() {
        try {
            // Load props
            load_properties();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Load exp data
        load_exp_data();

        // Set load
        client.setLoadFromDb(true);
    }

    @Override
    public void initTablesNames() {

        serie_ids.put(TimeSeriesHandler.INDEX, 4369);
        serie_ids.put(TimeSeriesHandler.FUT_DAY, 4759);
        serie_ids.put(TimeSeriesHandler.FUT_Q1, 4367);
        serie_ids.put(TimeSeriesHandler.FUT_Q2, 4368);
        serie_ids.put(TimeSeriesHandler.OP_AVG_240_CONTINUE, 5634);
        serie_ids.put(TimeSeriesHandler.OP_AVG_DAY_5, 5631);
        serie_ids.put(TimeSeriesHandler.OP_AVG_DAY_15, 5632);
        serie_ids.put(TimeSeriesHandler.OP_AVG_DAY_60, 5633);
//        serie_ids.put(TimeSeriesHandler.BASKETS, 1418);
        serie_ids.put(TimeSeriesHandler.OP_AVG_DAY, 1899);
//        serie_ids.put(TimeSeriesHandler.OP_AVG_WEEK, 1900);
//        serie_ids.put(TimeSeriesHandler.OP_AVG_MONTH, 1901);
//        serie_ids.put(TimeSeriesHandler.OP_AVG_Q1, 1902);
//        serie_ids.put(TimeSeriesHandler.OP_AVG_Q2, 1903);


        client.getTimeSeriesHandler().put(TimeSeriesFactory.BASKETS_CDF, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.BASKETS_CDF, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_DAY, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_DAY, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_DAY_5, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_DAY_5, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_DAY_15, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_DAY_15, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_DAY_60, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_DAY_60, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_240_CONTINUE, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_240_CONTINUE, client));
//        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_Q1, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_Q1, client));
//        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_Q2, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_Q2, client));

        // Exp
        client.getTimeSeriesHandler().put(TimeSeriesFactory.EXP_WEEK_START, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.EXP_WEEK_START, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.EXP_MONTH_START, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.EXP_MONTH_START, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.EXP_Q1_START, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.EXP_Q1_START, client));
    }

    private void updateListsRetro() {
    }
}