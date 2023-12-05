package dataBase.mySql.dataUpdaters;

import charts.timeSeries.TimeSeriesFactory;
import charts.timeSeries.TimeSeriesHandler;
import exp.E;
import exp.ExpStrings;
import serverObjects.BASE_CLIENT_OBJECT;

import java.time.Instant;
import java.util.ArrayList;

public class DataBaseHandler_Dax extends IDataBaseHandler {

    E q1, q2, week, month;

    ArrayList<MyTimeStampObject> index_timestamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> index_bid_synthetic_timestamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> index_ask_synthetic_timestamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> baskets_timestamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_e1_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_e2_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_week_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_month_timeStamp = new ArrayList<>();

    double baskets_0 = 0;
    double index_bid_synthetic_0 = 0;
    double index_ask_synthetic_0 = 0;
    double index_0 = 0;
    double fut_e1_0 = 0;
    double fut_e2_0 = 0;
    double fut_week_0 = 0;
    double fut_month_0 = 0;

    public DataBaseHandler_Dax(BASE_CLIENT_OBJECT client) {
        super(client);
        initTablesNames();
        q1 = (E) exps.getExp(ExpStrings.q1);
        q2 = (E) exps.getExp(ExpStrings.q2);
        week = (E) exps.getExp(ExpStrings.day);
        month = (E) exps.getExp(ExpStrings.month);
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

        // Baskets
        int basket = client.getBasketFinder_by_stocks().getBaskets();

        if (basket != baskets_0) {
            double last_count = basket - baskets_0;
            baskets_0 = basket;
            baskets_timestamp.add(new MyTimeStampObject(Instant.now(), last_count));
        }

//        // Fut week
        double fut_week = week.get_future();

        if (fut_week != fut_week_0) {

            if (Math.abs(fut_week - fut_week_0) < 15) {
                fut_week_timeStamp.add(new MyTimeStampObject(Instant.now(), fut_week_0));
            }
            fut_week_0 = fut_week;
        }

        // Fut month
        double fut_month = month.get_future();

        if (fut_month != fut_month_0) {

            if (Math.abs(fut_week - fut_month_0) < 15) {
                fut_month_timeStamp.add(new MyTimeStampObject(Instant.now(), fut_month_0));
            }
            fut_month_0 = fut_month;
        }

        // Is live db
        if (client.isLive_db()) {

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
                fut_e1_timeStamp.add(new MyTimeStampObject(Instant.now(), fut_e1_0));
            }

            // Fut e2
            double fut_e2 = q2.get_future();

            if (fut_e2 != fut_e2_0) {
                fut_e2_0 = fut_e2;
                fut_e2_timeStamp.add(new MyTimeStampObject(Instant.now(), fut_e2_0));
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

        serie_ids.put(TimeSeriesHandler.INDEX, 4369);
        serie_ids.put(TimeSeriesHandler.INDEX_AVG_3600, 4369);
        serie_ids.put(TimeSeriesHandler.INDEX_AVG_900, 4369);
        serie_ids.put(TimeSeriesHandler.INDEX_BID_SYNTHETIC, 9062);
        serie_ids.put(TimeSeriesHandler.INDEX_ASK_SYNTHETIC, 9061);
        serie_ids.put(TimeSeriesHandler.FUT_Q1, 9881);
        serie_ids.put(TimeSeriesHandler.FUT_WEEK, 9522);
        serie_ids.put(TimeSeriesHandler.FUT_MONTH, 9637);

        serie_ids.put(TimeSeriesHandler.BASKETS, 9520);

        // DF
        serie_ids.put(TimeSeriesHandler.DF_2, 9643);
        serie_ids.put(TimeSeriesHandler.DF_7, 9644);
        serie_ids.put(TimeSeriesHandler.DF_2_ROLL, 9652);
        serie_ids.put(TimeSeriesHandler.DF_9, 9498);

        // Week
        serie_ids.put(TimeSeriesHandler.OP_AVG_WEEK_900, 9615);
        serie_ids.put(TimeSeriesHandler.OP_AVG_WEEK_3600, 9616);
        serie_ids.put(TimeSeriesHandler.OP_AVG_WEEK_DAILY, 9665);

        // Q1
        serie_ids.put(TimeSeriesHandler.OP_AVG_Q1_900, 9611);
        serie_ids.put(TimeSeriesHandler.OP_AVG_Q1_3600, 9612);
        serie_ids.put(TimeSeriesHandler.OP_AVG_Q1_DAILY, 9662);

        // Q2
        serie_ids.put(TimeSeriesHandler.OP_AVG_Q2_900, 9605);
        serie_ids.put(TimeSeriesHandler.OP_AVG_Q2_3600, 9600);
        serie_ids.put(TimeSeriesHandler.OP_AVG_Q2_DAILY, 9663);

        // Roll week q1
        serie_ids.put(TimeSeriesHandler.ROLL_WEEK_Q1_900, 9546);
        serie_ids.put(TimeSeriesHandler.ROLL_WEEK_Q1_3600, 9547);
        serie_ids.put(TimeSeriesHandler.ROLL_WEEK_Q1_DAILY, 9672);


        // Roll q1 q2
        serie_ids.put(TimeSeriesHandler.ROLL_Q1_Q2_900, 9675);
        serie_ids.put(TimeSeriesHandler.ROLL_Q1_Q2_3600, 9678);
        serie_ids.put(TimeSeriesHandler.ROLL_Q1_Q2_DAILY, 9679);

        // Index
        client.getTimeSeriesHandler().put(TimeSeriesFactory.INDEX_AVG_3600, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.INDEX_AVG_3600, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.INDEX_AVG_900, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.INDEX_AVG_900, client));

        // Baskets
        client.getTimeSeriesHandler().put(TimeSeriesFactory.BASKETS_CDF, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.BASKETS_CDF, client));

