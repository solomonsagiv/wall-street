package gui.index;

import gui.MyGuiComps;
import serverObjects.indexObjects.INDEX_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class IndexWindow extends MyGuiComps.MyFrame {

    // Variables
    IndexPanel indexPanel;

    // Constructor
    public IndexWindow(String title, INDEX_CLIENT_OBJECT client) throws HeadlessException {
        super(title, client);
    }

    public static void main(String[] args) {

        Spx dax = Spx.getInstance();
        new IndexWindow("Dax", dax);
    }

    @Override
    public void initOnClose() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                indexPanel.close();
            }
        });
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initialize() {

        // This
        setPreferredSize(new Dimension(492, 222));
        setLayout(null);

        // Index panel
        indexPanel = new IndexPanel((INDEX_CLIENT_OBJECT) client);
        indexPanel.setBounds(0, 0, 550, 500);
        getContentPane().add(indexPanel);

    }
}