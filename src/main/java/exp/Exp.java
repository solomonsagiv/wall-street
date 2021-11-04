package exp;

import locals.L;
import options.Options;
import serverObjects.BASE_CLIENT_OBJECT;
import tws.TwsContractsEnum;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Exp {

    // Variables
    private double dividend = 0;
    private double interest = 0;
    private double days_to_exp = -1;

    protected BASE_CLIENT_OBJECT client;
    protected LocalDate expDate;
    protected double future = 0;
    protected double future_bid = 0;
    protected double future_ask = 0;
    protected int fut_bid_ask_counter = 0;

    protected int last_deal_quantity = 0;
    ExpData expData;
    List<Double> opFutList = new ArrayList<>();

    protected double op_avg_sum = 0;
    protected int op_avg_sum_count = 0;

    protected Options options;

    String expName;
    TwsContractsEnum twsContractsEnum;

    public Exp(BASE_CLIENT_OBJECT client, String expName) {
        this.client = client;
        this.expName = expName;
        this.expData = new ExpData(expName, client);
        this.options = new Options(client, this);
    }

    public void add_op(double op) {
        op_avg_sum += op;
        op_avg_sum_count++;
    }

    public void add_op() {
        op_avg_sum += future - client.getIndex();
        op_avg_sum_count++;
    }

    public void set_op_avg(double sum, int sum_count) {
        op_avg_sum = sum;
        op_avg_sum_count = sum_count;
    }

    public double get_op_avg() {
        return L.floor(op_avg_sum / (double) op_avg_sum_count, 100);
    }

    public double get_op_avg(int secondes) {
        try {

            // If op future list < seconds
            if (secondes > opFutList.size() - 1) {
                return get_op_avg();
            }

            double sum = 0;

            for (int i = opFutList.size() - secondes; i < opFutList.size(); i++) {
                sum += opFutList.get(i);
            }

            return L.floor(sum / secondes, 100);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public double get_op() {
        return future - client.getIndex();
    }

    public void set_op_avg(double opAvg) {
        // Op by sum count
        int size = op_avg_sum_count;
        op_avg_sum = opAvg * size;

        // Op list
        opFutList.clear();
        for (int i = 0; i < opFutList.size(); i++) {
            opFutList.add(opAvg);
        }
    }

    public LocalDate getExpDate() {
        return expDate;
    }

    public double get_future() {
        return future;
    }

    public void set_future(double future) {
        if (future > 1) {
            this.future = future;
        }
    }

    public double getFuture_bid() {
        return future_bid;
    }

    public void setFuture_bid(double future_bid) {
            this.future_bid = future_bid;
    }

    public double getFuture_ask() {
        return future_ask;
    }

    public void setFuture_ask(double future_ask) {
            this.future_ask = future_ask;
    }

    public double getDividend() {
        return dividend;
    }

    public void setDividend(double dividend) {
        this.dividend = dividend;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public double getDays_to_exp() {
        return days_to_exp;
    }

    public void setDays_to_exp(double days_to_exp) {
        this.days_to_exp = days_to_exp;
    }

    public int getFut_bid_ask_counter() {
        return fut_bid_ask_counter;
    }

    public void setFut_bid_ask_counter(int fut_bid_ask_counter) {
        this.fut_bid_ask_counter = fut_bid_ask_counter;
    }

    public List<Double> getOpFutList() {
        return opFutList;
    }

    public TwsContractsEnum getTwsContractsEnum() {
        return twsContractsEnum;
    }

    public String getName() {
        return expName;
    }

}
