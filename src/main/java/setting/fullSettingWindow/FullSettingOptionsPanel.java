package setting.fullSettingWindow;

import gui.MyGuiComps;
import locals.Themes;
import options.Options;
import options.OptionsEnum;
import serverObjects.BASE_CLIENT_OBJECT;
import setting.TwsPanel;
import setting.optionsPanel.PropsPanel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FullSettingOptionsPanel extends MyGuiComps.MyPanel {

    static String INDEXS = "INDEXS", STOCKS = "STOCKS";

    // Variables
    BASE_CLIENT_OBJECT client;
    public static Options options;

    JComboBox comboBox;
    PropsPanel propsPanel;
    TwsPanel twsPanel;

    // Constructor
    public FullSettingOptionsPanel( BASE_CLIENT_OBJECT client ) {
        this.client = client;
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
        propsPanel = new PropsPanel( client );
        propsPanel.setXY( 0, 20 );
        add( propsPanel );

        // Tws
        twsPanel = new TwsPanel( client );
        twsPanel.setXY( propsPanel.getX( ) + propsPanel.getWidth( ) + 1, propsPanel.getY( ) );
        add( twsPanel );

        // Combo
        comboBox = new JComboBox( getOptionsArrayString( ) );
        comboBox.setBackground( Themes.BLUE );
        comboBox.setForeground( Themes.GREY_VERY_LIGHT );
        comboBox.setBounds( twsPanel.getX( ) + twsPanel.getWidth( ) + 5, twsPanel.getY( ), 120, 25 );
        add( comboBox );
    }

    public String[] getOptionsArrayString() {
        return new String[]{ "INDEXS", "STOCKS", "ALL" };
    }

}