package basketFinder;

import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;
import spx100.MiniStock;

import java.util.Map;

public class BasketFinder extends MyBaseService {

    // Variables
    private BASE_CLIENT_OBJECT client;
    private Map< Integer, MiniStock > miniStockMap;

    // Constructor
    public BasketFinder( BASE_CLIENT_OBJECT client, Map< Integer, MiniStock > miniStockMap ) {
        super(client);
        this.client = client;
        this.miniStockMap = miniStockMap;
    }

    public boolean searchBasket() {

        if ( client.isStarted( ) ) {
            boolean changed = true;
            int changeCounter = 0;
            int notChangeCounter = 0;

            StringBuilder stringBuilder = new StringBuilder();


            for ( MiniStock stock : miniStockMap.values( ) ) {

                // Volume != lastCheckVolume
                if ( stock.getVolume( ) == stock.getLastCheckVolume( ) ) {
                    changed = false;
                    notChangeCounter++;
                } else {
                    changeCounter++;
                    stringBuilder.append( stock.getName() ).append( ", " );
                }

                stock.updateLastCheckVolume( );

            }

            if ( changeCounter > 40 ) {
                System.out.println( "Changed : " + changeCounter );
                System.out.println( stringBuilder );

            }

            System.out.println( );

            return changed;
        }

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
}
