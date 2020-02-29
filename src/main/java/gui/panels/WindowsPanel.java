package gui.panels;

import gui.MyGuiComps;
import locals.Themes;

import javax.swing.*;
import java.awt.*;

public class WindowsPanel extends MyGuiComps.MyPanel {


    // Variables
    String[] clientName = {"Spx", "Ndx", "Apple"};
    JComboBox clientsCombo = new JComboBox(clientName);

    MyGuiComps.MyLabel windowsLbl = new MyGuiComps.MyLabel("Windows");

    // Constructor
    public WindowsPanel() {
        super();
        initialize();
    }

    private void initialize() {

        // This
        setBackground(Themes.GREY_VERY_LIGHT);
        setWidth(200);
        setHeight(400);

        // Windows lbl
        windowsLbl.setXY(10, 10);
        windowsLbl.setWidth(150);
        windowsLbl.setFont(windowsLbl.getFont().deriveFont(Font.PLAIN));
        windowsLbl.setFont(windowsLbl.getFont().deriveFont(18f));
        windowsLbl.setHorizontalAlignment(JLabel.LEFT);
        windowsLbl.setForeground(Color.WHITE);
        add(windowsLbl);

    }

}
