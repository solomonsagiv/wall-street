package stocksHandler.stocksDelta;

import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;
import stocksHandler.MiniStock;
import stocksHandler.StocksHandler;

public class StocksDeltaCalc extends MyBaseService {

    BASE_CLIENT_OBJECT client;
    StocksHandler stocksHandler;

    public StocksDeltaCalc(BASE_CLIENT_OBJECT client) {
        super(client);
        this.client = client;
        this.stocksHandler = client.getStocksHandler();
    }

    @Override
    public void go() {

        // For each stock
        for (MiniStock stock : stocksHandler.getStocks()) {

            // Check volume
            double volume_quantity = stock.getVolume() - stock.getVolume_0();
            if ( volume_quantity > 0 ) {
                double delta = 0;
                // Buy
                if (stock.getLastPrice() >= stock.getAsk_0()) {
                    delta = volume_quantity * stock.getWeight();
                }

                // Sell
                if (stock.getLastPrice() <= stock.getBid_0()) {
                    delta = volume_quantity * stock.getWeight() * -1;
                }

                // Append delta
                stock.append_delta(delta);
            }
        }

    }

    @Override
    public String getName() {
        return "Stocks delta calc";
    }

    @Override
    public int getSleep() {
        return 100;
    }
}
