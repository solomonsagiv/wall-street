package Tests;

import javax.swing.*;
import java.awt.*;

public class TestOptionsWindow {

	private JFrame frame;

	/**
	 * Create the application.
	 */
	public TestOptionsWindow () {
		initialize ( );
	}

	/**
	 * Launch the application.
	 */
	public static void main ( String[] args ) {
		EventQueue.invokeLater ( new Runnable ( ) {
			public void run () {
				try {
					TestOptionsWindow window = new TestOptionsWindow ( );
					window.frame.setVisible ( true );
				} catch ( Exception e ) {
					e.printStackTrace ( );
				}
			}
		} );
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize () {
		frame = new JFrame ( );
		frame.setBounds ( 100, 100, 450, 300 );
		frame.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
	}

}
