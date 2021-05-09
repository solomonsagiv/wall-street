package stocksHandler;

import serverObjects.BASE_CLIENT_OBJECT;

import java.util.ArrayList;

public class StocksHandler {

    private BASE_CLIENT_OBJECT client;
    private ArrayList<MiniStock> stocks = new ArrayList<>();
    private double delta_0 = 0;
    private double delta = 0;

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

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        if (this.delta_0 == 0) {
            this.delta_0 = delta;
        } else {
            this.delta_0 = this.delta;
        }
        this.delta = delta;
    }

    public double getDelta_0() {
        return delta_0;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (MiniStock stock : stocks) {
            str.append(stock.toString() + "\n");
        }
        return str.toString();
    }

    public void append_delta(double delta) {
        this.delta_0 = this.delta;
        this.delta += delta;
    }
}
