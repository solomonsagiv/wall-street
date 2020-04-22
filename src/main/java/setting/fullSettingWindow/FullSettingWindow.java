package setting.fullSettingWindow;

import dataBase.mySql.mySqlComps.TablesEnum;
import gui.MyGuiComps;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;
import setting.DataBasePanel;
import setting.TickerPanel;
import setting.optionsPanel.OptionsPanel;

import java.awt.*;

public class FullSettingWindow extends MyGuiComps.MyFrame {

    public static void main( String[] args ) {
        Spx.getInstance().getTablesHandler().getTable( TablesEnum.TWS_CONTRACTS ).load();
        FullSettingWindow settingWindow = new FullSettingWindow( "Setting", Spx.getInstance() );
    }

    // Races panel
    FullSettingOptionsPanel optionsPanel;
    FullSettingDataBasePanel dataBasePanel;

    public FullSettingWindow( String title, BASE_CLIENT_OBJECT client ) throws HeadlessException {
        super( title, client );
    }

    @Override
    public void onClose() {

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
        optionsPanel = new FullSettingOptionsPanel( client );
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
