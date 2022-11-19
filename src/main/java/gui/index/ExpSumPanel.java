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
    MyGuiComps.MyLabel monthLbl;
    MyGuiComps.MyLabel q1Lbl;

    MyGuiComps.MyPanel bodyPanel;

    // Week
    MyGuiComps.MyTextField moveField_week;
    MyGuiComps.MyTextField df_2_Field_week;
    MyGuiComps.MyTextField df_7_Field_week;
    MyGuiComps.MyTextField df_8_Field_week;

    // Month
    MyGuiComps.MyTextField moveField_month;
    MyGuiComps.MyTextField df_2_Field_month;
    MyGuiComps.MyTextField df_7_Field_month;
    MyGuiComps.MyTextField df_8_Field_month;

    // Q1
    MyGuiComps.MyTextField moveField_q1;
    MyGuiComps.MyTextField df_2_Field_q1;
    MyGuiComps.MyTextField df_7_Field_q1;
    MyGuiComps.MyTextField df_8_Field_q1;

    // DF exp
    MyGuiComps.MyTextField df_week_field, df_month_field, df_weighted_field;

    MyTimeSeries df_2_cdf, df_7_cdf, df_8_cdf,
            df_2_week, df_7_week, df_8_week,
            df_2_month, df_7_month, df_8_month,
            df_2_q1, df_7_q1, df_8_q1,
            exp_week_start, exp_month_start, exp_q1_start,
            df_week, df_month, df_weighted;

    Exp exp;

    public ExpSumPanel(BASE_CLIENT_OBJECT client) {
        super();
        this.client = client;
        initsialize();
        this.exp = client.getExps().getExp(ExpStrings.q1);

        // CDF
        df_2_cdf = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_2_CDF);
        df_7_cdf = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_7_CDF);
        df_8_cdf = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_8_CDF);

        // Week
        df_2_week = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_2_WEEK);
        df_7_week = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_7_WEEK);
        df_8_week = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_8_WEEK);

        // Month
        df_2_month = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_2_MONTH);
        df_7_month = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_7_MONTH);
        df_8_month = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_8_MONTH);

        // Q1
        df_2_q1 = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_2_Q1);
        df_7_q1 = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_7_Q1);
        df_8_q1 = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_8_Q1);

        // Exp start
        exp_week_start = client.getTimeSeriesHandler().get(TimeSeriesFactory.EXP_WEEK_START);
        exp_month_start = client.getTimeSeriesHandler().get(TimeSeriesFactory.EXP_MONTH_START);
        exp_q1_start = client.getTimeSeriesHandler().get(TimeSeriesFactory.EXP_Q1_START);

        // DF Exp
        df_week = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_WEEK);
        df_month = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_MONTH);
        df_weighted = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_WEIGHTED);
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

        weekLbl = new MyGuiComps.MyLabel("Week", true);
        weekLbl.setHorizontalAlignment(JLabel.CENTER);
        weekLbl.setXY(5, 0);
        headerPanel.add(weekLbl);


        monthLbl = new MyGuiComps.MyLabel("Month", true);
        monthLbl.setHorizontalAlignment(JLabel.CENTER);
        monthLbl.setXY(weekLbl.getX() + weekLbl.getWidth() + 7, weekLbl.getY());
        headerPanel.add(monthLbl);

        q1Lbl = new MyGuiComps.MyLabel("Q1", true);
        q1Lbl.setHorizontalAlignment(JLabel.CENTER);
        q1Lbl.setXY(monthLbl.getX() + monthLbl.getWidth() + 7, monthLbl.getY());
        headerPanel.add(q1Lbl);

        // ------ Body ------ //
        bodyPanel = new MyGuiComps.MyPanel();
        bodyPanel.setXY(0, headerPanel.getY() + headerPanel.getHeight() + 1);
        bodyPanel.setSize(width, 200);
        add(bodyPanel);

        // ---------------------- Week ---------------------- //
        // DF 2
        df_2_Field_week = new MyGuiComps.MyTextField();
        df_2_Field_week.setXY(3, 3);
        bodyPanel.add(df_2_Field_week);

        // DF 7
        df_7_Field_week = new MyGuiComps.MyTextField();
        df_7_Field_week.setXY(df_2_Field_week.getX(), df_2_Field_week.getY() + df_2_Field_week.getHeight() + 1);
        bodyPanel.add(df_7_Field_week);

        // DF 8
        df_8_Field_week = new MyGuiComps.MyTextField();
        df_8_Field_week.setXY(df_7_Field_week.getX(), df_7_Field_week.getY() + df_7_Field_week.getHeight() + 1);
        bodyPanel.add(df_8_Field_week);


        moveField_week = new MyGuiComps.MyTextField();
        moveField_week.setXY(df_8_Field_week.getX(), df_8_Field_week.getY() + df_8_Field_week.getHeight() + 1);
        bodyPanel.add(moveField_week);

        // ---------------------- Month ---------------------- //
        // DF 2
        df_2_Field_month = new MyGuiComps.MyTextField();
        df_2_Field_month.setXY(df_2_Field_week.getX() + df_2_Field_week.getWidth() + 3, df_2_Field_week.getY());
        bodyPanel.add(df_2_Field_month);

        // DF 7
        df_7_Field_month = new MyGuiComps.MyTextField();
        df_7_Field_month.setXY(df_2_Field_month.getX(), df_2_Field_month.getY() + df_2_Field_month.getHeight() + 1);
        bodyPanel.add(df_7_Field_month);

        // DF 8
        df_8_Field_month = new MyGuiComps.MyTextField();
        df_8_Field_month.setXY(df_7_Field_month.getX(), df_7_Field_month.getY() + df_7_Field_month.getHeight() + 1);
        bodyPanel.add(df_8_Field_month);


        moveField_month = new MyGuiComps.MyTextField();
        moveField_month.setXY(df_8_Field_month.getX(), df_8_Field_month.getY() + df_8_Field_month.getHeight() + 1);
        bodyPanel.add(moveField_month);

        // ---------------------- Q1 ---------------------- //
        // DF 2
        df_2_Field_q1 = new MyGuiComps.MyTextField();
        df_2_Field_q1.setXY(df_2_Field_month.getX() + df_2_Field_month.getWidth() + 3, df_2_Field_month.getY());
        bodyPanel.add(df_2_Field_q1);

        // DF 7
        df_7_Field_q1 = new MyGuiComps.MyTextField();
        df_7_Field_q1.setXY(df_2_Field_q1.getX(), df_2_Field_q1.getY() + df_2_Field_q1.getHeight() + 1);
        bodyPanel.add(df_7_Field_q1);

        // DF 8
        df_8_Field_q1 = new MyGuiComps.MyTextField();
        df_8_Field_q1.setXY(df_7_Field_q1.getX(), df_7_Field_q1.getY() + df_7_Field_q1.getHeight() + 1);
        bodyPanel.add(df_8_Field_q1);

        // Move
        moveField_q1 = new MyGuiComps.MyTextField();
        moveField_q1.setXY(df_8_Field_q1.getX(), df_8_Field_q1.getY() + df_8_Field_q1.getHeight() + 1);
        bodyPanel.add(moveField_q1);


        // Df exp
        df_week_field = new MyGuiComps.MyTextField();
        df_week_field.setXY(moveField_week.getX(), moveField_week.getY() + moveField_week.getHeight() + 1);
        bodyPanel.add(df_week_field);

        df_month_field = new MyGuiComps.MyTextField();
        df_month_field.setXY(moveField_month.getX(), moveField_month.getY() + moveField_month.getHeight() + 1);
        bodyPanel.add(df_month_field);

        df_weighted_field = new MyGuiComps.MyTextField();
        df_weighted_field.setXY(moveField_q1.getX(), moveField_q1.getY() + moveField_q1.getHeight() + 1);
        bodyPanel.add(df_weighted_field);

    }

    @Override
    public void updateText() {
        double week_start = exp_week_start.get_value_with_exp();
        double month_start = exp_month_start.get_value_with_exp();
        double q1_start = exp_q1_start.get_value_with_exp();

        double week_move = L.floor(((client.getIndex() - week_start) / week_start) * 100, 100);
        double month_move = L.floor(((client.getIndex() - month_start) / month_start) * 100, 100);
        double q1_move = L.floor(((client.getIndex() - q1_start) / q1_start) * 100, 100);

        // Set text
        moveField_week.colorBack(week_move, L.format100(), "%");
        moveField_month.colorBack(month_move, L.format100(), "%");
        moveField_q1.colorBack(q1_move, L.format100(), "%");

        try {
            // Week
            df_2_Field_week.colorForge((int) ((df_2_week.get_value_with_exp() + df_2_cdf.getValue()) / 1000));
            df_7_Field_week.colorForge((int) ((df_7_week.get_value_with_exp() + df_7_cdf.getValue()) / 1000));
            df_8_Field_week.colorForge((int) ((df_8_week.get_value_with_exp() + df_8_cdf.getValue()) / 1000));

            // Month
            df_2_Field_month.colorForge((int) ((df_2_month.get_value_with_exp() + df_2_cdf.getValue()) / 1000));
            df_7_Field_month.colorForge((int) ((df_7_month.get_value_with_exp() + df_7_cdf.getValue()) / 1000));
            df_8_Field_month.colorForge((int) ((df_8_month.get_value_with_exp() + df_8_cdf.getValue()) / 1000));

            // Week
            df_2_Field_q1.colorForge((int) ((df_2_q1.get_value_with_exp() + df_2_cdf.getValue()) / 1000));
            df_7_Field_q1.colorForge((int) ((df_7_q1.get_value_with_exp() + df_7_cdf.getValue()) / 1000));
            df_8_Field_q1.colorForge((int) ((df_8_q1.get_value_with_exp() + df_8_cdf.getValue()) / 1000));

            // DF exp
            df_week_field.colorForge((int) df_week.getValue());
            df_month_field.colorForge((int) df_month.getValue());
            df_weighted_field.colorForge((int) df_weighted.getValue());
        } catch (Exception e) {
//            e.printStackTrace();
        }

    }
}
