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

    MyGuiComps.MyTextField moveField;
    MyGuiComps.MyTextField df_2_Field;
    MyGuiComps.MyTextField df_7_Field;
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

        headerLbl = new MyGuiComps.MyLabel("Exp", true);
        headerLbl.setHorizontalAlignment(JLabel.CENTER);
        headerPanel.add(headerLbl);

        // ------ Body ------ //
        bodyPanel = new MyGuiComps.MyPanel();
        bodyPanel.setXY(0, headerPanel.getY() + headerPanel.getHeight() + 1);
        bodyPanel.setSize(width, 200);
        add(bodyPanel);

        // DF 2
        df_2_Field = new MyGuiComps.MyTextField();
        df_2_Field.setXY(3, 3);
        bodyPanel.add(df_2_Field);

        // DF 7
        df_7_Field = new MyGuiComps.MyTextField();
        df_7_Field.setXY(df_2_Field.getX(), df_2_Field.getY() + df_2_Field.getHeight() + 1);
        bodyPanel.add(df_7_Field);

        // DF 8
        df_8_Field = new MyGuiComps.MyTextField();
        df_8_Field.setXY(df_7_Field.getX(), df_7_Field.getY() + df_7_Field.getHeight() + 1);
        bodyPanel.add(df_8_Field);


        moveField = new MyGuiComps.MyTextField();
        moveField.setXY(df_8_Field.getX(), df_8_Field.getY() + df_8_Field.getHeight() + 1);
        bodyPanel.add(moveField);

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
