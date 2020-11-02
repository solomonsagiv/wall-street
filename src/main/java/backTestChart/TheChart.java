package backTestChart;

import charts.myChart.*;
import locals.Themes;
import org.jfree.data.time.Second;
import serverObjects.BASE_CLIENT_OBJECT;

import java.awt.*;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TheChart extends MyChartCreator {

    MyTimeSeries index;
    MyTimeSeries bid;
    MyTimeSeries ask;
    MyTimeSeries fut;

    int year;
    int month;
    int day;

    Map< String, MyTimeSeries > seriesMap;

    // Constructor
    public TheChart( BASE_CLIENT_OBJECT client ) {
        super( client );
        crateSeries( );
    }

    // Constructor
    public TheChart( BASE_CLIENT_OBJECT client, int year, int month, int day ) {
        this( client );
        this.year = year;
        this.month = month;
        this.day = day;
    }
    
    private void crateSeries() {
        // Index
        index = new MyTimeSeries( "Index", client ) {
            @Override
            public double getData() throws UnknownHostException {
                return 0;
            }
        };
        index.setStokeSize( 1.5f );
        index.setColor( Color.BLACK );

        // Bid
        bid = new MyTimeSeries( "Bid", client ) {
            @Override
            public double getData() throws UnknownHostException {
                return 0;
            }
        };
        bid.setStokeSize( 1.5f );
        bid.setColor( Themes.BLUE );

        // Index
        ask = new MyTimeSeries( "Ask", client ) {
            @Override
            public double getData() throws UnknownHostException {
                return 0;
            }
        };
        ask.setStokeSize( 1.5f );
        ask.setColor( Themes.RED );

        // Index
        fut = new MyTimeSeries( "Fut", client ) {
            @Override
            public double getData() throws UnknownHostException {
                return 0;
            }
        };
        fut.setStokeSize( 1.5f );
        fut.setColor( Themes.GREEN );


        seriesMap = new HashMap<>( );
        seriesMap.put( "index", index );
        seriesMap.put( "bid", bid );
        seriesMap.put( "ask", ask );
        seriesMap.put( "fut", fut );
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
        series = new MyTimeSeries[ 4 ];
        series[ 0 ] = index;
        series[ 1 ] = bid;
        series[ 2 ] = ask;
        series[ 3 ] = fut;

        // Chart
        MyChart indexChart = new MyChart( client, series, props );

        // ----- Charts ----- //
        MyChart[] charts = { indexChart };

        // ----- Container ----- //
        MyChartContainer chartContainer = new MyChartContainer( client, charts, getClass( ).getName( ) );
        chartContainer.create( );

    }

    public void appendData( Map< String, ArrayList< Double > > map, ArrayList< LocalTime > dateTimeArray ) {
        for ( Map.Entry< String, ArrayList< Double > > entry : map.entrySet( ) ) {
            MyTimeSeries series = seriesMap.get( entry.getKey( ) );
            for ( int i = 0; i < entry.getValue( ).size( ); i++ ) {
                LocalTime time = dateTimeArray.get( i );
                Second second = new Second( time.getSecond( ), time.getMinute( ), time.getHour( ), day, month, year );
                series.addOrUpdate( second, entry.getValue( ).get( i ) );
            }
        }
    }

}
