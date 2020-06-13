package charts;

import exp.ExpEnum;
import locals.Themes;
import options.Options;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.Layer;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Ndx;
import serverObjects.indexObjects.Spx;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

public class MySingleFreeChartLive {

    XYSeries[] series;
    Color[] colors;
    BASE_CLIENT_OBJECT client;
    XYPlot plot;
    double margin;
    ChartUpdater chartUpdater;
    int seconds;
    int basicSecondes;
    int secondesOnMess = 10;
    int sleep = 200;
    double[] oldVals;
    private JFreeChart chart;
    private MyChartPanel chartPanel;
    private boolean includeTickerData;
    Options mainOptions;
    Options quarterOptions;


    public MySingleFreeChartLive( BASE_CLIENT_OBJECT client, XYSeries[] series, Color[] colors, double margin,
                                  ArrayList< String > list, int seconds, boolean includeTickerData, double rangeTickUnit,
                                  float strokeSize, boolean rangeGridLineVisible, Marker marker ) {
        this.mainOptions = client.getExps().getMainExp().getOptions();
        this.quarterOptions = client.getExps().getExp( ExpEnum.E1 ).getOptions();
        this.client = client;
        this.series = series;
        this.colors = colors;
        this.margin = margin;
        this.seconds = seconds;
        this.basicSecondes = seconds;
        this.setIncludeTickerData( includeTickerData );
        oldVals = new double[ list.size( ) ];

        // Series
        XYSeriesCollection data = new XYSeriesCollection( );

        // Create the chart
        chart = ChartFactory.createXYLineChart( null, null, null, data, PlotOrientation.VERTICAL, false, true, false );

        plot = chart.getXYPlot( );
        plot.setBackgroundPaint( Themes.GREY_VERY_LIGHT );
        plot.setRangeGridlinesVisible( rangeGridLineVisible );
        plot.setDomainGridlinesVisible( false );
        plot.setRangeGridlinePaint( Color.BLACK );
        plot.setRangeAxisLocation( AxisLocation.BOTTOM_OR_RIGHT );
        plot.getDomainAxis( ).setVisible( false );

        if ( marker != null ) {
            plot.addRangeMarker( marker, Layer.BACKGROUND );
        }

        if ( rangeTickUnit > 0 ) {
            ValueAxis range = plot.getRangeAxis( );
            ( ( NumberAxis ) range ).setTickUnit( new NumberTickUnit( rangeTickUnit ) );
        }

        // Style lines
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
        renderer.setShapesVisible( false );
        plot.setRenderer( renderer );

        // For each serie
        for ( int i = 0; i < series.length; i++ ) {

            // Append serie
            data.addSeries( series[ i ] );

            // Style serie
            renderer.setSeriesShapesVisible( i, false );
            renderer.setSeriesPaint( i, colors[ i ] );
            renderer.setSeriesStroke( i, new BasicStroke( strokeSize ) );
        }

        // Run chart updater
        chartUpdater = new ChartUpdater( list );
        chartUpdater.start( );

    }

    public void closeUpdate() {
        if ( chartUpdater != null ) {
            if ( chartUpdater.isAlive( ) ) {
                chartUpdater.close( );
            }
        }
    }

    public JFreeChart getChart() {
        return chart;
    }

    public MyChartPanel getChartPanel() {
        return chartPanel;
    }

    public void setChartPanel( MyChartPanel chartPanel ) {
        this.chartPanel = chartPanel;
    }

    public boolean isIncludeTickerData() {
        return includeTickerData;
    }

    public void setIncludeTickerData( boolean includeTickerData ) {
        this.includeTickerData = includeTickerData;
    }

    // Chart updater thread
    private class ChartUpdater extends Thread {

        ArrayList< Double > dots = new ArrayList<>( );
        ArrayList< String > list;
        NumberAxis range;
        boolean run = true;

        int x = 0;

        public ChartUpdater( ArrayList< String > list ) {
            this.list = list;
        }

        @Override
        public void run() {

            // While loop
            while ( run ) {
                try {

                    updateChart( );

                    // Sleep
                    sleep( sleep );
                } catch ( InterruptedException e ) {
                    e.printStackTrace( );
                    run = false;
                }
            }
        }

