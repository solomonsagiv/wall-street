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

    Map< MySeriesEnum, MyTimeSeries > series;
    MyChartProps props;

    // Constructor
    public MyChart( BASE_CLIENT_OBJECT client, Map< MySeriesEnum, MyTimeSeries > series, MyChartProps props ) {
        this.client = client;
        this.series = series;
        this.props = props;
        oldVals = new double[ series.size( ) ];

        // Init
        init( series, props );

        // Start updater
        updater = new ChartUpdater( series );
        updater.getHandler( ).start( );

    }

    private void init( Map< MySeriesEnum, MyTimeSeries > series, MyChartProps props ) {
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
        for ( Map.Entry< MySeriesEnum, MyTimeSeries > entry : series.entrySet( ) ) {
            MyTimeSeries serie = entry.getValue( );

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
        Map< MySeriesEnum, MyTimeSeries > series;
        NumberAxis range;

        // Constructor
        public ChartUpdater( Map< MySeriesEnum, MyTimeSeries > series ) {
            this.series = series;
        }

        @Override
        public void run() {

            // While loop
            while ( isRun( ) ) {
                try {

                    // Append data
                    appendDataToSeries( );

                    // Update chart range
                    updateChartRange( );

                    // Sleep
                    Thread.sleep( 200 );
                } catch ( InterruptedException e ) {
                    e.printStackTrace( );
                    break;
                }
            }
        }

        // Append data to series
        private void appendDataToSeries() {
            try {
                // If new val
                if ( isDataChanged( ) ) {
                    for ( Map.Entry< MySeriesEnum, MyTimeSeries > entry : series.entrySet( ) ) {
                        MyTimeSeries serie = entry.getValue( );

                        // Append data
                        dots.add( serie.add( ) );

                        // If bigger then targetSeconds
                        if ( serie.getItemCount( ) > props.getSeconds( ) ) {
                            serie.delete( serie.getMyChartList( ).get( 0 ).getTime( ) );
                            dots.remove( 0 );
                        }
                    }
                }
            } catch ( IndexOutOfBoundsException e ) {
            }
        }

        private void updateChartRange() {
            try {
                range = ( NumberAxis ) plot.getRangeAxis( );
                range.setRange( Collections.min( dots ) - props.getMarginMaxMain( ), Collections.max( dots ) + props.getMarginMaxMain( ) );
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

        // Is data changed
        private boolean isDataChanged() {

            boolean change = false;
            double oldVal = 0;
            double newVal = 0;

            int i = 0;
            for ( Map.Entry< MySeriesEnum, MyTimeSeries > entry : series.entrySet( ) ) {

                MyTimeSeries serie = entry.getValue( );

                oldVal = oldVals[ i ];
                newVal = serie.getData( );

                // If new val
                if ( newVal != oldVal ) {
                    oldVals[ i ] = newVal;
                    change = true;
                    break;
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

// Update the range
// try {
// range = (NumberAxis) plot.getRangeAxis();
// range.setRange(Collections.min(dots) - margin, Collections.max(dots) +
// margin);
// range.setAutoRangeIncludesZero(false);
// range.setDefaultAutoRange(new Range(Collections.min(dots) - margin,
// Collections.max(dots) + margin));
// } catch (NoSuchElementException e) {
// e.printStackTrace();
// }