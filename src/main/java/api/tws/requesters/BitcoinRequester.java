package api.tws.requesters;

import api.Downloader;
import api.Manifest;
import api.tws.ITwsRequester;
import bitcoin.BitcoinChart;
import charts.myCharts.bitcoinCharts.BitcoinLiveChart;
import com.ib.client.TickAttr;
import exp.ExpEnum;
import exp.ExpMonth;
import serverObjects.bitcoinObjects.BITCOIN_CLIENT;
import serverObjects.bitcoinObjects.Bitcoin;
import tws.MyContract;

public class BitcoinRequester implements ITwsRequester {

    public static void main( String[] args ) throws InterruptedException {
        Manifest.CLIENT_ID = 77;
        Downloader downloader = Downloader.getInstance( );
        downloader.start();

        Thread.sleep( 2000 );

        BitcoinRequester requester = new BitcoinRequester();
        downloader.addRequester( requester );
        requester.request( downloader );


        BitcoinLiveChart chart = new BitcoinLiveChart( Bitcoin.getInstance() );
        chart.createChart();
    }

    BITCOIN_CLIENT client;
    ExpMonth expMonth;

    @Override
    public void request( Downloader downloader ) {
        try {

            client = Bitcoin.getInstance();
            expMonth = ( ExpMonth ) client.getExps().getExp( ExpEnum.MONTH );

            // Index contract
            MyContract indContract = new MyContract(  );
            indContract.currency("USD");
            indContract.exchange( "CME" );
            indContract.symbol( "BRTI" );
            indContract.secType( "IND" );
            indContract.includeExpired( false );

//            // Future contract
            MyContract futureContract = new MyContract(  );
            futureContract.currency("USD");
            futureContract.secType( "FUT" );
            futureContract.lastTradeDateOrContractMonth( "20200731" );
            futureContract.multiplier( "5" );
            futureContract.exchange( "CMECRYPTO" );
            futureContract.symbol( "BTCNO" );
            futureContract.tradingClass( "BTC" );

            // Index
            downloader.reqMktData( 200000, indContract );
            downloader.reqMktData( 200001, futureContract );

        } catch ( Exception e ) {
            e.printStackTrace( );
        }
    }

    @Override
    public void reciever( int tickerId, int field, double price, TickAttr attribs ) {
        int index = 200000;
        int minID, maxID;
        int future = 200001;

        System.out.println( "Price: " + price );

        // Index
        if ( tickerId == index && price > 0 ) {

            // Last
            if ( field == 4 ) {
                client.setIndex( price );
            }

            // Bid
            if ( field == 1 ) {
                client.setIndexBid( price );
            }

            // Ask
            if ( field == 2 ) {
                client.setIndexAsk( price );
            }

        }

        // Future
        if ( tickerId == future && price > 0 ) {

            // Last
            if ( field == 4 ) {
                expMonth.setCalcFut( price );
            }
        }
    }


    @Override
    public void sizeReciever( int tickerId, int field, int size ) {

    }
}
