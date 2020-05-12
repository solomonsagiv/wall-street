package basketFinder.window;

import basketFinder.BasketService;
import gui.MyGuiComps;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Dax;
import serverObjects.indexObjects.INDEX_CLIENT_OBJECT;
import threads.MyThread;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class BasketWindow extends MyGuiComps.MyFrame {

    public static void main( String[] args ) {
        BasketWindow basketWindow = new BasketWindow( "Basket Dax", Dax.getInstance() );
    }

    TextUpdater textUpdater;

    MyGuiComps.MyPanel basketsStatusPanel;

    // Variables
    MyGuiComps.MyLabel basketsLbl;
    MyGuiComps.MyTextField basketUpField;
    MyGuiComps.MyTextField basketDownField;
    MyGuiComps.MyTextField sumBasketsField;

    MyGuiComps.MyLabel stocksChangedLbl;
    MyGuiComps.MyTextField stocksChangedField;

    // Constructor
    public BasketWindow(String title, INDEX_CLIENT_OBJECT client) throws HeadlessException {
        super(title, client);
        textUpdater = new TextUpdater( client );
        textUpdater.getHandler().start();
    }

    @Override
    public void initOnClose() {
        addWindowListener( new WindowAdapter( ) {
            @Override
            public void windowClosed( WindowEvent e ) {
                textUpdater.getHandler().close();
                super.windowClosed( e );
            }
        } );
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initialize() {

        // This
        setBounds(200, 200, 200, 200);
        setSize( 300, 300 );

        // Baskets status
        basketsStatusPanel = new MyGuiComps.MyPanel();
        basketsStatusPanel.setBounds( 0, 0, 300, 300 );

        basketsLbl = new MyGuiComps.MyLabel( "Baskets" );
        basketsLbl.setXY( 5, 5 );
        basketsStatusPanel.add( basketsLbl );

        basketUpField = new MyGuiComps.MyTextField(  );
        basketUpField.setXY( 5, 35 );
        basketsStatusPanel.add( basketUpField );

        basketDownField = new MyGuiComps.MyTextField(  );
        basketDownField.setXY( 5, 65 );
        basketsStatusPanel.add( basketDownField );

        sumBasketsField = new MyGuiComps.MyTextField(  );
        sumBasketsField.setXY( 5, 95 );
        basketsStatusPanel.add( sumBasketsField );

        // Changes
        stocksChangedLbl = new MyGuiComps.MyLabel( "Changed" );
        stocksChangedLbl.setXY( 90, 5 );
        basketsStatusPanel.add( stocksChangedLbl );

        stocksChangedField = new MyGuiComps.MyTextField(  );
        stocksChangedField.setXY( 90, 35 );
        basketsStatusPanel.add( stocksChangedField );

        add( basketsStatusPanel );
    }


    private class TextUpdater extends MyThread implements Runnable {

        // Constructor
        public TextUpdater( INDEX_CLIENT_OBJECT client ) {
            super( client );
        }

        @Override
        public void run() {
            while ( isRun() ) {
                try {
                    // Sleep
                    Thread.sleep( 1000 );

                    updateText();

                } catch ( InterruptedException e ) {
                } catch ( Exception e ) {
                    e.printStackTrace();
                }
            }
        }

        private void updateText() {
            try {

                INDEX_CLIENT_OBJECT myClient = ( INDEX_CLIENT_OBJECT ) client;
                BasketService basketService = myClient.getBasketService( );

                basketUpField.colorForge( basketService.getBasketUp( ) );
                basketDownField.colorForge( basketService.getBasketDown( ) );
                sumBasketsField.colorForge( basketService.getBaskets( ) );

                stocksChangedField.colorForge( basketService.getChangeCounter( ) );
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }

        @Override
        public void initRunnable() {
            setRunnable( this );
        }
    }
}
