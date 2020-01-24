package options.fullOptions;

import locals.L;
import locals.Themes;
import options.Option;
import options.Options;
import serverObjects.BASE_CLIENT_OBJECT;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;

public class FullOptionsWindow {

    public static JLabel indexLabel;
    public static JLabel indexPresentLabel;
    public JFrame frame;
    public JTable table;
    public String[] headers = { "Vega", "Delta", "Bid", "Theo", "Ask", "Strike", "IV", "Bid", "Theo", "Ask", "Delta", "Vega" };
    public Object[][] tableData;
    FullOptionsUpdater fullOptionsUpdater;
    BASE_CLIENT_OBJECT client;
    Options optionsFather;
    PositionCalculator positionCalculator;

    /**
     * Create the application.
     */
    public FullOptionsWindow( BASE_CLIENT_OBJECT client ) {
        this.client = client;
        this.optionsFather = client.getOptionsHandler( ).getOptionsDay( );
        initialize( );

        // Options data handler
        client.getOptionsDataHandler( ).setOptionsFather( optionsFather );
        client.getOptionsDataHandler( ).getRunner( ).getHandler( ).start( );

        // Full options window updater
        fullOptionsUpdater = new FullOptionsUpdater( client, optionsFather, table );
        fullOptionsUpdater.getHandler( ).start( );

        // Options position handler
        positionCalculator = new PositionCalculator( client );
    }


    public FullOptionsWindow() {
        initialize( );
    }

    /**
     * Launch the application.
     */
    public static void main( String[] args ) {
        EventQueue.invokeLater( new Runnable( ) {
            public void run() {
                try {

//					SpxCLIENTObject spx = SpxCLIENTObject.getInstance();
//
//					spx.getTwsData().getOptionMonthContract ().lastTradeDateOrContractMonth( "20200116" );
//
//					Manifest.CLIENT_ID = 678;
//
//					Downloader downloader = Downloader.getInstance();
//					downloader.start();

                    FullOptionsWindow window = new FullOptionsWindow( );
                    window.frame.setVisible( true );
                } catch ( Exception e ) {
                    e.printStackTrace( );
                }
            }
        } );
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame( );
        frame.addWindowListener( new WindowAdapter( ) {
            @Override
            public void windowClosing( WindowEvent arg0 ) {
                fullOptionsUpdater.getHandler( ).close( );
                client.getOptionsDataHandler( ).getRunner( ).getHandler( ).close( );
            }
        } );
        frame.setBounds( 100, 100, 700, 333 );
        frame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );

        JPanel panel = new JPanel( );
        panel.setBounds( 0, 26, frame.getWidth( ), frame.getHeight( ) );
        frame.getContentPane( ).add( panel );
        panel.setLayout( null );

        JPanel settingPanel = new JPanel( );
        settingPanel.setLayout( null );
        settingPanel.setBounds( 0, 0, frame.getWidth( ), 25 );
        settingPanel.setBackground( Themes.GREY_LIGHT );
        frame.getContentPane( ).add( settingPanel );

        // Right click open Popup menu
        settingPanel.addMouseListener( new MouseAdapter( ) {
            @Override
            public void mouseClicked( MouseEvent e ) {
                super.mouseClicked( e );
                if ( e.getModifiers( ) == MouseEvent.BUTTON3_MASK ) {
                    new AdvendedOptionsMenu( e );
                }
            }
        } );

        // Index
        indexLabel = new JLabel( "Index" );
        indexLabel.setForeground( Color.BLACK );
        indexLabel.setFont( Themes.VEDANA_12.deriveFont( Font.BOLD ) );
        indexLabel.setBounds( 451, 0, 99, settingPanel.getHeight( ) );

        settingPanel.add( indexLabel );

        // Index present
        indexPresentLabel = new JLabel( "Present" );
        indexPresentLabel.setFont( Themes.VEDANA_12.deriveFont( Font.BOLD ) );
        indexPresentLabel.setBounds( 526, 0, 99, settingPanel.getHeight( ) );

