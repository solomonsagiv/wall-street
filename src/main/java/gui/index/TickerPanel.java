package gui.index;

import exp.Exp;
import exp.Exps;
import gui.MyGuiComps;
import gui.panels.IMyPanel;
import locals.L;
import serverObjects.BASE_CLIENT_OBJECT;
import java.awt.*;
import java.util.ArrayList;

public class TickerPanel extends MyGuiComps.MyPanel implements IMyPanel {

    BASE_CLIENT_OBJECT client;
    Exps exps;

    ExpsPanel expsPanel;

    MyGuiComps.MyPanel headerPanel;
    MyGuiComps.MyPanel bodyPanel;

    // Ticker
    MyGuiComps.MyLabel openLbl;
    MyGuiComps.MyLabel lastLbl;
    MyGuiComps.MyLabel lowLbl;
    MyGuiComps.MyLabel highLbl;

    MyGuiComps.MyTextField openField;
    MyGuiComps.MyTextField openPresentField;
    MyGuiComps.MyTextField lastField;
    MyGuiComps.MyTextField lastPresentField;
    MyGuiComps.MyTextField lowField;
    MyGuiComps.MyTextField lowPresentField;
    MyGuiComps.MyTextField highField;
    MyGuiComps.MyTextField highPresentField;

    public TickerPanel( BASE_CLIENT_OBJECT client ) {
        super( );
        this.client = client;
        this.exps = client.getExps( );
        initsialize( );
    }

    private void initsialize() {
        setSize( 290, 500 );

        // ------ Head ------ //
        headerPanel = new MyGuiComps.MyPanel( );
        headerPanel.setSize( 320, 25 );
        headerPanel.setXY( 5, 0 );
        add( headerPanel );

        // Open
        openLbl = new MyGuiComps.MyLabel( "Open", true );
        openLbl.setXY( 0, 0 );
        headerPanel.add( openLbl );

        // Last
        lastLbl = new MyGuiComps.MyLabel( "Last", true );
        lastLbl.setXY( openLbl.getX( ) + openLbl.getWidth( ) + 9, 0 );
        headerPanel.add( lastLbl );

        // Low
        lowLbl = new MyGuiComps.MyLabel( "Low", true );
        lowLbl.setXY( lastLbl.getX( ) + lastLbl.getWidth( ) + 9, 0 );
        headerPanel.add( lowLbl );

        // High
        highLbl = new MyGuiComps.MyLabel( "High", true );
        highLbl.setXY( lowLbl.getX( ) + lowLbl.getWidth( ) + 9, 0 );
        headerPanel.add( highLbl );

        // ------- Body ------- //
        bodyPanel = new MyGuiComps.MyPanel( );
        bodyPanel.setXY( headerPanel.getX( ), headerPanel.getY( ) + headerPanel.getHeight( ) + 2 );
        bodyPanel.setSize( 500, 350 );
        add( bodyPanel );

        // Open
        openField = new MyGuiComps.MyTextField( );
        openField.setXY( openLbl.getX( ), 6 );
        bodyPanel.add( openField );

        openPresentField = new MyGuiComps.MyTextField( );
        openPresentField.setForeground( Color.WHITE );
        openPresentField.setFont( openPresentField.getFont( ).deriveFont( Font.BOLD ) );
        openPresentField.setXY( openField.getX( ), openField.getY( ) + openField.getHeight( ) + 3 );
        bodyPanel.add( openPresentField );

        // Last
        lastField = new MyGuiComps.MyTextField( );
        lastField.setXY( lastLbl.getX( ), 6 );
        bodyPanel.add( lastField );

        lastPresentField = new MyGuiComps.MyTextField( );
        lastPresentField.setForeground( Color.WHITE );
        lastPresentField.setFont( lastPresentField.getFont( ).deriveFont( Font.BOLD ) );
        lastPresentField.setXY( lastField.getX( ), lastField.getY( ) + lastField.getHeight( ) + 3 );
        bodyPanel.add( lastPresentField );

        // Low
        lowField = new MyGuiComps.MyTextField( );
        lowField.setXY( lowLbl.getX( ), 6 );
        bodyPanel.add( lowField );

        lowPresentField = new MyGuiComps.MyTextField( );
        lowPresentField.setForeground( Color.WHITE );
        lowPresentField.setFont( lowPresentField.getFont( ).deriveFont( Font.BOLD ) );
        lowPresentField.setXY( lowField.getX( ), lowField.getY( ) + lowField.getHeight( ) + 3 );
        bodyPanel.add( lowPresentField );

        // High
        highField = new MyGuiComps.MyTextField( );
        highField.setXY( highLbl.getX( ), 6 );
        bodyPanel.add( highField );

        highPresentField = new MyGuiComps.MyTextField( );
        highPresentField.setForeground( Color.WHITE );
        highPresentField.setFont( highPresentField.getFont( ).deriveFont( Font.BOLD ) );
        highPresentField.setXY( highField.getX( ), highField.getY( ) + highField.getHeight( ) + 3 );
        bodyPanel.add( highPresentField );

        // Exps
        expsPanel = new ExpsPanel( exps );
        expsPanel.setXY( openPresentField.getX( ), openPresentField.getY( ) + openPresentField.getHeight( ) + 6 );
        bodyPanel.add( expsPanel );

    }