        // DF
        client.getTimeSeriesHandler().put(TimeSeriesFactory.DF_9_CDF, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_9_CDF, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.DF_2_ROLL_CDF, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_2_ROLL_CDF, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.DF_2_CDF, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_2_CDF, client));

        // Week
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_WEEK_900, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_WEEK_900, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_WEEK_3600, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_WEEK_3600, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_WEEK_DAILY, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_WEEK_DAILY, client));

        // Q1
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_Q1_900, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_Q1_900, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_Q1_3600, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_Q1_3600, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_Q1_14400, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_Q1_14400, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_Q1_DAILY, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_Q1_DAILY, client));

        // Q2
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_Q2_DAILY, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_Q2_DAILY, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_Q2_900, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_Q2_900, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_Q2_3600, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_Q2_3600, client));

        // Roll week q1
        client.getTimeSeriesHandler().put(TimeSeriesFactory.ROLL_WEEK_Q1_900, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.ROLL_WEEK_Q1_900, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.ROLL_WEEK_Q1_3600, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.ROLL_WEEK_Q1_3600, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.ROLL_WEEK_Q1_DAILY, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.ROLL_WEEK_Q1_DAILY, client));

        // Roll q1 q2
        client.getTimeSeriesHandler().put(TimeSeriesFactory.ROLL_Q1_Q2_900, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.ROLL_Q1_Q2_900, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.ROLL_Q1_Q2_3600, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.ROLL_Q1_Q2_3600, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.ROLL_Q1_Q2_DAILY, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.ROLL_Q1_Q2_DAILY, client));

        // Exp Week
        client.getTimeSeriesHandler().put(TimeSeriesFactory.EXP_WEEK_START, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.EXP_WEEK_START, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.DF_2_WEEK, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_2_WEEK, client));
//        client.getTimeSeriesHandler().put(TimeSeriesFactory.DF_2_ROLL_WEEK, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_2_ROLL_WEEK, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.BASKETS_WEEK, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.BASKETS_WEEK, client));

        // Exp Month
        client.getTimeSeriesHandler().put(TimeSeriesFactory.EXP_MONTH_START, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.EXP_MONTH_START, client));

    }

    private void updateListsRetro() {
        insertListRetro(baskets_timestamp, serie_ids.get(TimeSeriesHandler.BASKETS));
        insertListRetro(index_timestamp, serie_ids.get(TimeSeriesHandler.INDEX));
        insertListRetro(index_bid_synthetic_timestamp, serie_ids.get(TimeSeriesHandler.INDEX_BID_SYNTHETIC));
        insertListRetro(index_ask_synthetic_timestamp, serie_ids.get(TimeSeriesHandler.INDEX_ASK_SYNTHETIC));
        insertListRetro(fut_e1_timeStamp, serie_ids.get(TimeSeriesHandler.FUT_Q1));
        insertListRetro(fut_week_timeStamp, serie_ids.get(TimeSeriesHandler.FUT_WEEK));
        insertListRetro(fut_month_timeStamp, serie_ids.get(TimeSeriesHandler.FUT_MONTH));
    }
}