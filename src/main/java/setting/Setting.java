package setting;

import dataBase.HB;
import gui.MyGuiComps;
import locals.L;
import locals.Themes;
import options.Options;
import org.json.JSONObject;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.DaxCLIENTObject;
import serverObjects.indexObjects.INDEX_CLIENT_OBJECT;
import serverObjects.indexObjects.NdxCLIENTObject;
import serverObjects.indexObjects.SpxCLIENTObject;
import tables.status.IndexStatusTable;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

public class Setting {

    public JFrame frmSetting;
    public JLabel statusLabel;
    JPanel racesPanel;
    JScrollPane scrollPane;
    String indexOrStock;
    Options options;
    private JLabel lblSet;
    private JPanel panel;
    private JPanel setting_panel_inside;
    private JPanel set_panel_image;
    private JTextField index_down;
    private JTextField future_down;
    private JTextField future_up;
    private JTextField index_up;
    private JLabel lblRaces;
    private JLabel lblExpiration;
    private JLabel lblOpen;
    private JTextField open;
    private JLabel lblBase;
    private JTextField base;
    private BASE_CLIENT_OBJECT client;
    private JDesktopPane deskTop;
    private JLabel lblRefresh;
    private JLabel lblRefresh_1;
    private JLabel lblArik_1;
    private JPanel arik_panel;
    private ChooserPanel myChooserPanel;
    private JSeparator separator_2;
    private JLabel lblProperties;
    private JLabel lblInterest;
    private JTextField InterestField;
    private JLabel lblDevidend;
    private JTextField devidendField;
    private JTextField differentDaysLeftField;
    private JButton btnOptHandlerStart;
    private JLabel lblOptimi;
    private JLabel lblPesimi;
    private JTextField conBdCounterField;
    private JLabel lblConDayBd;
    private JTextField conDayBdCounterField;
    private JButton btnResetStatus;
    private JButton btnLoadStatus;
    private JTextField opAvgField;
    private JTextField equalMoveField;
    private JTextField askCoastField;
    private JButton btnUpdate;
    private JComboBox optionsCombo;
    private JTextField futureField;
    private MyGuiComps.MyTextField opAvgMoveField;

    /**
     * Create the application.
     *
     * @wbp.parser.entryPoint
     */
    public Setting( String indexOrStock ) {
        this.indexOrStock = indexOrStock;
        initialize( );

        client = SpxCLIENTObject.getInstance( );
        options = client.getOptionsHandler( ).getMainOptions( );
    }

