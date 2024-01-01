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
    ArrayList<MyTimeStampObject> index_bid_timestamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> index_ask_timestamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_day_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_e1_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_e2_timeStamp = new ArrayList<>();

    ArrayList<MyTimeStampObject> index_test_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_week_test_timeStamp = new ArrayList<>();

    double index_0 = 0;
    double index_bid_0 = 0;
    double index_ask_0 = 0;
    double fut_day_0 = 0;
    double fut_e1_0 = 0;
    double fut_e2_0 = 0;
    double index_test_0 = 0;
    double fut_week_test_0 = 0;


    Exp day;
    E q1, q2;

    public DataBaseHandler_Spx(BASE_CLIENT_OBJECT client) {
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

//         Test
//        if (client.getIndex() != index_test_0) {
//            index_test_0 = client.getIndex();
//            index_test_timeStamp.add(new MyTimeStampObject(Instant.now(), index_test_0));
//        }
//
//         Fut day
//        double fut_day = day.get_future();
//
//        if (fut_day != fut_week_test_0) {
//            fut_week_test_0 = fut_day;
//            fut_week_test_timeStamp.add(new MyTimeStampObject(Instant.now(), fut_week_test_0));
//        }



        if (client.isLive_db()) {

            // Index
            if (client.getIndex() != index_0) {
                index_0 = client.getIndex();
                index_timestamp.add(new MyTimeStampObject(Instant.now(), index_0));
            }

            // Index bid
            if (client.getIndexBid() != index_bid_0) {
                index_bid_0 = client.getIndexBid();
                index_bid_timestamp.add(new MyTimeStampObject(Instant.now(), index_bid_0));
            }

            // Index ask
            if (client.getIndexAsk() != index_ask_0) {
                index_ask_0 = client.getIndexAsk();
                index_ask_timestamp.add(new MyTimeStampObject(Instant.now(), index_ask_0));
            }

            // Fut day
//            double fut_day = day.get_future();
//
//            if (fut_day != fut_day_0) {
//                fut_day_0 = fut_day;
//                fut_day_timeStamp.add(new MyTimeStampObject(Instant.now(), fut_day_0));
//            }

        }

//         Fut e1
//        double fut_e1 = q1.get_future();
//
//        if (fut_e1 != fut_e1_0) {
//            fut_e1_0 = fut_e1;
//            fut_e1_timeStamp.add(new MyTimeStampObject(Instant.now(), fut_e1_0));
//        }
//
//         Fut e2
//        double fut_e2 = q2.get_future();
//
//        if (fut_e2 != fut_e2_0) {
//            fut_e2_0 = fut_e2;
//            fut_e2_timeStamp.add(new MyTimeStampObject(Instant.now(), fut_e2_0));
//        }

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

        // Index
        serie_ids.put(TimeSeriesHandler.INDEX_TEST, 11971);
        serie_ids.put(TimeSeriesHandler.FUTURE_WEEK_TEST, 11972);
        serie_ids.put(TimeSeriesHandler.INDEX, 9470);
        serie_ids.put(TimeSeriesHandler.INDEX_AVG_3600, 9470);
        serie_ids.put(TimeSeriesHandler.INDEX_AVG_900, 9470);
        serie_ids.put(TimeSeriesHandler.INDEX_BID, 16);
        serie_ids.put(TimeSeriesHandler.INDEX_ASK, 15);
        serie_ids.put(TimeSeriesHandler.FUT_WEEK, 4);
        serie_ids.put(TimeSeriesHandler.FUT_Q1, 9531);
        serie_ids.put(TimeSeriesHandler.FUT_Q2, 9532);

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

    private void updateListsRetro() {
        // Dev
        insertListRetro(index_test_timeStamp, serie_ids.get(TimeSeriesHandler.INDEX_TEST), MySql.JIBE_DEV_CONNECTION);
        insertListRetro(fut_week_test_timeStamp, serie_ids.get(TimeSeriesHandler.FUTURE_WEEK_TEST), MySql.JIBE_DEV_CONNECTION);

        // Prod
        insertListRetro(index_timestamp, serie_ids.get(TimeSeriesHandler.INDEX), MySql.JIBE_PROD_CONNECTION);
        insertListRetro(index_bid_timestamp, serie_ids.get(TimeSeriesHandler.INDEX_BID), MySql.JIBE_PROD_CONNECTION);
        insertListRetro(index_ask_timestamp, serie_ids.get(TimeSeriesHandler.INDEX_ASK), MySql.JIBE_PROD_CONNECTION);
        insertListRetro(fut_day_timeStamp, serie_ids.get(TimeSeriesHandler.FUT_WEEK), MySql.JIBE_PROD_CONNECTION);
        insertListRetro(fut_e1_timeStamp, serie_ids.get(TimeSeriesHandler.FUT_Q1), MySql.JIBE_PROD_CONNECTION);
        insertListRetro(fut_e2_timeStamp, serie_ids.get(TimeSeriesHandler.FUT_Q2), MySql.JIBE_PROD_CONNECTION);
    }
}