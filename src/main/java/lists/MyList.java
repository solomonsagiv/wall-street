package lists;

import serverObjects.BASE_CLIENT_OBJECT;

import java.util.ArrayList;
import java.util.List;

public class MyList {

	// Variables
	BASE_CLIENT_OBJECT client;
	List list;
	String objectName;
	int optionalMaxSize = 0;

	// Constructors
	public MyList ( BASE_CLIENT_OBJECT client, String objectName ) {
		
		list = new ArrayList < Double > ( );
		this.client = client;
		this.objectName = objectName;

	}

	public MyList ( BASE_CLIENT_OBJECT client, String objectName, int optionalMaxSize ) {

		list = new ArrayList < Double > ( );
		this.client = client;
		this.objectName = objectName;
		this.optionalMaxSize = optionalMaxSize;

	}

	// Functions
	public void addVal () {

		// Remove index 0 if size > optionalMaxSize && size > 0
		if ( optionalMaxSize > 0 && list.size ( ) > optionalMaxSize ) {
			list.remove ( 0 );
		}
		list.add ( getTargeObject ( ) );
	}

	public void clear() {
		list.clear ();
	}

	public void setValues( double value ) {
		int size = getList ().size ();

		clear ();

		for ( int i = 0; i < size; i++ ) {

			getList ().add ( value );

		}

	}

	public Object getTargeObject () {

		switch ( objectName ) {
			case "index":
				return client.getIndex ( );
			case "contract":
				return client.getOptionsHandler().getMainOptions ( ).getContract ( );
			case "op":
				return client.getOptionsHandler().getMainOptions ().getOp ( );
			case "indexBid":
				return client.getIndexBid ( );
			case "indexAsk":
				return client.getIndexAsk ( );
			case "contractBid":
				return client.getOptionsHandler().getMainOptions ( ).getContractBid ( );
			case "contractAsk":
				return client.getOptionsHandler().getMainOptions ( ).getContractAsk ( );
			case "opQuarter":
				return client.getOptionsHandler().getOptionsQuarter ().getContract () - client.getIndex ();
			default:
				return null;
		}
	}

	public Object getLastItem () {
		return getList ( ).get ( getList ( ).size ( ) - 1 );
	}

	// Getters and Setters
	public List getList () {
		return list;
	}

	public void setList ( List list ) {
		this.list = list;
	}

	public ArrayList < Double > getAsDoubleList () {
		return ( ArrayList < Double > ) list;
	}

	public BASE_CLIENT_OBJECT getClient () {
		return client;
	}

	public void setClient ( BASE_CLIENT_OBJECT client ) {
		this.client = client;
	}

	public String getObjectName () {
		return objectName;
	}

	public void setObjectName ( String objectName ) {
		this.objectName = objectName;
	}
}
