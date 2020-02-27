package options.fullOptions;

import gui.MyGuiComps;
import locals.L;
import locals.Themes;
import options.*;
import serverObjects.BASE_CLIENT_OBJECT;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;

public class FullOptionsWindow extends MyGuiComps.MyFrame {

    public static MyGuiComps.MyLabel indexLabel;
    public static MyGuiComps.MyLabel indexPresentLabel;
    //    public JFrame frame;
    public JTable table;
    public String[] headers = { "Vega", "Delta", "Bid", "Theo", "Ask", "Strike", "IV", "Bid", "Theo", "Ask", "Delta", "Vega" };
    public Object[][] tableData;
    FullOptionsUpdater fullOptionsUpdater;
    BASE_CLIENT_OBJECT client;
    Options options;
    MyGuiComps.MyBoardPanel sumPanel;
    JScrollPane scrollPane;
    MyGuiComps.MyBoardPanel settingPanel;

    public static MyGuiComps.MyLabel pnlLbl;
    public static MyGuiComps.MyLabel deltaLbl;
    public static MyGuiComps.MyLabel vegaLbl;

    MyGuiComps.MyLabel pnlHeader;
    MyGuiComps.MyLabel deltaHeader;
    MyGuiComps.MyLabel vegaHeader;

    /**
     * Create the application.
     */
    public FullOptionsWindow( BASE_CLIENT_OBJECT client ) {
        super( "Options" );
        this.client = client;
        this.options = client.getOptionsHandler( ).getOptions( OptionsEnum.WEEK );
        initialize( );
        initListeners( );

        // Options data handler
        client.getOptionsDataHandler( ).setOptions( options );
        client.getOptionsDataHandler( ).getRunner( ).getHandler( ).start( );

        // Full options window updater
        fullOptionsUpdater = new FullOptionsUpdater( client, options, table );
        fullOptionsUpdater.getHandler( ).start( );
    }

    @Override
    public void onClose() {
        // This
        addWindowListener( new WindowAdapter( ) {
            @Override
            public void windowClosing( WindowEvent arg0 ) {
                fullOptionsUpdater.getHandler( ).close( );
                client.getOptionsDataHandler( ).getRunner( ).getHandler( ).close( );
            }
        } );
    }

    @Override
    public void initListeners() {

        // Setting panel
        settingPanel.addMouseListener( new MouseAdapter( ) {
            @Override
            public void mouseClicked( MouseEvent e ) {
                super.mouseClicked( e );
                if ( e.getModifiers( ) == MouseEvent.BUTTON3_MASK ) {
                    new AdvendedOptionsMenu( e );
                }
            }
        } );
    }

    /**
     * Initialize the contents of the frame.
     */
    @Override
    public void initialize() {

        Container mainContainer = rootPane.getContentPane( );
        mainContainer.setLayout( new BorderLayout( ) );

        // This
        setBounds( 100, 100, 700, 333 );
        setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );

        // Setting panel
        settingPanel = new MyGuiComps.MyBoardPanel( 1, 8, new Dimension( mainContainer.getWidth( ), 25 ), new Dimension( 40, 25 ) );
        mainContainer.add( settingPanel, BorderLayout.NORTH );

        pnlHeader = new MyGuiComps.MyLabel( "P/L" );
        settingPanel.setLabel( pnlHeader, 0, 1 );

        pnlLbl = new MyGuiComps.MyLabel( "" );
        pnlLbl.setForeground( new Color( 4, 4, 4 ) );
        pnlLbl.setFont( Themes.VEDANA_12.deriveFont( Font.BOLD ) );
        settingPanel.setLabel( pnlLbl, 0, 2 );

        // Index
        indexLabel = new MyGuiComps.MyLabel( "Index" );
        indexLabel.setFont( Themes.VEDANA_12.deriveFont( Font.BOLD ) );
        settingPanel.setLabel( indexLabel, 0, 6 );

        // Index present
        indexPresentLabel = new MyGuiComps.MyLabel( "Present" );
        indexPresentLabel.setFont( Themes.VEDANA_12.deriveFont( Font.BOLD ) );
        settingPanel.setLabel( indexPresentLabel, 0, 7 );

        // Table
        table = createTable( this );

