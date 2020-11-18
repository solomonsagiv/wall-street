package backTestChart;

import charts.myChart.*;
import org.jfree.data.time.Second;
import serverObjects.BASE_CLIENT_OBJECT;

import java.awt.*;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TheChart extends MyChartCreator {

    int year;
    int month;
    int day;

    Map< String, MyTimeSeries > seriesMap;
    Map< String, ArrayList< Double > > dataMap;
    ArrayList< LocalTime > dateTimeArray;
    Color[] colors;
    String[] chartsNames;

    // Constructor
    public TheChart( BASE_CLIENT_OBJECT client, int year, int month, int day, Map< String, ArrayList< Double > > dataMap, Color[] colors, ArrayList< LocalTime > dateTimeArray, String[] chartsNames ) {
        super( client );
        this.dataMap = dataMap;
        this.year = year;
        this.month = month;
        this.day = day;
        seriesMap = new HashMap<>( );
        this.dateTimeArray = dateTimeArray;
        this.colors = colors;
        this.chartsNames = chartsNames;
        crateSeries( );
    }

    private void crateSeries() {

        for ( int i = 0; i < chartsNames.length; i++ ) {
            String chartName = chartsNames[ i ];
            Color color = colors[ i ];
            ArrayList< Double > list = dataMap.get( chartName );

            MyTimeSeries serie = new MyTimeSeries( chartName, null ) {
                @Override
                public double getData() throws UnknownHostException {
                    return 0;
                }
            };
            serie.setColor( color );
            serie.setStokeSize( 1.5f );

            // Append serie to seriesMap
            seriesMap.put( chartName, serie );


            // Append data
            for ( int j = 0; j < list.size( ); j++ ) {
                LocalTime time = dateTimeArray.get( j );
                Second second = new Second( time.getSecond( ), time.getMinute( ), time.getHour( ), day, month, year );
                serie.addOrUpdate( second, list.get( j ) );
            }
        }


    }

    @Override
    public void createChart() throws CloneNotSupportedException {

        MyTimeSeries[] series;

        // Props
        props = new MyProps( );
        props.setProp( ChartPropsEnum.SECONDS, INFINITE );
        props.setProp( ChartPropsEnum.IS_INCLUDE_TICKER, false );
        props.setProp( ChartPropsEnum.MARGIN, .17 );
        props.setProp( ChartPropsEnum.RANGE_MARGIN, 0.0 );
        props.setProp( ChartPropsEnum.IS_GRID_VISIBLE, false );
        props.setProp( ChartPropsEnum.IS_LOAD_DB, true );
        props.setProp( ChartPropsEnum.IS_LIVE, false );
        props.setProp( ChartPropsEnum.SLEEP, INFINITE );
        props.setProp( ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS, INFINITE );
        props.setProp( ChartPropsEnum.SECONDS_ON_MESS, INFINITE );
        props.setProp( ChartPropsEnum.INCLUDE_DOMAIN_AXIS, true );

        // Series
        series = new MyTimeSeries[ seriesMap.size( ) ];

        int i = 0;

        for ( Map.Entry< String, MyTimeSeries > entry : seriesMap.entrySet( ) ) {
            series[ i ] = entry.getValue( );
            i++;
        }


        // Chart
        MyChart indexChart = new MyChart( client, series, props );

        // ----- Charts ----- //
        MyChart[] charts = { indexChart };

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer( client, charts, getClass( ).getName( ) );
        chartContainer.create( );

    }

}
