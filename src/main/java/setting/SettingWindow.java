package setting;

import gui.MyGuiComps;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.stockObjects.Apple;

import java.awt.*;

public class SettingWindow extends MyGuiComps.MyFrame {

    public static void main( String[] args ) {
        SettingWindow settingWindow = new SettingWindow( "Setting", Apple.getInstance() );
    }

    // Races panel
    TickerPanel tickerPanel;
    OptionsPanel optionsPanel;

    // Constructor
    public SettingWindow( String title ) throws HeadlessException {
        super( title );
    }

    public SettingWindow( String title, BASE_CLIENT_OBJECT client ) throws HeadlessException {
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
        setSize( 850, 400 );

        // Ticker
        tickerPanel = new TickerPanel( client );
        tickerPanel.setBounds( 0, 0, ( int ) getPreferredSize().getWidth(), 125 );
        add( tickerPanel );

        // Options
        optionsPanel = new OptionsPanel( client );
        optionsPanel.setXY( 0, tickerPanel.getY() + tickerPanel.getHeight() + 1 );
        optionsPanel.setSize( ( int ) getPreferredSize().getWidth(), 300 );
        add( optionsPanel );

    }
}
