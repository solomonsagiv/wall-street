package basketFinder;

import basketFinder.handlers.StocksHandler;
import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;

import java.util.Map;

public class BasketService2 extends MyBaseService {

    // Variables
    private BASE_CLIENT_OBJECT client;
    private Map<Integer, MiniStock> miniStockMap;
    private int basketUp = 0;
    private int basketDown = 0;
    private double ind = 0;
    private int plagForBasket = 0;
    private int upCounter = 0;
    private int downCounter = 0;

    // Constructor
    public BasketService2(BASE_CLIENT_OBJECT client, StocksHandler stocksHandler, int plagForBasket) {
        super(client);
        this.client = client;
        this.miniStockMap = stocksHandler.getMiniStockMap();
        this.plagForBasket = plagForBasket;
    }

    public boolean searchBasket() {

        boolean basket = false;

        if (client.isStarted()) {

            upCounter = 0;
            downCounter = 0;

            for (MiniStock stock : miniStockMap.values()) {
                // Volume check
                if (stock.getVolume() != stock.getLastCheckVolume()) {
                    // Up
                    if (stock.isUp()) {
                        upCounter++;
                    }
                    // Down
                    if (stock.isDown()) {
                        downCounter++;
                    }
                }
                stock.updateLastData();
            }

            if (upCounter > plagForBasket) {
                // Up
                if (client.getIndex() > ind) {
                    basketUp++;
                }
                basket = true;
            }

            if (downCounter > plagForBasket) {

                // Down
                if (client.getIndex() < ind) {
                    basketDown++;
                }
                basket = true;
            }

        }
        ind = client.getIndex();
        return basket;
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
        return 3000;
    }

    public int getBaskets() {
        return basketUp - basketDown;
    }

    public int getBasketUp() {
        return basketUp;
    }

    public int getBasketDown() {
        return basketDown;
    }
}
