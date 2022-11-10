package dataBase.mySql.dataUpdaters;

import api.Manifest;
import charts.timeSeries.TimeSeriesFactory;
import charts.timeSeries.TimeSeriesHandler;
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
    
    double index_0 = 0;
    double index_bid_0 = 0;
    double index_ask_0 = 0;
    double fut_day_0 = 0;
    double fut_e1_0 = 0;
    double fut_e2_0 = 0;
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

        serie_ids.put(TimeSeriesHandler.INDEX, 3);
        serie_ids.put(TimeSeriesHandler.INDEX_BID, 16);
        serie_ids.put(TimeSeriesHandler.INDEX_ASK, 15);
        serie_ids.put(TimeSeriesHandler.FUT_DAY, 4);
        serie_ids.put(TimeSeriesHandler.FUT_Q1, 17);
        serie_ids.put(TimeSeriesHandler.FUT_Q2, 18);
        serie_ids.put(TimeSeriesHandler.OP_AVG_240_CONITNUE, 1034);
        serie_ids.put(TimeSeriesHandler.OP_AVG_DAY_5, 1030);
        serie_ids.put(TimeSeriesHandler.OP_AVG_DAY_15, 1030);
        serie_ids.put(TimeSeriesHandler.OP_AVG_DAY_60, 1032);
        serie_ids.put(TimeSeriesHandler.DF_7, 1028);
        serie_ids.put(TimeSeriesHandler.DF_2, 1022);//1023
        serie_ids.put(TimeSeriesHandler.BASKETS, 1418);
        serie_ids.put(TimeSeriesHandler.OP_AVG_DAY, 1899);
        serie_ids.put(TimeSeriesHandler.OP_AVG_WEEK, 1900);
        serie_ids.put(TimeSeriesHandler.OP_AVG_MONTH, 1901);
        serie_ids.put(TimeSeriesHandler.OP_AVG_Q1, 1902);
        serie_ids.put(TimeSeriesHandler.OP_AVG_Q2, 1903);
        serie_ids.put(TimeSeriesHandler.DF_8, 1029);
        serie_ids.put(TimeSeriesHandler.DF_WEEK, 3756);
        serie_ids.put(TimeSeriesHandler.DF_MONTH, 3757);
        serie_ids.put(TimeSeriesHandler.DF_WEIGHTED, 3850);
        serie_ids.put(TimeSeriesHandler.STD_MOVE, 3753);
        serie_ids.put(TimeSeriesHandler.OP_AVG_DAY_15, 1031);
        serie_ids.put(TimeSeriesHandler.DF_8_900, 4888);
        serie_ids.put(TimeSeriesHandler.DF_8_3600, 5284);


        client.getTimeSeriesHandler().put(TimeSeriesFactory.DF_2_CDF, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_2_CDF, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.DF_7_CDF, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_7_CDF, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.DF_8_CDF, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_8_CDF, client));

        client.getTimeSeriesHandler().put(TimeSeriesFactory.DF_8_RAW_900, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_8_RAW_900, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.DF_8_RAW_3600, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_8_RAW_3600, client));



        client.getTimeSeriesHandler().put(TimeSeriesFactory.BASKETS_CDF, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.BASKETS_CDF, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_DAY, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_DAY, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_DAY_5, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_DAY_5, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_DAY_15, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_DAY_15, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_DAY_60, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_DAY_60, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_240_CONTINUE, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_240_CONTINUE, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_Q1, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_Q1, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_Q2, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_Q2, client));

        client.getTimeSeriesHandler().put(TimeSeriesFactory.DF_2_WEEK, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_2_WEEK, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.DF_2_MONTH, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_2_MONTH, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.DF_2_Q1, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_2_Q1, client));

        client.getTimeSeriesHandler().put(TimeSeriesFactory.DF_7_WEEK, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_7_WEEK, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.DF_7_MONTH, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_7_MONTH, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.DF_7_Q1, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_7_Q1, client));

        client.getTimeSeriesHandler().put(TimeSeriesFactory.DF_8_WEEK, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_8_WEEK, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.DF_8_MONTH, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_8_MONTH, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.DF_8_Q1, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_8_Q1, client));

        // Exp
        client.getTimeSeriesHandler().put(TimeSeriesFactory.EXP_WEEK_START, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.EXP_WEEK_START, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.EXP_MONTH_START, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.EXP_MONTH_START, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.EXP_Q1_START, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.EXP_Q1_START, client));

        // JUPITER
        client.getTimeSeriesHandler().put(TimeSeriesFactory.DF_WEEK, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_WEEK, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.DF_MONTH, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_MONTH, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.DF_WEIGHTED, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_WEIGHTED, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.STD_MOVE, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.STD_MOVE, client));
    }

    private void updateListsRetro() {
        insertListRetro(index_timestamp, serie_ids.get(TimeSeriesHandler.INDEX));
        insertListRetro(index_bid_timestamp, serie_ids.get(TimeSeriesHandler.INDEX_BID));
        insertListRetro(index_ask_timestamp, serie_ids.get(TimeSeriesHandler.INDEX_ASK));
        insertListRetro(fut_day_timeStamp, serie_ids.get(TimeSeriesHandler.FUT_DAY));
        insertListRetro(fut_e1_timeStamp, serie_ids.get(TimeSeriesHandler.FUT_Q1));
        insertListRetro(fut_e2_timeStamp, serie_ids.get(TimeSeriesHandler.FUT_Q2));
    }
}