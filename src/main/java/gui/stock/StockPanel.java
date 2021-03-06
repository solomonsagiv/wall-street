package gui.stock;

import gui.MyGuiComps;
import gui.panels.IMyPanel;
import gui.popupsFactory.PopupsMenuFactory;
import locals.L;
import locals.Themes;
import options.Options;
import options.OptionsEnum;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.stockObjects.STOCK_OBJECT;
import threads.MyThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StockPanel extends JPanel implements IMyPanel {

    // Ticker
    MyGuiComps.MyPanel ticker;
    MyGuiComps.MyTextField openField;
    MyGuiComps.MyTextField openPresentField;
    MyGuiComps.MyTextField indexField;
    MyGuiComps.MyTextField indexPresentField;
    MyGuiComps.MyTextField lowField;
    MyGuiComps.MyTextField lowPresentField;
    MyGuiComps.MyTextField highField;
    MyGuiComps.MyTextField highPresentField;
    MyGuiComps.MyTextField futureField;
    MyGuiComps.MyTextField opField;
    MyGuiComps.MyTextField opAvgField;

    // Exp
    MyGuiComps.MyPanel exp;

    // Quarter
    MyGuiComps.MyTextField opAvgQuarterField;
    MyGuiComps.MyTextField opQuarterField;
    MyGuiComps.MyTextField contractQuarterField;

    // Races and roll
    MyGuiComps.MyPanel racesAndRollPanel;
    MyGuiComps.MyLabel conRacesLbl;
    MyGuiComps.MyLabel indRacesLbl;
    MyGuiComps.MyLabel rollLbl;

    public MyGuiComps.MyTextField conRacesField;
    public MyGuiComps.MyTextField indRacesField;
    MyGuiComps.MyTextField rollField;

    // Exp
    MyGuiComps.MyPanel expPanel;

    MyGuiComps.MyLabel expMoveLbl;
    MyGuiComps.MyLabel expRacesLbl;
    MyGuiComps.MyLabel expOpAvgLbl;

    MyGuiComps.MyTextField expMoveField;
    MyGuiComps.MyTextField expRacesField;
    MyGuiComps.MyTextField expOpAvgField;

    int height = 200;

    Color backGround = Themes.GREY_LIGHT;

    STOCK_OBJECT client;
    Options optWeek;
    Options optMonth;
    Options mainOptions;

    private Updater updater;

    public StockPanel( STOCK_OBJECT client ) {
        this.client = client;
        optWeek = client.getOptionsHandler( ).getOptions( OptionsEnum.WEEK );
        optMonth = client.getOptionsHandler( ).getOptions( OptionsEnum.MONTH );
        mainOptions = client.getOptionsHandler( ).getMainOptions( );

        init( );
        initListeners( );

        // Updater
        getUpdater( ).getHandler( ).start( );
    }

    private void initListeners() {
        // Right click
        addMouseListener( new MouseAdapter( ) {
            @Override
            public void mouseClicked( MouseEvent event ) {
                if ( event.getModifiers( ) == MouseEvent.BUTTON3_MASK ) {
                    showPopUpMenu( event );
                }
            }
        } );
    }

    public void showPopUpMenu( MouseEvent event ) {
        JPopupMenu menu = PopupsMenuFactory.stockPanel( client );
        menu.show( event.getComponent( ), event.getX( ), event.getY( ) );
    }

    private void init() {

        setLayout( null );
        setBounds( 0, 0, 0, height );

        // Ticker section
        ticker = new MyGuiComps.MyPanel( );
        ticker.setLayout( null );
        ticker.setBounds( 0, 0, 311, height );
        ticker.setBackground( backGround );

        openField = new MyGuiComps.MyTextField( );
        openField.setXY( 5, 6 );
        ticker.add( openField );

        openPresentField = new MyGuiComps.MyTextField( );
        openPresentField.setFont( openPresentField.getFont( ).deriveFont( Font.BOLD ) );
        openPresentField.setXY( 5, 35 );
        ticker.add( openPresentField );

        indexField = new MyGuiComps.MyTextField( );
        indexField.setXY( 80, 6 );
        ticker.add( indexField );

        indexPresentField = new MyGuiComps.MyTextField( );
        indexPresentField.setFont( indexPresentField.getFont( ).deriveFont( Font.BOLD ) );
        indexPresentField.setXY( 80, 35 );
        ticker.add( indexPresentField );

        lowField = new MyGuiComps.MyTextField( );
        lowField.setXY( 155, 6 );
        ticker.add( lowField );

        lowPresentField = new MyGuiComps.MyTextField( );
        lowPresentField.setFont( lowPresentField.getFont( ).deriveFont( Font.BOLD ) );
        lowPresentField.setXY( 155, 35 );
        ticker.add( lowPresentField );

        highField = new MyGuiComps.MyTextField( );
        highField.setXY( 230, 6 );
        ticker.add( highField );

        highPresentField = new MyGuiComps.MyTextField( );
        highPresentField.setFont( highPresentField.getFont( ).deriveFont( Font.BOLD ) );
        highPresentField.setXY( 230, 35 );
        ticker.add( highPresentField );

        futureField = new MyGuiComps.MyTextField( );
        futureField.setXY( 5, 64 );
        ticker.add( futureField );

        opField = new MyGuiComps.MyTextField( );
        opField.setFont( opField.getFont( ).deriveFont( Font.BOLD ) );
        opField.setXY( 80, 64 );
        ticker.add( opField );

        opAvgField = new MyGuiComps.MyTextField( );
        opAvgField.setXY( 155, 64 );
        ticker.add( opAvgField );

        // Quarter
        opAvgQuarterField = new MyGuiComps.MyTextField( );
        opAvgQuarterField.setXY( 155, 93 );
        ticker.add( opAvgQuarterField );

        contractQuarterField = new MyGuiComps.MyTextField( );
        contractQuarterField.setXY( 5, 93 );
        ticker.add( contractQuarterField );

        opQuarterField = new MyGuiComps.MyTextField( );
        opQuarterField.setFont( opQuarterField.getFont( ).deriveFont( Font.BOLD ) );
        opQuarterField.setXY( 80, 93 );
        opQuarterField.setForeground( Color.WHITE );
        ticker.add( opQuarterField );
        add( ticker );

        // ---------- Races and roll ---------- //
        // Panel
        racesAndRollPanel = new MyGuiComps.MyPanel( );
        racesAndRollPanel.setLayout( null );
        racesAndRollPanel.setBackground( backGround );
        racesAndRollPanel.setBounds( 312, 0, 111, height );
        add( racesAndRollPanel );

        // Con lbl
        conRacesLbl = new MyGuiComps.MyLabel( "Cont" );
        conRacesLbl.setHorizontalAlignment( JLabel.CENTER );
        conRacesLbl.setBounds( 5, 5, 50, 25 );
        conRacesLbl.setForeground( Themes.BLUE );
        racesAndRollPanel.add( conRacesLbl );

        // Con field
        conRacesField = new MyGuiComps.MyTextField( );
        conRacesField.setBounds( 55, 7, 50, 25 );
        racesAndRollPanel.add( conRacesField );

        // Ind lbl
        indRacesLbl = new MyGuiComps.MyLabel( "Ind" );
        indRacesLbl.setHorizontalAlignment( JLabel.CENTER );
        indRacesLbl.setBounds( 5, 35, 50, 25 );
        indRacesLbl.setForeground( Themes.BLUE );
        racesAndRollPanel.add( indRacesLbl );

        // Ind field
        indRacesField = new MyGuiComps.MyTextField( );
        indRacesField.setBounds( 55, 37, 50, 25 );
        racesAndRollPanel.add( indRacesField );

        rollLbl = new MyGuiComps.MyLabel( "Roll" );
        rollLbl.setHorizontalAlignment( JLabel.CENTER );
        rollLbl.setBounds( 5, 65, 50, 25 );
        rollLbl.setForeground( Themes.BLUE );
        racesAndRollPanel.add( rollLbl );

        // Roll field
        rollField = new MyGuiComps.MyTextField( );
        rollField.setBounds( 55, 67, 50, 25 );
        racesAndRollPanel.add( rollField );

        // ---------- Exp ---------- //
        expPanel = new MyGuiComps.MyPanel( );
        expPanel.setLayout( null );
        expPanel.setBounds( 424, 0, 111, height );
        add( expPanel );

        // Move
        expMoveLbl = new MyGuiComps.MyLabel( "Move" );
        expMoveLbl.setBounds( 5, 7, 50, 25 );
        expMoveLbl.setFont( Themes.VEDANA_12 );
        expPanel.add( expMoveLbl );

        expMoveField = new MyGuiComps.MyTextField( );
        expMoveField.setBounds( 55, 7, 50, 25 );
        expPanel.add( expMoveField );

        // Races
        expRacesLbl = new MyGuiComps.MyLabel( "Races" );
        expRacesLbl.setFont( Themes.VEDANA_12 );
        expRacesLbl.setBounds( 5, 37, 50, 25 );
        expPanel.add( expRacesLbl );

        expRacesField = new MyGuiComps.MyTextField( );
        expRacesField.setBounds( 55, 37, 50, 25 );
        expPanel.add( expRacesField );

        // OpAvg
        expOpAvgLbl = new MyGuiComps.MyLabel( "OP/AVG" );
        expOpAvgLbl.setFont( Themes.VEDANA_12 );
        expOpAvgLbl.setBounds( 5, 67, 50, 25 );
        expPanel.add( expOpAvgLbl );

        expOpAvgField = new MyGuiComps.MyTextField( );
        expOpAvgField.setBounds( 55, 67, 50, 25 );
        expPanel.add( expOpAvgField );

    }

    public Updater getUpdater() {
        if ( updater == null ) {
            updater = new Updater( client );
        }
        return updater;
    }

    public void close() {
        getUpdater().close();
    }

    @Override
    public void updateText() {
        try {

            // ---------- Ticker ---------- //
            openField.setText( L.format100( client.getOpen( ) ) );
            highField.setText( L.format100( client.getHigh( ) ) );
            lowField.setText( L.format100( client.getLow( ) ) );
            indexField.setText( L.format100( client.getIndex( ) ) );
            futureField.setText( L.format100( mainOptions.getContract( ) ) );

            // Ticker present
            openPresentField.colorBack( L.present( client.getOpen( ), client.getBase( ) ), L.format100( ), "%" );
            highPresentField.colorBack( L.present( client.getHigh( ), client.getBase( ) ), L.format100( ), "%" );
            lowPresentField.colorBack( L.present( client.getLow( ), client.getBase( ) ), L.format100( ), "%" );
            indexPresentField.colorBack( L.present( client.getIndex( ), client.getBase( ) ), L.format100( ), "%" );
            // OP
            opAvgField.colorForge( mainOptions.getOpAvg( ), L.format100( ) );
            opField.colorBack( mainOptions.getOp( ), L.format100( ) );

            // Quarter
            opQuarterField.colorBack( optMonth.getOp( ), L.format100( ) );
            opAvgQuarterField.colorForge( optMonth.getOpAvg( ), L.format100( ) );
            contractQuarterField.setText( L.format100( optMonth.getContract( ) ) );

            // Races and roll
            // Races
            conRacesField.colorForge( client.getFutSum( ) );
            indRacesField.colorForge( client.getIndexSum( ) );

            // Roll
            double month = optWeek.getContract( );
            double quarter = optMonth.getContract( );
            rollField.colorForge( quarter - month, L.format100( ) );
        } catch ( NullPointerException e ) {
            e.printStackTrace( );
        } catch ( Exception e ) {
            e.printStackTrace( );
        }
    }

    @Override
    public void updateRaces() {
        updateIfChanged( );
    }

    void updateIfChanged() {
        // Con up
        if ( client.isConUpChanged( ) ) {
            L.noisy( conRacesField, Themes.GREEN );
        }

        // Con down
        if ( client.isConDownChanged( ) ) {
            L.noisy( conRacesField, Themes.RED );
        }

        // Ind up
        if ( client.isIndUpChanged( ) ) {
            L.noisy( indRacesField, Themes.GREEN );
        }

        // Ind down
        if ( client.isIndDownChanged( ) ) {
            L.noisy( indRacesField, Themes.RED );
        }
    }

    public class Updater extends MyThread implements Runnable {

        public Updater( BASE_CLIENT_OBJECT client ) {
            super( client );
            setName( "UPDATER" );
        }

        @Override
        public void initRunnable() {
            setRunnable( this );
        }

        @Override
        public void run() {

            while ( isRun( ) ) {
                try {
                    // Sleep
                    Thread.sleep( 500 );

                    updateText( );

                } catch ( InterruptedException e ) {
                }
            }
        }

        public void close() {
            setRun( false );
        }

    }

}
