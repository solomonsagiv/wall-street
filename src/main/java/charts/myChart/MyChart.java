package charts.myChart;

import charts.MyChartPanel;
import locals.L;
import locals.Themes;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.DateRange;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleInsets;
import serverObjects.BASE_CLIENT_OBJECT;
import threads.MyThread;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

public class MyChart {

    public XYPlot plot;
    public ChartUpdater updater;
    // Variables
    BASE_CLIENT_OBJECT client;
    double[] oldVals;
    JFreeChart chart;
    MyChartPanel chartPanel;
    MyTimeSeries[] series;
    MyProps props;
    boolean load = false;

    // Constructor
    public MyChart( BASE_CLIENT_OBJECT client, MyTimeSeries[] series, MyProps props ) {
        this.client = client;
        this.series = series;
        this.props = props;
        oldVals = new double[ series.length ];

        // Init
        init( series, props );

        // Start updater
        updater = new ChartUpdater( client, series );
        updater.getHandler( ).start( );
    }

    private void init( MyTimeSeries[] series, MyProps props ) {

        // Series
        TimeSeriesCollection data = new TimeSeriesCollection( );

        // Create the chart
        chart = ChartFactory.createTimeSeriesChart( null, null, null, data, false, true, false );

        plot = chart.getXYPlot( );
        plot.setBackgroundPaint( Color.WHITE );
        plot.setRangeGridlinesVisible( props.getBool( ChartPropsEnum.IS_GRID_VISIBLE ) );
        plot.setDomainGridlinesVisible( false );
        plot.setRangeGridlinePaint( Color.BLACK );
        plot.setRangeAxisLocation( AxisLocation.BOTTOM_OR_RIGHT );
        plot.getDomainAxis( ).setVisible( props.getBool( ChartPropsEnum.INCLUDE_DOMAIN_AXIS ) );
        plot.setAxisOffset( new RectangleInsets( 5.0, 5.0, 5.0, 5.0 ) );
        plot.setDomainPannable( true );
        plot.setRangePannable( true );
        plot.getRangeAxis( ).setLabelPaint( Themes.BLUE_DARK );
        plot.getRangeAxis( ).setLabelFont( Themes.ARIEL_15 );

        DateAxis axis = ( DateAxis ) plot.getDomainAxis( );
        axis.setAutoRange( true );
        axis.setDateFormatOverride( new SimpleDateFormat( "HH:mm" ) );

        NumberAxis numberAxis = ( NumberAxis ) plot.getRangeAxis( );
        DecimalFormat df = new DecimalFormat( "#0000.00" );
        df.setNegativePrefix( "-" );
        numberAxis.setNumberFormatOverride( df );

        // Marker
        if ( props.get( ChartPropsEnum.MARKER ) != null ) {
            plot.addRangeMarker( ( Marker ) props.get( ChartPropsEnum.MARKER ), Layer.BACKGROUND );
        }

        // Range unit
        if ( props.getDouble( ChartPropsEnum.RANGE_MARGIN ) > 0 ) {
            ValueAxis range = plot.getRangeAxis( );
            ( ( NumberAxis ) range ).setTickUnit( new NumberTickUnit( props.getDouble( ChartPropsEnum.RANGE_MARGIN ) ) );
        }

        // Style lines
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
        renderer.setShapesVisible( false );
        plot.setRenderer( renderer );

        int i = 0;
        for ( MyTimeSeries serie : series ) {

            // Append serie
            data.addSeries( serie );

            // Style serie
            renderer.setSeriesShapesVisible( i, false );
            renderer.setSeriesPaint( i, serie.getColor( ) );
            renderer.setSeriesStroke( i, new BasicStroke( serie.getStokeSize( ) ) );

            i++;
        }

    }

    public ChartUpdater getUpdater() {
        return updater;
    }

    // ---------- Chart updater thread ---------- //
    class ChartUpdater extends MyThread implements Runnable {

        // Variables
//        ArrayList< Double > dots = new ArrayList<>( );
        MyTimeSeries[] series;
        NumberAxis range;
        double start, end;

        // Constructor
        public ChartUpdater( BASE_CLIENT_OBJECT client, MyTimeSeries[] series ) {
            super( client );
            this.series = series;
            initListeners( );
        }

        private void initListeners() {

        }

        @Override
        public void run() {

            // While loop
            while ( isRun( ) ) {
                try {
                    if ( client.isStarted( ) ) {
                        if ( !load ) {
//                            loadChartData( );
                            load = true;
                        }

                        // Sleep
                        Thread.sleep( props.getInt( ChartPropsEnum.SLEEP ) );

                        if ( props.getBool( ChartPropsEnum.IS_LIVE ) ) {
                            if ( isDataChanged( ) ) {
                                append( );
                            }
                        } else {
                            append( );
                        }
                    } else {
                        // Sleep
                        Thread.sleep( 1000 );
                    }
                } catch ( InterruptedException e ) {
                    break;
                }
            }
        }