    // /**
    // * Launch the application.
    // */
    public static void main( String[] args ) {
        EventQueue.invokeLater( new Runnable( ) {
            public void run() {
                try {
                    Setting window = new Setting( "index" );
                    window.frmSetting.setVisible( true );
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

        try {
            frmSetting = new JFrame( );
            frmSetting.setTitle( "Setting" );
            frmSetting.setBounds( 100, 100, 764, 827 );
            frmSetting.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
            Image image = ( new ImageIcon( "C:\\Users\\user\\Desktop\\Work\\Development\\icons\\Misc-Database-3-icon.png" )
                    .getImage( ).getScaledInstance( 30, 30, Image.SCALE_SMOOTH ) );
            frmSetting.getContentPane( ).setLayout( null );

            panel = new JPanel( );
            panel.setBounds( 0, 50, 150, 738 );
            frmSetting.getContentPane( ).add( panel );
            panel.setBackground( new Color( 47, 79, 79 ) );
            panel.setLayout( null );

            set_panel_image = new JPanel( );
            set_panel_image.setBackground( new Color( 47, 79, 79 ) );
            set_panel_image.setBounds( 0, 0, 150, 50 );
            panel.add( set_panel_image );
            set_panel_image.setLayout( null );

            lblSet = new JLabel( "Setting" );
            lblSet.setForeground( new Color( 255, 255, 255 ) );
            lblSet.setBounds( 0, 0, 150, 50 );
            set_panel_image.add( lblSet );
            lblSet.setHorizontalAlignment( SwingConstants.CENTER );
            lblSet.addMouseListener( new MouseAdapter( ) {
                @Override
                public void mouseClicked( MouseEvent arg0 ) {
                    activater( scrollPane, set_panel_image );
                }
            } );
            lblSet.setIcon(
                    new ImageIcon( new ImageIcon( "C:\\Users\\user\\Desktop\\Work\\Development\\icons\\Setting.png" )
                            .getImage( ).getScaledInstance( 30, 30, Image.SCALE_SMOOTH ) ) );
            lblSet.setBackground( new Color( 47, 79, 79 ) );
            lblSet.setFont( new Font( "Arial", Font.PLAIN, 15 ) );

            deskTop = new JDesktopPane( );
            deskTop.addMouseListener( new MouseAdapter( ) {
                @Override
                public void mouseReleased( MouseEvent arg0 ) {
                    resetStatusLabel( );
                }
            } );
            deskTop.addKeyListener( new KeyAdapter( ) {
                @Override
                public void keyReleased( KeyEvent e ) {
                    resetStatusLabel( );
                }
            } );
            deskTop.setBounds( 149, 0, 456, 826 );
            frmSetting.getContentPane( ).add( deskTop );

            setting_panel_inside = new JPanel( );
            setting_panel_inside.addMouseListener( new MouseAdapter( ) {
                @Override
                public void mouseReleased( MouseEvent e ) {
                    resetStatusLabel( );
                }
            } );
            setting_panel_inside.addKeyListener( new KeyAdapter( ) {
                @Override
                public void keyReleased( KeyEvent arg0 ) {
                    resetStatusLabel( );
                }
            } );
            setting_panel_inside.addFocusListener( new FocusAdapter( ) {
                @Override
                public void focusGained( FocusEvent arg0 ) {
                    resetStatusLabel( );
                }

            } );
            setting_panel_inside.setBounds( 165, 13, 1, 1 );
            setting_panel_inside.setPreferredSize( new Dimension( 491, 800 ) );
            setting_panel_inside.setBackground( Color.WHITE );
            setting_panel_inside.setLayout( null );

            lblRaces = new JLabel( "Races" );
            lblRaces.setFont( new Font( "Dubai Medium", Font.PLAIN, 20 ) );
            lblRaces.setBounds( 10, 56, 72, 33 );
            setting_panel_inside.add( lblRaces );

            JLabel lblFuture = new JLabel( "Future" );
            lblFuture.setBounds( 11, 86, 45, 21 );
            setting_panel_inside.add( lblFuture );
            lblFuture.setFont( new Font( "Dubai Medium", Font.PLAIN, 15 ) );

            JLabel lblIndex_1 = new JLabel( "Index" );
            lblIndex_1.setBounds( 68, 86, 45, 21 );
            setting_panel_inside.add( lblIndex_1 );
            lblIndex_1.setFont( new Font( "Dubai Medium", Font.PLAIN, 15 ) );

            index_up = new JTextField( );
            index_up.setBounds( 64, 113, 45, 22 );
            setting_panel_inside.add( index_up );
            index_up.setHorizontalAlignment( SwingConstants.CENTER );
            index_up.setForeground( new Color( 0, 51, 153 ) );
            index_up.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
            index_up.setColumns( 10 );
            index_up.setBorder( new LineBorder( new Color( 220, 220, 220 ), 2, true ) );
            index_up.setBackground( Color.WHITE );

            future_up = new JTextField( );
            future_up.setBounds( 10, 113, 45, 22 );
            setting_panel_inside.add( future_up );
            future_up.setHorizontalAlignment( SwingConstants.CENTER );
            future_up.setForeground( new Color( 0, 51, 153 ) );
            future_up.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
            future_up.setColumns( 10 );
            future_up.setBorder( new LineBorder( new Color( 220, 220, 220 ), 2, true ) );
            future_up.setBackground( Color.WHITE );

            future_down = new JTextField( );
            future_down.setBounds( 10, 146, 45, 22 );
            setting_panel_inside.add( future_down );
            future_down.setHorizontalAlignment( SwingConstants.CENTER );
            future_down.setForeground( new Color( 204, 0, 51 ) );
            future_down.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
            future_down.setColumns( 10 );
            future_down.setBorder( new LineBorder( new Color( 220, 220, 220 ), 2, true ) );
            future_down.setBackground( Color.WHITE );

            index_down = new JTextField( );
            index_down.setBounds( 64, 146, 45, 22 );
            setting_panel_inside.add( index_down );
            index_down.setHorizontalAlignment( SwingConstants.CENTER );
            index_down.setForeground( new Color( 204, 0, 51 ) );
            index_down.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
            index_down.setColumns( 10 );
            index_down.setBorder( new LineBorder( new Color( 220, 220, 220 ), 2, true ) );
            index_down.setBackground( Color.WHITE );

            JButton button = new JButton( "Set" );
            button.addActionListener( new ActionListener( ) {
                public void actionPerformed( ActionEvent e ) {
                    try {
                        client.setConUp( Integer.parseInt( future_up.getText( ) ) );
                        client.setConDown( Integer.parseInt( future_down.getText( ) ) );
                        client.setIndexUp( Integer.parseInt( index_up.getText( ) ) );
                        client.setIndexDown( Integer.parseInt( index_down.getText( ) ) );
                    } catch ( Exception e2 ) {
                        // TODO: handle exception
                    }
                }
            } );
            button.setBounds( 10, 179, 99, 22 );
            setting_panel_inside.add( button );
            button.setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR ) );
            button.setForeground( new Color( 0, 0, 51 ) );
            button.setFont( new Font( "Dubai Medium", Font.PLAIN, 15 ) );
            button.setBackground( new Color( 220, 220, 220 ) );

            lblExpiration = new JLabel( "Exp" );
            lblExpiration.setFont( new Font( "Dubai Medium", Font.PLAIN, 24 ) );
            lblExpiration.setBounds( 10, 260, 88, 33 );
            setting_panel_inside.add( lblExpiration );

            JLabel label_1 = new JLabel( "Future" );
            label_1.setFont( new Font( "Dubai Medium", Font.PLAIN, 15 ) );
            label_1.setBounds( 11, 386, 45, 21 );
            setting_panel_inside.add( label_1 );

            JLabel label_2 = new JLabel( "Index" );
            label_2.setFont( new Font( "Dubai Medium", Font.PLAIN, 15 ) );
            label_2.setBounds( 68, 386, 45, 21 );
            setting_panel_inside.add( label_2 );


            futureField = new JTextField( 10 );
            futureField.setBounds( 300, 5, 65, 30 );
            futureField.addActionListener( new ActionListener( ) {
                @Override
                public void actionPerformed( ActionEvent actionEvent ) {
                    client.setFuture( Double.parseDouble( futureField.getText( ) ) );
                    System.out.println( futureField.getText( ) );
                }
            } );
            setting_panel_inside.add( futureField );

            JLabel lblExpirationStartPrice = new JLabel( "Open" );
            lblExpirationStartPrice.setFont( new Font( "Dubai Medium", Font.PLAIN, 15 ) );
            lblExpirationStartPrice.setBounds( 10, 446, 45, 21 );
            setting_panel_inside.add( lblExpirationStartPrice );

            scrollPane = new JScrollPane( setting_panel_inside );
            scrollPane.setBounds( 0, 0, 517, 815 );
            scrollPane.getHorizontalScrollBar( ).setVisible( false );
            deskTop.add( scrollPane );

            lblOpen = new JLabel( "Open" );
            lblOpen.setFont( new Font( "Dubai Medium", Font.PLAIN, 15 ) );
            lblOpen.setBounds( 131, 86, 45, 21 );
            setting_panel_inside.add( lblOpen );

            open = new JTextField( );
            open.addActionListener( new ActionListener( ) {
                public void actionPerformed( ActionEvent arg0 ) {
                    try {
                        client.setOpen(  Double.parseDouble( open.getText( ) )    );
                    } catch ( Exception e ) {
                        e.printStackTrace();
                    }
                }
            } );
            open.setHorizontalAlignment( SwingConstants.CENTER );
            open.setForeground( new Color( 0, 51, 153 ) );
            open.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
            open.setColumns( 10 );
            open.setBorder( new LineBorder( new Color( 220, 220, 220 ), 2, true ) );
            open.setBackground( Color.WHITE );
            open.setBounds( 131, 113, 72, 22 );
            setting_panel_inside.add( open );

            lblBase = new JLabel( "Base" );
            lblBase.setFont( new Font( "Dubai Medium", Font.PLAIN, 15 ) );
            lblBase.setBounds( 217, 86, 45, 21 );
            setting_panel_inside.add( lblBase );

            base = new JTextField( );
            base.addActionListener( new ActionListener( ) {
                public void actionPerformed( ActionEvent e ) {
                    try {
                        client.setBase( Double.parseDouble( base.getText( ) )  );
                    } catch ( Exception e1 ) {
                        e1.printStackTrace();
                    }
                }
            } );
            base.setHorizontalAlignment( SwingConstants.CENTER );
            base.setForeground( new Color( 0, 51, 153 ) );
            base.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
            base.setColumns( 10 );
            base.setBorder( new LineBorder( new Color( 220, 220, 220 ), 2, true ) );
            base.setBackground( Color.WHITE );
            base.setBounds( 213, 113, 72, 22 );
            setting_panel_inside.add( base );

            JButton btnDoExp = new JButton( "Do exp" );
            btnDoExp.addActionListener( new ActionListener( ) {
                public void actionPerformed( ActionEvent arg0 ) {
                }
            } );
            btnDoExp.setForeground( new Color( 0, 0, 51 ) );
            btnDoExp.setFont( new Font( "Dubai Medium", Font.PLAIN, 15 ) );
            btnDoExp.setBackground( new Color( 220, 220, 220 ) );
            btnDoExp.setBounds( 10, 502, 99, 22 );
            setting_panel_inside.add( btnDoExp );

            JLabel lblRaces_1 = new JLabel( "Month" );
            lblRaces_1.setFont( new Font( "Dubai Medium", Font.PLAIN, 20 ) );
            lblRaces_1.setBounds( 10, 302, 72, 33 );
            setting_panel_inside.add( lblRaces_1 );

            JSeparator separator = new JSeparator( );
            separator.setForeground( Color.BLACK );
            separator.setBounds( 10, 297, 417, 9 );
            setting_panel_inside.add( separator );

            JLabel lblDaily = new JLabel( "Daily" );
            lblDaily.setFont( new Font( "Dubai Medium", Font.PLAIN, 24 ) );
            lblDaily.setBounds( 10, 11, 88, 33 );
            setting_panel_inside.add( lblDaily );

            JSeparator separator_1 = new JSeparator( );
            separator_1.setForeground( Color.BLACK );
            separator_1.setBounds( 10, 48, 417, 9 );
            setting_panel_inside.add( separator_1 );

            JButton btnRefrseh = new JButton( "Refrseh" );
            btnRefrseh.setForeground( new Color( 0, 0, 51 ) );
            btnRefrseh.setFont( new Font( "Dubai Medium", Font.PLAIN, 15 ) );
            btnRefrseh.setBackground( new Color( 220, 220, 220 ) );
            btnRefrseh.addActionListener( new ActionListener( ) {

                @Override
                public void actionPerformed( ActionEvent e ) {
                    try {
                        options.getLogicService().refresh();
                    } catch ( Exception e1 ) {
                        // TODO: handle exception
                    }
                }
            } );
            btnRefrseh.setBounds( 10, 212, 99, 22 );
            setting_panel_inside.add( btnRefrseh );

            JLabel lblRaces_2 = new JLabel( "Races" );
            lblRaces_2.setFont( new Font( "Dubai Medium", Font.PLAIN, 20 ) );
            lblRaces_2.setBounds( 10, 346, 72, 33 );
            setting_panel_inside.add( lblRaces_2 );

            JLabel lblWeek = new JLabel( "Week" );
            lblWeek.setFont( new Font( "Dubai Medium", Font.PLAIN, 20 ) );
            lblWeek.setBounds( 170, 302, 72, 33 );
            setting_panel_inside.add( lblWeek );

            JLabel label_3 = new JLabel( "Races" );
            label_3.setFont( new Font( "Dubai Medium", Font.PLAIN, 20 ) );
            label_3.setBounds( 170, 346, 72, 33 );
            setting_panel_inside.add( label_3 );

            JLabel label_4 = new JLabel( "Future" );
            label_4.setFont( new Font( "Dubai Medium", Font.PLAIN, 15 ) );
            label_4.setBounds( 171, 386, 45, 21 );
            setting_panel_inside.add( label_4 );

            JLabel label_5 = new JLabel( "Index" );
            label_5.setFont( new Font( "Dubai Medium", Font.PLAIN, 15 ) );
            label_5.setBounds( 228, 386, 45, 21 );
            setting_panel_inside.add( label_5 );

            JLabel label = new JLabel( "Open" );
            label.setFont( new Font( "Dubai Medium", Font.PLAIN, 15 ) );
            label.setBounds( 169, 446, 45, 21 );
            setting_panel_inside.add( label );

            JButton btnStop = new JButton( "Stop" );
            btnStop.addActionListener( new ActionListener( ) {
                public void actionPerformed( ActionEvent e ) {
                    client.closeAll( );
                }
            } );
            btnStop.setForeground( new Color( 0, 0, 51 ) );
            btnStop.setFont( new Font( "Dubai Medium", Font.PLAIN, 15 ) );
            btnStop.setBackground( new Color( 220, 220, 220 ) );
            btnStop.setBounds( 131, 212, 99, 22 );
            setting_panel_inside.add( btnStop );

            JButton btnStart = new JButton( "Start" );
            btnStart.addActionListener( new ActionListener( ) {
                public void actionPerformed( ActionEvent e ) {
                    client.startAll( );
                }
            } );
            btnStart.setFont( new Font( "Dubai Medium", Font.PLAIN, 15 ) );
            btnStart.setBackground( new Color( 220, 220, 220 ) );
            btnStart.setBounds( 244, 212, 99, 22 );
            setting_panel_inside.add( btnStart );

            JLabel lblFutureBidAsk = new JLabel( "Fut bd" );
            lblFutureBidAsk.setFont( new Font( "Dubai Medium", Font.PLAIN, 15 ) );
            lblFutureBidAsk.setBounds( 131, 140, 99, 21 );
            setting_panel_inside.add( lblFutureBidAsk );

            separator_2 = new JSeparator( );
            separator_2.setForeground( Color.BLACK );
            separator_2.setBounds( 10, 572, 417, 9 );
            setting_panel_inside.add( separator_2 );

            lblProperties = new JLabel( "Properties" );
            lblProperties.setFont( new Font( "Dubai Medium", Font.PLAIN, 24 ) );
            lblProperties.setBounds( 10, 535, 113, 33 );
            setting_panel_inside.add( lblProperties );

            lblInterest = new JLabel( "Interest" );
            lblInterest.setHorizontalAlignment( SwingConstants.CENTER );
            lblInterest.setFont( new Font( "Dubai Medium", Font.PLAIN, 15 ) );
            lblInterest.setBounds( 11, 579, 71, 21 );
            setting_panel_inside.add( lblInterest );

            InterestField = new JTextField( );
            InterestField.setHorizontalAlignment( SwingConstants.CENTER );
            InterestField.setForeground( new Color( 0, 51, 153 ) );
            InterestField.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
            InterestField.setColumns( 10 );
            InterestField.setBorder( new LineBorder( new Color( 220, 220, 220 ), 2, true ) );
            InterestField.setBackground( Color.WHITE );
            InterestField.setBounds( 10, 606, 73, 22 );
            setting_panel_inside.add( InterestField );

            lblDevidend = new JLabel( "Devidend" );
            lblDevidend.setHorizontalAlignment( SwingConstants.CENTER );
            lblDevidend.setFont( new Font( "Dubai Medium", Font.PLAIN, 15 ) );
            lblDevidend.setBounds( 96, 579, 72, 21 );
            setting_panel_inside.add( lblDevidend );

            devidendField = new JTextField( );
            devidendField.setHorizontalAlignment( SwingConstants.CENTER );
            devidendField.setForeground( new Color( 0, 51, 153 ) );
            devidendField.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
            devidendField.setColumns( 10 );
            devidendField.setBorder( new LineBorder( new Color( 220, 220, 220 ), 2, true ) );
            devidendField.setBackground( Color.WHITE );
            devidendField.setBounds( 95, 606, 73, 22 );
            setting_panel_inside.add( devidendField );

            JLabel lblDaysLeft = new JLabel( "Days left" );
            lblDaysLeft.setHorizontalAlignment( SwingConstants.CENTER );
            lblDaysLeft.setFont( new Font( "Dubai Medium", Font.PLAIN, 15 ) );
            lblDaysLeft.setBounds( 262, 579, 72, 21 );
            setting_panel_inside.add( lblDaysLeft );

            differentDaysLeftField = new JTextField( );
            differentDaysLeftField.setHorizontalAlignment( SwingConstants.CENTER );
            differentDaysLeftField.setForeground( new Color( 0, 51, 153 ) );
            differentDaysLeftField.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
            differentDaysLeftField.setColumns( 10 );
            differentDaysLeftField.setBorder( new LineBorder( new Color( 220, 220, 220 ), 2, true ) );
            differentDaysLeftField.setBackground( Color.WHITE );
            differentDaysLeftField.setBounds( 261, 606, 73, 22 );
            setting_panel_inside.add( differentDaysLeftField );

            String[] optionsNames = { "Day", "Month", "Three month", "Main options" };

            optionsCombo = new JComboBox( optionsNames );
            optionsCombo.setBounds( 340, 606, 120, 22 );
            optionsCombo.setFont( new Font( "Arial", Font.PLAIN, 12 ) );
            optionsCombo.addActionListener( new ActionListener( ) {
                @Override
                public void actionPerformed( ActionEvent event ) {

                    String selected = ( String ) optionsCombo.getSelectedItem( );

                    switch ( selected.toLowerCase( ) ) {
                        case "day":
                            options = client.getOptionsHandler( ).getOptionsDay( );
                            break;
                        case "month":
                            options = client.getOptionsHandler( ).getOptionsMonth( );
                            break;
                        case "three month":
                            options = client.getOptionsHandler( ).getOptionsQuarter( );
                            break;
                        case "main options":
                            options = client.getOptionsHandler( ).getMainOptions( );
                            break;
                        default:
                            break;
                    }
                }
            } );
            setting_panel_inside.add( optionsCombo );


            JButton btnGotOpt = new JButton( "Got Opt" );
            btnGotOpt.addActionListener( new ActionListener( ) {
                public void actionPerformed( ActionEvent arg0 ) {
                    for ( Options options : client.getOptionsHandler( ).getOptionsList( ) ) {
                        options.setGotData( true );
                    }
                }
            } );
            btnGotOpt.setForeground( new Color( 0, 0, 51 ) );
            btnGotOpt.setFont( new Font( "Dubai Medium", Font.PLAIN, 15 ) );
            btnGotOpt.setBackground( new Color( 220, 220, 220 ) );
            btnGotOpt.setBounds( 244, 242, 99, 22 );
            setting_panel_inside.add( btnGotOpt );

            btnOptHandlerStart = new JButton( "Opt handler start" );
            btnOptHandlerStart.addActionListener( new ActionListener( ) {
                public void actionPerformed( ActionEvent e ) {
                    client.getOptionsDataHandler( ).getRunner( ).getHandler( ).start( );
                }
            } );
            btnOptHandlerStart.setForeground( new Color( 0, 0, 51 ) );
            btnOptHandlerStart.setFont( new Font( "Dubai Medium", Font.PLAIN, 15 ) );
            btnOptHandlerStart.setBackground( new Color( 220, 220, 220 ) );
            btnOptHandlerStart.setBounds( 131, 268, 212, 22 );
            setting_panel_inside.add( btnOptHandlerStart );

            lblOptimi = new JLabel( "Optimi" );
            lblOptimi.setFont( new Font( "Dubai Medium", Font.PLAIN, 15 ) );
            lblOptimi.setBounds( 308, 86, 45, 21 );
            setting_panel_inside.add( lblOptimi );

            lblPesimi = new JLabel( "Pesimi" );
            lblPesimi.setFont( new Font( "Dubai Medium", Font.PLAIN, 15 ) );
            lblPesimi.setBounds( 308, 136, 45, 21 );
            setting_panel_inside.add( lblPesimi );

            JLabel lblConBdCounter = new JLabel( "Con bd" );
            lblConBdCounter.setFont( new Font( "Dubai Medium", Font.PLAIN, 15 ) );
            lblConBdCounter.setBounds( 230, 140, 55, 21 );
            setting_panel_inside.add( lblConBdCounter );

            conBdCounterField = new JTextField( );
            conBdCounterField.addActionListener( new ActionListener( ) {
                public void actionPerformed( ActionEvent arg0 ) {
                    try {
                        client.getOptionsHandler( ).getOptionsMonth( ).setContractBidAskCounter( Integer.parseInt( conBdCounterField.getText( ) ) );
                    } catch ( Exception e ) {
                        // TODO: handle exception
                    }
                }
            } );
            conBdCounterField.setHorizontalAlignment( SwingConstants.CENTER );
            conBdCounterField.setForeground( new Color( 0, 51, 153 ) );
            conBdCounterField.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
            conBdCounterField.setColumns( 10 );
            conBdCounterField.setBorder( new LineBorder( new Color( 220, 220, 220 ), 2, true ) );
            conBdCounterField.setBackground( Color.WHITE );
            conBdCounterField.setBounds( 217, 163, 72, 22 );
            setting_panel_inside.add( conBdCounterField );

            lblConDayBd = new JLabel( "Con day bd" );
            lblConDayBd.setFont( new Font( "Dubai Medium", Font.PLAIN, 15 ) );
            lblConDayBd.setBounds( 371, 136, 72, 21 );
            setting_panel_inside.add( lblConDayBd );

            conDayBdCounterField = new JTextField( );
            conDayBdCounterField.addActionListener( new ActionListener( ) {
                public void actionPerformed( ActionEvent e ) {
                    try {
                        client.getOptionsHandler( ).getOptionsDay( ).setContractBidAskCounter( Integer.parseInt( conDayBdCounterField.getText( ) ) );
                    } catch ( Exception e2 ) {
                        // TODO: handle exception
                    }
                }
            } );
            conDayBdCounterField.setHorizontalAlignment( SwingConstants.CENTER );
            conDayBdCounterField.setForeground( new Color( 0, 51, 153 ) );
            conDayBdCounterField.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
            conDayBdCounterField.setColumns( 10 );
            conDayBdCounterField.setBorder( new LineBorder( new Color( 220, 220, 220 ), 2, true ) );
            conDayBdCounterField.setBackground( Color.WHITE );
            conDayBdCounterField.setBounds( 371, 163, 72, 22 );
            setting_panel_inside.add( conDayBdCounterField );

            JButton btnStopDb = new JButton( "Stop Mysql" );
            btnStopDb.addActionListener( new ActionListener( ) {
                public void actionPerformed( ActionEvent arg0 ) {
                    try {
                        client.getDb( ).getMySql( ).getHandler( ).close( );
                    } catch ( Exception e ) {
                        popup( "Stop mysql faild", e );
                    }
                }
            } );
            btnStopDb.setForeground( new Color( 0, 0, 51 ) );
            btnStopDb.setFont( new Font( "Dubai Medium", Font.PLAIN, 14 ) );
            btnStopDb.setBackground( new Color( 220, 220, 220 ) );
            btnStopDb.setBounds( 10, 690, 139, 22 );
            setting_panel_inside.add( btnStopDb );

            JButton btnStartDb = new JButton( "Start Mysql" );
            btnStartDb.addActionListener( new ActionListener( ) {
                public void actionPerformed( ActionEvent e ) {
                    try {

                        client.getDb( ).getMySql( ).getHandler( ).start( );

                    } catch ( Exception e2 ) {
                        popup( "Start mysql faild", e2 );
                    }
                }
            } );
            btnStartDb.setForeground( new Color( 0, 0, 51 ) );
            btnStartDb.setFont( new Font( "Dubai Medium", Font.PLAIN, 14 ) );
            btnStartDb.setBackground( new Color( 220, 220, 220 ) );
            btnStartDb.setBounds( 10, 723, 139, 22 );
            setting_panel_inside.add( btnStartDb );

            JButton btnOptions = new JButton( "Reset options" );
            btnOptions.addActionListener( new ActionListener( ) {
                public void actionPerformed( ActionEvent arg0 ) {

                    for ( Options options : client.getOptionsHandler( ).getOptionsList( ) ) {
                        options.resetOptionsBidAskCounter( );
                    }

                }
            } );
            btnOptions.setForeground( new Color( 0, 0, 51 ) );
            btnOptions.setFont( new Font( "Dubai Medium", Font.PLAIN, 15 ) );
            btnOptions.setBackground( new Color( 220, 220, 220 ) );
            btnOptions.setBounds( 10, 242, 117, 22 );
            setting_panel_inside.add( btnOptions );

            btnResetStatus = new JButton( "Reset status" );
            btnResetStatus.addActionListener( new ActionListener( ) {
                public void actionPerformed( ActionEvent e ) {
                    client.getTablesHandler( ).getArrayHandler( ).getHandler( ).resetData( );
                    client.getTablesHandler( ).getStatusHandler( ).getHandler( ).resetData( );
//                    Arik.getInstance( ).sendMessage( Arik.sagivID, "Reset success " + Emojis.check_mark, null );
                }
            } );
            btnResetStatus.setForeground( new Color( 0, 0, 51 ) );
            btnResetStatus.setFont( new Font( "Dubai Medium", Font.PLAIN, 14 ) );
            btnResetStatus.setBackground( new Color( 220, 220, 220 ) );
            btnResetStatus.setBounds( 170, 690, 139, 22 );
            setting_panel_inside.add( btnResetStatus );

            btnLoadStatus = new JButton( "Load status " );
            btnLoadStatus.addActionListener( new ActionListener( ) {
                public void actionPerformed( ActionEvent e ) {

                    client.getTablesHandler( ).getStatusHandler( ).getHandler( ).loadData( );

                }
            } );
            btnLoadStatus.setForeground( new Color( 0, 0, 51 ) );
            btnLoadStatus.setFont( new Font( "Dubai Medium", Font.PLAIN, 14 ) );
            btnLoadStatus.setBackground( new Color( 220, 220, 220 ) );
            btnLoadStatus.setBounds( 170, 723, 139, 22 );
            setting_panel_inside.add( btnLoadStatus );

            opAvgField = new JTextField( );
            opAvgField.addActionListener( new ActionListener( ) {
                public void actionPerformed( ActionEvent arg0 ) {

                    try {
                        double opAvgFromUser = Double.parseDouble( opAvgField.getText( ) );

                        options.setOpValues( opAvgFromUser );

                    } catch ( Exception e ) {
                        e.printStackTrace( );
                    }

                }
            } );
            opAvgField.setHorizontalAlignment( SwingConstants.CENTER );
            opAvgField.setForeground( new Color( 0, 51, 153 ) );
            opAvgField.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
            opAvgField.setColumns( 10 );
            opAvgField.setBorder( new LineBorder( new Color( 220, 220, 220 ), 2, true ) );
            opAvgField.setBackground( Color.WHITE );
            opAvgField.setBounds( 355, 239, 72, 22 );
            setting_panel_inside.add( opAvgField );

            opAvgMoveField = new MyGuiComps.MyTextField( 20 );
            opAvgMoveField.setXY( 355, 270 );
            opAvgMoveField.setBorder( new LineBorder( new Color( 220, 220, 220 ), 2, true ) );
            opAvgMoveField.addActionListener( new ActionListener( ) {
                @Override
                public void actionPerformed( ActionEvent actionEvent ) {

                    try {
                        double d = L.dbl( opAvgMoveField.getText( ) );
                        options.getOpAvgMoveService().setMove( d );
                    } catch ( Exception e ) {
                        e.printStackTrace();
                    }
                }
            } );
            setting_panel_inside.add( opAvgMoveField );

            JLabel lblOpavg = new JLabel( "OpAvg" );
            lblOpavg.setFont( new Font( "Dubai Medium", Font.PLAIN, 15 ) );
            lblOpavg.setBounds( 355, 212, 45, 21 );
            setting_panel_inside.add( lblOpavg );

            JLabel lblEqualMove = new JLabel( "Equal move" );
            lblEqualMove.setFont( new Font( "Dubai Medium", Font.PLAIN, 15 ) );
            lblEqualMove.setBounds( 371, 86, 82, 21 );
            setting_panel_inside.add( lblEqualMove );

            equalMoveField = new JTextField( );
            equalMoveField.addActionListener( new ActionListener( ) {
                public void actionPerformed( ActionEvent arg0 ) {

                    double equalMove = 0;

                    try {
                        equalMove = Double.parseDouble( equalMoveField.getText( ) );
                        client.getOptionsHandler( ).getMainOptions( ).getEqualMoveService( ).setMove( equalMove );
                    } catch ( Exception e ) {
                        popup( "Faild to set equalMove", e );
                    }
                }
            } );
            equalMoveField.setHorizontalAlignment( SwingConstants.CENTER );
            equalMoveField.setForeground( new Color( 0, 51, 153 ) );
            equalMoveField.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
            equalMoveField.setColumns( 10 );
            equalMoveField.setBorder( new LineBorder( new Color( 220, 220, 220 ), 2, true ) );
            equalMoveField.setBackground( Color.WHITE );
            equalMoveField.setBounds( 371, 113, 72, 22 );
            setting_panel_inside.add( equalMoveField );

            JLabel lblAskcoast = new JLabel( "Ask coast" );
            lblAskcoast.setHorizontalAlignment( SwingConstants.CENTER );
            lblAskcoast.setFont( new Font( "Dubai Medium", Font.PLAIN, 15 ) );
            lblAskcoast.setBounds( 179, 579, 72, 21 );
            setting_panel_inside.add( lblAskcoast );

            askCoastField = new JTextField( );
            askCoastField.setHorizontalAlignment( SwingConstants.CENTER );
            askCoastField.setForeground( new Color( 0, 51, 153 ) );
            askCoastField.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
            askCoastField.setColumns( 10 );
            askCoastField.setBorder( new LineBorder( new Color( 220, 220, 220 ), 2, true ) );
            askCoastField.setBackground( Color.WHITE );
            askCoastField.setBounds( 178, 606, 73, 22 );
            setting_panel_inside.add( askCoastField );

            statusLabel = new JLabel( "Empty" );
            statusLabel.setHorizontalAlignment( SwingConstants.CENTER );
            statusLabel.setFont( new Font( "Dubai Medium", Font.PLAIN, 15 ) );
            statusLabel.setBounds( 371, 607, 72, 21 );
            setting_panel_inside.add( statusLabel );

            JButton btnSumLine = new JButton( "Sum line" );
            btnSumLine.addActionListener( new ActionListener( ) {
                public void actionPerformed( ActionEvent arg0 ) {
                    client.getTablesHandler( ).getSumHandler( ).getHandler( ).insertLine( );
                }
            } );
            btnSumLine.setForeground( new Color( 0, 0, 51 ) );
            btnSumLine.setFont( new Font( "Dubai Medium", Font.PLAIN, 14 ) );
            btnSumLine.setBackground( new Color( 220, 220, 220 ) );
            btnSumLine.setBounds( 170, 756, 139, 22 );
            setting_panel_inside.add( btnSumLine );

            btnUpdate = new JButton( "Update" );
            btnUpdate.addActionListener( new ActionListener( ) {
                public void actionPerformed( ActionEvent e ) {
                    double interest = 1;
                    double askCoast = 0;
                    double daysLeft = 0;
                    double devidend = 0;

                    try {

                        // If Index object
                        if ( client instanceof INDEX_CLIENT_OBJECT ) {

                            // Load data from DB
                            IndexStatusTable table = ( IndexStatusTable ) HB.get_line_by_id( IndexStatusTable.class,
                                    client.getDbId( ), client.getSessionfactory( ) );

                            JSONObject props = new JSONObject( table.getOptions( ) ).getJSONObject( options.getName( ) ).getJSONObject( "props" );
                            options.setPropsDataFromJson( props );

                            // Set data
                            if ( !InterestField.getText( ).isEmpty( ) ) {
                                interest = Double.parseDouble( InterestField.getText( ) );
                                options.setInterestWithCalc( interest );
                            }

                            if ( !askCoastField.getText( ).isEmpty( ) ) {
                                askCoast = Double.parseDouble( askCoastField.getText( ) );
                                options.setBorrow( askCoast );
                            }

                            if ( !differentDaysLeftField.getText( ).isEmpty( ) ) {
                                daysLeft = Double.parseDouble( differentDaysLeftField.getText( ) );
                                options.setDaysToExp( daysLeft );
                            }

                            if ( !devidendField.getText( ).isEmpty( ) ) {
                                devidend = Double.parseDouble( devidendField.getText( ) );
                                options.setDevidend( devidend );
                            }

                            // Update to DB
                            table.setOptions( client.getOptionsHandler( ).getAllOptionsAsJson( ).toString( ) );

                            HB.update( client.getSessionfactory( ), table );

                            // Update status
                            updateStatus( );
                        }

                    } catch ( Exception e2 ) {

                        popup( "Update faild", e2 );

                    }

                }
            } );
            btnUpdate.setForeground( new Color( 0, 0, 51 ) );
            btnUpdate.setFont( new Font( "Dubai Medium", Font.PLAIN, 14 ) );
            btnUpdate.setBackground( new Color( 220, 220, 220 ) );
            btnUpdate.setBounds( 160, 639, 154, 22 );
            setting_panel_inside.add( btnUpdate );
            scrollPane.getHorizontalScrollBar( ).setBackground( Color.GRAY );
            scrollPane.setBorder( null );
            scrollPane.setViewportBorder( null );
            scrollPane.getVerticalScrollBar( ).setUnitIncrement( 16 );

            JButton mainOptionsBtn = new JButton( "Set main options" );
            mainOptionsBtn.setBounds( 5, 639, 150, 22 );
            mainOptionsBtn.setBackground( new Color( 220, 220, 220 ) );
            mainOptionsBtn.addActionListener( new ActionListener( ) {
                @Override
                public void actionPerformed( ActionEvent actionEvent ) {
                }
            } );
            setting_panel_inside.add( mainOptionsBtn );

            lblArik_1 = new JLabel( "Arik" );
            lblArik_1.setBounds( 0, 49, 150, 50 );
            panel.add( lblArik_1 );
            lblArik_1.setIcon( new ImageIcon( new ImageIcon( "C://Users/user/Desktop/Work/Development/icons/profile_1.ico" )
                    .getImage( ).getScaledInstance( 30, 30, Image.SCALE_SMOOTH ) ) );

            lblArik_1.setIcon(
                    new ImageIcon( new ImageIcon( "C://Users//user//Desktop//Work//Development//icons//profile_1.png" )
                            .getImage( ).getScaledInstance( 30, 30, Image.SCALE_SMOOTH ) ) );
            lblArik_1.setHorizontalAlignment( SwingConstants.CENTER );
            lblArik_1.setForeground( Color.WHITE );
            lblArik_1.setFont( new Font( "Arial", Font.PLAIN, 15 ) );

            arik_panel = new JPanel( );
            arik_panel.setBounds( 0, 0, 517, 789 );
            deskTop.add( arik_panel );
            arik_panel.setLayout( null );

            JLabel lblArik = new JLabel( "Arik" );
            lblArik.setFont( new Font( "Arial", Font.PLAIN, 25 ) );
            lblArik.setBounds( 30, 0, 125, 57 );
            arik_panel.add( lblArik );

            JButton startArik = new JButton( "Start" );
            startArik.addActionListener( new ActionListener( ) {
                public void actionPerformed( ActionEvent arg0 ) {
                    // Arik the bot
//                    Arik.getInstance( ).start( );
                }
            } );
            startArik.setForeground( new Color( 0, 0, 51 ) );
            startArik.setFont( new Font( "Arial", Font.BOLD, 15 ) );
            startArik.setBackground( new Color( 220, 220, 220 ) );
            startArik.setBounds( 10, 432, 99, 30 );
            arik_panel.add( startArik );

            JButton stopArik = new JButton( "Stop" );
            stopArik.addActionListener( new ActionListener( ) {
                public void actionPerformed( ActionEvent e ) {
//                    Arik.getInstance( ).close( );
                }
            } );
            stopArik.setForeground( new Color( 0, 0, 51 ) );
            stopArik.setFont( new Font( "Arial", Font.BOLD, 15 ) );
            stopArik.setBackground( new Color( 220, 220, 220 ) );
            stopArik.setBounds( 119, 432, 99, 30 );
            arik_panel.add( stopArik );

            racesPanel = new JPanel( );
            racesPanel.setBounds( 0, 0, 517, 789 );
            deskTop.add( racesPanel );
            racesPanel.setLayout( null );

            lblRefresh = new JLabel( "Races" );
            lblRefresh.setFont( new Font( "Arial", Font.PLAIN, 25 ) );
            lblRefresh.setBounds( 29, 0, 125, 57 );
            racesPanel.add( lblRefresh );

            lblRefresh_1 = new JLabel( "Refresh" );
            lblRefresh_1.setFont( new Font( "Arial", Font.PLAIN, 18 ) );
            lblRefresh_1.setBounds( 30, 62, 72, 33 );
            racesPanel.add( lblRefresh_1 );

            JPanel panel_2 = new JPanel( );
            panel_2.setLayout( null );
            panel_2.setBackground( new Color( 47, 79, 79 ) );
            panel_2.setBounds( 0, 0, 150, 50 );
            frmSetting.getContentPane( ).add( panel_2 );

            JLabel lblIndex = new JLabel( "Index" );
            lblIndex.setHorizontalAlignment( SwingConstants.CENTER );
            lblIndex.setForeground( Color.WHITE );
            lblIndex.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
            lblIndex.setBounds( 0, 0, 150, 50 );
            panel_2.add( lblIndex );

            myChooserPanel = new ChooserPanel( indexOrStock );
            frmSetting.getContentPane( ).add( myChooserPanel );

        } catch ( HeadlessException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace( );
        } finally {
            // Activate on startup
            activater( scrollPane, set_panel_image );
        }

    }

    public void updateStatus() {
        statusLabel.setText( "Updated" );
        statusLabel.setForeground( Themes.GREEN );
    }

    public void resetStatusLabel() {

        statusLabel.setText( "" );
        statusLabel.setForeground( Color.BLACK );

    }

    // Activater panel
    private void activater( JScrollPane panel, JPanel panel_image ) {
        scrollPane.setVisible( false );
        // racesPanel.setVisible(false);
        panel.setVisible( true );
        set_selection_background( panel_image );
    }

    // Set the background of selection menue
    private void set_selection_background( JPanel panel ) {
        Color color = new Color( 43, 88, 160 );
        Color original = new Color( 47, 79, 79 );
        set_panel_image.setBackground( original );
        panel.setBackground( color );
    }

    // Creating popup window alert
    private void popup( String message, Exception e ) {
        JOptionPane.showMessageDialog( frmSetting, message + "\n" + e.getMessage( ) );
    }

    // Set the window visible
    public void setVisible() {
        frmSetting.setVisible( true );
    }

    public ChooserPanel getMyChooserPanel() {
        return myChooserPanel;
    }

    public void setMyChooserPanel( ChooserPanel myChooserPanel ) {
        this.myChooserPanel = myChooserPanel;
    }

    class ChooserPanel extends JPanel implements MouseListener {

        private String indexOrStock;

        private JRadioButton daxRadioButton;
        private JRadioButton spxRadioButton;
        private JRadioButton ndxRadioButton;

        private ButtonGroup group;

        public ChooserPanel( String indexOrStock ) {
            this.indexOrStock = indexOrStock;
            init( );
        }

        private void init() {

            setBounds( 600, 50, 80, 500 );

            int width = 50;

            // Indexes
            daxRadioButton = initMyRadioButton( "Dax" );
            daxRadioButton.setBounds( 5, 10, width, 20 );
            ndxRadioButton = initMyRadioButton( "Ndx" );
            ndxRadioButton.setBounds( width += width + 5, 10, width, 20 );
            spxRadioButton = initMyRadioButton( "Spx" );
            spxRadioButton.setBounds( width += width + 5, 10, width, 20 );

            if ( indexOrStock.contains( "index" ) ) {
                add( daxRadioButton );
                add( ndxRadioButton );
                add( spxRadioButton );

                group = new ButtonGroup( );
                group.add( daxRadioButton );
                group.add( ndxRadioButton );
                group.add( spxRadioButton );

            }

        }

        // Init my radioButton
        public JRadioButton initMyRadioButton( String name ) {
            JRadioButton radioButton = new JRadioButton( name );
            radioButton.addMouseListener( this );
            radioButton.setFont( new Font( "Dubai Medium", Font.PLAIN, 16 ) );
            return radioButton;
        }

        // Mouse click
        @Override
        public void mouseClicked( MouseEvent e ) {

            if ( daxRadioButton.isSelected( ) ) {
                client = DaxCLIENTObject.getInstance( );
            }
            if ( ndxRadioButton.isSelected( ) ) {
                client = NdxCLIENTObject.getInstance( );
            }
            if ( spxRadioButton.isSelected( ) ) {
                client = SpxCLIENTObject.getInstance( );
            }
        }

        @Override
        public void mouseEntered( MouseEvent e ) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseExited( MouseEvent e ) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mousePressed( MouseEvent e ) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseReleased( MouseEvent e ) {
            if ( daxRadioButton.isSelected( ) ) {
                client = DaxCLIENTObject.getInstance( );
            }
            if ( ndxRadioButton.isSelected( ) ) {
                client = NdxCLIENTObject.getInstance( );
            }
            if ( spxRadioButton.isSelected( ) ) {
                client = SpxCLIENTObject.getInstance( );
            }
        }
    }
}
