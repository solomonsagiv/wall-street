package setting;

import arik.Arik;
import arik.locals.Emojis;
import gui.MyGuiComps;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DataBasePanel extends MyGuiComps.MyPanel {

    // Variables
    BASE_CLIENT_OBJECT client;

    MyGuiComps.MyButton stopBtn;
    MyGuiComps.MyButton startBtn;
    MyGuiComps.MyButton resetBtn;
    MyGuiComps.MyButton updateBtn;
    MyGuiComps.MyButton loadBtn;
    MyGuiComps.MyButton sumBtn;

    // Constructor
    public DataBasePanel( BASE_CLIENT_OBJECT client ) {
        this.client = client;
        initialize();
        initListeners();
    }

    private void initListeners() {

        // Start
        startBtn.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent actionEvent ) {
                client.getMyServiceHandler( ).addService( client.getMySqlService( ) );
            }
        } );

        // Stop
        stopBtn.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent actionEvent ) {
                client.getMyServiceHandler( ).removeService( client.getMySqlService( ) );
            }
        } );

        // Reset
        resetBtn.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent actionEvent ) {
                client.getMyTableHandler( ).getMyStatusTable( ).reset( );
                client.getMyTableHandler( ).getMyArraysTable( ).reset( );
                Arik.getInstance( ).sendMessage( Arik.sagivID, "Reset success " + Emojis.check_mark, null );
            }
        } );

        // Update
        updateBtn.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent actionEvent ) {
                client.getMyTableHandler().getMyStatusTable().update();
                client.getMyTableHandler().getMyArraysTable().update();
            }
        } );

        // Load
        updateBtn.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent actionEvent ) {
                client.getMyTableHandler().getMyStatusTable().load();
                client.getMyTableHandler().getMyArraysTable().load();
            }
        } );

        // Sum line
        sumBtn.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent actionEvent ) {
                client.getMyTableHandler().getMySumTable().insert();
                Arik.getInstance( ).sendMessage( Arik.sagivID, "Sum line inserted " + Emojis.check_mark, null );
            }
        } );

    }

    private void initialize() {

        // This
        setSize( 150, 150 );
        TitledBorder titledBorder = new TitledBorder( "Data base" );
        titledBorder.setTitleColor( Themes.BLUE_DARK );
        setBorder( titledBorder );

        // Start
        startBtn = new MyGuiComps.MyButton("Start mysql");
        startBtn.setXY( 10, 30 );
        startBtn.setBackground(Themes.BLUE);
        startBtn.setWidth(70);
        startBtn.setFont( startBtn.getFont( ).deriveFont( 9f ) );
        startBtn.setForeground(Themes.GREY_VERY_LIGHT);
        add(startBtn);

        // Stop
        stopBtn = new MyGuiComps.MyButton("Stop mysql");
        stopBtn.setXY( 90, 30 );
        stopBtn.setWidth(70);
        stopBtn.setFont( stopBtn.getFont( ).deriveFont( 9f ) );
        stopBtn.setBackground(Themes.BLUE);
        stopBtn.setForeground(Themes.GREY_VERY_LIGHT);
        add(stopBtn);

        // Reset
        resetBtn = new MyGuiComps.MyButton("Reset");
        resetBtn.setXY( 10, 60 );
        resetBtn.setWidth(70);
        resetBtn.setFont( resetBtn.getFont( ).deriveFont( 9f ) );
        resetBtn.setBackground(Themes.BLUE);
        resetBtn.setForeground(Themes.GREY_VERY_LIGHT);
        add(resetBtn);

        // Update
        updateBtn = new MyGuiComps.MyButton("Update");
        updateBtn.setXY( 90, 60 );
        updateBtn.setWidth(70);
        updateBtn.setFont( updateBtn.getFont( ).deriveFont( 9f ) );
        updateBtn.setBackground(Themes.BLUE);
        updateBtn.setForeground(Themes.GREY_VERY_LIGHT);
        add(updateBtn);

        // Load
        loadBtn = new MyGuiComps.MyButton("Load");
        loadBtn.setXY( 170, 60 );
        loadBtn.setWidth(70);
        loadBtn.setFont( loadBtn.getFont( ).deriveFont( 9f ) );
        loadBtn.setBackground(Themes.BLUE);
        loadBtn.setForeground(Themes.GREY_VERY_LIGHT);
        add(loadBtn);

        // Sum line
        sumBtn = new MyGuiComps.MyButton("Sum");
        sumBtn.setXY( 10, 90 );
        sumBtn.setWidth(70);
        sumBtn.setFont( sumBtn.getFont( ).deriveFont( 9f ) );
        sumBtn.setBackground(Themes.BLUE);
        sumBtn.setForeground(Themes.GREY_VERY_LIGHT);
        add(sumBtn);

    }
}
