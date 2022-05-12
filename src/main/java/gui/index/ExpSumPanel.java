package gui.index;

import exp.Exp;
import exp.ExpStrings;
import gui.MyGuiComps;
import gui.panels.IMyPanel;
import jibeDataGraber.DecisionsFunc;
import jibeDataGraber.DecisionsFuncFactory;
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

    MyGuiComps.MyLabel v7_Lbl;
    MyGuiComps.MyTextField v7_Field;

    MyGuiComps.MyLabel v3_Lbl;
    MyGuiComps.MyTextField v3_Field;

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
        v7_Lbl = new MyGuiComps.MyLabel("0");
        v7_Lbl.setXY(moveLbl.getX(), moveLbl.getY() + moveLbl.getHeight() + 3);
        bodyPanel.add(v7_Lbl);

        v7_Field = new MyGuiComps.MyTextField();
        v7_Field.setXY(moveField.getX(), moveField.getY() + moveField.getHeight() + 3);
        bodyPanel.add(v7_Field);

        // V103
        v3_Lbl = new MyGuiComps.MyLabel("4");
        v3_Lbl.setXY(v7_Lbl.getX(), v7_Lbl.getY() + v7_Lbl.getHeight() + 3);
        bodyPanel.add(v3_Lbl);

        v3_Field = new MyGuiComps.MyTextField();
        v3_Field.setXY(v7_Field.getX(), v7_Field.getY() + v7_Field.getHeight() + 3);
        bodyPanel.add(v3_Field);

    }

    @Override
    public void updateText() {
        // Get data
        double exp_move = 0;
        if (exp.getStart() != 0) {
            exp_move = L.floor(((client.getIndex() - exp.getStart()) / exp.getStart()) * 100, 100);
        }

        DecisionsFunc v7 = client.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_7);

        double v7_exp = exp.getV107() + v7.getValue();

        // Set text
        moveField.colorBack(exp_move, L.format100(), "%");
        v7_Field.colorForge((int) (v7_exp / 1000));
    }
}
