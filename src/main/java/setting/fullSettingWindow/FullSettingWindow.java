package setting.fullSettingWindow;

import gui.MyGuiComps;

import java.awt.*;

public class FullSettingWindow extends MyGuiComps.MyFrame {

    public static void main( String[] args ) {
        FullSettingWindow settingWindow = new FullSettingWindow( "Setting" );
    }

    // Races panel
    FullSettingOptionsPanel optionsPanel;
    FullSettingDataBasePanel dataBasePanel;

    public FullSettingWindow( String title ) throws HeadlessException {
        super( title );
    }

    @Override
    public void initOnClose() {

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initialize() {

        // This
        setXY( 200, 200 );
        setLayout( null );
        setSize( 800, 450 );
        setResizable( false );

        // Options
        optionsPanel = new FullSettingOptionsPanel( );
        optionsPanel.setXY( 0, 0 );
        optionsPanel.setSize( ( int ) getPreferredSize().getWidth(), optionsPanel.getHeight() );
        add( optionsPanel );

        // Data base
        dataBasePanel = new FullSettingDataBasePanel( client );
        dataBasePanel.setXY( 0, optionsPanel.getY() + optionsPanel.getHeight() + 1 );
        dataBasePanel.setSize(  ( int ) getPreferredSize().getWidth(), 150  );
        add( dataBasePanel );

    }

}
