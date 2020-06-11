package options;

import exp.ExpEnum;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class OptionsWindow {

    public JFrame frame;
    public JTextField contractCounterField;
    public JTextField opAvgField;
    public JTextField opAvg15Field;
    public JTextField move15Field;
    Color lightGreen = new Color( 12, 135, 0 );
    Color lightRed = new Color( 229, 19, 0 );
    OptionsWindowUpdater optionsWindowUpdater;
    BASE_CLIENT_OBJECT client;
    Options optionsFather;
    private JTable table;

    /**
     * Create the application.
     */
    public OptionsWindow( BASE_CLIENT_OBJECT client, Options optionsFather ) {
        this.client = client;
        this.optionsFather = optionsFather;
        initialize( );
    }

    /**
     * Launch the application.
     */

    public void startWindowUpdater() {
        optionsWindowUpdater = new OptionsWindowUpdater( );
        optionsWindowUpdater.start( );
    }

    public void closeWindowUpdater() {
        optionsWindowUpdater.close( );
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame( );
        frame.getContentPane( ).setBackground( SystemColor.inactiveCaption );
        frame.addWindowListener( new WindowAdapter( ) {
            @Override
            public void windowClosed( WindowEvent arg0 ) {
                optionsWindowUpdater.close( );
            }
        } );
        frame.setBounds( 100, 100, 300, 350 );
        frame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        frame.getContentPane( ).setLayout( null );


        String[] header = { "Call", "Strike", "Put" };

        int striksNum = client.getExps( ).getExp( ExpEnum.MONTH ).getOptions().getStrikes( ).size( );

        Object[][] data = new Object[ striksNum ][ 3 ];

        table = myTable( data, header );

        JScrollPane scrollPane = new JScrollPane( table );
        scrollPane.setBounds( 5, 11, 275, 203 );
        frame.getContentPane( ).add( scrollPane );

        contractCounterField = new JTextField( );
        contractCounterField.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
        contractCounterField.setHorizontalAlignment( SwingConstants.CENTER );
        contractCounterField.setBounds( 5, 245, 86, 25 );
        frame.getContentPane( ).add( contractCounterField );
        contractCounterField.setColumns( 10 );

        JLabel lblNewLabel = new JLabel( "Contract counter" );
        lblNewLabel.setHorizontalAlignment( SwingConstants.CENTER );
        lblNewLabel.setFont( new Font( "Dubai Medium", Font.PLAIN, 12 ) );
        lblNewLabel.setBounds( 5, 225, 86, 14 );
        frame.getContentPane( ).add( lblNewLabel );

        opAvgField = new JTextField( );
        opAvgField.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
        opAvgField.setHorizontalAlignment( SwingConstants.CENTER );
        opAvgField.setColumns( 10 );
        opAvgField.setBounds( 101, 245, 86, 25 );
        frame.getContentPane( ).add( opAvgField );

        JLabel lblOpAvg = new JLabel( "Op avg" );
        lblOpAvg.setHorizontalAlignment( SwingConstants.CENTER );
        lblOpAvg.setFont( new Font( "Dubai Medium", Font.PLAIN, 12 ) );
        lblOpAvg.setBounds( 101, 225, 86, 14 );
        frame.getContentPane( ).add( lblOpAvg );

        opAvg15Field = new JTextField( );
        opAvg15Field.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
        opAvg15Field.setHorizontalAlignment( SwingConstants.CENTER );
        opAvg15Field.setColumns( 10 );
        opAvg15Field.setBounds( 194, 245, 86, 25 );
        frame.getContentPane( ).add( opAvg15Field );

        move15Field = new JTextField( );
        move15Field.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
        move15Field.setHorizontalAlignment( SwingConstants.CENTER );
        move15Field.setColumns( 10 );
        move15Field.setBounds( 194, 280, 86, 25 );
        frame.getContentPane( ).add( move15Field );

        JLabel lblOpAvg_1 = new JLabel( "Op avg 15" );
        lblOpAvg_1.setHorizontalAlignment( SwingConstants.CENTER );
        lblOpAvg_1.setFont( new Font( "Dubai Medium", Font.PLAIN, 12 ) );
        lblOpAvg_1.setBounds( 194, 225, 86, 14 );
        frame.getContentPane( ).add( lblOpAvg_1 );
    }

    private JTable myTable( Object[][] rowsData, String[] cols ) {
        Color darkBlue = new Color( 0, 51, 102 );

        Font boldFont = new Font( "Arial", Font.BOLD, 14 );

        // Table
        JTable table = new JTable( rowsData, cols ) {
            public Component prepareRenderer( TableCellRenderer renderer, int row, int col ) {

                // Current component
                Component c = super.prepareRenderer( renderer, row, col );

                // Current cell value
                String cellVal = String.valueOf( getValueAt( row, col ) );

                if ( !cellVal.equals( "" ) ) {

                    try {

                        // Call
                        if ( col == 0 ) {

                            int val = toInt( cellVal );

                            // Color forf
                            if ( val > 0 ) {
                                c.setForeground( lightGreen );
                            } else {
                                c.setForeground( lightRed );
                            }

                        } else

                            // Put
                            if ( col == 2 ) {

                                int val = toInt( cellVal );

                                // Color forf
                                if ( val > 0 ) {
                                    c.setForeground( lightGreen );
                                } else {
                                    c.setForeground( lightRed );
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
                }

                return c;
            }
        };
        table.addMouseListener( new MouseAdapter( ) {
            @Override
            public void mouseClicked( MouseEvent event ) {

                if ( event.getModifiers( ) == MouseEvent.BUTTON3_MASK ) {
                    // Main menu
                    JPopupMenu menu = new JPopupMenu( );

                    JMenuItem chart = new JMenuItem( "Chart" );
                    chart.addActionListener( new ActionListener( ) {
                        @Override
                        public void actionPerformed( ActionEvent e ) {
                            try {

                                // Do something here

                            } catch ( Exception exception ) {
                                exception.printStackTrace( );
                            }
                        }
                    } );

                    menu.add( chart );
                    // Show the menu
                    menu.show( event.getComponent( ), event.getX( ), event.getY( ) );
                }
            }
        } );

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

    public double dbl( String s ) {
        return Double.parseDouble( s );
    }

    public int toInt( String s ) {
        return Integer.parseInt( s );
    }

    private class OptionsWindowUpdater extends Thread {

        boolean run = true;

        int minsInSecondes = 900;
        ArrayList<Double> indexList = client.getIndexList().getValues();
        
        @Override
        public void run() {

            while ( run ) {
                try {

                    // Sleep
                    sleep( 1000 );

                    int row = 0;

                    double start15Move;

                    if ( indexList.size( ) > minsInSecondes ) {
                        start15Move = indexList.get( indexList.size( ) - minsInSecondes );
                    } else {
                        start15Move = indexList.get( 0 );
                    }

                    double move15Min = floor( ( ( client.getIndex( ) - start15Move ) / start15Move ) * 100 );

                    for ( Strike strike : optionsFather.getStrikes( ) ) {

                        double strikePrice = strike.getStrike( );

                        // Call
                        table.setValueAt( strike.getCall( ).getBidAskCounter( ), row, 0 );

                        // Set strike
                        table.setValueAt( ( int ) strikePrice, row, 1 );

                        // Put
                        table.setValueAt( strike.getPut( ).getBidAskCounter( ), row, 2 );
                        row++;
                    }

                    colorForfInt( contractCounterField, client.getExps( ).getExp( ExpEnum.MONTH ).getOptions().getConBidAskCounter( ) );
                    colorForf( opAvgField, client.getExps( ).getMainExp( ).getOptions().getOpAvg( ), null );

                } catch ( InterruptedException e ) {
                    close( );
                }
            }
        }

        // Present
        public void colorForf( JTextField field, double val, String stringToAdd ) {

            if ( val > 0 ) {
                field.setForeground( Themes.GREEN );
            } else {
                field.setForeground( Themes.RED );
            }

            if ( stringToAdd != null ) {

                field.setText( str( val ) + stringToAdd );

            } else {

                field.setText( str( val ) );

            }

        }

        // Present
        public void colorForfInt( JTextField field, double val ) {

            if ( val > 0 ) {
                field.setText( str( ( int ) val ) );
                field.setForeground( Themes.GREEN );
            } else {
                field.setText( str( ( int ) val ) );
                field.setForeground( Themes.RED );
            }
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

        public void close() {
            run = false;
        }

    }
}
