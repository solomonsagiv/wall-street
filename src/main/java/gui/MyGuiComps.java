package gui;

import api.LayOutTest;
import locals.L;
import locals.Themes;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class MyGuiComps {

    // ---------- JFrame ---------- //
    public static abstract class MyFrame extends JFrame {

        public MyFrame( String title ) throws HeadlessException {
            super( title );

            onClose( );
            init( );
        }

        private void init() {

            setDefaultCloseOperation( DISPOSE_ON_CLOSE );
            setBackground( Themes.GREY_LIGHT );
            pack();
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
//            setLayout( null );
        }

        public void setXY( int x, int y ) {
            setBounds( x, y, getWidth( ), getHeight( ) );
        }

        public void setWidth( int width ) {
            setBounds( getX( ), getY( ), width, getHeight( ) );
        }

        public void setHeight( int height ) {
            setBounds( getX( ), getY( ), getWidth( ), height );
        }
    }


    // ---------- JTextField ---------- //
    public static class MyTextField extends JTextField {

        public MyTextField( int columns ) {
            super( columns );

            init( );
        }

        public void setWidth( int width ) {
            setBounds( getX( ), getY( ), width, getHeight( ) );
        }

        public void setHeight( int height ) {
            setBounds( getX( ), getY( ), getWidth( ), height );
        }

        private void init() {

            setBounds( new Rectangle( 65, 25 ) );
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

            setText( L.str( val ) );
        }

        public void colorBack( double val, DecimalFormat format ) {

            setForeground( Color.WHITE );
            setFont( getFont().deriveFont( Font.BOLD ) );

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

        public void setWidth( int width ) {
            setBounds( getX( ), getY( ), width, getHeight( ) );
        }

        public void setHeight( int height ) {
            setBounds( getX( ), getY( ), getWidth( ), height );
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

            setText( L.coma( val ) );
        }


        public void colorForge( int val, Color green ) {
            if ( val >= 0 ) {
                setForeground( green );
            } else {
                setForeground( Themes.RED );
            }

            setText( L.coma( val ) );
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
            setFont( getFont( ).deriveFont( Font.BOLD ) );
            setHorizontalAlignment( JLabel.CENTER );
            setBackground( Themes.GREY_LIGHT );
            setBorder( null );

        }


        public void setXY( int x, int y ) {
            setBounds( x, y, getWidth( ), getHeight( ) );
        }
    }


    // ---------- GridPanel ---------- //
    public static class MyBoardPanel extends JPanel {

        public Field[][] fields;

        int rows, cols;
        Dimension panelMinDimension, fieldsMinDimension;

        public MyBoardPanel( int rows, int cols, Dimension panelMinDimension, Dimension fieldsMinDimension ) {
            this.rows = rows;
            this.cols = cols;
            fields = new Field[rows][cols];
            this.panelMinDimension = panelMinDimension;
            this.fieldsMinDimension = fieldsMinDimension;

            setLayout( new GridLayout( rows, cols ) );
            setMinimumSize( new Dimension( panelMinDimension ) );
            setPreferredSize( new Dimension( panelMinDimension ) );
            setBackground( Themes.GREY_LIGHT );
            fillBoard();
        }

        private void fillBoard() {
            for ( int i = 0; i < rows; ++i ) {
                for ( int j = 0; j < cols; ++j ) {
                    fields[ i ][ j ] = new Field( fieldsMinDimension );
                    add( fields[ i ][ j ] );
                }
            }
        }

        public void setLabel( JLabel label, int row, int col ) {
            fields[ row ][ col ].add( label );
        }

        class Field extends JPanel {

            public Field(Dimension minDimension ) {
                setMinimumSize( minDimension );
                setPreferredSize( minDimension );
            }

        }
    }



}




abstract class AFrame extends JFrame {

    public AFrame( String title ) throws HeadlessException {
        super( title );
    }

    public abstract void onClose();
}