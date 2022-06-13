package gui.index;

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
import java.util.ArrayList;

public class MachinePanel extends MyGuiComps.MyPanel implements IMyPanel {

    BASE_CLIENT_OBJECT client;

    MyGuiComps.MyPanel header;
    MyGuiComps.MyLabel headerLbl;

    MyGuiComps.MyPanel body;
    BasketFinder_by_stocks basketFinder;

    Df_panel df_panel_1;
    Df_panel df_panel_2;
    Df_panel df_panel_3;

    int width = 100;
    int height = 300;

    private ArrayList<MyTimeSeries> df_list;

    public MachinePanel(BASE_CLIENT_OBJECT client) {
        super();
        this.client = client;
        this.basketFinder = client.getBasketFinder_by_stocks();

        df_list = new ArrayList<>();
        df_list.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_2_CDF));
        df_list.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_7_CDF));
        df_list.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.BASKETS_CDF));

        initsialize();
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
        headerLbl.setWidth(width);
        header.add(headerLbl);

        // Body
        body = new MyGuiComps.MyPanel();
        body.setXY(0, header.getY() + header.getHeight() + 1);
        body.setSize(width, height);
        add(body);

        // DF panels
        df_panel_1 = new Df_panel("DF 2", new MyTimeSeries[]{df_list.get(0)});
        df_panel_1.setXY(3, 3);
        df_panel_1.setWidth(width);
        df_panel_1.setHeight(25);
        body.add(df_panel_1);

        // DF panels
        df_panel_2 = new Df_panel("DF 7", new MyTimeSeries[]{df_list.get(1)});
        df_panel_2.setXY(df_panel_1.getX(), df_panel_1.getY() + df_panel_1.getHeight() + 1);
        df_panel_2.setWidth(width);
        df_panel_2.setHeight(25);
        body.add(df_panel_2);

        // DF panels
        df_panel_3 = new Df_panel("Baskets", new MyTimeSeries[]{df_list.get(2)});
        df_panel_3.setXY(df_panel_2.getX(), df_panel_2.getY() + df_panel_2.getHeight() + 1);
        df_panel_3.setWidth(width);
        df_panel_3.setHeight(25);
        body.add(df_panel_3);

    }

    private class Df_panel extends MyGuiComps.MyPanel implements IMyPanel {

        private MyTimeSeries[] timeSeries;
        private MyGuiComps.MyLabel nameLbl;
        private MyGuiComps.MyTextField df_field;
        private String name;

        public Df_panel(String name, MyTimeSeries[] df_unc) {
            super();
            this.name = name;
            this.timeSeries = df_unc;

            init();
        }

        protected void init() {
            super.init();

            // Name
            nameLbl = new MyGuiComps.MyLabel(name);
            nameLbl.setXY(0, 0);
            nameLbl.setWidth(30);
            nameLbl.setHeight(25);
            add(nameLbl);

            // Df
            df_field = new MyGuiComps.MyTextField();
            df_field.setXY(nameLbl.getX() + nameLbl.getWidth() + 1, nameLbl.getY());
            df_field.setWidth(60);
            add(df_field);
        }

        @Override
        public void updateText() {
            for (MyTimeSeries ts : timeSeries) {
                try {
                    if (ts.getData() != 0) {
                        df_field.colorForge((int) (ts.getData() / 1000), L.format_int());
                    } else {
                        df_field.colorForge(0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void updateText() {
        df_panel_1.updateText();
        df_panel_2.updateText();
        df_panel_3.updateText();
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
