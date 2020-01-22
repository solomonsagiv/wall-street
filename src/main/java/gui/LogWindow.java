package gui;

import logger.MyLogger;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class LogWindow {

    public static LogWindow window;

    public Runner runner;
    public JFrame frame;
    public JTextArea textArea;

    /**
     * Create the application.
     */
    public LogWindow() {
        initialize( );

        // Start runner
        runner = new Runner( );
        runner.start( );
    }

    /**
     * Launch the application.
     */
    public static void main( String[] args ) {
        EventQueue.invokeLater( new Runnable( ) {
            public void run() {
                try {
                    window = new LogWindow( );
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
            public void windowClosing( WindowEvent e ) {

                runner.interrupt( );

            }
        } );
        frame.setBounds( 100, 100, 901, 498 );
        frame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );

        textArea = new JTextArea( );
        textArea.setFont( new Font( "Dubai Medium", Font.PLAIN, 12 ) );
        textArea.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        JScrollPane scrollPane = new JScrollPane( textArea );
        frame.getContentPane( ).add( scrollPane, BorderLayout.CENTER );

    }


    private class Runner extends Thread {

        // Variables
        MyLogger logger;

        // Constructor
        public Runner() {

            logger = MyLogger.getInstance( );

        }

        @Override
        public void run() {

            init( );

        }

        private void init() {

            while ( !interrupted( ) ) {

                try {

                    String text = logger.getAllText( ).toString( );
                    textArea.setText( text );

                    sleep( 1000 );
                } catch ( InterruptedException e ) {
                    interrupt( );
                } catch ( IOException e ) {
                    interrupt( );
                    e.printStackTrace( );
                }

            }

        }

    }

}



