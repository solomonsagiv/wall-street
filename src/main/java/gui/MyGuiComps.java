package gui;

import locals.L;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class MyGuiComps {

    // ---------- JFrame ---------- //
    public static abstract class MyFrame extends JFrame {

        protected BASE_CLIENT_OBJECT client;

        public MyFrame( String title ) throws HeadlessException {
            super( title );
            init( );
            initialize( );
            packAndFinish( );
            onClose( );
        }

        public MyFrame( String title, BASE_CLIENT_OBJECT client ) throws HeadlessException {
            super( title );
            this.client = client;
            init( );
            initialize( );
            packAndFinish( );
            onClose( );
        }

        private void packAndFinish() {
            pack( );
            setVisible( true );
        }

        private void init() {
            setDefaultCloseOperation( DISPOSE_ON_CLOSE );
            setBackground( Themes.GREY_LIGHT );
            getContentPane( ).setLayout( null );
            setLayout( null );
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

        public void setSize( int width, int height ) {
            setPreferredSize( new Dimension( width, height ) );
        }

        public abstract void onClose();

        public abstract void initListeners();

        public abstract void initialize();
    }


    // ---------- JPanel ---------- //
    public static class MyPanel extends JPanel {

        public MyPanel() {
            init( );
        }

        protected void init() {
            setFont( Themes.VEDANA_12 );
            setBackground( Themes.GREY_LIGHT );
            setBorder( null );
            setLayout( null );
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

        public MyTextField() {
            super( );
            init( );
        }

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
//            setEnabled(false);
        }

        public void setFontSize( int size ) {
            setFont( getFont( ).deriveFont( ( float ) size ) );
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
            setFont( getFont( ).deriveFont( Font.BOLD ) );

            if ( val >= 0 ) {
                setBackground( Themes.GREEN );
            } else {
                setBackground( Themes.RED );
            }

            setText( format.format( val ) );
        }

        public void colorBack( double val, DecimalFormat format, String sign ) {

            setForeground( Color.WHITE );
            setFont( getFont( ).deriveFont( Font.BOLD ) );

            if ( val >= 0 ) {
                setBackground( Themes.GREEN );
            } else {
                setBackground( Themes.RED );
            }

            setText( format.format( val ) + sign );
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
            setVerticalAlignment( JLabel.CENTER );

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
            setWidth( 80 );
            setHeight( 25 );
            setFont( Themes.VEDANA_12 );
            setForeground( Themes.BLUE );
            setHorizontalAlignment( JLabel.CENTER );
            setBackground( Themes.GREY_LIGHT );
            setOpaque( true );
            addMouseListener( MyListeners.onOverMyButton( this ) );
        }

        public void complete() {
            Color backGroundColor = getBackground( );
            setBackground( Themes.GREEN );
            try {
                Thread.sleep( 1000 );
            } catch ( InterruptedException e ) {
                e.printStackTrace( );
            }
            setBackground( backGroundColor );

        }

        public void setWidth( int width ) {
            setBounds( getX( ), getY( ), width, getHeight( ) );
        }

        public void setHeight( int height ) {
            setBounds( getX( ), getY( ), getWidth( ), height );
        }

        public void setSize( int width, int height ) {
            setPreferredSize( new Dimension( width, height ) );
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
            fields = new Field[ rows ][ cols ];
            this.panelMinDimension = panelMinDimension;
            this.fieldsMinDimension = fieldsMinDimension;

            setLayout( new GridLayout( rows, cols ) );
            setMinimumSize( new Dimension( panelMinDimension ) );
            setPreferredSize( new Dimension( panelMinDimension ) );
            setBackground( Themes.GREY_LIGHT );
            fillBoard( );
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

            public Field( Dimension minDimension ) {
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