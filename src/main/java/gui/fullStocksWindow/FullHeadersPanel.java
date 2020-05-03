package gui.fullStocksWindow;

import gui.MyGuiComps;
import locals.Themes;

import java.awt.*;

public class FullHeadersPanel extends MyGuiComps.MyPanel {

    public static MyGuiComps.MyPanel namePanel;
    public static MyGuiComps.MyLabel nameLbl;
    public static MyGuiComps.MyPanel tickerPanel;
    public static MyGuiComps.MyLabel openLbl;
    public static MyGuiComps.MyLabel indLbl;
    public static MyGuiComps.MyLabel highLbl;
    public static MyGuiComps.MyLabel lowLbl;
    public static MyGuiComps.MyLabel opLbl;
    public static MyGuiComps.MyLabel rollLbl;

    public static MyGuiComps.MyPanel racesPanel;
    public static MyGuiComps.MyLabel conRacesLbl;
    public static MyGuiComps.MyLabel indRacesLbl;

    MyGuiComps.MyPanel exp;

    public FullHeadersPanel() {
        inititalize();
        initListeners();
    }

    private void initListeners() {

    }

    private void inititalize() {
        // This
        Color backGround = Themes.BLUE_DARK;
        Color forgGround = Color.WHITE;
        Font font = Themes.ARIEL_BOLD_12;

        // ---------- Name ---------- //
        namePanel = new MyGuiComps.MyPanel();
        namePanel.setBounds(0, 0, 65, 25);
        namePanel.setBackground(backGround);

        nameLbl = new MyGuiComps.MyLabel("Stock");
        nameLbl.setXY(0, 0);
        nameLbl.setSize(namePanel.getSize());
        nameLbl.setForeground(forgGround);
        nameLbl.setFont( font );
        namePanel.add(nameLbl);

        add(namePanel);

        // ---------- Ticker ---------- //
        tickerPanel = new MyGuiComps.MyPanel();
        tickerPanel.setBackground(backGround);
        tickerPanel.setBounds( namePanel.getX() + namePanel.getWidth() + 1, 0, 380, 25);

        // Open
        openLbl = new MyGuiComps.MyLabel( "Open" );
        openLbl.setXY( 5, 0 );
        openLbl.setForeground(forgGround);
        openLbl.setFont( font );

        tickerPanel.add( openLbl );

        // Ind
        indLbl = new MyGuiComps.MyLabel( "Ind" );
        indLbl.setXY( openLbl.getX() + openLbl.getWidth() + 1, 0 );
        indLbl.setForeground(forgGround);
        indLbl.setFont( font );
        tickerPanel.add( indLbl );

        // High
        highLbl = new MyGuiComps.MyLabel( "High" );
        highLbl.setXY( indLbl.getX() + indLbl.getWidth() + 1, 0 );
        highLbl.setForeground(forgGround);
        highLbl.setFont( font );
        tickerPanel.add( highLbl );

        // Low
        lowLbl = new MyGuiComps.MyLabel( "Low" );
        lowLbl.setXY( highLbl.getX() + highLbl.getWidth() + 1, 0 );
        lowLbl.setForeground(forgGround);
        lowLbl.setFont( font );
        tickerPanel.add( lowLbl );

        // Op
        opLbl = new MyGuiComps.MyLabel( "O/P" );
        opLbl.setXY( lowLbl.getX() + lowLbl.getWidth() + 1, 0 );
        opLbl.setForeground(forgGround);
        opLbl.setFont( font );
        tickerPanel.add( opLbl );

        // Roll
        rollLbl = new MyGuiComps.MyLabel( "Roll" );
        rollLbl.setXY( opLbl.getX() + opLbl.getWidth() + 1, 0 );
        rollLbl.setForeground(forgGround);
        rollLbl.setFont( font );
        tickerPanel.add( rollLbl );

        add( tickerPanel );

        // ---------- Races ---------- //
        racesPanel = new MyGuiComps.MyPanel();
        racesPanel.setBackground(backGround);
        racesPanel.setBounds(  tickerPanel.getX() + tickerPanel.getWidth() + 1, 0 , 135, 25 );

        // Con races
        conRacesLbl = new MyGuiComps.MyLabel( "Con" );
        conRacesLbl.setXY( 5, 0 );
        conRacesLbl.setFont( font );
        conRacesLbl.setForeground(forgGround);
        racesPanel.add( conRacesLbl );

        // Ind races
        indRacesLbl = new MyGuiComps.MyLabel( "Ind" );
        indRacesLbl.setFont( font );
        indRacesLbl.setForeground(forgGround);
        indRacesLbl.setXY( conRacesLbl.getX() + conRacesLbl.getWidth() + 1, 0 );
        racesPanel.add( indRacesLbl );

        add( racesPanel );

    }
}
