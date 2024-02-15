package dataBase.mySql.dataUpdaters;

import charts.timeSeries.TimeSeriesFactory;
import charts.timeSeries.TimeSeriesHandler;
import dataBase.mySql.MySql;
import exp.E;
import exp.Exp;
import exp.ExpStrings;
import serverObjects.BASE_CLIENT_OBJECT;
import java.time.Instant;
import java.util.ArrayList;

public class DataBaseHandler_Ndx extends IDataBaseHandler {

    ArrayList<MyTimeStampObject> index_timestamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> baskets_timestamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_q1_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_q2_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_week_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> vix_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> index_calc_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> future_calc_timeStamp = new ArrayList<>();

    double baskets_0 = 0;
    double index_0 = 0;
    double fut_q1_0 = 0;
    double fut_q2_0 = 0;
    double fut_week_0 = 0;
    double vix_0 = 0;
    double index_calc_0 = 0;
    double future_calc_0 = 0;

    Exp week;
    E q1, q2;

    public DataBaseHandler_Ndx(BASE_CLIENT_OBJECT client) {
        super(client);
        initTablesNames();
        week = exps.getExp(ExpStrings.day);
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
        if (sleep_count % 10000 == 0) {
            updateListsRetro();
        }

        // On data changed
        on_data_chage();

        // Update count
        sleep_count += sleep;
    }

    private void on_data_chage() {

        // Is live db
        if (client.isLive_db()) {
            // Baskets
            int basket = client.getBasketFinder_by_stocks().getBaskets();

            if (basket != baskets_0) {
                double last_count = basket - baskets_0;
                baskets_0 = basket;
                baskets_timestamp.add(new MyTimeStampObject(Instant.now(), last_count));
            }

            // Fut week
            double fut_week = week.get_future();

            if (fut_week != fut_week_0) {

                if (Math.abs(fut_week - fut_week_0) < 15) {
                    fut_week_timeStamp.add(new MyTimeStampObject(Instant.now(), fut_week_0));
                }
                fut_week_0 = fut_week;
            }

            // Index
            if (client.getIndex() != index_0) {
                index_0 = client.getIndex();
                index_timestamp.add(new MyTimeStampObject(Instant.now(), index_0));
            }

            // Fut e1
            double fut_q1 = q1.get_future();

            if (fut_q1 != fut_q1_0) {
                fut_q1_0 = fut_q1;
                fut_q1_timeStamp.add(new MyTimeStampObject(Instant.now(), fut_q1_0));
            }

            // Fut e2
            double fut_q2 = q2.get_future();

            if (fut_q2 != fut_q2_0) {
                fut_q2_0 = fut_q2;
                fut_q2_timeStamp.add(new MyTimeStampObject(Instant.now(), fut_q2_0));
            }

            // Vix
            double vix = client.getVix();

            if (vix != vix_0) {
                vix_0 = vix;
                vix_timeStamp.add(new MyTimeStampObject(Instant.now(), vix_0));
            }

            // Index calc
            double index_calc = client.getIndex_calc();

            if (index_calc != index_calc_0) {
                index_calc_0 = index_calc;
                index_calc_timeStamp.add(new MyTimeStampObject(Instant.now(), index_calc_0));
            }

            // Future calc
            double future_calc = client.getFuture_calc();

            if (future_calc != future_calc_0) {
                future_calc_0 = future_calc;
                future_calc_timeStamp.add(new MyTimeStampObject(Instant.now(), future_calc_0));
            }
        }
    }

