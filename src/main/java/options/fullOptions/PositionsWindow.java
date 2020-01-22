package options.fullOptions;

import gui.MyGuiComps;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.SpxCLIENTObject;
import threads.MyThread;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PositionsWindow extends MyGuiComps.MyFrame {


    // Variables
    BASE_CLIENT_OBJECT client;
    PositionPanel positionPanel;
    HeaderPanel headerPanel;
    ArrayList< PositionPanel > positionPanels;
    // Prop
    int row1 = 3;
    int row2 = 30;
    int col1 = 5;
    int col2 = 70;
    int col3 = 135;
    int col4 = 200;
    int col5 = 265;

    // COnstructor
    public PositionsWindow( BASE_CLIENT_OBJECT client ) {
        super( "Positions" );
        this.client = client;
        positionPanels = new ArrayList<>( );

        init( );
    }

    public static void main( String[] args ) {
        new PositionsWindow( SpxCLIENTObject.getInstance( ) );
    }

    public void appnedPanel( PositionCalculator.OptionPosition position ) {

        PositionPanel positionPanel = new PositionPanel( position, this );

        // Add to arraylist
        positionPanels.add( positionPanel );

        // Init again
        revalidate( );
        repaint( );
    }

    public void removePanel( PositionCalculator.OptionPosition positionToRemove ) {

        // Remove the panel
        for ( PositionPanel positionPanel : positionPanels ) {
            if ( positionPanel.position == positionToRemove ) {
                positionPanels.remove( positionToRemove );
            }
        }

        // Init again
        revalidate( );
        repaint( );
    }

    private void init() {

        // Headers
        headerPanel = new HeaderPanel( this );
        headerPanel.setXY( 0, 0 );
        add( headerPanel );

        // Panels
        initPanels( );

    }

    public void initPanels() {

        int y = 0;
        int marginBetweenPanels = 1;

        // For each position panel
        for ( PositionPanel positionpanel : positionPanels ) {

            // Set y
            positionpanel.setXY( 0, y );

            // Append to window
            add( positionpanel );

            // Increment y
            y += positionPanel.getHeight( ) + marginBetweenPanels;

        }
    }

    // ---------- Position panel ---------- //
    class PositionPanel extends MyGuiComps.MyPanel {

        // Variables
        PositionCalculator.OptionPosition position;

        // Labels
        MyGuiComps.MyLabel quantityLbl;
        MyGuiComps.MyLabel priceLbl;
        MyGuiComps.MyLabel vegaLbl;
        MyGuiComps.MyLabel deltaLbl;
        MyGuiComps.MyLabel pnlLbl;

        // Text fields
        MyGuiComps.MyTextField quantityField;
        MyGuiComps.MyTextField priceField;
        MyGuiComps.MyTextField vegaField;
        MyGuiComps.MyTextField deltaField;
        MyGuiComps.MyTextField pnlField;

        // Buttons
        MyGuiComps.MyButton cancelBtn;
        MyGuiComps.MyButton editBtn;

        // Constructor
        public PositionPanel( PositionCalculator.OptionPosition position, JFrame frame ) {

            // Super
            super( );

            // This
            this.position = position;

            // Init
            initialize( frame );

        }

        private void initialize( JFrame frame ) {
            // This panel
            setBounds( 0, 0, frame.getWidth( ), 50 );

            // Pnl
            pnlField = new MyGuiComps.MyTextField( 20 );
            pnlField.setXY( col1, row1 );
            add( pnlField );

            // Delta
            deltaField = new MyGuiComps.MyTextField( 20 );
            deltaField.setXY( col2, row1 );
            add( deltaField );

            // Vega
            vegaField = new MyGuiComps.MyTextField( 20 );
            vegaField.setXY( col3, row1 );
            add( vegaField );

            // Delta
            priceField = new MyGuiComps.MyTextField( 20 );
            priceField.setXY( col4, row1 );
            add( priceField );

            // Delta
            quantityField = new MyGuiComps.MyTextField( 20 );
            quantityField.setXY( col5, row1 );
            add( quantityField );
        }
    }


    // ---------- Header panel ---------- //
    class HeaderPanel extends MyGuiComps.MyPanel {

        // Labels
        MyGuiComps.MyLabel quantityLbl;
        MyGuiComps.MyLabel priceLbl;
        MyGuiComps.MyLabel vegaLbl;
        MyGuiComps.MyLabel deltaLbl;
        MyGuiComps.MyLabel pnlLbl;

        // Constructor
        public HeaderPanel( JFrame frame ) {

            // Super
            super( );

            // This
            setBackground( Themes.GREY );
            setBounds( new Rectangle( frame.getWidth( ), 30 ) );

            // Pnl
            pnlLbl = new MyGuiComps.MyLabel( "P/L" );
            pnlLbl.setXY( col1, row1 );
            add( pnlLbl );

            // Delta
            deltaLbl = new MyGuiComps.MyLabel( "Delta" );
            deltaLbl.setXY( col2, row1 );
            add( deltaLbl );

            // Vega
            vegaLbl = new MyGuiComps.MyLabel( "Vega" );
            vegaLbl.setXY( col3, row1 );
            add( vegaLbl );

            // Price
            priceLbl = new MyGuiComps.MyLabel( "Price" );
            priceLbl.setXY( col4, row1 );
            add( priceLbl );

            // Quantity
            quantityLbl = new MyGuiComps.MyLabel( "Quantity" );
            quantityLbl.setXY( col5, row1 );
            add( quantityLbl );
        }
    }


    public class Runner extends MyThread implements Runnable {


        // Constructor
        public Runner( BASE_CLIENT_OBJECT client ) {
            super( client );
        }

        @Override
        public void run() {
            try {
                // Sleep
                Thread.sleep( 2000 );

                update( );

            } catch ( InterruptedException e ) {
                getHandler( ).close( );
            }

        }


        private void update() {


        }

        @Override
        public void initRunnable() {
            setRunnable( this );
        }
    }
}



