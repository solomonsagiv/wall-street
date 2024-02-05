package DataUpdater;

import charts.timeSeries.MyTimeSeries;
import charts.timeSeries.TimeSeriesFactory;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Dax;
import serverObjects.indexObjects.Ndx;
import serverObjects.indexObjects.Spx;
import service.MyBaseService;
import java.util.ArrayList;

public class DataUpdaterService extends MyBaseService {

    ArrayList<MyTimeSeries> time_series;

    public DataUpdaterService(BASE_CLIENT_OBJECT client) {
        super(client);
        time_series = new ArrayList<>();

        if (client instanceof Spx) {
            // DOW
        }

        // Spx || Ndx
        if (client instanceof Spx || client instanceof Ndx) {
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.INDEX_AVG_3600));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.INDEX_AVG_900));

            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_2_CDF));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_9_CDF));

            // Week
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_WEEK_900));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_WEEK_3600));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_WEEK_240_CONTINUE));

            // Q1
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_Q1_900));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_Q1_3600));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_Q1_DAILY));

            // Q2
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_Q2_900));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_Q2_3600));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_Q2_DAILY));

            // Roll week q1
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.ROLL_WEEK_Q1_900));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.ROLL_WEEK_Q1_3600));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.ROLL_WEEK_Q1_DAILY));

            // Roll q1 q2
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.ROLL_Q1_Q2_900));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.ROLL_Q1_Q2_3600));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.ROLL_Q1_Q2_DAILY));

            // Week
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_2_WEEK));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.EXP_WEEK_START));

            // Month
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.EXP_MONTH_START));

            // Pre_day avg
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.PRE_DAY_OP_AVG));
        }

        if (client instanceof Ndx) {
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.BASKETS_WEEK));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.BASKETS_CDF));
        }

        // Dax
        if (client instanceof Dax) {
            // Index
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.INDEX_AVG_3600));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.INDEX_AVG_900));

            // Baskets
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.BASKETS_CDF));

            // DF
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_9_CDF));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_2_CDF));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_2_ROLL_CDF));

            // Week
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_WEEK_900));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_WEEK_3600));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_WEEK_DAILY));

            // Q1
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_Q1_900));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_Q1_3600));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_Q1_14400));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_Q1_DAILY));

            // Q2
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_Q2_900));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_Q2_3600));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_Q2_DAILY));

            // Week
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_2_WEEK));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.EXP_WEEK_START));

            // Month
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.EXP_MONTH_START));

            // Pre_day avg
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.PRE_DAY_OP_AVG));

            System.out.println();
        }
    }


    @Override
    public void go() {
        // Update timeseries
        update_timeseries_data();
    }
    
    private void update_timeseries_data() {
        for (MyTimeSeries ts : time_series) {
            try {
                 System.out.println(ts.getName() + " " + ts.getValue());

                ts.updateData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getName() {
        return client.getName() + " " + "Data updater service";
    }

    @Override
    public int getSleep() {
        return 15000;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        return str.toString();
    }
}
