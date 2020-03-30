package charts.myChart;

import charts.MyChartPanel;
import locals.L;
import locals.Themes;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.Layer;
import serverObjects.BASE_CLIENT_OBJECT;
import threads.MyThread;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
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
    MyChartProps props;

    // Constructor

    public MyChart( BASE_CLIENT_OBJECT client, MyTimeSeries[] series, MyChartProps props ) {
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

    private void init( MyTimeSeries[] series, MyChartProps props ) {
        // Series
        TimeSeriesCollection data = new TimeSeriesCollection( );

        // Create the chart
        chart = ChartFactory.createXYLineChart( null, null, null, data, PlotOrientation.VERTICAL, false, true, false );

        plot = chart.getXYPlot( );
        plot.setBackgroundPaint( Themes.GREY_VERY_LIGHT );
        plot.setRangeGridlinesVisible( props.isGridLineVisible( ) );
        plot.setDomainGridlinesVisible( false );
        plot.setRangeGridlinePaint( Color.BLACK );
        plot.setRangeAxisLocation( AxisLocation.BOTTOM_OR_RIGHT );
        plot.getDomainAxis( ).setVisible( false );

        // Marker
        if ( props.getMarker( ) != null ) {
            plot.addRangeMarker( props.getMarker( ), Layer.BACKGROUND );
        }

        // Range unit
        if ( props.getRangeMargin( ) > 0 ) {
            ValueAxis range = plot.getRangeAxis( );
            ( ( NumberAxis ) range ).setTickUnit( new NumberTickUnit( props.getRangeMargin( ) ) );
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

            // While loop
            while ( isRun( ) ) {
                try {

                    // Sleep
                    Thread.sleep( 200 );

                    if ( isDataChanged( ) ) {

                        // Append data
                        appendDataToSeries( );

                        // Change range getting bigger
                        chartRangeGettingBigFilter( );

                    }

                } catch ( InterruptedException e ) {
                    e.printStackTrace( );
                    break;
                }
            }
        }

        // Append data to series
        private void appendDataToSeries() {
            try {

                Millisecond millisecond = new Millisecond();

                for ( MyTimeSeries serie : series ) {

                    // If bigger then target Seconds
                    if ( serie.getItemCount( ) > props.getSeconds( ) ) {
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
                double min = Collections.min( dots ) - props.getMarginMaxMin( );
                double max = Collections.max( dots ) + props.getMarginMaxMin( );

                if ( dots.size( ) > series.length * props.getSecondsOnMess( ) ) {

                    // If need to rearrange
                    if ( max - min > props.getChartHighInDots( ) ) {

                        // For each serie
                        for ( MyTimeSeries serie : series ) {

                            serie.delete( 0, serie.getItemCount( ) - props.getSecondsOnMess( ) - 1 );

                            for ( int i = 0; i < dots.size( ) -  ( props.getSecondsOnMess( ) * series.length ); i++ ) {
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