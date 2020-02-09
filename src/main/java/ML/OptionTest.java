package ML;

import shlomi.positions.Position;

import java.util.ArrayList;

public class OptionTest {

    // Variables
    private double strike;
    private int id;
    private String side;
    private double last;
    private double high;
    private double low;
    private double open;
    private double base;
    private double bid;
    private int bid_quantity;
    private double ask;
    private int ask_quantity;
    private double stDev;
    private double theoreticPrice;
    private boolean callOrPut;

    // Trading variables
    private Position position;

    private ArrayList< Double > bidStateList = new ArrayList<>( );
    private ArrayList< Double > askStateList = new ArrayList<>( );

    private int bidAskCounter = 0;
    private ArrayList< Integer > bidAskCounterList = new ArrayList<>( );

    // Constructor
    public OptionTest( String side, double strike, int id ) {
        this.side = side;
        this.strike = strike;
        this.id = id;

        if ( side.toLowerCase( ).contains( "c" ) ) {
            setCallOrPut( true );
        } else {
            setCallOrPut( false );
        }

        setPosition( new Position( ) );
    }


    public boolean isBetweenBid_Ask( double price ) {
        return price > getBid( ) && price < getAsk( );
    }

    public boolean isBetweenBid_Avg( double price ) {
        return price > getBid( ) && price < getBidAskAvg( );
    }

    public void increaseBidAskCounter() {
        bidAskCounter++;
    }

    public void decreaseBidAskCounter() {
        bidAskCounter--;
    }

    public double getStrike() {
        return strike;
    }

    public void setStrike( int strike ) {
        this.strike = strike;
    }

    public void setStrike( double strike ) {
        this.strike = strike;
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getSide() {
        return side;
    }

    public void setSide( String side ) {
        this.side = side;
    }

    public double getLast() {
        return last;
    }

    public void setLast( double last ) {
        this.last = last;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh( double high ) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow( double low ) {
        this.low = low;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen( double open ) {
        this.open = open;
    }

    public double getBase() {
        return base;
    }

    public void setBase( double base ) {
        this.base = base;
    }

    public double getBid() {
        return bid;
    }

    public void setBid( double newBid ) {
        this.bid = newBid;
    }

    public void setBidWithCalc( double newBid ) {
        if ( newBid > bid && bid != 0 ) {
            increaseBidAskCounter( );
        }
        this.bid = newBid;
    }

    public int getBid_quantity() {
        return bid_quantity;
    }

    public void setBid_quantity( int bid_quantity ) {
        this.bid_quantity = bid_quantity;
    }

    public double getAsk() {
        return ask;
    }

    public void setAsk( double newAsk ) {
        this.ask = newAsk;
    }

    public void setAskWithCalc( double newAsk ) {
        if ( newAsk < ask && ask != 0 ) {
            decreaseBidAskCounter( );
        }
        this.ask = newAsk;
    }

    public int getAsk_quantity() {
        return ask_quantity;
    }

    public void setAsk_quantity( int ask_quantity ) {
        this.ask_quantity = ask_quantity;
    }

    public int getBidAskCounter() {
        return bidAskCounter;
    }

    public void setBidAskCounter( int bidAskCounter ) {
        this.bidAskCounter = bidAskCounter;
    }

    public ArrayList< Double > getBidStateList() {
        return bidStateList;
    }

    public void setBidStateList( ArrayList< Double > bidStateList ) {
        this.bidStateList = bidStateList;
    }

    public ArrayList< Double > getAskStateList() {
        return askStateList;
    }

    public void setAskStateList( ArrayList< Double > askStateList ) {
        this.askStateList = askStateList;
    }

    public void addBidState( double bid ) {
        if ( bidStateList.size( ) < 2 ) {
            bidStateList.add( bid );
        } else {
            bidStateList.remove( 0 );
            bidStateList.add( bid );
        }
    }

    public double getBidState( int index ) throws NullPointerException {
        return bidStateList.get( index );
    }

    public double getAskState( int index ) throws NullPointerException {
        return askStateList.get( index );
    }

    public void addAskState( double ask ) {
        if ( askStateList.size( ) < 2 ) {
            askStateList.add( ask );
        } else {
            askStateList.remove( 0 );
            askStateList.add( ask );
        }
    }

    public double getStDev() {
        return stDev;
    }

    public void setStDev( double stDev ) {
        this.stDev = stDev;
    }

    public ArrayList< Integer > getBidAskCounterList() {
        return bidAskCounterList;
    }

    public void setBidAskCounterList( ArrayList< Integer > bidAskCounterList ) {
        this.bidAskCounterList = bidAskCounterList;
    }

    public String getName() {
        return getSide( ) + getStrike( );
    }

    public double getBidAskAvg() {
        return ( bid + ask ) / 2;
    }

    @Override
    public String toString() {
        return getSide( ) + " [last=" + last + ", base=" + base + ", bid=" + bid + ", ask=" + ask + ", bidAskCounter="
                + bidAskCounter + "]";
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition( Position position ) {
        this.position = position;
    }

    public double getTheoreticPrice() {
        return theoreticPrice;
    }

    public void setTheoreticPrice( double theoreticPrice ) {
        this.theoreticPrice = theoreticPrice;
    }

    public boolean isCallOrPut() {
        return callOrPut;
    }

    public void setCallOrPut( boolean callOrPut ) {
        this.callOrPut = callOrPut;
    }


}
