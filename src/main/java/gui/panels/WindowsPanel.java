package gui.panels;

import gui.MyGuiComps;
import gui.index.IndexWindow;
import locals.LocalHandler;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.*;
import setting.clientSetting.SettingWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

public class WindowsPanel extends MyGuiComps.MyPanel {

    // Variables
    JComboBox clientsCombo;
    MyGuiComps.MyButton setting_btn;

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
                        new IndexWindow("Spx window", Spx.getInstance());
                        break;
                    case "NDX":
                        new IndexWindow("Ndx window", Ndx.getInstance());
                        break;
                    default:
                        break;
                }
            }
        });

        // Setting
        setting_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Spx client = Spx.getInstance();
                new SettingWindow(client.getName() + " setting", client);
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
        setting_btn = new MyGuiComps.MyButton("Setting");
        setting_btn.setXY(clientsCombo.getX(), clientsCombo.getY() + clientsCombo.getHeight() + 5);
        setting_btn.setWidth(80);
        add(setting_btn);
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
