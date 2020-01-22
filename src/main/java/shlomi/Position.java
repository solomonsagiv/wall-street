package shlomi;

import com.ib.client.Contract;
import options.Option;
import serverObjects.BASE_CLIENT_OBJECT;

import java.time.LocalTime;

public class Position {

    // Variables
    private int id;
    private int quantity;
    private String positionType;
    private double stoplossPrice;
    private double profitPrice;
    private int stoplossId;
    private int profitId;
    private double startPrice;
    private double closePrice;
    private boolean live;
    private double pnl;
    private BASE_CLIENT_OBJECT client;
    private Contract contract;
    private Option option;
    private int position = 0;
    private LocalTime startTime;
    private LocalTime closeTime;

    // Constructor
    public Position( int id, String positionType, Contract contract, int position, BASE_CLIENT_OBJECT client ) {
        this.id = id;
        this.positionType = positionType;
        this.contract = contract;
        this.position = position;
        this.quantity = Math.abs( position );
        this.setClient( client );
    }

    public Position() {
    }

    // Function
    public void open( double startPrice ) {
        live = true;
        setStartTime( LocalTime.now( ) );
        this.startPrice = startPrice;
    }

    public void close( double closePrice ) {
        this.live = false;
        setCloseTime( LocalTime.now( ) );
        this.position = 0;
        this.closePrice = closePrice;
    }

    // ---------- Getters and Setters ---------- //
    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive( boolean live ) {
        this.live = live;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity( int quantity ) {
        this.quantity = quantity;
    }

    public double getStoplossPrice() {
        return stoplossPrice;
    }

    public void setStoplossPrice( double stoplossPrice ) {
        this.stoplossPrice = stoplossPrice;
    }

    public double getProfitPrice() {
        return profitPrice;
    }

    public void setProfitPrice( double profitPrice ) {
        this.profitPrice = profitPrice;
    }

    public int getStoplossId() {
        return stoplossId;
    }

    public void setStoplossId( int stoplossId ) {
        this.stoplossId = stoplossId;
    }

    public int getProfitId() {
        return profitId;
    }

    public void setProfitId( int profitId ) {
        this.profitId = profitId;
    }

    public double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice( double startPrice ) {
        this.startPrice = startPrice;
    }

    public double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice( double closePrice ) {
        this.closePrice = closePrice;
    }

    public double getPnl() {

        double pnl = 0;

        if ( isLive( ) ) {

            // Long
            if ( position > 0 ) {
                pnl = floor( client.getFuture( ) - startPrice );
            }

            // Short
            if ( position < 0 ) {
                pnl = floor( client.getFuture( ) - startPrice );
            }

        }

        return pnl;
    }

    public void setPnl( double pnl ) {
        this.pnl = pnl;
    }

    public String toStringVertical() {
        String string = toString( );
        String[] array = string.split( ", " );
        String returnString = "";
        for ( int i = 0; i < array.length; i++ ) {
            returnString += array[ i ] + "\n";
        }
        return returnString;
    }

    public double floor( double d ) {
        return Math.floor( d * 10 ) / 10;
    }


    public double oposite( double d ) {
        return d * -1;
    }

    @Override
    public String toString() {
        return "Position [quantity=" + quantity + ", positionType=" + positionType + ", startPrice=" + startPrice
                + ", closePrice=" + closePrice + ", live=" + live + ", pnl=" + pnl + ", position=" + position
                + ", startTime=" + startTime + ", closeTime=" + closeTime + "]";
    }

    public String toStringVerticalYogi() {
        String string = toString( );
        String[] array = string.split( ", " );
        String returnString = "";
        for ( int i = 0; i < array.length; i++ ) {
            returnString += array[ i ] + "\n";
        }
        return returnString;
    }

    /**
     * @return the option
     */
    public Option getOption() {
        return option;
    }

    /**
     * @param option the option to set
     */
    public void setOption( Option option ) {
        this.option = option;
    }

    /**
     * @return the contract
     */
    public Contract getContract() {
        return contract;
    }

    /**
     * @param contract the contract to set
     */
    public void setContract( Contract contract ) {
        this.contract = contract;
    }

    /**
     * @return the position
     */
    public int getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition( int position ) {
        this.position = position;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime( LocalTime startTime ) {
        this.startTime = startTime;
    }

    public LocalTime getCloseTime() {
        return closeTime;
    }

    public void setCloseTime( LocalTime closeTime ) {
        this.closeTime = closeTime;
    }

    public String getPositionType() {
        return positionType;
    }

    public void setPositionType( String positionType ) {
        this.positionType = positionType;
    }

    public BASE_CLIENT_OBJECT getClient() {
        return client;
    }

    public void setClient( BASE_CLIENT_OBJECT client ) {
        this.client = client;
    }

}
