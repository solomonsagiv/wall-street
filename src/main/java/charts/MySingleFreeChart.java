package charts;

import locals.Themes;
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
import serverObjects.indexObjects.NdxCLIENTObject;
import serverObjects.indexObjects.SpxCLIENTObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class MySingleFreeChart {

    XYSeries[] series;
    Color[] colors;
    BASE_CLIENT_OBJECT client;
    XYPlot plot;
    double margin;
    ChartUpdater chartUpdater;
    int seconds;
    int basicSecondes;
    int secondesOnMess = 10;
    Map< String, List<Double> > map;
    private JFreeChart chart;
    private MyChartPanel chartPanel;
    private boolean includeTickerData;
    private boolean loadFromHB;
    private boolean rangeFixer;


    public MySingleFreeChart( BASE_CLIENT_OBJECT client, XYSeries[] series, Color[] colors, double margin,
                              Map< String, List<Double> > map, int seconds, boolean includeTickerData, double rangeTickUnit,
                              float strokeSize, boolean rangeGridLineVisible, boolean loadFromHB, boolean rangeFixer, Marker marker ) {

        this.rangeFixer = rangeFixer;
        this.client = client;
        this.series = series;
        this.colors = colors;
        this.margin = margin;
        this.seconds = seconds;
        this.basicSecondes = seconds;
        this.loadFromHB = loadFromHB;
        this.map = map;
        setIncludeTickerData( includeTickerData );

        // Series
        XYSeriesCollection data = new XYSeriesCollection( );

        // Create the chart
        chart = ChartFactory.createXYLineChart( null, null, null, data, PlotOrientation.VERTICAL, false, true, false);

        plot = chart.getXYPlot( );
        plot.setBackgroundPaint( Color.WHITE );
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
        chartUpdater = new ChartUpdater( map );
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
        Map< String, List<Double> > map;
        NumberAxis range;
        boolean run = true;

        int x = 0;

        public ChartUpdater( Map< String, List<Double> > map ) {
            this.map = map;
        }

        @Override
        public void run() {

            if ( loadFromHB ) {
                loadDataFromDB( );
            }

            // While loop
            while ( run ) {
                try {

                    updateChart( );

                    // Sleep
                    sleep( 1000 );
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

            if ( client instanceof SpxCLIENTObject ) {
                marginFromMaxToMin = 7;
            }

            if ( client instanceof NdxCLIENTObject ) {
                marginFromMaxToMin = 30;
            }

            // Include ticker data if only one chart
            includeTickerIfOnlyOneChart( );

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

                int i = 0;
                for ( Map.Entry< String, List<Double> > entry : map.entrySet( ) ) {

                    // Get current
                    List<Double> myList = entry.getValue( );
                    currentSerie = series[ i ];

                    // Remove index 0
                    currentSerie.remove( 0 );
                    dots.remove( 0 );

                    // Append last item
                    double item = ( double ) myList.get( myList.size() - 1 );
                    currentSerie.add( x, item );
                    dots.add( item );

                    i++;
                }

            } else {
                // Append last item to series
                appendDataToSeries( );

            }

        }

        private void chartRangeGetiingBigFilter( double marginFromMaxToMin, double max, double min ) {

            if ( rangeFixer ) {
                if ( dots.size( ) > map.size( ) * secondesOnMess ) {

                    // If need to rerange
                    if ( max - min > marginFromMaxToMin ) {

                        XYSeries currentSerie;

                        // For each serie
                        for ( int i = 0; i < map.size( ); i++ ) {

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

        }

        private void appendDataToSeries() {

            try {
                XYSeries currentSerie;

                int i = 0;
                for ( Map.Entry< String, List<Double> > entry : map.entrySet( ) ) {

                    // Get current
                    List<Double> myList = entry.getValue( );
                    currentSerie = series[ i ];

                    // Append last item
                    double item = ( double ) myList.get( myList.size() - 1 );
                    currentSerie.add( x, item );
                    dots.add( item );

                    i++;
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

        private void includeTickerIfOnlyOneChart() {

            // If only one chart -> include ticker data
            if ( map.size( ) == 1 && includeTickerData ) {
                try {
                    ArrayList< Double > list = null;
                    for ( Map.Entry< String, List<Double> > entry : map.entrySet( ) ) {
                        list = ( ArrayList< Double > ) entry.getValue( );
                    }

                    if ( list.size( ) > 0 ) {

                        double high = floor( Collections.max( list ) );
                        double low = floor( Collections.min( list ) );
                        double last = floor( list.get( list.size( ) - 1 ) );

                        setTextWithColor( chartPanel.highLbl, high );
                        setTextWithColor( chartPanel.lowLbl, low );
                        chartPanel.lastLbl.setText( str( last ) );
                    }

                } catch ( Exception e ) {
                    // TODO: handle exception
                }
            }
        }


        // Load charts data from DB
        private void loadDataFromDB() {
            int i = 0;
            for ( Map.Entry< String, List<Double> > entry : map.entrySet( ) ) {

                ArrayList< Double > list = ( ArrayList< Double > ) entry.getValue( );

                if ( list.size( ) > 0 ) {

                    int start = 0;
                    if ( list.size( ) > seconds && seconds > 0 ) {
                        start = list.size( ) - seconds;
                    }

                    for ( int j = start; j < list.size( ); j++ ) {

                        if ( list.get( j ) != 0 ) {
                            series[ i ].add( j, list.get( j ) );
                            dots.add( list.get( j ) );
                        }

                    }
                }
                i++;
                x = list.size( );
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