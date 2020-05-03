package gui.fullStocksWindow;

import gui.MyGuiComps;
import gui.fullStocksWindow.miniStockPanel.MiniStockPanel;
import locals.LocalHandler;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.stockObjects.STOCK_OBJECT;
import threads.MyThread;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class FullStocksWindow extends MyGuiComps.MyFrame {

    // Variables
    FullHeadersPanel headersPanel;
    ArrayList< MiniStockPanel > panels;
    TextUpdater textUpdater;

    // Constructor
    public FullStocksWindow( String title ) throws HeadlessException {
        super( title );
        textUpdater = new TextUpdater();
        textUpdater.getHandler().start();
    }

    @Override
    public void onClose() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                close();
                super.windowClosed(e);
            }
        });
    }

    public void close() {
        textUpdater.getHandler().close();
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initialize() {

        panels = new ArrayList<>( );

        int height = 0;
        int width = 580;

        // This
        setXY( 200, 200 );

        // Header
        headersPanel = new FullHeadersPanel();
        headersPanel.setBounds( 0, 0, width, 25 );
        add( headersPanel );

        height += headersPanel.getHeight() + 1;

        // Panels
        panels(height);

        // Set size
        setPreferredSize( new Dimension( width, panels.get( 0 ).getHeight() * (panels.size() + 1 ) ) );
    }

    private void panels( int height ) {
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
    }

    private class TextUpdater extends MyThread implements Runnable {

        @Override
        public void initRunnable() {
            setRunnable(this);
        }

        @Override
        public void run() {

            while (isRun()) {
                try {

                    // Sleep
                    Thread.sleep(1000);

                    // Update text
                    updateText();

                } catch (InterruptedException e) {
                    break;
                } catch (Exception e) {

                }
            }
        }

        private void updateText() {
            for ( MiniStockPanel panel: panels ) {
                try {
                    panel.updateText();
                } catch ( Exception e ) {
                    e.printStackTrace();
                }
            }
        }
    }
}
