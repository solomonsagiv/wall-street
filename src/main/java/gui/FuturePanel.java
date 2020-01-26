package gui;

import charts.CONTRACT_IND_CHART;
import charts.CONTRACT_IND_CHART_LIVE;
import charts.INDEX_OPAVG_EQUALMOVE_CHART;
import charts.QUARTER_CONTRACT_IND_CHART_LIVE;
import locals.L;
import locals.Themes;
import options.Option;
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
import java.text.DecimalFormat;

public class FuturePanel extends BaseFuturePanel {

    // Url
    String url = "";

    // Ticker
    JPanel ticker;
    JTextField openField;
    JTextField openPresentField;
    JTextField indexField;
    JTextField indexPresentField;
    JTextField lowField;
    JTextField lowPresentField;
    JTextField highField;
    JTextField highPresentField;
    JTextField futureField;
    JTextField opField;
    JTextField opAvgField;
    JTextField opAvgEqualeMoveField;

    // Exp
    JPanel exp;

    // Quarter
    JTextField opAvgQuarterField;
    JTextField opQuarterField;
    JTextField contractQuarterField;

    // Races and roll
    JPanel racesAndRollPanel;
    JLabel conRacesLbl;
    JLabel indRacesLbl;
    JLabel rollLbl;

    public JTextField conRacesField;
    public JTextField indRacesField;
    JTextField rollField;

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

