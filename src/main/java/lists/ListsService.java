package lists;

import options.Options;
import org.jfree.data.time.Second;
import roll.Roll;
import roll.RollEnum;
import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;
import service.ServiceEnum;

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

        // List for charts
        client.getIndexList().add(new MyChartPoint(new Second(), client.getIndex()));
        client.getIndexBidList().add(new MyChartPoint(new Second(), client.getIndexBid()));
        client.getIndexAskList().add(new MyChartPoint(new Second(), client.getIndexAsk()));
        client.getIndexBidAskCounterList().add(new MyChartPoint(new Second(), client.getIndexBidAskCounter()));

        // Options lists
        for (Options options : client.getOptionsHandler().getOptionsList()) {
            options.getOpList().add(options.getOp());
            options.getOpAvgList().add(options.getOpAvg());
            options.getConList().add(options.getContract());
            options.getConBidList().add(options.getContractBid());
            options.getConAskList().add(options.getContractAsk());
        }

        // Roll lists
        for (Map.Entry<RollEnum, Roll> entry : getClient().getRollHandler().getRollMap().entrySet()) {
            Roll roll = entry.getValue();
            roll.addRoll();
        }

    }
}