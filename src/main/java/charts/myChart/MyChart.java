package charts.myChart;

import charts.MyChartPanel;
import lists.MyChartPoint;
import locals.L;
import locals.Themes;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleInsets;
import serverObjects.BASE_CLIENT_OBJECT;
import threads.MyThread;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.NoSuchElementException;

public class MyChart {

    // Variables
    BASE_CLIENT_OBJECT client;
    XYPlot plot;
    double[] oldVals;
    JFreeChart chart;
    MyChartPanel chartPanel;
    public ChartUpdater updater;

    MyTimeSeries[] series;
    MyProps props;

    // Constructor
    public MyChart( BASE_CLIENT_OBJECT client, MyTimeSeries[] series, MyProps props ) {
        this.client = client;
        this.series = series;
        this.props = props;
        oldVals = new double[ series.length ];

        // Init
        init( series, props );

        // Start updater
        updater = new ChartUpdater( series );
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
        plot.getDomainAxis( ).setVisible( true );
        plot.setAxisOffset( new RectangleInsets( 50.0, 5.0, 5.0, 5.0 ) );
        DateAxis axis = ( DateAxis ) plot.getDomainAxis( );
        axis.setAutoRange( true );
        axis.setDateFormatOverride( new SimpleDateFormat( "HH:mm" ) );

        NumberAxis numberAxis = ( NumberAxis ) plot.getRangeAxis( );
        DecimalFormat df = new DecimalFormat("#0000.##");
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
        ArrayList< Double > dots = new ArrayList<>( );
        MyTimeSeries[] series;
        NumberAxis range;

        // Constructor
        public ChartUpdater( MyTimeSeries[] series ) {
            this.series = series;
        }

        @Override
        public void run() {

            // Load data
            loadChartData( );

            // While loop
            while ( isRun( ) ) {
                try {

                    // Sleep
                    Thread.sleep( props.getInt( ChartPropsEnum.SLEEP ) );

                    if ( isDataChanged( ) ) {

                        // Append data
                        appendDataToSeries( );

                        // Change range getting bigger
                        chartRangeGettingBigFilter( );

                        // Ticker
                        setTickerData( );

                    }

                } catch ( InterruptedException e ) {
                    e.printStackTrace( );
                    break;
                }
            }
        }

        private void loadChartData() {
            if ( props.getBool( ChartPropsEnum.IS_LOAD_DB ) ) {
                for ( MyTimeSeries serie : series ) {
                    for ( int i = 0; i < serie.getMyChartList( ).size( ); i++ ) {
                        MyChartPoint point = serie.getMyChartList( ).get( i );
                        serie.addOrUpdate( new Millisecond( new Date( point.getX( ).getNano() ) ), point.getY( ) );
                        dots.add( point.getY( ) );
                    }
                }
            }
        }

        public void setTickerData() {
            if ( props.getBool( ChartPropsEnum.IS_INCLUDE_TICKER ) ) {
                try {
                    double min = Collections.min( dots );
                    double max = Collections.max( dots );
                    double last = dots.get( dots.size( ) - 1 );

                    chartPanel.getHighLbl( ).colorForge( max, L.format10( ) );
                    chartPanel.getLowLbl( ).colorForge( min, L.format10( ) );
                    chartPanel.getLastLbl( ).colorForge( last, L.format10( ) );
                } catch ( Exception e ) {
                    e.printStackTrace( );
                }
            }
        }

        // Append data to series
        private void appendDataToSeries() {
            try {

                Millisecond millisecond = new Millisecond( );

                for ( MyTimeSeries serie : series ) {

                    // If bigger then target Seconds
                    if ( serie.getItemCount( ) > props.getInt( ChartPropsEnum.SECONDS ) ) {
                        serie.delete( 0, 0 );
                        dots.remove( 0 );
                    }

                    // Append data
                    dots.add( serie.add( millisecond ) );
                }
            } catch ( IndexOutOfBoundsException e ) {
            }
        }

        private void updateChartRange( double min, double max ) {
            try {
                if ( dots.size( ) > 0 ) {
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

            if ( dots.size( ) > 0 ) {
                double min = Collections.min( dots ) - props.getDouble( ChartPropsEnum.MARGIN );
                double max = Collections.max( dots ) + props.getDouble( ChartPropsEnum.MARGIN );

                if ( dots.size( ) > series.length * props.getInt( ChartPropsEnum.SECONDS_ON_MESS ) ) {

                    // If need to rearrange
                    if ( max - min > props.getDouble( ChartPropsEnum.CHART_MAX_HEIGHT_IN_DOTS ) ) {

                        // For each serie
                        for ( MyTimeSeries serie : series ) {

                            serie.delete( 0, serie.getItemCount( ) - props.getInt( ChartPropsEnum.SECONDS_ON_MESS ) - 1 );

                            for ( int i = 0; i < dots.size( ) - ( props.getInt( ChartPropsEnum.SECONDS_ON_MESS ) * series.length ); i++ ) {
                                dots.remove( i );
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
                newVal = serie.getData( );

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