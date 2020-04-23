package setting.clientSetting.optionsPanel;

import gui.MyGuiComps;
import locals.L;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExecutorsPanel extends MyGuiComps.MyPanel {

    // Variables
    BASE_CLIENT_OBJECT client;

    MyGuiComps.MyLabel opAvgLbl;
    MyGuiComps.MyTextField opAvgField;
    MyGuiComps.MyLabel futBidAskCounterLbl;
    MyGuiComps.MyTextField futBidAskCounterField;

    // Constructor
    public ExecutorsPanel( BASE_CLIENT_OBJECT client ) {
        this.client = client;
        initialize( );
        initListeners( );
    }

    private void initListeners() {

        // OP Avg
        opAvgField.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent actionEvent ) {
                try {
                    double opAvg = L.dbl( opAvgField.getText( ) );
                    OptionsPanel.options.setOpAvg( opAvg );
                } catch ( Exception e ) {
                    JOptionPane.showMessageDialog( null, e.getMessage( ) );
                }
            }
        } );

        // Fut bid ask counter
        futBidAskCounterField.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent actionEvent ) {
                try {
                    int counter = L.INT( futBidAskCounterField.getText( ) );
                    OptionsPanel.options.setFutureBidAskCounter( counter );
                } catch ( Exception e ) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog( null, e.getCause() );
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
        opAvgField.setFontSize( 9 );
        opAvgField.setSize( 50, 20 );
        add( opAvgField );

        // ----- Fut bid ask counter ----- //
        futBidAskCounterLbl = new MyGuiComps.MyLabel( "Fut B/A" );
        futBidAskCounterLbl.setXY( 70, 10 );
        futBidAskCounterLbl.setHorizontalAlignment( JLabel.LEFT );
        futBidAskCounterLbl.setFont( futBidAskCounterLbl.getFont( ).deriveFont( 9f ) );
        futBidAskCounterLbl.setLabelFor( futBidAskCounterLbl );
        add( futBidAskCounterLbl );

        // Field
        futBidAskCounterField = new MyGuiComps.MyTextField( );
        futBidAskCounterField.setXY( 70, 30 );
        futBidAskCounterField.setFontSize( 9 );
        futBidAskCounterField.setSize( 50, 20 );
        add( futBidAskCounterField );

    }


}