        // Scroll pane
        scrollPane = new JScrollPane( table );
        scrollPane.getVerticalScrollBar( ).setPreferredSize( new Dimension( 0, 0 ) );
        mainContainer.add( scrollPane, BorderLayout.CENTER );

        // ---------- Sum panel ---------- //
        sumPanel = new MyGuiComps.MyBoardPanel( 1, 10, new Dimension( mainContainer.getWidth( ), 25 ), new Dimension( 40, 25 ) );
        mainContainer.add( sumPanel, BorderLayout.SOUTH );

        // Delta
        deltaHeader = new MyGuiComps.MyLabel( "Delta" );
        sumPanel.setLabel( deltaHeader, 0, 0 );

        deltaLbl = new MyGuiComps.MyLabel( "" );
        sumPanel.setLabel( deltaLbl, 0, 1 );

        // Vega
        vegaHeader = new MyGuiComps.MyLabel( "Vega" );
        sumPanel.setLabel( vegaHeader, 0, 2 );

        vegaLbl = new MyGuiComps.MyLabel( "" );
        sumPanel.setLabel( vegaLbl, 0, 3 );
        
    }

    private JTable createTable( MyGuiComps.MyFrame frame ) {

        int rows = options.getStrikes( ).size( );

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

                rightClick( e, col, row );
            }

            @Override
            public void mouseReleased( MouseEvent e ) {
                super.mouseReleased( e );

                int row = table.rowAtPoint( e.getPoint( ) );
                int col = table.columnAtPoint( e.getPoint( ) );

                oneClick( e, col, row );
            }

            private void oneClick( MouseEvent e, int col, int row ) {

                if ( !table.isRowSelected( row ) && row >= 0 && row < table.getRowCount( ) ) {
                    table.clearSelection( );
                } else {
                    table.setRowSelectionInterval( row, row );
                }
            }

            // Right click
            private void rightClick( MouseEvent e, int col, int row ) {
                if ( e.getButton( ) == MouseEvent.BUTTON3 ) {
                    double strike = L.dbl( L.str( table.getValueAt( row, strikeCol ) ) );
                    int side;
                    // Call
                    if ( col < strikeCol ) {

                        Call call = getOptions().getCall( strike );
                        // Buy or Sell
                        if ( col == callBid ) {
                            new CreatePositionWindow( call.getName( ) + " Sell", call, client.getOptionsHandler( ).getPositionCalculator( ), PositionCalculator.OptionPosition.SELL, frame );
                        } else if ( col == callAsk ) {
                            new CreatePositionWindow( call.getName( ) + " Buy", call, client.getOptionsHandler( ).getPositionCalculator( ), PositionCalculator.OptionPosition.BUY, frame );
                        }

                    }

                    // Put
                    if ( col > iv ) {

                        Put put = getOptions().getPut( strike );

                        // Buy or Sell
                        if ( col == putBid ) {
                            new CreatePositionWindow( put.getName( ) + " Sell", put, client.getOptionsHandler( ).getPositionCalculator( ), PositionCalculator.OptionPosition.SELL, frame );
                        } else if ( col == putAsk ) {
                            new CreatePositionWindow( put.getName( ) + " Buy", put, client.getOptionsHandler( ).getPositionCalculator( ), PositionCalculator.OptionPosition.BUY, frame );
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

        return table;

    }

    public Options getOptions() {
        return options;
    }

    public void setOptions( Options options ) {
        this.options = options;
        this.fullOptionsUpdater.setOptions( options );
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
                    setOptions( client.getOptionsHandler( ).getOptions( OptionsEnum.MONTH ) );
                    client.getOptionsDataHandler( ).setOptions( client.getOptionsHandler( ).getOptions( OptionsEnum.MONTH ) );
                }
            } );

            // Options day
            JMenuItem optionsDay = new JMenuItem( "Day" );
            optionsDay.addActionListener( new ActionListener( ) {
                @Override
                public void actionPerformed( ActionEvent actionEvent ) {
                    setOptions( client.getOptionsHandler( ).getOptions( OptionsEnum.WEEK ) );
                    client.getOptionsDataHandler( ).setOptions( client.getOptionsHandler( ).getOptions( OptionsEnum.WEEK ) );
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

