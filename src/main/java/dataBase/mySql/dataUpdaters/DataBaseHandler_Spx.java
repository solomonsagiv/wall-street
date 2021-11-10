package dataBase.mySql.dataUpdaters;

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
    ArrayList<MyTimeStampObject> fut_week_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_month_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_e1_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_e2_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> ind_bid_ask_counter_timestamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_delta_timestamp = new ArrayList<>();

    double index_0 = 0;
    double index_bid_0 = 0;
    double index_ask_0 = 0;
    double fut_day_0 = 0;
    double fut_week_0 = 0;
    double fut_month_0 = 0;
    double fut_e1_0 = 0;
    double fut_e2_0 = 0;
    double index_bid_ask_counter_0 = 0;
    double fut_delta_0 = 0;

    E q1;

    public DataBaseHandler_Spx(BASE_CLIENT_OBJECT client) {
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
        if (sleep_count % 15000 == 0) {
            updateListsRetro();
        }

        // Insert tick speed
        if (sleep_count % 60000 == 0) {
            String fut_table_location = tablesNames.get(FUT_Q1_TABLE);
            String fut_tick_speed_table_location = tablesNames.get(FUT_E1_TICK_SPEED);
            insert_batch_data(tick_logic(load_uncalced_tick_speed_time(fut_table_location, fut_tick_speed_table_location)), fut_tick_speed_table_location);
        }

        // On changed data
        on_change_data();

        // Grab decisions
        if (sleep_count % 20000 == 0) {
            grab_decisions();
        }

        // Update count
        sleep_count += sleep;
    }

    private void on_change_data() {
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

        // Ind bid ask counter
        int ind_bid_ask_counter = client.getIndexBidAskCounter();

        if (ind_bid_ask_counter != index_bid_ask_counter_0) {
            double last_count = ind_bid_ask_counter - index_bid_ask_counter_0;
            index_bid_ask_counter_0 = ind_bid_ask_counter;
            if (last_count <= 1 || last_count >= -1) {
                ind_bid_ask_counter_timestamp.add(new MyTimeStampObject(Instant.now(), last_count));
            }
        }

        // Delta
        double fut_delta = q1.getDelta();

        if (fut_delta != fut_delta_0) {
            double last_count = fut_delta - fut_delta_0;
            fut_delta_0 = fut_delta;
            fut_delta_timestamp.add(new MyTimeStampObject(Instant.now(), last_count));
        }
    }


    @Override
    public void loadData() {

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

        // BID ASK COUNTER
        load_bid_ask_counter(MySql.Queries.get_sum(tablesNames.get(BID_ASK_COUNTER_TABLE)));

        //  FUT DELTA
        load_fut_delta(q1, MySql.Queries.get_sum(tablesNames.get(FUT_DELTA_TABLE)));

        // Load props
        load_properties();

        // Set load
        client.setLoadFromDb(true);
    }

    @Override
    public void initTablesNames() {
        tablesNames.put(INDEX_TABLE, "data.spx500_index");
        tablesNames.put(FUT_E1_TICK_SPEED, "data.spx500_fut_e1_tick_speed");
        tablesNames.put(INDEX_BID_TABLE, "data.spx500_index_bid");
        tablesNames.put(INDEX_ASK_TABLE, "data.spx500_index_ask");
        tablesNames.put(OP_AVG_DAY_TABLE, "sagiv.spx500_op_avg_day");
        tablesNames.put(OP_AVG_15_DAY_TABLE, "sagiv.spx500_op_avg_15_day");
        tablesNames.put(BID_ASK_COUNTER_TABLE, "data.spx500_index_bid_ask_counter_cdf");
        tablesNames.put(FUT_DAY_TABLE, "data.spx500_fut_day");
        tablesNames.put(FUT_WEEK_TABLE, "data.spx500_fut_week");
        tablesNames.put(FUT_MONTH_TABLE, "data.spx500_fut_month");
        tablesNames.put(FUT_Q1_TABLE, "data.spx500_fut_e1");
        tablesNames.put(FUT_Q2_TABLE, "data.spx500_fut_e2");
        tablesNames.put(FUT_DELTA_TABLE, "data.spx500_fut_delta_cdf");
    }

    @Override
    protected void open_chart_on_start() {
        // todo
    }

    private void updateListsRetro() {
        insertListRetro(index_timestamp, tablesNames.get(INDEX_TABLE));
        insertListRetro(index_bid_timestamp, tablesNames.get(INDEX_BID_TABLE));
        insertListRetro(index_ask_timestamp, tablesNames.get(INDEX_ASK_TABLE));
        insertListRetro(fut_day_timeStamp, tablesNames.get(FUT_DAY_TABLE));
        insertListRetro(fut_week_timeStamp, tablesNames.get(FUT_WEEK_TABLE));
        insertListRetro(fut_month_timeStamp, tablesNames.get(FUT_MONTH_TABLE));
        insertListRetro(fut_e1_timeStamp, tablesNames.get(FUT_Q1_TABLE));
        insertListRetro(fut_e2_timeStamp, tablesNames.get(FUT_Q2_TABLE));
        insertListRetro(ind_bid_ask_counter_timestamp, tablesNames.get(BID_ASK_COUNTER_TABLE));
        insertListRetro(fut_delta_timestamp, tablesNames.get(FUT_DELTA_TABLE));
    }
}