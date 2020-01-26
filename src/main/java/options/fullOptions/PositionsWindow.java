package options.fullOptions;

import gui.MyGuiComps;
import locals.L;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;
import threads.MyThread;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PositionsWindow extends MyGuiComps.MyFrame {

    // Variables
    BASE_CLIENT_OBJECT client;
    HeaderPanel headerPanel;
    MyGuiComps.MyPanel positionsAreaPanel;

    ArrayList<PositionCalculator.OptionPosition> positions;
    ArrayList<PositionPanel> positionPanels;
    int prePositionsSize = 0;

    Runner runner;

    // Prop
    int row1 = 3;
    int row2 = 30;

    int col1 = 5;
    int col2 = 70;
    int col3 = 135;
    int col4 = 200;
    int col5 = 265;
    int col6 = 330;
    int col7 = 395;

    // COnstructor
    public PositionsWindow(BASE_CLIENT_OBJECT client, ArrayList<PositionCalculator.OptionPosition> positions) {
        super("Positions");
        this.client = client;
        this.positions = positions;
        positionPanels = new ArrayList<>();

        init();

        runner = new Runner(client);
        runner.getHandler().start();
    }

    private void init() {

        setBounds( 100, 100, 600, 500 );

        // Headers
        headerPanel = new HeaderPanel(this);
        headerPanel.setXY(0, 0);
        add(headerPanel);

        // Positions area panel
        positionsAreaPanel = new MyGuiComps.MyPanel();
        positionsAreaPanel.setBackground(Themes.GREY_VERY_LIGHT);
        positionsAreaPanel.setBounds(0, 25, getWidth(), getHeight() - headerPanel.getHeight());
        add(positionsAreaPanel);

        // Panels
        initPanels( positionsAreaPanel );

    }

    public void initPanels(MyGuiComps.MyPanel panel) {

        int y = 0;
        int marginBetweenPanels = 1;

        // For each position panel
        for (PositionCalculator.OptionPosition position : positions) {

            PositionPanel positionPanel = new PositionPanel(position, panel);
            positionPanels.add(positionPanel);

            positionPanel.setBackground(Themes.GREY_LIGHT);

            // Set y
            positionPanel.setXY(0, y);

            // Append to window
            panel.add(positionPanel);

            // Increment y
            y += positionPanel.getHeight() + marginBetweenPanels;

        }

        panel.revalidate();
        panel.repaint();

    }

    private boolean isPositionsChanges() {
        return prePositionsSize != positions.size();
    }

    // ---------- Position panel ---------- //
    class PositionPanel extends MyGuiComps.MyPanel {

        // Variables
        PositionCalculator.OptionPosition position;

        // Text fields
        MyGuiComps.MyTextField quantityField;
        MyGuiComps.MyTextField priceField;
        MyGuiComps.MyTextField vegaField;
        MyGuiComps.MyTextField deltaField;
        MyGuiComps.MyTextField pnlField;

        // Buttons
        MyGuiComps.MyButton cancelBtn;
        MyGuiComps.MyButton editBtn;

        // Constructor
        public PositionPanel(PositionCalculator.OptionPosition position, MyGuiComps.MyPanel panel) {

            // Super
            super();

            // This
            this.position = position;

            // Init
            initialize(panel);

        }

        private void initialize(MyGuiComps.MyPanel panel) {
            // This panel
            setBounds(0, 0, panel.getWidth(), 50);

            // Pnl
            pnlField = new MyGuiComps.MyTextField(20);
            pnlField.setXY(col1, row1);
            add(pnlField);

            // Delta
            deltaField = new MyGuiComps.MyTextField(20);
            deltaField.setXY(col2, row1);
            add(deltaField);

            // Vega
            vegaField = new MyGuiComps.MyTextField(20);
            vegaField.setXY(col3, row1);
            add(vegaField);

            // Delta
            priceField = new MyGuiComps.MyTextField(20);
            priceField.setXY(col4, row1);
            add(priceField);

            // Delta
            quantityField = new MyGuiComps.MyTextField(20);
            quantityField.setXY(col5, row1);
            add(quantityField);

            // Edit btn
            editBtn = new MyGuiComps.MyButton("Edit");
            editBtn.setXY(col6, row1);
            add(editBtn);

            // Cancel btn
            cancelBtn = new MyGuiComps.MyButton("Cancel");
            cancelBtn.setXY(col7, row1);
            add(cancelBtn);
        }

    }

    // ---------- Header panel ---------- //
    class HeaderPanel extends MyGuiComps.MyPanel {

        // Labels
        MyGuiComps.MyLabel quantityLbl;
        MyGuiComps.MyLabel priceLbl;
        MyGuiComps.MyLabel vegaLbl;
        MyGuiComps.MyLabel deltaLbl;
        MyGuiComps.MyLabel pnlLbl;

        // Constructor
        public HeaderPanel(JFrame frame) {

            // Super
            super();

            // This
            setBackground(Themes.GREY);
            setBounds(new Rectangle(frame.getWidth(), 30));

            // Pnl
            pnlLbl = new MyGuiComps.MyLabel("P/L");
            pnlLbl.setXY(col1, row1);
            add(pnlLbl);

            // Delta
            deltaLbl = new MyGuiComps.MyLabel("Delta");
            deltaLbl.setXY(col2, row1);
            add(deltaLbl);

            // Vega
            vegaLbl = new MyGuiComps.MyLabel("Vega");
            vegaLbl.setXY(col3, row1);
            add(vegaLbl);

            // Price
            priceLbl = new MyGuiComps.MyLabel("Price");
            priceLbl.setXY(col4, row1);
            add(priceLbl);

            // Quantity
            quantityLbl = new MyGuiComps.MyLabel("Quantity");
            quantityLbl.setXY(col5, row1);
            add(quantityLbl);
        }
    }


    public class Runner extends MyThread implements Runnable {


        // Constructor
        public Runner(BASE_CLIENT_OBJECT client) {
            super(client);
        }

        @Override
        public void run() {
            while (isRun()) {
                try {
                    // Sleep
                    Thread.sleep(2000);

                    // Update the panels
                    update();

                    // Update text
                    updateText();

                } catch (InterruptedException e) {
                    getHandler().close();
                }

            }

        }

        private void updateText() {

            PositionCalculator.OptionPosition position;

            for (PositionPanel panel: positionPanels) {
                position = panel.position;

                panel.priceField.setText(L.format100(position.getPrice()));
                panel.deltaField.setText(L.format100(position.getDelta()));
                panel.vegaField.setText(L.format100(position.getVega()));
                panel.quantityField.setText(L.format100(position.getQuantity()));
                panel.pnlField.setText(L.format100(position.getPnl()));
                
            }

        }


        private void update() {
            if (isPositionsChanges()) {
                initPanels(positionsAreaPanel);

                prePositionsSize = positions.size();
            }
        }

        @Override
        public void initRunnable() {
            setRunnable(this);
        }
    }
}
