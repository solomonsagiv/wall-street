package lists;

import exp.Exp;
import options.Options;
import roll.Roll;
import roll.RollEnum;
import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;
import service.ServiceEnum;

import java.time.LocalDateTime;
import java.util.Map;

// Regular list updater
public class ListsService extends MyBaseService {

    // Variables
    BASE_CLIENT_OBJECT client;

    // Constructor
    public ListsService(BASE_CLIENT_OBJECT client) {
        super(client);
        this.client = client;
    }

    @Override
    public void go() {
        insert();
    }

    @Override
    public String getName() {
        return "lists";
    }

    @Override
    public int getSleep() {
        return 1000;
    }

    @Override
    public ServiceEnum getType() {
        return ServiceEnum.REGULAR_LISTS;
    }

    private void insert() {

        LocalDateTime now = LocalDateTime.now();

        // List for charts
        client.getIndexBidSeries().add();
        client.getIndexAskSeries().add();
        client.getIndexBidAskCounterSeries().add();

        // Options lists
        for ( Exp exp : client.getExps().getExpList()) {
            try {
                exp.getFutureList().add(exp.getFuture());
                client.getIndexSeries().add();
                exp.getOpFutureList().add(exp.getOpFuture());
                try {
                    exp.getOpAvgFutureSeries().add();
                    exp.getOpAvg15FutureSeries().add();
                } catch (Exception e) {
                    System.out.println(getClient().getName() + " OpAvgFutureList is empty");
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            Options options = exp.getOptions();
            options.getOpList().add(options.getOp());
            options.getOpAvgList().add(options.getOpAvg());
            options.getConList().add(options.getContract());
            options.getConBidList().add(options.getContractBid());
            options.getConAskList().add(options.getContractAsk());
            exp.getFutBidAskCounterSeries().add();
            options.getConBidAskCounterSeries().add();
        }

        // Roll lists
        try {
            for (Map.Entry<RollEnum, Roll> entry : getClient().getRollHandler().getRollMap().entrySet()) {
                Roll roll = entry.getValue();
                roll.addRoll();
            }
        } catch (NullPointerException e) {
        }
    }
}