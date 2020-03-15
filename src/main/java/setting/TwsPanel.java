package setting;

import gui.MyGuiComps;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class TwsPanel extends MyGuiComps.MyPanel {

    // Variables
    BASE_CLIENT_OBJECT client;

    MyGuiComps.MyLabel dateLbl;
    MyGuiComps.MyTextField dateField;

    // Constructor
    public TwsPanel(BASE_CLIENT_OBJECT client) {
        this.client = client;
        initialize();
        initListeners();
    }

    private void initListeners() {
    }

    private void initialize() {

        // This
        setSize( 150, 125 );
        TitledBorder titledBorder = new TitledBorder( "Tws" );
        titledBorder.setTitleColor( Themes.BLUE_DARK );
        setBorder( titledBorder );

        // ----- Date ----- //
        // Lbl
        dateLbl = new MyGuiComps.MyLabel( "Date" );
        dateLbl.setXY( 10, 10 );
        dateLbl.setHorizontalAlignment( JLabel.LEFT );
        dateLbl.setFont( dateLbl.getFont( ).deriveFont( 9f ) );
        dateLbl.setLabelFor( dateLbl );
        add( dateLbl );

        // Field
        dateField = new MyGuiComps.MyTextField( true );
        dateField.setXY( 10, 30 );
        dateField.setFontSize(9);
        dateField.setSize( 70, 20 );
        add( dateField );

    }


}
