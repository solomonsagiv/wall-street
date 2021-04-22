package baskets;

import locals.L;
import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;
import stocksHandler.MiniStock;
import stocksHandler.StocksHandler;

import java.time.LocalTime;
import java.util.ArrayList;

public class BasketFinder_3 extends MyBaseService {

    // Variables
    private BASE_CLIENT_OBJECT client;
    StocksHandler stocksHandler;
    private int targetChanges = 0;
    private int basketUp = 0;
    private int basketDown = 0;
    private int sleep_for_basket = 0;
    private ArrayList<IndexFrame> index_frames;
    private ArrayList<BasketFrame> frames;
    private int sleep_count = 0;
    private double biggest_change = 0;
    private int sleep_between_frames = 1000;

    public BasketFinder_3(BASE_CLIENT_OBJECT client, int targetChanges, int sleep_for_basket) {
        super(client);
        this.client = client;
        this.targetChanges = targetChanges;
        this.sleep_for_basket = sleep_for_basket;
        this.stocksHandler = client.getStocksHandler();
        index_frames = new ArrayList<>();
        frames = new ArrayList<>();
    }

    @Override
    public void go() {
        // Handle sleep
        sleep_count += getSleep();

        // Collect index changes
        // 100 millis
        append_index_changed();

        // Append frame (volume)
        // 1000 millis
        if (sleep_count % sleep_for_basket == 0) {
            append_frame();
        }

        // Look for basket
        // 1000 millis
        if (sleep_count % sleep_for_basket == 0) {
            look_for_basket();
            sleep_count = 0;
        }
    }

    private void look_for_basket() {

        for (:
             ) {
            
        }
        
    }

    private int get_changed_count() {
        // Reset params
        int changesCount = 0;

        // Look for changes
        for (MiniStock stock : stocksHandler.getStocks()) {
            try {

                // If changed
                if (stock.getVolume() > stock.getVol_0() && stock.getVolume() > 0 && stock.getVol_0() > 0) {
                    changesCount++;
                }

                // Update pre volume
                stock.setVolume_0(stock.getVolume());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return changesCount;
    }

    private double pre_index_price = 0;

    private void append_index_changed() {
        double last_index_price = client.getIndex();

        // If changed
        if (last_index_price != pre_index_price) {

            // Append to changes list
            index_frames.add(new IndexFrame(LocalTime.now(), last_index_price - pre_index_price));

            // Update pre index price
            pre_index_price = last_index_price;
        }
    }

    private void append_frame() {
        // Handle size of frame list
        if (frames.size() >= 3) {
            frames.remove(0);
        }
        // Append frame
        frames.add(new BasketFrame(LocalTime.now(), get_changed_count(), client.getIndex()));
    }

    private class IndexFrame {

        LocalTime time;
        double change;

        public IndexFrame(LocalTime time, double change) {
            this.time = time;
            this.change = change;
        }
    }

    private class BasketFrame {
        LocalTime time;
        double volume;
        double index;

        public BasketFrame(LocalTime time, double volume, double index) {
            this.time = time;
            this.volume = volume;
            this.index = index;
        }
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
