package gui.mainWindow;

import DDE.DDEReader;
import api.Downloader;
import api.Manifest;
import gui.LogWindow;
import gui.MyGuiComps;
import locals.Themes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectionPanel extends MyGuiComps.MyPanel {

    // Variables
    JComboBox connectionComboBox;
    MyGuiComps.MyLabel connecionLbl = new MyGuiComps.MyLabel( "Connection " );
    MyGuiComps.MyButton connectionBtn = new MyGuiComps.MyButton( "Connect" );
    MyGuiComps.MyButton disConnectBtn = new MyGuiComps.MyButton( "Disconnect" );
    MyGuiComps.MyButton logBtn = new MyGuiComps.MyButton( "Log" );
    MyGuiComps.MyLabel statusLbl = new MyGuiComps.MyLabel( "Not connected" );
    MyGuiComps.MyLabel portLbl = new MyGuiComps.MyLabel( "Port" );
    MyGuiComps.MyTextField portField = new MyGuiComps.MyTextField( );

    DDEReader ddeReader;
    Downloader downloader;

    // Constructor
    public ConnectionPanel() {
        super( );
        initialize( );
        initListeners( );
    }

    private void initListeners() {

        // Connection btn
        connectionBtn.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent actionEvent ) {

                switch ( connectionComboBox.getSelectedItem( ).toString( ) ) {
                    case "ALL":
                        connectAll( );
                        break;
                    case "DDE":
                        connectDDE( );
                        break;
                    case "TWS":
                        connectTws( );
                        break;
                    default:
                        break;
                }
                // Log window
                LogWindow logWindow = new LogWindow( );
                logWindow.frame.setVisible( true );
            }
        } );

        // Disconnect btn
        disConnectBtn.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent e ) {
                ddeReader.getHandler( ).close( );
            }
        } );

        // Log btn
        logBtn.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent e ) {
                // Log window
                LogWindow logWindow = new LogWindow( );
                logWindow.frame.setVisible( true );
            }
        } );
    }

    public void connectAll() {
        connectDDE( );
        connectTws( );
    }

    public void connectDDE() {
        try {
            ddeReader = new DDEReader( );
            ddeReader.getHandler( ).start( );
        } catch ( Exception e ) {
            e.printStackTrace( );
        }
    }

    public void connectTws() {
        try {
            downloader = Downloader.getInstance( );
            downloader.start( );
        } catch ( Exception e ) {
            e.printStackTrace( );
        }
    }

    private void initialize() {

        // This
        setWidth( 500 );
        setHeight( 130 );
        setBackground( Color.WHITE );

        // Connection
        connecionLbl.setXY( 10, 10 );
        connecionLbl.setWidth( 150 );
        connecionLbl.setFont( connectionBtn.getFont( ).deriveFont( Font.PLAIN ) );
        connecionLbl.setFont( connectionBtn.getFont( ).deriveFont( 14f ) );
        connecionLbl.setHorizontalAlignment( JLabel.LEFT );
        connecionLbl.setForeground( Color.BLACK );
        add( connecionLbl );

        // Port lbl
        portLbl.setXY( 20, 50 );
        portLbl.setForeground( Themes.BLUE_DARK );
        portLbl.setWidth( 80 );
        portLbl.setHorizontalAlignment( JLabel.LEFT );
        portLbl.setFont( portLbl.getFont( ).deriveFont( 12f ).deriveFont( Font.BOLD ) );
        portLbl.setLabelFor( portField );
        add( portLbl );

        portField.setXY( 20, 80 );
        portField.setWidth( 80 );
        portField.setOpaque( false );
        portField.setBorder( BorderFactory.createMatteBorder( 0, 0, 1, 0, Themes.BLUE_DARK ) );
        portField.setForeground( Themes.BLUE_DARK );
        portField.setText( String.valueOf( Manifest.CLIENT_ID ) );
        portField.setEnabled( true );
        portField.setEditable( true );
        add( portField );

        // Connect btn
        connectionBtn.setXY( portField.getX( ) + portField.getWidth( ) + 20, 50 );
        connectionBtn.setForeground( Color.WHITE );
        connectionBtn.setBorder( BorderFactory.createLineBorder( Themes.GREEN.brighter( ) ) );
        connectionBtn.setBackground( Themes.GREEN );
        add( connectionBtn );

        // Connection comboBox
        String[] items = new String[] { "ALL", "DDE", "TWS" };
        connectionComboBox = new JComboBox( items );
        connectionComboBox.setBounds( connectionBtn.getX( ) + connectionBtn.getWidth( ) + 20, connectionBtn.getY( ), 80, 25 );
        connectionComboBox.setBackground( Themes.BLUE_DARK );
        connectionComboBox.setForeground( Color.WHITE );
        add( connectionComboBox );

        // Disconnect btn
        disConnectBtn.setXY( portField.getX( ) + portField.getWidth( ) + 20, 80 );
        disConnectBtn.setForeground( Color.WHITE );
        disConnectBtn.setBackground( Themes.RED );
        disConnectBtn.setBorder( BorderFactory.createLineBorder( Themes.RED.brighter( ) ) );
        add( disConnectBtn );

        // Status lbl
        statusLbl.setXY( disConnectBtn.getX( ) + disConnectBtn.getWidth( ) + 20, disConnectBtn.getY( ) );
        statusLbl.setWidth( 120 );
        statusLbl.setHorizontalAlignment( JLabel.LEFT );
        statusLbl.setForeground( Themes.RED );
        add( statusLbl );

        // Log btn
        logBtn.setXY( statusLbl.getX( ) + statusLbl.getWidth( ) + 60, 80 );
        logBtn.setBackground( Color.WHITE );
        logBtn.setBorder( BorderFactory.createLineBorder( Themes.BLUE_DARK.brighter( ) ) );
        logBtn.setForeground( Themes.BLUE_DARK );
        add( logBtn );

    }

}
