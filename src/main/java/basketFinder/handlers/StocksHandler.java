package basketFinder.handlers;

import basketFinder.MiniStock;

import java.util.HashMap;
import java.util.Map;

public abstract class StocksHandler {

    // Variables
    protected Map< Integer, MiniStock> miniStockMap = new HashMap<>( );

    protected int minId, maxId;

    public StocksHandler( int id ) {
        initStocks( id );
    }

    public abstract void initStocks(int id );

    public Map<Integer, MiniStock> getMiniStockMap() {
        return miniStockMap;
    }

    public int getMinId() {
        return minId;
    }

    public int getMaxId() {
        return maxId;
    }

    protected void setMinId( int id ) {
        this.minId = id;
    }

    protected void setMaxId( int id ) {
        this.minId = id;
    }
}
