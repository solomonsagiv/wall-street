package setting;

import gui.MyGuiComps;
import jxl.biff.drawing.ComboBox;
import locals.LocalHandler;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;
import setting.optionsPanel.OptionsPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SettingWindow extends MyGuiComps.MyFrame {

    public static void main( String[] args ) {
        SettingWindow settingWindow = new SettingWindow( "Setting", Spx.getInstance() );
    }

    // Races panel
    TickerPanel tickerPanel;
    OptionsPanel optionsPanel;
    DataBasePanel dataBasePanel;
//    JComboBox clientCombo;

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
        setSize( 800, 450 );
        setResizable( false );

        // Ticker
        tickerPanel = new TickerPanel( client );
        tickerPanel.setBounds( 0, 0, ( int ) getPreferredSize().getWidth(), 125 );
        add( tickerPanel );


//        // Client combo
//        clientCombo = new JComboBox(getClientComboItems());
//        clientCombo.setBounds( 500, 20, 60, 25 );
//        tickerPanel.add( clientCombo );

        // Options
        optionsPanel = new OptionsPanel( client );
        optionsPanel.setXY( 0, tickerPanel.getY() + tickerPanel.getHeight() + 1 );
        optionsPanel.setSize( ( int ) getPreferredSize().getWidth(), optionsPanel.getHeight() );
        add( optionsPanel );

        // Data base
        dataBasePanel = new DataBasePanel( client );
        dataBasePanel.setXY( 0, optionsPanel.getY() + optionsPanel.getHeight() + 1 );
        dataBasePanel.setSize(  ( int ) getPreferredSize().getWidth(), 150  );
        add( dataBasePanel );

    }

//    private String[] getClientComboItems() {
//
//        ArrayList< String > items = new ArrayList<>();
//
//        for ( BASE_CLIENT_OBJECT client : LocalHandler.clients ) {
//
//            items.add( client.getName().toUpperCase() );
//
//        }
//
//        items.add( "STOCKS" );
//
//        return ( String[] ) items.toArray();
//    }
}
