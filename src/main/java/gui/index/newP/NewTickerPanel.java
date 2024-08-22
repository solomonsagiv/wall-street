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
import races.Race_Logic;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Dax;

import java.awt.*;
import java.util.ArrayList;

public class NewTickerPanel extends MyGuiComps.MyPanel implements IMyPanel {

    BASE_CLIENT_OBJECT client;
    Exps exps;

    MyGuiComps.MyPanel headerPanel;
    MyGuiComps.MyPanel bodyPanel;

    // Ticker
    MyGuiComps.MyLabel races_lbl;
    MyGuiComps.MyLabel df_2_lbl;
    MyGuiComps.MyLabel avg_lbl;
    MyGuiComps.MyLabel roll_lbl;

    MyGuiComps.MyTextField index_races_iq_field;
    MyGuiComps.MyTextField q1_races_iq_field;
    MyGuiComps.MyTextField q1_races_qw_field;
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

        // Races
        races_lbl = new MyGuiComps.MyLabel("Races", true);
        races_lbl.setXY(3, 0);
        headerPanel.add(races_lbl);

        // Avg
        avg_lbl = new MyGuiComps.MyLabel("Avg", true);
        avg_lbl.setXY(races_lbl.getX() + races_lbl.getWidth() + 1, races_lbl.getY());
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
        index_races_iq_field = new MyGuiComps.MyTextField();
        index_races_iq_field.setXY(races_lbl.getX(), 3);
        index_races_iq_field.setForeground(Themes.GREEN);
        bodyPanel.add(index_races_iq_field);

        q1_races_iq_field = new MyGuiComps.MyTextField();
        q1_races_iq_field.setForeground(Themes.RED);
        q1_races_iq_field.setXY(index_races_iq_field.getX(), index_races_iq_field.getY() + index_races_iq_field.getHeight() + 1);
        bodyPanel.add(q1_races_iq_field);

        q1_races_qw_field = new MyGuiComps.MyTextField();
        q1_races_qw_field.setXY(q1_races_iq_field.getX(), q1_races_iq_field.getY() + q1_races_iq_field.getHeight() + 1);
        bodyPanel.add(q1_races_qw_field);

        // Avg week
        avg_week_field = new MyGuiComps.MyTextField();
        avg_week_field.setXY(avg_lbl.getX(), index_races_iq_field.getY());
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
    }

    @Override
    public void updateText() {
        try {
            // Data
            MyTimeSeries op_avg_week = client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_WEEK_DAILY);
            MyTimeSeries op_avg_q1 = client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_Q1_DAILY);
            MyTimeSeries op_avg_q2 = client.getTimeSeriesHandler().get(TimeSeriesFactory.OP_AVG_Q2_DAILY);
            MyTimeSeries roll_week_q1 = client.getTimeSeriesHandler().get(TimeSeriesFactory.ROLL_WEEK_Q1_DAILY);
            MyTimeSeries roll_q1_q2 = client.getTimeSeriesHandler().get(TimeSeriesFactory.ROLL_Q1_Q2_DAILY);

            // Races
            index_races_iq_field.colorForge((int) client.getRacesService().get_race_logic(Race_Logic.RACE_RUNNER_ENUM.Q1_INDEX).get_r_one_points());
            q1_races_iq_field.colorForge((int) client.getRacesService().get_race_logic(Race_Logic.RACE_RUNNER_ENUM.Q1_INDEX).get_r_two_points());
            try {
                q1_races_qw_field.colorForge((int) client.getRacesService().get_race_logic(Race_Logic.RACE_RUNNER_ENUM.DAY_Q1).get_r_one_points());
            } catch (Exception e) {
                q1_races_qw_field.setText("N/A");
                e.printStackTrace();
            }

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
