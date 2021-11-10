package arik.window;

import gui.MyGuiComps;

import java.awt.*;

public class ArikWindow extends MyGuiComps.MyFrame {

    public static void main(String[] args) {
        ArikWindow arikWindow = new ArikWindow("Arik window");
    }

    ArikMainPanel arikMainPanel;

    public ArikWindow(String title) throws HeadlessException {
        super(title);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initialize() {

        // This
        getContentPane().setLayout(null);
        setPreferredSize(new Dimension(500, 500));

        // Main panel
        arikMainPanel = new ArikMainPanel();
        arikMainPanel.setXY(0, 0);
        arikMainPanel.setWidth(500);
        arikMainPanel.setHeight(500);
        add(arikMainPanel);
    }

    @Override
    public void onClose() {
        super.onClose();
        System.exit(0);
    }
}
