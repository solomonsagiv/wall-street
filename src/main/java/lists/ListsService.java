package lists;

import options.Options;
import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;

import java.util.ArrayList;
import java.util.List;

// Regular list updater
public class ListsService extends MyBaseService {

    // Variables
    BASE_CLIENT_OBJECT client;

    // Constructor
    public ListsService(BASE_CLIENT_OBJECT client) {
        super( client );
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
    public int getType() {
        return MyBaseService.REGULAR_LISTS;
    }

    private void insert() {

        // TODO
        // Client lists
        client.getIndexList().add( client.getIndex() );
        client.getIndexBidList().add( client.getIndexBid() );
        client.getIndexAskList().add( client.getIndexAsk() );
        client.getIndexRacesList().add( ( double ) client.getIndexSum() );

        // Options lists
        for ( Options options: client.getOptionsHandler().getOptionsList() ) {
            options.getOpList().add( options.getOp() );
            options.getOpAvgList().add( options.getOpAvg() );
            options.getConList().add( options.getContract() );
            options.getConBidList().add( options.getContractBid() );
            options.getConAskList().add( options.getContractAsk() );
        }


    }
}