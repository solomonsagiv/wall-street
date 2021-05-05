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
import java.util.ArrayList;

public class FuturesChart extends MyChartCreator {

    // Constructor
    public FuturesChart(BASE_CLIENT_OBJECT client, MyChartContainer_2 myChartContainer_2, MyChart[] charts ) {
        super(client, charts, null);
    }

    ArrayList<MyTimeSeries> myTimeSeries = new ArrayList<>();

    MyChart[] charts;

    @Override
    public void createChart() {

        Exps exps = client.getExps();

        // Props
        props = new MyProps();
        props.setProp(ChartPropsEnum.SECONDS, 200);
        props.setProp(ChartPropsEnum.IS_INCLUDE_TICKER, -1);
        props.setProp(ChartPropsEnum.MARGIN, .17);
        props.setProp(ChartPropsEnum.RANGE_MARGIN, 0.0);
        props.setProp(ChartPropsEnum.IS_GRID_VISIBLE, -1);
        props.setProp(ChartPropsEnum.IS_LOAD_DB, -1);
        props.setProp(ChartPropsEnum.IS_LIVE, 1);
        props.setProp(ChartPropsEnum.SLEEP, 100);
        props.setProp(ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS, INFINITE);
        props.setProp(ChartPropsEnum.SECONDS_ON_MESS, 10);

        // ----- Chart 1 ----- //
        // Index
        MyTimeSeries index = new MyTimeSeries("Index", client) {
            @Override
            public double getData() {
                return client.getIndex();
            }

            @Override
            public void load_data() {

            }
        };
        index.setColor(Color.BLACK);
        index.setStokeSize(2.25f);

        // Bid
        MyTimeSeries bid = new MyTimeSeries("Index bid", client) {
            @Override
            public double getData() {
                return client.getIndexBid();
            }

            @Override
            public void load_data() {

            }
        };
        bid.setColor(Themes.BLUE);
        bid.setStokeSize(2.25f);

        // Ask
        MyTimeSeries ask = new MyTimeSeries("Index ask", client) {
            @Override
            public double getData() {
                return client.getIndexAsk();
            }

            @Override
            public void load_data() {

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
                public double getData() throws UnknownHostException {
                    return exp.get_future();
                }

                @Override
                public void load_data() {}
            };

            myTimeSerie.setStokeSize(2.25f);
            myTimeSerie.setColor(greens.get(i));
            i++;
            myTimeSeries.add(myTimeSerie);

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
