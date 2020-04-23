package setting.fullSettingWindow;

import gui.MyGuiComps;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;
import setting.clientSetting.TwsPanel;
import setting.clientSetting.optionsPanel.PropsPanel;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class FullSettingOptionsPanel extends MyGuiComps.MyPanel {

    static String INDEXS = "INDEXS", STOCKS = "STOCKS";
    static String WEEK = "WEEK", MONTH = "MONTH", QUARTER = "QUARTER", QUARTER_FAR = "QUARTER_FAR";

    // Variables
    BASE_CLIENT_OBJECT client;

    public static JComboBox clientsCombo;
    public static JComboBox optionsCombo;
    FullSettingOptionsProps propsPanel;

    // Constructor
    public FullSettingOptionsPanel( ) {
        initialize( );
        initListeners( );
    }

    private void initListeners() {
        // Combo
    }

    private void initialize() {

        // This
        setSize( 640, 150 );

        TitledBorder titledBorder = BorderFactory.createTitledBorder( "Options" );
        titledBorder.setTitleColor( Themes.BLUE_DARK );
        setBorder( titledBorder );

        // Props
        propsPanel = new FullSettingOptionsProps( client );
        propsPanel.setXY( 0, 20 );
        add( propsPanel );

//        // Tws
//        twsPanel = new TwsPanel( client );
//        twsPanel.setXY( propsPanel.getX( ) + propsPanel.getWidth( ) + 1, propsPanel.getY( ) );
//        add( twsPanel );

        // Combo
        clientsCombo = new JComboBox( getOptionsArrayString( ) );
        clientsCombo.setBackground( Themes.BLUE );
        clientsCombo.setForeground( Themes.GREY_VERY_LIGHT );
        clientsCombo.setBounds( propsPanel.getX( ) + propsPanel.getWidth( ) + 5, propsPanel.getY( ), 120, 25 );
        clientsCombo.setSelectedItem( STOCKS );
        add(clientsCombo);

        // Options combo
        optionsCombo = new JComboBox( new String[]{"WEEK", "MONTH", "QUARTER", "QUARTER_FAR"});
        optionsCombo.setBackground( Themes.BLUE );
        optionsCombo.setForeground( Themes.GREY_VERY_LIGHT );
        optionsCombo.setBounds( clientsCombo.getX( ) + clientsCombo.getWidth( ) + 5, clientsCombo.getY( ), 120, 25 );
        add( optionsCombo );
    }

    public String[] getOptionsArrayString() {
        return new String[]{ "INDEXS", "STOCKS", "ALL" };
    }

}