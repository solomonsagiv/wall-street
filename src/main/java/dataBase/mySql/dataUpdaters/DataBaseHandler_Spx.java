package dataBase.mySql.dataUpdaters;

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

        if (client.isLive_db()) {

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
        
        serie_ids.put(TimeSeriesHandler.INDEX, 9470);
        serie_ids.put(TimeSeriesHandler.INDEX_AVG_3600, 9470);
        serie_ids.put(TimeSeriesHandler.INDEX_AVG_900, 9470);
        serie_ids.put(TimeSeriesHandler.INDEX_BID, 16);
        serie_ids.put(TimeSeriesHandler.INDEX_ASK, 15);
        serie_ids.put(TimeSeriesHandler.FUT_DAY, 4);
        serie_ids.put(TimeSeriesHandler.FUT_Q1, 9531);
        serie_ids.put(TimeSeriesHandler.FUT_Q2, 9532);
        serie_ids.put(TimeSeriesHandler.OP_AVG_240_CONTINUE, 9474);
        serie_ids.put(TimeSeriesHandler.OP_AVG_5, 9471);
        serie_ids.put(TimeSeriesHandler.OP_AVG_15, 9473);
        serie_ids.put(TimeSeriesHandler.OP_AVG_60, 9472);
        serie_ids.put(TimeSeriesHandler.DF_7, 9526);
        serie_ids.put(TimeSeriesHandler.DF_2, 9525);//1023
        serie_ids.put(TimeSeriesHandler.BASKETS, 9519);
        serie_ids.put(TimeSeriesHandler.OP_AVG_DAY, 9514);
        serie_ids.put(TimeSeriesHandler.DF_8, 9466);// Dynamic
        serie_ids.put(TimeSeriesHandler.DF_8_900, 9466);
        serie_ids.put(TimeSeriesHandler.DF_8_RELATIVE, 9476);
        serie_ids.put(TimeSeriesHandler.DOW_DF_8_ID, 9411);
        serie_ids.put(TimeSeriesHandler.DOW_RELATIVE_ID, 9421);
        serie_ids.put(TimeSeriesHandler.ROLL_900, 9538);
        serie_ids.put(TimeSeriesHandler.ROLL_3600, 9537);
        serie_ids.put(TimeSeriesHandler.OP_AVG_Q1, 9578);
        serie_ids.put(TimeSeriesHandler.OP_AVG_Q1_15, 9602);
        serie_ids.put(TimeSeriesHandler.OP_AVG_Q2, 9577);
        serie_ids.put(TimeSeriesHandler.OP_AVG_Q2_15, 9604);


        client.getTimeSeriesHandler().put(TimeSeriesFactory.INDEX_AVG_3600, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.INDEX_AVG_3600, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.INDEX_AVG_900, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.INDEX_AVG_900, client));

        client.getTimeSeriesHandler().put(TimeSeriesFactory.DF_2_CDF, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_2_CDF, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.DF_7_CDF, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_7_CDF, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.DF_8_CDF, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_8_CDF, client));

        client.getTimeSeriesHandler().put(TimeSeriesFactory.DF_8_RAW_900, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_8_RAW_900, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.DF_8_RAW_3600, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_8_RAW_3600, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.DF_8_RELATIVE, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_8_RELATIVE, client));

        client.getTimeSeriesHandler().put(TimeSeriesFactory.BASKETS_CDF, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.BASKETS_CDF, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_DAY, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_DAY, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_DAY_15, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_DAY_15, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_DAY_60, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_DAY_60, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_240_CONTINUE, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_240_CONTINUE, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_Q1, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_Q1, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_Q1_60, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_Q1_60, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_Q2, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_Q2, client));

        client.getTimeSeriesHandler().put(TimeSeriesFactory.ROLL_900, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.ROLL_900, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.ROLL_3600, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.ROLL_3600, client));

        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_Q1_15, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_Q1_15, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_Q2_15, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_Q2_15, client));


        client.getTimeSeriesHandler().put(TimeSeriesFactory.DOW_DF_8_CDF, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DOW_DF_8_CDF, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.DOW_RELATIVE, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DOW_RELATIVE, client));

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