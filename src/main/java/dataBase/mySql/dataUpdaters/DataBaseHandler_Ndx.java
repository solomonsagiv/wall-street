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
        tablesNames.put(INDEX_RACES_TABLE, "sagiv.ndx_index_races_cdf");
        tablesNames.put(FUT_RACES_TABLE, "sagiv.ndx_fut_races_cdf");
        tablesNames.put(BASKETS_TABLE, "data.ndx_baskets_cdf");
        tablesNames.put(FUT_DAY_TABLE, "data.ndx_fut_day");
        tablesNames.put(FUT_WEEK_TABLE, "data.ndx_fut_week");
        tablesNames.put(FUT_MONTH_TABLE, "data.ndx_fut_month");
        tablesNames.put(FUT_Q1_TABLE, "data.ndx_fut_e1");
        tablesNames.put(FUT_Q2_TABLE, "data.ndx_fut_e2");
        tablesNames.put(FUT_DELTA_TABLE, "data.ndx_fut_delta_cdf");
        tablesNames.put(E1_BID_ASK_COUNTER_TABLE, "data.ndx_e1_bid_ask_counter_cdf");
        tablesNames.put(CORR_15, "data.research_ndx_diffs_corr_mood_900");
        tablesNames.put(CORR_60, "data.research_ndx_diffs_corr_mood_3600");
        tablesNames.put(DE_CORR_15, "data.research_ndx_diffs_mood_900");
        tablesNames.put(DE_CORR_60, "data.research_ndx_diffs_mood_3600");
        tablesNames.put(OP_AVG_240_CONITNUE_TABLE, "data.ndx_op_avg_day_240_continue");
        tablesNames.put(OP_AVG_DAY_5_TABLE, "data.ndx_op_avg_day_5");
        tablesNames.put(OP_AVG_DAY_15_TABLE, "data.ndx_op_avg_day_15");
        tablesNames.put(OP_AVG_DAY_60_TABLE, "data.ndx_op_avg_day_60");
        tablesNames.put(CORR_MIX_CDF, "data.ndx_corr_mix_cdf");
        tablesNames.put(DE_CORR_MIX_CDF, "data.ndx_de_corr_mix_cdf");
        tablesNames.put(DECISION_FUNCTION_TABLE, "data.ndx_decision_func");
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