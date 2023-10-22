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
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_8_CDF));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.BASKETS_CDF));
//            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_DAY));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_8_RELATIVE));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_Q1_15));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_Q2));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_Q2_15));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_DAY_15));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_240_CONTINUE));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.ROLL_900));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.ROLL_3600));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_2_CDF));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_MONTH_15));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_MONTH_60));
        }

        // Dax
        if (client instanceof Dax) {
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_8_CDF));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.BASKETS_CDF));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_Q1_15));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_Q1_60));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.ROLL_60));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.ROLL_900));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.ROLL_3600));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_Q2));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_Q2_15));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_DAY_15));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_8_RELATIVE));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_MONTH_15));
            time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_MONTH_60));

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
