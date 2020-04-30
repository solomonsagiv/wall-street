package gui.fullStocksWindow.miniStockPanel;

import gui.MyGuiComps;
import gui.fullStocksWindow.FullHeadersPanel;
import serverObjects.stockObjects.STOCK_OBJECT;

public class MiniRacesPanel extends MyGuiComps.MyPanel {

    STOCK_OBJECT client;
    MyGuiComps.MyTextField conRaces;
    MyGuiComps.MyTextField indRaces;

    public MiniRacesPanel( STOCK_OBJECT client ) {
        this.client = client;
        initialize();
        initListeners();
    }

    private void initListeners() {

    }

    private void initialize() {
        // This
        setWidth( 100 );
        setHeight( 70 );

        // Con races
        conRaces = new MyGuiComps.MyTextField(  );
        conRaces.setXY( FullHeadersPanel.conRacesLbl.getX(),5 );
        conRaces.setWidth( FullHeadersPanel.conRacesLbl.getWidth() );
        add( conRaces );

        // Ind races
        indRaces = new MyGuiComps.MyTextField(  );
        indRaces.setXY( FullHeadersPanel.indRacesLbl.getX(),5 );
        indRaces.setWidth( FullHeadersPanel.indRacesLbl.getWidth() );
        add( indRaces );

    }


}
