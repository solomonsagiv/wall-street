package gui.fullStocksWindow.miniStockPanel;

import gui.MyGuiComps;
import gui.fullStocksWindow.FullHeadersPanel;
import serverObjects.stockObjects.STOCK_OBJECT;

public class MiniRacesPanel extends MyGuiComps.MyPanel implements IMiniPanel {

    STOCK_OBJECT client;
    MyGuiComps.MyTextField conRacesField;
    MyGuiComps.MyTextField indRacesField;

    public MiniRacesPanel(STOCK_OBJECT client) {
        this.client = client;
        initialize();
        initListeners();
    }

    private void initListeners() {

    }

    private void initialize() {
        // This
        setWidth(100);
        setHeight(70);

        // Con races
        conRacesField = new MyGuiComps.MyTextField();
        conRacesField.setBackground(getBackground());
        conRacesField.setXY(FullHeadersPanel.conRacesLbl.getX(), 5);
        conRacesField.setWidth(FullHeadersPanel.conRacesLbl.getWidth());
        add(conRacesField);

        // Ind races
        indRacesField = new MyGuiComps.MyTextField();
        indRacesField.setBackground(getBackground());
        indRacesField.setXY(FullHeadersPanel.indRacesLbl.getX(), 5);
        indRacesField.setWidth(FullHeadersPanel.indRacesLbl.getWidth());
        add(indRacesField);

    }

    @Override
    public void updateText() {

        // Races
        conRacesField.colorForge(client.getFutSum());
        indRacesField.colorForge(client.getIndexSum());

    }


}
