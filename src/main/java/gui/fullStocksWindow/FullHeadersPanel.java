package gui.fullStocksWindow;

import gui.MyGuiComps;

public class FullHeadersPanel extends MyGuiComps.MyPanel {

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

        // ---------- Ticker ---------- //
        tickerPanel = new MyGuiComps.MyPanel();
        tickerPanel.setBounds( 0, 0, 380, 25);

        // Open
        openLbl = new MyGuiComps.MyLabel( "Open" );
        openLbl.setXY( 5, 0 );
        tickerPanel.add( openLbl );

        // Ind
        indLbl = new MyGuiComps.MyLabel( "Ind" );
        indLbl.setXY( openLbl.getX() + openLbl.getWidth() + 1, 0 );
        tickerPanel.add( indLbl );

        // High
        highLbl = new MyGuiComps.MyLabel( "High" );
        highLbl.setXY( indLbl.getX() + indLbl.getWidth() + 1, 0 );
        tickerPanel.add( highLbl );

        // Low
        lowLbl = new MyGuiComps.MyLabel( "Low" );
        lowLbl.setXY( highLbl.getX() + highLbl.getWidth() + 1, 0 );
        tickerPanel.add( lowLbl );

        // Op
        opLbl = new MyGuiComps.MyLabel( "O/P" );
        opLbl.setXY( lowLbl.getX() + lowLbl.getWidth() + 1, 0 );
        tickerPanel.add( opLbl );

        // Roll
        rollLbl = new MyGuiComps.MyLabel( "Roll" );
        rollLbl.setXY( opLbl.getX() + opLbl.getWidth() + 1, 0 );
        tickerPanel.add( rollLbl );

        add( tickerPanel );

        // ---------- Races ---------- //
        racesPanel = new MyGuiComps.MyPanel();
        racesPanel.setBounds(  tickerPanel.getX() + tickerPanel.getWidth() + 2, 0 , 135, 25 );

        // Con races
        conRacesLbl = new MyGuiComps.MyLabel( "Con" );
        conRacesLbl.setXY( 5, 0 );
        racesPanel.add( conRacesLbl );

        // Ind races
        indRacesLbl = new MyGuiComps.MyLabel( "Ind" );
        indRacesLbl.setXY( conRacesLbl.getX() + conRacesLbl.getWidth() + 1, 0 );
        racesPanel.add( indRacesLbl );

        add( racesPanel );

    }
}
