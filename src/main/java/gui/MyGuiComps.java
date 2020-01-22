package gui;

import locals.Themes;

import javax.swing.*;
import java.awt.*;

public class MyGuiComps {

	// JFrame
	public static class MyFrame extends JFrame {

		public MyFrame ( String title ) throws HeadlessException {
			super ( title );
			init ();
		}

		private void init () {

			setDefaultCloseOperation ( DISPOSE_ON_CLOSE );
			setBackground ( Themes.GREY_LIGHT );
			setVisible ( true );
			setBounds ( 100, 100, 400, 400 );
			getContentPane ().setLayout ( null );

		}

	}


	// Panel
	public static class MyPanel extends JPanel {

		public MyPanel () {
			init();
		}

		private void init () {

			setFont ( Themes.VEDANA_12 );
			setBackground ( Themes.GREY_LIGHT );
			setBorder ( null );
			setLayout ( null );
		}

		public void setXY(int x, int y) {
			setBounds ( x, y, getWidth (), getHeight () );
		}
	}


	// TextField
	public static class MyTextField extends JTextField {

		public MyTextField ( int columns ) {
			super ( columns );

			init();
		}

		private void init () {

			setBounds ( new Rectangle ( 60, 25 ) );
			setFont ( Themes.VEDANA_12 );
			setForeground ( Color.BLACK );
			setHorizontalAlignment ( JTextField.CENTER );
			setBackground ( Themes.GREY_VERY_LIGHT );
			setBorder ( null );
		}

		public void setXY(int x, int y) {
			setBounds ( x, y, getWidth (), getHeight () );
		}

	}



	// TextField
	public static class MyLabel extends JLabel {

		public MyLabel ( String text ) {
			super ( text );

			init();

		}

		private void init () {

			setBounds ( new Rectangle ( 60, 25 ) );
			setFont ( Themes.VEDANA_12 );
			setForeground ( Themes.BLUE );
			setHorizontalAlignment ( JLabel.CENTER );

		}

		public void setXY(int x, int y) {
			setBounds ( x, y, getWidth (), getHeight () );
		}
	}

	// TextField
	public static class MyButton extends JButton {

		public MyButton ( String text ) {
			super ( text );

			init();

		}

		private void init () {

			setBounds ( new Rectangle ( 50, 25 ) );
			setFont ( Themes.VEDANA_12 );
			setForeground ( Themes.BLUE );
			setHorizontalAlignment ( JLabel.CENTER );
			setBackground ( Themes.GREY );
			setBorder ( null );
		}


		public void setXY(int x, int y) {
			setBounds ( x, y, getWidth (), getHeight () );
		}
	}

}
