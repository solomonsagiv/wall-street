package charts.myChart;

import arik.Arik;
import charts.MyChartPanel;
import dataBase.mySql.myTables.MyBoundsTable;
import dataBase.mySql.myTables.TablesEnum;
import serverObjects.BASE_CLIENT_OBJECT;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
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

    private void appendCharts() {
        for ( MyChart myChart : charts ) {
            MyChartPanel chartPanel = new MyChartPanel( myChart.chart, myChart.props.isIncludeTicker() );
            myChart.chartPanel = chartPanel;
            add( chartPanel );
        }
    }

    private void loadBounds() {
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
    }

    public void onClose( WindowEvent e ) {

        new Thread( () -> {
            (( MyBoundsTable )client.getTablesHandler( ).getTable( TablesEnum.BOUNDS )).updateBoundOrCreateNewOne( client.getName( ), name, getX( ), getY( ), getWidth( ), getHeight( ) );
        } ).start( );

        for ( MyChart myChart : charts ) {
            myChart.getUpdater().getHandler().close();
        }

        dispose( );
    }

}
