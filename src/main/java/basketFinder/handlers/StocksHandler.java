package basketFinder.handlers;

import basketFinder.MiniStock;
import myJson.MyJson;

import java.util.HashMap;
import java.util.Map;

public class StocksHandler {

    // Variables
    protected Map<Integer, MiniStock> stocksMap = new HashMap<>();

    protected int minId = 0, maxId = 0;
    protected int id;

    public StocksHandler(int id) {
        this.id = id;
    }

    public Map<Integer, MiniStock> getStocksMap() {
        return stocksMap;
    }

    public void loadStocksFromJson( MyJson json ) {
        int currId = id;
        minId = currId;
        for (String key : json.keySet()) {
            stocksMap.put( currId, new MiniStock( key, currId ));
            currId++;
        }
        maxId = currId;
    }

    public int getMinId() {
        return minId;
    }

    public int getMaxId() {
        return maxId;
    }
}