    @Override
    public void loadData() {
        try {
            // Props
            load_properties();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Load exp data
        load_exp_data();

        // Set load true
        client.setLoadFromDb(true);
    }

    @Override
    public void initTablesNames() {

        // Ids
        serie_ids.put(TimeSeriesHandler.INDEX_DEV, 1);
        serie_ids.put(TimeSeriesHandler.FUT_WEEK_DEV, 2);
        serie_ids.put(TimeSeriesHandler.FUT_Q1_DEV, 11);
        serie_ids.put(TimeSeriesHandler.FUT_Q2_DEV, 12);
        serie_ids.put(TimeSeriesHandler.BASKETS_DEV, 1418);
        serie_ids.put(TimeSeriesHandler.INDEX_BID_DEV, 9668);
        serie_ids.put(TimeSeriesHandler.INDEX_ASK_DEV, 9669);
        serie_ids.put(TimeSeriesHandler.VIX_DEV, 2483);
        serie_ids.put(TimeSeriesHandler.INDEX_CALC_DEV, 12352);
        serie_ids.put(TimeSeriesHandler.FUTURE_CALC_DEV, 12353);

        serie_ids.put(TimeSeriesHandler.INDEX_PROD, 1);
        serie_ids.put(TimeSeriesHandler.INDEX_AVG_3600_PROD, 1);
        serie_ids.put(TimeSeriesHandler.INDEX_AVG_900_PROD, 1);
        serie_ids.put(TimeSeriesHandler.FUT_WEEK_PROD, 2);
        serie_ids.put(TimeSeriesHandler.FUT_Q1_PROD, 9533);
        serie_ids.put(TimeSeriesHandler.FUT_Q2_PROD, 9534);
        serie_ids.put(TimeSeriesHandler.BASKETS_PROD, 9519);
        serie_ids.put(TimeSeriesHandler.INDEX_BID_PROD, 9389);
        serie_ids.put(TimeSeriesHandler.INDEX_ASK_PROD, 9388);
        serie_ids.put(TimeSeriesHandler.VIX_PROD, 9608);
        serie_ids.put(TimeSeriesHandler.INDEX_CALC_PROD, 9752);
        serie_ids.put(TimeSeriesHandler.FUTURE_CALC_PROD, 9753);

        // DF
//        serie_ids.put(TimeSeriesHandler.DF_7, 9529);
        serie_ids.put(TimeSeriesHandler.DF_2, 9528);//990
        serie_ids.put(TimeSeriesHandler.DF_9, 9455);

        // Week
        serie_ids.put(TimeSeriesHandler.OP_AVG_WEEK_900, 9729);
        serie_ids.put(TimeSeriesHandler.OP_AVG_WEEK_3600, 9728);
        serie_ids.put(TimeSeriesHandler.OP_AVG_WEEK_240_CONTINUE, 9730);
        serie_ids.put(TimeSeriesHandler.OP_AVG_WEEK_DAILY, 9517);

        // Q1
        serie_ids.put(TimeSeriesHandler.OP_AVG_Q1_900, 9601);
        serie_ids.put(TimeSeriesHandler.OP_AVG_Q1_3600, 9580);
        serie_ids.put(TimeSeriesHandler.OP_AVG_Q1_DAILY, 9685);

        // Q2
        serie_ids.put(TimeSeriesHandler.OP_AVG_Q2_900, 9603);
        serie_ids.put(TimeSeriesHandler.OP_AVG_Q2_3600, 9579);
        serie_ids.put(TimeSeriesHandler.OP_AVG_Q2_DAILY, 9683);

        // Roll week q1
        serie_ids.put(TimeSeriesHandler.ROLL_WEEK_Q1_900, 9540);
        serie_ids.put(TimeSeriesHandler.ROLL_WEEK_Q1_3600, 9539);
        serie_ids.put(TimeSeriesHandler.ROLL_WEEK_Q1_DAILY, 9671);

        // Roll q1 q2
        serie_ids.put(TimeSeriesHandler.ROLL_Q1_Q2_900, 9677);
        serie_ids.put(TimeSeriesHandler.ROLL_Q1_Q2_3600, 9681);
        serie_ids.put(TimeSeriesHandler.ROLL_Q1_Q2_DAILY, 9680);

        // Pre day avg
        serie_ids.put(TimeSeriesHandler.PRE_DAY_OP_AVG, 9685);

        // INDEX
        client.getTimeSeriesHandler().put(TimeSeriesFactory.INDEX_AVG_3600, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.INDEX_AVG_3600, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.INDEX_AVG_900, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.INDEX_AVG_900, client));

        // BASKETS
        client.getTimeSeriesHandler().put(TimeSeriesFactory.BASKETS_CDF, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.BASKETS_CDF, client));