        settingPanel.add( indexPresentLabel );

        table = createTable( );

        JScrollPane scrollPane = new JScrollPane( table );
        scrollPane.setBounds( 0, 0, frame.getWidth( ), 300 );
        scrollPane.setBorder( null );
        panel.add( scrollPane );

    }

    private JTable createTable() {

        int rows = optionsFather.getStrikes( ).size( );

        // int rows = 10;
        tableData = new Object[ rows ][ 12 ];

        final int callVega = 0;
        final int callDelta = 1;
        final int callBid = 2;
        final int callTheo = 3;
        final int callAsk = 4;
        final int strikeCol = 5;
        final int iv = 6;
        final int putBid = 7;
        final int putThoe = 8;
        final int putAsk = 9;
        final int putDelta = 10;
        final int putVega = 11;

        JTable table = new JTable( tableData, headers ) {

            public Component prepareRenderer( TableCellRenderer renderer, int row, int col ) {

                // Current component
                Component c = super.prepareRenderer( renderer, row, col );

                // Current cell value
                String val = String.valueOf( getValueAt( row, col ) );

                if ( !val.equals( "" ) ) {

                    if ( getSelectedRow( ) != row ) {

                        if ( row % 2 == 0 ) {
                            c.setBackground( Themes.GREY_VERY_LIGHT );
                        } else {
                            c.setBackground( Themes.GREY_LIGHT );
                        }
                    }

                    // ----- Call ----- //
                    // Vega
                    if ( col == callVega ) {
                        c.setForeground( Color.BLACK );
                        // Delta
                    } else if ( col == callDelta ) {
                        c.setForeground( Color.BLACK );
                        // Bid
                    } else if ( col == callBid ) {
                        c.setForeground( Themes.GREEN );
                        // Theo
                    } else if ( col == callTheo ) {
                        c.setForeground( Color.BLACK );
                        c.setFont( c.getFont( ).deriveFont( Font.BOLD ) );
                        // Ask
                    } else if ( col == callAsk ) {
                        c.setForeground( Themes.RED );
                        // ----- Strike ----- //
                    } else if ( col == strikeCol ) {
                        c.setForeground( Color.WHITE );
                        c.setBackground( Themes.BLUE_STRIKE );
                        c.setFont( getFont( ) );
                        c.setPreferredSize( new Dimension( 203, c.getHeight( ) ) );
                        // IV
                    } else if ( col == iv ) {
                        c.setForeground( Color.BLACK );
                        c.setFont( c.getFont( ).deriveFont( Font.BOLD ) );
                        // Bid
                    } else if ( col == putBid ) {
                        c.setForeground( Themes.GREEN );
                        // Theo
                    } else if ( col == putThoe ) {
                        c.setForeground( Color.BLACK );
                        c.setFont( c.getFont( ).deriveFont( Font.BOLD ) );
                        // Ask
                    } else if ( col == putAsk ) {
                        c.setForeground( Themes.RED );
                        // Delta
                    } else if ( col == putDelta ) {
                        c.setForeground( Color.BLACK );
                        // Vega
                    } else if ( col == putVega ) {
                        c.setForeground( Color.BLACK );
                    }
                }
                return c;
            }
        };

        // Selection listener
        table.setCellSelectionEnabled( true );
        table.addMouseListener( new MouseAdapter( ) {
            @Override
            public void mouseClicked( MouseEvent e ) {

                int row = table.rowAtPoint( e.getPoint( ) );
                int col = table.columnAtPoint( e.getPoint( ) );

                oneClick( e, col, row );

                rightClick( e, col, row );

                threeClicks( e, col, row );

                doubleClick( e, col, row );

            }

            private void oneClick( MouseEvent e, int col, int row ) {

                if ( row >= 0 && row < table.getRowCount( ) ) {
                    table.setRowSelectionInterval( row, row );
                }
            }

            private void threeClicks( MouseEvent e, int col, int row ) {

                if ( e.getClickCount( ) == 3 ) {


                }

            }

            // Double click
            private void doubleClick( MouseEvent e, int col, int row ) {

            }

            // Right click
            private void rightClick( MouseEvent e, int col, int row ) {
                if ( e.getButton( ) == MouseEvent.BUTTON3 ) {
                    double strike = L.dbl( L.str( table.getValueAt( row, strikeCol ) ) );
                    int side;
                    // Call
                    if ( col < strikeCol ) {

                        side = Option.CALL;
                        Option option = getOptions( ).getOption( side, strike );

                        // Buy or Sell
                        if ( col == callBid ) {
                            new CreatePositionWindow( option.getName( ) + " Sell", option, positionCalculator, PositionCalculator.OptionPosition.SELL );
                        } else if ( col == callAsk ) {
                            new CreatePositionWindow( option.getName( ) + " Buy", option, positionCalculator, PositionCalculator.OptionPosition.BUY );
                        }

                    }
                }
            }

        } );


        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer( );
        renderer.setHorizontalAlignment( SwingConstants.CENTER );
        table.setDefaultRenderer( Object.class, renderer );
        table.setFillsViewportHeight( true );
        table.setRowHeight( 21 );
        table.setFont( Themes.VEDANA_12 );
        table.setShowGrid( false );
        table.setIntercellSpacing( new Dimension( 0, 0 ) );
        table.getColumnModel( ).getColumn( 5 ).setPreferredWidth( 109 );
        table.setRowSelectionAllowed( true );
        table.setColumnSelectionAllowed( false );
        table.setSelectionBackground( Themes.GREY );

        // Headers
        JTableHeader header = table.getTableHeader( );
        header.setPreferredSize( new Dimension( 40, 30 ) );
        header.setBackground( Themes.GREY_VERY_LIGHT );
        header.setForeground( Color.BLACK );
        header.setFont( Themes.VEDANA_12.deriveFont( Font.PLAIN ) );

        UIManager.getDefaults( ).put( "TableHeader.cellBorder", BorderFactory.createEmptyBorder( 0, 0, 0, 0 ) );


        // Table mouse

        return table;

    }

    public Options getOptions() {
        return optionsFather;
    }

    public void setOptionsFather( Options optionsFather ) {
        this.optionsFather = optionsFather;
        this.fullOptionsUpdater.setOptionsFather( optionsFather );
    }

    // Jmenu
    class AdvendedOptionsMenu extends JPopupMenu {

        public AdvendedOptionsMenu( MouseEvent event ) {

            // Options menu
            JMenu optionsMenu = new JMenu( "Options" );

            // Options month
            JMenuItem optionsMonth = new JMenuItem( "Month" );
            optionsMonth.addActionListener( new ActionListener( ) {
                @Override
                public void actionPerformed( ActionEvent actionEvent ) {
                    setOptionsFather( client.getOptionsHandler( ).getOptionsMonth( ) );
                    client.getOptionsDataHandler( ).setOptionsFather( client.getOptionsHandler( ).getOptionsMonth( ) );
                }
            } );

            // Options day
            JMenuItem optionsDay = new JMenuItem( "Day" );
            optionsDay.addActionListener( new ActionListener( ) {
                @Override
                public void actionPerformed( ActionEvent actionEvent ) {
                    setOptionsFather( client.getOptionsHandler( ).getOptionsDay( ) );
                    client.getOptionsDataHandler( ).setOptionsFather( client.getOptionsHandler( ).getOptionsDay( ) );
                }
            } );

            // Add all items
            // Options menu
            optionsMenu.add( optionsMonth );
            optionsMenu.add( optionsDay );

            // Add to main menu
            add( optionsMenu );

            // Show event
            show( event.getComponent( ), event.getX( ), event.getY( ) );
        }

    }


}

