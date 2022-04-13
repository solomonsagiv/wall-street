package gui.index;

import exp.Exp;
import exp.ExpStrings;
import gui.MyGuiComps;
import gui.panels.IMyPanel;
import locals.L;
import serverObjects.BASE_CLIENT_OBJECT;

import javax.swing.*;
import java.awt.*;

public class ExpSumPanel extends MyGuiComps.MyPanel implements IMyPanel {

    BASE_CLIENT_OBJECT client;

    MyGuiComps.MyPanel headerPanel;
    MyGuiComps.MyLabel headerLbl;

    MyGuiComps.MyPanel bodyPanel;

    MyGuiComps.MyLabel moveLbl;
    MyGuiComps.MyTextField moveField;

    MyGuiComps.MyLabel v107_Lbl;
    MyGuiComps.MyTextField v107_Field;

    MyGuiComps.MyLabel v103_Lbl;
    MyGuiComps.MyTextField v103_Field;

    Exp exp;

    public ExpSumPanel(BASE_CLIENT_OBJECT client) {
        super();
        this.client = client;
        initsialize();
        this.exp = client.getExps().getExp(ExpStrings.q1);
    }

    int width = 300;

    private void initsialize() {
        setSize(width, 226);
        setPreferredSize(new Dimension(width, 226));

        // ------ Head ------ //
        headerPanel = new MyGuiComps.MyPanel();
        headerPanel.setSize(width, 25);
        headerPanel.setXY(0, 0);
        add(headerPanel);

        headerLbl = new MyGuiComps.MyLabel("Exp");
        headerLbl.setHorizontalAlignment(JLabel.CENTER);
        headerPanel.add(headerLbl);

        // ------ Body ------ //
        bodyPanel = new MyGuiComps.MyPanel();
        bodyPanel.setXY(0, headerPanel.getY() + headerPanel.getHeight() + 1);
        bodyPanel.setSize(width, 200);
        add(bodyPanel);

        // Move
        moveLbl = new MyGuiComps.MyLabel("Move");
        moveLbl.setXY(3, 3);
        bodyPanel.add(moveLbl);

        moveField = new MyGuiComps.MyTextField();
        moveField.setXY(moveLbl.getX() + moveLbl.getWidth() + 3, moveLbl.getY());
        bodyPanel.add(moveField);

        // V107
        v107_Lbl = new MyGuiComps.MyLabel("V107");
        v107_Lbl.setXY(moveLbl.getX(), moveLbl.getY() + moveLbl.getHeight() + 3);
        bodyPanel.add(v107_Lbl);

        v107_Field = new MyGuiComps.MyTextField();
        v107_Field.setXY(moveField.getX(), moveField.getY() + moveField.getHeight() + 3);
        bodyPanel.add(v107_Field);

        // V103
        v103_Lbl = new MyGuiComps.MyLabel("V103");
        v103_Lbl.setXY(v107_Lbl.getX(), v107_Lbl.getY() + v107_Lbl.getHeight() + 3);
        bodyPanel.add(v103_Lbl);

        v103_Field = new MyGuiComps.MyTextField();
        v103_Field.setXY(v107_Field.getX(), v107_Field.getY() + v107_Field.getHeight() + 3);
        bodyPanel.add(v103_Field);

    }

    @Override
    public void updateText() {
        // Get data
        double exp_move = 0;
        if (exp.getStart() != 0) {
            exp_move = L.floor(((client.getIndex() - exp.getStart()) / exp.getStart()) * 100, 100);
        }
        double v107 = exp.getV107();
        double v103 = exp.getV103();

        // Set text
        moveField.colorBack(exp_move, L.format100(), "%");
        v107_Field.colorForge((int) (v107 / 1000));
        v103_Field.colorForge((int) (v103 / 1000));
    }
}
