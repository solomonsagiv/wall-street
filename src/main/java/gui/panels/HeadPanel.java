package gui.panels;

import gui.MyGuiComps;
import locals.Themes;

import java.awt.*;

public class HeadPanel extends MyGuiComps.MyPanel {

    // Variables
    MyGuiComps.MyLabel titleLbl = new MyGuiComps.MyLabel("Wall Street");

    // Constructor
    public HeadPanel() {
        super();
        initialize();
    }

    private void initialize() {

        // This
        setWidth(500);
        setHeight(100);
        setBackground(Themes.BLUE_DARK);

        // Title lbl
        titleLbl.setBounds(0, 0, getWidth(), getHeight());
        titleLbl.setBackground(Color.BLACK);
        titleLbl.setForeground(Color.WHITE);
        titleLbl.setFont(titleLbl.getFont().deriveFont(24f));
        add(titleLbl);

    }

}