        // Update data
        private void updateChart() {

            // Increment x
            x++;

            int marginFromMaxToMin = 0;

            if ( client instanceof Spx ) {
                marginFromMaxToMin = 20;
            }

            if ( client instanceof Ndx ) {
                marginFromMaxToMin = 30;
            }

            // Append data to the series
            filterAndAppendData( marginFromMaxToMin );

        }

        private void filterAndAppendData( double marginFromMaxToMin ) {

            try {

                if ( dots.size( ) == 0 ) {

                    appendDataToSeries( );

                } else {

                    double max = Collections.max( dots );
                    double min = Collections.min( dots );

                    // Filters before appending data
                    // 1. At list "(secondesOnMees)" items each serie
                    chartRangeGetiingBigFilter( marginFromMaxToMin, max, min );

                    // 2. Chart item is bigger than "secondes"
                    chartLengthFilter( );

                    // Update range
                    updateChartRange( );
                }

            } catch ( Exception e ) {
                e.printStackTrace( );
            }
        }

        private void chartLengthFilter() {

            if ( seconds > 0 && series[ 0 ].getItemCount( ) > seconds ) {

                XYSeries currentSerie;
                double currentItem = 0;

                int i = 0;

                // If the data changed
                boolean newData = isDataChanged( );

                if ( newData ) {
                    for ( String string : list ) {

                        // Current list, param
                        currentSerie = series[ i ];
                        currentItem = getItem( string );

                        // Remove index 0
                        currentSerie.remove( 0 );
                        dots.remove( 0 );

                        // Append double
                        currentSerie.add( x, currentItem );
                        dots.add( currentItem );

                        i++;
                    }
                }

            } else {
                // Append last item to series
                appendDataToSeries( );

            }

        }


        private boolean isDataChanged() {

            boolean change = false;
            double oldVal = 0;
            double newVal = 0;

            int i = 0;

            for ( String string : list ) {

                oldVal = oldVals[ i ];
                newVal = getItem( string );

                if ( newVal != oldVal ) {

                    oldVals[ i ] = newVal;
                    change = true;
                }

                i++;
            }

            return change;

        }

        // Get item from client by string
        private double getItem( String string ) {
            switch ( string ) {
                case "index":
                    return client.getIndex( );
                case "contract":
                    return mainOptions.getContract();
                case "indexBid":
                    return client.getIndexBid( );
                case "indexAsk":
                    return client.getIndexAsk( );
                case "quarterContract":
                    return quarterOptions.getContract();
                default:
                    return 0;
            }
        }

        private void chartRangeGetiingBigFilter( double marginFromMaxToMin, double max, double min ) {

            if ( dots.size( ) > list.size( ) * secondesOnMess ) {

                // If need to rerange
                if ( max - min > marginFromMaxToMin ) {

                    XYSeries currentSerie;

                    // For each serie
                    for ( int i = 0; i < list.size( ); i++ ) {

                        // Current list, serie
                        currentSerie = series[ i ];

                        // Navigate last "secondesOnmess" items
                        for ( int j = 0; j < currentSerie.getItemCount( ) - secondesOnMess; j++ ) {

                            currentSerie.remove( j );
                            dots.remove( j );

                        }
                    }
                }
            }
        }

        private void appendDataToSeries() {

            try {

                XYSeries currentSerie;
                double currentItem = 0;

                int i = 0;

                // If the data changed
                boolean newData = isDataChanged( );

                if ( newData ) {

                    for ( String string : list ) {

                        // Current list, param
                        currentSerie = series[ i ];
                        currentItem = getItem( string );

                        // Append double
                        currentSerie.add( x, currentItem );
                        dots.add( currentItem );

                        i++;
                    }
                }
            } catch ( IndexOutOfBoundsException e ) {
            }
        }

        private void updateChartRange() {
            try {
                range = ( NumberAxis ) plot.getRangeAxis( );
                range.setRange( Collections.min( dots ) - margin, Collections.max( dots ) + margin );
            } catch ( NoSuchElementException e ) {
                e.printStackTrace( );
            }
        }

        public void close() {
            run = false;
        }

        public String str( Object o ) {
            return String.valueOf( o );
        }

        public double floor( double d ) {
            return Math.floor( d * 10 ) / 10;
        }

        public void setTextWithColor( JLabel label, double price ) {

            label.setText( str( price ) );

            if ( price > 0 ) {
                label.setForeground( Themes.GREEN );
            } else {
                label.setForeground( Themes.RED );
            }
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