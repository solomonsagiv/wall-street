package charts.myCharts;

import charts.myChart.*;
import exp.ExpStrings;
import locals.L;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;

import java.awt.*;
import java.util.Random;
import java.util.Scanner;

public class IndexVsQuarterQuarterFarLiveChart extends MyChartCreator {


    // Constructor
    public IndexVsQuarterQuarterFarLiveChart(BASE_CLIENT_OBJECT client) {
        super(client);
    }

    public static void main(String[] args) throws InterruptedException {
        Spx spx = Spx.getInstance();
        IndexVsQuarterQuarterFarLiveChart testNewChart = new IndexVsQuarterQuarterFarLiveChart(spx);
        testNewChart.createChart();

        while (true) {

            System.out.println("Enter future: ");
            String input = new Scanner(System.in).nextLine();

            double d = new Random().nextDouble() * 10;

            if (!input.isEmpty()) {
                d = L.dbl(input);
            }

            spx.setIndex(d);
            spx.setIndexBid(d - 2);
            spx.setIndexAsk(d + 1);

            Thread.sleep(200);
        }

    }

    @Override
    public void createChart() {

        // Props
        props = new MyProps();
        props.setProp(ChartPropsEnum.SECONDS, 150);
        props.setProp(ChartPropsEnum.IS_INCLUDE_TICKER, false);
        props.setProp(ChartPropsEnum.MARGIN, .17);
        props.setProp(ChartPropsEnum.RANGE_MARGIN, 0.0);
        props.setProp(ChartPropsEnum.IS_GRID_VISIBLE, false);
        props.setProp(ChartPropsEnum.IS_LOAD_DB, false);
        props.setProp(ChartPropsEnum.IS_LIVE, true);
        props.setProp(ChartPropsEnum.SLEEP, 200);
        props.setProp(ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS, INFINITE);
        props.setProp(ChartPropsEnum.SECONDS_ON_MESS, 10);

        // ----- Chart 1 ----- //
        // Index
        MyTimeSeries index = new MyTimeSeries("Index", client) {
            @Override
            public double getData() {
                return client.getIndex();
            }
        };
        index.setColor(Color.BLACK);
        index.setStokeSize(2.25f);

        // Bid
        MyTimeSeries bid = new MyTimeSeries("Bid", client) {
            @Override
            public double getData() {
                return client.getIndexBid();
            }
        };
        bid.setColor(Themes.BLUE);
        bid.setStokeSize(2.25f);

        // Ask
        MyTimeSeries ask = new MyTimeSeries("Ask", client) {
            @Override
            public double getData() {
                return client.getIndexAsk();
            }
        };

        // Future
        MyTimeSeries quarter = new MyTimeSeries("Quarter", client) {
            @Override
            public double getData() {
                return client.getExps().getExp(ExpStrings.e1).getFuture();
            }
        };

        quarter.setColor(Themes.GREEN);
        quarter.setStokeSize(2.25f);

        // Future
        MyTimeSeries quarterFar = new MyTimeSeries("QuarterFar", client) {
            @Override
            public double getData() {
                return client.getExps().getExp(ExpStrings.e2).getFuture();
            }
        };

        quarterFar.setColor(Themes.VERY_LIGHT_BLUE);
        quarterFar.setStokeSize(2.25f);

        MyTimeSeries[] series = {index, bid, ask, quarter, quarterFar};

        // Chart
        MyChart chart = new MyChart(client, series, props);

        // ----- Charts ----- //
        MyChart[] charts = {chart};

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer(client, charts, getClass().getName());
        chartContainer.create();


    }

}
