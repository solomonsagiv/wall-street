package stocksHandler.stocksDelta;

import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;
import stocksHandler.MiniStock;
import stocksHandler.StocksHandler;

public class StocksDeltaService extends MyBaseService {

    private BASE_CLIENT_OBJECT client;
    private StocksHandler stocksHandler;



    public StocksDeltaService(BASE_CLIENT_OBJECT client) {
        super(client);
        this.client = client;
        this.stocksHandler = client.getStocksHandler();
    }

    @Override
    public void go() {
        try {

            double sum_delta = 0;

            // For each stock
            for (MiniStock stock : stocksHandler.getStocks()) {

            }

            // Append delta
            stocksHandler.append_delta(sum_delta);
        } catch (Exception e) {
            e.printStackTrace();
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
