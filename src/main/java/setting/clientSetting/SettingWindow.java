package setting.clientSetting;

import gui.MyGuiComps;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;
import setting.clientSetting.optionsPanel.OptionsPanel;

import java.awt.*;

public class SettingWindow extends MyGuiComps.MyFrame {

    // Races panel
    TickerPanel tickerPanel;
    OptionsPanel optionsPanel;
    DataBasePanel dataBasePanel;

    // Constructor
    public SettingWindow(String title, BASE_CLIENT_OBJECT client) throws HeadlessException {
        super(title, client);
    }

    public static void main(String[] args) {
        SettingWindow settingWindow = new SettingWindow("Setting", Spx.getInstance());
    }

    @Override
    public void initListeners() {
    }
    
    @Override
    public void initialize() {

        // This
        setXY(200, 200);
        setLayout(null);
        setSize(800, 450);
        
        // Ticker
        tickerPanel = new TickerPanel(client);
        tickerPanel.setBounds(0, 0, (int) getPreferredSize().getWidth(), 125);
        add(tickerPanel);

        // Options
        optionsPanel = new OptionsPanel(client);
        optionsPanel.setXY(0, tickerPanel.getY() + tickerPanel.getHeight() + 1);
        optionsPanel.setSize((int) getPreferredSize().getWidth(), optionsPanel.getHeight());
        add(optionsPanel);

        // Data base
        dataBasePanel = new DataBasePanel(client);
        dataBasePanel.setXY(0, optionsPanel.getY() + optionsPanel.getHeight() + 1);
        dataBasePanel.setSize((int) getPreferredSize().getWidth(), 150);
        add(dataBasePanel);

    }

}
