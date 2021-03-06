package lists;

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
        client.getIndexBidList().add(new MyChartPoint(now, client.getIndexBid()));
        client.getIndexAskList().add(new MyChartPoint(now, client.getIndexAsk()));
        client.getIndexBidAskCounterList().add(new MyChartPoint(now, client.getIndexBidAskCounter()));
        client.getIndexBidAskCounter2List().add(new MyChartPoint(now, client.getIndexBidAskCounter2()));

        // Options lists
        for (Options options : client.getOptionsHandler().getOptionsList()) {
            try {
                options.getFutureList().add(options.getFuture());
                client.getIndexList().add(new MyChartPoint(now, client.getIndex()));
                options.getOpFutureList().add(options.getOpFuture());
                try {
                    options.getOpAvgFutureList().add(new MyChartPoint(now, options.getOpAvgFuture()));
                    options.getOpAvg15FutureList().add(new MyChartPoint(now, options.getOpAvgFuture(900)));
                } catch (Exception e) {
                    System.out.println(getClient().getName() + " OpAvgFutureList is empty");
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            options.getOpList().add(options.getOp());
            options.getOpAvgList().add(options.getOpAvg());
            options.getConList().add(options.getContract());
            options.getConBidList().add(options.getContractBid());
            options.getConAskList().add(options.getContractAsk());
            options.getFutBidAskCounterList().add(new MyChartPoint(now, options.getFutureBidAskCounter()));
            options.getConBidAskCounterList().add(new MyChartPoint(now, options.getConBidAskCounter()));
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