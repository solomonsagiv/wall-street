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
    private int targetChanges = 0;
    private int changesCount = 0;
    private ArrayList<MiniStock> stocksChanges = new ArrayList<>();
    private int basketUp = 0;
    private int basketDown = 0;
    private double preLastPrice = 0;
    private int sleep = 0;
    
    public BasketFinder(BASE_CLIENT_OBJECT client, int targetChanges, int sleep) {
        super(client);
        this.client = client;
        this.targetChanges = targetChanges;
        this.sleep = sleep;
        this.stocksHandler = client.getStocksHandler();
    }

    @Override
    public void go() {

        // Reset params
        changesCount = 0;
        double lastPrice = client.getIndex();

        // Look for changes
        for (MiniStock stock : stocksHandler.getStocks()) {
            try {
                // If changed
                if (stock.getVolume() > stock.getVol_0() && stock.getVolume() > 0 && stock.getVol_0() > 0) {
                    changesCount++;
                }

                // Update pre volume
                stock.setVolume_0_for_baskets(stock.getVolume());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Got basket
        if (changesCount >= targetChanges) {

            // Up
            if (lastPrice > preLastPrice) {
                basketUp++;
            }

            // Down
            if (lastPrice < preLastPrice) {
                basketDown++;
            }

        }

        // Set pre ind price
        preLastPrice = lastPrice;

    }

    public void add_basket_up() {
        basketUp++;
    }

    public void add_basket_down() {
        basketDown++;
    }

    public int getChangesCount() {
        return changesCount;
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

    public void setTargetChanges(int targetChanges) {
        this.targetChanges = targetChanges;
    }

    public int getBaskets() {
        return basketUp - basketDown;
    }

    public void setBasketUp(int basketUp) {
        this.basketUp = basketUp;
    }

    public void setBasketDown(int basketDown) {
        this.basketDown = basketDown;
    }

    public void setSleep(int sleep) {
        this.sleep = sleep;
    }

    @Override
    public String getName() {
        return client.getName() + " " + "basket finder";
    }

    @Override
    public int getSleep() {
        return sleep;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Target changes= " + targetChanges + "\n");
        str.append("Changes= " + changesCount + "\n");
        str.append("BasketUp= " + basketUp + "\n");
        str.append("BasketDown= " + basketDown + "\n");
        str.append("Stoocks= " + stocksHandler.toString());
        return str.toString();
    }
}
