package gui.index.newP;

import charts.timeSeries.MyTimeSeries;
import charts.timeSeries.TimeSeriesFactory;
import exp.Exp;
import exp.Exps;
import gui.MyGuiComps;
import gui.panels.IMyPanel;
import locals.L;
import locals.Themes;
import org.apache.commons.lang.StringUtils;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Dax;
import serverObjects.indexObjects.Ndx;

import java.awt.*;
import java.util.ArrayList;

public class NewTickerPanel extends MyGuiComps.MyPanel implements IMyPanel {

    BASE_CLIENT_OBJECT client;
    Exps exps;

    MyGuiComps.MyPanel headerPanel;
    MyGuiComps.MyPanel bodyPanel;

    // Ticker
    MyGuiComps.MyLabel baskets_lbl;
    MyGuiComps.MyLabel df_2_lbl;
    MyGuiComps.MyLabel avg_lbl;
    MyGuiComps.MyLabel roll_lbl;

    MyGuiComps.MyTextField basket_up_field;
    MyGuiComps.MyTextField basket_down_field;
    MyGuiComps.MyTextField basket_sum_field;
    MyGuiComps.MyTextField df_2_field;
    MyGuiComps.MyTextField df_2_roll_field;
    MyGuiComps.MyTextField df_9_field;
    MyGuiComps.MyTextField avg_week_field;
    MyGuiComps.MyTextField avg_q1_field;
    MyGuiComps.MyTextField avg_q2_field;
    MyGuiComps.MyTextField avg_roll_week_q1_field;
    MyGuiComps.MyTextField avg_roll_q1_q2_field;

    public static void main(String[] args) {
        NewPanel window = new NewPanel(Dax.getInstance());
    }

    public NewTickerPanel(BASE_CLIENT_OBJECT client) {
        super();
        this.client = client;
        this.exps = client.getExps();
        initsialize();
    }

    private void initsialize() {
        setSize(204, 112);
        setBackground(Color.WHITE);

        // -------------------- Head -------------------- //
        headerPanel = new MyGuiComps.MyPanel();
        headerPanel.setXY(0, 0);
        headerPanel.setSize(getWidth(), 25);
        add(headerPanel);

        // Baskets
        baskets_lbl = new MyGuiComps.MyLabel("Baskets", true);
        baskets_lbl.setXY(3, 0);
        headerPanel.add(baskets_lbl);

        // Avg
        avg_lbl = new MyGuiComps.MyLabel("Avg", true);
        avg_lbl.setXY(baskets_lbl.getX() + baskets_lbl.getWidth() + 1, baskets_lbl.getY());
        headerPanel.add(avg_lbl);

        // Roll
        roll_lbl = new MyGuiComps.MyLabel("Roll");
        roll_lbl.setXY(avg_lbl.getX() + avg_lbl.getWidth() + 1, avg_lbl.getY());
        roll_lbl.setFont(roll_lbl.getFont().deriveFont(Font.BOLD));
        headerPanel.add(roll_lbl);

        // DF 2
        df_2_lbl = new MyGuiComps.MyLabel("DF", true);
        df_2_lbl.setXY(roll_lbl.getX() + roll_lbl.getWidth() + 1, roll_lbl.getY());
        headerPanel.add(df_2_lbl);

        // --------------------- Body --------------------- //
        bodyPanel = new MyGuiComps.MyPanel();
        bodyPanel.setXY(headerPanel.getX(), headerPanel.getY() + headerPanel.getHeight() + 1);
        bodyPanel.setSize(headerPanel.getWidth(), 350);
        add(bodyPanel);

        // Baskets
        basket_up_field = new MyGuiComps.MyTextField();
        basket_up_field.setXY(baskets_lbl.getX(), 3);
        basket_up_field.setForeground(Themes.GREEN);
        bodyPanel.add(basket_up_field);

        basket_down_field = new MyGuiComps.MyTextField();
        basket_down_field.setForeground(Themes.RED);
        basket_down_field.setXY(basket_up_field.getX(), basket_up_field.getY() + basket_up_field.getHeight() + 1);
        bodyPanel.add(basket_down_field);

        basket_sum_field = new MyGuiComps.MyTextField();
        basket_sum_field.setXY(basket_down_field.getX(), basket_down_field.getY() + basket_down_field.getHeight() + 1);
        bodyPanel.add(basket_sum_field);

        // Avg week
        avg_week_field = new MyGuiComps.MyTextField();
        avg_week_field.setXY(avg_lbl.getX(), basket_up_field.getY());
        bodyPanel.add(avg_week_field);

        // Avg q1
        avg_q1_field = new MyGuiComps.MyTextField();
        avg_q1_field.setXY(avg_week_field.getX(), avg_week_field.getY() + avg_week_field.getHeight() + 1);
        bodyPanel.add(avg_q1_field);

        // Avg q2
        avg_q2_field = new MyGuiComps.MyTextField();
        avg_q2_field.setXY(avg_q1_field.getX(), avg_q1_field.getY() + avg_q1_field.getHeight() + 1);
        bodyPanel.add(avg_q2_field);

        // Avg roll week q1
        avg_roll_week_q1_field = new MyGuiComps.MyTextField();
        avg_roll_week_q1_field.setXY(roll_lbl.getX(), avg_week_field.getY());
        bodyPanel.add(avg_roll_week_q1_field);

        // Avg roll q1 q2
        avg_roll_q1_q2_field = new MyGuiComps.MyTextField();
        avg_roll_q1_q2_field.setXY(avg_roll_week_q1_field.getX(), avg_roll_week_q1_field.getY() + avg_roll_week_q1_field.getHeight() + 1);
        bodyPanel.add(avg_roll_q1_q2_field);

        // DF 2
        df_2_field = new MyGuiComps.MyTextField();
        df_2_field.setXY(df_2_lbl.getX(), avg_week_field.getY());
        bodyPanel.add(df_2_field);

        // DF 2 roll
        df_2_roll_field = new MyGuiComps.MyTextField();
        df_2_roll_field.setXY(df_2_field.getX(), df_2_field.getY() + df_2_field.getHeight() + 1);
        bodyPanel.add(df_2_roll_field);

        // DF 9
        df_9_field = new MyGuiComps.MyTextField();
        df_9_field.setXY(df_2_roll_field.getX(), df_2_roll_field.getY() + df_2_roll_field.getHeight() + 1);
        bodyPanel.add(df_9_field);
    }

