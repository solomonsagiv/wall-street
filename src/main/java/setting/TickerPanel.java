package setting;

import gui.MyGuiComps;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class TickerPanel extends MyGuiComps.MyPanel {

    // Variables
    BASE_CLIENT_OBJECT client;

    MyGuiComps.MyLabel openLbl;
    MyGuiComps.MyLabel baseLbl;
    MyGuiComps.MyTextField openField;
    MyGuiComps.MyTextField baseField;

    // Constructor
    public TickerPanel( BASE_CLIENT_OBJECT client ) {
        this.client = client;
        initialize();
        initListeners();
    }

    private void initListeners() {
    }

    private void initialize() {

        // This
        setSize( 200, 125 );

        TitledBorder titledBorder = BorderFactory.createTitledBorder( "Ticker" );
        titledBorder.setTitleColor( Themes.BLUE_DARK );

        setBorder( titledBorder );


        // ----- Open ----- //
        // Lbl
        openLbl = new MyGuiComps.MyLabel( "Open" );
        openLbl.setXY( 10, 10 );
        openLbl.setHorizontalAlignment( JLabel.LEFT );
        openLbl.setFont( openLbl.getFont().deriveFont( 9f ) );
        openLbl.setLabelFor( openField );
        add( openLbl );

        // Field
        openField = new MyGuiComps.MyTextField( true );
        openField.setXY( 10, 30 );
        openField.setSize( 50, 20 );
        add( openField );

        // ----- Base ----- //
        // Lbl
        baseLbl = new MyGuiComps.MyLabel( "Base" );
        baseLbl.setXY( 65, 10 );
        baseLbl.setHorizontalAlignment( JLabel.LEFT );
        baseLbl.setFont( baseLbl.getFont().deriveFont( 9f ) );
        baseLbl.setLabelFor( baseField );
        add( baseLbl );

        // Field
        baseField = new MyGuiComps.MyTextField( true );
        baseField.setXY( 65, 30 );
        baseField.setSize( 50, 20 );
        add( baseField );
    }

}
