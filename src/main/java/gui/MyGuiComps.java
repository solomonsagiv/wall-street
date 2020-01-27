package gui;

import locals.L;
import locals.Themes;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.text.DecimalFormat;

public class MyGuiComps {

    // ---------- JFrame ---------- //
    public static abstract class MyFrame extends JFrame {

        public MyFrame( String title ) throws HeadlessException {
            super( title );

            onClose();
            init( );
        }

        private void init() {

            setDefaultCloseOperation( DISPOSE_ON_CLOSE );
            setBackground( Themes.GREY_LIGHT );
            setVisible( true );
            setBounds( 100, 100, 400, 400 );
            getContentPane( ).setLayout( null );

        }

        public abstract void onClose();

    }


    // ---------- JPanel ---------- //
    public static class MyPanel extends JPanel {

        public MyPanel() {
            init( );
        }

        private void init() {

            setFont( Themes.VEDANA_12 );
            setBackground( Themes.GREY_LIGHT );
            setBorder( null );
            setLayout( null );
        }

        public void setXY( int x, int y ) {
            setBounds( x, y, getWidth( ), getHeight( ) );
        }
    }


    // ---------- JTextField ---------- //
    public static class MyTextField extends JTextField {

        public MyTextField( int columns ) {
            super( columns );

            init( );
        }

        private void init() {

            setBounds( new Rectangle( 60, 25 ) );
            setFont( Themes.VEDANA_12 );
            setForeground( Color.BLACK );
            setHorizontalAlignment( JTextField.CENTER );
            setBackground( Themes.GREY_VERY_LIGHT );
            setBorder( null );
        }

        public void setXY( int x, int y ) {
            setBounds( x, y, getWidth( ), getHeight( ) );
        }

        public void setText( double val, DecimalFormat format ) {
            if ( format != null ) {
                setText( format.format( val ) );
            } else {
                setText( L.str( val ) );
            }
        }

        public void colorForge( double val, DecimalFormat format ) {
            if ( val >= 0 ) {
                setForeground( Themes.GREEN );
            } else {
                setForeground( Themes.RED );
            }

            setText( format.format( val ) );
        }

        public void colorForge( int val ) {
            if ( val >= 0 ) {
                setForeground( Themes.GREEN );
            } else {
                setForeground( Themes.RED );
            }

            setText( L.str( val ));
        }

        public void colorBack( double val, DecimalFormat format ) {
            if ( val >= 0 ) {
                setBackground( Themes.GREEN );
            } else {
                setBackground( Themes.RED );
            }

            setText( format.format( val ) );
        }

    }


    // ---------- JLabel ---------- //
    public static class MyLabel extends JLabel {

        public MyLabel( String text ) {
            super( text );
            init( );
        }

        private void init() {

            setBounds( new Rectangle( 60, 25 ) );
            setFont( Themes.VEDANA_12 );
            setForeground( Themes.BLUE );
            setHorizontalAlignment( JLabel.CENTER );

        }

        public void setXY( int x, int y ) {
            setBounds( x, y, getWidth( ), getHeight( ) );
        }

        public void setText( double val, DecimalFormat format ) {
            if ( format != null ) {
                setText( format.format( val ) );
            } else {
                setText( L.str( val ) );
            }
        }

        public void colorForge( double val, DecimalFormat format ) {
            if ( val >= 0 ) {
                setForeground( Themes.GREEN );
            } else {
                setForeground( Themes.RED );
            }

            setText( format.format( val ) );
        }

        public void colorForge( int val ) {
            if ( val >= 0 ) {
                setForeground( Themes.GREEN );
            } else {
                setForeground( Themes.RED );
            }

            setText(L.coma( val ));
        }

        public void colorBack( double val, DecimalFormat format ) {
            if ( val >= 0 ) {
                setBackground( Themes.GREEN );
            } else {
                setBackground( Themes.RED );
            }

            setText( format.format( val ) );
        }
    }

    // ---------- JButton ---------- //
    public static class MyButton extends JButton {

        public MyButton( String text ) {
            super( text );

            init( );

        }

        private void init() {

            setBounds( new Rectangle( 70, 25 ) );
            setFont( Themes.VEDANA_12 );
            setForeground( Themes.BLUE );
            setFont( getFont().deriveFont( Font.BOLD ) );
            setHorizontalAlignment( JLabel.CENTER );
            setBackground( Themes.GREY_LIGHT );
            setBorder( null );

        }


        public void setXY( int x, int y ) {
            setBounds( x, y, getWidth( ), getHeight( ) );
        }
    }

}

abstract class AFrame extends JFrame {

    public AFrame( String title ) throws HeadlessException {
        super( title );
    }

    public abstract void onClose();
}