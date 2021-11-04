package gui.index;

import baskets.BasketFinder_3;
import gui.MyGuiComps;
import gui.panels.IMyPanel;
import jibeDataGraber.DecisionsFunc;
import jibeDataGraber.DecisionsFuncFactory;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;

import javax.swing.*;
import java.awt.*;

public class MachinePanel extends MyGuiComps.MyPanel implements IMyPanel {

    BASE_CLIENT_OBJECT client;

    MyGuiComps.MyPanel header;
    MyGuiComps.MyLabel headerLbl;

    MyGuiComps.MyPanel body;
    MyGuiComps.MyLabel df_5_lbl;
    MyGuiComps.MyLabel df_15_lbl;
    MyGuiComps.MyLabel df_60_lbl;
    MyGuiComps.MyLabel df_day_lbl;

    MyGuiComps.MyTextField df_n_5_field;
    MyGuiComps.MyTextField df_5_field;
    MyGuiComps.MyTextField df_n_15_field;
    MyGuiComps.MyTextField df_15_field;
    MyGuiComps.MyTextField df_n_60_field;
    MyGuiComps.MyTextField df_60_field;
    MyGuiComps.MyTextField df_n_day_field;
    MyGuiComps.MyTextField df_day_field;

    BasketFinder_3 basketFinder;

    int width = 150;
    int height = 300;

    DecisionsFunc df_n_5_func;
    DecisionsFunc df_5_func;
    DecisionsFunc df_n_15_func;
    DecisionsFunc df_15_func;
    DecisionsFunc df_n_60_func;
    DecisionsFunc df_60_func;
    DecisionsFunc df_n_day_func;

    public MachinePanel(BASE_CLIENT_OBJECT client) {
        super();
        this.client = client;
        this.basketFinder = client.getBasketFinder();
        initsialize();

        this.df_n_5_func = client.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_N_5);
        this.df_5_func = client.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_5);
        this.df_n_15_func = client.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_N_15);
        this.df_15_func = client.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_15);
        this.df_n_60_func = client.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_N_60);
        this.df_60_func = client.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_60);
        this.df_n_day_func = client.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_N_DAY);
    }

    private void initsialize() {
        setSize(width, height);

        // ------ Head ------ //
        header = new MyGuiComps.MyPanel();
        header.setSize(width, 25);
        header.setXY(0, 0);
        add(header);

        headerLbl = new MyGuiComps.MyLabel("Machine", true);
        headerLbl.setHorizontalAlignment(JLabel.CENTER);
        header.add(headerLbl);

        // Body
        body = new MyGuiComps.MyPanel();
        body.setXY(0, header.getY() + header.getHeight() + 1);
        body.setSize(width, height);
        add(body);

        // 5
        df_5_lbl = new MyGuiComps.MyLabel("5");
        df_5_lbl.setXY(0, 3);
        df_5_lbl.setWidth(40);
        body.add(df_5_lbl);

        df_n_5_field = new MyGuiComps.MyTextField();
        df_n_5_field.setXY(df_5_lbl.getX() + df_5_lbl.getWidth() + 1, df_5_lbl.getY());
        df_n_5_field.setWidth(50);
        body.add(df_n_5_field);

        df_5_field = new MyGuiComps.MyTextField();
        df_5_field.setXY(df_n_5_field.getX() + df_n_5_field.getWidth() + 1, df_n_5_field.getY());
        df_5_field.setWidth(50);
        body.add(df_5_field);

        // 15
        df_15_lbl = new MyGuiComps.MyLabel("15");
        df_15_lbl.setXY(df_5_lbl.getX(), df_5_lbl.getY() + df_5_lbl.getHeight() + 3);
        df_15_lbl.setWidth(40);
        body.add(df_15_lbl);

        df_n_15_field = new MyGuiComps.MyTextField();
        df_n_15_field.setXY(df_15_lbl.getX() + df_15_lbl.getWidth() + 1, df_15_lbl.getY());
        df_n_15_field.setWidth(50);
        body.add(df_n_15_field);

        df_15_field = new MyGuiComps.MyTextField();
        df_15_field.setXY(df_n_15_field.getX() + df_n_15_field.getWidth() + 1, df_n_15_field.getY());
        df_15_field.setWidth(50);
        body.add(df_15_field);

        // 60
        df_60_lbl = new MyGuiComps.MyLabel("60");
        df_60_lbl.setXY(df_15_lbl.getX(), df_15_lbl.getY() + df_15_lbl.getHeight() + 3);
        df_60_lbl.setWidth(40);
        body.add(df_60_lbl);

        df_n_60_field = new MyGuiComps.MyTextField();
        df_n_60_field.setXY(df_60_lbl.getX() + df_60_lbl.getWidth() + 1, df_60_lbl.getY());
        df_n_60_field.setWidth(50);
        body.add(df_n_60_field);

        df_60_field = new MyGuiComps.MyTextField();
        df_60_field.setXY(df_n_60_field.getX() + df_n_60_field.getWidth() + 1, df_n_60_field.getY());
        df_60_field.setWidth(50);
        body.add(df_60_field);

        // Day
        df_day_lbl = new MyGuiComps.MyLabel("Day");
        df_day_lbl.setXY(df_60_lbl.getX(), df_60_lbl.getY() + df_60_lbl.getHeight() + 3);
        df_day_lbl.setWidth(40);
        body.add(df_day_lbl);

        df_n_day_field = new MyGuiComps.MyTextField();
        df_n_day_field.setXY(df_day_lbl.getX() + df_day_lbl.getWidth() + 1, df_day_lbl.getY());
        df_n_day_field.setWidth(50);
        body.add(df_n_day_field);

        df_day_field = new MyGuiComps.MyTextField();
        df_day_field.setXY(df_n_day_field.getX() + df_n_day_field.getWidth() + 1, df_n_day_field.getY());
        df_day_field.setWidth(50);
        body.add(df_day_field);
    }

    @Override
    public void updateText() {
        df_n_5_field.colorForge((int) df_n_5_func.getValue());
        df_5_field.colorForge((int) df_5_func.getValue());
        df_n_15_field.colorForge((int) df_n_15_func.getValue());
        df_15_field.colorForge((int) df_15_func.getValue());
        df_n_60_field.colorForge((int) df_n_60_func.getValue());
        df_60_field.colorForge((int) df_60_func.getValue());
        df_n_day_field.colorForge((int) df_n_day_func.getValue());
    }

    private void nois(JTextField textField) {
        new Thread(() -> {
            try {
                // Default
                Color defaultcolor = textField.getBackground();
                textField.setBackground(Themes.BLUE_LIGHT_2);
                Thread.sleep(2000);
                textField.setBackground(defaultcolor);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
