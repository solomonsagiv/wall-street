package serverObjects.indexObjects;

import lists.MyList;
import options.Options;
import org.hibernate.SessionFactory;
import serverObjects.BASE_CLIENT_OBJECT;

public abstract class INDEX_CLIENT_OBJECT extends BASE_CLIENT_OBJECT {

	private Options futureOptionsFather;

	public INDEX_CLIENT_OBJECT () {
		super();
		setStocksNames( new String[]{ "Dax" , "Ndx" , "Spx" , "Russell" } );
	}

	@Override
	public SessionFactory getSessionfactory() {
		return gethBsession().getParisFactory ();
	}

	public Options getFutureOptionsFather() {
		if ( futureOptionsFather == null ) {
			futureOptionsFather = new Options( this , Options.FUTURE, getTwsData().getFutureOptionContract() );
		}
		return futureOptionsFather;
	}

	public void setFutureOptionsFather( Options futureOptionsFather ) {
		this.futureOptionsFather = futureOptionsFather;
	}


	@Override
	public void initMyLists() {

		MyList indexList = new MyList( this , "index" );
		MyList contractList = new MyList( this , "contract" );
		MyList indexBidList = new MyList( this , "indexBid" );
		MyList indexAskList = new MyList( this , "indexAsk" );
		MyList contractBidList = new MyList( this , "contractBid" );
		MyList contractAskList = new MyList( this , "contractAsk" );

		getListMap().put( "index" , indexList );
		getListMap().put( "contract" , contractList );
		getListMap().put( "indexBid" , indexBidList );
		getListMap().put( "indexAsk" , indexAskList );
		getListMap().put( "contractBid" , contractBidList );
		getListMap().put( "contractAsk" , contractAskList );
	}

}
