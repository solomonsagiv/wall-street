package baskets;

import locals.L;
import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;
import stocksHandler.MiniStock;
import stocksHandler.StocksHandler;

import java.util.ArrayList;
import java.util.Collections;

public class BasketFinder_2 extends MyBaseService {

    // Variables
    private BASE_CLIENT_OBJECT client;
    StocksHandler stocksHandler;
    private int targetChanges = 0;
    private int changesCount = 0;
    private int basketUp = 0;
    private int basketDown = 0;
    private int sleep_for_basket = 0;
    private ArrayList<Double> index_changed_list;
    private int sleep_count = 0;
    private double biggest_change = 0;
     
    public BasketFinder_2(BASE_CLIENT_OBJECT client, int targetChanges, int sleep_for_basket) {
        super(client);
        this.client = client;
        this.targetChanges = targetChanges;
        this.sleep_for_basket = sleep_for_basket;
        this.stocksHandler = client.getStocksHandler();
        index_changed_list = new ArrayList<>();
    }

    @Override
    public void go() {

        // Handle sleep
        sleep_count += getSleep();

        // Collect index changes
        collect_index_changes();

        // Look for basket
        if (sleep_count % sleep_for_basket == 0) {
            look_for_basket();
            sleep_count = 0;
        }
    }

    private double pre_index_price = 0;
    private void collect_index_changes() {

        double last_index_price = client.getIndex();

        // If changed
        if ( last_index_price != pre_index_price ) {

            // Append to changes list
            index_changed_list.add(last_index_price  - pre_index_price);

            // Update pre index price
            pre_index_price = last_index_price;
        }
    }

    private void look_for_basket() {

        // Reset params
        changesCount = 0;

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

            double max = Collections.max(index_changed_list);
            double min = Collections.min(index_changed_list);

            // Up
            if ( max > L.abs(min) ) {
                biggest_change = max;
                add_basket_up();
            }

            // Down
            if (L.abs(min) > max) {
                biggest_change = min;
                add_basket_down();
            }
        }

        // Reset variables
        reset_variables();
    }

    private void reset_variables() {
        index_changed_list.clear();
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

    public int getSleep_for_basket() {
        return sleep_for_basket;
    }

    public double getBiggest_change() {
        return biggest_change;
    }

    public void setSleep_for_basket(int sleep_for_basket) {
        this.sleep_for_basket = sleep_for_basket;
    }

    @Override
    public String getName() {
        return client.getName() + " " + "basket finder";
    }

    @Override
    public int getSleep() {
        return 100;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Target changes= " + targetChanges + "\n");
        str.append("Changes= " + changesCount + "\n");
        str.append("BasketUp= " + basketUp + "\n");
        str.append("BasketDown= " + basketDown + "\n");
        str.append("Biggest change= " + L.floor(biggest_change, 100));
        str.append("Stoocks= " + stocksHandler.toString());
        return str.toString();
    }
}
