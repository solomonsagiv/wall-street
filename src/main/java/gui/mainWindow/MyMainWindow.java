package gui.mainWindow;

import backGround.BackGroundHandler;
import dataBase.mySql.ConnectionPool;
import gui.MyGuiComps;
import gui.panels.HeadPanel;
import gui.panels.WindowsPanel;
import locals.LocalHandler;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Dax;
import serverObjects.indexObjects.Ndx;
import serverObjects.indexObjects.Spx;
import java.awt.*;

public class MyMainWindow extends MyGuiComps.MyFrame {

    static Spx spx;
    static Ndx ndx;
    static Dax dax;

    static {
        spx = Spx.getInstance();
        ndx = Ndx.getInstance();
        dax = Dax.getInstance();
    }

    // Variables
    HeadPanel headPanel;
    ConnectionPanel connectionPanel;
    WindowsPanel windowsPanel;

    // Constructor
    public MyMainWindow(String title) throws HeadlessException {
        super(title);
    }

    // Main
    public static void main(String[] args) {
        new MyMainWindow("My main window");
    }

    private void appendClients() {
        LocalHandler.clients.add(spx);
        LocalHandler.clients.add(ndx);
        LocalHandler.clients.add(dax);
    }

    @Override
    public void onClose() {
        super.onClose();
        System.exit(0);
    }

    @Override
    public void initListeners() {
    }

    @Override
    public void initialize() {
        // Append clients
        appendClients();

        // Load data from DB
        loadOnStartUp();

        // This
        setXY(100, 100);
        setSize(500, 420);
        setLayout(null);

        // Head
        headPanel = new HeadPanel();
        headPanel.setXY(0, 0);
        add(headPanel);

        // Connection
        connectionPanel = new ConnectionPanel();
        connectionPanel.setXY(0, headPanel.getHeight());
        getContentPane().add(connectionPanel);

        // Windows
        windowsPanel = new WindowsPanel();
        windowsPanel.setXY(0, connectionPanel.getY() + connectionPanel.getHeight() + 1);
        add(windowsPanel);
    }

    private void loadOnStartUp() {
        // Connect to db
        ConnectionPool.getConnectionsPoolInstance();
        // Start back runners
        for (BASE_CLIENT_OBJECT client : LocalHandler.clients) {
            new Thread(() -> {
                try {
                    // Load data from database
                    client.getMySqlService().getDataBaseHandler().loadData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Start back runner
                BackGroundHandler.getInstance().createNewRunner(client);
            }).start();
        }
    }
}