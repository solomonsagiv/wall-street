package serverObjects.indexObjects;

import lists.MyDoubleList;
import lists.MyIntList;
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

}
