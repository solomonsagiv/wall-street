package setting;

import gui.MyGuiComps;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;
import java.awt.*;

public class SettingWindow extends MyGuiComps.MyFrame {

    public static void main( String[] args ) {
        SettingWindow settingWindow = new SettingWindow( "Setting", Spx.getInstance() );
    }

    // Variables
    BASE_CLIENT_OBJECT client;

    // Races panel
    RacesPanel racesPanel;
    TickerPanel tickerPanel;

    // Constructor
    public SettingWindow( String title ) throws HeadlessException {
        super( title );
    }

    public SettingWindow( String title, BASE_CLIENT_OBJECT client ) throws HeadlessException {
        super( title );
        this.client = client;
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
        setSize( 600, 800 );

        // Races
        racesPanel = new RacesPanel( client );
        racesPanel.setXY( 10, 10 );
        add( racesPanel );

        // Ticker
        tickerPanel = new TickerPanel( client );
        tickerPanel.setXY( racesPanel.getX() + racesPanel.getWidth(), racesPanel.getY() );
        add( tickerPanel );

    }
}
