package gui.panels;

import gui.MyGuiComps;
import gui.fullStocksWindow.FullStocksWindow;
import gui.index.IndexWindow;
import gui.stock.StockWindow;
import locals.LocalHandler;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Ndx;
import serverObjects.indexObjects.Spx;
import serverObjects.stockObjects.*;
import setting.fullSettingWindow.FullSettingWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

public class WindowsPanel extends MyGuiComps.MyPanel {

    // Variables
    JComboBox clientsCombo;
    MyGuiComps.MyButton fullSettingBtn;

    MyGuiComps.MyLabel windowsLbl = new MyGuiComps.MyLabel("Windows");

    // Constructor
    public WindowsPanel() {
        super();
        initCombo();
        initialize();
        initListeners();
    }

    private void initListeners() {
        // Client combo
        clientsCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = clientsCombo.getSelectedItem().toString();

                switch (selected) {
                    case "SPX":
                        new IndexWindow("Spx", Spx.getInstance());
                        break;
                    case "NDX":
                        new IndexWindow("Ndx", Ndx.getInstance());
                        break;
                    case "APPLE":
                        new StockWindow("Apple", Apple.getInstance());
                        break;
                    case "AMAZON":
                        new StockWindow("Amazon", Amazon.getInstance());
                        break;
                    case "ULTA":
                        new StockWindow("Ulta", Ulta.getInstance());
                        break;
                    case "NETFLIX":
                        new StockWindow("Netflix", Netflix.getInstance());
                        break;
                    case "MICROSOFT":
                        new StockWindow("Microsoft", Microsoft.getInstance());
                        break;
                    case "AMD":
                        new StockWindow("Amd", Amd.getInstance());
                        break;
                    case "STOCKS":
                        new FullStocksWindow("Stocks");
                        break;
                    default:
                        break;
                }
            }
        });


        // Full setting
        fullSettingBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FullSettingWindow("Full setting");
            }
        });
    }

    private void initialize() {

        // This
        setBackground(Themes.GREY_LIGHT);
        setWidth(500);
        setHeight(150);

        // Windows lbl
        windowsLbl.setXY(10, 10);
        windowsLbl.setWidth(150);
        windowsLbl.setFont(windowsLbl.getFont().deriveFont(Font.PLAIN));
        windowsLbl.setFont(windowsLbl.getFont().deriveFont(14f));
        windowsLbl.setHorizontalAlignment(JLabel.LEFT);
        windowsLbl.setForeground(Color.BLACK);
        add(windowsLbl);

        // Clients combo
        clientsCombo.setBounds(20, 40, 80, 25);
        clientsCombo.setBackground(Themes.BLUE_DARK);
        clientsCombo.setForeground(Color.WHITE);
        add(clientsCombo);

        // Full setting
        fullSettingBtn = new MyGuiComps.MyButton("Full setting");
        fullSettingBtn.setXY(clientsCombo.getX(), clientsCombo.getY() + clientsCombo.getHeight() + 5);
        fullSettingBtn.setWidth(80);
        add(fullSettingBtn);
    }

    private void initCombo() {
        Set<BASE_CLIENT_OBJECT> clients = LocalHandler.clients;
        String[] clientNames = new String[LocalHandler.clients.size() + 1];
        int i = 0;
        for (BASE_CLIENT_OBJECT client : clients) {
            clientNames[i] = client.getName().toUpperCase();
            i++;
        }
        clientNames[i] = "STOCKS";
        clientsCombo = new JComboBox(clientNames);
    }

}
