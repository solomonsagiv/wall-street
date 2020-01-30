package serverObjects.indexObjects;

import lists.MyList;
import options.Options;
import org.hibernate.SessionFactory;
import serverObjects.BASE_CLIENT_OBJECT;

public abstract class INDEX_CLIENT_OBJECT extends BASE_CLIENT_OBJECT {

    private Options futureOptionsFather;

    public INDEX_CLIENT_OBJECT() {
        super( );
        setStocksNames( new String[] { "Dax", "Ndx", "Spx", "Russell" } );
    }

    @Override
    public SessionFactory getSessionfactory() {
        return gethBsession( ).getParisFactory( );
    }

    public Options getFutureOptionsFather() {
        if ( futureOptionsFather == null ) {
            futureOptionsFather = new Options( this, Options.FUTURE, getTwsData( ).getFutureOptionContract( ) );
        }
        return futureOptionsFather;
    }

    public void setFutureOptionsFather( Options futureOptionsFather ) {
        this.futureOptionsFather = futureOptionsFather;
    }


    @Override
    public void initMyLists() {

        MyList indexList = new MyList( this, MyList.INDEX );
        MyList contractList = new MyList( this, MyList.CONTRACT );
        MyList indexBidList = new MyList( this, MyList.INDEX_BID );
        MyList indexAskList = new MyList( this, MyList.INDEX_ASK );
        MyList contractBidList = new MyList( this, MyList.CONTRACT_BID );
        MyList contractAskList = new MyList( this, MyList.CONTRACT_ASK );
        MyList indexRacesList = new MyList( this, MyList.INDEX_RACES );

        getListMap( ).put( MyList.INDEX, indexList );
        getListMap( ).put( MyList.CONTRACT, contractList );
        getListMap( ).put( MyList.INDEX_BID, indexBidList );
        getListMap( ).put( MyList.INDEX_ASK, indexAskList );
        getListMap( ).put( MyList.CONTRACT_BID, contractBidList );
        getListMap( ).put( MyList.CONTRACT_ASK, contractAskList );
        getListMap( ).put( MyList.INDEX_RACES, indexRacesList );
}

}
