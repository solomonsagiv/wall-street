package api.tws.requesters;

import api.Downloader;
import api.tws.ITwsRequester;
import api.tws.TwsHandler;
import basketFinder.MiniStock;
import basketFinder.handlers.StocksHandler;
import com.ib.client.Contract;
import com.ib.client.TickAttr;
import delta.DeltaCalc;
import exp.Exp;
import exp.ExpEnum;
import serverObjects.indexObjects.Dax;
import tws.TwsContractsEnum;

import java.util.Map;

public class DaxRequester implements ITwsRequester {

    Dax dax;
    int indexId, futureId, futureFarId;
    Exp expWeek, expMonth;
    StocksHandler stocksHandler;
    DeltaCalc deltaCalc;

    @Override
    public void request( Downloader downloader ) {
        try {
            init( );

            TwsHandler twsHandler = dax.getTwsHandler( );

            // Index
            downloader.reqMktData( twsHandler.getMyContract( TwsContractsEnum.INDEX ).getMyId( ), twsHandler.getMyContract( TwsContractsEnum.INDEX ) );
            // Future
            downloader.reqMktData( twsHandler.getMyContract( TwsContractsEnum.FUTURE ).getMyId( ), twsHandler.getMyContract( TwsContractsEnum.FUTURE ) );
            // Stocks
            requestStocks( downloader );

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
        expWeek = dax.getExps( ).getExp( ExpEnum.WEEK );
        expMonth = dax.getExps( ).getExp( ExpEnum.MONTH );

        indexId = dax.getTwsHandler( ).getMyContract( TwsContractsEnum.INDEX ).getMyId( );
        futureId = dax.getTwsHandler().getMyContract( TwsContractsEnum.FUTURE ).getMyId();
        stocksHandler = dax.getStocksHandler( );
        deltaCalc = new DeltaCalc( dax );
    }

    @Override
    public void reciever( int tickerId, int field, double price, TickAttr attribs ) {

        if ( tickerId == indexId && price > 0 ) {
            if ( field == 4 ) {
                dax.setIndex( price );
            }

            if ( field == 9 ) {
                dax.setBase( price );
            }
        }

        if ( tickerId == futureId && price > 0 ) {
            if ( field == 4 ) {
                expMonth.setFut( price );
            }

            if ( field == 1 ) {
                expMonth.setFutBid( price );
            }

            if ( field == 2 ) {
                expMonth.setFutAsk( price );
            }
            
        }

        if ( dax.isStarted( ) ) {

            // Spx miniStocks
            if ( tickerId >= stocksHandler.getMinId( ) && tickerId < stocksHandler.getMaxId( ) ) {

                MiniStock stock = stocksHandler.getMiniStockMap( ).get( tickerId );

                // Bid
                if ( field == 1 ) {
                    stock.setIndexBid( price );
                }

                // Ask
                if ( field == 2 ) {
                    stock.setIndexAsk( price );
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

        if ( tickerId == futureId ) {
            if ( field == 8 ) {
                expMonth.setFutVolume( size );

                deltaCalc.calc( expMonth.getOptions(), size, expMonth.getFut() );

                System.out.println("size " + size );
            }
        }

        // Spx miniStocks
        if ( tickerId >= stocksHandler.getMinId( ) && tickerId < stocksHandler.getMaxId( ) ) {

            MiniStock stock = stocksHandler.getMiniStockMap( ).get( tickerId );

            if ( field == 8 ) {
                stock.setVolume( size );
            }

        }

    }
}
