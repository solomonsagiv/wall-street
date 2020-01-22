package gui;

import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.DaxCLIENTObject;
import serverObjects.indexObjects.NdxCLIENTObject;
import serverObjects.indexObjects.SpxCLIENTObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuMaker {

    BASE_CLIENT_OBJECT client;

    public MenuMaker( BASE_CLIENT_OBJECT client ) {
        this.client = client;
    }

    public JPopupMenu spxOptionsTableMenu() {
        // Main menu
        JPopupMenu menu = new JPopupMenu( );

        JMenuItem chart = new JMenuItem( "Chart" );
        chart.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent e ) {
                try {

                    // Do something here

                } catch ( Exception exception ) {
                    exception.printStackTrace( );
                }
            }
        } );

        JMenuItem setStrike = new JMenuItem( "Set strike" );
        setStrike.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent e ) {
                try {

                    int strike = Integer.parseInt( JOptionPane.showInputDialog( "Enter strike" ) );
                    TablesUpdater.setSpxTableStrikes( strike );

                } catch ( Exception exception ) {
                    exception.printStackTrace( );
                }
            }
        } );

        menu.add( setStrike );
        menu.add( chart );

        return menu;
    }

    public JPopupMenu futureCounterTableMenu() {
        // Main menu
        JPopupMenu menu = new JPopupMenu( );

        JMenuItem setStrike = new JMenuItem( "Set strike" );
        setStrike.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent e ) {
                try {
                    int strike = Integer.parseInt( JOptionPane.showInputDialog( "Enter strike" ) );
                    TablesUpdater.setSpxTableStrikes( strike );

                } catch ( Exception exception ) {
                    exception.printStackTrace( );
                }
            }
        } );

        menu.add( setStrike );

        return menu;
    }


    public BASE_CLIENT_OBJECT getClientByStringName( String name ) {

        name = name.toLowerCase( );

        if ( name.equals( "dax" ) ) {
            client = DaxCLIENTObject.getInstance( );
        }

        if ( name.equals( "spx" ) ) {
            client = SpxCLIENTObject.getInstance( );
        }

        if ( name.equals( "ndx" ) ) {
            client = NdxCLIENTObject.getInstance( );
        }

        return client;
    }

}
