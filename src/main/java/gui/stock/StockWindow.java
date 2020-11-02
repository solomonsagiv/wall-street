package gui.stock;

import gui.MyGuiComps;
import locals.Themes;
import serverObjects.stockObjects.STOCK_OBJECT;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class StockWindow extends MyGuiComps.MyFrame {

    // Variables
    StockPanel stockPanel;

    // Constructor
    public StockWindow(String title, STOCK_OBJECT client) throws HeadlessException {
        super(title, client);
    }

    @Override
    public void initOnClose() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                stockPanel.close();
            }
        });
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initialize() {

        // This
        setPreferredSize(new Dimension(552, 200));
        setLayout(null);

        // Headers
        HeaderPanel headerPanel = new HeaderPanel();
        headerPanel.setXY(0, 0);
        headerPanel.setBackground(Color.WHITE);
        getContentPane().add(headerPanel);

        // Index panel
        stockPanel = new StockPanel((STOCK_OBJECT) client);
        stockPanel.setBounds(0, 26, 550, 200);
        getContentPane().add(stockPanel);

    }

}

class HeaderPanel extends MyGuiComps.MyPanel {

    public HeaderPanel() {
        super();
        initMe();
        initListeners();
    }

    private void initListeners() {

    }

    private void initMe() {

        // This
        setWidth(550);
        setHeight(25);

        // -------------------- Ticker -------------------- //
        MyGuiComps.MyPanel tickerPanel = new MyGuiComps.MyPanel();
        tickerPanel.setBounds(0, 0, 311, 25);
        add(tickerPanel);

        // Open
        MyGuiComps.MyLabel openLbl = new MyGuiComps.MyLabel("Open");
        openLbl.setFont(Themes.VEDANA_12.deriveFont(Font.BOLD));
        openLbl.setXY(5, 0);
        tickerPanel.add(openLbl);

        // Last
        MyGuiComps.MyLabel lastLbl = new MyGuiComps.MyLabel("Last");
        lastLbl.setFont(Themes.VEDANA_12.deriveFont(Font.BOLD));
        lastLbl.setXY(85, 0);
        tickerPanel.add(lastLbl);

        // Low
        MyGuiComps.MyLabel lowLbl = new MyGuiComps.MyLabel("Low");
        lowLbl.setFont(Themes.VEDANA_12.deriveFont(Font.BOLD));
        lowLbl.setXY(160, 0);
        tickerPanel.add(lowLbl);

        // High
        MyGuiComps.MyLabel highLbl = new MyGuiComps.MyLabel("High");
        highLbl.setFont(Themes.VEDANA_12.deriveFont(Font.BOLD));
        highLbl.setXY(235, 0);
        tickerPanel.add(highLbl);

        // -------------------- Races and roll -------------------- //
        MyGuiComps.MyPanel racesAndRollPanel = new MyGuiComps.MyPanel();
        racesAndRollPanel.setBounds(312, 0, 111, 25);
        add(racesAndRollPanel);

        MyGuiComps.MyLabel racesLbl = new MyGuiComps.MyLabel("Races");
        racesLbl.setFont(Themes.VEDANA_12.deriveFont(Font.BOLD));
        racesLbl.setBounds(0, 0, racesAndRollPanel.getWidth(), racesAndRollPanel.getHeight());
        racesAndRollPanel.add(racesLbl);

        // -------------------- Exp -------------------- //
        MyGuiComps.MyPanel expPanel = new MyGuiComps.MyPanel();
        expPanel.setBounds(424, 0, 112, 25);
        add(expPanel);

        MyGuiComps.MyLabel expLbl = new MyGuiComps.MyLabel("Exp");

        expLbl.setFont(Themes.VEDANA_12.deriveFont(Font.BOLD));
        expLbl.setBounds(0, 0, expPanel.getWidth(), expPanel.getHeight());
        expPanel.add(expLbl);

    }

}
