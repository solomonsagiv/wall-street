package shlomi.logic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TraderWindow {

    TraderWindow traderWindow;
    JFrame frame;
    ShlomiTrader trader;
    ShlomiBackRunner shlomiBackRunner;

    JLabel Status;
    JTextArea log;
    JLabel statusLabel;
    private JButton btnClose;

    /**
     * Create the application.
     */
    public TraderWindow( ShlomiTrader trader ) {
        this.trader = trader;
        initialize( );

        this.traderWindow = this;

        onStartUp( );
    }

    private void onStartUp() {

        Thread thread = new Thread( () -> {

            try {
                Thread.sleep( 1000 );

                shlomiBackRunner = new ShlomiBackRunner( trader, traderWindow );
                shlomiBackRunner.getHandler( ).start( );
            } catch ( InterruptedException e ) {
                // TODO Auto-generated catch block
                e.printStackTrace( );
            }

        } );

        thread.start( );

    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame( );
        frame.setBounds( 100, 100, 450, 300 );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.getContentPane( ).setLayout( null );

        Status = new JLabel( "Status:" );
        Status.setFont( new Font( "Dubai Medium", Font.PLAIN, 15 ) );
        Status.setBounds( 10, 236, 42, 14 );
        frame.getContentPane( ).add( Status );

        statusLabel = new JLabel( "Not running" );
        statusLabel.setForeground( Color.RED );
        statusLabel.setFont( new Font( "Dubai Medium", Font.PLAIN, 15 ) );
        statusLabel.setBounds( 62, 236, 78, 14 );
        frame.getContentPane( ).add( statusLabel );

        JScrollPane scrollPane = new JScrollPane( );
        scrollPane.setBounds( 197, 0, 237, 261 );
        frame.getContentPane( ).add( scrollPane );

        log = new JTextArea( );
        log.setFont( new Font( "Dubai Medium", Font.PLAIN, 14 ) );
        log.setBackground( SystemColor.menu );
        scrollPane.setViewportView( log );

        JButton btnNewButton = new JButton( "Start" );
        btnNewButton.addActionListener( new ActionListener( ) {
            public void actionPerformed( ActionEvent arg0 ) {

                trader.getShlomiRunner( ).getHandler( ).start( );

            }
        } );
        btnNewButton.setFont( new Font( "Dubai Medium", Font.PLAIN, 15 ) );
        btnNewButton.setBounds( 10, 169, 78, 23 );
        frame.getContentPane( ).add( btnNewButton );

        btnClose = new JButton( "Close" );
        btnClose.addActionListener( new ActionListener( ) {
            public void actionPerformed( ActionEvent e ) {

                trader.getShlomiRunner( ).getHandler( ).close( );

            }
        } );
        btnClose.setFont( new Font( "Dubai Medium", Font.PLAIN, 15 ) );
        btnClose.setBounds( 10, 203, 78, 23 );
        frame.getContentPane( ).add( btnClose );
    }
}
