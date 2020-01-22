package charts;

import javax.swing.*;
import java.awt.*;

public class TestChartWindow {

    public JFrame frame;
    public JTextField indField;
    public JTextField futField;

    /**
     * Create the application.
     */
    public TestChartWindow() {
        initialize( );
    }

    /**
     * Launch the application.
     */
    public static void main( String[] args ) {
        EventQueue.invokeLater( new Runnable( ) {
            public void run() {
                try {
                    TestChartWindow window = new TestChartWindow( );
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
        frame.setBounds( 100, 100, 298, 170 );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.getContentPane( ).setLayout( null );

        indField = new JTextField( );
        indField.setText( "3000" );
        indField.setFont( new Font( "Dubai Medium", Font.PLAIN, 15 ) );
        indField.setHorizontalAlignment( SwingConstants.CENTER );
        indField.setBounds( 63, 58, 71, 20 );
        frame.getContentPane( ).add( indField );
        indField.setColumns( 10 );

        futField = new JTextField( );
        futField.setText( "3000" );
        futField.setFont( new Font( "Dubai Medium", Font.PLAIN, 15 ) );
        futField.setHorizontalAlignment( SwingConstants.CENTER );
        futField.setColumns( 10 );
        futField.setBounds( 144, 58, 71, 20 );
        frame.getContentPane( ).add( futField );

        JLabel lblNewLabel = new JLabel( "Ind" );
        lblNewLabel.setHorizontalAlignment( SwingConstants.CENTER );
        lblNewLabel.setBounds( 63, 33, 71, 14 );
        frame.getContentPane( ).add( lblNewLabel );

        JLabel lblFut = new JLabel( "Fut" );
        lblFut.setHorizontalAlignment( SwingConstants.CENTER );
        lblFut.setBounds( 144, 33, 71, 14 );
        frame.getContentPane( ).add( lblFut );
    }

}
