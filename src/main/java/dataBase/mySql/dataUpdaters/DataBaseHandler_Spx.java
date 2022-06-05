package dataBase.mySql.dataUpdaters;

import api.Manifest;
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
    ArrayList<MyTimeStampObject> fut_week_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_month_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_e1_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_e2_timeStamp = new ArrayList<>();

    double index_0 = 0;
    double index_bid_0 = 0;
    double index_ask_0 = 0;
    double fut_day_0 = 0;
    double fut_week_0 = 0;
    double fut_month_0 = 0;
    double fut_e1_0 = 0;
    double fut_e2_0 = 0;
    Exp day, week, month;
    E q1, q2;

    public DataBaseHandler_Spx(BASE_CLIENT_OBJECT client) {
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
        if (sleep_count % 15000 == 0) {
            updateListsRetro();
        }

        // On changed data
        on_change_data();

        // Update count
        sleep_count += sleep;
    }

    private void on_change_data() {

        if (Manifest.LIVE_DB) {

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
    }

    @Override
    public void loadData() {

        Exp day = exps.getExp(ExpStrings.day);
        Exp week = exps.getExp(ExpStrings.week);
        Exp month = exps.getExp(ExpStrings.month);
        Exp q1 = exps.getExp(ExpStrings.q1);
        Exp q2 = exps.getExp(ExpStrings.q2);

        load_op_avg(day, MySql.Queries.get_op_avg_mega(serie_ids.get(TimeSeriesHandler.INDEX_TABLE), serie_ids.get(TimeSeriesHandler.FUT_DAY_TABLE), MySql.AVG_TODAY));
        load_op_avg(week, MySql.Queries.get_op_avg_mega(serie_ids.get(TimeSeriesHandler.INDEX_TABLE), serie_ids.get(TimeSeriesHandler.FUT_WEEK_TABLE), MySql.AVG_TODAY));
        load_op_avg(month, MySql.Queries.get_op_avg_mega(serie_ids.get(TimeSeriesHandler.INDEX_TABLE), serie_ids.get(TimeSeriesHandler.FUT_MONTH_TABLE), MySql.AVG_TODAY));
        load_op_avg(q1, MySql.Queries.get_op_avg_mega(serie_ids.get(TimeSeriesHandler.INDEX_TABLE), serie_ids.get(TimeSeriesHandler.FUT_Q1_TABLE), MySql.AVG_TODAY));
        load_op_avg(q2, MySql.Queries.get_op_avg_mega(serie_ids.get(TimeSeriesHandler.INDEX_TABLE), serie_ids.get(TimeSeriesHandler.FUT_Q2_TABLE), MySql.AVG_TODAY));

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
        serie_ids.put(TimeSeriesHandler.INDEX_TABLE, 3);
        serie_ids.put(TimeSeriesHandler.INDEX_BID_TABLE, 16);
        serie_ids.put(TimeSeriesHandler.INDEX_ASK_TABLE, 15);
        serie_ids.put(TimeSeriesHandler.FUT_DAY_TABLE, 4);
        serie_ids.put(TimeSeriesHandler.FUT_WEEK_TABLE, 20);
        serie_ids.put(TimeSeriesHandler.FUT_MONTH_TABLE, 19);
        serie_ids.put(TimeSeriesHandler.FUT_Q1_TABLE, 17);
        serie_ids.put(TimeSeriesHandler.FUT_Q2_TABLE, 18);
        serie_ids.put(TimeSeriesHandler.OP_AVG_240_CONITNUE_TABLE, 117);
        serie_ids.put(TimeSeriesHandler.OP_AVG_DAY_5_TABLE, 116);
        serie_ids.put(TimeSeriesHandler.OP_AVG_DAY_60_TABLE, 115);
        serie_ids.put(TimeSeriesHandler.DF_7, 1028);
        serie_ids.put(TimeSeriesHandler.DF_7_300, 1042);
        serie_ids.put(TimeSeriesHandler.DF_7_900, 1043);
        serie_ids.put(TimeSeriesHandler.DF_2, 1023);
        serie_ids.put(TimeSeriesHandler.BASKETS_TABLE, 1418);
    }

    @Override
    protected void open_chart_on_start() {

    }

    private void updateListsRetro() {
        insertListRetro(index_timestamp, serie_ids.get(TimeSeriesHandler.INDEX_TABLE));
        insertListRetro(index_bid_timestamp, serie_ids.get(TimeSeriesHandler.INDEX_BID_TABLE));
        insertListRetro(index_ask_timestamp, serie_ids.get(TimeSeriesHandler.INDEX_ASK_TABLE));
        insertListRetro(fut_day_timeStamp, serie_ids.get(TimeSeriesHandler.FUT_DAY_TABLE));
        insertListRetro(fut_week_timeStamp, serie_ids.get(TimeSeriesHandler.FUT_WEEK_TABLE));
        insertListRetro(fut_month_timeStamp, serie_ids.get(TimeSeriesHandler.FUT_MONTH_TABLE));
        insertListRetro(fut_e1_timeStamp, serie_ids.get(TimeSeriesHandler.FUT_Q1_TABLE));
        insertListRetro(fut_e2_timeStamp, serie_ids.get(TimeSeriesHandler.FUT_Q2_TABLE));
    }
}