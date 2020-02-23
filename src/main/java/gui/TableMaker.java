package gui;

import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TableMaker {

    JPopupMenu popupMenu;

    public TableMaker( JPopupMenu popupMenu ) {
        this.popupMenu = popupMenu;
    }

    public JTable myTable( BASE_CLIENT_OBJECT client, Object[][] rowsData, String[] cols, int rowHeight ) {
        Color darkBlue = new Color( 0, 51, 102 );

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
                            }

                } catch ( Exception e ) {
                    e.printStackTrace( );
                }

                return c;
            }
        };
        table.setBounds( 0, 0, 300, 100 );
        table.setPreferredSize( new Dimension( 600, 90 ) );

        // Header
        table.setTableHeader( null );

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer( );
        centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );

        table.setDefaultRenderer( Object.class, centerRenderer );
        table.setFillsViewportHeight( true );
        table.setRowHeight( rowHeight );
        table.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
        table.setShowGrid( true );
        table.setSelectionBackground( Color.YELLOW );
        table.addMouseListener( new CustomMouseListerer( ) );

        return table;
    }

    public JTable futuresTable( Object[][] rowsData, String[] cols, int rowHeight ) {
        Color darkBlue = new Color( 0, 51, 102 );

        // Table
        JTable table = new JTable( rowsData, cols ) {


            public Component prepareRenderer( TableCellRenderer renderer, int row, int col ) {

                Component c = super.prepareRenderer( renderer, row, col );

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

                if ( col == 0 ) {
                    c.setFont( c.getFont( ).deriveFont( Font.BOLD ) );
                    c.setForeground( Color.BLACK );
                } else if ( col == 1 ) {
                    if ( cell_val > 0 ) {
                        c.setForeground( Themes.GREEN );
                    } else {
                        c.setForeground( Themes.RED );
                    }
                }
                return c;
            }
        };
        table.setBounds( 0, 0, 300, 235 );

        // Header
        table.setTableHeader( null );
        table.addMouseListener( new CustomMouseListerer( ) );

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer( );
        centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
        table.setDefaultRenderer( Object.class, centerRenderer );
        table.setFillsViewportHeight( true );
        table.setRowHeight( rowHeight );
        table.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
        table.setShowGrid( true );
        table.setSelectionBackground( Color.YELLOW );
        return table;
    }


    public JTable stocksTable( BASE_CLIENT_OBJECT client, Object[][] rowsData, String[] cols, int rowHeight ) {

        // Table
        JTable table = new JTable( rowsData, cols ) {

            public Component prepareRenderer( TableCellRenderer renderer, int row, int col ) {

                Component c = super.prepareRenderer( renderer, row, col );

                c.setBackground( Themes.BINANCE_GREY );

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
                            c.setForeground( Themes.BINANCE_GREEN );
                        } else {
                            c.setForeground( Themes.BINANCE_RED );
                        }

                    } else

                        // Put
                        if ( col == 2 ) {

                            // Color forf
                            if ( cell_val > 0 ) {
                                c.setForeground( Themes.BINANCE_GREEN );
                            } else {
                                c.setForeground( Themes.BINANCE_RED );
                            }

                        } else

                            // Strike
                            if ( col == 1 ) {
                                c.setFont( c.getFont( ).deriveFont( Font.BOLD ) );
                                c.setForeground( Color.WHITE );
                            }

                } catch ( Exception e ) {
                    e.printStackTrace( );
                }

                return c;
            }
        };
        table.setBounds( 0, 0, 300, 235 );

        // Header
        table.setTableHeader( null );

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer( );
        centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );

        table.setBackground( Themes.BINANCE_GREY );
        table.setDefaultRenderer( Object.class, centerRenderer );
        table.setFillsViewportHeight( false );
        table.setRowHeight( rowHeight );
        table.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
        table.setShowGrid( false );
        table.setSelectionBackground( Themes.BINANCE_ORANGE );
        table.addMouseListener( new CustomMouseListerer( ) );

        return table;
    }


    class CustomMouseListerer extends MouseAdapter {

        @Override
        public void mouseClicked( MouseEvent event ) {

            // Pop up the manu
            if ( event.getModifiers( ) == InputEvent.BUTTON3_MASK ) {
                // Show the menu
                popupMenu.show( event.getComponent( ), event.getX( ), event.getY( ) );
            }

            // Set the middle strike for spx table
            if ( event.getClickCount( ) == 3 ) {
                try {
                    Spx spx = Spx.getInstance( );
                    int strike = ( int ) spx.getOptionsHandler( ).getOptionsMonth( ).getStrikeInMoney( );
                    TablesUpdater.setSpxTableStrikes( strike );
                } catch ( Exception e ) {
                    AlertWindow.Show( e.getMessage( ), e.getCause( ).toString( ), e.getStackTrace( ).toString( ) );
                }
            }
        }
    }
}





