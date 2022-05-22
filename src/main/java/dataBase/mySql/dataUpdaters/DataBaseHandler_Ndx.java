package dataBase.mySql.dataUpdaters;

import api.Manifest;
import dataBase.mySql.MySql;
import exp.E;
import exp.Exp;
import exp.ExpStrings;
import serverObjects.BASE_CLIENT_OBJECT;

import java.time.Instant;
import java.util.ArrayList;

public class DataBaseHandler_Ndx extends IDataBaseHandler {

    ArrayList<MyTimeStampObject> index_timestamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_day_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_week_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_month_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_e1_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_e2_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> baskets_timestamp = new ArrayList<>();

    double index_0 = 0;
    double fut_day_0 = 0;
    double fut_week_0 = 0;
    double fut_month_0 = 0;
    double fut_e1_0 = 0;
    double fut_e2_0 = 0;
    double baskets_0 = 0;

    Exp day, week, month;
    E q1, q2;

    public DataBaseHandler_Ndx(BASE_CLIENT_OBJECT client) {
        super(client);
        initTablesNames();
        day = exps.getExp(ExpStrings.day);
        week = exps.getExp(ExpStrings.week);
        month = exps.getExp(ExpStrings.month);
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

        if (Manifest.LIVE_DB) {
            // Index
            if (client.getIndex() != index_0) {
                index_0 = client.getIndex();
                index_timestamp.add(new MyTimeStampObject(Instant.now(), index_0));
            }

            // Fut day
            double fut_day = day.get_future();

            if (fut_day != fut_day_0) {
                fut_day_0 = fut_day;
                fut_day_timeStamp.add(new MyTimeStampObject(Instant.now(), fut_day_0));
            }


            // Fut e1
            double fut_e1 = q1.get_future();

            if (fut_e1 != fut_e1_0) {
                fut_e1_0 = fut_e1;
                fut_e1_timeStamp.add(new MyTimeStampObject(Instant.now(), fut_e1_0));
            }

            // Fut e2
            double fut_e2 = q2.get_future();

            if (fut_e2 != fut_e2_0) {
                fut_e2_0 = fut_e2;
                fut_e2_timeStamp.add(new MyTimeStampObject(Instant.now(), fut_e2_0));
            }

        }

        // Fut week
        double fut_week = week.get_future();

        if (fut_week != fut_week_0) {
            fut_week_0 = fut_week;
            fut_week_timeStamp.add(new MyTimeStampObject(Instant.now(), fut_week_0));
        }

        // Fut month
        double fut_month = month.get_future();

        if (fut_month != fut_month_0) {
            fut_month_0 = fut_month;
            fut_month_timeStamp.add(new MyTimeStampObject(Instant.now(), fut_month_0));
        }

        // Baskets
        int basket = client.getBasketFinder().getBaskets();

        if (basket != baskets_0) {
            double last_count = basket - baskets_0;
            baskets_0 = basket;
            baskets_timestamp.add(new MyTimeStampObject(Instant.now(), last_count));
        }

    }

