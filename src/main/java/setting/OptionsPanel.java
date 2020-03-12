package setting;

import gui.MyGuiComps;
import locals.Themes;
import options.Options;
import serverObjects.BASE_CLIENT_OBJECT;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class OptionsPanel extends MyGuiComps.MyPanel {

    // Variables
    BASE_CLIENT_OBJECT client;

    JComboBox comboBox;
    RacesPanel racesPanel;
    PropsPanel propsPanel;
    ExecutorsPanel executorsPanel;

    // Constructor
    public OptionsPanel( BASE_CLIENT_OBJECT client ) {
        this.client = client;
        initialize( );
        initListeners( );
    }

    private void initListeners() {
    }

    private void initialize() {

        // This
        setSize( 640, 300 );

        TitledBorder titledBorder = BorderFactory.createTitledBorder( "Options" );
        titledBorder.setTitleColor( Themes.BLUE_DARK );
        setBorder( titledBorder );

        // Races
        racesPanel = new RacesPanel( client );
        racesPanel.setXY( 5, 20 );
        add( racesPanel );

        // Props
        propsPanel = new PropsPanel( client );
        propsPanel.setXY( racesPanel.getX( ) + racesPanel.getWidth( ) + 1, 20 );
        add( propsPanel );

        // Executors
        executorsPanel = new ExecutorsPanel( client );
        executorsPanel.setXY( propsPanel.getX() + propsPanel.getWidth() + 1, propsPanel.getY() );
        add( executorsPanel );

        // Combo
        comboBox = new JComboBox( getOptionsArrayString( ) );
        comboBox.setBackground( Themes.BLUE );
        comboBox.setForeground( Themes.GREY_VERY_LIGHT );
        comboBox.setBounds( executorsPanel.getX() + executorsPanel.getWidth() + 5, executorsPanel.getY(), 80, 25 );
        add( comboBox );

    }

    public String[] getOptionsArrayString() {
        String[] optionsTypes = new String[ client.getOptionsHandler( ).getOptionsList( ).size( ) ];
        int i = 0;
        for ( Options options : client.getOptionsHandler( ).getOptionsList( ) ) {
            optionsTypes[ i ] = options.getType( ).toString( );
            i++;
        }
        return optionsTypes;
    }


}


class PropsPanel extends MyGuiComps.MyPanel {

    // Variables
    BASE_CLIENT_OBJECT client;

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
        initialize( );
        initListeners( );
    }

    private void initListeners() {
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


class ExecutorsPanel extends MyGuiComps.MyPanel {

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
        testField.setSize( 50, 20 );
        add( testField );

    }


}