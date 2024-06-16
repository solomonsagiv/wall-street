package dataBase.mySql.dataUpdaters;

import charts.timeSeries.TimeSeriesFactory;
import charts.timeSeries.TimeSeriesHandler;
import dataBase.mySql.MySql;
import exp.E;
import exp.Exp;
import exp.ExpStrings;
import races.Race_Logic;
import serverObjects.BASE_CLIENT_OBJECT;

import java.time.Instant;
import java.util.ArrayList;

public class DataBaseHandler_Spx extends IDataBaseHandler {

    ArrayList<MyTimeStampObject> index_timestamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> index_bid_timestamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> index_ask_timestamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_q1_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_q2_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_week_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> vix_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> vix_f_1_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> vix_f_2_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> index_races_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> q1_races_timeStamp = new ArrayList<>();

    double index_0 = 0;
    double fut_e1_0 = 0;
    double fut_e2_0 = 0;
    double fut_week_0 = 0;
    double vix_0 = 0;
    double vix_f_1_0 = 0;
    double vix_f_2_0 = 0;
    double index_bid_0 = 0;
    double index_ask_0 = 0;
    double index_races_0 = 0;
    double q1_races_0 = 0;

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


            // --------------------------------------- Index --------------------------------------- //
            // Index
            if (client.getIndex() != index_0) {
                index_0 = client.getIndex();
                index_timestamp.add(new MyTimeStampObject(Instant.now(), index_0));
            }

            // Index bid
            double index_bid = client.getIndex_bid();

            if (index_bid != index_bid_0) {
                index_bid_0 = index_bid;
                index_bid_timestamp.add(new MyTimeStampObject(Instant.now(), index_bid_0));
            }

            // Index ask
            double index_ask = client.getIndexAsk();

            if (index_ask != index_ask_0) {
                index_ask_0 = index_ask;
                index_ask_timestamp.add(new MyTimeStampObject(Instant.now(), index_ask_0));
            }

            // --------------------------------------- Futures --------------------------------------- //
            // Fut week
            double fut_week = week.get_future();

            if (fut_week != fut_week_0) {

                if (Math.abs(fut_week - fut_week_0) < 15) {
                    fut_week_timeStamp.add(new MyTimeStampObject(Instant.now(), fut_week_0));
                }
                fut_week_0 = fut_week;
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

            // --------------------------------------- Vix --------------------------------------- //
            double vix = client.getVix();

            if (vix != vix_0) {
                vix_0 = vix;
                vix_timeStamp.add(new MyTimeStampObject(Instant.now(), vix_0));
            }

            // Vix
            double vix_f_1 = client.getVix_f_1();

            if (vix_f_1 != vix_f_1_0) {
                vix_f_1_0 = vix_f_1;
                vix_f_1_timeStamp.add(new MyTimeStampObject(Instant.now(), vix_f_1_0));
            }

            // Vix
            double vix_2 = client.getVix_f_2();

            if (vix_2 != vix_f_2_0) {
                vix_f_2_0 = vix_2;
                vix_f_2_timeStamp.add(new MyTimeStampObject(Instant.now(), vix_f_2_0));
            }


            // ---------------------------------- Races ---------------------------------- //
            // Index races
            double index_races = client.getRacesService().get_race_logic(Race_Logic.RACE_RUNNER_ENUM.Q1_INDEX).get_r_one_points();

            if (index_races != index_races_0) {
                index_races_0 = index_races;
                double last_count = index_races - index_races_0;
                index_races_timeStamp.add(new MyTimeStampObject(Instant.now(), last_count));
            }

            // Q1 races
            double q1_races = client.getRacesService().get_race_logic(Race_Logic.RACE_RUNNER_ENUM.Q1_INDEX).get_r_two_points();

            if (q1_races != q1_races_0) {
                q1_races_0 = q1_races;
                double last_count = q1_races - q1_races_0;
                q1_races_timeStamp.add(new MyTimeStampObject(Instant.now(), last_count));
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

        // Load races
        load_races(Race_Logic.RACE_RUNNER_ENUM.Q1_INDEX, serie_ids.get(TimeSeriesHandler.INDEX_RACES_PROD));
        load_races(Race_Logic.RACE_RUNNER_ENUM.Q1_INDEX, serie_ids.get(TimeSeriesHandler.Q1_RACES_PROD));

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
        serie_ids.put(TimeSeriesHandler.VIX_DEV, 988);
        serie_ids.put(TimeSeriesHandler.INDEX_CALC_DEV, 12356);
        serie_ids.put(TimeSeriesHandler.FUTURE_CALC_DEV, 12357);
        serie_ids.put(TimeSeriesHandler.VIX_F_1_DEV, 11212);
        serie_ids.put(TimeSeriesHandler.VIX_F_2_DEV, 11213);

        // Prod
        serie_ids.put(TimeSeriesHandler.INDEX_PROD, 3);
        serie_ids.put(TimeSeriesHandler.INDEX_AVG_3600_PROD, 3);
        serie_ids.put(TimeSeriesHandler.INDEX_AVG_900_PROD, 3);
        serie_ids.put(TimeSeriesHandler.INDEX_BID_PROD, 16);
        serie_ids.put(TimeSeriesHandler.INDEX_ASK_PROD, 15);
        serie_ids.put(TimeSeriesHandler.FUT_WEEK_PROD, 4);
        serie_ids.put(TimeSeriesHandler.FUT_Q1_PROD, 9531);
        serie_ids.put(TimeSeriesHandler.FUT_Q2_PROD, 9532);
        serie_ids.put(TimeSeriesHandler.VIX_PROD, 9607);
        serie_ids.put(TimeSeriesHandler.INDEX_CALC_PROD, 9754);
        serie_ids.put(TimeSeriesHandler.FUTURE_CALC_PROD, 9755);
        serie_ids.put(TimeSeriesHandler.VIX_F_1_PROD, 9574);
        serie_ids.put(TimeSeriesHandler.VIX_F_2_PROD, 9575);

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
//        serie_ids.put(TimeSeriesHandler.OP_AVG_Q1_14400, 9682);

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
//        serie_ids.put(TimeSeriesHandler., 9747);

        // Pre day avg
        serie_ids.put(TimeSeriesHandler.PRE_DAY_OP_AVG, 9682);

        // Races
        serie_ids.put(TimeSeriesHandler.INDEX_RACES_PROD, 9783);
        serie_ids.put(TimeSeriesHandler.Q1_RACES_PROD, 9780);



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

        // Races
        client.getTimeSeriesHandler().put(TimeSeriesFactory.INDEX_RACES, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.INDEX_RACES, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.Q1_RACES, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.Q1_RACES, client));
    }

