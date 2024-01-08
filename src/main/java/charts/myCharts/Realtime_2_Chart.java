package charts.myCharts;

import charts.myChart.*;
import charts.timeSeries.MyTimeSeries;
import exp.Exp;
import exp.ExpStrings;
import exp.Exps;
import locals.Themes;
import org.apache.commons.lang.StringUtils;
import serverObjects.BASE_CLIENT_OBJECT;

import java.awt.*;
import java.util.ArrayList;

public class Realtime_2_Chart extends MyChartCreator {

    // Constructor
    public Realtime_2_Chart(BASE_CLIENT_OBJECT client ) {
        super(client, null, null);
    }

    ArrayList<MyTimeSeries> myTimeSeries = new ArrayList<>();

    MyChart[] charts;

    @Override
    public void init() {

        Exps exps = client.getExps();

        // Props
        props = new MyProps();
        props.setProp(ChartPropsEnum.SECONDS, 400);
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
            public double getValue() {
                return client.getIndex();
            }

            @Override
            public void updateData() {

            }

            @Override
            public void load() {

            }

            @Override
            public void load_exp_data() {

            }
        };
        index.setColor(Color.BLACK);
        index.setStokeSize(1.75f);

        // Bid
        MyTimeSeries bid = new MyTimeSeries("Future bid", client) {

            @Override
            public double getValue() {
                return client.getExps().getExp(ExpStrings.q1).get_future() - 3;
            }

            @Override
            public void updateData() {

            }

            @Override
            public void load() {

            }

            @Override
            public void load_exp_data() {

            }
        };
        bid.setColor(Themes.BLUE);
        bid.setStokeSize(1.75f);
        bid.setVisible(false);

        // Ask
        MyTimeSeries ask = new MyTimeSeries("Future ask", client) {

            @Override
            public double getValue() {
                return client.getExps().getExp(ExpStrings.q1).get_future() + 3;
            }

            @Override
            public void updateData() {

            }

            @Override
            public void load() {

            }

            @Override
            public void load_exp_data() {

            }
        };
        ask.setColor(Themes.RED);
        ask.setStokeSize(1.75f);
        ask.setVisible(false);


        for (Exp exp : exps.getExpList()) {

            MyTimeSeries myTimeSerie = new MyTimeSeries(StringUtils.capitalize(exp.getName()), client) {

                @Override
                public double getValue() {
                    return exp.get_future();
                }

                @Override
                public void updateData() {

                }

                @Override
                public void load() {}

                @Override
                public void load_exp_data() {

                }
            };

            switch (exp.getName()) {
                case ExpStrings.day:myTimeSerie.setColor(Themes.GREEN);
                case ExpStrings.week:myTimeSerie.setColor(Themes.GREEN);
                case ExpStrings.q1:myTimeSerie.setColor(Themes.PURPLE);
                case ExpStrings.q2:myTimeSerie.setColor(Themes.GREEN);
            }

            myTimeSerie.setStokeSize(1.75f);
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
