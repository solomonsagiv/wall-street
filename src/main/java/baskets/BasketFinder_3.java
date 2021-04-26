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
    private int changesCount = 0;
    StocksHandler stocksHandler;
    private int targetChanges = 0;
    private int basketUp = 0;
    private int basketDown = 0;
    private int time_frame_for_basket = 0;
    private BigFrame bigFrame;
    private int sleep_count = 0;
    private double biggest_change = 0;
    private int sleep_between_frames = 1000;
    private int big_frame_time_in_secondes;

    public BasketFinder_3(BASE_CLIENT_OBJECT client, int targetChanges, int time_frame_for_basket, int big_frame_time_in_secondes) {
        super(client);
        this.client = client;
        this.targetChanges = targetChanges;
        this.time_frame_for_basket = time_frame_for_basket;
        this.big_frame_time_in_secondes = big_frame_time_in_secondes;
        this.stocksHandler = client.getStocksHandler();
        this.bigFrame = new BigFrame();
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
        if (sleep_count % sleep_between_frames == 0) {
            append_volume_frame();
        }

        // Look for basket
        // 1000 millis
        if (sleep_count % sleep_between_frames == 0) {
            look_for_basket();
            sleep_count = 0;
        }
    }

    private void append_volume_frame() {
        changesCount = find_volume_change_count();
        bigFrame.append_volume(LocalTime.now(),changesCount);
    }

    private int find_volume_change_count() {
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

    private void look_for_basket() {
        // If got enough changes
        if (bigFrame.get_volume_sum() >= targetChanges) {
            // Basket up or down
            if (bigFrame.is_index_up()) {
                add_basket_up();
            } else {
                add_basket_down();
            }
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
            bigFrame.append_change_frame(LocalTime.now(), last_index_price - pre_index_price);

            // Update pre index price
            pre_index_price = last_index_price;
        }
    }

    private class IndexFrame {
        LocalTime time;
        double change;

        public IndexFrame(LocalTime time, double change) {
            this.time = time;
            this.change = change;
        }
    }

    private class VolumeFrame {
        LocalTime time;
        double volume;

        public VolumeFrame(LocalTime time, double volume) {
            this.time = time;
            this.volume = volume;
        }
    }

    private class BigFrame {

        private LocalTime start_time;
        private LocalTime end_time;

        private ArrayList<IndexFrame> indexFrames;
        private ArrayList<VolumeFrame> volumeFrames;

        public BigFrame() {
            indexFrames = new ArrayList<>();
            volumeFrames = new ArrayList<>();
        }

        public void append_change_frame(LocalTime time, double value) {
            indexFrames.add(new IndexFrame(time, value));

            // Remove data before last sec
            if (indexFrames.get(0).time.isBefore(time.minusSeconds(big_frame_time_in_secondes))) {
                indexFrames.remove(0);
            }
        }

        public void append_volume(LocalTime time, double value) {
            volumeFrames.add(new VolumeFrame(time, value));

            // Remove data before last 5 sec
            if (volumeFrames.get(0).time.isBefore(time.minusSeconds(big_frame_time_in_secondes))) {
                volumeFrames.remove(0);
            }
        }

        public boolean is_index_up() {
            double change = 0;
            double change_abs = 0;
            for (IndexFrame index_frame : indexFrames) {
                // If time is between start and end (Frame)
                // Set biggest change if new got one
                if (L.abs(index_frame.change) > change_abs) {
                    change = index_frame.change;
                    change_abs = L.abs(index_frame.change);
                }
            }
            
            // Up or down
            return change > 0;
        }

        public double get_volume_sum() {
            double sum = 0;

            for (int i = 0; i < volumeFrames.size() - 1; i++) {
                sum += volumeFrames.get(i).volume;
            }
            return sum;
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

    public int getTime_frame_for_basket() {
        return time_frame_for_basket;
    }

    public double getBiggest_change() {
        return biggest_change;
    }

    public void setTime_frame_for_basket(int time_frame_for_basket) {
        this.time_frame_for_basket = time_frame_for_basket;
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
