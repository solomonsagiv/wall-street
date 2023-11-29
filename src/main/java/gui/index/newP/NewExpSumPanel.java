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
    MyGuiComps.MyLabel weekLbl, month_Lbl, exp_lbl;
    MyGuiComps.MyPanel bodyPanel;

    // Week
    MyGuiComps.MyTextField moveField_week;
    MyGuiComps.MyTextField basketsField_week;
    MyGuiComps.MyTextField df_2_Field_week;
    MyGuiComps.MyTextField df_2_roll_Field_week;

    // Month
    MyGuiComps.MyTextField move_move_field;
    MyGuiComps.MyTextField df_2_Field_month;
    MyGuiComps.MyTextField basketsField_month;
    MyGuiComps.MyTextField df_2_roll_Field_month;

    MyTimeSeries df_2_cdf, df_2_roll_cdf, df_2_month, df_2_roll_month,
            df_2_week, df_2_roll_week, baskets_week, baskets_month,
            exp_week_start, exp_month_start;

    Exp exp;

    public NewExpSumPanel(BASE_CLIENT_OBJECT client) {
        super();
        this.client = client;
        initsialize();
        this.exp = client.getExps().getExp(ExpStrings.q1);

        // CDF
        df_2_cdf = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_2_CDF);
        df_2_roll_cdf = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_2_ROLL_CDF);

        // Week
        df_2_week = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_2_WEEK);
        df_2_roll_week = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_2_ROLL_WEEK);
        baskets_week = client.getTimeSeriesHandler().get(TimeSeriesFactory.BASKETS_WEEK);

        // Month
//        df_2_month = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_2_ROLL_WEEK);
//        df_2_roll_month = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_2_ROLL_WEEK);
//        df_2_month = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_2_ROLL_WEEK);

        // Exp start
        exp_week_start = client.getTimeSeriesHandler().get(TimeSeriesFactory.EXP_WEEK_START);
        exp_month_start = client.getTimeSeriesHandler().get(TimeSeriesFactory.EXP_MONTH_START);
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

        // Baskets
        basketsField_week = new MyGuiComps.MyTextField();
        basketsField_week.setXY(moveField_week.getX() + moveField_week.getWidth() + 1, moveField_week.getY());
        bodyPanel.add(basketsField_week);

        // DF 2
        df_2_Field_week = new MyGuiComps.MyTextField();
        df_2_Field_week.setXY(basketsField_week.getX() + basketsField_week.getWidth() + 1, basketsField_week.getY());
        bodyPanel.add(df_2_Field_week);

        // DF 2 Roll
        df_2_roll_Field_week = new MyGuiComps.MyTextField();
        df_2_roll_Field_week.setXY(df_2_Field_week.getX() + df_2_Field_week.getWidth() + 1, df_2_Field_week.getY());
        bodyPanel.add(df_2_roll_Field_week);

        // ---------------------- Month ---------------------- //

        // Month lbl
        month_Lbl = new MyGuiComps.MyLabel("Q1", true);
        month_Lbl.setHorizontalAlignment(JLabel.CENTER);
        month_Lbl.setXY(weekLbl.getX(), weekLbl.getY() + weekLbl.getHeight() + 3);
        headerPanel.add(month_Lbl);

        // Move
        move_move_field = new MyGuiComps.MyTextField();
        move_move_field.setXY(month_Lbl.getX() + month_Lbl.getWidth() + 1, month_Lbl.getY());
        bodyPanel.add(move_move_field);

        // Baskets
        basketsField_month = new MyGuiComps.MyTextField();
        basketsField_month.setXY(move_move_field.getX() + move_move_field.getWidth() + 1, move_move_field.getY());
        bodyPanel.add(basketsField_month);

        // DF 2
        df_2_Field_month = new MyGuiComps.MyTextField();
        df_2_Field_month.setXY(basketsField_month.getX() + basketsField_month.getWidth() + 1, basketsField_month.getY());
        bodyPanel.add(df_2_Field_month);

        // DF 2 Roll
        df_2_roll_Field_month = new MyGuiComps.MyTextField();
        df_2_roll_Field_month.setXY(df_2_Field_month.getX() + df_2_Field_month.getWidth() + 1, df_2_Field_month.getY());
        bodyPanel.add(df_2_roll_Field_month);
    }

    @Override
    public void updateText() {
        // Week
        double week_start = exp_week_start.getValue();
        double week_move = L.floor(((client.getIndex() - week_start) / week_start) * 100, 100);
        moveField_week.colorBack(week_move, L.format100(), "%");

        df_2_Field_week.colorForge((int) ((df_2_week.get_value_with_exp())));
//        df_2_roll_Field_week.colorForge((int) ((df_2_roll_week.get_value_with_exp()) / 1000));
        basketsField_week.colorForge((int) baskets_week.get_value_with_exp());

        // Month
        move_move_field.colorForge((int) exp_month_start.getValue());

    }
}

