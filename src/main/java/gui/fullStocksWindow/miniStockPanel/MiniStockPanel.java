package gui.fullStocksWindow.miniStockPanel;

import gui.MyGuiComps;
import gui.fullStocksWindow.FullHeadersPanel;
import serverObjects.stockObjects.STOCK_OBJECT;

public class MiniStockPanel extends MyGuiComps.MyPanel {

    STOCK_OBJECT client;
    MiniTickerPanel tickerPanel;
    MiniRacesPanel racesPanel;

    public MiniStockPanel( STOCK_OBJECT client ) {
        this.client = client;
        initialize();
        initListeners();
    }

    private void initListeners() {
    }

    private void initialize() {

        // This
        setWidth( FullHeadersPanel.racesPanel.getX() + FullHeadersPanel.racesPanel.getWidth() );
        setHeight( 55 );

        // Ticker
        tickerPanel = new MiniTickerPanel( client );
        tickerPanel.setXY( FullHeadersPanel.tickerPanel.getX(), 0 );
        tickerPanel.setWidth( FullHeadersPanel.tickerPanel.getWidth() );
        add( tickerPanel );

        // Races
        racesPanel = new MiniRacesPanel( client );
        racesPanel.setXY( FullHeadersPanel.racesPanel.getX() , 0 );
        racesPanel.setWidth( FullHeadersPanel.racesPanel.getWidth() );
        add( racesPanel );

    }
}
