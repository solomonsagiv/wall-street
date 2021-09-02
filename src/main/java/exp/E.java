package exp;

import delta.DeltaCalc;
import serverObjects.BASE_CLIENT_OBJECT;

public class E extends Exp {

    private int delta = 0;
    private int volume = 0;
    protected int last_deal_quantity = 0;

    protected double naked_future_bid = 0;
    protected double naked_future_ask = 0;
    protected double naked_future = 0;

    public E(BASE_CLIENT_OBJECT client, String expEnum) {
        super(client, expEnum);
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        int quantity = volume - this.volume;
        if (quantity > 0 && this.volume > 0 && client.isStarted()) {
            last_deal_quantity = quantity;
            calc_delta();
        }
        this.volume = volume;
    }

    private void calc_delta() {
        new Thread(() -> {
            double delta = DeltaCalc.calc(last_deal_quantity, naked_future, naked_future_bid, naked_future_ask);
            append_delta(delta);
        }).start();
    }

    public void append_delta(double delta) {
        this.delta += delta;
    }

    public int getDelta() {
        return delta;
    }

    public void setDelta(int delta) {
        this.delta = delta;
    }

    public void setNaked_future(double naked_future) {
        this.naked_future = naked_future;
    }

    public void setNaked_future_bid(double naked_future_bid) {
        this.naked_future_bid = naked_future_bid;
    }

    public void setNaked_future_ask(double naked_future_ask) {
        this.naked_future_ask = naked_future_ask;
    }
}
