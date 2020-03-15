package setting.optionsPanel;

import gui.MyGuiComps;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class ExecutorsPanel extends MyGuiComps.MyPanel {

    // Variables
    BASE_CLIENT_OBJECT client;

    MyGuiComps.MyLabel testLbl;
    MyGuiComps.MyTextField testField;

    // Constructor
    public ExecutorsPanel( BASE_CLIENT_OBJECT client ) {
        this.client = client;
        initialize( );
        initListeners( );
    }

    private void initListeners() {
    }

    private void initialize() {

        // This
        setSize( 160, 125 );
        TitledBorder titledBorder = BorderFactory.createTitledBorder( "Props" );
        titledBorder.setTitleColor( Themes.BLUE_DARK );
        setBorder( titledBorder );

        // ----- Interest ----- //
        testLbl = new MyGuiComps.MyLabel( "Test" );
        testLbl.setXY( 10, 10 );
        testLbl.setHorizontalAlignment( JLabel.LEFT );
        testLbl.setFont( testLbl.getFont( ).deriveFont( 9f ) );
        testLbl.setLabelFor( testLbl );
        add( testLbl );

        // Field
        testField = new MyGuiComps.MyTextField( true );
        testField.setXY( 10, 30 );
        testField.setFontSize(9);
        testField.setSize( 50, 20 );
        add( testField );

    }


}