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

    private double op_avg_1 = 0;
    private double op_avg_5 = 0;
    private double op_avg_15 = 0;
    private double op_avg_60 = 0;

    protected BASE_CLIENT_OBJECT client;
    protected LocalDate expDate;
    protected double future = 0;
    protected double contract_bid = 0;
    protected double last_checked_future_bid = 0;
    protected double contract_ask = 0;
    protected double last_checked_future_ask = 0;
    protected int contract_bid_ask_counter = 0;

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

    public void set_contract_bid(double new_bid) {
        if (new_bid > 1) {
            // If increment state
            if (new_bid > contract_bid && contract_ask == last_checked_future_ask) {
                contract_bid_ask_counter++;
            }
            this.contract_bid = new_bid;

            // Handle state
            last_checked_future_ask = contract_ask;
            last_checked_future_bid = contract_bid;
        }
    }

    public void set_contract_ask(double new_ask) {
        if (new_ask > 1) {
            // If increment state
            if (new_ask < contract_ask && contract_bid == last_checked_future_bid) {
                contract_bid_ask_counter--;
            }
            this.contract_ask = new_ask;

            // Handle state
            last_checked_future_ask = contract_ask;
            last_checked_future_bid = contract_bid;
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

    public double getContract_bid() {
        return contract_bid;
    }

    public double getContract_ask() {
        return contract_ask;
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

    public int getContract_bid_ask_counter() {
        return contract_bid_ask_counter;
    }

    public void setContract_bid_ask_counter(int contract_bid_ask_counter) {
        this.contract_bid_ask_counter = contract_bid_ask_counter;
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

    public double getOp_avg_1() {
        return op_avg_1;
    }

    public void setOp_avg_1(double op_avg_1) {
        this.op_avg_1 = op_avg_1;
    }

    public double getOp_avg_5() {
        return op_avg_5;
    }

    public void setOp_avg_5(double op_avg_5) {
        this.op_avg_5 = op_avg_5;
    }

    public double getOp_avg_15() {
        return op_avg_15;
    }

    public void setOp_avg_15(double op_avg_15) {
        this.op_avg_15 = op_avg_15;
    }

    public double getOp_avg_60() {
        return op_avg_60;
    }

    public void setOp_avg_60(double op_avg_60) {
        this.op_avg_60 = op_avg_60;
    }
}
