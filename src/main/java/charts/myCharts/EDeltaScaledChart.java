package charts.myCharts;

import charts.myChart.*;
import exp.E;
import exp.ExpStrings;
import locals.Themes;
import org.jfree.chart.plot.ValueMarker;
import serverObjects.BASE_CLIENT_OBJECT;

import java.awt.*;

public class EDeltaScaledChart extends MyChartCreator {

    // Constructor
    public EDeltaScaledChart( BASE_CLIENT_OBJECT client ) {
        super( client );
    }

    @Override
    public void createChart() throws CloneNotSupportedException {

        MyTimeSeries[] series;

        // Props
        props = new MyProps( );
        props.setProp( ChartPropsEnum.SECONDS, INFINITE );
        props.setProp( ChartPropsEnum.IS_INCLUDE_TICKER, false );
        props.setProp( ChartPropsEnum.MARGIN, 0.005 );
        props.setProp( ChartPropsEnum.RANGE_MARGIN, 0.0 );
        props.setProp( ChartPropsEnum.IS_GRID_VISIBLE, true );
        props.setProp( ChartPropsEnum.IS_LOAD_DB, true );
        props.setProp( ChartPropsEnum.IS_LIVE, false );
        props.setProp( ChartPropsEnum.SLEEP, 1000 );
        props.setProp( ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS, INFINITE );
        props.setProp( ChartPropsEnum.SECONDS_ON_MESS, INFINITE );
        props.setProp( ChartPropsEnum.INCLUDE_DOMAIN_AXIS, true );
        props.setProp( ChartPropsEnum.SCALED_DATA, true );

        E e1 = ( E ) client.getExps( ).getExp( ExpStrings.e1 );

        // Index
        MyTimeSeries indexSeries = client.getIndexScaledSeries( );
        indexSeries.setColor( Color.BLACK );
        indexSeries.setStokeSize( 1.5f );

        // E1 Delta
        MyTimeSeries deltaSerie = e1.getDeltaScaledSerie( );
        deltaSerie.setColor( Themes.GREEN );
        deltaSerie.setStokeSize( 1.5f );

        series = new MyTimeSeries[ 2 ];
        series[ 0 ] = indexSeries;
        series[ 1 ] = deltaSerie;

        // Chart
        MyChart chart = new MyChart( client, series, props );

        // ----- Charts ----- //
        MyChart[] charts = { chart };

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer( client, charts, getClass( ).getName( ) );
        chartContainer.create( );

    }

}
