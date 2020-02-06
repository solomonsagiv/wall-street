package gui;

import charts.CONTRACT_IND_CHART_LIVE;
import charts.INDEX_RACES_CHART;
import charts.OPAVG_MOVE_CHART;
import charts.QUARTER_CONTRACT_IND_CHART_LIVE;
import locals.L;
import locals.Themes;
import options.Option;
import options.Options;
import options.OptionsWindow;
import options.Strike;
import options.fullOptions.FullOptionsWindow;
import options.fullOptions.PositionsWindow;
import serverObjects.BASE_CLIENT_OBJECT;
import threads.MyThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FuturePanel extends BaseFuturePanel {

    // Url
    String url = "";

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
    MyGuiComps.MyTextField opAvgEqualeMoveField;

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

    Font font = Themes.VEDANA_12;
    Color green = Themes.GREEN;
    Color red = Themes.RED;
    Color backGround = Themes.GREY_LIGHT;

    BASE_CLIENT_OBJECT client;

    int listSleep = 1000;

    private Updater updater;

    public FuturePanel( BASE_CLIENT_OBJECT base_CLIENT_OBJECT ) {
        init( base_CLIENT_OBJECT );
    }

    private void init( BASE_CLIENT_OBJECT client ) {
        addMouseListener( new MouseAdapter( ) {
            @Override
            public void mouseClicked( MouseEvent event ) {
                if ( event.getModifiers( ) == MouseEvent.BUTTON3_MASK ) {
                    // Main menu
                    JPopupMenu menu = new JPopupMenu( );

                    // Charts menu
                    JMenu charts = new JMenu( "Charts" );

                    JMenuItem indexRacesChart = new JMenuItem( "Index races" );
                    indexRacesChart.addActionListener( new ActionListener( ) {
                        @Override
                        public void actionPerformed( ActionEvent e ) {

                            INDEX_RACES_CHART chart = new INDEX_RACES_CHART( client );
                            chart.createChart( );

                        }
                    } );

                    JMenuItem opAvgMoveChart = new JMenuItem( "Op avg move" );
                    opAvgMoveChart.addActionListener( new ActionListener( ) {
                        @Override
                        public void actionPerformed( ActionEvent e ) {

                            OPAVG_MOVE_CHART chart = new OPAVG_MOVE_CHART( client );
                            chart.createChart( );

                        }
                    } );

                    JMenuItem contractIndexRealTime = new JMenuItem( "Contract vs Ind real time" );
                    contractIndexRealTime.addActionListener( new ActionListener( ) {
                        @Override
                        public void actionPerformed( ActionEvent e ) {

                            CONTRACT_IND_CHART_LIVE chart = new CONTRACT_IND_CHART_LIVE( client );
                            chart.createChart( );

                        }
                    } );

                    JMenuItem quarterContractIndexRealTime = new JMenuItem( "Quarter vs Ind real time" );
                    quarterContractIndexRealTime.addActionListener( new ActionListener( ) {
                        @Override
                        public void actionPerformed( ActionEvent e ) {

                            QUARTER_CONTRACT_IND_CHART_LIVE chart = new QUARTER_CONTRACT_IND_CHART_LIVE( client );
                            chart.createChart( );

                        }
                    } );

                    // Export menu
                    JMenu export = new JMenu( "Export" );

                    JMenuItem exportSumLine = new JMenuItem( "Export sum line" );
                    exportSumLine.addActionListener( new ActionListener( ) {
                        @Override
                        public void actionPerformed( ActionEvent e ) {
                            client.getTablesHandler( ).getSumHandler( ).getHandler( ).insertLine( );
                        }
                    } );

                    JMenuItem details = new JMenuItem( "Details" );
                    details.addActionListener( new ActionListener( ) {
                        @Override
                        public void actionPerformed( ActionEvent e ) {
                            DetailsWindow detailsWindow = new DetailsWindow( client );
                            detailsWindow.frame.setVisible( true );
                        }
                    } );

                    JMenuItem optionsCounter = new JMenuItem( "Options counter table" );
                    optionsCounter.addActionListener( new ActionListener( ) {
                        @Override
                        public void actionPerformed( ActionEvent e ) {
                            OptionsWindow window = new OptionsWindow( client, client.getOptionsHandler( ).getMainOptions( ) );
                            window.frame.setVisible( true );
                            window.startWindowUpdater( );
                        }
                    } );

                    JMenuItem fullOptionsTable = new JMenuItem( "Full options table" );
                    fullOptionsTable.addActionListener( new ActionListener( ) {
                        @Override
                        public void actionPerformed( ActionEvent e ) {
                            new FullOptionsWindow( client );
                        }
                    } );

                    JMenuItem optionsPosition = new JMenuItem( "Positions" );
                    optionsPosition.addActionListener( new ActionListener( ) {
                        @Override
                        public void actionPerformed( ActionEvent e ) {
                            new PositionsWindow( client, client.getOptionsHandler( ).getPositionCalculator( ).getPositions( ) );
                        }
                    } );

                    export.add( exportSumLine );

                    charts.add( quarterContractIndexRealTime );
                    charts.add( contractIndexRealTime );
                    charts.add( indexRacesChart );
                    charts.add( opAvgMoveChart );

                    menu.add( details );
                    menu.add( export );
                    menu.add( charts );
                    menu.add( optionsCounter );
                    menu.add( fullOptionsTable );
                    menu.add( optionsPosition );

                    // Show the menu
                    menu.show( event.getComponent( ), event.getX( ), event.getY( ) );
                }
            }
        } );

        this.client = client;
        setLayout( null );
        setBounds( 0, 0, 0, height );

        // Ticker section
        ticker = new MyGuiComps.MyPanel( );
        ticker.setLayout( null );
        ticker.setBounds( 0, 0, 311, height );
        ticker.setBackground( backGround );

        openField = new MyGuiComps.MyTextField( 20 );
        openField.setXY( 5, 6 );
        ticker.add( openField );

        openPresentField = new MyGuiComps.MyTextField( 20 );
        openPresentField.setForeground( Color.WHITE );
        openPresentField.setFont( openPresentField.getFont( ).deriveFont( Font.BOLD ) );
        openPresentField.setXY( 5, 35 );
        ticker.add( openPresentField );

        indexField = new MyGuiComps.MyTextField( 20 );
        indexField.setXY( 80, 6 );
        ticker.add( indexField );

        indexPresentField = new MyGuiComps.MyTextField( 20 );
        indexPresentField.setForeground( Color.WHITE );
        indexPresentField.setFont( indexPresentField.getFont( ).deriveFont( Font.BOLD ) );
        indexPresentField.setXY( 80, 35 );
        ticker.add( indexPresentField );

        lowField = new MyGuiComps.MyTextField( 20 );
        lowField.setXY( 155, 6 );
        ticker.add( lowField );

        lowPresentField = new MyGuiComps.MyTextField( 20 );
        lowPresentField.setForeground( Color.WHITE );
        lowPresentField.setFont( lowPresentField.getFont( ).deriveFont( Font.BOLD ) );
        lowPresentField.setXY( 155, 35 );
        ticker.add( lowPresentField );

        highField = new MyGuiComps.MyTextField( 20 );
        highField.setXY( 230, 6 );
        ticker.add( highField );

        highPresentField = new MyGuiComps.MyTextField( 20 );
        highPresentField.setForeground( Color.WHITE );
        highPresentField.setFont( highPresentField.getFont( ).deriveFont( Font.BOLD ) );
        highPresentField.setXY( 230, 35 );
        ticker.add( highPresentField );

        futureField = new MyGuiComps.MyTextField( 20 );
        futureField.setXY( 5, 64 );
        ticker.add( futureField );

        opField = new MyGuiComps.MyTextField( 20 );
        opField.setForeground( Color.WHITE );
        opField.setFont( opField.getFont( ).deriveFont( Font.BOLD ) );
        opField.setXY( 80, 64 );
        ticker.add( opField );

        opAvgEqualeMoveField = new MyGuiComps.MyTextField( 20 );
        opAvgEqualeMoveField.setXY( 230, 64 );
        ticker.add( opAvgEqualeMoveField );

        opAvgField = new MyGuiComps.MyTextField( 20 );
        opAvgField.setXY( 155, 64 );
        ticker.add( opAvgField );

        // Quarter
        opAvgQuarterField = new MyGuiComps.MyTextField( 20 );
        opAvgQuarterField.setXY( 155, 93 );
        ticker.add( opAvgQuarterField );

        contractQuarterField = new MyGuiComps.MyTextField( 20 );
        contractQuarterField.setXY( 5, 93 );
        ticker.add( contractQuarterField );

        opQuarterField = new MyGuiComps.MyTextField( 20 );
        opQuarterField.setForeground( Color.WHITE );
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
        conRacesField = new MyGuiComps.MyTextField( 20 );
        conRacesField.setBounds( 55, 7, 50, 25 );
        racesAndRollPanel.add( conRacesField );

        // Ind lbl
        indRacesLbl = new MyGuiComps.MyLabel( "Ind" );
        indRacesLbl.setHorizontalAlignment( JLabel.CENTER );
        indRacesLbl.setBounds( 5, 35, 50, 25 );
        indRacesLbl.setForeground( Themes.BLUE );

        racesAndRollPanel.add( indRacesLbl );

        // Ind field
        indRacesField = new MyGuiComps.MyTextField( 20 );
        indRacesField.setBounds( 55, 37, 50, 25 );
        racesAndRollPanel.add( indRacesField );

        rollLbl = new MyGuiComps.MyLabel( "Roll" );
        rollLbl.setHorizontalAlignment( JLabel.CENTER );
        rollLbl.setBounds( 5, 65, 50, 25 );
        rollLbl.setForeground( Themes.BLUE );

        racesAndRollPanel.add( rollLbl );

        // Roll field
        rollField = new MyGuiComps.MyTextField( 20 );
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

        expMoveField = new MyGuiComps.MyTextField( 20 );
        expMoveField.setBounds( 55, 7, 50, 25 );
        expPanel.add( expMoveField );

        // Races
        expRacesLbl = new MyGuiComps.MyLabel( "Races" );
        expRacesLbl.setFont( Themes.VEDANA_12 );
        expRacesLbl.setBounds( 5, 37, 50, 25 );
        expPanel.add( expRacesLbl );

        expRacesField = new MyGuiComps.MyTextField( 20 );
        expRacesField.setBounds( 55, 37, 50, 25 );
        expPanel.add( expRacesField );

        // OpAvg
        expOpAvgLbl = new MyGuiComps.MyLabel( "OP/AVG" );
        expOpAvgLbl.setFont( Themes.VEDANA_12 );
        expOpAvgLbl.setBounds( 5, 67, 50, 25 );
        expPanel.add( expOpAvgLbl );

        expOpAvgField = new MyGuiComps.MyTextField( 20 );
        expOpAvgField.setBounds( 55, 67, 50, 25 );
        expPanel.add( expOpAvgField );

    }

    @Override
    public void colorRaces( int runner1_up_down, int runner2_up_down, int competition_Number ) {
    }

    public Updater getUpdater() {
        if ( updater == null ) {
            updater = new Updater( client );
        }
        return updater;
    }

    public class Updater extends MyThread implements Runnable {


        long mySleep = 0;

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

            setRun( true );

            Options optionsMonth = client.getOptionsHandler( ).getOptionsMonth( );
            Options optionsQuarter = client.getOptionsHandler( ).getOptionsQuarter( );
            Options mainOptions = client.getOptionsHandler( ).getMainOptions( );
            while ( isRun( ) ) {
                try {

                    // Sleep
                    Thread.sleep( 1000 );

                    updateLists( );

                    // ---------- Ticker ---------- //
                    openField.setText( L.format100( client.getOpen( ).getVal( ) ) );
                    highField.setText( L.format100( client.getHigh( ).getVal( ) ) );
                    lowField.setText( L.format100( client.getLow( ).getVal( ) ) );
                    indexField.setText( L.format100( client.getIndex( ).getVal( ) ) );
                    futureField.setText( L.format100( mainOptions.getContract( ).getVal( ) ) );

                    // Ticker present
                    openPresentField.colorBack( toPresent( client.getOpen( ).getVal( ) ), L.format100( ), "%" );
                    highPresentField.colorBack( toPresent( client.getHigh( ).getVal( ) ), L.format100( ), "%" );
                    lowPresentField.colorBack( toPresent( client.getLow( ).getVal( ) ), L.format100( ), "%" );
                    indexPresentField.colorBack( toPresent( client.getIndex( ).getVal( ) ), L.format100( ), "%" );

                    // OP
                    opAvgField.colorForge( mainOptions.getOpAvg( ).getVal( ), L.format100( ) );
                    opField.colorBack( mainOptions.getOp( ).getVal( ), L.format100( ) );

                    // Equal move OpAvg
                    opAvgEqualeMoveField.colorForge( mainOptions.getOpAvgEqualMoveCalculator( ).getMove( ).getVal( ), L.format100( ) );

                    // Quarter
                    opQuarterField.colorBack( optionsQuarter.getOp( ).getVal( ), L.format100( ) );
                    opAvgQuarterField.colorForge( optionsQuarter.getOpAvg( ).getVal( ), L.format100( ) );
                    contractQuarterField.setText( L.format100( client.getOptionsHandler( ).getOptionsQuarter( ).getContract( ).getVal( ) ) );

                    // Races and roll
                    // Races
                    conRacesField.colorForge( client.getFutSum( ) );
                    indRacesField.colorForge( client.getIndexSum( ) );

                    // Roll
                    double month = optionsMonth.getContract( ).getVal( );
                    double quarter = optionsQuarter.getContract( ).getVal( );
                    rollField.colorForge( quarter - month, L.format100( ) );

                    mySleep += 1000;
                } catch ( InterruptedException e ) {
                    break;
                }
            }
        }

        private double toPresent( double d ) {
            double base = client.getBase( ).getVal( );
            return ( ( d - base ) / base ) * 100;
        }

        private void updateLists() {

            // Options lists
            for ( Strike strike : client.getOptionsHandler( ).getMainOptions( ).getStrikes( ) ) {
                Option call = strike.getCall( );
                call.getBidAskCounterList( ).add( call.getBidAskCounter( ) );

                Option put = strike.getPut( );
                put.getBidAskCounterList( ).add( put.getBidAskCounter( ) );

            }
        }

        public void close() {
            setRun( false );
        }

    }

}
