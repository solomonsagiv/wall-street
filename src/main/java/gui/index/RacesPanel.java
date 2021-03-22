package gui.index;

import gui.MyGuiComps;
import gui.panels.IMyPanel;
import serverObjects.BASE_CLIENT_OBJECT;

import javax.swing.*;

public class RacesPanel extends MyGuiComps.MyPanel implements IMyPanel {

    BASE_CLIENT_OBJECT client;

    MyGuiComps.MyPanel header;
    MyGuiComps.MyLabel headerLbl;

    MyGuiComps.MyPanel body;
    MyGuiComps.MyLabel futLbl;
    MyGuiComps.MyLabel indLbl;

    MyGuiComps.MyTextField futField;
    MyGuiComps.MyTextField indField;

    public RacesPanel( BASE_CLIENT_OBJECT client ) {
        super();
        this.client = client;
        initsialize();
    }

    private void initsialize() {
        setSize( 90, 300 );

        // ------ Head ------ //
        header = new MyGuiComps.MyPanel();
        header.setSize( 90, 25 );
        header.setXY( 0, 0 );
        add( header );

        headerLbl = new MyGuiComps.MyLabel( "Races", true );
        headerLbl.setHorizontalAlignment( JLabel.CENTER );
        header.add( headerLbl );

        // ------ Body ------ //
        body = new MyGuiComps.MyPanel();
        body.setXY( 0, header.getY() + header.getHeight() + 1 );
        body.setSize( 90, 300 );
        add( body );

        // Fut
        futLbl = new MyGuiComps.MyLabel( "Fut" );
        futLbl.setXY( 0, 3 );
        futLbl.setWidth( 40 );
        body.add( futLbl );

        futField = new MyGuiComps.MyTextField(  );
        futField.setXY( futLbl.getX() + futLbl.getWidth(), futLbl.getY() );
        futField.setWidth( 40 );
        body.add( futField );

        // Ind
        indLbl = new MyGuiComps.MyLabel( "Ind" );
        indLbl.setWidth( 40 );
        indLbl.setXY( futLbl.getX(), futLbl.getY() + futLbl.getHeight() + 1 );
        body.add( indLbl );

        indField = new MyGuiComps.MyTextField(  );
        indField.setWidth( 40 );
        indField.setXY( futField.getX(), futField.getY() + futField.getHeight() + 1 );
        body.add( indField );
    }

    @Override
    public void updateText() {
        futField.colorForge( client.getFutSum() );
        indField.colorForge( client.getIndexSum() );
    }
}
