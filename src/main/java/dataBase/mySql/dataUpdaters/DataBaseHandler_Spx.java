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

public class DataBaseHandler_Spx extends IDataBaseHandler {

    ArrayList<MyTimeStampObject> index_timestamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> index_bid_synthetic_timestamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> index_ask_synthetic_timestamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> baskets_timestamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_q1_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_q2_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_week_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_month_timeStamp = new ArrayList<>();

    double baskets_0 = 0;
    double index_bid_synthetic_0 = 0;
    double index_ask_synthetic_0 = 0;
    double index_0 = 0;
    double fut_e1_0 = 0;
    double fut_e2_0 = 0;
    double fut_week_0 = 0;

    Exp week;
    E q1, q2;

    public DataBaseHandler_Spx(BASE_CLIENT_OBJECT client) {
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
        if (sleep_count % 15000 == 0) {
            updateListsRetro();
        }

        // On changed data
        on_change_data();

        // Update count
        sleep_count += sleep;
    }

    private void on_change_data() {

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

            // Index bid synthetic
            if (client.getIndex_bid_synthetic() != index_bid_synthetic_0) {
                index_bid_synthetic_0 = client.getIndex_bid_synthetic();
                index_bid_synthetic_timestamp.add(new MyTimeStampObject(Instant.now(), index_bid_synthetic_0));
            }

            // Index ask synthetic
            if (client.getIndex_ask_synthetic() != index_ask_synthetic_0) {
                index_ask_synthetic_0 = client.getIndex_ask_synthetic();
                index_ask_synthetic_timestamp.add(new MyTimeStampObject(Instant.now(), index_ask_synthetic_0));
            }

            // Fut e1
            double fut_e1 = q1.get_future();

            if (fut_e1 != fut_e1_0) {
                fut_e1_0 = fut_e1;
                fut_q1_timeStamp.add(new MyTimeStampObject(Instant.now(), fut_e1_0));
            }

            // Fut e2
            double fut_e2 = q2.get_future();

            if (fut_e2 != fut_e2_0) {
                fut_e2_0 = fut_e2;
                fut_q2_timeStamp.add(new MyTimeStampObject(Instant.now(), fut_e2_0));
            }
        }


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

        // Dev
        serie_ids.put(TimeSeriesHandler.INDEX_DEV, 3);
        serie_ids.put(TimeSeriesHandler.INDEX_BID_DEV, 16);
        serie_ids.put(TimeSeriesHandler.INDEX_ASK_DEV, 15);
        serie_ids.put(TimeSeriesHandler.FUT_WEEK_DEV, 4);
        serie_ids.put(TimeSeriesHandler.FUT_Q1_DEV, 17);
        serie_ids.put(TimeSeriesHandler.FUT_Q2_DEV, 18);

        // Prod
        serie_ids.put(TimeSeriesHandler.INDEX_PROD, 3);
        serie_ids.put(TimeSeriesHandler.INDEX_AVG_3600_PROD, 3);
        serie_ids.put(TimeSeriesHandler.INDEX_AVG_900_PROD, 3);
        serie_ids.put(TimeSeriesHandler.INDEX_BID_PROD, 16);
        serie_ids.put(TimeSeriesHandler.INDEX_ASK_PROD, 15);
        serie_ids.put(TimeSeriesHandler.FUT_WEEK_PROD, 4);
        serie_ids.put(TimeSeriesHandler.FUT_Q1_PROD, 9531);
        serie_ids.put(TimeSeriesHandler.FUT_Q2_PROD, 9532);


        // DF
//        serie_ids.put(TimeSeriesHandler.DF_7, 9526);
        serie_ids.put(TimeSeriesHandler.DF_2, 9525);//1023
        serie_ids.put(TimeSeriesHandler.DF_9, 9466);// Dynamic

        // Week
        serie_ids.put(TimeSeriesHandler.OP_AVG_WEEK_900, 9473);
        serie_ids.put(TimeSeriesHandler.OP_AVG_WEEK_3600, 9472);
        serie_ids.put(TimeSeriesHandler.OP_AVG_WEEK_240_CONTINUE, 9474);
        serie_ids.put(TimeSeriesHandler.OP_AVG_WEEK_DAILY, 9514);

        // Q1
        serie_ids.put(TimeSeriesHandler.OP_AVG_Q1_900, 9602);
        serie_ids.put(TimeSeriesHandler.OP_AVG_Q1_3600, 9578);
        serie_ids.put(TimeSeriesHandler.OP_AVG_Q1_DAILY, 9682);

        // Q2
        serie_ids.put(TimeSeriesHandler.OP_AVG_Q2_900, 9604);
        serie_ids.put(TimeSeriesHandler.OP_AVG_Q2_3600, 9577);
        serie_ids.put(TimeSeriesHandler.OP_AVG_Q2_DAILY, 9684);

        // Roll week q1
        serie_ids.put(TimeSeriesHandler.ROLL_WEEK_Q1_900, 9538);
        serie_ids.put(TimeSeriesHandler.ROLL_WEEK_Q1_3600, 9537);
        serie_ids.put(TimeSeriesHandler.ROLL_WEEK_Q1_DAILY, 9670);

        // Roll q1 q2
        serie_ids.put(TimeSeriesHandler.ROLL_Q1_Q2_900, 9673);
        serie_ids.put(TimeSeriesHandler.ROLL_Q1_Q2_3600, 9674);
        serie_ids.put(TimeSeriesHandler.ROLL_Q1_Q2_DAILY, 9676);

        // Pre day avg
        serie_ids.put(TimeSeriesHandler.PRE_DAY_OP_AVG, 9682);


        // INDEX
        client.getTimeSeriesHandler().put(TimeSeriesFactory.INDEX_AVG_3600, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.INDEX_AVG_3600, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.INDEX_AVG_900, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.INDEX_AVG_900, client));

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
        insert_dev_prod(index_timestamp, TimeSeriesHandler.INDEX_DEV, TimeSeriesHandler.INDEX_PROD);
        insert_dev_prod(fut_week_timeStamp, TimeSeriesHandler.FUT_WEEK_DEV, TimeSeriesHandler.FUT_WEEK_PROD);
        insert_dev_prod(fut_q1_timeStamp, TimeSeriesHandler.FUT_Q1_DEV, TimeSeriesHandler.FUT_Q1_PROD);
        insert_dev_prod(fut_q2_timeStamp, TimeSeriesHandler.FUT_Q2_DEV, TimeSeriesHandler.FUT_Q2_PROD);
        insert_dev_prod(baskets_timestamp, TimeSeriesHandler.BASKETS_DEV, TimeSeriesHandler.BASKETS_PROD);
        insert_dev_prod(index_bid_synthetic_timestamp, TimeSeriesHandler.INDEX_BID_DEV, TimeSeriesHandler.INDEX_BID_PROD);
        insert_dev_prod(index_ask_synthetic_timestamp, TimeSeriesHandler.INDEX_ASK_DEV, TimeSeriesHandler.INDEX_ASK_PROD);
    }
}