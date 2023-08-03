package exp;

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
    private double cof = 0;

    private double op_avg_15 = 0;
    private double op_avg_60_continue = 0;
    private double op_avg_240_continue = 0;

    private double op_avg_5 = 0;
    private double op_avg_60 = 0;
    private double op_avg = 0;

    protected BASE_CLIENT_OBJECT client;
    protected LocalDate expDate;
    protected double future = 0;
    protected double contract_bid = 0;
    protected double last_checked_future_bid = 0;
    protected double contract_ask = 0;
    protected double last_checked_future_ask = 0;
    protected double normalized_num = 0;

    public double v107 = 0;
    public double v103 = 0;

    ExpData expData;
    List<Double> opFutList = new ArrayList<>();

    protected Options options;

    String expName;
    TwsContractsEnum twsContractsEnum;

    public Exp(BASE_CLIENT_OBJECT client, String expName) {
        this.client = client;
        this.expName = expName;
        this.expData = new ExpData(expName, client);
        this.options = new Options(client, this);
    }

    public double get_op() {
        return future - client.getIndex();
    }

    public LocalDate getExpDate() {
        return expDate;
    }

    public double get_future() {
        return future;
    }


    double pre_future = 0;

    public void set_future(double future) {
        if (pre_future == 0) {
            pre_future = future;
        }
        
        if (future > 1) {
            this.future = future;
        }
    }

    public double getOp_avg() {
        return op_avg;
    }

    public void setOp_avg(double op_avg) {
        this.op_avg = op_avg;
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

    public List<Double> getOpFutList() {
        return opFutList;
    }

    public TwsContractsEnum getTwsContractsEnum() {
        return twsContractsEnum;
    }

    public String getName() {
        return expName;
    }

    public double getOp_avg_15() {
        return op_avg_15;
    }

    public void setOp_avg_15(double op_avg_15) {
        this.op_avg_15 = op_avg_15;
    }

    public double getOp_avg_60_continue() {
        return op_avg_60_continue;
    }

    public void setOp_avg_60_continue(double op_avg_60_continue) {
        this.op_avg_60_continue = op_avg_60_continue;
    }

    public double getOp_avg_240_continue() {
        return op_avg_240_continue;
    }

    public void setOp_avg_240_continue(double op_avg_240_continue) {
        this.op_avg_240_continue = op_avg_240_continue;
    }

    public double getOp_avg_5() {
        return op_avg_5;
    }

    public void setOp_avg_5(double op_avg_5) {
        this.op_avg_5 = op_avg_5;
    }

    public double getOp_avg_60() {
        return op_avg_60;
    }

    public void setOp_avg_60(double op_avg_60) {
        this.op_avg_60 = op_avg_60;
    }

    public double getV107() {
        return v107;
    }

    public void setV107(double v107) {
        this.v107 = v107;
    }

    public double getV103() {
        return v103;
    }

    public void setV103(double v103) {
        this.v103 = v103;
    }

    public double getCof() {
        return cof;
    }

    public void setCof(double cof) {
        this.cof = cof;
    }

    public double getNormalized_num() {
        return normalized_num;
    }

    public void setNormalized_num(double normalized_num) {
        this.normalized_num = normalized_num;
    }
}
