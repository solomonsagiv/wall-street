package gui.fullStocksWindow.miniStockPanel;

import gui.MyGuiComps;
import locals.Themes;
import org.apache.commons.lang3.StringUtils;
import serverObjects.stockObjects.STOCK_OBJECT;

import javax.swing.*;

public class MiniStockNamePanel extends MyGuiComps.MyPanel {

    STOCK_OBJECT client;
    MyGuiComps.MyLabel nameLbl;

    public MiniStockNamePanel(STOCK_OBJECT client) {
        this.client = client;
        initialize();
        initListeners();
    }

    private void initListeners() {}

    private void initialize() {
        // This

        // Name
        nameLbl = new MyGuiComps.MyLabel(StringUtils.capitalize(client.getName()));
        nameLbl.setFont(Themes.ARIEL_BOLD_12);
        nameLbl.setForeground(Themes.ORANGE);
        nameLbl.setHorizontalAlignment(JLabel.LEFT);
        nameLbl.setXY(5, 0);
        nameLbl.setSize(65, 55);
        add(nameLbl);

    }


}
