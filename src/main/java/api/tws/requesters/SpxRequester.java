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
import serverObjects.indexObjects.Spx;
import tws.TwsContractsEnum;

import java.util.Map;

public class SpxRequester implements ITwsRequester {

    Spx spx;
    int indexId, futureId, futureFarId;
    E e1, e2;
    StocksHandler stocksHandler;

    @Override
    public void request( Downloader downloader ) {
        try {
            init( );

            TwsHandler twsHandler = spx.getTwsHandler( );

            // Index
            downloader.reqMktData( twsHandler.getMyContract( TwsContractsEnum.INDEX ).getMyId( ), twsHandler.getMyContract( TwsContractsEnum.INDEX ) );

            // Futures
            // Quarter
            downloader.reqMktData( twsHandler.getMyContract( TwsContractsEnum.FUTURE ).getMyId( ), twsHandler.getMyContract( TwsContractsEnum.FUTURE ) );
            downloader.reqMktData( twsHandler.getMyContract( TwsContractsEnum.FUTURE_FAR ).getMyId( ), twsHandler.getMyContract( TwsContractsEnum.FUTURE_FAR ) );

            // Stocks
//            requestStocks( downloader );

        } catch ( Exception e ) {
            e.printStackTrace( );
        }
    }

    private void requestStocks( Downloader downloader ) throws Exception {

        Contract contract = new Contract( );
        contract.secType( "STK" );
        contract.exchange( "SMART" );
        contract.currency( "USD" );
        contract.primaryExch( "ISLAND" );

        for ( Map.Entry< Integer, MiniStock > entry : spx.getStocksHandler( ).getStocksMap( ).entrySet( ) ) {
            try {
                MiniStock stock = entry.getValue( );
                contract.symbol( stock.getName( ) );
                
                downloader.reqMktData( stock.getId( ), contract );
            } catch ( Exception e ) {
                e.printStackTrace( );
            }
        }
    }

    private void init() {
        spx = Spx.getInstance( );
        stocksHandler = spx.getStocksHandler( );
        e1 = ( E ) spx.getExps( ).getExp( ExpStrings.e1 );
        e2 = ( E ) spx.getExps( ).getExp( ExpStrings.e2 );

        indexId = spx.getTwsHandler( ).getMyContract( TwsContractsEnum.INDEX ).getMyId( );
        futureId = spx.getTwsHandler( ).getMyContract( TwsContractsEnum.FUTURE ).getMyId( );
        futureFarId = spx.getTwsHandler( ).getMyContract( TwsContractsEnum.FUTURE_FAR ).getMyId( );
    }

    @Override
    public void reciever( int tickerId, int field, double price, TickAttr attribs ) {

        if ( spx.isStarted( ) ) {

            // ---------- Future ---------- //
            if ( tickerId == futureId && price > 0 ) {

                // Bid
                if ( field == 1 ) {
                    e1.setCalcFutBid( price );
                    e1.setBidForDelta( price );
                }

                // Ask
                if ( field == 2 ) {
                    e1.setCalcFutAsk( price );
                    e1.setAskForDelta( price );
                }

                // Last
                if ( field == 4 ) {
                    e1.setFutForDelta( price );
                }
            }

            // ---------- Future far ---------- //
            if ( tickerId == futureFarId && price > 0 ) {
                // Bid
                if ( field == 1 ) {
                    e2.setCalcFutBid( price );
                }
                // Ask
                if ( field == 2 ) {
                    e2.setCalcFutAsk( price );
                }
            }

            // Spx miniStocks
            if ( tickerId >= stocksHandler.getMinId( ) && tickerId < stocksHandler.getMaxId( ) ) {

                System.out.println( tickerId );

                MiniStock stock = stocksHandler.getStocksMap( ).get( tickerId );

                // Bid
                if ( field == 1 ) {
                    stock.setIndBid( price );
                }

                // Ask
                if ( field == 2 ) {
                    stock.setIndAsk( price );
                }

                // Last
                if ( field == 4 ) {
                    stock.setInd( price );
                }
            }
        }
    }

    @Override
    public void sizeReciever( int tickerId, int field, int size ) {
        if ( spx.isStarted( ) ) {
            // Last
            if ( tickerId == futureId && size > 0 ) {
                if ( field == 8 ) {
                    e1.setVolumeFutForDelta( size );
                }
            }
        }

        // Spx miniStocks
        if ( tickerId >= stocksHandler.getMinId( ) && tickerId < stocksHandler.getMaxId( ) ) {

            MiniStock stock = stocksHandler.getStocksMap( ).get( tickerId );

            if ( field == 8 ) {
                stock.setVolume( size );
            }

        }

    }
}
