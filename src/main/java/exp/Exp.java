package exp;

import charts.myChart.MyTimeSeries;
import dataBase.mySql.MySql;
import dataBase.mySql.dataUpdaters.IDataBaseHandler;
import locals.L;
import serverObjects.BASE_CLIENT_OBJECT;
import tws.TwsContractsEnum;

import java.net.UnknownHostException;
import java.sql.ResultSet;
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
    List<Double> futList = new ArrayList<>();

    protected double op_avg_sum = 0;
    protected int op_avg_sum_count = 0;

    String expName;
    TwsContractsEnum twsContractsEnum;
    private double futureAskForCheck = 0;
    private double futureBidForCheck = 0;

    private MyTimeSeries op_avg_serie;
    private MyTimeSeries op_avg_15_serie;

    IDataBaseHandler dataBaseHandler;

    public Exp(BASE_CLIENT_OBJECT client, String expName) {
        this.client = client;
        this.expName = expName;
        this.expData = new ExpData(expName, client);
        this.dataBaseHandler = client.getMySqlService().getDataBaseHandler();
        init_series();
    }

    protected void init_series() {
        op_avg_serie = new MyTimeSeries("O/P avg", client) {
            @Override
            public double getData() throws UnknownHostException {
                return get_op_avg();
            }

            @Override
            public void load_data() {

                String index_table = dataBaseHandler.get_table_loc(IDataBaseHandler.INDEX_TABLE);
                String fut_table = dataBaseHandler.get_table_loc(expName);
                ResultSet rs = MySql.Queries.op_avg_cumulative_query(index_table, fut_table);
                IDataBaseHandler.loadSerieData(rs, op_avg_serie);
            }
        };

        op_avg_15_serie = new MyTimeSeries("O/P avg 15", client) {
            @Override
            public double getData() throws UnknownHostException {
                return getOpAvgFut(900);
            }

            @Override
            public void load_data() {
                String index_table = dataBaseHandler.get_table_loc(IDataBaseHandler.INDEX_TABLE);
                String fut_table = dataBaseHandler.get_table_loc(expName);
                ResultSet rs = MySql.Queries.op_avg_cumulative_query(index_table, fut_table, 15);
                IDataBaseHandler.loadSerieData(rs, op_avg_serie);
            }
        };
    }

    public void add_op(double op) {
        op_avg_sum += op;
        op_avg_sum_count++;
    }

    public void set_op_avg(double sum, int sum_count) {
        op_avg_sum = sum;
        op_avg_sum_count = sum_count;
    }

    public double get_op_avg() {
        return L.floor(op_avg_sum / (double) op_avg_sum_count, 100);
    }

    public double getFutureOp() {
        return future - client.getIndex();
    }


    public double getOpAvgFut() throws UnknownHostException {
        double sum = 0;
        if (!opFutList.isEmpty()) {
            try {
                for (int i = 0; i < opFutList.size(); i++) {
                    sum += opFutList.get(i);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return L.floor(sum / opFutList.size(), 100);
        } else {
            throw new NullPointerException(client.getName() + " op future list empty");
        }
    }

    public double getOpAvgFut(int secondes) {
        try {

            // If op future list < seconds
            if (secondes > opFutList.size() - 1) {
                return getOpAvgFut();
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

    public double getOpFuture() {
        return future - client.getIndex();
    }

    public void setOpAvgFuture(double opAvg) {
        int size = opFutList.size();
        opFutList.clear();

        for (int i = 0; i < size; i++) {
            opFutList.add(opAvg);
        }
    }

    public LocalDate getExpDate() {
        return expDate;
    }

    public double getFuture() {
        return future;
    }

    public void setFuture(double future) {
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

    public MyTimeSeries getOp_avg_serie() {
        return op_avg_serie;
    }

    public MyTimeSeries getOp_avg_15_serie() {
        return op_avg_15_serie;
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

    public List<Double> getFutList() {
        return futList;
    }

    public TwsContractsEnum getTwsContractsEnum() {
        return twsContractsEnum;
    }

    public String getName() {
        return expName;
    }

}
