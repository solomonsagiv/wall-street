package gui;

import javax.swing.*;
import java.awt.*;

public class DeepDetailsWindow {

    private JFrame frame;

    /**
     * Create the application.
     */
    public DeepDetailsWindow() {
        initialize( );
    }

    /**
     * Launch the application.
     */
    public static void main( String[] args ) {
        EventQueue.invokeLater( new Runnable( ) {
            public void run() {
                try {
                    DeepDetailsWindow window = new DeepDetailsWindow( );
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
        frame.setBounds( 100, 100, 406, 247 );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.getContentPane( ).setLayout( null );

        JPanel panel = new JPanel( );
        panel.setBackground( new Color( 255, 255, 255 ) );
        panel.setForeground( new Color( 255, 255, 255 ) );
        panel.setBounds( 0, 0, 390, 208 );
        frame.getContentPane( ).add( panel );
        panel.setLayout( null );

        JLabel lblNewLabel = new JLabel( "Ind races" );
        lblNewLabel.setForeground( new Color( 0, 0, 102 ) );
        lblNewLabel.setFont( new Font( "Dubai Medium", Font.BOLD, 15 ) );
        lblNewLabel.setBounds( 10, 44, 78, 14 );
        panel.add( lblNewLabel );

        JLabel lblConRaces = new JLabel( "Con races" );
        lblConRaces.setForeground( new Color( 0, 0, 102 ) );
        lblConRaces.setFont( new Font( "Dubai Medium", Font.BOLD, 15 ) );
        lblConRaces.setBounds( 10, 82, 78, 14 );
        panel.add( lblConRaces );

        JLabel lblEqual = new JLabel( "Equal" );
        lblEqual.setForeground( new Color( 0, 0, 102 ) );
        lblEqual.setFont( new Font( "Dubai Medium", Font.BOLD, 15 ) );
        lblEqual.setBounds( 10, 123, 62, 14 );
        panel.add( lblEqual );

        JLabel lblContractBd = new JLabel( "Contract bd " );
        lblContractBd.setForeground( new Color( 0, 0, 102 ) );
        lblContractBd.setFont( new Font( "Dubai Medium", Font.BOLD, 15 ) );
        lblContractBd.setBounds( 10, 162, 97, 14 );
        panel.add( lblContractBd );

        JLabel lblLast = new JLabel( "Last" );
        lblLast.setForeground( new Color( 0, 0, 102 ) );
        lblLast.setFont( new Font( "Dubai Medium", Font.BOLD, 15 ) );
        lblLast.setBounds( 124, 11, 62, 14 );
        panel.add( lblLast );

        JLabel lblHigh = new JLabel( "High" );
        lblHigh.setForeground( new Color( 0, 0, 102 ) );
        lblHigh.setFont( new Font( "Dubai Medium", Font.BOLD, 15 ) );
        lblHigh.setBounds( 321, 11, 62, 14 );
        panel.add( lblHigh );

        JLabel lblLow = new JLabel( "Low" );
        lblLow.setForeground( new Color( 0, 0, 102 ) );
        lblLow.setFont( new Font( "Dubai Medium", Font.BOLD, 15 ) );
        lblLow.setBounds( 220, 11, 62, 14 );
        panel.add( lblLow );

        JSeparator separator = new JSeparator( );
        separator.setForeground( new Color( 0, 0, 102 ) );
        separator.setBounds( 10, 30, 373, 14 );
        panel.add( separator );

        JSeparator separator_1 = new JSeparator( );
        separator_1.setOrientation( SwingConstants.VERTICAL );
        separator_1.setForeground( new Color( 0, 0, 102 ) );
        separator_1.setBounds( 98, 34, 13, 166 );
        panel.add( separator_1 );
    }
}