    @Override
    public void updateText() {

        // Raw
        openField.setText( L.str( client.getOpen( ) ) );
        lastField.setText( L.str( client.getIndex( ) ) );
        lowField.setText( L.str( client.getLow( ) ) );
        highField.setText( L.str( client.getHigh( ) ) );

        // Present
        openPresentField.colorBack( L.present( client.getOpen( ), client.getBase( ) ), L.format100( ), "%" );
        lastPresentField.colorBack( L.present( client.getIndex( ), client.getBase( ) ), L.format100( ), "%" );
        lowPresentField.colorBack( L.present( client.getLow( ), client.getBase( ) ), L.format100( ), "%" );
        highPresentField.colorBack( L.present( client.getHigh( ), client.getBase( ) ), L.format100( ), "%" );

        // Exps mini panels
        expsPanel.updateText( );
    }
}


class ExpsPanel extends MyGuiComps.MyPanel implements IMyPanel {

    Exps exps;
    ArrayList< ExpMiniPanel > miniPanels = new ArrayList<>( );

    public ExpsPanel( Exps exps ) {
        super( );
        this.exps = exps;
        initsialize( );
    }

    private void initsialize() {
        setSize( 300, 500 );

        // Exp mini panels
        int y = 0;
        for ( Exp exp : exps.getExpList( ) ) {
            ExpMiniPanel expMiniPanel = new ExpMiniPanel( exp );
            miniPanels.add( expMiniPanel );
            expMiniPanel.setXY( 0, y );
            add( expMiniPanel );
            y += expMiniPanel.getHeight( ) + 3;
        }
    }

    @Override
    public void updateText() {
        for ( ExpMiniPanel miniPanel : miniPanels ) {
            miniPanel.updateText( );
        }
    }
}


// Exp mini panels
class ExpMiniPanel extends MyGuiComps.MyPanel implements IMyPanel {

    Exp exp;

    MyGuiComps.MyLabel expNameLbl;
    MyGuiComps.MyTextField futField;
    MyGuiComps.MyTextField opField;
    MyGuiComps.MyTextField opAvgField;

    public ExpMiniPanel( Exp exp ) {
        super( );
        this.exp = exp;
        initsialize( );
    }

    private void initsialize() {

        setSize( 300, 28 );

        // Name
        expNameLbl = new MyGuiComps.MyLabel( exp.getName( ) );
        expNameLbl.setXY( 5, 0 );
        add( expNameLbl );

        // Fut
        futField = new MyGuiComps.MyTextField( );
        futField.setXY( expNameLbl.getX( ) + expNameLbl.getWidth( ) + 4, expNameLbl.getY( ) );
        add( futField );

        // Op
        opField = new MyGuiComps.MyTextField( );
        opField.setXY( futField.getX( ) + futField.getWidth( ) + 4, futField.getY( ) );
        add( opField );

        // Op avg
        opAvgField = new MyGuiComps.MyTextField( );
        opAvgField.setXY( opField.getX( ) + opField.getWidth( ) + 4, opField.getY( ) );
        add( opAvgField );
    }

    @Override
    public void updateText() {
        try {
            futField.setText( L.str( L.floor( exp.getFuture( ) ,10) ) );
            opField.colorBack( exp.getOpFuture( ), L.format10( ) );

            if ( exp.getOpFutList( ).size( ) > 0 ) {
                opAvgField.colorForge( exp.getOpAvgFut( ), L.format100( ) );
            }

        } catch ( Exception e ) {
            e.printStackTrace( );
        }
    }
}
