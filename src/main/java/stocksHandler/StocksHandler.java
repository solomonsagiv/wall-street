package stocksHandler;

import serverObjects.BASE_CLIENT_OBJECT;

import java.util.ArrayList;

public class StocksHandler {

    private BASE_CLIENT_OBJECT client;
    private ArrayList<MiniStock> stocks = new ArrayList<>();

    public StocksHandler(BASE_CLIENT_OBJECT client) {
        this.client = client;
    }

    public ArrayList<MiniStock> getStocks() {
        return stocks;
    }

    public void addStock(String name) {
        stocks.add(new MiniStock(name));
    }

    public void addStock(String name, int row) {
        System.out.println("Stock added " + name);
        stocks.add(new MiniStock(name, row));

    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (MiniStock stock : stocks) {
            str.append(stock.toString() + "\n");
        }
        return str.toString();
    }
}
