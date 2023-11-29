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

public class ExpSumPanel extends MyGuiComps.MyPanel implements IMyPanel {

    BASE_CLIENT_OBJECT client;

    MyGuiComps.MyPanel headerPanel;
    MyGuiComps.MyLabel weekLbl;

    MyGuiComps.MyPanel bodyPanel;

    // Week
    MyGuiComps.MyTextField moveField_week;
    MyGuiComps.MyTextField df_2_Field_week;
    MyGuiComps.MyTextField df_7_Field_week;
    MyGuiComps.MyTextField df_8_Field_week;

    // DF exp
    MyGuiComps.MyTextField df_week_field;

    MyTimeSeries df_2_cdf, df_7_cdf, df_8_cdf,
            df_2_week, df_7_week, df_8_week,
            exp_week_start,
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
        df_8_cdf = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_9_CDF);

        // Week
        df_2_week = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_2_WEEK);
        df_8_week = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_8_WEEK);

        // Exp start
        exp_week_start = client.getTimeSeriesHandler().get(TimeSeriesFactory.EXP_WEEK_START);

        // DF Exp
        df_week = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_WEEK);
        df_month = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_MONTH);
        df_weighted = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_WEIGHTED);
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
        df_week_field = new MyGuiComps.MyTextField();
        df_week_field.setXY(moveField_week.getX(), moveField_week.getY() + moveField_week.getHeight() + 1);
        bodyPanel.add(df_week_field);

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

            // DF exp
            df_week_field.colorForge((int) df_week.getValue());
        }
    }
}
