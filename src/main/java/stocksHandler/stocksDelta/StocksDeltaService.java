package stocksHandler.stocksDelta;

import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;
import stocksHandler.MiniStock;
import stocksHandler.StocksHandler;

import java.time.LocalTime;

public class StocksDeltaService extends MyBaseService {

    BASE_CLIENT_OBJECT client;
    StocksHandler stocksHandler;

    public StocksDeltaService(BASE_CLIENT_OBJECT client) {
        super(client);
        this.client = client;
        this.stocksHandler = client.getStocksHandler();
    }

    @Override
    public void go() {
        try {
            // For each stock
            for (MiniStock stock : stocksHandler.getStocks()) {
                // Check volume
                double volume_quantity = stock.getVolume() - stock.getVolume_0_for_delta();
                if (volume_quantity > 0) {
                    double delta = 0;
                    // Buy
                    if (stock.getLastPrice() >= stock.getAsk_0_for_delta()) {
                        delta = volume_quantity * stock.getWeight();
                    }
                                                                                                
                    // Sell
                    if (stock.getLastPrice() <= stock.getBid_0_for_delta()) {
                        delta = volume_quantity * stock.getWeight() * -1;
                    }

                    // Append delta
                    stock.append_delta(delta);
                    stock.setVolume_0_for_delta(stock.getVolume());
                    stock.setBid_0_for_delta(stock.getBid());
                    stock.setAsk_0_for_delta(stock.getAsk());


//                    System.out.println(stock.getName().replace("\\s+",""));

                    if (stock.getName().replace("\\s+","").contains("SAP GY Equity")) {


                        ddd


                        System.out.println();
                        System.out.println(LocalTime.now());
                        System.out.println("Last " + stock.getLastPrice());
                        System.out.println("Bid " + stock.getBid_0_for_delta());
                        System.out.println("Ask " + stock.getAsk_0_for_delta());
                        System.out.println("Q  " + volume_quantity);
                        System.out.println("Delta " + delta);
                    }



                }
            }
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
