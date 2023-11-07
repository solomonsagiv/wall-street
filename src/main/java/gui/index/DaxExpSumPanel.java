package gui.index;

import charts.timeSeries.MyTimeSeries;
import charts.timeSeries.TimeSeriesFactory;
import exp.Exp;
import exp.ExpStrings;
import gui.MyGuiComps;
import gui.panels.IMyPanel;
import locals.L;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Ndx;
import serverObjects.indexObjects.Spx;

import javax.swing.*;
import java.awt.*;

public class DaxExpSumPanel extends MyGuiComps.MyPanel implements IMyPanel {

    BASE_CLIENT_OBJECT client;

    MyGuiComps.MyPanel headerPanel;
    MyGuiComps.MyLabel weekLbl, month_Lbl;

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

    // DF exp
    MyGuiComps.MyTextField df_week_move_field;

    MyTimeSeries df_2_cdf, df_7_cdf, df_8_cdf,
            df_2_week, df_7_week, df_8_week,
            df_2_month, df_7_month, df_8_month,
            exp_week_start, exp_month_start,
            df_week, df_month, df_weighted;

    Exp exp;
    
    public DaxExpSumPanel(BASE_CLIENT_OBJECT client) {
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

        // Exp start
        exp_week_start = client.getTimeSeriesHandler().get(TimeSeriesFactory.EXP_WEEK_START);
        exp_month_start = client.getTimeSeriesHandler().get(TimeSeriesFactory.EXP_MONTH_START);

        // DF Exp
    }

    int width = 400;

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

        month_Lbl = new MyGuiComps.MyLabel("Month", true);
        month_Lbl.setHorizontalAlignment(JLabel.CENTER);
        month_Lbl.setXY(weekLbl.getX() + weekLbl.getWidth() + 3, weekLbl.getY());
        headerPanel.add(month_Lbl);


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


        // Df exp
        df_week_move_field = new MyGuiComps.MyTextField();
        df_week_move_field.setXY(moveField_week.getX(), moveField_week.getY() + moveField_week.getHeight() + 1);
        bodyPanel.add(df_week_move_field);


        // ---------------------- Month ---------------------- //
        // DF 2
        df_2_Field_month = new MyGuiComps.MyTextField();
        df_2_Field_month.setXY(3, 3);
        bodyPanel.add(df_2_Field_month);

        // DF 7
        df_7_Field_month = new MyGuiComps.MyTextField();
        df_7_Field_month.setXY(df_2_Field_month.getX(), df_2_Field_month.getY() + df_2_Field_month.getHeight() + 1);
        bodyPanel.add(df_7_Field_month);

        // DF 8
        df_8_Field_week = new MyGuiComps.MyTextField();
        df_8_Field_week.setXY(df_7_Field_month.getX(), df_7_Field_month.getY() + df_7_Field_month.getHeight() + 1);
        bodyPanel.add(df_8_Field_week);


        moveField_month = new MyGuiComps.MyTextField();
        moveField_month.setXY(df_8_Field_week.getX(), df_8_Field_week.getY() + df_8_Field_week.getHeight() + 1);
        bodyPanel.add(moveField_month);

    }
    
    @Override
    public void updateText() {

        double week_start = exp_week_start.get_value_with_exp();

        double week_move = L.floor(((client.getIndex() - week_start) / week_start) * 100, 100);

        // Set text
        moveField_week.colorBack(week_move, L.format100(), "%");

        if (client instanceof Spx || client instanceof Ndx) {

            // Week
            df_2_Field_week.colorForge((int) ((df_2_week.get_value_with_exp() + df_2_cdf.getValue()) / 1000));
            df_7_Field_week.colorForge((int) ((df_7_week.get_value_with_exp() + df_7_cdf.getValue()) / 1000));
            df_8_Field_week.colorForge((int) ((df_8_week.get_value_with_exp() + df_8_cdf.getValue()) / 1000));

            // Month
            df_2_Field_month.colorForge((int) ((df_2_month.get_value_with_exp() + df_2_cdf.getValue()) / 1000));
            df_7_Field_month.colorForge((int) ((df_7_month.get_value_with_exp() + df_7_cdf.getValue()) / 1000));
            df_8_Field_month.colorForge((int) ((df_8_month.get_value_with_exp() + df_8_cdf.getValue()) / 1000));

            // DF exp
            df_week_move_field.colorForge((int) df_week.getValue());
        }
    }
}
