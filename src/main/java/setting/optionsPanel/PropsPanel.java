package setting.optionsPanel;

import dataBase.mySql.myTables.TablesEnum;
import gui.MyGuiComps;
import locals.L;
import locals.Themes;
import options.Options;
import serverObjects.BASE_CLIENT_OBJECT;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PropsPanel extends MyGuiComps.MyPanel {

    // Variables
    BASE_CLIENT_OBJECT client;
    Options options;

    MyGuiComps.MyLabel interestLbl;
    MyGuiComps.MyTextField interestField;
    MyGuiComps.MyLabel divLbl;
    MyGuiComps.MyTextField divField;
    MyGuiComps.MyLabel daystLbl;
    MyGuiComps.MyTextField daysField;
    MyGuiComps.MyButton submitBtn;

    // Constructor
    public PropsPanel( BASE_CLIENT_OBJECT client ) {
        this.client = client;
        options = OptionsPanel.options;
        initialize( );
        initListeners( );
    }

    private void initListeners() {

        // Submit
        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent actionEvent ) {
                // Interest
                if ( !interestField.getText().isEmpty() ) {
                    try {
                        double d = L.dbl( interestField.getText() );
                        OptionsPanel.options.setInterestWithCalc( d );
                    } catch ( Exception e ) {
                        JOptionPane.showMessageDialog( null, e.getMessage() );
                        e.printStackTrace();
                    }
                }
                // Div
                if ( !divField.getText().isEmpty() ) {
                    try {
                        double d = L.dbl( divField.getText() );
                        OptionsPanel.options.setDevidend( d );
                    } catch ( Exception e ) {
                        JOptionPane.showMessageDialog( null, e.getMessage() );
                        e.printStackTrace();
                    }
                }
                // Days
                if ( !daysField.getText().isEmpty() ) {
                    try {
                        double d = L.dbl( daysField.getText() );
                        OptionsPanel.options.setDaysToExp( d );
                    } catch ( Exception e ) {
                        JOptionPane.showMessageDialog( null, e.getMessage() );
                        e.printStackTrace();
                    }
                }

                // Update to DB
                client.getTablesHandler( ).getTable( TablesEnum.STATUS ).update( );
            }
        });
    }

    private void initialize() {

        // This
        setSize( 180, 125 );
        TitledBorder titledBorder = BorderFactory.createTitledBorder( "Props" );
        titledBorder.setTitleColor( Themes.BLUE_DARK );
        setBorder( titledBorder );

        // ----- Interest ----- //
        interestLbl = new MyGuiComps.MyLabel( "Interest" );
        interestLbl.setXY( 10, 10 );
        interestLbl.setHorizontalAlignment( JLabel.LEFT );
        interestLbl.setFont( interestLbl.getFont( ).deriveFont( 9f ) );
        interestLbl.setLabelFor( interestLbl );
        add( interestLbl );

        // Field
        interestField = new MyGuiComps.MyTextField( true );
        interestField.setXY( 10, 30 );
        interestField.setFontSize(9);
        interestField.setSize( 50, 20 );
        add( interestField );

        // ----- Div ----- //
        divLbl = new MyGuiComps.MyLabel( "Div" );
        divLbl.setXY( 65, 10 );
        divLbl.setHorizontalAlignment( JLabel.LEFT );
        divLbl.setFont( divLbl.getFont( ).deriveFont( 9f ) );
        divLbl.setLabelFor( divLbl );
        add( divLbl );

        // Field
        divField = new MyGuiComps.MyTextField( true );
        divField.setXY( 65, 30 );
        divField.setSize( 50, 20 );
        divField.setFontSize(9);
        add( divField );

        // ----- Days ----- //
        daystLbl = new MyGuiComps.MyLabel( "Days" );
        daystLbl.setXY( 120, 10 );
        daystLbl.setHorizontalAlignment( JLabel.LEFT );
        daystLbl.setFont( daystLbl.getFont( ).deriveFont( 9f ) );
        daystLbl.setLabelFor( daystLbl );
        add( daystLbl );

        // Field
        daysField = new MyGuiComps.MyTextField( true );
        daysField.setXY( 120, 30 );
        daysField.setSize( 50, 20 );
        daysField.setFontSize(9);
        add( daysField );

        // Submit
        submitBtn = new MyGuiComps.MyButton( "Submit" );
        submitBtn.setXY( 10, 90 );
        submitBtn.setWidth( 70 );
        submitBtn.setFont( submitBtn.getFont().deriveFont( 9f ) );
        submitBtn.setBackground( Themes.BLUE );
        submitBtn.setForeground( Themes.GREY_VERY_LIGHT );
        add( submitBtn );
    }


}

