package gui.fullStocksWindow.miniStockPanel;

import gui.MyGuiComps;
import gui.fullStocksWindow.FullHeadersPanel;
import serverObjects.stockObjects.STOCK_OBJECT;

public class MiniTickerPanel extends MyGuiComps.MyPanel {

    // Variables
    STOCK_OBJECT client;
    MyGuiComps.MyTextField openField;
    MyGuiComps.MyTextField indField;
    MyGuiComps.MyTextField highField;
    MyGuiComps.MyTextField lowField;
    MyGuiComps.MyTextField opField;
    MyGuiComps.MyTextField opAvgField;
    MyGuiComps.MyTextField rollField;

    public MiniTickerPanel( STOCK_OBJECT client ) {
        this.client = client;
        initialize();
        initListeners();
    }

    private void initListeners() {
    }

    private void initialize() {
        // This
        setWidth( 400 );
        setHeight( 50 );

        // Open
        openField = new MyGuiComps.MyTextField(  );
        openField.setXY( FullHeadersPanel.openLbl.getX(), 1 );
        openField.setWidth( FullHeadersPanel.openLbl.getWidth() );
        openField.setBackground( getBackground() );
        add( openField );

        // Ind
        indField = new MyGuiComps.MyTextField(  );
        indField.setXY( FullHeadersPanel.indLbl.getX(), 1 );
        indField.setWidth( FullHeadersPanel.indLbl.getWidth() );
        indField.setBackground( getBackground() );
        add( indField );

        // High
        highField = new MyGuiComps.MyTextField(  );
        highField.setXY( FullHeadersPanel.highLbl.getX(), 1 );
        highField.setWidth( FullHeadersPanel.highLbl.getWidth());
        highField.setBackground( getBackground() );
        add( highField );

        // Low
        lowField = new MyGuiComps.MyTextField(  );
        lowField.setXY( FullHeadersPanel.lowLbl.getX(), 1 );
        lowField.setWidth( FullHeadersPanel.lowLbl.getWidth());
        lowField.setBackground( getBackground() );
        add( lowField );

        // O/P
        opField = new MyGuiComps.MyTextField(  );
        opField.setXY( FullHeadersPanel.opLbl.getX(), 1 );
        opField.setWidth( FullHeadersPanel.opLbl.getWidth());
        opField.setBackground( getBackground() );
        add( opField );

        // O/P avg
        opAvgField = new MyGuiComps.MyTextField(  );
        opAvgField.setXY( FullHeadersPanel.opLbl.getX(), opField.getY() + opField.getHeight() + 1 );
        opAvgField.setWidth( FullHeadersPanel.opLbl.getWidth());
        opAvgField.setBackground( getBackground() );
        add( opAvgField );

        // Roll
        rollField = new MyGuiComps.MyTextField(  );
        rollField.setXY( FullHeadersPanel.rollLbl.getX(), 1 );
        rollField.setWidth( FullHeadersPanel.rollLbl.getWidth());
        rollField.setBackground( getBackground() );
        add( rollField );

    }


    public void updateText() {

    }
}
