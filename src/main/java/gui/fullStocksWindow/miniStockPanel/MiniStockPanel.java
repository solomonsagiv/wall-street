package gui.fullStocksWindow.miniStockPanel;

import gui.MyGuiComps;
import gui.fullStocksWindow.FullHeadersPanel;
import gui.popupsFactory.PopupsMenuFactory;
import serverObjects.stockObjects.STOCK_OBJECT;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MiniStockPanel extends MyGuiComps.MyPanel implements IMiniPanel {

    STOCK_OBJECT client;

    MiniStockNamePanel namePanel;
    MiniTickerPanel tickerPanel;
    MiniRacesPanel racesPanel;

    public MiniStockPanel(STOCK_OBJECT client) {
        this.client = client;
        initialize();
        initListeners();
    }

    private void initListeners() {
        // Right click
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                if (event.getModifiers() == MouseEvent.BUTTON3_MASK) {
                    showPopUpMenu(event);
                }
            }
        });
    }

    public void showPopUpMenu(MouseEvent event) {
        JPopupMenu menu = PopupsMenuFactory.stockPanel(client);
        // Show the menu
        menu.show(event.getComponent(), event.getX(), event.getY());
    }

    private void initialize() {

        // This
        setWidth(FullHeadersPanel.racesPanel.getX() + FullHeadersPanel.racesPanel.getWidth());
        setHeight(55);

        // Name
        namePanel = new MiniStockNamePanel(client);
        namePanel.setBounds(0, 0 , 60, getHeight() );
        add(namePanel);

        // Ticker
        tickerPanel = new MiniTickerPanel(client);
        tickerPanel.setXY(FullHeadersPanel.tickerPanel.getX(), 0);
        tickerPanel.setWidth(FullHeadersPanel.tickerPanel.getWidth());
        add(tickerPanel);

        // Races
        racesPanel = new MiniRacesPanel(client);
        racesPanel.setXY(FullHeadersPanel.racesPanel.getX(), 0);
        racesPanel.setWidth(FullHeadersPanel.racesPanel.getWidth());
        add(racesPanel);

    }

    @Override
    public void updateText() {
        tickerPanel.updateText();
        racesPanel.updateText();
    }

}
