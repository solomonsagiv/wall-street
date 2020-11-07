package backTestChart;

import gui.MyGuiComps;
import locals.L;
import locals.Themes;
import options.JsonStrings;
import serverObjects.indexObjects.Spx;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Map;

public class Window extends MyGuiComps.MyFrame {

    public static void main( String[] args ) {
        Window window = new Window( "Window" );

    }

    MyGuiComps.MyPanel mainPanel;
    MyGuiComps.MyLabel dayLbl;
    MyGuiComps.MyTextField dayField;
    MyGuiComps.MyLabel monthLbl;
    MyGuiComps.MyTextField monthField;
    MyGuiComps.MyLabel yearLbl;
    MyGuiComps.MyTextField yearField;
    MyGuiComps.MyButton chartBtn;
    MyGuiComps.MyButton deltaChartBtn;

    public Window( String title ) throws HeadlessException {
        super( title );

    }

    @Override
    public void initOnClose() {
        addWindowListener( new WindowAdapter( ) {
            @Override
            public void windowClosed( WindowEvent e ) {
                super.windowClosed( e );
                System.exit( 0 );
            }
        } );
    }

    @Override
    public void initListeners() {

        // Chart btn
        chartBtn.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent actionEvent ) {

                chartBtn.setEnabled( false );

                String dayString = dayField.getText();
                String monthString = monthField.getText();
                String yearString = yearField.getText();

                int day = L.INT( dayString );
                int month = L.INT( monthString );
                int year = L.INT( yearString );

                String query = String.format( "select * from jsonTables.spxJsonDay where date = '%s-%s-%s';", yearString, monthString, dayString );

                String[] cols = { JsonStrings.e1Fut, JsonStrings.ind, JsonStrings.indBid, JsonStrings.indAsk };
                Color[] colors = { Themes.GREEN, Color.BLACK, Themes.BLUE, Themes.RED };

                GetDataFromDB dataFromDB = new GetDataFromDB( query );
                Map< String, ArrayList< Double > > map = dataFromDB.getDataFromDb( cols );

                TheChart chart = new TheChart( Spx.getInstance( ), year, month, day, map, colors, dataFromDB.getTimeList(), cols );

                try {
                    chart.createChart( );
                } catch ( CloneNotSupportedException e ) {
                    e.printStackTrace( );
                }
                chartBtn.setEnabled( true );
            }
        } );

        // Delta chart btn
        deltaChartBtn.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent actionEvent ) {
                try {
                    deltaChartBtn.setEnabled( false );

                    // Date
                    String dayString = dayField.getText();
                    String monthString = monthField.getText();
                    String yearString = yearField.getText();

                    int day = L.INT( dayString );
                    int month = L.INT( monthString );
                    int year = L.INT( yearString );

                    // Props
                    String[] cols = { JsonStrings.ind, JsonStrings.e1Delta };
                    Color[] colors = { Color.BLACK, Themes.GREEN };

                    // Query
                    String query = String.format( "select * from jsonTables.spxJsonDay where date = '%s-%s-%s';", yearString, monthString, dayString );

                    // Data
                    GetDataFromDB dataFromDB = new GetDataFromDB( query );
                    Map< String, ArrayList< Double > > map = dataFromDB.getDataFromDb( cols );

                    // Chart
                    DeltaChartBackTest deltaChartBackTest = new DeltaChartBackTest( Spx.getInstance( ), year, month, day, map, colors, dataFromDB.getTimeList( ), cols );
                    deltaChartBackTest.createChart( );


                    deltaChartBtn.setEnabled( true );
                } catch ( CloneNotSupportedException e ) {
                    e.printStackTrace( );
                }
            }
        } );


    }

    @Override
    public void initialize() {

        // This
        setSize( 300, 300 );
        setXY( 100, 100 );

        // Main panel
        mainPanel = new MyGuiComps.MyPanel( );
        mainPanel.setBounds( 0, 0, ( int ) getPreferredSize( ).getWidth( ), ( int ) getPreferredSize( ).getHeight( ) );
        getContentPane( ).add( mainPanel );

        // Day
        dayLbl = new MyGuiComps.MyLabel( "Day" );
        dayLbl.setXY( 10, 10 );
        mainPanel.add( dayLbl );

        dayField = new MyGuiComps.MyTextField( );
        dayField.setText( "02" );
        dayField.setXY( dayLbl.getX( ), dayLbl.getY( ) + dayLbl.getHeight( ) + 5 );
        mainPanel.add( dayField );

        // Month
        monthLbl = new MyGuiComps.MyLabel( "Month" );
        monthLbl.setXY( dayField.getX( ) + dayField.getWidth( ) + 5, dayLbl.getY( ) );
        mainPanel.add( monthLbl );

        monthField = new MyGuiComps.MyTextField( );
        monthField.setText( "11" );
        monthField.setXY( monthLbl.getX( ), monthLbl.getY( ) + monthLbl.getHeight( ) + 5 );
        mainPanel.add( monthField );

        // Year
        yearLbl = new MyGuiComps.MyLabel( "Year" );
        yearLbl.setXY( monthField.getX( ) + monthField.getWidth( ) + 5, monthLbl.getY( ) );
        mainPanel.add( yearLbl );

        yearField = new MyGuiComps.MyTextField( );
        yearField.setText( "2020" );
        yearField.setXY( yearLbl.getX( ), yearLbl.getY( ) + yearLbl.getHeight( ) + 5 );
        mainPanel.add( yearField );

        // Btn
        chartBtn = new MyGuiComps.MyButton( "Chart" );
        chartBtn.setXY( dayField.getX( ), dayField.getY( ) + dayField.getHeight( ) + 5 );
        chartBtn.setWidth( 150 );
        mainPanel.add( chartBtn );

        // Delta chart btn
        deltaChartBtn = new MyGuiComps.MyButton( "Delta chart" );
        deltaChartBtn.setXY( chartBtn.getX( ), chartBtn.getY( ) + chartBtn.getHeight( ) + 5 );
        deltaChartBtn.setWidth( 150 );
        mainPanel.add( deltaChartBtn );

    }
}
