package gui.index;

import gui.MyGuiComps;
import gui.panels.IMyPanel;
import serverObjects.BASE_CLIENT_OBJECT;

import javax.swing.*;

public class ExpSumPanel extends MyGuiComps.MyPanel implements IMyPanel {

    BASE_CLIENT_OBJECT client;

    MyGuiComps.MyPanel headerPanel;
    MyGuiComps.MyLabel headerLbl;

    MyGuiComps.MyPanel bodyPanel;

    MyGuiComps.MyLabel moveLbl;
    MyGuiComps.MyTextField moveField;

    public ExpSumPanel(BASE_CLIENT_OBJECT client) {
        super();
        this.client = client;
        initsialize();
    }

    private void initsialize() {
        setSize(111, 226);

        // ------ Head ------ //
        headerPanel = new MyGuiComps.MyPanel();
        headerPanel.setSize(111, 25);
        headerPanel.setXY(0, 0);
        add(headerPanel);

        headerLbl = new MyGuiComps.MyLabel("Exp");
        headerLbl.setHorizontalAlignment(JLabel.CENTER);
        headerPanel.add(headerLbl);

        // ------ Body ------ //
        bodyPanel = new MyGuiComps.MyPanel();
        bodyPanel.setXY(0, headerPanel.getY() + headerPanel.getHeight() + 1);
        bodyPanel.setSize(111, 200);
        add(bodyPanel);

        // Move
        moveLbl = new MyGuiComps.MyLabel("Move");
        moveLbl.setXY(3, 3);
        bodyPanel.add(moveLbl);

        moveField = new MyGuiComps.MyTextField();
        moveField.setXY(moveLbl.getX() + moveLbl.getWidth() + 3, moveLbl.getY());
        bodyPanel.add(moveField);

    }

    @Override
    public void updateText() {
        //TODO
    }
}
