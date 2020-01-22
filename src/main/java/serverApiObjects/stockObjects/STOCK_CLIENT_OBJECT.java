package serverApiObjects.stockObjects;

import org.hibernate.SessionFactory;
import serverObjects.BASE_CLIENT_OBJECT;

import java.time.LocalTime;

public abstract class STOCK_CLIENT_OBJECT extends BASE_CLIENT_OBJECT {

    double indexAsk = 0;
    double indexBid = 0;

    public STOCK_CLIENT_OBJECT() {
        super( );
        getOptionsHandler( ).getOptionsDay( ).setInterest( 1.0275 );
        setStocksNames( new String[] { "Amazon", "Apple", "Facebook", "Netflix" } );
        getOptionsHandler( ).getMainOptions( ).setGotData( true );
    }

    public SessionFactory getSessionfactory() {
        return gethBsession( ).getStockFactory( );
    }

    public void initStartOfIndexTrading() {
        setStartOfIndexTrading( LocalTime.of( 16, 30, 0 ) );
    }

    @Override
    public void initEndOfIndexTrading() {
        setEndOfIndexTrading( LocalTime.of( 23, 0, 0 ) );
    }

    @Override
    public void initEndOfFutureTrading() {
    }

    @Override
    public void initRacesMargin() {
        setRacesMargin( 0 );
    }

    public double getIndexAsk() {
        return indexAsk;
    }

    public void setIndexAsk( double index_ask ) {
        this.indexAsk = index_ask;
    }

    public double getIndexBid() {
        return indexBid;
    }

    public void setIndexBid( double index_bid ) {
        this.indexBid = index_bid;
    }

}
