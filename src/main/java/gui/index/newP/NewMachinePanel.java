package gui.index.newP;

import baskets.BasketFinder_by_stocks;
import charts.timeSeries.MyTimeSeries;
import charts.timeSeries.TimeSeriesFactory;
import gui.MyGuiComps;
import gui.panels.IMyPanel;
import locals.L;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class NewMachinePanel extends MyGuiComps.MyPanel implements IMyPanel {

    BASE_CLIENT_OBJECT client;

    MyGuiComps.MyPanel header;

    MyGuiComps.MyLabel today_header_lbl;

    MyGuiComps.MyPanel body;
    BasketFinder_by_stocks basketFinder;

    Df_panel df_week_panel_3;
    Df_panel df_week_panel_1;
    Df_panel df_week_panel_2;
    Df_panel df_week_panel_4;

    int panel_width = 127;
    int width = 60;
    int height = 400;

    private ArrayList<MyTimeSeries> df_list_week;
    private ArrayList<MyTimeSeries> df_list_month;

    public NewMachinePanel(BASE_CLIENT_OBJECT client) {
        super();
        this.client = client;
        this.basketFinder = client.getBasketFinder_by_stocks();

        df_list_week = new ArrayList<>();
        df_list_week.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.EXP_WEEK_START));
        df_list_week.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_2_CDF));
        df_list_week.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_7_CDF));
//        df_list.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_2_ROLL_WEEK_MONTH_CDF));
        df_list_week.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.BASKETS_CDF));

        initsialize();
    }

    private void initsialize() {
        setSize(panel_width, height);

        // ------ Head ------ //
        header = new MyGuiComps.MyPanel();
        header.setSize(panel_width, 25);
        header.setXY(0, 0);
        add(header);

        // Fast
        today_header_lbl = new MyGuiComps.MyLabel("Today", true);
        today_header_lbl.setXY(3, 0);
        today_header_lbl.setWidth(width);
        today_header_lbl.setHeight(25);
        today_header_lbl.setHorizontalAlignment(JLabel.CENTER);
        header.add(today_header_lbl);

        // ------------ Body ------------ //
        body = new MyGuiComps.MyPanel();
        body.setXY(0, header.getY() + header.getHeight() + 1);
        body.setSize(panel_width, height);
        add(body);


        // ------------- Week ------------- //

        df_week_panel_1 = new Df_panel(new MyTimeSeries[]{df_list_week.get(0)}, true, L.format_int(), false);
        df_week_panel_1.setXY(3, 3);
        df_week_panel_1.setWidth(width);
        df_week_panel_1.setHeight(25);
        body.add(df_week_panel_1);

        df_week_panel_2 = new Df_panel(new MyTimeSeries[]{df_list_week.get(1)}, false, L.format100(), true);
        df_week_panel_2.setXY(df_week_panel_1.getX(), df_week_panel_1.getY() + df_week_panel_1.getHeight() + 1);
        df_week_panel_2.setWidth(width);
        df_week_panel_2.setHeight(25);
        body.add(df_week_panel_2);

        df_week_panel_3 = new Df_panel(new MyTimeSeries[]{df_list_week.get(2)}, false, L.format_int(), false);
        df_week_panel_3.setXY(df_week_panel_2.getX(), df_week_panel_2.getY() + df_week_panel_2.getHeight() + 1);
        df_week_panel_3.setWidth(width);
        df_week_panel_3.setHeight(25);
        body.add(df_week_panel_3);

        df_week_panel_4 = new Df_panel(new MyTimeSeries[]{df_list_week.get(3)}, false, L.format_int(), false);
        df_week_panel_4.setXY(df_week_panel_3.getX(), df_week_panel_3.getY() + df_week_panel_3.getHeight() + 27);
        df_week_panel_4.setWidth(width);
        df_week_panel_4.setHeight(25);
        body.add(df_week_panel_4);

    }

    private class Df_panel extends MyGuiComps.MyPanel implements IMyPanel {

        private MyTimeSeries[] timeSeries;
        private MyGuiComps.MyTextField df_field;
        private boolean round;
        private DecimalFormat df;
        private boolean dbl;

        public Df_panel(MyTimeSeries[] df_unc, boolean round, DecimalFormat df, boolean dbl) {
            super();
            this.timeSeries = df_unc;
            this.round = round;
            this.df = df;
            this.dbl = dbl;
            init();
        }

        protected void init() {
            super.init();
            // Df
            df_field = new MyGuiComps.MyTextField();
            df_field.setXY(0, 0);
            df_field.setWidth(width);
            add(df_field);
        }

        @Override
        public void updateText() {
            for (MyTimeSeries ts : timeSeries) {
                try {
                    if (ts != null) {

                        // Double
                        if (dbl) {
                            df_field.colorForge(ts.getValue(), df);
                            continue;
                        }

                        // INT and round
                        if (!dbl && round) {
                            df_field.colorForge((int) (ts.getValue() / 1000), L.format_int());
                            continue;
                        }

                        // Only INT
                        if (!dbl) {
                            df_field.colorForge((int) ts.getValue());
                            continue;
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void updateText() {
        df_week_panel_1.updateText();
        df_week_panel_2.updateText();
        df_week_panel_3.updateText();
        df_week_panel_4.updateText();
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
