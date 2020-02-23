package backBest;

import locals.Themes;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BazkTestDailyWindow {

    public static double base = 0;
    static BazkTestDailyWindow window;
    int preSleep;
    private JFrame frame;
    private JTextField future_up;
    private JTextField index_up;
    private JTextField future_down;
    private JTextField index_down;
    private JTextField future_sum;
    private JTextField index_sum;
    private JTextField open;
    private JTextField index;
    private JTextField low;
    private JTextField high;
    private JTextField open_present;
    private JTextField index_present;
    private JTextField low_present;
    private JTextField high_present;
    private JTextField future;
    private JTextField sourceFile;
    private JTextField sleep;
    private JTextField time;
    private JCheckBox exportCheckBox;
    private JScrollPane scrollPane;
    private JTextArea log;
    private JTextField dailyPnl;
    private JTextField longSum;
    private JLabel lblLong;
    private JLabel lblShort;
    private JTextField shortSum;
    private JTextField realCounterPnlField;
    private JTextArea errorLogArea;
    private JTextField totalPnl;
    private JTextField futureLiveExpField;
    private JTextField indexLiveExpField;
    private JLabel lblExp;
    private JTextField baseField;
    private JLabel lblBase;
    private JTextField opField;
    private JTextField opAvgField;
    private JTextField racesRatioField;
    private JLabel lblRatio;
    private JTextField expMove;
    private JTable table;

    /**
     * Create the application.
     */
    public BazkTestDailyWindow() {
        initialize( );
    }

    /**
     * Launch the application.
     */
    public static void main( String[] args ) {
        EventQueue.invokeLater( new Runnable( ) {
            public void run() {
                try {
                    window = new BazkTestDailyWindow( );
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
        frame.getContentPane( ).setBackground( SystemColor.inactiveCaption );
        frame.setBounds( 100, 100, 909, 640 );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.getContentPane( ).setLayout( null );

        future_up = new JTextField( );
        future_up.setForeground( new Color( 0, 100, 0 ) );
        future_up.setBounds( 10, 11, 63, 26 );
        future_up.setHorizontalAlignment( SwingConstants.CENTER );
        future_up.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
        frame.getContentPane( ).add( future_up );
        future_up.setColumns( 10 );

        index_up = new JTextField( );
        index_up.setForeground( new Color( 0, 100, 0 ) );
        index_up.setBounds( 83, 11, 63, 26 );
        index_up.setHorizontalAlignment( SwingConstants.CENTER );
        index_up.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
        index_up.setColumns( 10 );
        frame.getContentPane( ).add( index_up );

        future_down = new JTextField( );
        future_down.setForeground( new Color( 220, 20, 60 ) );
        future_down.setBounds( 10, 48, 63, 26 );
        future_down.setHorizontalAlignment( SwingConstants.CENTER );
        future_down.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
        future_down.setColumns( 10 );
        frame.getContentPane( ).add( future_down );

        index_down = new JTextField( );
        index_down.setForeground( new Color( 220, 20, 60 ) );
        index_down.setBounds( 83, 48, 63, 26 );
        index_down.setHorizontalAlignment( SwingConstants.CENTER );
        index_down.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
        index_down.setColumns( 10 );
        frame.getContentPane( ).add( index_down );

        future_sum = new JTextField( );
        future_sum.setBounds( 10, 93, 63, 26 );
        future_sum.setHorizontalAlignment( SwingConstants.CENTER );
        future_sum.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
        future_sum.setColumns( 10 );
        frame.getContentPane( ).add( future_sum );

        index_sum = new JTextField( );
        index_sum.setBounds( 83, 93, 63, 26 );
        index_sum.setHorizontalAlignment( SwingConstants.CENTER );
        index_sum.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
        index_sum.setColumns( 10 );
        frame.getContentPane( ).add( index_sum );

        open = new JTextField( );
        open.setBounds( 221, 11, 63, 26 );
        open.setHorizontalAlignment( SwingConstants.CENTER );
        open.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
        open.setColumns( 10 );
        frame.getContentPane( ).add( open );

        index = new JTextField( );
        index.setBounds( 303, 11, 63, 26 );
        index.setHorizontalAlignment( SwingConstants.CENTER );
        index.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
        index.setColumns( 10 );
        frame.getContentPane( ).add( index );

        low = new JTextField( );
        low.setBounds( 381, 11, 63, 26 );
        low.setHorizontalAlignment( SwingConstants.CENTER );
        low.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
        low.setColumns( 10 );
        frame.getContentPane( ).add( low );

        high = new JTextField( );
        high.setBounds( 456, 11, 63, 26 );
        high.setHorizontalAlignment( SwingConstants.CENTER );
        high.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
        high.setColumns( 10 );
        frame.getContentPane( ).add( high );

        open_present = new JTextField( );
        open_present.setForeground( new Color( 255, 255, 255 ) );
        open_present.setBounds( 221, 48, 63, 26 );
        open_present.setHorizontalAlignment( SwingConstants.CENTER );
        open_present.setFont( new Font( "Arial", Font.BOLD, 15 ) );
        open_present.setColumns( 10 );
        frame.getContentPane( ).add( open_present );

        index_present = new JTextField( );
        index_present.setForeground( new Color( 255, 255, 255 ) );
        index_present.setBounds( 303, 48, 63, 26 );
        index_present.setHorizontalAlignment( SwingConstants.CENTER );
        index_present.setFont( new Font( "Arial", Font.BOLD, 15 ) );
        index_present.setColumns( 10 );
        frame.getContentPane( ).add( index_present );

        low_present = new JTextField( );
        low_present.setForeground( new Color( 255, 255, 255 ) );
        low_present.setBounds( 381, 48, 63, 26 );
        low_present.setHorizontalAlignment( SwingConstants.CENTER );
        low_present.setFont( new Font( "Arial", Font.BOLD, 15 ) );
        low_present.setColumns( 10 );
        frame.getContentPane( ).add( low_present );

        high_present = new JTextField( );
        high_present.setForeground( new Color( 255, 255, 255 ) );
        high_present.setBounds( 456, 48, 63, 26 );
        high_present.setHorizontalAlignment( SwingConstants.CENTER );
        high_present.setFont( new Font( "Arial", Font.BOLD, 15 ) );
        high_present.setColumns( 10 );
        frame.getContentPane( ).add( high_present );

        future = new JTextField( );
        future.setBounds( 303, 93, 63, 26 );
        future.setHorizontalAlignment( SwingConstants.CENTER );
        future.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
        future.setColumns( 10 );
        frame.getContentPane( ).add( future );

        JButton start = new JButton( "Start" );
        start.setBounds( 595, 167, 102, 27 );
        start.addActionListener( new ActionListener( ) {
            public void actionPerformed( ActionEvent e ) {
                getLog( ).setText( null );
            }
        } );
        start.setFont( new Font( "Arial", Font.BOLD, 15 ) );
        frame.getContentPane( ).add( start );

        JButton stop = new JButton( "Stop" );
        stop.setBounds( 595, 203, 102, 27 );

        stop.addActionListener( new ActionListener( ) {
            public void actionPerformed( ActionEvent arg0 ) {
                try {
                    preSleep = Integer.parseInt( sleep.getText( ) );
                } catch ( NullPointerException e ) {
                    preSleep = 0;
                }
                sleep.setText( "1000000" );
            }
        } );
        stop.setFont( new Font( "Arial", Font.BOLD, 15 ) );
        frame.getContentPane( ).add( stop );

        JButton btnContinue = new JButton( "Continue" );
        btnContinue.setBounds( 595, 241, 102, 27 );
        btnContinue.addActionListener( new ActionListener( ) {
            public void actionPerformed( ActionEvent e ) {
                sleep.setText( String.valueOf( preSleep ) );
            }
        } );
        btnContinue.setFont( new Font( "Arial", Font.BOLD, 15 ) );
        frame.getContentPane( ).add( btnContinue );

        sourceFile = new JTextField( );
        sourceFile.setBounds( 282, 280, 272, 26 );
        sourceFile.setText( "2019-10-21" );
        sourceFile.setHorizontalAlignment( SwingConstants.CENTER );
        frame.getContentPane( ).add( sourceFile );
        sourceFile.setColumns( 10 );

        sleep = new JTextField( );
        sleep.setText( "0" );
        sleep.setBounds( 564, 279, 133, 26 );
        sleep.setHorizontalAlignment( SwingConstants.CENTER );
        sleep.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
        sleep.setColumns( 10 );
        frame.getContentPane( ).add( sleep );

        time = new JTextField( );
        time.setBounds( 10, 130, 102, 26 );
        time.setHorizontalAlignment( SwingConstants.CENTER );
        time.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
        time.setColumns( 10 );
        frame.getContentPane( ).add( time );


        log = new JTextArea( );
        log.setBounds( 10, 264, 674, 80 );

        scrollPane = new JScrollPane( log );
        scrollPane.setBounds( 10, 318, 687, 130 );
        frame.getContentPane( ).add( scrollPane );

        JButton btnStartulti = new JButton( "Start multi" );
        btnStartulti.setBounds( 465, 167, 120, 27 );
        btnStartulti.addActionListener( new ActionListener( ) {
            public void actionPerformed( ActionEvent arg0 ) {
            }
        } );
        btnStartulti.setFont( new Font( "Arial", Font.BOLD, 14 ) );
        frame.getContentPane( ).add( btnStartulti );

        JButton btnStopmulti = new JButton( "Stop multi" );
        btnStopmulti.setBounds( 465, 203, 120, 27 );
        btnStopmulti.addActionListener( new ActionListener( ) {
            public void actionPerformed( ActionEvent e ) {
                try {
                } catch ( Exception exception ) {
                    errorLogArea.append( exception.getMessage( ) + "\n" + exception.getCause( ) );
                }
            }
        } );
        btnStopmulti.setFont( new Font( "Arial", Font.BOLD, 14 ) );
        frame.getContentPane( ).add( btnStopmulti );

        JScrollPane scrollPane_1 = new JScrollPane( );
        scrollPane_1.setBounds( 10, 459, 687, 134 );
        frame.getContentPane( ).add( scrollPane_1 );

        errorLogArea = new JTextArea( );
        scrollPane_1.setViewportView( errorLogArea );

        futureLiveExpField = new JTextField( );
        futureLiveExpField.setHorizontalAlignment( SwingConstants.CENTER );
        futureLiveExpField.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
        futureLiveExpField.setColumns( 10 );
        futureLiveExpField.setBounds( 561, 48, 63, 26 );
        frame.getContentPane( ).add( futureLiveExpField );

        indexLiveExpField = new JTextField( );
        indexLiveExpField.setHorizontalAlignment( SwingConstants.CENTER );
        indexLiveExpField.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
        indexLiveExpField.setColumns( 10 );
        indexLiveExpField.setBounds( 634, 48, 63, 26 );
        frame.getContentPane( ).add( indexLiveExpField );

        lblExp = new JLabel( "Exp" );
        lblExp.setHorizontalAlignment( SwingConstants.CENTER );
        lblExp.setFont( new Font( "Tahoma", Font.PLAIN, 12 ) );
        lblExp.setBounds( 595, 29, 68, 14 );
        frame.getContentPane( ).add( lblExp );

        JPanel panel = new JPanel( );
        panel.setBackground( SystemColor.inactiveCaption );
        panel.setBounds( 10, 167, 262, 140 );
        frame.getContentPane( ).add( panel );
        panel.setLayout( null );

        lblLong = new JLabel( "Long" );
        lblLong.setBounds( 15, 90, 46, 14 );
        panel.add( lblLong );

        longSum = new JTextField( );
        longSum.setBounds( 0, 114, 63, 26 );
        panel.add( longSum );
        longSum.setHorizontalAlignment( SwingConstants.CENTER );
        longSum.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
        longSum.setColumns( 10 );

        shortSum = new JTextField( );
        shortSum.setBounds( 73, 114, 63, 26 );
        panel.add( shortSum );
        shortSum.setHorizontalAlignment( SwingConstants.CENTER );
        shortSum.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
        shortSum.setColumns( 10 );

        lblShort = new JLabel( "Short" );
        lblShort.setBounds( 88, 90, 46, 14 );
        panel.add( lblShort );

        totalPnl = new JTextField( );
        totalPnl.setBounds( 150, 114, 102, 26 );
        panel.add( totalPnl );
        totalPnl.setHorizontalAlignment( SwingConstants.CENTER );
        totalPnl.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
        totalPnl.setColumns( 10 );

        JLabel lblNewLabel = new JLabel( "Total p&l" );
        lblNewLabel.setBounds( 177, 89, 49, 14 );
        panel.add( lblNewLabel );
        lblNewLabel.setFont( new Font( "Tahoma", Font.PLAIN, 12 ) );

        JLabel lblDailyPl = new JLabel( "Daily p&l" );
        lblDailyPl.setBounds( 177, -3, 49, 14 );
        panel.add( lblDailyPl );
        lblDailyPl.setFont( new Font( "Tahoma", Font.PLAIN, 12 ) );

        dailyPnl = new JTextField( );
        dailyPnl.setBounds( 150, 16, 102, 26 );
        panel.add( dailyPnl );
        dailyPnl.setHorizontalAlignment( SwingConstants.CENTER );
        dailyPnl.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
        dailyPnl.setColumns( 10 );

        realCounterPnlField = new JTextField( );
        realCounterPnlField.setBounds( 150, 52, 102, 26 );
        panel.add( realCounterPnlField );
        realCounterPnlField.setHorizontalAlignment( SwingConstants.CENTER );
        realCounterPnlField.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
        realCounterPnlField.setColumns( 10 );

        exportCheckBox = new JCheckBox( "Export file " );
        exportCheckBox.setBounds( 465, 244, 120, 23 );
        frame.getContentPane( ).add( exportCheckBox );

        baseField = new JTextField( );
        baseField.setText( "0" );
        baseField.setHorizontalAlignment( SwingConstants.CENTER );
        baseField.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
        baseField.setColumns( 10 );
        baseField.setBounds( 381, 93, 63, 26 );
        frame.getContentPane( ).add( baseField );

        lblBase = new JLabel( "Base" );
        lblBase.setHorizontalAlignment( SwingConstants.CENTER );
        lblBase.setFont( new Font( "Tahoma", Font.PLAIN, 12 ) );
        lblBase.setBounds( 381, 74, 68, 14 );
        frame.getContentPane( ).add( lblBase );

        opField = new JTextField( );
        opField.setHorizontalAlignment( SwingConstants.CENTER );
        opField.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
        opField.setColumns( 10 );
        opField.setBounds( 221, 93, 63, 26 );
        frame.getContentPane( ).add( opField );

        opAvgField = new JTextField( );
        opAvgField.setHorizontalAlignment( SwingConstants.CENTER );
        opAvgField.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
        opAvgField.setColumns( 10 );
        opAvgField.setBounds( 456, 93, 63, 26 );
        frame.getContentPane( ).add( opAvgField );

        racesRatioField = new JTextField( );
        racesRatioField.setHorizontalAlignment( SwingConstants.CENTER );
        racesRatioField.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
        racesRatioField.setColumns( 10 );
        racesRatioField.setBounds( 150, 93, 63, 26 );
        frame.getContentPane( ).add( racesRatioField );

        lblRatio = new JLabel( "Ratio" );
        lblRatio.setHorizontalAlignment( SwingConstants.CENTER );
        lblRatio.setFont( new Font( "Tahoma", Font.PLAIN, 12 ) );
        lblRatio.setBounds( 145, 75, 68, 14 );
        frame.getContentPane( ).add( lblRatio );

        expMove = new JTextField( );
        expMove.setHorizontalAlignment( SwingConstants.CENTER );
        expMove.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
        expMove.setColumns( 10 );
        expMove.setBounds( 564, 93, 134, 26 );
        frame.getContentPane( ).add( expMove );

        // Options table
        table = myTable( new Object[ 20 ][ 3 ], new String[] { "Call", "Strike", "Put" } );
        table.setBounds( 0, 0, 150, 300 );

        JScrollPane scrollPane = new JScrollPane( table );
        scrollPane.setBounds( 720, 10, 150, 300 );
        frame.getContentPane( ).add( scrollPane );

    }


    private JTable myTable( Object[][] rowsData, String[] cols ) {
        Color darkBlue = new Color( 0, 51, 102 );

        Font boldFont = new Font( "Arial", Font.BOLD, 14 );

        // Table
        JTable table = new JTable( rowsData, cols ) {
            public Component prepareRenderer( TableCellRenderer renderer, int row, int col ) {

                Component c = super.prepareRenderer( renderer, row, col );

                String strike = String.valueOf( getValueAt( row, 1 ) );

                int cell_val = 0;
                try {
                    if ( !getValueAt( row, col ).equals( "" ) ) {
                        try {
                            cell_val = ( int ) getValueAt( row, col );
                        } catch ( ClassCastException e ) {
                            cell_val = Integer.parseInt( ( String ) getValueAt( row, col ) );
                        }
                    }
                } catch ( Exception e ) {
                    // TODO: handle exception
                }

                try {

                    // Call
                    if ( col == 0 ) {
                        // Color forf
                        if ( cell_val > 0 ) {
                            c.setForeground( Themes.GREEN );
                        } else {
                            c.setForeground( Themes.RED );
                        }

                    } else

                        // Put
                        if ( col == 2 ) {

                            // Color forf
                            if ( cell_val > 0 ) {
                                c.setForeground( Themes.GREEN );
                            } else {
                                c.setForeground( Themes.RED );
                            }

                        } else

                            // Strike
                            if ( col == 1 ) {
                                c.setFont( c.getFont( ).deriveFont( Font.BOLD ) );
                                c.setForeground( Color.BLACK );
                                c.setBackground( Color.WHITE );
                            }

                } catch ( Exception e ) {
                    e.printStackTrace( );
                }

                return c;
            }
        };

        table.setBounds( 0, 0, 352, 120 );

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer( );
        renderer.setHorizontalAlignment( SwingConstants.CENTER );
        table.setDefaultRenderer( Object.class, renderer );
        table.setFillsViewportHeight( true );
        table.setRowHeight( 20 );
        table.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
        table.setShowGrid( true );

        // Header
        JTableHeader header = table.getTableHeader( );
        header.setDefaultRenderer( renderer );
        header.setBackground( darkBlue );

        return table;
    }


    public JTextField getDailyPnl() {
        return dailyPnl;
    }

    public void setDailyPnl( JTextField dailyPnl ) {
        this.dailyPnl = dailyPnl;
    }

    public JTextField getTotalPnl() {
        return totalPnl;
    }

    public void setTotalPnl( JTextField totalPnl ) {
        this.totalPnl = totalPnl;
    }

    public JTextArea getErrorLogArea() {
        return errorLogArea;
    }

    public void setErrorLogArea( JTextArea errorLogArea ) {
        this.errorLogArea = errorLogArea;
    }

    public JTextField getRealCounterPnlField() {
        return realCounterPnlField;
    }

    public void setRealCounterPnlField( JTextField realCounterPnlField ) {
        this.realCounterPnlField = realCounterPnlField;
    }

    public JTextField getFutureLiveExpField() {
        return futureLiveExpField;
    }

    public void setFutureLiveExpField( JTextField futureLiveExpField ) {
        this.futureLiveExpField = futureLiveExpField;
    }

    public JTextField getIndexLiveExpField() {
        return indexLiveExpField;
    }

    public void setIndexLiveExpField( JTextField indexLiveExpField ) {
        this.indexLiveExpField = indexLiveExpField;
    }

    public JTextField getPnl() {
        return dailyPnl;
    }

    public void setPnl( JTextField pnl ) {
        this.dailyPnl = pnl;
    }

    public JTextArea getLog() {
        return log;
    }

    public void setLog( JTextArea log ) {
        this.log = log;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame( JFrame frame ) {
        this.frame = frame;
    }

    public JTextField getFuture_up() {
        return future_up;
    }

    public void setFuture_up( JTextField future_up ) {
        this.future_up = future_up;
    }

    public JTextField getIndex_up() {
        return index_up;
    }

    public void setIndex_up( JTextField index_up ) {
        this.index_up = index_up;
    }

    public JTextField getFuture_down() {
        return future_down;
    }

    public void setFuture_down( JTextField future_down ) {
        this.future_down = future_down;
    }

    public JTextField getIndex_down() {
        return index_down;
    }

    public void setIndex_down( JTextField index_down ) {
        this.index_down = index_down;
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

    public JTextField getOpen() {
        return open;
    }

    public void setOpen( JTextField open ) {
        this.open = open;
    }

    public JTextField getIndex() {
        return index;
    }

    public void setIndex( JTextField index ) {
        this.index = index;
    }

    public JTextField getLow() {
        return low;
    }

    public void setLow( JTextField low ) {
        this.low = low;
    }

    public JTextField getHigh() {
        return high;
    }

    public void setHigh( JTextField high ) {
        this.high = high;
    }

    public JTextField getOpen_present() {
        return open_present;
    }

    public void setOpen_present( JTextField open_present ) {
        this.open_present = open_present;
    }

    public JTextField getIndex_present() {
        return index_present;
    }

    public void setIndex_present( JTextField index_present ) {
        this.index_present = index_present;
    }

    public JTextField getLow_present() {
        return low_present;
    }

    public void setLow_present( JTextField low_present ) {
        this.low_present = low_present;
    }

    public JTextField getHigh_present() {
        return high_present;
    }

    public void setHigh_present( JTextField high_present ) {
        this.high_present = high_present;
    }

    public JTextField getFuture() {
        return future;
    }

    public void setFuture( JTextField future ) {
        this.future = future;
    }

    public JTextField getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile( JTextField sourceFile ) {
        this.sourceFile = sourceFile;
    }

    public JTextField getSleep() {
        return sleep;
    }

    public void setSleep( JTextField sleep ) {
        this.sleep = sleep;
    }

    public JTextField getTime() {
        return time;
    }

    public void setTime( JTextField time ) {
        this.time = time;
    }

    public JTextField getLongSum() {
        return longSum;
    }

    public void setLongSum( JTextField longSum ) {
        this.longSum = longSum;
    }

    public JTextField getShortSum() {
        return shortSum;
    }

    public void setShortSum( JTextField shortSum ) {
        this.shortSum = shortSum;
    }

    public JTextArea getTextArea() {
        return errorLogArea;
    }

    public void setTextArea( JTextArea textArea ) {
        this.errorLogArea = textArea;
    }

    public JCheckBox getExportCheckBox() {
        return exportCheckBox;
    }

    public void setExportCheckBox( JCheckBox exportCheckBox ) {
        this.exportCheckBox = exportCheckBox;
    }

    public JTextField getBaseField() {
        return baseField;
    }

    public void setBaseField( JTextField baseField ) {
        this.baseField = baseField;
    }

    public JTextField getOpField() {
        return opField;
    }

    public void setOpField( JTextField opField ) {
        this.opField = opField;
    }

    public JTextField getOpAvgField() {
        return opAvgField;
    }

    public void setOpAvgField( JTextField opAvgField ) {
        this.opAvgField = opAvgField;
    }

    public JTextField getRacesRatioField() {
        return racesRatioField;
    }

    public void setRacesRatioField( JTextField racesRatioField ) {
        this.racesRatioField = racesRatioField;
    }

    public JTextField getExpMove() {
        return expMove;
    }

    public void setExpMove( JTextField expMove ) {
        this.expMove = expMove;
    }

    public JTable getTable() {
        return table;
    }

    public void setTable( JTable table ) {
        this.table = table;
    }

}
