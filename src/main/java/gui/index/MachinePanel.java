package gui.index;

import baskets.BasketFinder_3;
import gui.MyGuiComps;
import gui.panels.IMyPanel;
import jibeDataGraber.DecisionsFunc;
import jibeDataGraber.DecisionsFuncFactory;
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
    BasketFinder_3 basketFinder;

    Df_panel df_panel_1;
    Df_panel df_panel_2;
    Df_panel df_panel_3;

    int width = 100;
    int height = 300;

    private ArrayList<DecisionsFunc> df_list;

    public MachinePanel(BASE_CLIENT_OBJECT client) {
        super();
        this.client = client;
        this.basketFinder = client.getBasketFinder();

        df_list = new ArrayList<>();
        df_list.add(client.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_2));
        df_list.add(client.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_7));
        df_list.add(client.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_8));

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
        df_panel_1 = new Df_panel("DF 2", new DecisionsFunc[]{df_list.get(0)});
        df_panel_1.setXY(3, 3);
        df_panel_1.setWidth(width);
        df_panel_1.setHeight(25);
        body.add(df_panel_1);

        // DF panels
        df_panel_2 = new Df_panel("DF 7", new DecisionsFunc[]{df_list.get(1)});
        df_panel_2.setXY(df_panel_1.getX(), df_panel_1.getY() + df_panel_1.getHeight() + 1);
        df_panel_2.setWidth(width);
        df_panel_2.setHeight(25);
        body.add(df_panel_2);

        // DF panels
        df_panel_3 = new Df_panel("DF 8", new DecisionsFunc[]{df_list.get(2)});
        df_panel_3.setXY(df_panel_2.getX(), df_panel_2.getY() + df_panel_2.getHeight() + 1);
        df_panel_3.setWidth(width);
        df_panel_3.setHeight(25);
        body.add(df_panel_3);

    }

    private class Df_panel extends MyGuiComps.MyPanel implements IMyPanel {

        private DecisionsFunc[] df_func;
        private MyGuiComps.MyLabel nameLbl;
        private MyGuiComps.MyTextField df_field;
        private String name;

        public Df_panel(String name, DecisionsFunc[] df_unc) {
            super();
            this.name = name;
            this.df_func = df_unc;

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
            for (DecisionsFunc df : df_func) {
                df_field.colorForge((int) (df.getValue() / 1000), L.format_int());
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
