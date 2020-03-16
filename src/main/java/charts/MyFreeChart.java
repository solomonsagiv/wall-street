package charts;

import arik.Arik;
import dataBase.mySql.myTables.MyBoundsTable;
import dataBase.mySql.myTables.TablesEnum;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import serverObjects.BASE_CLIENT_OBJECT;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;

public class MyFreeChart extends JFrame {

    private static final long serialVersionUID = 1L;

    // Index series array
    XYPlot plot;
    MySingleFreeChart[] singleFreeCharts;

    BASE_CLIENT_OBJECT client;
    String name;

    public MyFreeChart( MySingleFreeChart[] singleFreeCharts, BASE_CLIENT_OBJECT client, String name ) {
        this.singleFreeCharts = singleFreeCharts;
        this.client = client;
        this.name = name;
        init( );
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

    @Override
    public String getName() {
        return name;
    }

    private void init() {

        new Thread( () -> {
            try {
                ResultSet rs = (( MyBoundsTable ) client.getTablesHandler( ).getTable( TablesEnum.BOUNDS )).getBound( client.getName( ), getName( ) );

                int width = 100, height = 100, x = 100, y = 100;

                while ( rs.next( ) ) {
                    x = rs.getInt( "x" );
                    y = rs.getInt( "y" );
                    width = rs.getInt( "width" );
                    height = rs.getInt( "height" );
                }

                setPreferredSize( new Dimension( width, height ) );
                setBounds( x, y, width, height );
            } catch ( Exception e ) {
                e.printStackTrace();
                Arik.getInstance().sendErrorMessage( e );
            }
        } ).start( );


        // On Close
        addWindowListener( new java.awt.event.WindowAdapter( ) {
            public void windowClosing( WindowEvent e ) {
                onClose( e );
            }
        } );

        setLayout( new GridLayout( singleFreeCharts.length, 0 ) );


        // Append charts
        for ( MySingleFreeChart mySingleFreeChart : singleFreeCharts ) {
            JFreeChart chart = mySingleFreeChart.getChart( );
            MyChartPanel chartPanel = new MyChartPanel( chart, mySingleFreeChart.isIncludeTickerData( ) );
            mySingleFreeChart.setChartPanel( chartPanel );
            add( chartPanel );
        }

    }

    public void onClose( WindowEvent e ) {

        new Thread( () -> {
            (( MyBoundsTable )client.getTablesHandler( ).getTable( TablesEnum.BOUNDS )).updateBoundOrCreateNewOne( client.getName( ), name, getX( ), getY( ), getWidth( ), getHeight( ) );
        } ).start( );

        for ( MySingleFreeChart mySingleFreeChart : singleFreeCharts ) {
            mySingleFreeChart.closeUpdate( );
        }

        dispose( );
    }

}
