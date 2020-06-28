package charts.myCharts;

import charts.myChart.*;
import exp.Exp;
import exp.ExpEnum;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;

import java.awt.*;

public class IndexVsQuarterVSOpAvg15LiveChart extends MyChartCreator {

    // Constructor
    public IndexVsQuarterVSOpAvg15LiveChart( BASE_CLIENT_OBJECT client ) {
        super( client );
    }

    @Override
    public void createChart() {

        // Props
        props = new MyProps();
        props.setProp( ChartPropsEnum.SECONDS, 150 );
        props.setProp( ChartPropsEnum.IS_INCLUDE_TICKER, false );
        props.setProp( ChartPropsEnum.MARGIN, .17 );
        props.setProp( ChartPropsEnum.RANGE_MARGIN, 0.0 );
        props.setProp( ChartPropsEnum.IS_GRID_VISIBLE, false );
        props.setProp( ChartPropsEnum.IS_LOAD_DB, false );
        props.setProp( ChartPropsEnum.IS_LIVE, true );
        props.setProp( ChartPropsEnum.SLEEP, 200 );
        props.setProp( ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS, (double)INFINITE );
        props.setProp( ChartPropsEnum.SECONDS_ON_MESS, 10 );

        // ----- Chart 1 ----- //
        // Index
        MyTimeSeries index = new MyTimeSeries( "Index", client ) {
            @Override
            public double getData() {
                return client.getIndex();
            }
        };
        index.setColor( Color.BLACK );
        index.setStokeSize( 2.25f );

        // Bid
        MyTimeSeries bid = new MyTimeSeries( "Bid", client ) {
            @Override
            public double getData() {
                return client.getIndexBid();
            }
        };
        bid.setColor( Themes.BLUE );
        bid.setStokeSize( 2.25f );

        // Ask
        MyTimeSeries ask = new MyTimeSeries( "Ask", client ) {
            @Override
            public double getData() {
                return client.getIndexAsk();
            }
        };

        // Future
        MyTimeSeries quarter = new MyTimeSeries( "Quarter", client ) {
            @Override
            public double getData() {
                return client.getExps().getExp( ExpEnum.E1 ).getCalcFut();
            }
        };

        quarter.setColor( Themes.GREEN );
        quarter.setStokeSize( 2.25f );

        // OpAvg
        MyTimeSeries opAvg = new MyTimeSeries( "OpAvg15Future", client ) {
            @Override
            public double getData() {
                Exp exp = client.getExps().getExp( ExpEnum.E1 );
                return exp.getCalcFut() - exp.getOpAvgFut(900);
            }
        };

        opAvg.setColor( Themes.BLUE_LIGHT_2 );
        opAvg.setStokeSize( 2.25f );

        MyTimeSeries[] series = { index, bid, ask, quarter, opAvg };

        // Chart
        MyChart chart = new MyChart( client, series, props );

        // ----- Charts ----- //
        MyChart[] charts = { chart };

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer( client, charts, getClass().getName() );
        chartContainer.create();


    }

}