    private void insert_dev_prod(ArrayList<MyTimeStampObject> list, int dev_id, int prod_id) {
        System.out.println("------------------------ Insert start ----------------------------");
        insertListRetro(list, dev_id, MySql.JIBE_DEV_CONNECTION);
        insertListRetro(list, prod_id, MySql.JIBE_PROD_CONNECTION);
        System.out.println("------------------------ Insert End ----------------------------");
        list.clear();
    }

    private void updateListsRetro() {

        // Dev and Prod
        insert_dev_prod(index_timestamp, serie_ids.get(TimeSeriesHandler.INDEX_DEV), serie_ids.get(TimeSeriesHandler.INDEX_PROD));
        insert_dev_prod(index_bid_timestamp, serie_ids.get(TimeSeriesHandler.INDEX_BID_DEV), serie_ids.get(TimeSeriesHandler.INDEX_BID_PROD));
        insert_dev_prod(index_ask_timestamp, serie_ids.get(TimeSeriesHandler.INDEX_ASK_DEV), serie_ids.get(TimeSeriesHandler.INDEX_ASK_PROD));
        insert_dev_prod(fut_week_timeStamp, serie_ids.get(TimeSeriesHandler.FUT_WEEK_DEV), serie_ids.get(TimeSeriesHandler.FUT_WEEK_PROD));
        insert_dev_prod(fut_q1_timeStamp, serie_ids.get(TimeSeriesHandler.FUT_Q1_DEV), serie_ids.get(TimeSeriesHandler.FUT_Q1_PROD));
        insert_dev_prod(fut_q2_timeStamp, serie_ids.get(TimeSeriesHandler.FUT_Q2_DEV), serie_ids.get(TimeSeriesHandler.FUT_Q2_PROD));
        insert_dev_prod(vix_timeStamp, serie_ids.get(TimeSeriesHandler.VIX_DEV), serie_ids.get(TimeSeriesHandler.VIX_PROD));
        insert_dev_prod(vix_f_1_timeStamp, serie_ids.get(TimeSeriesHandler.VIX_F_1_DEV), serie_ids.get(TimeSeriesHandler.VIX_F_1_PROD));
        insert_dev_prod(vix_f_2_timeStamp, serie_ids.get(TimeSeriesHandler.VIX_F_2_DEV), serie_ids.get(TimeSeriesHandler.VIX_F_2_PROD));

        // Races
        insert_dev_prod(index_races_timeStamp, 0, serie_ids.get(TimeSeriesHandler.INDEX_RACES_PROD));
        insert_dev_prod(q1_races_timeStamp, 0, serie_ids.get(TimeSeriesHandler.Q1_RACES_PROD));
    }
}