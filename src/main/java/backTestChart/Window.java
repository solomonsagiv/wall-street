package backTestChart;

import gui.MyGuiComps;
import locals.L;
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

                int day = L.INT( dayField.getText() );
                int month = L.INT( monthField.getText() );
                int year = L.INT( yearField.getText() );

                String query = String.format("select * from jsonTables.spxJsonDay where date = '%s-%s-%s';", year, month, day);

                GetDataFromDB dataFromDB = new GetDataFromDB( query );
                Map<String, ArrayList<Double> > map = dataFromDB.getDataFromDb();

                TheChart chart = new TheChart( Spx.getInstance(), year, month, day );
                chart.appendData( map, dataFromDB.getTimeList() );
                try {
                    chart.createChart();
                } catch ( CloneNotSupportedException e ) {
                    e.printStackTrace( );
                }

                chartBtn.setEnabled( true );
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

        dayField = new MyGuiComps.MyTextField(  );
        dayField.setText( "26" );
        dayField.setXY( dayLbl.getX( ), dayLbl.getY( ) + dayLbl.getHeight( ) + 5 );
        mainPanel.add( dayField );

        // Month
        monthLbl = new MyGuiComps.MyLabel( "Month" );
        monthLbl.setXY( dayField.getX() + dayField.getWidth() + 5, dayLbl.getY() );
        mainPanel.add( monthLbl );

        monthField = new MyGuiComps.MyTextField(  );
        monthField.setText( "10" );
        monthField.setXY( monthLbl.getX(), monthLbl.getY() + monthLbl.getHeight() + 5 );
        mainPanel.add( monthField );

        // Year
        yearLbl = new MyGuiComps.MyLabel( "Year" );
        yearLbl.setXY( monthField.getX() + monthField.getWidth() + 5, monthLbl.getY() );
        mainPanel.add( yearLbl );

        yearField = new MyGuiComps.MyTextField(  );
        yearField.setText( "2020" );
        yearField.setXY( yearLbl.getX(), yearLbl.getY() + yearLbl.getHeight() + 5 );
        mainPanel.add( yearField );

        // Btn
        chartBtn = new MyGuiComps.MyButton( "Chart" );
        chartBtn.setXY( dayField.getX( ), dayField.getY( ) + dayField.getHeight( ) + 5 );
        chartBtn.setWidth( 100 );
        mainPanel.add( chartBtn );

    }
}
