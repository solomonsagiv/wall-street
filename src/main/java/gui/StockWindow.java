package gui;

import locals.L;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;

import java.awt.*;

public class StockWindow extends MyGuiComps.MyFrame {

    BASE_CLIENT_OBJECT client;

    public static void main( String[] args ) {
        StockWindow window = new StockWindow( "Test" );
        window.setClient( Spx.getInstance( ) );

        window.updateText( );
    }

    // ----- Headers ----- //
    // Ticker head
    MyGuiComps.MyPanel tickerHeadPanel;
    MyGuiComps.MyLabel openLbl;
    MyGuiComps.MyLabel highLbl;
    MyGuiComps.MyLabel lowLbl;
    MyGuiComps.MyLabel indLbl;

    // Races head
    MyGuiComps.MyPanel racesHeadPanel;
    MyGuiComps.MyLabel racesLbl;

    // ----- Data ----- //
    // Ticker
    MyGuiComps.MyPanel tickerPanel;
    MyGuiComps.MyTextField openField;
    MyGuiComps.MyTextField openPresentField;
    MyGuiComps.MyTextField highField;
    MyGuiComps.MyTextField highPresentField;
    MyGuiComps.MyTextField lowField;
    MyGuiComps.MyTextField lowPresentField;
    MyGuiComps.MyTextField indField;
    MyGuiComps.MyTextField indPresentField;
    MyGuiComps.MyTextField conField;
    MyGuiComps.MyTextField opField;
    MyGuiComps.MyTextField opAvgField;

    // Races
    MyGuiComps.MyPanel racesPanel;
    MyGuiComps.MyLabel indRacesLbl;
    MyGuiComps.MyLabel conRacesLbl;
    MyGuiComps.MyTextField indRacesField;
    MyGuiComps.MyTextField conRacesField;

    // Constructor
    public StockWindow( String title ) throws HeadlessException {
        super( title );

        initialize( );
        pack( );
        setVisible( true );

        // This
        setBounds( 200, 200, tickerWidth + racesWidth + 15, 160 );
    }

    @Override
    public void onClose() {
    }

    int tickerWidth = 287;
    int racesWidth = 120;
    int datePanelHeight = 95;

