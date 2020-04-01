package setting.optionsPanel;

import gui.MyGuiComps;
import locals.L;
import locals.Themes;
import options.Options;
import serverObjects.BASE_CLIENT_OBJECT;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExecutorsPanel extends MyGuiComps.MyPanel {

    // Variables
    BASE_CLIENT_OBJECT client;
    Options options;

    MyGuiComps.MyLabel opAvgLbl;
    MyGuiComps.MyTextField opAvgField;

    // Constructor
    public ExecutorsPanel( BASE_CLIENT_OBJECT client, Options options ) {
        this.client = client;
        this.options = options;
        initialize( );
        initListeners( );
    }

    private void initListeners() {

        // OP Avg
        opAvgField.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent actionEvent ) {
                try {
                    double  opAvg = L.dbl( opAvgField.getText() );
                    options.setOpAvg( opAvg );
                } catch ( Exception e ) {
                    JOptionPane.showMessageDialog( null, e.getMessage() );
                }
            }
        } );

    }

    private void initialize() {

        // This
        setSize( 160, 125 );
        TitledBorder titledBorder = BorderFactory.createTitledBorder( "Props" );
        titledBorder.setTitleColor( Themes.BLUE_DARK );
        setBorder( titledBorder );

        // ----- Interest ----- //
        opAvgLbl = new MyGuiComps.MyLabel( "O/P Avg" );
        opAvgLbl.setXY( 10, 10 );
        opAvgLbl.setHorizontalAlignment( JLabel.LEFT );
        opAvgLbl.setFont( opAvgLbl.getFont( ).deriveFont( 9f ) );
        opAvgLbl.setLabelFor( opAvgLbl );
        add( opAvgLbl );

        // Field
        opAvgField = new MyGuiComps.MyTextField( );
        opAvgField.setXY( 10, 30 );
        opAvgField.setFontSize(9);
        opAvgField.setSize( 50, 20 );
        add( opAvgField );

    }


}