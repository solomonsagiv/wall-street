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
    MyGuiComps.MyLabel headerLbl;

    MyGuiComps.MyPanel bodyPanel;

    MyGuiComps.MyLabel moveLbl;
    MyGuiComps.MyTextField moveField;

    MyGuiComps.MyLabel df_2_Lbl;
    MyGuiComps.MyTextField df_2_Field;

    MyGuiComps.MyLabel df_7_Lbl;
    MyGuiComps.MyTextField df_7_Field;

    MyGuiComps.MyLabel df_8_Lbl;
    MyGuiComps.MyTextField df_8_Field;

    MyTimeSeries df_2_cdf, df_7_cdf, df_8_cdf;

    Exp exp;

    public ExpSumPanel(BASE_CLIENT_OBJECT client) {
        super();
        this.client = client;
        initsialize();
        this.exp = client.getExps().getExp(ExpStrings.q1);

        df_2_cdf = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_2_CDF);
        df_7_cdf = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_7_CDF);
        df_8_cdf = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_8_CDF);
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

        // DF 2
        df_2_Lbl = new MyGuiComps.MyLabel("DF 2");
        df_2_Lbl.setXY(moveLbl.getX(), moveLbl.getY() + moveLbl.getHeight() + 3);
        bodyPanel.add(df_2_Lbl);

        df_2_Field = new MyGuiComps.MyTextField();
        df_2_Field.setXY(moveField.getX(), moveField.getY() + moveField.getHeight() + 3);
        bodyPanel.add(df_2_Field);

        // DF 7
        df_7_Lbl = new MyGuiComps.MyLabel("DF 7");
        df_7_Lbl.setXY(df_2_Lbl.getX(), df_2_Lbl.getY() + df_2_Lbl.getHeight() + 3);
        bodyPanel.add(df_7_Lbl);

        df_7_Field = new MyGuiComps.MyTextField();
        df_7_Field.setXY(df_2_Field.getX(), df_2_Field.getY() + df_2_Field.getHeight() + 3);
        bodyPanel.add(df_7_Field);


        // DF 8
        df_8_Lbl = new MyGuiComps.MyLabel("DF 8");
        df_8_Lbl.setXY(df_7_Lbl.getX(), df_7_Lbl.getY() + df_7_Lbl.getHeight() + 3);
        bodyPanel.add(df_8_Lbl);

        df_8_Field = new MyGuiComps.MyTextField();
        df_8_Field.setXY(df_7_Field.getX(), df_7_Field.getY() + df_7_Field.getHeight() + 3);
        bodyPanel.add(df_8_Field);

    }

    @Override
    public void updateText() {
        // Get data
        double exp_move = 0;
        if (exp.getStart() != 0) {
            exp_move = L.floor(((client.getIndex() - exp.getStart()) / exp.getStart()) * 100, 100);
        }
//         Set text
        moveField.colorBack(exp_move, L.format100(), "%");

        // DF 2
        df_2_Field.colorForge((int)(df_2_cdf.get_value_with_exp() / 1000));
        df_7_Field.colorForge((int)(df_7_cdf.get_value_with_exp() / 1000));
        df_8_Field.colorForge((int)(df_8_cdf.get_value_with_exp() / 1000));


    }
}
