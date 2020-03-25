package setting;

import gui.MyGuiComps;
import locals.L;
import locals.Themes;
import options.Options;
import serverObjects.BASE_CLIENT_OBJECT;
import setting.optionsPanel.OptionsPanel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

public class TwsPanel extends MyGuiComps.MyPanel {

    // Variables
    BASE_CLIENT_OBJECT client;
    Options options;

    MyGuiComps.MyLabel dateLbl;
    MyGuiComps.MyTextField dateField;

    // Constructor
    public TwsPanel(BASE_CLIENT_OBJECT client) {
        this.client = client;
        options = OptionsPanel.options;
        initialize();
        initListeners();
    }

    private void initListeners() {

        // Date
        dateField.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent actionEvent ) {
                // Interest
                if ( !dateField.getText().isEmpty() ) {
                    try {
                        String s = dateField.getText();
                        options.getTwsContract().lastTradeDateOrContractMonth( s );
                    } catch ( Exception e ) {}
                }
            }
        } );


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
        dateField = new MyGuiComps.MyTextField( );
        dateField.setXY( 10, 30 );
        dateField.setFontSize(9);
        dateField.setSize( 70, 20 );
        add( dateField );

    }


}
