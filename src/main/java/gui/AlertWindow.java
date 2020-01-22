package gui;

import api.Manifest;
import locals.Themes;

import javax.swing.*;
import java.awt.*;

public class AlertWindow {

	JTextArea textArea;
	JLabel messageLbl;
	JLabel causeLbl;
	private JFrame frame;

	/**
	 * Create the application.
	 */
	public AlertWindow () {
		initialize ( );
	}


	public AlertWindow ( String message, String cause, String stackTrace ) {
		this ( );
		messageLbl.setText ( message );
		causeLbl.setText ( cause );
		textArea.setText ( stackTrace );
	}

	public static void Show ( String message, String cause, String stackTrace ) {
		AlertWindow alertWindow = new AlertWindow ( message, cause, stackTrace );
		alertWindow.frame.setVisible ( true );
		showOnScreen ( Manifest.SCREEN, alertWindow.frame );
	}

	public static void Show ( String message, Throwable cause, StackTraceElement[] stackTrace ) {
		AlertWindow alertWindow = new AlertWindow ( message, cause.toString ( ), stackTrace.toString ( ) );
		alertWindow.frame.setVisible ( true );
		showOnScreen ( Manifest.SCREEN, alertWindow.frame );
	}

	/**
	 * Launch the application.
	 */
	public static void main ( String[] args ) {
		EventQueue.invokeLater ( new Runnable ( ) {
			public void run () {
				try {
					AlertWindow window = new AlertWindow ( "Exception", "My Couse",
							"You should put the column djhdlksjsakdhaklsdjfhaskjfhasdkjfhaskfhasdkfhsdfkash \n\n sdfsdsdasdasdas\n\n dfgdfgdf \n\n\nfdsdfsdfs" );
					window.frame.setVisible ( true );
				} catch ( Exception e ) {
					e.printStackTrace ( );
				}
			}
		} );
	}

	// Show on screen
	public static void showOnScreen ( int screen, JFrame frame ) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment ( );
		GraphicsDevice[] gd = ge.getScreenDevices ( );
		if ( screen > -1 && screen < gd.length ) {
			frame.setLocation ( gd[ screen ].getDefaultConfiguration ( ).getBounds ( ).x + frame.getX ( ), frame.getY ( ) );
		} else if ( gd.length > 0 ) {
			frame.setLocation ( gd[ 0 ].getDefaultConfiguration ( ).getBounds ( ).x + frame.getX ( ), frame.getY ( ) );
		} else {
			throw new RuntimeException ( "No Screens Found" );
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize () {
		frame = new JFrame ( );
		frame.setBounds ( 100, 100, 427, 491 );
		frame.setDefaultCloseOperation ( JFrame.DISPOSE_ON_CLOSE );
		frame.getContentPane ( ).setBackground ( Themes.BINANCE_GREY );
		frame.getContentPane ( ).setLayout ( null );

		JPanel panel = new JPanel ( );
		panel.setBounds ( 0, 0, 411, 452 );
		panel.setBackground ( Themes.BINANCE_GREY );
		panel.setLayout ( null );
		frame.getContentPane ( ).add ( panel );

		messageLbl = new JLabel ( "Message" );
		messageLbl.setBounds ( 0, 0, 413, 32 );
		messageLbl.setHorizontalAlignment ( SwingConstants.CENTER );
		messageLbl.setForeground ( Themes.BINANCE_ORANGE );
		messageLbl.setFont ( new Font ( "Dubai Medium", Font.PLAIN, 18 ) );
		panel.add ( messageLbl );

		causeLbl = new JLabel ( "Cause" );
		causeLbl.setBounds ( 0, 49, 413, 26 );
		causeLbl.setHorizontalAlignment ( SwingConstants.CENTER );
		causeLbl.setForeground ( Themes.BINANCE_ORANGE );
		causeLbl.setFont ( new Font ( "Dubai Medium", Font.PLAIN, 15 ) );
		panel.add ( causeLbl );

		JScrollPane scrollPane = new JScrollPane ( );
		scrollPane.setBounds ( 10, 103, 403, 349 );
		scrollPane.setViewportBorder ( null );
		scrollPane.setBorder ( null );
		panel.add ( scrollPane );

		textArea = new JTextArea ( );
		scrollPane.setViewportView ( textArea );
		textArea.setFont ( new Font ( "Dubai Medium", Font.PLAIN, 14 ) );
		textArea.setForeground ( Color.WHITE );
		textArea.setBackground ( Themes.BINANCE_GREY );

		JSeparator separator = new JSeparator ( );
		separator.setForeground ( Color.BLACK );
		separator.setBackground ( Color.BLACK );
		separator.setBounds ( 10, 86, 391, 12 );
		panel.add ( separator );
	}


}
