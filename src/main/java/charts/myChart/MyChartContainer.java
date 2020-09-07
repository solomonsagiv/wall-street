package charts.myChart;

import charts.MyChartPanel;
import dataBase.mySql.myBaseTables.MyBoundsTable;
import dataBase.mySql.mySqlComps.TablesEnum;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import serverObjects.BASE_CLIENT_OBJECT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.sql.ResultSet;

public class MyChartContainer extends JFrame {

    private static final long serialVersionUID = 1L;

    // Index series array
    MyChart[] charts;

    BASE_CLIENT_OBJECT client;
    String name;

    public MyChartContainer( BASE_CLIENT_OBJECT client, MyChart[] charts, String name ) {
        this.charts = charts;
        this.client = client;
        this.name = name;
        init( );
    }

    @Override
    public String getName() {
        return name;
    }

    private void init() {

        // Load bounds
        loadBounds( );

        // On Close
        addWindowListener( new java.awt.event.WindowAdapter( ) {
            public void windowClosing( WindowEvent e ) {
                onClose( e );
            }
        } );

        // Layout
        setLayout( new GridLayout( charts.length, 0 ) );

        // Append charts
        appendCharts( );

    }

    public void create() {
        pack( );
        setVisible( true );
    }

    private void appendCharts() {
        for ( MyChart myChart : charts ) {
            MyChartPanel chartPanel = new MyChartPanel( myChart.chart, myChart.props.getBool( ChartPropsEnum.IS_INCLUDE_TICKER ) );
            myChart.chartPanel = chartPanel;

            chartPanel.setMouseZoomable( true );
            chartPanel.setMouseWheelEnabled( true );
            chartPanel.setDomainZoomable( true );
            chartPanel.setRangeZoomable( false );
            chartPanel.setZoomTriggerDistance( Integer.MAX_VALUE );
            chartPanel.setFillZoomRectangle( false );
            chartPanel.setZoomAroundAnchor( false );

            chartPanel.addMouseListener( new MouseAdapter( ) {
                @Override
                public void mouseClicked( MouseEvent e ) {
                    super.mouseClicked( e );
                    if ( e.getClickCount( ) == 2 ) {
                        DateAxis axis = ( DateAxis ) myChart.plot.getDomainAxis( );
                        NumberAxis rangeAxis = ( NumberAxis ) myChart.plot.getRangeAxis( );

                        rangeAxis.setAutoRange( true );
                        axis.setAutoRange( true );
                    }
                }
            } );

//            try {
//                Field mask = ChartPanel.class.getDeclaredField( "panMask" );
//                mask.setAccessible( true );
//                mask.set( chartPanel, 0 );
//            } catch ( NoSuchFieldException e ) {
//                e.printStackTrace( );
//            } catch ( IllegalAccessException e ) {
//                e.printStackTrace( );
//            }
            chartPanel.addMouseWheelListener( new MouseWheelListener( ) {
                @Override
                public void mouseWheelMoved( MouseWheelEvent e ) {
                    myChart.getUpdater( ).updateChartRange( );
                }
            } );
            add( chartPanel );
        }
    }

    private void loadBounds() {
        new Thread( () -> {
            try {
                ResultSet rs = ( ( MyBoundsTable ) client.getTablesHandler( ).getTable( TablesEnum.BOUNDS ) ).getBound( client.getName( ), getName( ) );

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
                e.printStackTrace( );
            }
        } ).start( );
    }

    public void onClose( WindowEvent e ) {
        new Thread( () -> {
            ( ( MyBoundsTable ) client.getTablesHandler( ).getTable( TablesEnum.BOUNDS ) ).updateBoundOrCreateNewOne( client.getName( ), name, getX( ), getY( ), getWidth( ), getHeight( ) );
        } ).start( );

        for ( MyChart myChart : charts ) {
            myChart.getUpdater( ).getHandler( ).close( );
        }
        dispose( );
    }

}
