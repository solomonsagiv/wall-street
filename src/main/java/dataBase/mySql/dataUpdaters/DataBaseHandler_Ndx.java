package dataBase.mySql.dataUpdaters;

import dataBase.mySql.MySql;
import exp.E;
import exp.Exp;
import exp.ExpStrings;
import jibeDataGraber.DecisionsFunc;
import jibeDataGraber.DecisionsFuncFactory;
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
    ArrayList<MyTimeStampObject> fut_delta_timestamp = new ArrayList<>();

    double index_0 = 0;
    double fut_day_0 = 0;
    double fut_week_0 = 0;
    double fut_month_0 = 0;
    double fut_e1_0 = 0;
    double fut_e2_0 = 0;
    double baskets_0 = 0;
    double fut_delta_0 = 0;

    E q1;

    public DataBaseHandler_Ndx(BASE_CLIENT_OBJECT client) {
        super(client);
        initTablesNames();
        q1 = (E) exps.getExp(ExpStrings.q1);
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

        // Index
        if (client.getIndex() != index_0) {
            index_0 = client.getIndex();
            index_timestamp.add(new MyTimeStampObject(Instant.now(), index_0));
        }

        // Fut day
        double fut_day = exps.getExp(ExpStrings.day).get_future();

        if (fut_day != fut_day_0) {
            fut_day_0 = fut_day;
            fut_day_timeStamp.add(new MyTimeStampObject(Instant.now(), fut_day_0));
        }

        // Fut week
        double fut_week = exps.getExp(ExpStrings.week).get_future();

        if (fut_week != fut_week_0) {
            fut_week_0 = fut_week;
            fut_week_timeStamp.add(new MyTimeStampObject(Instant.now(), fut_week_0));
        }

        // Fut month
        double fut_month = exps.getExp(ExpStrings.month).get_future();

        if (fut_month != fut_month_0) {
            fut_month_0 = fut_month;
            fut_month_timeStamp.add(new MyTimeStampObject(Instant.now(), fut_month_0));
        }

        // Fut e1
        double fut_e1 = exps.getExp(ExpStrings.q1).get_future();

        if (fut_e1 != fut_e1_0) {
            fut_e1_0 = fut_e1;
            fut_e1_timeStamp.add(new MyTimeStampObject(Instant.now(), fut_e1_0));
        }

        // Fut e2
        double fut_e2 = exps.getExp(ExpStrings.q2).get_future();

        if (fut_e2 != fut_e2_0) {
            fut_e2_0 = fut_e2;
            fut_e2_timeStamp.add(new MyTimeStampObject(Instant.now(), fut_e2_0));
        }

        // Baskets
        int basket = client.getBasketFinder().getBaskets();

        if ( basket != baskets_0 ) {
            double last_count = basket - baskets_0;
            baskets_0 = basket;
            baskets_timestamp.add(new MyTimeStampObject(Instant.now(), last_count));
        }

        // Baskets
        // Delta
        double fut_delta = q1.getDelta();

        if (fut_delta != fut_delta_0) {
            double last_count = fut_delta - fut_delta_0;
            fut_delta_0 = fut_delta;
            fut_delta_timestamp.add(new MyTimeStampObject(Instant.now(), last_count));
        }

        // Grab decisions
        if (sleep_count % 20000 == 0) {
            grab_decisions();
        }


        // Update count
        sleep_count += sleep;
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

        //  FUT DELTA
        load_fut_delta(q1, MySql.Queries.get_sum(tablesNames.get(FUT_DELTA_TABLE)));

        // Props
        load_properties();

        // Set load true
        client.setLoadFromDb(true);
    }

    private void grab_decisions() {
        DecisionsFunc df_n_5_func = client.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_N_5);
        DecisionsFunc df_5_func = client.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_5);
        DecisionsFunc df_n_15_func = client.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_N_15);
        DecisionsFunc df_15_func = client.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_15);
        DecisionsFunc df_n_60_func = client.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_N_60);
        DecisionsFunc df_60_func = client.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_60);
        DecisionsFunc df_n_day_func = client.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_N_DAY);

        df_n_5_func.setValue(handle_rs(MySql.Queries.get_sum(df_n_5_func.getTable_location())));
        df_5_func.setValue(handle_rs(MySql.Queries.get_sum(df_5_func.getTable_location())));
        df_n_15_func.setValue(handle_rs(MySql.Queries.get_sum(df_n_15_func.getTable_location())));
        df_15_func.setValue(handle_rs(MySql.Queries.get_sum(df_15_func.getTable_location())));
        df_n_60_func.setValue(handle_rs(MySql.Queries.get_sum(df_n_60_func.getTable_location())));
        df_60_func.setValue(handle_rs(MySql.Queries.get_sum(df_60_func.getTable_location())));
        df_n_day_func.setValue(handle_rs(MySql.Queries.get_sum(df_n_day_func.getTable_location())));
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
    }

    @Override
    protected void open_chart_on_start() {
        // todo
    }

    private void updateListsRetro() {
        insertListRetro(index_timestamp,tablesNames.get(INDEX_TABLE));
        insertListRetro(fut_day_timeStamp, tablesNames.get(FUT_DAY_TABLE));
        insertListRetro(fut_week_timeStamp, tablesNames.get(FUT_WEEK_TABLE));
        insertListRetro(fut_month_timeStamp,tablesNames.get(FUT_MONTH_TABLE));
        insertListRetro(fut_e1_timeStamp, tablesNames.get(FUT_Q1_TABLE));
        insertListRetro(fut_e2_timeStamp, tablesNames.get(FUT_Q2_TABLE));
        insertListRetro(baskets_timestamp, tablesNames.get(BASKETS_TABLE));
        insertListRetro(fut_delta_timestamp, tablesNames.get(FUT_DELTA_TABLE));
    }
}