        private void append() {
            // Append data
            appendDataToSeries( );
            // Change range getting bigger
            chartRangeGettingBigFilter( );
            // Ticker
            setTickerData( );
        }

        // Append data to series
        private void appendDataToSeries() {
            if ( props.getBool( ChartPropsEnum.IS_LIVE ) ) {
                try {
                    for ( MyTimeSeries serie : series ) {

                        // If bigger then target Seconds
                        if ( serie.getItemCount( ) > props.getInt( ChartPropsEnum.SECONDS ) ) {
                            serie.remove( 0 );
                        }

                        // Append data
                        serie.add( );
                    }
                } catch ( IndexOutOfBoundsException e ) {
                    e.printStackTrace( );
                }
            }
        }

        public void setTickerData() {
            if ( props.getBool( ChartPropsEnum.IS_INCLUDE_TICKER ) ) {
                try {
                    double min = Collections.min( series[ 0 ].getValues( ) );
                    double max = Collections.max( series[ 0 ].getValues( ) );
                    double last = ( double ) series[ 0 ].getLastItem( ).getValue( );

                    chartPanel.getHighLbl( ).colorForge( max, L.format10( ) );
                    chartPanel.getLowLbl( ).colorForge( min, L.format10( ) );
                    chartPanel.getLastLbl( ).colorForge( last, L.format10( ) );
                } catch ( Exception e ) {
                    e.printStackTrace( );
                }
            }
        }

        public void updateChartRange() {
            try {
                if ( series[ 0 ].getItemCount( ) > 0 ) {

                    // X
                    DateRange xRange = ( DateRange ) plot.getDomainAxis( ).getRange( );

                    RegularTimePeriod startPeroid = new Second( L.formatter.parse( xRange.getLowerDate( ).toString( ) ) );
                    RegularTimePeriod endPeroid = new Second( L.formatter.parse( xRange.getUpperDate( ).toString( ) ) );

                    try {
                        start = ( double ) series[ 0 ].getDataItem( startPeroid ).getValue( );
                        end = ( double ) series[ 0 ].getDataItem( endPeroid ).getValue( );
                    } catch ( Exception e ) {
                    }

                    double min, max;

                    if ( start < end ) {
                        min = start;
                        max = end;
                    } else {
                        min = end;
                        max = start;
                    }

                    double avg = ( min + max ) / 2;

                    min = min - ( avg * 0.0015 );
                    max = max + ( avg * 0.0015 );

                    range = ( NumberAxis ) plot.getRangeAxis( );
                    range.setRange( min, max );

                }
            } catch ( NoSuchElementException | ParseException e ) {
                e.printStackTrace( );
            }
        }

        private void updateChartRange( double min, double max ) {
            try {
                if ( series[ 0 ].getItemCount( ) > 0 ) {
                    range = ( NumberAxis ) plot.getRangeAxis( );
                    range.setRange( min, max );
                }
            } catch ( NoSuchElementException e ) {
                e.printStackTrace( );
            }
        }

        public void setTextWithColor( JLabel label, double price ) {

            label.setText( L.str( price ) );

            if ( price > 0 ) {
                label.setForeground( Themes.GREEN );
            } else {
                label.setForeground( Themes.RED );
            }
        }

        private void chartRangeGettingBigFilter() {

            if ( series[ 0 ].getItemCount( ) > 0 ) {

                ArrayList< Double > dots = new ArrayList<>( );
                for ( MyTimeSeries serie : series ) {
                    dots.addAll( serie.getValues( ) );
                }

                double min = Collections.min( dots ) - props.getDouble( ChartPropsEnum.MARGIN );
                double max = Collections.max( dots ) + props.getDouble( ChartPropsEnum.MARGIN );

                if ( dots.size( ) > series.length * props.getInt( ChartPropsEnum.SECONDS_ON_MESS ) ) {

                    // If need to rearrange
                    if ( max - min > props.getInt( ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS ) ) {

                        // For each serie
                        for ( MyTimeSeries serie : series ) {
                            for ( int i = 0; i < serie.getItemCount( ) - props.getInt( ChartPropsEnum.SECONDS_ON_MESS ) - 1; i++ ) {
                                serie.remove( i );
                            }
                        }
                    }
                }

                // Update chart range
                updateChartRange( min, max );
            }

        }

        // Is data changed
        private boolean isDataChanged() {
            boolean change = false;
            double oldVal = 0;
            double newVal = 0;

            int i = 0;
            for ( MyTimeSeries serie : series ) {

                oldVal = oldVals[ i ];

                try {
                    newVal = serie.getData( );
                } catch ( Exception e ) {
                    e.printStackTrace( );
                    change = false;
                    break;
                }

                // If 0
                if ( newVal == 0 ) {
                    break;
                }

                // If new val
                if ( newVal != oldVal ) {
                    oldVals[ i ] = newVal;
                    change = true;
                }
                i++;
            }
            return change;
        }

        @Override
        public void initRunnable() {
            setRunnable( this );
        }
    }
}