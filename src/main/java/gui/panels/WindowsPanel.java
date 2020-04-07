package gui.panels;

import gui.MyGuiComps;
import gui.index.IndexWindow;
import gui.stock.StockWindow;
import locals.LocalHandler;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Ndx;
import serverObjects.indexObjects.Spx;
import serverObjects.stockObjects.Apple;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

public class WindowsPanel extends MyGuiComps.MyPanel {

    // Variables
    JComboBox clientsCombo;

    MyGuiComps.MyLabel windowsLbl = new MyGuiComps.MyLabel( "Windows" );

    // Constructor
    public WindowsPanel() {
        super( );
        initCombo( );
        initialize( );
        initListeners( );
    }

    private void initListeners() {
        // Client combo
        clientsCombo.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent e ) {
                String selected = clientsCombo.getSelectedItem( ).toString( );

                switch ( selected ) {
                    case "SPX":
                        new IndexWindow( "Spx", Spx.getInstance( ) );
                        break;
                    case "NDX":
                        new IndexWindow( "Ndx", Ndx.getInstance( ) );
                        break;
                    case "APPLE":
                        new StockWindow( "Apple", Apple.getInstance( ) );
                        break;
                    default:
                        break;
                }
            }
        } );
    }

    private void initialize() {

        // This
        setBackground( Themes.GREY_LIGHT );
        setWidth( 500 );
        setHeight( 150 );

        // Windows lbl
        windowsLbl.setXY( 10, 10 );
        windowsLbl.setWidth( 150 );
        windowsLbl.setFont( windowsLbl.getFont( ).deriveFont( Font.PLAIN ) );
        windowsLbl.setFont( windowsLbl.getFont( ).deriveFont( 14f ) );
        windowsLbl.setHorizontalAlignment( JLabel.LEFT );
        windowsLbl.setForeground( Color.BLACK );
        add( windowsLbl );

        // Clients combo
        clientsCombo.setBounds( 20, 40, 80, 25 );
        clientsCombo.setBackground( Themes.BLUE_DARK );
        clientsCombo.setForeground( Color.WHITE );
        add( clientsCombo );

    }

    private void initCombo() {
        Set<BASE_CLIENT_OBJECT>clients = LocalHandler.clients;
        String[] clientNames = new String[ LocalHandler.clients.size( ) ];
        int i = 0;
        for ( BASE_CLIENT_OBJECT client : clients ) {
            clientNames[ i ] = client.getName( ).toUpperCase( );
            i++;
        }
        clientsCombo = new JComboBox( clientNames );
    }

}
