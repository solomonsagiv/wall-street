package lists;

import options.Options;
import roll.Roll;
import roll.RollEnum;
import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;
import service.ServiceEnum;

import java.time.LocalTime;
import java.util.Map;

// Regular list updater
public class ListsService extends MyBaseService {

    // Variables
    BASE_CLIENT_OBJECT client;

    // Constructor
    public ListsService( BASE_CLIENT_OBJECT client ) {
        super( client );
        this.client = client;
    }

    @Override
    public void go() {
        insert( );
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

        LocalTime now = LocalTime.now();

        // List for charts
        client.getIndexList( ).add( new MyChartPoint( now, client.getIndex( ) ) );
        client.getIndexBidList( ).add( new MyChartPoint( now, client.getIndexBid( ) ) );
        client.getIndexAskList( ).add( new MyChartPoint( now, client.getIndexAsk( ) ) );
        client.getIndexBidAskCounterList( ).add( new MyChartPoint( now, client.getIndexBidAskCounter( ) ) );

        // Options lists
        for ( Options options : client.getOptionsHandler( ).getOptionsList( ) ) {
            options.getOpList( ).add( options.getOp( ) );
            options.getOpAvgList( ).add( options.getOpAvg( ) );
            options.getConList( ).add( options.getContract( ) );
            options.getConBidList( ).add( options.getContractBid( ) );
            options.getConAskList( ).add( options.getContractAsk( ) );
            options.getFutBidAskCounterList().add( new MyChartPoint(now, options.getContractBidAskCounter()) );

        }

        // Roll lists
        for ( Map.Entry< RollEnum, Roll > entry : getClient( ).getRollHandler( ).getRollMap( ).entrySet( ) ) {
            Roll roll = entry.getValue( );
            roll.addRoll( );
        }

    }
}