    @Override
    public void updateText() {
        try {
            // Data
            MyTimeSeries df_2 = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_2_CDF);
//            MyTimeSeries df_2_roll = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_2_ROLL_CDF);
            MyTimeSeries df_9 = client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_9_CDF);
            MyTimeSeries op_avg_week = client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_WEEK_DAILY);
            MyTimeSeries op_avg_q1 = client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_Q1_DAILY);
            MyTimeSeries op_avg_q2 = client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_Q2_DAILY);
            MyTimeSeries roll_week_q1 = client.getTimeSeriesHandler().get(TimeSeriesFactory.ROLL_WEEK_Q1_DAILY);
            MyTimeSeries roll_q1_q2 = client.getTimeSeriesHandler().get(TimeSeriesFactory.ROLL_Q1_Q2_DAILY);

            // CDF
            df_2_field.colorForge((int) df_2.getValue() / 1000);
//            df_2_roll_field.setText(L.format_int(df_2_roll.getValue()));
            df_9_field.colorForge((int) df_9.getValue() / 1000);

            avg_week_field.colorForge(op_avg_week.getValue(), L.format10());
            avg_q1_field.colorForge(op_avg_q1.getValue(), L.format10());

            try {
                avg_q2_field.colorForge(op_avg_q2.getValue(), L.format10());
            } catch (Exception e) {
//                e.printStackTrace();
            }

            try {
                avg_roll_week_q1_field.colorForge(roll_week_q1.getValue(), L.format10());
                avg_roll_q1_q2_field.colorForge(roll_q1_q2.getValue(), L.format10());
            } catch (Exception e) {
//                e.printStackTrace();
            }

            if (client instanceof Ndx) {
                // Present
                basket_up_field.setText(L.str(client.getBasketFinder_by_stocks().getBasketUp()));
                basket_down_field.setText(L.str(client.getBasketFinder_by_stocks().getBasketDown()));
                basket_sum_field.colorForge(client.getBasketFinder_by_stocks().getBaskets());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


class DaxExpsPanel extends MyGuiComps.MyPanel implements IMyPanel {

    Exps exps;
    ArrayList<DaxExpMiniPanel> miniPanels = new ArrayList<>();

    public DaxExpsPanel(Exps exps) {
        super();
        this.exps = exps;
        initsialize();
    }

    private void initsialize() {
        setSize(300, 500);

        // Exp mini panels
        int y = 0;
        for (Exp exp : exps.getExpList()) {
            DaxExpMiniPanel expMiniPanel = new DaxExpMiniPanel(exp);
            miniPanels.add(expMiniPanel);
            expMiniPanel.setXY(0, y);
            add(expMiniPanel);
            y += expMiniPanel.getHeight() + 1;
        }
    }

    @Override
    public void updateText() {
        for (DaxExpMiniPanel miniPanel : miniPanels) {
            miniPanel.updateText();
        }
    }
}


// Exp mini panels
class DaxExpMiniPanel extends MyGuiComps.MyPanel implements IMyPanel {

    Exp exp;

    MyGuiComps.MyLabel expNameLbl;
    MyGuiComps.MyTextField futField;
    MyGuiComps.MyTextField opField;
    MyGuiComps.MyTextField opAvgField;

    public DaxExpMiniPanel(Exp exp) {
        super();
        this.exp = exp;
        initsialize();
    }

    private void initsialize() {

        setSize(300, 26);

        // Name
        expNameLbl = new MyGuiComps.MyLabel(StringUtils.capitalize(exp.getName()));
        expNameLbl.setXY(5, 0);
        add(expNameLbl);

        // Fut
        futField = new MyGuiComps.MyTextField();
        futField.setXY(expNameLbl.getX() + expNameLbl.getWidth() + 4, expNameLbl.getY());
        add(futField);

        // Op
        opField = new MyGuiComps.MyTextField();
        opField.setXY(futField.getX() + futField.getWidth() + 4, futField.getY());
        add(opField);

        // Op avg
        opAvgField = new MyGuiComps.MyTextField();
        opAvgField.setXY(opField.getX() + opField.getWidth() + 4, opField.getY());
        add(opAvgField);
    }

    @Override
    public void updateText() {
        try {
            futField.setText(L.str(L.floor(exp.get_future(), 10)));
            opField.colorBack(exp.get_op(), L.format10());
            opAvgField.colorForge(exp.getOp_avg(), L.format100());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
