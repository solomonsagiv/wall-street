package setting;

import gui.MyGuiComps;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

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

        // ----- Open ----- //
        // Field
        openField = new MyGuiComps.MyTextField();
        openField.setXY( 15, 50 );
        openField.setEnabled( true );
        openField.setSize( 50, 25 );
        addBorder( openField, "Open" );
        add( openField );

    }

    public void addBorder( JTextField textField, String title ) {
        TitledBorder titledBorder = BorderFactory.createTitledBorder( title );
        titledBorder.setTitleColor( Themes.BLUE_DARK );

        textField.setBorder( titledBorder );
    }


}
