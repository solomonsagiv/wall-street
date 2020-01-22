package gui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.VetoableChangeListener;

public class Test {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Create the application.
	 */
	public Test () {
		initialize ( );
	}

	/**
	 * Launch the application.
	 */
	public static void main ( String[] args ) {
		EventQueue.invokeLater ( new Runnable ( ) {
			public void run () {
				try {
					Test window = new Test ( );
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
		frame.getContentPane ( ).setLayout ( null );

		textField = new JTextField ( );
		textField.addVetoableChangeListener ( new VetoableChangeListener ( ) {
			public void vetoableChange ( PropertyChangeEvent arg0 ) {

			}
		} );

		// Listen for changes in the text
		textField.getDocument ( ).addDocumentListener ( new DocumentListener ( ) {
			public void changedUpdate ( DocumentEvent e ) {
				warn ( );
			}

			public void removeUpdate ( DocumentEvent e ) {
			}

			public void insertUpdate ( DocumentEvent e ) {
				warn ( );
			}

			public void warn () {
				try {
					System.out.println ( "Warn" );
					int num = Integer.parseInt ( textField.getText ( ) );

					if ( num > 0 ) {
						textField.setForeground ( Color.GREEN );
					} else {
						textField.setForeground ( Color.RED );
					}
				} catch ( NumberFormatException e ) {
				}
			}
		} );

		textField.setBounds ( 69, 76, 86, 20 );
		frame.getContentPane ( ).add ( textField );
		textField.setColumns ( 10 );

		textField_1 = new JTextField ( );
		textField_1.addActionListener ( new ActionListener ( ) {
			public void actionPerformed ( ActionEvent arg0 ) {
				textField.setText ( String.valueOf ( textField_1.getText ( ) ) );
			}
		} );
		textField_1.setBounds ( 69, 146, 86, 20 );
		frame.getContentPane ( ).add ( textField_1 );
		textField_1.setColumns ( 10 );
	}
}
