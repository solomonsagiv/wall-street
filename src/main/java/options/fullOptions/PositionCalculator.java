package options.fullOptions;

import locals.L;
import options.Option;
import serverObjects.BASE_CLIENT_OBJECT;

import java.util.ArrayList;

public class PositionCalculator {

    public static final int CASH_FLOW = 0;
    public static final int PNL = 1;
    public static final int DELTA = 2;
    public static final int VEGA = 3;

    // Variables
    BASE_CLIENT_OBJECT client;
    ArrayList< OptionPosition > positions;

    int validID = 0;

    // Constructor
    public PositionCalculator( BASE_CLIENT_OBJECT client ) {
        this.client = client;
        this.positions = new ArrayList<>( );
    }

    // Methods
    public void addPosition( Option option, int pos, double price ) {

        // Create position
        OptionPosition position = new OptionPosition( validID, option, pos, price );
        positions.add( position );

        // Update positions window
        PositionsWindow positionsWindow = new PositionsWindow( client );
        positionsWindow.appnedPanel( position );

        // ID
        handleID( );
    }

    public double getTotalData( int data ) {
        double d = 0;
        for ( OptionPosition position : positions ) {
            d += getData( position, data );
        }
        return d;
    }

    private double getData( OptionPosition position, int data ) {
        switch ( data ) {
            case CASH_FLOW:
                return position.getCashFlow( );
            case PNL:
                return position.getPnl( );
            case DELTA:
                return position.getDelta( );
            case VEGA:
                return position.getVega( );
            default:
                return 0;
        }
    }

    private synchronized void handleID() {
        validID++;
    }

    public int getValidID() {
        return validID;
    }

    // ---------- Option position ---------- //
    public static class OptionPosition {

        public static final int BUY = 1;
        public static final int SELL = -1;

        // Variables
        private int id;
        private Option option;
        private int quantity;
        private int pos;
        private double cashFlow;
        private double price;

        // Constructor
        public OptionPosition( int id, Option option, int pos, double price ) {
            this.id = id;
            this.option = option;
            this.quantity = Math.abs( pos );
            this.pos = pos;
            this.price = price;
            this.cashFlow = price * L.opo( pos ) * 100;
        }

        public double getDelta() {
            return option.getDelta( ) * pos;
        }

        public double getVega() {
            return option.getVega( ) * pos;
        }

        // Getters and Setters
        public Option getOption() {
            return option;
        }

        public void setOption( Option option ) {
            this.option = option;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity( int quantity ) {
            this.quantity = quantity;
        }

        public int getPos() {
            return pos;
        }

        public void setPos( int pos ) {
            this.pos = pos;
        }

        public double getPnl() {
            return getCashFlow( ) + ( option.getTheoreticPrice( ) * pos * 100 );
        }

        public int getId() {
            return id;
        }

        public void setId( int id ) {
            this.id = id;
        }

        public double getCashFlow() {
            return cashFlow;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice( double price ) {
            this.price = price;
        }

        @Override
        public String toString() {
            return "OptionPosition{" +
                    "option=" + option +
                    ", quantity=" + quantity +
                    ", pos=" + pos +
                    '}';
        }
    }

}


