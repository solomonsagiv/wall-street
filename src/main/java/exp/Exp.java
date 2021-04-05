package exp;

import locals.L;
import serverObjects.BASE_CLIENT_OBJECT;
import tws.TwsContractsEnum;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Exp {

    // Variables
    protected BASE_CLIENT_OBJECT client;
    protected LocalDate expDate;
    protected double future = 0;
    protected double futureBid = 0;
    protected double futureAsk = 0;
    protected int futBidAskCounter = 0;
    protected double futDelta = 0;
    ExpData expData;
    List<Double> opFutList = new ArrayList<>();

    protected double op_avg_sum = 0;
    protected int op_avg_sum_count = 0;

    String expName;
    TwsContractsEnum twsContractsEnum;
    private double futureAskForCheck = 0;
    private double futureBidForCheck = 0;

    public Exp(BASE_CLIENT_OBJECT client, String expName) {
        this.client = client;
        this.expName = expName;
        this.expData = new ExpData(expName, client);
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

    public double getFutureBid() {
        return futureBid;
    }

    public void setFutureBid(double futureBid) {

        // If increment state
        if (futureBid > this.futureBid && futureAskForCheck == this.futureAsk && client.isStarted()) {
            futBidAskCounter++;
        }
        this.futureBid = futureBid;

        // Ask for bid change state
        futureAskForCheck = this.futureAsk;

    }

    public double getFutureAsk() {
        return futureAsk;
    }

    public void setFutureAsk(double futureAsk) {

        // If increment state
        if (futureAsk < this.futureAsk && futureBidForCheck == this.futureBid && client.isStarted()) {
            futBidAskCounter--;
        }
        this.futureAsk = futureAsk;

        // Ask for bid change state
        futureBidForCheck = this.futureBid;

    }

    public int getFutBidAskCounter() {
        return futBidAskCounter;
    }

    public void setFutBidAskCounter(int futBidAskCounter) {
        this.futBidAskCounter = futBidAskCounter;
    }

    public double getFutDelta() {
        return futDelta;
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
