package gui.index;

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

public class ExpSumPanel extends MyGuiComps.MyPanel implements IMyPanel {

    BASE_CLIENT_OBJECT client;

    MyGuiComps.MyPanel headerPanel;
    MyGuiComps.MyLabel weekLbl;

    MyGuiComps.MyPanel bodyPanel;

    // Week
    MyGuiComps.MyTextField moveField_week_field;
    MyGuiComps.MyTextField index_races_week_field;
    MyGuiComps.MyTextField q1_races_week_field;
    MyGuiComps.MyTextField df_8_Field_week;

    // DF exp
    MyGuiComps.MyTextField df_week_field;

    MyTimeSeries
            index_races_week, q1_races_week,
            exp_week_start;

    Exp exp;

    public ExpSumPanel(BASE_CLIENT_OBJECT client) {
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

    int width = 200;

    private void initsialize() {
        setSize(width, 226);
        setPreferredSize(new Dimension(width, 226));

        // ------ Head ------ //
        headerPanel = new MyGuiComps.MyPanel();
        headerPanel.setSize(width, 25);
        headerPanel.setXY(0, 0);
        add(headerPanel);

        weekLbl = new MyGuiComps.MyLabel("Week", true);
        weekLbl.setHorizontalAlignment(JLabel.CENTER);
        weekLbl.setXY(5, 0);
        headerPanel.add(weekLbl);


        // ------ Body ------ //
        bodyPanel = new MyGuiComps.MyPanel();
        bodyPanel.setXY(0, headerPanel.getY() + headerPanel.getHeight() + 1);
        bodyPanel.setSize(width, 200);
        add(bodyPanel);

        // ---------------------- Week ---------------------- //
        // DF 2
        index_races_week_field = new MyGuiComps.MyTextField();
        index_races_week_field.setXY(3, 3);
        bodyPanel.add(index_races_week_field);

        // DF 7
        q1_races_week_field = new MyGuiComps.MyTextField();
        q1_races_week_field.setXY(index_races_week_field.getX(), index_races_week_field.getY() + index_races_week_field.getHeight() + 1);
        bodyPanel.add(q1_races_week_field);

        // DF 8
        df_8_Field_week = new MyGuiComps.MyTextField();
        df_8_Field_week.setXY(q1_races_week_field.getX(), q1_races_week_field.getY() + q1_races_week_field.getHeight() + 1);
        bodyPanel.add(df_8_Field_week);


        moveField_week_field = new MyGuiComps.MyTextField();
        moveField_week_field.setXY(df_8_Field_week.getX(), df_8_Field_week.getY() + df_8_Field_week.getHeight() + 1);
        bodyPanel.add(moveField_week_field);


        // Df exp
        df_week_field = new MyGuiComps.MyTextField();
        df_week_field.setXY(moveField_week_field.getX(), moveField_week_field.getY() + moveField_week_field.getHeight() + 1);
        bodyPanel.add(df_week_field);

    }
    
    @Override
    public void updateText() {

        double week_start = exp_week_start.get_value_with_exp();
        double week_move = L.floor(((client.getIndex() - week_start) / week_start) * 100, 100);

        // Set text
        moveField_week_field.colorBack(week_move, L.format100(), "%");
        index_races_week_field.colorForge((int) ((index_races_week.get_value_with_exp())));
        q1_races_week_field.colorForge((int) ((q1_races_week.get_value_with_exp())));

    }
}
