package dataBase.mySql.dataUpdaters;

import charts.timeSeries.TimeSeriesFactory;
import charts.timeSeries.TimeSeriesHandler;
import exp.E;
import exp.ExpStrings;
import serverObjects.BASE_CLIENT_OBJECT;

import java.time.Instant;
import java.util.ArrayList;

public class DataBaseHandler_Dax extends IDataBaseHandler {

    E q1, q2, week;

    ArrayList<MyTimeStampObject> index_timestamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> index_bid_synthetic_timestamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> index_ask_synthetic_timestamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> baskets_timestamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_e1_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_e2_timeStamp = new ArrayList<>();
//    ArrayList<MyTimeStampObject> fut_week_timeStamp = new ArrayList<>();
    
    double baskets_0 = 0;
    double index_bid_synthetic_0 = 0;
    double index_ask_synthetic_0 = 0;
    double index_0 = 0;
    double fut_e1_0 = 0;
    double fut_e2_0 = 0;
//    double fut_week_0 = 0;

    public DataBaseHandler_Dax(BASE_CLIENT_OBJECT client) {
        super(client);
        initTablesNames();
        q1 = (E) exps.getExp(ExpStrings.q1);
        q2 = (E) exps.getExp(ExpStrings.q2);
        week = (E) exps.getExp(ExpStrings.week);
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
//        double fut_week = week.get_future();
//
//        if (fut_week != fut_week_0) {
//            fut_week_0 = fut_week;
//
////                if (Math.abs(fut_week - fut_week_0) > 50) {
//            fut_week_timeStamp.add(new MyTimeStampObject(Instant.now(), fut_week_0));
////                }
//        }

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

//        serie_ids.put(TimeSeriesHandler.INDEX, 4369);
//        serie_ids.put(TimeSeriesHandler.INDEX_BID_SYNTHETIC, 9062);
//        serie_ids.put(TimeSeriesHandler.INDEX_ASK_SYNTHETIC, 9061);
//        serie_ids.put(TimeSeriesHandler.FUT_DAY, 4759);
//        serie_ids.put(TimeSeriesHandler.FUT_Q1, 4367);
//        serie_ids.put(TimeSeriesHandler.FUT_Q2, 4368);
//
//        serie_ids.put(TimeSeriesHandler.OP_AVG_Q1_15, 9185);
//        serie_ids.put(TimeSeriesHandler.OP_AVG_Q1_60, 9184);
//        serie_ids.put(TimeSeriesHandler.OP_AVG_240_CONTINUE, 9186);
//
////        serie_ids.put(TimeSeriesHandler.OP_AVG_Q1_15, 5632);
////        serie_ids.put(TimeSeriesHandler.OP_AVG_Q1_60, 5633);
//        serie_ids.put(TimeSeriesHandler.BASKETS, 5805);
//        serie_ids.put(TimeSeriesHandler.OP_AVG_Q1, 6561);
//        serie_ids.put(TimeSeriesHandler.OP_AVG_WEEK, 5806);
//
//        serie_ids.put(TimeSeriesHandler.STOXX_DF_8_ID, 9361);
//        serie_ids.put(TimeSeriesHandler.STOXX_RELATIVE_ID, 9379);
//        serie_ids.put(TimeSeriesHandler.CAC_DF_8_ID, 9431);
//        serie_ids.put(TimeSeriesHandler.CAC_RELATIVE_ID, 9442);
//
//        // DF 9 and relative
//        serie_ids.put(TimeSeriesHandler.DF_8, 9173);
//        serie_ids.put(TimeSeriesHandler.DF_8_RELATIVE, 9199);

        serie_ids.put(TimeSeriesHandler.INDEX, 4369);
        serie_ids.put(TimeSeriesHandler.INDEX_BID_SYNTHETIC, 9062);
        serie_ids.put(TimeSeriesHandler.INDEX_ASK_SYNTHETIC, 9061);
        serie_ids.put(TimeSeriesHandler.FUT_Q1, 9881);

        serie_ids.put(TimeSeriesHandler.OP_AVG_Q1_15, 9611);
        serie_ids.put(TimeSeriesHandler.OP_AVG_Q1_60, 9612);
        serie_ids.put(TimeSeriesHandler.OP_AVG_Q1_14400, 9508);

//        serie_ids.put(TimeSeriesHandler.OP_AVG_Q1_15, 5632);
//        serie_ids.put(TimeSeriesHandler.OP_AVG_Q1_60, 5633);
        serie_ids.put(TimeSeriesHandler.BASKETS, 9520);
        serie_ids.put(TimeSeriesHandler.OP_AVG_Q1, 9521);
        serie_ids.put(TimeSeriesHandler.OP_AVG_WEEK, 5806);

        serie_ids.put(TimeSeriesHandler.STOXX_DF_8_ID, 9422);
        serie_ids.put(TimeSeriesHandler.STOXX_RELATIVE_ID, 9432);

        serie_ids.put(TimeSeriesHandler.STOXX_OP_AVG_900, 9429);
        serie_ids.put(TimeSeriesHandler.STOXX_OP_AVG_3600, 9428);

        serie_ids.put(TimeSeriesHandler.CAC_DF_8_ID, 9394);
        serie_ids.put(TimeSeriesHandler.CAC_RELATIVE_ID, 9410);
        serie_ids.put(TimeSeriesHandler.CAC_OP_AVG_900, 9407);
        serie_ids.put(TimeSeriesHandler.CAC_OP_AVG_3600, 9406);

        serie_ids.put(TimeSeriesHandler.OP_AVG_Q2, 9600);
        serie_ids.put(TimeSeriesHandler.OP_AVG_Q2_15, 9605);

        // DF 9 and relative
        serie_ids.put(TimeSeriesHandler.DF_8, 9498);
        serie_ids.put(TimeSeriesHandler.DF_8_RELATIVE, 9510);

        // Roll
        serie_ids.put(TimeSeriesHandler.ROLL_900, 9546);
        serie_ids.put(TimeSeriesHandler.ROLL_3600, 9547);


        client.getTimeSeriesHandler().put(TimeSeriesFactory.BASKETS_CDF, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.BASKETS_CDF, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_Q1_15, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_Q1_15, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_Q1_60, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_Q1_60, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_Q1_14400, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_Q1_14400, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_Q1, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_Q1, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_Q2, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_Q2, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_Q2_15, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_Q2_15, client));


