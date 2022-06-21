package DataUpdater;

import charts.timeSeries.MyTimeSeries;
import charts.timeSeries.TimeSeriesFactory;
import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;
import java.util.ArrayList;

public class DataUpdaterService extends MyBaseService {

    ArrayList<MyTimeSeries> time_series;

    public DataUpdaterService(BASE_CLIENT_OBJECT client) {
        super(client);
        time_series = new ArrayList<>();
        time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_2_300_RAW));
        time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_2_3600_RAW));
        time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_7_300_RAW));
        time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_7_3600_RAW));
        time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_2_CDF));
        time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_7_CDF));
        time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.BASKETS_CDF));
        time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_DAY));
        time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_Q1));
        time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_Q2));
        time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_DAY_5));
        time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_DAY_60));
        time_series.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_240_CONTINUE));
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
