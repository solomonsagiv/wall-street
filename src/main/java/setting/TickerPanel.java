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

        // ----- Open ----- //
        // Label


    }

    public void addBorder( JTextField textField, String title ) {
        TitledBorder titledBorder = BorderFactory.createTitledBorder( title );
        titledBorder.setTitleColor( Themes.BLUE_DARK );

        textField.setBorder( titledBorder );
    }


}