        client.getTimeSeriesHandler().put(TimeSeriesFactory.ROLL_900, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.ROLL_900, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.ROLL_3600, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.ROLL_3600, client));

        // Exp
        client.getTimeSeriesHandler().put(TimeSeriesFactory.EXP_WEEK_START, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.EXP_WEEK_START, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.EXP_MONTH_START, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.EXP_MONTH_START, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.EXP_Q1_START, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.EXP_Q1_START, client));

        client.getTimeSeriesHandler().put(TimeSeriesFactory.DF_8_RAW_900, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_8_RAW_900, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.DF_8_CDF, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_8_CDF, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.DF_8_RELATIVE, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_8_RELATIVE, client));

        client.getTimeSeriesHandler().put(TimeSeriesFactory.STOXX_DF_8_CDF, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.STOXX_DF_8_CDF, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.STOXX_RELATIVE, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.STOXX_RELATIVE, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.CAC_DF_8_CDF, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.CAC_DF_8_CDF, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.CAC_RELATIVE, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.CAC_RELATIVE, client));

        client.getTimeSeriesHandler().put(TimeSeriesFactory.CAC_OP_AVG_900, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.CAC_OP_AVG_900, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.CAC_OP_AVG_3600, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.CAC_OP_AVG_3600, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.STOXX_OP_AVG_900, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.STOXX_OP_AVG_900, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.STOXX_OP_AVG_3600, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.STOXX_OP_AVG_3600, client));

    }

    private void updateListsRetro() {
        insertListRetro(baskets_timestamp, serie_ids.get(TimeSeriesHandler.BASKETS));
        insertListRetro(index_timestamp, serie_ids.get(TimeSeriesHandler.INDEX));
        insertListRetro(index_bid_synthetic_timestamp, serie_ids.get(TimeSeriesHandler.INDEX_BID_SYNTHETIC));
        insertListRetro(index_ask_synthetic_timestamp, serie_ids.get(TimeSeriesHandler.INDEX_ASK_SYNTHETIC));
        insertListRetro(fut_e1_timeStamp, serie_ids.get(TimeSeriesHandler.FUT_Q1));
//        insertListRetro(fut_week_timeStamp, serie_ids.get(TimeSeriesHandler.FUT_DAY));
    }
}