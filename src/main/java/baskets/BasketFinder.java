package baskets;

import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;
import stocksHandler.MiniStock;
import stocksHandler.StocksHandler;

import java.util.ArrayList;

public class BasketFinder extends MyBaseService {

    // Variables
    private BASE_CLIENT_OBJECT client;
    StocksHandler stocksHandler;
    private int targetChanges = 25;
    private int changesCount = 0;
    private ArrayList< MiniStock > stocksChanges = new ArrayList<>( );
    private int basketUp = 0;
    private int basketDown = 0;
    private double preLastPrice = 0;

    public BasketFinder( BASE_CLIENT_OBJECT client ) {
        super( client );
        this.client = client;
        this.stocksHandler = client.getStocksHandler( );
    }

    @Override
    public void go() {

        // Reset params
        changesCount = 0;
        double lastPrice = client.getIndex( );

        // Look for changes
        for ( MiniStock stock : stocksHandler.getStocks( ) ) {
            try {
                // If changed
                if ( stock.getVolume( ) > stock.getVol_0( ) && stock.getVolume( ) > 0 ) {
                    changesCount++;
                }

                // Update pre volume
                stock.setVolume_0( stock.getVolume( ) );

            } catch ( Exception e ) {
                e.printStackTrace( );
            }
        }

        // Got basket
        if ( changesCount >= targetChanges ) {

            // Up
            if ( lastPrice > preLastPrice ) {
                basketUp++;
            }

            // Down
            if ( lastPrice < preLastPrice ) {
                basketDown++;
            }

        }

        // Set pre ind price
        preLastPrice = lastPrice;

    }

    public int getBasketUp() {
        return basketUp;
    }

    public int getBasketDown() {
        return basketDown;
    }

    public int getTargetChanges() {
        return targetChanges;
    }

    public void setTargetChanges( int targetChanges ) {
        this.targetChanges = targetChanges;
    }

    @Override
    public String getName() {
        return client.getName( ) + " " + "basket finder";
    }

    @Override
    public int getSleep() {
        return 3000;
    }
}
