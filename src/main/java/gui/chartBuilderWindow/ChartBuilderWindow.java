package gui.chartBuilderWindow;

import gui.MyGuiComps;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;

import java.awt.*;
import java.util.ArrayList;

public class ChartBuilderWindow extends MyGuiComps.MyFrame {

    public static void main(String[] args) {
        ChartBuilderWindow window = new ChartBuilderWindow("A", Spx.getInstance());
    }

    RightPanel rightPanel;
    private ArrayList<SingleChartPanel> singleChartPanels;


    // Constructor
    public ChartBuilderWindow(String title, BASE_CLIENT_OBJECT client) throws HeadlessException {
        super(title, client);
        singleChartPanels = new ArrayList<>();
    }

    @Override
    public void initOnClose() {

    }

    @Override
    public void initListeners() {

        // Right panel
        rightPanel = new RightPanel();
        rightPanel.setXY(500, 0);
        add(rightPanel);



    }




    @Override
    public void initialize() {
        // This
        setSize(500, 500);





    }
}