    // Initialize
    @Override
    public void initialize() {
        // ----- Header ----- //
        tickerHeadPanel = new MyGuiComps.MyPanel( );
        tickerHeadPanel.setLayout( null );
        tickerHeadPanel.setBounds( 0, 0, tickerWidth, 25 );
        getContentPane( ).add( tickerHeadPanel );

        // Open
        openLbl = new MyGuiComps.MyLabel( "Open" );
        openLbl.setXY( 5, 0 );
        tickerHeadPanel.add( openLbl );

        // Ind
        indLbl = new MyGuiComps.MyLabel( "Ind" );
        indLbl.setXY( 75, 0 );
        tickerHeadPanel.add( indLbl );

        // Low
        lowLbl = new MyGuiComps.MyLabel( "Low" );
        lowLbl.setXY( 145, 0 );
        tickerHeadPanel.add( lowLbl );

        // Races panel
        racesHeadPanel = new MyGuiComps.MyPanel( );
        racesHeadPanel.setBounds( tickerWidth + 1, 0, racesWidth, 25 );
        getContentPane( ).add( racesHeadPanel );

        // Races
        racesLbl = new MyGuiComps.MyLabel( "Races" );
        racesLbl.setXY( 5, 3 );
        racesHeadPanel.add( racesLbl );

        // High
        highLbl = new MyGuiComps.MyLabel( "High" );
        highLbl.setXY( 215, 0 );
        tickerHeadPanel.add( highLbl );

        // ----- Data ----- //
        tickerPanel = new MyGuiComps.MyPanel( );
        tickerPanel.setLayout( null );
        tickerPanel.setBounds( 0, 26, 287, datePanelHeight );
        getContentPane( ).add( tickerPanel );

        // Open
        openField = new MyGuiComps.MyTextField( );
        openField.setXY( 5, 3 );
        tickerPanel.add( openField );

        openPresentField = new MyGuiComps.MyTextField( );
        openPresentField.setXY( 5, 33 );
        tickerPanel.add( openPresentField );

        // Ind
        indField = new MyGuiComps.MyTextField( );
        indField.setXY( 75, 3 );
        tickerPanel.add( indField );

        indPresentField = new MyGuiComps.MyTextField( );
        indPresentField.setXY( 75, 33 );
        tickerPanel.add( indPresentField );

        // Low
        lowField = new MyGuiComps.MyTextField( );
        lowField.setXY( 145, 3 );
        tickerPanel.add( lowField );

        lowPresentField = new MyGuiComps.MyTextField( );
        lowPresentField.setXY( 145, 33 );
        tickerPanel.add( lowPresentField );

        // High
        highField = new MyGuiComps.MyTextField( );
        highField.setXY( 215, 3 );
        tickerPanel.add( highField );

        highPresentField = new MyGuiComps.MyTextField( );
        highPresentField.setXY( 215, 33 );
        tickerPanel.add( highPresentField );

        // Con
        conField = new MyGuiComps.MyTextField( );
        conField.setXY( 5, 63 );
        tickerPanel.add( conField );

        // Op
        opField = new MyGuiComps.MyTextField( );
        opField.setXY( 75, 63 );
        tickerPanel.add( opField );

        // OpAvg
        opAvgField = new MyGuiComps.MyTextField( );
        opAvgField.setXY( 145, 63 );
        tickerPanel.add( opAvgField );

        // ----- Races panel ----- //
        racesPanel = new MyGuiComps.MyPanel( );
        racesPanel.setLayout( null );
        racesPanel.setBounds( tickerWidth + 1, 26, racesWidth, datePanelHeight );
        getContentPane( ).add( racesPanel );

        // Ind races
        indRacesLbl = new MyGuiComps.MyLabel( "Ind" );
        indRacesLbl.setXY( 0, 3 );
        racesPanel.add( indRacesLbl );

        indRacesField = new MyGuiComps.MyTextField( );
        indRacesField.setXY( 60, 3 );
        indRacesField.setWidth( 50 );
        racesPanel.add( indRacesField );

        // Con races
        conRacesLbl = new MyGuiComps.MyLabel( "Con" );
        conRacesLbl.setXY( 0, 33 );
        racesPanel.add( conRacesLbl );

        conRacesField = new MyGuiComps.MyTextField( );
        conRacesField.setWidth( 50 );
        conRacesField.setXY( 60, 33 );
        racesPanel.add( conRacesField );
    }

    @Override
    public void initListeners() {
    }

    // Update text
    public void updateText() {
        if ( client != null ) {

            // Ticker
            openField.setText( client.getOpen( ), L.format100( ) );
            openPresentField.colorBack( L.present( client.getOpen( ), client.getBase( ) ), L.format100( ), "%" );
            indField.setText( client.getIndex( ), L.format100( ) );
            indPresentField.colorBack( L.present( client.getIndex( ), client.getBase( ) ), L.format100( ), "%" );
            lowField.setText( client.getLow( ), L.format100( ) );
            lowPresentField.colorBack( L.present( client.getLow( ), client.getBase( ) ), L.format100( ), "%" );
            highField.setText( client.getHigh( ), L.format100( ) );
            highPresentField.colorBack( L.present( client.getHigh( ), client.getBase( ) ), L.format100( ), "%" );
            conField.setText( client.getOptionsHandler( ).getMainOptions( ).getContract( ), L.format100( ) );
            opField.colorBack( client.getOptionsHandler( ).getMainOptions( ).getOp( ), L.format100( ) );
            opAvgField.colorForge( client.getOptionsHandler( ).getMainOptions( ).getOpAvg( ), L.format100( ) );

            // Races
            indRacesField.colorForge( client.getIndexSum( ) );
            conRacesField.colorForge( client.getFutSum( ) );

        }
    }

    public BASE_CLIENT_OBJECT getClient() {
        return client;
    }

    public void setClient( BASE_CLIENT_OBJECT client ) {
        this.client = client;
    }
}
