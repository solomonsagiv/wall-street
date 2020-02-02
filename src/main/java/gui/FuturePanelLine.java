package gui;

import locals.Themes;
import options.OptionsWindow;
import options.Strike;
import org.hibernate.SessionFactory;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.INDEX_CLIENT_OBJECT;
import threads.MyThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FuturePanelLine extends BaseFuturePanel {

    // Url
    String url = "";

    // Races
    JPanel races;
    JTextField future_up;
    JTextField future_down;
    JTextField index_up;
    JTextField index_down;
    JTextField future_sum;
    JTextField index_sum;

    // Ticker
    JPanel ticker;

    JTextField open;
    JTextField open_present;
    JTextField index;
    JTextField index_present;
    JTextField low;
    JTextField low_present;
    JTextField high;
    JTextField high_present;
    JTextField future;
    JTextField futureOptionsCounterField;

    // Options bid ask counter table
    JPanel optionsPanel;
    JScrollPane optionsScrollPane;
    JTable optionsTable;

    // Exp
    JPanel exp;
    JTextField exp_future;
    JTextField exp_index;
    JTextField exp_move;
    JTextField optimipesimi;

    // Log
    JPanel logPanel;
    JTextArea logArea;

    Font bold_font = new Font( "Verdana", Font.BOLD, 15 );
    Font font = new Font( "Arial", Font.PLAIN, 17 );
    // Color backGroundColor = new Color(176, 196, 222);
    Color darkYellow = new Color( 250, 197, 0 );

    BASE_CLIENT_OBJECT client;

    private Updater updater;

    public FuturePanelLine( BASE_CLIENT_OBJECT client ) {
        this.client = client;
        setUpdater( new Updater( client ) );

        addMouseListener( new MouseAdapter( ) {
            @Override
            public void mouseClicked( MouseEvent event ) {
                if ( event.getModifiers( ) == MouseEvent.BUTTON3_MASK ) {
                    // Main menu
                    JPopupMenu menu = new JPopupMenu( );

                    // Charts menu
                    JMenu charts = new JMenu( "Charts" );
                    JMenu times = new JMenu( "Index vs races" );
                    charts.add( times );

                    // Start
                    JMenuItem start = new JMenuItem( "Start" );
                    start.addActionListener( new ActionListener( ) {
                        @Override
                        public void actionPerformed( ActionEvent e ) {
                            client.startAll( );
                        }
                    } );


                    // Close
                    JMenuItem close = new JMenuItem( "Close" );
                    close.addActionListener( new ActionListener( ) {
                        @Override
                        public void actionPerformed( ActionEvent e ) {
                            client.closeAll( );
                        }
                    } );

                    // Details
                    JMenuItem details = new JMenuItem( "Details" );
                    details.addActionListener( new ActionListener( ) {
                        @Override
                        public void actionPerformed( ActionEvent e ) {
                            DetailsWindow detailsWindow = new DetailsWindow( client );
                            detailsWindow.frame.setVisible( true );
                        }
                    } );

                    // Export
                    JMenu export = new JMenu( "Export" );

                    JMenuItem fullExport = new JMenuItem( "Full export" );
                    fullExport.addActionListener( new ActionListener( ) {
                        @Override
                        public void actionPerformed( ActionEvent e ) {
                            SessionFactory factory = client.getSessionfactory( );
                            client.getTablesHandler( ).getSumHandler( ).getHandler( ).insertLine( );
                            client.closeAll( );
                        }
                    } );

                    JMenuItem exportSumLine = new JMenuItem( "Export sum line" );
                    exportSumLine.addActionListener( new ActionListener( ) {
                        @Override
                        public void actionPerformed( ActionEvent e ) {
                            client.getTablesHandler( ).getSumHandler( ).getHandler( ).insertLine( );
                        }
                    } );

                    // Append to export
                    export.add( fullExport );
                    export.add( exportSumLine );

                    // Options counter
                    JMenuItem optionsCounter = new JMenuItem( "Options counter table" );
                    optionsCounter.addActionListener( new ActionListener( ) {
                        @Override
                        public void actionPerformed( ActionEvent e ) {
                            OptionsWindow window = new OptionsWindow( client, client.getOptionsHandler( ).getOptionsMonth( ) );
                            window.frame.setVisible( true );
                            window.startWindowUpdater( );
                        }
                    } );

                    menu.add( start );
                    menu.add( details );
                    menu.add( export );
                    menu.add( close );
                    menu.add( charts );
                    menu.add( optionsCounter );

                    // Show the menu
                    menu.show( event.getComponent( ), event.getX( ), event.getY( ) );
                }
            }
        } );

        this.client = client;
        setLayout( null );
        setBounds( 0, 0, 900, 105 );
        setBorder( null );

        setBackground( Themes.BINANCE_GREY );

        // Races section
        races = new JPanel( null );
        races.setBounds( 0, 0, 100, getHeight( ) );
        races.setBackground( Themes.BINANCE_GREY );

        future_up = racesTextField( future_up, 5, 7 );
        future_up.setForeground( Themes.BINANCE_GREEN );
        races.add( future_up );
        future_down = racesTextField( future_down, 5, 37 );
        future_down.setForeground( Themes.BINANCE_RED );
        races.add( future_down );

        future_sum = racesTextField( future_sum, 5, 67 );
        races.add( future_sum );

        index_up = racesTextField( index_up, 53, 7 );
        index_up.setForeground( Themes.BINANCE_GREEN );
        races.add( index_up );
        index_down = racesTextField( index_down, 53, 37 );
        index_down.setForeground( Themes.BINANCE_RED );
        races.add( index_down );

        index_sum = racesTextField( index_sum, 53, 67 );
        races.add( index_sum );
        add( races );

        // Ticker section
        ticker = new JPanel( null );
        ticker.setBounds( 101, 0, 305, getHeight( ) );
        ticker.setBackground( Themes.BINANCE_GREY );

        open = tickerTextField( 5, 7 );
        ticker.add( open );
        open_present = tickerPresent( 5, 37 );
        ticker.add( open_present );
        index = tickerTextField( 80, 7 );
        ticker.add( index );
        index_present = tickerPresent( 80, 37 );
        ticker.add( index_present );
        low = tickerTextField( 155, 7 );
        ticker.add( low );
        low_present = tickerPresent( 155, 37 );
        ticker.add( low_present );
        high = tickerTextField( 230, 7 );
        ticker.add( high );
        high_present = tickerPresent( 230, 37 );
        ticker.add( high_present );
        future = tickerTextField( 5, 67 );
        ticker.add( future );

        optimipesimi = racesTextField( optimipesimi, 80, 67 );
        optimipesimi.setBounds( optimipesimi.getX( ), optimipesimi.getY( ), 70, optimipesimi.getHeight( ) );
        ticker.add( optimipesimi );

        JLabel label = new JLabel( );
        try {
            label.setText( client.getName( ).toUpperCase( ) );
        } catch ( Exception e ) {
            // TODO: handle exception
        }
        label.setFont( bold_font.deriveFont( ( float ) 10.0 ) );
        label.setBounds( 160, 70, 100, 25 );
        label.setForeground( darkYellow );

        futureOptionsCounterField = racesTextField( futureOptionsCounterField, 80, 67 );
        futureOptionsCounterField.setBounds( 230, 67, 60, futureOptionsCounterField.getHeight( ) );
        ticker.add( futureOptionsCounterField );


        ticker.add( label );

        add( ticker );

        // Options bid ask counter table section
        optionsPanel = new JPanel( null );
        optionsPanel.setBounds( 406, 0, 170, getHeight( ) );
        optionsPanel.setBackground( Themes.BINANCE_GREY );

        int strikesNum = ( int ) ( client.getEndStrike( ) - client.getStartStrike( ) );
        optionsTable = new TableMaker( new JPopupMenu( ) ).stocksTable( client, new Object[ strikesNum ][ 3 ],
                new String[] { "a", "b", "c" }, 19 );
        optionsScrollPane = new JScrollPane( optionsTable );
        optionsScrollPane.setBorder( null );
        optionsScrollPane.setViewportBorder( null );

        optionsScrollPane.setBounds( 0, 0, optionsPanel.getWidth( ), optionsPanel.getHeight( ) - 10 );
        optionsPanel.add( optionsScrollPane );

        add( optionsPanel );

        // Exp section
        exp = new JPanel( null );
        exp.setBounds( 700, 0, 100, getHeight( ) );
        exp.setBackground( Themes.BINANCE_GREY );

        exp_future = racesTextField( exp_future, 5, 7 );
        exp.add( exp_future );
        exp_index = racesTextField( exp_index, 52, 7 );
        exp.add( exp_index );
        exp_move = tickerPresent( 5, 37 );
        exp_move.setBounds( exp_move.getX( ), exp_move.getY( ), 89, exp_move.getHeight( ) );
        exp.add( exp_move );

        add( exp );

        // Log section
        logPanel = new JPanel( null );
        logPanel.setBounds( 704, 0, 300, 300 );
        logPanel.setBackground( Themes.BINANCE_GREY );

        logArea = new JTextArea( );
        logArea.setBounds( 0, 0, 300, 38 );
        logArea.setFont( new Font( "Ariel", Font.PLAIN, 14 ) );
        logPanel.add( logArea );

        add( logPanel );
    }

    @Override
    public void colorRaces( int runner1_up_down, int runner2_up_down, int competition_Number ) {

        if ( runner1_up_down == 1 ) {
            getFuture_up( ).setBackground( Themes.OPEN_RACE );
        }

        if ( runner1_up_down == 2 ) {
            getFuture_down( ).setBackground( Themes.OPEN_RACE );
        }

        if ( runner2_up_down == 1 ) {
            getIndex_up( ).setBackground( Themes.OPEN_RACE );
        }

        if ( runner2_up_down == 2 ) {
            getIndex_down( ).setBackground( Themes.OPEN_RACE );
        }

        // back to white
        if ( runner1_up_down == 0 ) {
            getFuture_up( ).setBackground( Themes.BINANCE_GREY );
            getFuture_down( ).setBackground( Themes.BINANCE_GREY );
        }

        if ( runner2_up_down == 0 ) {
            getIndex_up( ).setBackground( Themes.BINANCE_GREY );
            getIndex_down( ).setBackground( Themes.BINANCE_GREY );
        }

        if ( competition_Number == 0 ) {
            getFuture_up( ).setBackground( Themes.BINANCE_GREY );
            getFuture_down( ).setBackground( Themes.BINANCE_GREY );
            getIndex_up( ).setBackground( Themes.BINANCE_GREY );
            getIndex_down( ).setBackground( Themes.BINANCE_GREY );
        }

    }

    // JText filed in races type
    public JTextField racesTextField( JTextField field, int x, int y ) {
        field = new JTextField( );
        field.setBounds( x, y, 42, 25 );
        field.setFont( font );
        field.setHorizontalAlignment( JTextField.CENTER );
        field.setBackground( Themes.BINANCE_GREY );
        field.setBorder( null );
        return field;
    }

    // JText filed in races type
    public JTextField tickerTextField( int x, int y ) {
        JTextField field = new JTextField( );
        field.setBounds( x, y, 70, 25 );
        field.setFont( font );
        field.setHorizontalAlignment( JTextField.CENTER );
        field.setForeground( Color.WHITE );
        field.setBackground( Themes.BINANCE_GREY );
        field.setBorder( null );
        return field;
    }

    // JText filed in races type
    public JTextField tickerPresent( int x, int y ) {
        JTextField field = new JTextField( );
        field.setBounds( x, y, 70, 25 );
        field.setFont( bold_font );
        field.setForeground( Color.WHITE );
        field.setHorizontalAlignment( JTextField.CENTER );
        field.setBackground( Themes.BINANCE_GREY );
        field.setBorder( null );
        return field;
    }

    public double dbl( String s ) {
        return Double.parseDouble( s );
    }

    public String getUrl() {
        return url;
    }

    public JPanel getRaces() {
        return races;
    }

    public JTextField getFuture_sum() {
        return future_sum;
    }

    public void setFuture_sum( JTextField future_sum ) {
        this.future_sum = future_sum;
    }

    public JTextField getIndex_sum() {
        return index_sum;
    }

    public void setIndex_sum( JTextField index_sum ) {
        this.index_sum = index_sum;
    }

    public JPanel getTicker() {
        return ticker;
    }

    public JTextField getOpen_present() {
        return open_present;
    }

    public JTextField getIndex() {
        return index;
    }

    public JTextField getIndex_present() {
        return index_present;
    }

    public JTextField getLow_present() {
        return low_present;
    }

    public JTextField getHigh_present() {
        return high_present;
    }

    public JTextField getFuture() {
        return future;
    }

    public JPanel getExp() {
        return exp;
    }

    public JTextField getExp_future() {
        return exp_future;
    }

    public JTextField getExp_index() {
        return exp_index;
    }

    public JTextField getExp_move() {
        return exp_move;
    }

    public JTextField getOptimipesimi() {
        return optimipesimi;
    }

    public JPanel getLogPanel() {
        return logPanel;
    }

    public JTextArea getLogArea() {
        return logArea;
    }

    public Font getBold_font() {
        return bold_font;
    }

    public Font getFont() {
        return font;
    }

    public BASE_CLIENT_OBJECT getClient() {
        return client;
    }

    public JTextField getFuture_up() {
        return future_up;
    }

    public void setFuture_up( JTextField future_up ) {
        this.future_up = future_up;
    }

    public JTextField getFuture_down() {
        return future_down;
    }

    public void setFuture_down( JTextField future_down ) {
        this.future_down = future_down;
    }

    public JTextField getIndex_up() {
        return index_up;
    }

    public void setIndex_up( JTextField index_up ) {
        this.index_up = index_up;
    }

    public JTextField getIndex_down() {
        return index_down;
    }

    public void setIndex_down( JTextField index_down ) {
        this.index_down = index_down;
    }

    public JPanel getOptionsPanel() {
        return optionsPanel;
    }

    public void setOptionsPanel( JPanel optionsPanel ) {
        this.optionsPanel = optionsPanel;
    }

    public JScrollPane getOptionsScrollPane() {
        return optionsScrollPane;
    }

    public void setOptionsScrollPane( JScrollPane optionsScrollPane ) {
        this.optionsScrollPane = optionsScrollPane;
    }

    public JTable getOptionsTable() {
        return optionsTable;
    }

    public void setOptionsTable( JTable optionsTable ) {
        this.optionsTable = optionsTable;
    }

    public JTextField getFutureOptionsCounterField() {
        return futureOptionsCounterField;
    }

    public Updater getUpdater() {
        return updater;
    }

    public void setUpdater( Updater updater ) {
        this.updater = updater;
    }

    public class Updater extends MyThread implements Runnable {

        boolean run = true;
        double futureOption;
        double sinteticFuture;
        double futureBid = 0;
        double futureAsk = 0;
        // Ticker present
        double openPresent;
        double indexPresent;
        double highPresent;
        double lowPresent;
        // ---------- Races ---------- //
        int future_sum;
        int index_sum;
        double start_exp;
        int future_exp;
        int index_exp;
        double exp_move;
        int optimiTimer = 0;
        int pesimiTimer = 0;

        public Updater( BASE_CLIENT_OBJECT client ) {
            super( client );
            setName( "PanelLine" );
        }

        @Override
        public void initRunnable() {
            setRunnable( this );
        }

        @Override
        public void run() {

            while ( isRun( ) ) {
                try {

                    futureOption = floor( getClient( ).getOptionsHandler( ).getMainOptions( ).getContract( ).getVal() );

                    // ---------- Ticker ---------- //
                    getIndex( ).setText( str( getClient( ).getIndex( ) ) );
                    getFuture( ).setText( str( futureOption ) );

                    futureBid = 0;
                    futureAsk = 0;

                    if ( getClient( ) instanceof INDEX_CLIENT_OBJECT ) {
                        futureBid = getClient( ).getFutureBid( );
                        futureAsk = getClient( ).getFutureAsk( );
                    }

                    // Ticker present
                    openPresent = floor( ( ( getClient( ).getOpen( ) - getClient( ).getBase( ) ) / getClient( ).getBase( ) ) * 100 );
                    indexPresent = floor( ( ( getClient( ).getIndex( ) - getClient( ).getBase( ) ) / getClient( ).getBase( ) ) * 100 );
                    highPresent = floor( ( ( getClient( ).getHigh( ) - getClient( ).getBase( ) ) / getClient( ).getBase( ) ) * 100 );
                    lowPresent = floor( ( ( getClient( ).getLow( ) - getClient( ).getBase( ) ) / getClient( ).getBase( ) ) * 100 );

                    // ---------- Races ---------- //
                    future_sum = getClient( ).getConUp( ) - getClient( ).getConDown( );
                    index_sum = getClient( ).getIndexUp( ) - getClient( ).getIndexDown( );

                    // ---------- Exp ---------- //
                    start_exp = getClient( ).getStart_exp( );
                    future_exp = getClient( ).getFuture_exp( );
                    index_exp = getClient( ).getIndex_exp( );

                    exp_move = floor( ( ( getClient( ).getIndex( ) - start_exp ) / start_exp ) * 100 );

                    // ========== Update data ========== //
                    getClient( ).setLive_future_exp( future_exp + future_sum );
                    getClient( ).setLive_index_exp( index_exp + index_sum );

                    // Write data to panel
                    writeData( );

                    // Write data to table
                    updateOptionsTable( );

                    Thread.sleep( 1000 );
                } catch ( InterruptedException e ) {
                    close( );
                }
            }
        }

        private void updateOptionsTable() {

            // Write the data to table
            int row = 0;
            for ( Strike strike : getClient( ).getOptionsHandler( ).getOptionsMonth( ).getStrikes( ) ) {
                double strikePrice = strike.getStrike( );

                // Strike
                optionsTable.setValueAt( strikePrice, row, 1 );

                // Call
                int callCounter = strike.getCall( ).getBidAskCounter( );
                optionsTable.setValueAt( callCounter, row, 0 );

                // Put
                int putCounter = strike.getPut( ).getBidAskCounter( );
                optionsTable.setValueAt( putCounter, row, 2 );

                row++;
            }
        }

        // Write the data
        protected void writeData() {

            open.setText( str( getClient( ).getOpen( ) ) );
            high.setText( str( getClient( ).getHigh( ) ) );
            low.setText( str( getClient( ).getLow( ) ) );

            colorBackPresent( getOpen_present( ), openPresent );
            colorBackPresent( getIndex_present( ), indexPresent );
            colorBackPresent( getHigh_present( ), highPresent );
            colorBackPresent( getLow_present( ), lowPresent );

            future_up.setText( str( getClient( ).getConUp( ) ) );
            future_down.setText( str( getClient( ).getConDown( ) ) );
            index_up.setText( str( getClient( ).getIndexUp( ) ) );
            index_down.setText( str( getClient( ).getIndexDown( ) ) );

            colorForf( getOptimipesimi( ), floor( getClient( ).getOptionsHandler( ).getMainOptions( ).getOp( ) ) );

            colorForfInt( getFuture_sum( ), future_sum );
            colorForfInt( getIndex_sum( ), index_sum );

            colorForfInt( getExp_future( ), future_exp + future_sum );
            colorForfInt( getExp_index( ), index_exp + index_sum );
            colorBackPresent( getExp_move( ), exp_move );

            colorForfInt( getFutureOptionsCounterField( ), getClient( ).getOptionsHandler( ).getOptionsDay( ).getContractBidAskCounter( ) );
        }


        public boolean isRun() {
            return run;
        }

        public void setRun( boolean run ) {
            this.run = run;
        }

        public void close() {
            run = false;
        }

        public double dbl( String s ) {
            return Double.parseDouble( s );
        }

        // To string
        public String str( Object o ) {
            return String.valueOf( o );
        }

        // Floor
        public double floor( double d ) {
            return Math.floor( d * 100 ) / 100;
        }

        // Present
        public void colorBackPresent( JTextField field, double val ) {
            if ( val >= 0 ) {
                field.setText( str( val ) + "%" );
                field.setForeground( Themes.BINANCE_GREEN );
            } else {
                field.setText( str( val ) + "%" );
                field.setForeground( Themes.BINANCE_RED );
            }
        }

        // Present
        public void colorBack( JTextField field, double val ) {
            if ( val >= 0 ) {
                field.setText( str( val ) );
                field.setForeground( Themes.BINANCE_GREEN );
            } else {
                field.setText( str( val ) );
                field.setForeground( Themes.BINANCE_RED );
            }
        }

        public void colorForf( JTextField field, double val ) {

            if ( val > 0 ) {
                field.setText( str( val ) );
                field.setForeground( Themes.BINANCE_GREEN );
            } else {
                field.setText( str( val ) );
                field.setForeground( Themes.BINANCE_RED );
            }
        }

        // Present
        public void colorForfInt( JTextField field, double val ) {
            if ( val > 0 ) {
                field.setText( str( ( int ) val ) );
                field.setForeground( Themes.BINANCE_GREEN );
            } else {
                field.setText( str( ( int ) val ) );
                field.setForeground( Themes.BINANCE_RED );
            }
        }
    }

}
