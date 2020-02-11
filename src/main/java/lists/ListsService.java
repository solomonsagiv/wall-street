package lists;

import options.Options;
import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;

import java.util.ArrayList;
import java.util.List;

// Regular list updater
public class ListsService extends MyBaseService {

    List indexList = new ArrayList< Double >( );
    List indexBidList = new ArrayList< Double >( );
    List indexAskList = new ArrayList< Double >( );
    List indexRacesList = new ArrayList< Integer >( );

    // Variables
    BASE_CLIENT_OBJECT client;

    // Constructor
    public ListsService(BASE_CLIENT_OBJECT client, String name, int type, int sleep) {
        super(client, name, type, sleep);
        this.client = client;
    }

    @Override
    public void go() {
        insert();
    }

    private void insert() {

        // TODO
        // Client lists
        client.getIndexList().add( client.getIndex() );
        client.getIndexBidList().add( client.getIndexBid() );
        client.getIndexAskList().add( client.getIndexAsk() );
        client.getIndexRacesList().add( client.getIndexSum() );

        // Options lists
        for ( Options options: client.getOptionsHandler().getOptionsList() ) {
            options.getOpList().add( options.getOp() );
            options.getOpAvgList().add( options.getOpAvg() );
            options.getConList().add( options.getContract() );
            options.getConBidList().add( options.getContractBid() );
            options.getConAskList().add( options.getContractAsk() );

            // Equal Move
            options.getEqualMoveService().getMoveList().add( options.getEqualMoveService().getMove() );
            options.getOpAvgMoveService().getMoveList().add( options.getOpAvgMoveService().getMove() );
        }


    }
}