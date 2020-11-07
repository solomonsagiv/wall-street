package charts.myCharts;

import charts.myChart.*;
import exp.E;
import exp.ExpStrings;
import locals.Themes;
import org.jfree.chart.plot.ValueMarker;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.INDEX_CLIENT_OBJECT;

import java.awt.*;

public class EDeltaStocksDeltaChart extends MyChartCreator {

    INDEX_CLIENT_OBJECT client;

    // Constructor
    public EDeltaStocksDeltaChart( BASE_CLIENT_OBJECT client ) {
        super( client );
        this.client = ( INDEX_CLIENT_OBJECT ) client;
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

        E e1 = ( E ) client.getExps( ).getExp( ExpStrings.e1 );

        MyProps props1 = ( MyProps ) props.clone( );
        ValueMarker marker = new ValueMarker( 0 );
        marker.setPaint( Color.BLACK );
        marker.setStroke( new BasicStroke( 2f ) );
        props1.setProp( ChartPropsEnum.MARKER, marker );
        props1.setProp( ChartPropsEnum.INCLUDE_DOMAIN_AXIS, false );

        // --------- Delta ---------- //
        MyTimeSeries deltaSerie = e1.getDeltaSerie( );
        deltaSerie.setColor( Themes.GREEN );
        deltaSerie.setStokeSize( 1.5f );

        series = new MyTimeSeries[ 1 ];
        series[ 0 ] = deltaSerie;


        // Chart
        MyChart eDeltaChart = new MyChart( client, series, props1 );

        MyProps newProps = ( MyProps ) props.clone( );
        newProps.setProp( ChartPropsEnum.INCLUDE_DOMAIN_AXIS, false );


        // --------- Stocks Delta ---------- //
        MyTimeSeries stocksDelta = client.getStocksHandler( ).getDeltaSeries( );
        stocksDelta.setColor( Themes.PURPLE );
        stocksDelta.setStokeSize( 1.5f );

        series = new MyTimeSeries[ 1 ];
        series[ 0 ] = stocksDelta;

        // Chart
        MyChart stocksDeltaChart = new MyChart( client, series, props1 );

        // --------- Index ---------- //
        newProps.setProp( ChartPropsEnum.INCLUDE_DOMAIN_AXIS, false );

        // Index
        MyTimeSeries indexSeries = client.getIndexSeries( );
        indexSeries.setColor( Color.BLACK );
        indexSeries.setStokeSize( 1.5f );

        series = new MyTimeSeries[ 1 ];
        series[ 0 ] = indexSeries;

        // Chart
        MyChart indexChart = new MyChart( client, series, newProps );

        // -------------------- Chart -------------------- //

        // ----- Charts ----- //
        MyChart[] charts = { indexChart, eDeltaChart, stocksDeltaChart };

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer( client, charts, getClass( ).getName( ) );
        chartContainer.create( );

    }

}
