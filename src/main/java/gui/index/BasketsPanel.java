package gui.index;

import gui.MyGuiComps;
import gui.panels.IMyPanel;
import locals.L;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;

import javax.swing.*;
import java.awt.*;

public class BasketsPanel extends MyGuiComps.MyPanel implements IMyPanel {

    BASE_CLIENT_OBJECT client;

    MyGuiComps.MyPanel header;
    MyGuiComps.MyLabel headerLbl;

    MyGuiComps.MyPanel body;
    MyGuiComps.MyLabel upLbl;
    MyGuiComps.MyLabel downLbl;
    MyGuiComps.MyLabel sumLbl;

    MyGuiComps.MyTextField upField;
    MyGuiComps.MyTextField downField;
    MyGuiComps.MyTextField sumField;

    public BasketsPanel( BASE_CLIENT_OBJECT client ) {
        super( );
        this.client = client;
        initsialize( );
    }

    private void initsialize() {
        setSize( 90, 300 );

        // ------ Head ------ //
        header = new MyGuiComps.MyPanel( );
        header.setSize( 90, 25 );
        header.setXY( 0, 0 );
        add( header );

        headerLbl = new MyGuiComps.MyLabel( "Baskets", true );
        headerLbl.setHorizontalAlignment( JLabel.CENTER );
        header.add( headerLbl );

        // Body
        body = new MyGuiComps.MyPanel( );
        body.setXY( 0, header.getY( ) + header.getHeight( ) + 1 );
        body.setSize( 90, 300 );
        add( body );

        // Up
        upLbl = new MyGuiComps.MyLabel( "Up" );
        upLbl.setXY( 0, 3 );
        upLbl.setWidth( 40 );
        body.add( upLbl );

        upField = new MyGuiComps.MyTextField( );
        upField.setForeground( Themes.GREEN );
        upField.setWidth( 40 );
        upField.setXY( upLbl.getX( ) + upLbl.getWidth( ) + 3, upLbl.getY( ) );
        body.add( upField );

        // Down
        downLbl = new MyGuiComps.MyLabel( "Down" );
        downLbl.setXY( 0, upLbl.getY( ) + upLbl.getHeight( ) + 2 );
        downLbl.setWidth( 40 );
        body.add( downLbl );

        downField = new MyGuiComps.MyTextField( );
        downField.setForeground( Themes.RED );
        downField.setWidth( 40 );
        downField.setXY( downLbl.getX( ) + downLbl.getWidth( ) + 3, downLbl.getY( ) );
        body.add( downField );

        // Sum
        sumLbl = new MyGuiComps.MyLabel( "Sum" );
        sumLbl.setXY( 0, downLbl.getY( ) + downLbl.getHeight( ) + 2 );
        sumLbl.setWidth( 40 );
        body.add( sumLbl );

        sumField = new MyGuiComps.MyTextField( );
        sumField.setWidth( 40 );
        sumField.setXY( sumLbl.getX( ) + sumLbl.getWidth( ) + 3, sumLbl.getY( ) );
        body.add( sumField );
    }

    int basket_up_0 = 0;
    int basket_down_0 = 0;

    @Override
    public void updateText() {
        if ( client.getBasketFinder( ) != null ) {

            int basketUp = 0;
            int basketDown = 0;
            try {
                // Get current
                basketUp = L.INT( upField.getText( ) );
                basketDown = L.INT( downField.getText( ) );
            } catch ( Exception e ) {
                System.out.println( "First basket" );
            }

            // Up
            if ( basketUp > basket_up_0 ) {
                nois( upField );
            }

            // Down
            if ( basketDown > basket_down_0 ) {
                nois( downField );
            }

            // Update text
            upField.setText( L.str( client.getBasketFinder( ).getBasketUp( ) ) );
            downField.setText( L.str( client.getBasketFinder( ).getBasketDown( ) ) );
            sumField.colorForge( client.getBasketFinder( ).getBaskets( ) );

            // Update pre
            basket_up_0 = basketUp;
            basket_down_0 = basketDown;


        }
    }

    private void nois( JTextField textField ) {
        new Thread( () -> {
            try {
                // Default
                Color defaultcolor = textField.getBackground( );
                textField.setBackground( Themes.BLUE_LIGHT_2 );
                Thread.sleep( 2000 );
                textField.setBackground( defaultcolor );
            } catch ( InterruptedException e ) {
                e.printStackTrace( );
            }
        } ).start( );
    }
}
