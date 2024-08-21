package gui.index.newP;

import charts.timeSeries.MyTimeSeries;
import charts.timeSeries.TimeSeriesFactory;
import exp.Exp;
import exp.ExpStrings;
import gui.MyGuiComps;
import gui.panels.IMyPanel;
import locals.L;
import serverObjects.BASE_CLIENT_OBJECT;

import javax.swing.*;
import java.awt.*;

public class NewExpSumPanel extends MyGuiComps.MyPanel implements IMyPanel {

    BASE_CLIENT_OBJECT client;

    MyGuiComps.MyPanel headerPanel;
    MyGuiComps.MyLabel weekLbl, exp_lbl;
    MyGuiComps.MyPanel bodyPanel;

    // Week
    MyGuiComps.MyTextField moveField_week;
    MyGuiComps.MyTextField index_races_week_field;
    MyGuiComps.MyTextField q1_races_week_field;

    MyTimeSeries
            index_races_week, q1_races_week,
            exp_week_start;

    Exp exp;

    public NewExpSumPanel(BASE_CLIENT_OBJECT client) {
        super();
        this.client = client;
        initsialize();
        this.exp = client.getExps().getExp(ExpStrings.q1);

        // Week
        index_races_week = client.getTimeSeriesHandler().get(TimeSeriesFactory.INDEX_RACES);
        q1_races_week = client.getTimeSeriesHandler().get(TimeSeriesFactory.Q1_RACES);

        // Exp start
        exp_week_start = client.getTimeSeriesHandler().get(TimeSeriesFactory.EXP_WEEK_START);
    }

    int width = 550;

    private void initsialize() {
        setSize(width, 226);
        setPreferredSize(new Dimension(width, 226));
        setBackground(Color.WHITE);

        // ------ Head ------ //
        headerPanel = new MyGuiComps.MyPanel();
        headerPanel.setSize(width, 25);
        headerPanel.setXY(0, 0);
        add(headerPanel);

        // Exp
        exp_lbl = new MyGuiComps.MyLabel("Exp", true);
        exp_lbl.setXY(0, 0);
        headerPanel.add(exp_lbl);

        // ------ Body ------ //
        bodyPanel = new MyGuiComps.MyPanel();
        bodyPanel.setXY(0, headerPanel.getY() + headerPanel.getHeight() + 1);
        bodyPanel.setSize(width, 200);
        add(bodyPanel);

        // ---------------------- Week ---------------------- //

        // Week lbl
        weekLbl = new MyGuiComps.MyLabel("Week", true);
        weekLbl.setHorizontalAlignment(JLabel.CENTER);
        weekLbl.setXY(0, 3);
        bodyPanel.add(weekLbl);

        // Move
        moveField_week = new MyGuiComps.MyTextField();
        moveField_week.setXY(weekLbl.getX() + weekLbl.getWidth() + 1, weekLbl.getY());
        bodyPanel.add(moveField_week);

        // Index races
        index_races_week_field = new MyGuiComps.MyTextField();
        index_races_week_field.setXY(moveField_week.getX() + moveField_week.getWidth() + 1, moveField_week.getY());
        bodyPanel.add(index_races_week_field);

        // Q1 races 
        q1_races_week_field = new MyGuiComps.MyTextField();
        q1_races_week_field.setXY(index_races_week_field.getX() + index_races_week_field.getWidth() + 1, index_races_week_field.getY());
        bodyPanel.add(q1_races_week_field);
    }

    @Override
    public void updateText() {
        // Week
        double week_start = exp_week_start.getExp_data();
        double week_move = L.floor(((client.getIndex() - week_start) / week_start) * 100, 100);
        moveField_week.colorBack(week_move, L.format100(), "%");

        index_races_week_field.colorForge((int) ((index_races_week.get_value_with_exp())));
        q1_races_week_field.colorForge((int) ((q1_races_week.get_value_with_exp())));
    }
}

