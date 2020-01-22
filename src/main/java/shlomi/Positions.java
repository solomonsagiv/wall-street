package shlomi;

import serverObjects.BASE_CLIENT_OBJECT;

import java.util.HashMap;
import java.util.Map.Entry;

public class Positions {

	// Variables
	BASE_CLIENT_OBJECT client;
	private HashMap < Integer, Position > positions;
	private int id = 0;

	private double twsTotalPnl = 0;
	private double twsPositionPnl = 0;
	private int positionStatus = 0;

	// Constructor
	public Positions( BASE_CLIENT_OBJECT client ) {
		this.client = client;
		setPositions( new HashMap <>() );
	}

	// Functions
	public int getNextId() {

		int id = 0;

		for ( Entry < Integer, Position > entry : positions.entrySet() ) {
			id = entry.getKey();
		}

		return id++;
	}

	public void addPosition( Position position ) {
		positions.put( getNextId() , position );
	}

	public Position openNewPosition( String positionType , int pos , double startPrice ) {

		Position position = new Position( getNextId() , positionType , client.getTwsData().getFutureContract() , pos , client );
		position.open( startPrice );

		positions.put( position.getId() , position );

		return position;
	}


	public double getTotalPnl() {

		double pnl = 0;

		for ( Entry < Integer, Position > entry : positions.entrySet() ) {

			Position position = entry.getValue();

			pnl += position.getPnl();

		}

		return pnl;

	}

	public String getAllPositionText() {

		String text = "";

		for ( Entry < Integer, Position > entry : positions.entrySet() ) {

			Position position = entry.getValue();

			text += position.toString() + " \n";

		}

		return text;
	}


	public double getTotalAvgPnl() {

		double pnl = getTotalPnl();
		int posQuantity = positions.size();

		return floor( pnl / posQuantity );

	}


	public Position getPosition( int id ) {
		return positions.get( id );
	}

	// Getters and setters
	public HashMap < Integer, Position > getPositions() {
		return positions;
	}

	public void setPositions( HashMap < Integer, Position > positions ) {
		this.positions = positions;
	}


	public double getTwsTotalPnl() {
		return twsTotalPnl;
	}


	public void setTwsTotalPnl( double twsTotalPnl ) {
		this.twsTotalPnl = twsTotalPnl;
	}


	public double getTwsPositionPnl() {
		return twsPositionPnl;
	}


	public void setTwsPositionPnl( double twsPositionPnl ) {
		this.twsPositionPnl = twsPositionPnl;
	}


	public int getPositionStatus() {
		return positionStatus;
	}


	public void setPositionStatus( int positionStatus ) {
		this.positionStatus = positionStatus;
	}


	public double floor( double d ) {
		return Math.floor( d * 100 ) / 100;
	}

}
