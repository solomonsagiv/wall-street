package options.fullOptions;

import arik.Arik;
import gui.MyGuiComps;
import locals.L;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;
import threads.MyThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class PositionsWindow extends MyGuiComps.MyFrame {

    JFrame frame;

    // Variables
    BASE_CLIENT_OBJECT client;
    HeaderPanel headerPanel;
    MyGuiComps.MyPanel positionsAreaPanel;

    ArrayList< PositionCalculator.OptionPosition > positions;
    ArrayList< PositionPanel > positionPanels;
    int prePositionsSize = 0;

    Runner runner;

    // Prop
    int row1 = 3;
    int row2 = 30;

    int col1 = 5;
    int col2 = 70;
    int col3 = 135;
    int col4 = 200;
    int col5 = 265;
    int col6 = 330;
    int col7 = 395;
    int col8 = 460;

    // Constructor
    public PositionsWindow( BASE_CLIENT_OBJECT client, ArrayList< PositionCalculator.OptionPosition > positions ) {
        super( "Positions" );
        this.client = client;
        this.positions = positions;

        positionPanels = new ArrayList<>( );

        init( );

        runner = new Runner( client );
        runner.getHandler( ).start( );

        frame = this;
    }

    private void init() {

        setBounds( 100, 100, 560, 200 );

        // Headers
        headerPanel = new HeaderPanel( this );
        headerPanel.setLayout( null );
        headerPanel.setXY( 0, 0 );
        add( headerPanel );

        // Positions area panel
        positionsAreaPanel = new MyGuiComps.MyPanel( );
        positionsAreaPanel.setLayout( null );
        positionsAreaPanel.setBackground( Themes.GREY_VERY_LIGHT );
        positionsAreaPanel.setBounds( 0, headerPanel.getHeight( ) + 1, getWidth( ), getHeight( ) - headerPanel.getHeight( ) );
        add( positionsAreaPanel );

    }

    @Override
    public void onClose() {
        addWindowListener( new WindowAdapter( ) {
            @Override
            public void windowClosed( WindowEvent e ) {
                super.windowClosed( e );
                runner.getHandler( ).close( );
            }
        } );
    }

    public void initPanels( MyGuiComps.MyPanel panel ) {
        try {
            panel.removeAll( );

            int y = 1;
            int marginBetweenPanels = 1;

            positionPanels = new ArrayList<>( );

            // For each position panel
            for ( PositionCalculator.OptionPosition position : positions ) {
                PositionPanel positionPanel = new PositionPanel( position, panel );

                positionPanels.add( positionPanel );

                // Set y
                positionPanel.setXY( 0, y );

                // Append to window
                panel.add( positionPanel );

                // Increment y
                y += positionPanel.getHeight( ) + marginBetweenPanels;

            }

            panel.revalidate( );
            panel.repaint( );
        } catch ( Exception e ) {
            e.printStackTrace( );
            Arik.getInstance( ).sendErrorMessage( e );
        }

    }

    private boolean isPositionsChanges() {
        return prePositionsSize != positions.size( );
    }

    // ---------- Position panel ---------- //
    class PositionPanel extends MyGuiComps.MyPanel {

        // Variables
        PositionCalculator.OptionPosition position;

        // Text fields
        MyGuiComps.MyLabel quantityLbl;
        MyGuiComps.MyLabel priceLbl;
        MyGuiComps.MyLabel vegaLbl;
        MyGuiComps.MyLabel deltaLbl;
        MyGuiComps.MyLabel pnlLbl;
        MyGuiComps.MyLabel nameLbl;

        // Buttons
        MyGuiComps.MyButton cancelBtn;
        MyGuiComps.MyButton editBtn;
        MyGuiComps.MyPanel panel;

        // Constructor
        public PositionPanel( PositionCalculator.OptionPosition position, MyGuiComps.MyPanel panel ) {

            // Super
            super( );
            this.panel = panel;

            // This
            this.position = position;

            // Init
            initialize( );

            // Init listeners
            initListener( );
        }

        private void initListener() {

            // Edit
            editBtn.addActionListener( new ActionListener( ) {
                @Override
                public void actionPerformed( ActionEvent actionEvent ) {
                    new EditPositionWindow( "Edit", position, frame );
                }
            } );

            // Cancel
            cancelBtn.addActionListener( new ActionListener( ) {
                @Override
                public void actionPerformed( ActionEvent actionEvent ) {
                    positions.remove( position );
                }
            } );

        }

        private void initialize() {
            // This panel
            setBounds( 0, 0, panel.getWidth( ), 33 );
            setLayout( null );

            // Pnl
            pnlLbl = new MyGuiComps.MyLabel( "" );
            pnlLbl.setXY( col1, row1 );
            add( pnlLbl );

            // Delta
            deltaLbl = new MyGuiComps.MyLabel( "" );
            deltaLbl.setXY( col2, row1 );
            add( deltaLbl );

            // Vega
            vegaLbl = new MyGuiComps.MyLabel( "" );
            vegaLbl.setXY( col3, row1 );
            add( vegaLbl );

            // Delta
            priceLbl = new MyGuiComps.MyLabel( "" );
            priceLbl.setXY( col4, row1 );
            add( priceLbl );

            // Delta
            quantityLbl = new MyGuiComps.MyLabel( "" );
            quantityLbl.setXY( col5, row1 );
            add( quantityLbl );

            // Name
            nameLbl = new MyGuiComps.MyLabel( "" );
            nameLbl.setXY( col6, row1 );
            add( nameLbl );

            // Edit btn
            editBtn = new MyGuiComps.MyButton( "Edit" );
            editBtn.setXY( col7, row1 );
            editBtn.setForeground( Themes.BLUE_LIGHT_2 );
            add( editBtn );

            // Cancel btn
            cancelBtn = new MyGuiComps.MyButton( "Cancel" );
            cancelBtn.setXY( col8, row1 );
            cancelBtn.setForeground( Themes.RED );
            add( cancelBtn );
        }

    }

    // ---------- Header panel ---------- //
    class HeaderPanel extends MyGuiComps.MyPanel {

        // Labels
        MyGuiComps.MyLabel quantityField;
        MyGuiComps.MyLabel priceField;
        MyGuiComps.MyLabel vegaField;
        MyGuiComps.MyLabel deltaField;
        MyGuiComps.MyLabel pnlField;
        MyGuiComps.MyLabel nameLbl;

        // Constructor
        public HeaderPanel( JFrame frame ) {

            // Super
            super( );

            // This
            setBackground( Themes.GREY_LIGHT );
            setBounds( new Rectangle( frame.getWidth( ), 30 ) );

            // Pnl
            pnlField = new MyGuiComps.MyLabel( "P/L" );
            pnlField.setXY( col1, row1 );
            pnlField.setFont( pnlField.getFont( ).deriveFont( Font.BOLD ) );
            add( pnlField );

            // Delta
            deltaField = new MyGuiComps.MyLabel( "Delta" );
            deltaField.setFont( deltaField.getFont( ).deriveFont( Font.BOLD ) );
            deltaField.setXY( col2, row1 );
            add( deltaField );

            // Vega
            vegaField = new MyGuiComps.MyLabel( "Vega" );
            vegaField.setFont( vegaField.getFont( ).deriveFont( Font.BOLD ) );
            vegaField.setXY( col3, row1 );
            add( vegaField );

            // Price
            priceField = new MyGuiComps.MyLabel( "Price" );
            priceField.setFont( priceField.getFont( ).deriveFont( Font.BOLD ) );
            priceField.setXY( col4, row1 );
            add( priceField );

            // Quantity
            quantityField = new MyGuiComps.MyLabel( "Quantity" );
            quantityField.setFont( quantityField.getFont( ).deriveFont( Font.BOLD ) );
            quantityField.setXY( col5, row1 );
            add( quantityField );

            // Name
            nameLbl = new MyGuiComps.MyLabel( "Option" );
            nameLbl.setXY( col6, row1 );
            nameLbl.setFont( nameLbl.getFont( ).deriveFont( Font.BOLD ) );
            add( nameLbl );

        }
    }


    public class Runner extends MyThread implements Runnable {

        // Constructor
        public Runner( BASE_CLIENT_OBJECT client ) {
            super( client );
            setName( "PositionsWindow" );
        }

        @Override
        public void run() {
            while ( isRun( ) ) {
                try {
                    // Sleep
                    Thread.sleep( 500 );

                    // Update the panels
                    update( );

                    // Update text
                    updateText( );

                } catch ( InterruptedException e ) {
                    break;
                }
            }
        }

        private void updateText() {

            try {

                PositionCalculator.OptionPosition position;

                if ( positionPanels.size( ) > 0 ) {

                    for ( PositionPanel panel : positionPanels ) {
                        position = panel.position;

                        panel.priceLbl.setText( L.format10( position.getPrice( ) ) );
                        panel.deltaLbl.colorForge( ( int ) position.getDelta( ) );
                        panel.vegaLbl.colorForge( ( int ) position.getVega( ) );
                        panel.quantityLbl.colorForge( position.getPos( ) );
                        panel.pnlLbl.colorForge( ( int ) position.getPnl( ) );
                        panel.nameLbl.setText( position.getOption( ).getIntName( ).toUpperCase( ) );

                    }
                }
            } catch ( Exception e ) {
                e.printStackTrace( );
            }

        }

        private void update() {

            try {
                if ( isPositionsChanges( ) ) {

                    initPanels( positionsAreaPanel );

                    prePositionsSize = positions.size( );
                }
            } catch ( Exception e ) {
                e.printStackTrace( );
            }
        }

        @Override
        public void initRunnable() {
            setRunnable( this );
        }
    }
}
