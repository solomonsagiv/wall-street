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
        time_series.add(TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_2, client));
        time_series.add(TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_7, client));
        time_series.add(TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.DF_7_300, client));
        time_series.add(TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.BASKETS, client));
        time_series.add(TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_DAY, client));
        time_series.add(TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_WEEK, client));
        time_series.add(TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_MONTH, client));
        time_series.add(TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_Q1, client));
        time_series.add(TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_Q2, client));
        time_series.add(TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_DAY_5, client));
        time_series.add(TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_DAY_60, client));
        time_series.add(TimeSeriesFactory.getTimeSeries(TimeSeriesFactory.OP_AVG_240_CONTINUE, client));
    }

    @Override
    public void go() {
        // Update timeseries
        update_timeseries_data();
    }

    private void update_timeseries_data() {
        for (MyTimeSeries ts : time_series) {
            try {
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
