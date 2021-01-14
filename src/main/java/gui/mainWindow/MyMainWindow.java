package gui.mainWindow;

import backGround.BackGroundHandler;
import dataBase.mySql.ConnectionPool;
import gui.MyGuiComps;
import gui.panels.HeadPanel;
import gui.panels.WindowsPanel;
import locals.LocalHandler;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;
import javax.swing.*;
import java.awt.*;

public class MyMainWindow extends MyGuiComps.MyFrame {

    static Spx spx;

    static {
//        dax = Dax.getInstance();
        spx = Spx.getInstance();
//        apple = Apple.getInstance();
//        amazon = Amazon.getInstance();
//        netflix = Netflix.getInstance();
//        microsoft = Microsoft.getInstance();
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
        MyMainWindow mainWindow = new MyMainWindow("My main window");
        System.out.println(mainWindow.getWidth());
    }

    private void appendClients() {
//        LocalHandler.clients.add(dax);
        LocalHandler.clients.add(spx);
//        LocalHandler.clients.add(apple);
//        LocalHandler.clients.add(amazon);
//        LocalHandler.clients.add(netflix);
//        LocalHandler.clients.add(microsoft);
    }
    
    @Override
    public void initOnClose() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
                    client.getDataBaseHandler().load();
                    BackGroundHandler.getInstance().createNewRunner(client);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}