package gui.fullStocksWindow;

import gui.MyGuiComps;
import gui.fullStocksWindow.miniStockPanel.MiniStockPanel;
import locals.LocalHandler;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.stockObjects.STOCK_OBJECT;
import java.awt.*;
import java.util.ArrayList;

public class FullStocksWindow extends MyGuiComps.MyFrame {

    // Variables
    FullHeadersPanel headersPanel;
    ArrayList< MiniStockPanel > panels;

    // Constructor
    public FullStocksWindow( String title ) throws HeadlessException {
        super( title );
    }

    @Override
    public void onClose() {

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initialize() {

        panels = new ArrayList<>( );

        int height = 0;
        int width = 800;

        // This
        setXY( 200, 200 );

        // Header
        headersPanel = new FullHeadersPanel();
        headersPanel.setBounds( 0, 0, width, 25 );
        add( headersPanel );

        height += headersPanel.getHeight() + 1;

        // Mini panels
        for ( BASE_CLIENT_OBJECT client: LocalHandler.clients ) {
            if ( client instanceof STOCK_OBJECT ) {
                MiniStockPanel panel = new MiniStockPanel( ( STOCK_OBJECT ) client );

                panel.setXY( 0, height );
                panels.add( panel );

                height += panel.getHeight() + 1;

                add( panel );

            }
        }
        setPreferredSize( new Dimension( width, height + panels.get( 0 ).getHeight() ) );
    }
}
