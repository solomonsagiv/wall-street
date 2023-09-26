package arik.window;

import dataBase.mySql.ConnectionPool;
import gui.AlertWindow;
import gui.MyGuiComps;
import tws.accounts.ConnectionsAndAccountHandler;
import tws.handlers.AccountAndConnection;
import java.awt.*;
import java.sql.SQLException;

public class ArikWindow extends MyGuiComps.MyFrame {

    public static ArikWindow arikWindow;

    public static void main(String[] args) throws SQLException {

        arikWindow = new ArikWindow("Arik window");
        ConnectionPool.getConnectionsPoolInstance(1);

        try {
            // Load account data
            ConnectionsAndAccountHandler.load_data();
        } catch (Exception e) {
            e.printStackTrace();
            AlertWindow.Show(e.getMessage(), e.getCause(), e.getStackTrace());
        }

        // Update ui after load
        update_ui_after_load_data();
    }

    public static void update_ui_after_load_data() {
        // Combobox
        update_combobox();
    }


    private static void update_combobox() {
        arikWindow.arikMainPanel.comboBox.addItem("All");
        for (AccountAndConnection accountAndConnection: ConnectionsAndAccountHandler.accountAndConnectionHashMap.values()) {
            String name = accountAndConnection.account.getAccountName();
            arikWindow.arikMainPanel.comboBox.addItem(name);
        }
    }


    ArikMainPanel arikMainPanel;

    public ArikWindow(String title) throws HeadlessException {
        super(title);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initialize() {

        // This
        getContentPane().setLayout(null);
        setPreferredSize(new Dimension(950, 500));

        // Main panel
        arikMainPanel = new ArikMainPanel();
        arikMainPanel.setXY(0, 0);
        arikMainPanel.setWidth(1000);
        arikMainPanel.setHeight(500);
        add(arikMainPanel);
    }

    @Override
    public void onClose() {
        super.onClose();
        System.exit(0);
    }
}
