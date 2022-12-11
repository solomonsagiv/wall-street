package dataBase.mySql.dataUpdaters;

import charts.timeSeries.TimeSeriesFactory;
import charts.timeSeries.TimeSeriesHandler;
import exp.E;
import exp.ExpStrings;
import serverObjects.BASE_CLIENT_OBJECT;

import java.time.Instant;
import java.util.ArrayList;

public class DataBaseHandler_Dax extends IDataBaseHandler {

    E q1, q2;

    ArrayList<MyTimeStampObject> baskets_timestamp = new ArrayList<>();

    double baskets_0 = 0;

    public DataBaseHandler_Dax(BASE_CLIENT_OBJECT client) {
        super(client);
        initTablesNames();
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

        // Baskets
        int basket = client.getBasketFinder_by_stocks().getBaskets();

        if (basket != baskets_0) {
            double last_count = basket - baskets_0;
            baskets_0 = basket;
            baskets_timestamp.add(new MyTimeStampObject(Instant.now(), last_count));
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
        serie_ids.put(TimeSeriesHandler.OP_AVG_WEEK, 5806);
        serie_ids.put(TimeSeriesHandler.OP_AVG_Q1, 6561);


        client.getTimeSeriesHandler().put(TimeSeriesFactory.BASKETS_CDF, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.BASKETS_CDF, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_Q1_15, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_Q1_15, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_Q1_60, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_Q1_60, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.OP_AVG_Q1, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_Q1, client));


        // Exp
        client.getTimeSeriesHandler().put(TimeSeriesFactory.EXP_WEEK_START, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.EXP_WEEK_START, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.EXP_MONTH_START, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.EXP_MONTH_START, client));
        client.getTimeSeriesHandler().put(TimeSeriesFactory.EXP_Q1_START, TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.EXP_Q1_START, client));
    }

    private void updateListsRetro() {
        insertListRetro(baskets_timestamp, serie_ids.get(TimeSeriesHandler.BASKETS));
    }
}