        // DF
        client.getTimeSeriesHandler().put(TimeSeriesFactory.DF_2_CDF, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_2_CDF, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.DF_9_CDF, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_9_CDF, client));

        // Weekly
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_WEEK_DAILY, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_WEEK_DAILY, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_WEEK_900, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_WEEK_900, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_WEEK_3600, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_WEEK_3600, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_WEEK_240_CONTINUE, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_WEEK_240_CONTINUE, client));

        // Q1
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_Q1_DAILY, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_Q1_DAILY, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_Q1_900, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_Q1_900, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_Q1_3600, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_Q1_3600, client));

        // Q2
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_Q2_DAILY, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_Q2_DAILY, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_Q2_900, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_Q2_900, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_Q2_3600, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_Q2_3600, client));

        // ROLL WEEK Q1
        client.getTimeSeriesHandler().put(TimeSeriesFactory.ROLL_WEEK_Q1_900, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.ROLL_WEEK_Q1_900, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.ROLL_WEEK_Q1_3600, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.ROLL_WEEK_Q1_3600, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.ROLL_WEEK_Q1_DAILY, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.ROLL_WEEK_Q1_DAILY, client));

        // ROLL Q1 Q2
        client.getTimeSeriesHandler().put(TimeSeriesFactory.ROLL_Q1_Q2_900, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.ROLL_Q1_Q2_900, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.ROLL_Q1_Q2_3600, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.ROLL_Q1_Q2_3600, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.ROLL_Q1_Q2_DAILY, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.ROLL_Q1_Q2_DAILY, client));

        // EXP WEEK
        client.getTimeSeriesHandler().put(TimeSeriesFactory.EXP_WEEK_START, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.EXP_WEEK_START, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.DF_2_WEEK, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_2_WEEK, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.BASKETS_WEEK, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.BASKETS_WEEK, client));

        // EXP MONTH
        client.getTimeSeriesHandler().put(TimeSeriesFactory.EXP_MONTH_START, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.EXP_MONTH_START, client));

        // Pre day q1 op avg
        client.getTimeSeriesHandler().put(TimeSeriesFactory.PRE_DAY_OP_AVG, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.PRE_DAY_OP_AVG, client));

    }

    private void insert_dev_prod(ArrayList<MyTimeStampObject> list, int dev_id, int prod_id) {
        insertListRetro(list, dev_id, MySql.JIBE_DEV_CONNECTION);
        insertListRetro(list, prod_id, MySql.JIBE_PROD_CONNECTION);
        list.clear();
    }

    private void updateListsRetro() {
        // Dev and Prod
        insert_dev_prod(index_timestamp, serie_ids.get(TimeSeriesHandler.INDEX_DEV), serie_ids.get(TimeSeriesHandler.INDEX_PROD));
        insert_dev_prod(fut_week_timeStamp, serie_ids.get(TimeSeriesHandler.FUT_WEEK_DEV), serie_ids.get(TimeSeriesHandler.FUT_WEEK_PROD));
        insert_dev_prod(fut_q1_timeStamp, serie_ids.get(TimeSeriesHandler.FUT_Q1_DEV), serie_ids.get(TimeSeriesHandler.FUT_Q1_PROD));
        insert_dev_prod(fut_q2_timeStamp, serie_ids.get(TimeSeriesHandler.FUT_Q2_DEV), serie_ids.get(TimeSeriesHandler.FUT_Q2_PROD));
        insert_dev_prod(baskets_timestamp, serie_ids.get(TimeSeriesHandler.BASKETS_DEV), serie_ids.get(TimeSeriesHandler.BASKETS_PROD));
        insert_dev_prod(vix_timeStamp, serie_ids.get(TimeSeriesHandler.VIX_DEV), serie_ids.get(TimeSeriesHandler.VIX_PROD));
        insert_dev_prod(index_calc_timeStamp, serie_ids.get(TimeSeriesHandler.INDEX_CALC_DEV), serie_ids.get(TimeSeriesHandler.INDEX_CALC_PROD));
        insert_dev_prod(future_calc_timeStamp, serie_ids.get(TimeSeriesHandler.FUTURE_CALC_DEV), serie_ids.get(TimeSeriesHandler.FUTURE_CALC_PROD));
    }
}