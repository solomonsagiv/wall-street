package backBest;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import serverObjects.BASE_CLIENT_OBJECT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class BackTestChart extends JFrame {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    NumberAxis indexRange;
    NumberAxis indexRacesRange;
    NumberAxis futureRacesRange;

    JFreeChart daxIndexChart = null;
    JFreeChart indexRacesChart = null;
    JFreeChart futureRacesChart = null;

    // Index series array
    XYSeries index = new XYSeries( "index" );

    // Races series array
    XYSeries races = new XYSeries( "index_races" );

    XYSeries futureRacesSeries = new XYSeries( "index_races" );

    double margin = 1;
    int x = 0;


    // Colors
    Color blue = new Color( 0, 51, 180 );
    Color green = new Color( 0, 150, 48 );
    Color red = new Color( 190, 23, 0 );

    int timeInSecondes;

    BASE_CLIENT_OBJECT stock;
    ArrayList< Double > indexDots = new ArrayList<>( );
    ArrayList< Double > indexRacesDots = new ArrayList<>( );
    ArrayList< Double > futureRacesDots = new ArrayList<>( );

    // Constructor
    public BackTestChart() {

        // Build the window
        init( );
        setPreferredSize( new Dimension( 632, 625 ) );

        showOnScreen( 1, this );

    }

    public static void main( String[] args ) {
        BackTestChart chart = new BackTestChart( );
        chart.pack( );
        chart.setVisible( true );

        Thread thread = new Thread( () -> {

            while ( true ) {
                try {

                    double r = new Random( ).nextDouble( ) * 10000;
                    int rr = new Random( ).nextInt( 10 );
                    int rrr = new Random( ).nextInt( 5 );

                    chart.updateChart( r, rr, rrr );
                    Thread.sleep( 1000 );
                } catch ( InterruptedException e ) {
                    // TODO Auto-generated catch block
                    e.printStackTrace( );
                }
            }
        } );
        thread.start( );
    }

    // Show on screen
    public static void showOnScreen( int screen, JFrame frame ) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment( );
        GraphicsDevice[] gd = ge.getScreenDevices( );
        if ( screen > -1 && screen < gd.length ) {
            frame.setLocation( gd[ screen ].getDefaultConfiguration( ).getBounds( ).x + frame.getX( ), frame.getY( ) );
        } else if ( gd.length > 0 ) {
            frame.setLocation( gd[ 0 ].getDefaultConfiguration( ).getBounds( ).x + frame.getX( ), frame.getY( ) );
        } else {
            throw new RuntimeException( "No Screens Found" );
        }
    }

    // Init function
    private void init() {
        // On Close
        addWindowListener( new java.awt.event.WindowAdapter( ) {
            public void windowClosing( WindowEvent e ) {
                onClose( e );
            }
        } );

        setLayout( new GridLayout( 3, 0 ) );
//		setBounds(2618, 3, 640, 100);
        setBounds( 500, 3, 640, 100 );

        daxIndexChart = getChart( "index", index, Color.BLACK, null );
        indexRacesChart = getChart( "races", races, green, blue );
        futureRacesChart = getChart( "Future", futureRacesSeries, blue, blue );

        ChartPanel daxIdexChartPanel = new ChartPanel( daxIndexChart );
        ChartPanel indexRacesChartPanel = new ChartPanel( indexRacesChart );
        ChartPanel futureRacesChartPanel = new ChartPanel( futureRacesChart );

        add( daxIdexChartPanel );
        add( indexRacesChartPanel );
        add( futureRacesChartPanel );

    }

    // On close
    public void onClose( WindowEvent e ) {
        dispose( );
    }

    // Create complete chart
    @SuppressWarnings( "deprecation" )
    public JFreeChart getChart( String seriesName, XYSeries xySeries, Color seriesColor, Color seriesColor2 ) {

        XYSeriesCollection data = new XYSeriesCollection( );

        data.addSeries( xySeries );

        JFreeChart chart = ChartFactory.createXYLineChart( null, null, null, data, PlotOrientation.VERTICAL, false, true,
                false );


        // Style
        XYPlot plot = chart.getXYPlot( );
        plot.setBackgroundPaint( Color.WHITE );
        plot.setDomainGridlinePaint( Color.black );
        plot.setRangeGridlinesVisible( false );
        plot.setRangeGridlinePaint( Color.blue );

        ValueAxis range = plot.getRangeAxis( );
        range.setVisible( true );

        // Style lines
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
        renderer.setSeriesPaint( 0, seriesColor );
        renderer.setSeriesPaint( 1, seriesColor2 );

        renderer.setSeriesStroke( 0, new BasicStroke( 1.5f ) );
        renderer.setSeriesStroke( 1, new BasicStroke( 1.5f ) );
        renderer.setSeriesStroke( 2, new BasicStroke( 1.5f ) );
        renderer.setShapesVisible( false );
        plot.setRenderer( renderer );
        return chart;
    }

    public void updateChart( double indexPrice, double indexRaces, double futureRaces ) {

        indexDots.add( indexPrice );
        indexRacesDots.add( indexRaces );
        futureRacesDots.add( futureRaces );

        index.add( x, indexPrice );
        races.add( x, indexRaces );
        futureRacesSeries.add( x, futureRaces );

        System.out.println( Collections.min( indexDots ) - margin + " \n" + Collections.max( indexDots ) + margin );

        // Update the range
        indexRange = ( NumberAxis ) daxIndexChart.getXYPlot( ).getRangeAxis( );
        indexRange.setRange( Collections.min( indexDots ) - margin, Collections.max( indexDots ) + margin );

        // Update the range
        indexRacesRange = ( NumberAxis ) indexRacesChart.getXYPlot( ).getRangeAxis( );
        indexRacesRange.setRange( Collections.min( indexRacesDots ) - margin, Collections.max( indexRacesDots ) + margin );

        // Update the range
        futureRacesRange = ( NumberAxis ) futureRacesChart.getXYPlot( ).getRangeAxis( );
        futureRacesRange.setRange( Collections.min( futureRacesDots ) - margin, Collections.max( futureRacesDots ) + margin );
        // Append series
        x++;
    }
}
