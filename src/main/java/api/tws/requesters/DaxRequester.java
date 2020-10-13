package api.tws.requesters;

import api.Downloader;
import api.tws.ITwsRequester;
import api.tws.TwsHandler;
import basketFinder.MiniStock;
import basketFinder.handlers.StocksHandler;
import com.ib.client.Contract;
import com.ib.client.TickAttr;
import exp.E;
import exp.ExpStrings;
import serverObjects.indexObjects.Dax;
import tws.TwsContractsEnum;

import java.util.Map;

public class DaxRequester implements ITwsRequester {

    Dax dax;
    int indexId, futureId;
    E e1;
    StocksHandler stocksHandler;

    @Override
    public void request( Downloader downloader ) {
        try {
            init( );

            TwsHandler twsHandler = dax.getTwsHandler( );

            // Index
            downloader.reqMktData( twsHandler.getMyContract( TwsContractsEnum.INDEX ).getMyId( ), twsHandler.getMyContract( TwsContractsEnum.INDEX ) );
            // Future
            downloader.reqMktData( twsHandler.getMyContract( TwsContractsEnum.FUTURE ).getMyId( ), twsHandler.getMyContract( TwsContractsEnum.FUTURE ) );

        } catch ( Exception e ) {
            e.printStackTrace( );
        }
    }

    private void requestStocks( Downloader downloader ) throws Exception {

        Contract contract = new Contract( );
        contract.secType( "STK" );
        contract.exchange( "SMART" );
        contract.currency( "EUR" );
        contract.primaryExch( "IBIS" );

        for ( Map.Entry< Integer, MiniStock > entry : dax.getStocksHandler( ).getMiniStockMap( ).entrySet( ) ) {
            MiniStock stock = entry.getValue( );
            contract.symbol( stock.getName( ) );

            System.out.println( stock.getName( ) + " ID: " + stock.getId( ) );

            downloader.reqMktData( stock.getId( ), contract );
        }
    }

    private void init() {
        dax = Dax.getInstance( );
        e1 = ( E ) dax.getExps( ).getExp( ExpStrings.e1 );

        indexId = dax.getTwsHandler( ).getMyContract( TwsContractsEnum.INDEX ).getMyId( );
        futureId = dax.getTwsHandler( ).getMyContract( TwsContractsEnum.FUTURE ).getMyId( );
        stocksHandler = dax.getStocksHandler( );
    }

    @Override
    public void reciever( int tickerId, int field, double price, TickAttr attribs ) {

        if ( tickerId == indexId && price > 0 ) {
            if ( field == 4 ) {
                dax.setIndex( price );
                dax.setIndexAsk( price + 2 );
                dax.setIndexBid( price - 2 );
            }

            if ( field == 9 ) {
                dax.setBase( price );
            }
        }

        if ( tickerId == futureId && price > 0 ) {
            if ( field == 4 ) {
                e1.setCalcFut( price );
                e1.setFutForDelta( price );
            }

            if ( field == 1 ) {
                e1.setCalcFutBid( price );
                e1.setBidForDelta( price );
            }

            if ( field == 2 ) {
                e1.setCalcFutAsk( price );
                e1.setAskForDelta( price );
            }
        }
    }

    @Override
    public void sizeReciever( int tickerId, int field, int size ) {
        if ( dax.isStarted( ) ) {
            // Last
            if ( tickerId == futureId && size > 0 ) {
                if ( field == 8 ) {
                    e1.setVolumeFutForDelta( size );
                }
            }
        }
    }
}
