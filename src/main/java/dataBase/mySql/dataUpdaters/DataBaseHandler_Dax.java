package dataBase.mySql.dataUpdaters;

import api.Manifest;
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
    ArrayList<MyTimeStampObject> baskets_timestamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_e1_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_week_timeStamp = new ArrayList<>();

    double baskets_0 = 0;
    double index_0 = 0;
    double fut_e1_0 = 0;
    double fut_week_0 = 0;

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

        if (Manifest.LIVE_DB) {
            // Baskets
            int basket = client.getBasketFinder_by_stocks().getBaskets();

            if (basket != baskets_0) {
                double last_count = basket - baskets_0;
                baskets_0 = basket;
                baskets_timestamp.add(new MyTimeStampObject(Instant.now(), last_count));
            }

            // Index
            if (client.getIndex() != index_0) {
                index_0 = client.getIndex();
                index_timestamp.add(new MyTimeStampObject(Instant.now(), index_0));
            }

            // Fut e1
            double fut_e1 = q1.get_future();

            if (fut_e1 != fut_e1_0) {
                fut_e1_0 = fut_e1;
                fut_e1_timeStamp.add(new MyTimeStampObject(Instant.now(), fut_e1_0));
            }

            // Fut week
            double fut_week = week.get_future();

            if (fut_week != fut_week_0) {
                fut_week_0 = fut_week;

                if (Math.abs(fut_week - fut_week_0) > 50) {
                    fut_week_timeStamp.add(new MyTimeStampObject(Instant.now(), fut_week_0));
                }
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
        serie_ids.put(TimeSeriesHandler.FUT_DAY, 4759);
        serie_ids.put(TimeSeriesHandler.FUT_Q1, 4367);
        serie_ids.put(TimeSeriesHandler.FUT_Q2, 4368);

        serie_ids.put(TimeSeriesHandler.OP_AVG_Q1_15, 5632);
        serie_ids.put(TimeSeriesHandler.OP_AVG_Q1_60, 5633);
        serie_ids.put(TimeSeriesHandler.BASKETS, 5805);
        serie_ids.put(TimeSeriesHandler.OP_AVG_Q1, 6561);
        serie_ids.put(TimeSeriesHandler.OP_AVG_WEEK, 5806);



        client.getTimeSeriesHandler().put(TimeSeriesFactory.BASKETS_CDF, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.BASKETS_CDF, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_Q1_15, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_Q1_15, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_Q1_60, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_Q1_60, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_Q1, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_Q1, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_WEEK, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_WEEK, client));

        // Exp
        client.getTimeSeriesHandler().put(TimeSeriesFactory.EXP_WEEK_START, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.EXP_WEEK_START, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.EXP_MONTH_START, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.EXP_MONTH_START, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.EXP_Q1_START, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.EXP_Q1_START, client));
    }

    private void updateListsRetro() {
        insertListRetro(baskets_timestamp, serie_ids.get(TimeSeriesHandler.BASKETS));
        insertListRetro(index_timestamp, serie_ids.get(TimeSeriesHandler.INDEX));
        insertListRetro(fut_e1_timeStamp, serie_ids.get(TimeSeriesHandler.FUT_Q1));
    }
}