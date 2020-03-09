package gui.mainWindow;

import gui.MyGuiComps;
import gui.panels.HeadPanel;
import gui.panels.WindowsPanel;
import locals.LocalHandler;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;
import serverObjects.stockObjects.Apple;

import javax.swing.*;
import java.awt.*;

public class MyMainWindow extends MyGuiComps.MyFrame {

    // Main
    public static void main( String[] args ) {
        MyMainWindow mainWindow = new MyMainWindow( "My main window" );
        System.out.println( mainWindow.getWidth( ) );
    }

    // Variables
    HeadPanel headPanel;
    ConnectionPanel connectionPanel;
    WindowsPanel windowsPanel;

    // Constructor
    public MyMainWindow( String title ) throws HeadlessException {
        super( title );
    }

    private void appendClients() {
        LocalHandler.clients.add( Spx.getInstance( ) );
        LocalHandler.clients.add( Apple.getInstance( ) );
    }

    @Override
    public void onClose() {
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    }

    @Override
    public void initListeners() {
    }

    @Override
    public void initialize() {

        // Append clients
        appendClients( );

        // Load data from DB
        loadOnStartUp( );

        // This
        setXY( 100, 100 );
        setSize( 500, 500 );
        setLayout( null );

        // Head
        headPanel = new HeadPanel( );
        headPanel.setXY( 0, 0 );
        add( headPanel );

        // Connection
        connectionPanel = new ConnectionPanel( );
        connectionPanel.setXY( 0, headPanel.getHeight( ) );
        getContentPane( ).add( connectionPanel );

        // Windows
        windowsPanel = new WindowsPanel( );
        windowsPanel.setXY( 0, connectionPanel.getY( ) + connectionPanel.getHeight( ) + 1 );
        add( windowsPanel );

    }

    private void loadOnStartUp() {

        for ( BASE_CLIENT_OBJECT client : LocalHandler.clients ) {

            client.getMyTableHandler().getMyStatusTable().load();
            client.getMyTableHandler().getMyArraysTable().load();

            client.setLoadStatusFromHB( true );
            client.setLoadArraysFromHB( true );
            client.setLoadFromDb( true );
        }

    }
}
