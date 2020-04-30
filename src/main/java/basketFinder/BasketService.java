package basketFinder;

import basketFinder.handlers.StocksHandler;
import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;

import java.util.Map;

public class BasketService extends MyBaseService {

    // Variables
    private BASE_CLIENT_OBJECT client;
    private Map< Integer, MiniStock > miniStockMap;
    private int basketUp = 0;
    private int basketDown = 0;
    private double ind = 0;
    private int plagForBasket = 0;

    // Constructor
    public BasketService(BASE_CLIENT_OBJECT client, StocksHandler stocksHandler, int plagForBasket ) {
        super(client);
        this.client = client;
        this.miniStockMap = stocksHandler.getMiniStockMap();
        this.plagForBasket = plagForBasket;
    }

    public boolean searchBasket() {

        if ( client.isStarted( ) ) {
            boolean changed = true;
            int changeCounter = 0;

            StringBuilder stringBuilder = new StringBuilder();

            for ( MiniStock stock : miniStockMap.values( ) ) {

                // Volume != lastCheckVolume
                if ( stock.getVolume( ) == stock.getLastCheckVolume( ) ) {
                    changed = false;
                } else {
                    changeCounter++;
                    stringBuilder.append( stock.getName() ).append( ", " );
                }
                stock.updateLastCheckVolume( );
            }

            System.out.println( "Changed: " + changeCounter );
            if ( changeCounter > plagForBasket ) {

                // Up
                if ( client.getIndex() > ind ) {
                    basketUp++;
                    System.out.println( "Basket up" );
                }

                // Down
                if ( client.getIndex() < ind ) {
                    basketDown++;
                    System.out.println( "Basket down" );
                }

            }
            return changed;
        }

        ind = client.getIndex();

        return false;
    }

    @Override
    public void go() {
        searchBasket();
    }

    @Override
    public String getName() {
        return "Basket";
    }

    @Override
    public int getSleep() {
        return 1000;
    }

    public int getBaskets() {
        return basketUp - basketDown;
    }
}
