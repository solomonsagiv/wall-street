package dataBase.mySql.dataUpdaters;

import dataBase.mySql.MySql;
import exp.Exp;
import exp.ExpStrings;
import exp.Exps;
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
    ArrayList<MyTimeStampObject> op_avg_fut_day_timestamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> op_avg_fut_day_15_timestamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> ind_races_timestamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_races_timestamp = new ArrayList<>();

    double index_0 = 0;
    double index_bid_0 = 0;
    double index_ask_0 = 0;
    double fut_day_0 = 0;
    double fut_week_0 = 0;
    double fut_month_0 = 0;
    double fut_e1_0 = 0;
    double fut_e2_0 = 0;
    double index_bid_ask_counter_0 = 0;
    double ind_races_0 = 0;
    double fut_races_0 = 0;

    Exps exps;

    public DataBaseHandler_Spx(BASE_CLIENT_OBJECT client) {
        super(client);
        initTablesNames();
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

        // Races ind counter
        int ind_races = client.getIndexSum();

        if (ind_races != ind_races_0) {
            double last_count = ind_races - ind_races_0;
            ind_races_0 = ind_races;
            if (last_count <= 1 || last_count >= -1) {
                ind_races_timestamp.add(new MyTimeStampObject(Instant.now(), last_count));
            }
        }

        // Races fut counter
        int fut_races = client.getFutSum();

        if (fut_races != fut_races_0) {
            double last_count = fut_races - fut_races_0;
            fut_races_0 = fut_races;

            if (last_count <= 1 || last_count >= -1) {
                fut_races_timestamp.add(new MyTimeStampObject(Instant.now(), last_count));
            }
        }

        // Update count
        sleep_count += sleep;
    }

    @Override
    public void loadData() {

        // OP AVG
        load_data_agg(MySql.Queries.op_query(tablesNames.get(INDEX_TABLE), tablesNames.get(FUT_DAY_TABLE)), client, client.getExps().getExp(ExpStrings.day), OP_AVG_TYPE);
        load_data_agg(MySql.Queries.op_query(tablesNames.get(INDEX_TABLE), tablesNames.get(FUT_WEEK_TABLE)), client, client.getExps().getExp(ExpStrings.week), OP_AVG_TYPE);
        load_data_agg(MySql.Queries.op_query(tablesNames.get(INDEX_TABLE), tablesNames.get(FUT_MONTH_TABLE)), client, client.getExps().getExp(ExpStrings.month), OP_AVG_TYPE);
        load_data_agg(MySql.Queries.op_query(tablesNames.get(INDEX_TABLE), tablesNames.get(FUT_Q1_TABLE)), client, client.getExps().getExp(ExpStrings.q1), OP_AVG_TYPE);
        load_data_agg(MySql.Queries.op_query(tablesNames.get(INDEX_TABLE), tablesNames.get(FUT_Q2_TABLE)), client, client.getExps().getExp(ExpStrings.q2), OP_AVG_TYPE);

        // BID ASK COUNTER
        load_data_agg(MySql.Queries.get_serie(tablesNames.get(BID_ASK_COUNTER_TABLE)), client, null, BID_ASK_COUNTER_TYPE);

        //  RACES
        load_data_agg(MySql.Queries.get_serie(tablesNames.get(INDEX_RACES_TABLE)), client, null, INDEX_RACES_TYPE);
        load_data_agg(MySql.Queries.get_serie(tablesNames.get(FUT_RACES_TABLE)), client, null, FUT_RACES_TYPE);

    }

    @Override
    public void initTablesNames() {
        tablesNames.put(INDEX_TABLE, "data.spx500_index");
        tablesNames.put(INDEX_BID_TABLE, "data.spx500_index_bid");
        tablesNames.put(INDEX_ASK_TABLE, "data.spx500_index_ask");
        tablesNames.put(OP_AVG_DAY_TABLE, "sagiv.spx500_op_avg_day");
        tablesNames.put(OP_AVG_15_DAY_TABLE, "sagiv.spx500_op_avg_15_day");
        tablesNames.put(BID_ASK_COUNTER_TABLE, "data.spx500_index_bid_ask_counter_cdf");
        tablesNames.put(INDEX_RACES_TABLE, "sagiv.spx500_index_races_cdf");
        tablesNames.put(FUT_RACES_TABLE, "sagiv.spx500_fut_races_cdf");
        tablesNames.put(FUT_DAY_TABLE, "data.spx500_fut_day");
        tablesNames.put(FUT_WEEK_TABLE, "data.spx500_fut_week");
        tablesNames.put(FUT_MONTH_TABLE, "data.spx500_fut_month");
        tablesNames.put(FUT_Q1_TABLE, "data.spx500_fut_e1");
        tablesNames.put(FUT_Q2_TABLE, "data.spx500_fut_e2");
    }

    @Override
    protected void open_chart_on_start() {
        // todo
    }

    @Override
    public void updateInterests() {
        for (Exp exp : client.getExps().getExpList()) {
            MySql.Queries.update_rates_query(client.getId_name(), exp.getName(),
                    exp.getInterest(), exp.getDividend(), exp.getDays_to_exp(), client.getBase());
        }
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
        insertListRetro(op_avg_fut_day_timestamp, tablesNames.get(OP_AVG_DAY_TABLE));
        insertListRetro(op_avg_fut_day_15_timestamp, tablesNames.get(OP_AVG_15_DAY_TABLE));
        insertListRetro(ind_races_timestamp, tablesNames.get(INDEX_RACES_TABLE));
        insertListRetro(fut_races_timestamp, tablesNames.get(FUT_RACES_TABLE));
    }
}