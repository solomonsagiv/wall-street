package basketFinder.handlers;

import basketFinder.MiniStock;
import charts.myChart.MyTimeSeries;
import locals.L;
import myJson.MyJson;
import serverObjects.indexObjects.INDEX_CLIENT_OBJECT;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class StocksHandler {

    // Variables
    protected Map<Integer, MiniStock> stocksMap = new HashMap<>();
    protected int minId = 0, maxId = 0;
    protected int id;
    protected double delta = 0;
    private MyTimeSeries deltaSeries;
    INDEX_CLIENT_OBJECT client;

    // Constructor
    public StocksHandler( int id, INDEX_CLIENT_OBJECT client ) {
        this.id = id;
        this.client = client;
        initSeries();
    }

    private void initSeries() {
        deltaSeries = new MyTimeSeries( "Delta", client ) {
            @Override
            public double getData() throws UnknownHostException {
                return delta;
            }
        };
    }

    // Load
    public void loadStocksFromJson( MyJson json ) {
        int currId = id;
        minId = currId;
        for (String key : json.keySet()) {

            // Mini stock
            MiniStock stock = new MiniStock( key, currId, this );
            stock.setWeight( L.dbl( json.getString( key )) / 100 );

            // Set weight
            stocksMap.put( currId, stock );
            currId++;
        }
        maxId = currId;
    }

    // Getters and setters
    public Map<Integer, MiniStock> getStocksMap() {
        return stocksMap;
    }

    public double getDelta() {
        return delta;
    }

    public int getMinId() {
        return minId;
    }

    public int getMaxId() {
        return maxId;
    }

    public void appendDelta( double delta ) {
        this.delta += delta;
    }

    public MyTimeSeries getDeltaSeries() {
        return deltaSeries;
    }

}
