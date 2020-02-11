package gui;

import api.Downloader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Set;

public class MyMainWindow {

    public JFrame frame;
    JLabel connectionStatusLbl;
    Thread runner;
    private JButton wallstreetWindowBtn;
    private JTextField daxField;
    private JTextField spxField;
    private JTextField stocksField;
    private JTextField ndxField;
    private JTextField daxSymbolField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;

    /**
     * Create the application.
     */
    public MyMainWindow() {
        initialize( );

        runner = new Thread( () -> {
            while ( true ) {
                try {
                    Set< Thread > threads = Thread.getAllStackTraces( ).keySet( );
                    System.out.println( threads.size( ) );

                    Thread.sleep( 1000 );
                } catch ( InterruptedException e ) {
                    break;
                }
            }
        } );

        runner.start( );
    }

    /**
     * Launch the application.
     */
    public static void main( String[] args ) {
        EventQueue.invokeLater( new Runnable( ) {
            public void run() {
                try {
                    MyMainWindow window = new MyMainWindow( );
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
            public void windowClosed( WindowEvent arg0 ) {
                runner.interrupt( );
                frame.dispose( );
            }
        } );
        frame.setBounds( 100, 100, 736, 499 );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.getContentPane( ).setLayout( null );

        JPanel panel = new JPanel( );
        panel.setBackground( new Color( 0, 0, 51 ) );
        panel.setBounds( 0, 0, 111, 465 );
        frame.getContentPane( ).add( panel );

        JDesktopPane desktopPane = new JDesktopPane( );
        desktopPane.setBounds( 110, 0, 611, 465 );
        frame.getContentPane( ).add( desktopPane );

        JPanel twsPanel = new JPanel( );
        twsPanel.setBackground( UIManager.getColor( "CheckBox.light" ) );
        twsPanel.setBounds( 0, 0, desktopPane.getWidth( ), desktopPane.getHeight( ) );
        desktopPane.add( twsPanel );
        twsPanel.setLayout( null );

        JPanel panel_1 = new JPanel( );
        panel_1.setBackground( UIManager.getColor( "CheckBox.light" ) );
        panel_1.setBounds( 10, 11, 592, 112 );
        twsPanel.add( panel_1 );
        panel_1.setLayout( null );

        JLabel lblNewLabel = new JLabel( "Tws connection" );
        lblNewLabel.setBounds( 10, 0, 150, 34 );
        panel_1.add( lblNewLabel );
        lblNewLabel.setFont( new Font( "Arial", Font.BOLD, 16 ) );

        JButton btnDisconnect = new JButton( "Disconnect" );
        btnDisconnect.setBounds( 460, 0, 122, 27 );
        panel_1.add( btnDisconnect );
        btnDisconnect.addActionListener( new ActionListener( ) {
            public void actionPerformed( ActionEvent arg0 ) {
                new Thread( () -> {
                    try {
                        Downloader downloader = Downloader.getInstance( );
                        downloader.close( );
                    } finally {
                    }
                } ).start( );
            }
        } );
        btnDisconnect.setFont( new Font( "Arial", Font.BOLD, 15 ) );

        JButton connectBtn = new JButton( "Connect" );
        connectBtn.setBounds( 460, 38, 122, 27 );
        panel_1.add( connectBtn );
        connectBtn.addActionListener( new ActionListener( ) {
            public void actionPerformed( ActionEvent arg0 ) {
                new Thread( () -> {
                    try {
                        Downloader downloader = Downloader.getInstance( );
                        downloader.start( );
                    } finally {
                    }
                } ).start( );
            }
        } );
        connectBtn.setFont( new Font( "Arial", Font.BOLD, 15 ) );

        connectionStatusLbl = new JLabel( "False" );
        connectionStatusLbl.setBounds( 485, 76, 96, 34 );
        panel_1.add( connectionStatusLbl );
        connectionStatusLbl.setHorizontalAlignment( SwingConstants.CENTER );
        connectionStatusLbl.setFont( new Font( "Arial", Font.PLAIN, 15 ) );

        JPanel panel_2 = new JPanel( );
        panel_2.setBackground( UIManager.getColor( "CheckBox.light" ) );
        panel_2.setBounds( 10, 134, 261, 161 );
        twsPanel.add( panel_2 );
        panel_2.setLayout( null );

        JLabel lblWallStreet = new JLabel( "Wall street" );
        lblWallStreet.setFont( new Font( "Arial", Font.BOLD, 16 ) );
        lblWallStreet.setBounds( 10, 0, 150, 34 );
        panel_2.add( lblWallStreet );

        JLabel lblOpenCounterWindow = new JLabel( "Counter" );
        lblOpenCounterWindow.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
        lblOpenCounterWindow.setBounds( 10, 33, 61, 34 );
        panel_2.add( lblOpenCounterWindow );

        wallstreetWindowBtn = new JButton( "Window" );
        wallstreetWindowBtn.addActionListener( new ActionListener( ) {
            public void actionPerformed( ActionEvent e ) {
                WallStreetWindow window = new WallStreetWindow( );
                window.start( );
            }
        } );
        wallstreetWindowBtn.setFont( new Font( "Arial", Font.BOLD, 15 ) );
        wallstreetWindowBtn.setBounds( 10, 65, 101, 27 );
        panel_2.add( wallstreetWindowBtn );

        JPanel panel_3 = new JPanel( );
        panel_3.setBackground( UIManager.getColor( "CheckBox.light" ) );
        panel_3.setBounds( 10, 306, 261, 142 );
        twsPanel.add( panel_3 );
        panel_3.setLayout( null );

        JLabel lblStockNdx = new JLabel( "NDX Stocks" );
        lblStockNdx.setFont( new Font( "Arial", Font.BOLD, 16 ) );
        lblStockNdx.setBounds( 10, 0, 150, 34 );
        panel_3.add( lblStockNdx );

        JLabel label = new JLabel( "Counter" );
        label.setFont( new Font( "Arial", Font.PLAIN, 15 ) );
        label.setBounds( 10, 35, 61, 34 );
        panel_3.add( label );

        JPanel panel_4 = new JPanel( );
        panel_4.setBounds( 281, 134, 321, 309 );
        twsPanel.add( panel_4 );
        panel_4.setLayout( null );

        JLabel lblDax = new JLabel( "Dax" );
        lblDax.setBounds( 0, 11, 55, 25 );
        panel_4.add( lblDax );
        lblDax.setHorizontalAlignment( SwingConstants.CENTER );
        lblDax.setFont( new Font( "Arial", Font.PLAIN, 15 ) );

        daxField = new JTextField( );
        daxField.setBounds( 59, 16, 63, 20 );
        panel_4.add( daxField );
        daxField.setColumns( 10 );

        daxSymbolField = new JTextField( );
        daxSymbolField.setColumns( 10 );
        daxSymbolField.setBounds( 132, 16, 63, 20 );
        panel_4.add( daxSymbolField );

        JLabel lblUsa = new JLabel( "Spx" );
        lblUsa.setBounds( 0, 47, 55, 25 );
        panel_4.add( lblUsa );
        lblUsa.setHorizontalAlignment( SwingConstants.CENTER );
        lblUsa.setFont( new Font( "Arial", Font.PLAIN, 15 ) );

        JLabel lblNdx = new JLabel( "Ndx" );
        lblNdx.setBounds( 0, 83, 55, 25 );
        panel_4.add( lblNdx );
        lblNdx.setHorizontalAlignment( SwingConstants.CENTER );
        lblNdx.setFont( new Font( "Arial", Font.PLAIN, 15 ) );

        JLabel lblSocks = new JLabel( "Socks" );
        lblSocks.setBounds( 0, 119, 55, 25 );
        panel_4.add( lblSocks );
        lblSocks.setHorizontalAlignment( SwingConstants.CENTER );
        lblSocks.setFont( new Font( "Arial", Font.PLAIN, 15 ) );

        spxField = new JTextField( );
        spxField.setBounds( 59, 47, 63, 20 );
        panel_4.add( spxField );
        spxField.setColumns( 10 );

        textField_1 = new JTextField( );
        textField_1.setColumns( 10 );
        textField_1.setBounds( 132, 47, 63, 20 );
        panel_4.add( textField_1 );

        ndxField = new JTextField( );
        ndxField.setBounds( 59, 86, 63, 20 );
        panel_4.add( ndxField );
        ndxField.setColumns( 10 );

        textField_2 = new JTextField( );
        textField_2.setColumns( 10 );
        textField_2.setBounds( 132, 86, 63, 20 );
        panel_4.add( textField_2 );

        stocksField = new JTextField( );
        stocksField.setBounds( 59, 122, 63, 20 );
        panel_4.add( stocksField );
        stocksField.setColumns( 10 );

        textField_3 = new JTextField( );
        textField_3.setColumns( 10 );
        textField_3.setBounds( 132, 122, 63, 20 );
        panel_4.add( textField_3 );

    }

    // JOptionpane popup
    public void popUpMessage( String message, Exception e ) {
        JOptionPane.showMessageDialog( frame, message + "\n" + e.getMessage( ) + "\n" + e.getCause( ) );
    }
}
