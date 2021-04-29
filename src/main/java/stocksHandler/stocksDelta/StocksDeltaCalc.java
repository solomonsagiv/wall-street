package stocksHandler.stocksDelta;

import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;
import stocksHandler.StocksHandler;

public class StocksDeltaCalc extends MyBaseService {

    BASE_CLIENT_OBJECT client;
    StocksHandler stocksHandler;

    public StocksDeltaCalc(BASE_CLIENT_OBJECT client, BASE_CLIENT_OBJECT client1) {
        super(client);
        this.client = client1;
        this.stocksHandler = client.getStocksHandler();
    }

    @Override
    public void go() {



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
