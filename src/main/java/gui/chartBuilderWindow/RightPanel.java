package gui.chartBuilderWindow;

import gui.MyGuiComps;

public class RightPanel extends MyGuiComps.MyPanel {

    private MyGuiComps.MyButton plusSingleChartBtn;

    public RightPanel() {
        init();
    }

    @Override
    protected void init() {
        super.init();

        // This
        setSize(100, 500);

        // Plus single chart
        plusSingleChartBtn = new MyGuiComps.MyButton("+");
        plusSingleChartBtn.setXY(5, 5);
        add(plusSingleChartBtn);

    }
}
