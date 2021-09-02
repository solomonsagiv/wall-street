package charts.myCharts;

import charts.myChart.*;
import charts.timeSeries.MyTimeSeries;
import exp.Exp;
import exp.Exps;
import locals.Themes;
import org.apache.commons.lang.StringUtils;
import serverObjects.BASE_CLIENT_OBJECT;

import java.awt.*;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.util.ArrayList;

public class FuturesChartLong extends MyChartCreator {

    // Constructor
    public FuturesChartLong(BASE_CLIENT_OBJECT client ) {
        super(client, null, null);
    }

    ArrayList<MyTimeSeries> myTimeSeries = new ArrayList<>();

    MyChart[] charts;

    @Override
    public void createChart() {

        Exps exps = client.getExps();

        // Props
        props = new MyProps();
        props.setProp(ChartPropsEnum.SECONDS, 1000);
        props.setProp(ChartPropsEnum.IS_INCLUDE_TICKER, -1);
        props.setProp(ChartPropsEnum.MARGIN, 0.003);
        props.setProp(ChartPropsEnum.IS_RANGE_GRID_VISIBLE, -1);
        props.setProp(ChartPropsEnum.IS_LOAD_DB, -1);
        props.setProp(ChartPropsEnum.IS_LIVE, 1);
        props.setProp(ChartPropsEnum.SLEEP, 100);
        props.setProp(ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS, INFINITE);
        props.setProp(ChartPropsEnum.SECONDS_ON_MESS, 10);

        // ----- Chart 1 ----- //
        // Index
        MyTimeSeries index = new MyTimeSeries("Index", client) {
            @Override
            public ResultSet load_last_x_time(int minuts) {
                return null;
            }

            @Override
            public double getData() {
                return client.getIndex();
            }

            @Override
            public void load() {

            }
        };
        index.setColor(Color.BLACK);
        index.setStokeSize(2.25f);

        // Bid
        MyTimeSeries bid = new MyTimeSeries("Index bid", client) {
            @Override
            public ResultSet load_last_x_time(int minuts) {
                return null;
            }

            @Override
            public double getData() {
                return client.getIndexBid();
            }

            @Override
            public void load() {

            }
        };
        bid.setColor(Themes.BLUE);
        bid.setStokeSize(2.25f);

        // Ask
        MyTimeSeries ask = new MyTimeSeries("Index ask", client) {
            @Override
            public ResultSet load_last_x_time(int minuts) {
                return null;
            }

            @Override
            public double getData() {
                return client.getIndexAsk();
            }

            @Override
            public void load() {

            }
        };
        ask.setColor(Themes.RED);
        ask.setStokeSize(2.25f);

        // Futures
        ArrayList<Color> greens = new ArrayList<>();
        greens.add(Themes.GREEN_LIGHT_4);
        greens.add(Themes.GREEN_LIGHT_3);
        greens.add(Themes.GREEN);
        greens.add(Themes.GREEN);
        greens.add(Themes.GREEN_LIGHT_3);

        int i = 0;

        for (Exp exp : exps.getExpList()) {

            MyTimeSeries myTimeSerie = new MyTimeSeries(StringUtils.capitalize(exp.getName()), client) {
                @Override
                public ResultSet load_last_x_time(int minuts) {
                    return null;
                }

                @Override
                public double getData() throws UnknownHostException {
                    return exp.get_future();
                }

                @Override
                public void load() {}
            };

            myTimeSerie.setStokeSize(2.25f);
            myTimeSerie.setColor(greens.get(i));
            i++;
            myTimeSeries.add(myTimeSerie);

            // Is main exp set visible
            if (client.getExps().getMainExp().getName().equals(exp.getName())) {
                myTimeSerie.setVisible(true);
            } else {
                myTimeSerie.setVisible(false);
            }
        }

        myTimeSeries.add(index);
        myTimeSeries.add(bid);
        myTimeSeries.add(ask);

        // Series
        MyTimeSeries[] series = toArray();

        // Chart
        MyChart chart = new MyChart(client, series, props);

        // ----- Charts ----- //
        MyChart[] charts = {chart};

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer(client, charts, getClass().getName());
        chartContainer.create();
    }

    private MyChart[] getCharts() {
        return charts;
    }

    private MyTimeSeries[] toArray() {
        MyTimeSeries[] arr = new MyTimeSeries[myTimeSeries.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = myTimeSeries.get(i);
        }
        return arr;
    }

}