    @Override
    public void loadData() {
        // OP AVG
        Exp day = exps.getExp(ExpStrings.day);
        Exp week = exps.getExp(ExpStrings.week);
        Exp month = exps.getExp(ExpStrings.month);
        Exp q1 = exps.getExp(ExpStrings.q1);
        Exp q2 = exps.getExp(ExpStrings.q2);

        load_op_avg(day, MySql.Queries.op_query(tablesNames.get(INDEX_TABLE), tablesNames.get(FUT_DAY_TABLE)));
        load_op_avg(week, MySql.Queries.op_query(tablesNames.get(INDEX_TABLE), tablesNames.get(FUT_WEEK_TABLE)));
        load_op_avg(month, MySql.Queries.op_query(tablesNames.get(INDEX_TABLE), tablesNames.get(FUT_MONTH_TABLE)));
        load_op_avg(q1, MySql.Queries.op_query(tablesNames.get(INDEX_TABLE), tablesNames.get(FUT_Q1_TABLE)));
        load_op_avg(q2, MySql.Queries.op_query(tablesNames.get(INDEX_TABLE), tablesNames.get(FUT_Q2_TABLE)));

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
        tablesNames.put(INDEX_TABLE, "data.ndx_index");
        tablesNames.put(BASKETS_TABLE, "data.ndx_baskets_cdf");
        tablesNames.put(FUT_DAY_TABLE, "data.ndx_fut_day");
        tablesNames.put(FUT_WEEK_TABLE, "data.ndx_fut_week");
        tablesNames.put(FUT_MONTH_TABLE, "data.ndx_fut_month");
        tablesNames.put(FUT_Q1_TABLE, "data.ndx_fut_e1");
        tablesNames.put(FUT_Q2_TABLE, "data.ndx_fut_e2");
        tablesNames.put(FUT_DELTA_TABLE, "data.ndx_fut_delta_cdf");
        tablesNames.put(E1_BID_ASK_COUNTER_TABLE, "data.ndx_e1_bid_ask_counter_cdf");
        tablesNames.put(OP_AVG_240_CONITNUE_TABLE, "data.ndx_op_avg_day_240_continue");
        tablesNames.put(OP_AVG_DAY_5_TABLE, "data.ndx_op_avg_day_5");
        tablesNames.put(OP_AVG_DAY_60_TABLE, "data.ndx_op_avg_day_60");
        tablesNames.put(DECISION_FUNCTION_TABLE, "data.ndx_decision_func");



        // Ids

//        serie_ids.put(INDEX_TABLE, 1);
//        serie_ids.put(FUT_DAY_TABLE, 2);
//        serie_ids.put(FUT_WEEK_TABLE, 14);
//        serie_ids.put(FUT_MONTH_TABLE, 13);
//        serie_ids.put(FUT_Q1_TABLE,11);
//        serie_ids.put(FUT_Q2_TABLE, 12);
//        serie_ids.put(OP_AVG_240_CONITNUE_TABLE, 112);
//        serie_ids.put(OP_AVG_DAY_5_TABLE, 114);
//        serie_ids.put(OP_AVG_DAY_60_TABLE, 113);
//        serie_ids.put(DF_7_300, 54);
//        serie_ids.put(DF_7_900, 54);
//        serie_ids.put(DF_7_3600, 54);
//        serie_ids.put(DF_7, 54);
//
//
//        1011 NDX
//        1012 SPX
//
//        304,ndx_7_300
//        54,ndx_7_300
//        306,ndx_7_3600
//        56,ndx_7_3600
//        55,ndx_7_900
//        305,ndx_7_900
//        49,ndx_7_speed_300
//        299,ndx_7_speed_300
//        300,ndx_7_speed_900
//        50,ndx_7_speed_900
//        40,ndx_d2d_diffs_mood_14400
//        311,ndx_d2d_diffs_mood_14400
//        41,ndx_d2d_diffs_mood_28800
//        312,ndx_d2d_diffs_mood_28800
//        24,ndx_df_1
//        284,ndx_df_1
//        285,ndx_df_2
//        25,ndx_df_2
//        26,ndx_df_3
//        286,ndx_df_3
//        27,ndx_df_4
//        287,ndx_df_4
//        28,ndx_df_5
//        288,ndx_df_5
//        289,ndx_df_6
//        29,ndx_df_6
//        30,ndx_df_7
//        290,ndx_df_7
//        291,ndx_df_8
//        31,ndx_df_8
//        310,ndx_diffs_mood_14400
//        39,ndx_diffs_mood_14400
//        36,ndx_diffs_mood_300
//        307,ndx_diffs_mood_300
//        38,ndx_diffs_mood_3600
//        309,ndx_diffs_mood_3600
//        308,ndx_diffs_mood_900
//        37,ndx_diffs_mood_900
//        45,ndx_diffs_mood_sum
//        295,ndx_diffs_mood_sum
//        2,ndx_futures_day
//        11,ndx_futures_e1
//        12,ndx_futures_e2
//        13,ndx_futures_month
//        14,ndx_futures_week
//        1,ndx_index


    }

    @Override
    protected void open_chart_on_start() {
        // todo
    }

    private void updateListsRetro() {
        insertListRetro(index_timestamp, tablesNames.get(INDEX_TABLE));
        insertListRetro(fut_day_timeStamp, tablesNames.get(FUT_DAY_TABLE));
        insertListRetro(fut_week_timeStamp, tablesNames.get(FUT_WEEK_TABLE));
        insertListRetro(fut_month_timeStamp, tablesNames.get(FUT_MONTH_TABLE));
        insertListRetro(fut_e1_timeStamp, tablesNames.get(FUT_Q1_TABLE));
        insertListRetro(fut_e2_timeStamp, tablesNames.get(FUT_Q2_TABLE));
        insertListRetro(baskets_timestamp, tablesNames.get(BASKETS_TABLE));
    }
}