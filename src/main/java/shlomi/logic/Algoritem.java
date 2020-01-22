package shlomi.logic;

import api.tws.MyTwsClient;
import arik.Arik;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.TwsData;
import shlomi.Position;

import java.util.ArrayList;

public abstract class Algoritem {

	boolean withYogi = false;
	Arik arik;
	// Data variables
	ArrayList < Position > positions;
	// Client
	private BASE_CLIENT_OBJECT client;
	private MyTwsClient twsClient;
	private TwsData twsData;
	// Algo variables
	private boolean LONG = false;
	private boolean SHORT = false;
	private Position currentPosition;

	public Algoritem( BASE_CLIENT_OBJECT client , MyTwsClient twsClient ) {
		this.setClient( client );
		this.twsClient = twsClient;
		this.twsData = client.getTwsData();
		positions = new ArrayList <>();
		arik = Arik.getInstance();
	}

	public abstract void doLogic();


	public abstract String getType();

	public void LONG() {
		try {

			currentPosition = client.getPositions().openNewPosition( getType() , client.getTwsData().getQuantity() , client.getIndex() );

			LONG = true;
			buyMarket();
			notifyMe( "Long " + getType() , currentPosition );
		} catch ( Exception e ) {
			e.printStackTrace();
			arik.sendMessage( client.getName() + " " + " Long faild " + " type: " + getType() );
		}
	}

	public void EXIT_LONG() {
		try {

			currentPosition.close( client.getIndex() );
			LONG = false;
			sellMarket();
			notifyMe( "Exit Long " + getType() , currentPosition );
		} catch ( Exception e ) {
			e.printStackTrace();
			arik.sendMessage( client.getName() + " " + " Exit long faild " + " type: " + getType() );
		}

	}

	public void SHORT() {
		try {
			currentPosition = client.getPositions().openNewPosition( getType() , ( int ) opo( client.getTwsData().getQuantity() ) , client.getIndex() );

			SHORT = true;
			sellMarket();
			notifyMe( "Short " + getType() , currentPosition );
		} catch ( Exception e ) {
			e.printStackTrace();
			arik.sendMessage( client.getName() + " " + " Short faild " + " type: " + getType() );
		}

	}

	public void EXIT_SHORT() {
		try {
			currentPosition.close( client.getIndex() );

			SHORT = false;
			buyMarket();
			notifyMe( "Exit Short " + getType() , currentPosition );
		} catch ( Exception e ) {
			e.printStackTrace();
			arik.sendMessage( client.getName() + " " + " Exit short faild " + " type: " + getType() );
		}

	}

	// Notify messgaes
	private void notifyMe( String messageTitle , Position position ) {

		String text = client.getName() + " " + messageTitle + " \n" + position.toStringVertical();

		if ( withYogi ) {

			arik.sendMessage( Arik.yosiID , text , null );
		}

		arik.sendMessage( Arik.sagivID , text , null );

	}

	public Position openPosition( int pos ) {
		Position position = new Position( getClient().getPositions().getNextId() , getType() , getClient().getTwsData().getFutureContract() , pos , client );
		position.open( getClient().getIndex() );
		return position;
	}

	// Trading functions
	private void buyMarket() throws Exception {
		twsClient.buyMarket( twsData.getFutureContract() , twsData.getQuantity() );
	}

	private void sellMarket() throws Exception {
		twsClient.sellMarket( twsData.getFutureContract() , twsData.getQuantity() );
	}

	public double opo( double d ) {
		return d * -1;
	}

	// ---------- Getters and setters ---------- //
	public BASE_CLIENT_OBJECT getClient() {
		return client;
	}

	public void setClient( BASE_CLIENT_OBJECT client ) {
		this.client = client;
	}

	public boolean isLONG() {
		return LONG;
	}

	public void setLONG( boolean LONG ) {
		this.LONG = LONG;
	}

	public boolean isInPos() {
		return LONG || SHORT;
	}

	public boolean isSHORT() {
		return SHORT;
	}

	public void setSHORT( boolean SHORT ) {
		this.SHORT = SHORT;
	}

	public MyTwsClient getTwsClient() {
		return twsClient;
	}

	public void setTwsClient( MyTwsClient twsClient ) {
		this.twsClient = twsClient;
	}

	public TwsData getTwsData() {
		return twsData;
	}

	public void setTwsData( TwsData twsData ) {
		this.twsData = twsData;
	}

}
