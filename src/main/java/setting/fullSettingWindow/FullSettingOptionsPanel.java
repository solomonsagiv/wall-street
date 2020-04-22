package setting.fullSettingWindow;

import gui.MyGuiComps;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;
import setting.FullSettingTwsPanel;
import setting.optionsPanel.FullSettingPropsPanel;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class FullSettingOptionsPanel extends MyGuiComps.MyPanel {

    static String INDEXS = "INDEXS", STOCKS = "STOCKS";
    static String WEEK = "WEEK", MONTH = "MONTH", QUARTER = "QUARTER", QUARTER_FAR = "QUARTER_FAR";

    // Variables
    BASE_CLIENT_OBJECT client;

    JComboBox clientsCombo;
    JComboBox optionsCombo;
    FullSettingPropsPanel fullSettingPropsPanel;
    FullSettingTwsPanel fullSettingTwsPanel;

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
        fullSettingPropsPanel = new FullSettingPropsPanel( client );
        fullSettingPropsPanel.setXY( 0, 20 );
        add(fullSettingPropsPanel);

        // Tws
        fullSettingTwsPanel = new FullSettingTwsPanel( client );
        fullSettingTwsPanel.setXY( fullSettingPropsPanel.getX( ) + fullSettingPropsPanel.getWidth( ) + 1, fullSettingPropsPanel.getY( ) );
        add(fullSettingTwsPanel);

        // Combo
        clientsCombo = new JComboBox( getOptionsArrayString( ) );
        clientsCombo.setBackground( Themes.BLUE );
        clientsCombo.setForeground( Themes.GREY_VERY_LIGHT );
        clientsCombo.setBounds( fullSettingTwsPanel.getX( ) + fullSettingTwsPanel.getWidth( ) + 5, fullSettingTwsPanel.getY( ), 120, 25 );
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