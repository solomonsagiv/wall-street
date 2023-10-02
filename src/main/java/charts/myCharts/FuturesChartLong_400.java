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

public class FuturesChartLong_400 extends MyChartCreator {

    // Constructor
    public FuturesChartLong_400(BASE_CLIENT_OBJECT client ) {
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


        // Future calc
        MyTimeSeries future_calc = new MyTimeSeries("Future calc", client) {

            @Override
            public double getValue() {
                double future_week  = client.getExps().getExp(ExpStrings.day).get_future();
                double future_q1  = client.getExps().getExp(ExpStrings.q1).get_future();
                return (future_week - (future_q1 - 3)) + client.getIndex();
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
        future_calc.setColor(Themes.GREEN);
        future_calc.setStokeSize(1.75f);




        // Bid
        MyTimeSeries bid = new MyTimeSeries("Index bid", client) {

            @Override
            public double getValue() {
                return client.getIndexBid();
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
        MyTimeSeries ask = new MyTimeSeries("Index ask", client) {

            @Override
            public double getValue() {
                return client.getIndexAsk();
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


        // Ask
        MyTimeSeries index_plus_avg = new MyTimeSeries("Index + avg 3600", client) {

            @Override
            public double getValue() {
                double avg = client.getExps().getExp(ExpStrings.q1).getOp_avg_60();
                return client.getIndex() + avg;
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
        index_plus_avg.setColor(Themes.BLUE);
        index_plus_avg.setStokeSize(1.4f);
        index_plus_avg.setVisible(false);


        // Ask
        MyTimeSeries index_plus_avg_900 = new MyTimeSeries("Index + avg 900", client) {

            @Override
            public double getValue() {
                double avg = client.getExps().getExp(ExpStrings.q1).getOp_avg_15();
                return client.getIndex() + avg;
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
        index_plus_avg_900.setColor(Themes.LIGHT_RED);
        index_plus_avg_900.setStokeSize(1.4f);
        index_plus_avg_900.setVisible(false);


        // Futures
        ArrayList<Color> greens = new ArrayList<>();
        greens.add(Themes.GREEN);
        greens.add(Themes.GREEN_LIGHT_4);
        greens.add(Themes.GREEN_LIGHT_4);
        greens.add(Themes.GREEN_LIGHT_4);
        greens.add(Themes.GREEN_LIGHT_4);

        int i = 0;

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

            myTimeSerie.setStokeSize(1.75f);
            myTimeSerie.setColor(greens.get(i));
            i++;
            myTimeSeries.add(myTimeSerie);

            // Is main exp set visible
            if (exp.getName().equals(ExpStrings.day)) {
                myTimeSerie.setVisible(true);
            } else {
                myTimeSerie.setVisible(false);
            }
        }

        myTimeSeries.add(index);
        myTimeSeries.add(bid);
        myTimeSeries.add(ask);
        myTimeSeries.add(index_plus_avg);
        myTimeSeries.add(index_plus_avg_900);
        myTimeSeries.add(future_calc);

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