                    JMenuItem fut_ind_chart = new JMenuItem( "Contract vs Ind" );
                    fut_ind_chart.addActionListener( new ActionListener( ) {
                        @Override
                        public void actionPerformed( ActionEvent e ) {

                            CONTRACT_IND_CHART chart = new CONTRACT_IND_CHART( client );
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

                    JMenuItem index_opAvg_equalMove = new JMenuItem( "Ind OpAvg Equal move" );
                    index_opAvg_equalMove.addActionListener( new ActionListener( ) {
                        @Override
                        public void actionPerformed( ActionEvent e ) {

                            INDEX_OPAVG_EQUALMOVE_CHART chart = new INDEX_OPAVG_EQUALMOVE_CHART( client );
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
                            FullOptionsWindow fullOptionsWindow = new FullOptionsWindow( client );
                            fullOptionsWindow.frame.setVisible( true );
                        }
                    } );

                    JMenuItem optionsPosition = new JMenuItem( "Positions" );
                    optionsPosition.addActionListener( new ActionListener( ) {
                        @Override
                        public void actionPerformed( ActionEvent e ) {
                            new PositionsWindow( client, client.getOptionsHandler().getPositionCalculator().getPositions() );
                        }
                    } );

                    export.add( exportSumLine );

                    charts.add( quarterContractIndexRealTime );
                    charts.add( contractIndexRealTime );
                    charts.add( index_opAvg_equalMove );
                    charts.add( fut_ind_chart );

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
        ticker = new JPanel( null );
        ticker.setBounds( 0, 0, 311, height );
        ticker.setBackground( backGround );

        openField = tickerTextField( 5, 6 );
        ticker.add( openField );
        openPresentField = tickerPresent( 5, 35 );
        ticker.add( openPresentField );
        indexField = tickerTextField( 80, 6 );
        ticker.add( indexField );
        indexPresentField = tickerPresent( 80, 35 );
        ticker.add( indexPresentField );
        lowField = tickerTextField( 155, 6 );
        ticker.add( lowField );
        lowPresentField = tickerPresent( 155, 35 );
        ticker.add( lowPresentField );
        highField = tickerTextField( 230, 6 );
        ticker.add( highField );
        highPresentField = tickerPresent( 230, 35 );
        ticker.add( highPresentField );
        futureField = tickerTextField( 5, 64 );
        ticker.add( futureField );

        opField = tickerPresent( 80, 64 );
        ticker.add( opField );

        opAvgEqualeMoveField = tickerTextField( 230, 64 );
        ticker.add( opAvgEqualeMoveField );

        opAvgField = tickerTextField( 155, 64 );
        ticker.add( opAvgField );

        // Quarter
        opAvgQuarterField = tickerTextField( 155, 93 );
        ticker.add( opAvgQuarterField );

        contractQuarterField = tickerTextField( 5, 93 );
        ticker.add( contractQuarterField );

        opQuarterField = tickerTextField( 80, 93 );
        opQuarterField.setForeground( Color.WHITE );
        ticker.add( opQuarterField );

        add( ticker );

        // Races and roll
        // Panel
        racesAndRollPanel = new JPanel( null );
        racesAndRollPanel.setBackground( backGround );
        racesAndRollPanel.setBounds( 312, 0, 111, height );
        add( racesAndRollPanel );

        // Con lbl
        conRacesLbl = new JLabel( "Cont" );
        conRacesLbl.setHorizontalAlignment( JLabel.CENTER );
        conRacesLbl.setBounds( 5, 5, 50, 25 );
        conRacesLbl.setForeground( Themes.BLUE );

        racesAndRollPanel.add( conRacesLbl );

        // Con field
        conRacesField = tickerTextField( 55, 7, 50, 25 );
        racesAndRollPanel.add( conRacesField );

        // Ind lbl
        indRacesLbl = new JLabel( "Ind" );
        indRacesLbl.setHorizontalAlignment( JLabel.CENTER );
        indRacesLbl.setBounds( 5, 35, 50, 25 );
        indRacesLbl.setForeground( Themes.BLUE );

        racesAndRollPanel.add( indRacesLbl );

        // Ind field
        indRacesField = tickerTextField( 55, 37, 50, 25 );
        racesAndRollPanel.add( indRacesField );

        rollLbl = new JLabel( "Roll" );
        rollLbl.setHorizontalAlignment( JLabel.CENTER );
        rollLbl.setBounds( 5, 65, 50, 25 );
        rollLbl.setForeground( Themes.BLUE );

        racesAndRollPanel.add( rollLbl );

        // Roll field
        rollField = tickerTextField( 55, 67, 50, 25 );
        racesAndRollPanel.add( rollField );

    }

    @Override
    public void colorRaces( int runner1_up_down, int runner2_up_down, int competition_Number ) {
    }

    // Present
    public void colorBack( JTextField field, double val ) {

        if ( val >= 0 ) {
            field.setText( L.str( val ) );
            field.setBackground( green );
        } else {
            field.setText( L.str( val ) );
            field.setBackground( red );
        }

    }

    // Present
    public void colorBack( JTextField field, double val, DecimalFormat format ) {

        if ( val >= 0 ) {
            field.setText( format.format( val ) );
            field.setBackground( green );
        } else {
            field.setText( format.format( val ) );
            field.setBackground( red );
        }

    }

    // Present
    public void colorForf( JTextField field, double val ) {

        if ( val > 0 ) {
            field.setText( L.str( val ) );
            field.setForeground( green );
        } else {
            field.setText( L.str( val ) );
            field.setForeground( red );
        }
    }

    // Present
    public void colorForf( JTextField field, double val, DecimalFormat format ) {

        if ( val > 0 ) {
            field.setText( format.format( val ) );
            field.setForeground( green );
        } else {
            field.setText( format.format( val ) );
            field.setForeground( red );
        }
    }

    // Present
    public void colorForfInt( JTextField field, double val ) {

        if ( val > 0 ) {
            field.setText( L.str( ( int ) val ) );
            field.setForeground( green );
        } else {
            field.setText( L.str( ( int ) val ) );
            field.setForeground( red );
        }
    }

    // Present
    public void colorBackPresent( JTextField field, double val ) {

        if ( val >= 0 ) {
            field.setText( L.str( val ) + "%" );
            field.setBackground( green );
        } else {
            field.setText( L.str( val ) + "%" );
            field.setBackground( red );
        }

    }

    // Present
    public void colorBackPresent( JTextField field, double val, DecimalFormat format ) {

        if ( val >= 0 ) {
            field.setText( format.format( val ) + "%" );
            field.setBackground( green );
        } else {
            field.setText( format.format( val ) + "%" );
            field.setBackground( red );
        }

    }

    // JText filed in races type
    public JTextField racesTextField( JTextField field, int x, int y ) {
        field = new JTextField( );
        field.setBounds( x, y, 48, 25 );
        field.setFont( font );
        field.setHorizontalAlignment( JTextField.CENTER );
        field.setBackground( Themes.GREY_VERY_LIGHT );
        field.setBorder( null );
        return field;
    }

    // JText filed in races type
    public JTextField tickerTextField( int x, int y ) {
        JTextField field = new JTextField( );
        field.setBounds( x, y, 70, 25 );
        field.setFont( font );
        field.setHorizontalAlignment( JTextField.CENTER );
        field.setBackground( Themes.GREY_VERY_LIGHT );
        field.setBorder( null );
        return field;
    }

    // JText filed in races type
    public JTextField tickerTextField( int x, int y, int width, int height ) {
        JTextField field = new JTextField( );
        field.setBounds( x, y, width, height );
        field.setFont( font );
        field.setHorizontalAlignment( JTextField.CENTER );
        field.setBackground( Themes.GREY_VERY_LIGHT );
        field.setBorder( null );
        return field;
    }

    // JText filed in races type
    public JTextField tickerPresent( int x, int y ) {
        JTextField field = new JTextField( );
        field.setBounds( x, y, 70, 25 );
        field.setFont( font.deriveFont( Font.BOLD ) );
        field.setForeground( Color.WHITE );
        field.setHorizontalAlignment( JTextField.CENTER );
        field.setBackground( Themes.GREY_VERY_LIGHT );
        field.setBorder( null );
        return field;
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

            while ( isRun( ) ) {
                try {

                    // Sleep
                    Thread.sleep( 1000 );

                    updateLists( );

                    // ---------- Ticker ---------- //
                    openField.setText( L.format100( client.getOpen( ) ) );
                    highField.setText( L.format100( client.getHigh( ) ) );
                    lowField.setText( L.format100( client.getLow( ) ) );
                    indexField.setText( L.format100( client.getIndex( ) ) );
                    futureField.setText( L.format100( client.getOptionsHandler( ).getMainOptions( ).getContract( ) ) );

                    // Ticker present
                    colorBackPresent( openPresentField, toPresent( client.getOpen( ), client.getBase( ) ), L.format100( ) );
                    colorBackPresent( highPresentField, toPresent( client.getHigh( ), client.getBase( ) ), L.format100( ) );
                    colorBackPresent( lowPresentField, toPresent( client.getLow( ), client.getBase( ) ), L.format100( ) );
                    colorBackPresent( indexPresentField, toPresent( client.getIndex( ), client.getBase( ) ), L.format100( ) );

                    colorForf( opAvgField, client.getOptionsHandler( ).getMainOptions( ).getOpAvg( ), L.format100( ) );

                    // OP
                    double op = client.getOptionsHandler( ).getMainOptions( ).getOp( );
                    colorBack( opField, op );

                    // Equal move OpAvg
                    colorForf( opAvgEqualeMoveField, client.getOptionsHandler( ).getMainOptions( ).getOpAvgEqualMoveCalculator( ).getMoveOpAvg( ), L.format100( ) );

                    // Quarter
                    colorBack( opQuarterField, client.getOptionsHandler( ).getOptionsQuarter( ).getOp( ), L.format100( ) );
                    colorForf( opAvgQuarterField, client.getOptionsHandler( ).getOptionsQuarter( ).getOpAvg( ), L.format100( ) );
                    contractQuarterField.setText( L.format100( client.getOptionsHandler( ).getOptionsQuarter( ).getContract( ) ) );

                    // Races and roll
                    // Races
                    colorForfInt( conRacesField, client.getFutSum( ) );
                    colorForfInt( indRacesField, client.getIndexSum( ) );

                    // Roll
                    double month = client.getOptionsHandler( ).getOptionsMonth( ).getContract( );
                    double quarter = client.getOptionsHandler( ).getOptionsQuarter( ).getContract( );
                    colorForf( rollField, quarter - month, L.format100( ) );

                    mySleep += 1000;
                } catch ( InterruptedException e ) {
                    close( );
                }
            }
        }

        private double toPresent( double d, double base ) {
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
