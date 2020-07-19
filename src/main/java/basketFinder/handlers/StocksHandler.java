package basketFinder.handlers;

import basketFinder.MiniStock;

import java.util.HashMap;
import java.util.Map;

public abstract class StocksHandler {

    // Variables
    protected Map<Integer, MiniStock> miniStockMap = new HashMap<>();

    protected int minId = 99999999, maxId = 0;
    protected int id;

    public StocksHandler(int id) {
        this.id = id;
    }

    public Map<Integer, MiniStock> getMiniStockMap() {
        return miniStockMap;
    }

    public int getMinId() {
        return minId;
    }

    private void setMinId(int id) {
        if (id < minId) {
            minId = id;
        }
    }

    public int getMaxId() {
        return maxId;
    }

    private void setMaxId(int id) {
        if (id > maxId) {
            maxId = id;
        }
    }

    public void addStock(String stock) {

        setMinId(id);
        setMaxId(id);

        miniStockMap.put(id, new MiniStock(stock, id++));
    }
}
