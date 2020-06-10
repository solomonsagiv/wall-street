package charts.myCharts;

import charts.myChart.*;
import exp.Exp;
import exp.ExpEnum;
import locals.Themes;
import options.Options;
import options.OptionsEnum;
import org.jfree.chart.plot.ValueMarker;
import serverObjects.BASE_CLIENT_OBJECT;

import java.awt.*;

public class OpAvg15Future_E2_IndexCounter_Index_Chart extends MyChartCreator {

    // Constructor
    public OpAvg15Future_E2_IndexCounter_Index_Chart( BASE_CLIENT_OBJECT client ) {
        super( client );
    }

    @Override
    public void createChart() throws CloneNotSupportedException {

        MyTimeSeries[] series;

        // Props
        props = new MyProps();
        props.setProp( ChartPropsEnum.SECONDS, INFINITE );
        props.setProp( ChartPropsEnum.IS_INCLUDE_TICKER, false );
        props.setProp( ChartPropsEnum.MARGIN, 0.0001 );
        props.setProp( ChartPropsEnum.RANGE_MARGIN, 0.0 );
        props.setProp( ChartPropsEnum.IS_GRID_VISIBLE, true );
        props.setProp( ChartPropsEnum.IS_LOAD_DB, true );
        props.setProp( ChartPropsEnum.IS_LIVE, false );
        props.setProp( ChartPropsEnum.SLEEP, 1000 );
        props.setProp( ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS, (double) INFINITE);
        props.setProp( ChartPropsEnum.SECONDS_ON_MESS, 10 );
        props.setProp( ChartPropsEnum.INCLUDE_DOMAIN_AXIS, true );

        Exp e1 = client.getExps().getExp( ExpEnum.E1 );

        // --------- OpAvgFuture 1 ---------- //

        MyProps opAvgFutureProps = ( MyProps ) props.clone();
        ValueMarker marker = new ValueMarker(0);
        marker.setPaint( Color.BLACK );
        marker.setStroke( new BasicStroke( 2f ) );
        opAvgFutureProps.setProp( ChartPropsEnum.MARKER, marker );
        opAvgFutureProps.setProp( ChartPropsEnum.INCLUDE_DOMAIN_AXIS, false );

        // Index
        MyTimeSeries opAvgFuture = new MyTimeSeries( "OpAvgFuture", Themes.PURPLE, 1.5f, opAvgFutureProps, e1.getOpAvg15FutureList() ) {
            @Override
            public double getData() {
                return e1.getOpAvgFuture(900);
            }
        };

        series = new MyTimeSeries[1];
        series[0] = opAvgFuture;

        // Chart
        MyChart opAvgFutureChart = new MyChart( client, series, opAvgFutureProps );


        MyProps newProps = ( MyProps ) props.clone();
        newProps.setProp( ChartPropsEnum.INCLUDE_DOMAIN_AXIS, false );

        // --------- Index 2 ---------- //
        newProps.setProp( ChartPropsEnum.INCLUDE_DOMAIN_AXIS, false );

        // Index
        MyTimeSeries index = new MyTimeSeries( "Index", Color.BLACK, 1.5f, newProps, client.getIndexList() ) {
            @Override
            public double getData() {
                return client.getIndex();
            }
        };

        series = new MyTimeSeries[1];
        series[0] = index;

        // Chart
        MyChart indexChart = new MyChart( client, series, newProps );

        // -------------------- Chart -------------------- //

        // ----- Charts ----- //
        MyChart[] charts = { indexChart, opAvgFutureChart };

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer( client, charts, getClass().getName() );
        chartContainer.create();

    }

}
