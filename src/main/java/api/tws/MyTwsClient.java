package api.tws;

import api.Manifest;
import com.ib.client.Contract;
import com.ib.client.EClientSocket;
import com.ib.client.Order;
import com.ib.client.OrderType;
import serverObjects.indexObjects.SpxCLIENTObject;
import shlomi.Position;

public class MyTwsClient {

    TWSConnection twsConnection;

    public MyTwsClient( TWSConnection twsConnection ) {
        this.twsConnection = twsConnection;
    }

    public static void main( String[] args ) throws InterruptedException {

        TWSConnection connection = new TWSConnection( );
        connection.start( );

        Thread.sleep( 2000 );

        Contract contract = SpxCLIENTObject.getInstance( ).getTwsData( ).getFutureContract( );
        System.out.println( contract );

        connection.getTwsClient( ).sellMarket( SpxCLIENTObject.getInstance( ).getTwsData( ).getFutureContract( ), 3 );
    }

    public int nextOrderId() {
        do {
            try {
                Thread.sleep( 50 );
            } catch ( InterruptedException e ) {
                break;
            }
        } while ( twsConnection.getNextOrderId( ) == 0 );

        return twsConnection.getNextOrderId( );
    }

    private EClientSocket getClient() {
        return twsConnection.getClient( );
    }


    // Place order
    private synchronized int placeOrder( Contract contract, Order order ) {
        int id = nextOrderId( );
        getClient( ).placeOrder( id, contract, order );
        return id;
    }

    // Buy market
    public int buyMarket( Contract contract, int quantity ) {
        Order order = new Order( );
        order.totalQuantity( quantity );
        order.action( "BUY" );
        order.orderType( OrderType.MKT );
        order.transmit( true );
        order.outsideRth( true );
        order.account( Manifest.TEST_ACCOUNT );

        // Place the order
        return placeOrder( contract, order );
    }

    // Sell market
    public int sellMarket( Contract contract, int quantity ) {
        Order order = new Order( );
        order.totalQuantity( quantity );
        order.action( "SELL" );
        order.orderType( OrderType.MKT );
        order.transmit( true );
        order.outsideRth( true );

        // Place the order
        return placeOrder( contract, order );
    }

    // Sell market
    public int buyLimit( Contract contract, double price, int quantity ) {
        Order order = new Order( );
        order.action( "BUY" );
        order.orderType( OrderType.LMT );
        order.totalQuantity( quantity );
        order.lmtPrice( price );

        // Place the order
        return placeOrder( contract, order );
    }

    // Sell market
    public int sellLimit( Contract contract, double price, int quantity ) {
        Order order = new Order( );
        order.action( "SELL" );
        order.orderType( OrderType.LMT );
        order.totalQuantity( quantity );
        order.lmtPrice( price );

        // Place the order
        return placeOrder( contract, order );
    }

    // Sell market
    public int stopBuy( Contract contract, double price, int quantity ) {
        Order order = new Order( );
        order.action( "BUY" );
        order.orderType( OrderType.STP );
        order.auxPrice( price );
        order.totalQuantity( quantity );

        // Place the order
        return placeOrder( contract, order );
    }

    // Sell market
    public int stopSell( Contract contract, double price, int quantity ) {
        Order order = new Order( );
        order.action( "SELL" );
        order.orderType( OrderType.STP );
        order.auxPrice( price );
        order.totalQuantity( quantity );

        // Place the order
        return placeOrder( contract, order );
    }

    // Cancel position
    public void cancellOrder( int id ) {
        getClient( ).cancelOrder( id );
    }

    // Cancel all orders
    public void cancelAllOrders() {
        getClient( ).reqGlobalCancel( );
    }

    public int sellMarket( Position position ) {
        Order order = new Order( );
        order.totalQuantity( position.getQuantity( ) );
        order.action( "SELL" );
        order.orderType( OrderType.MKT );
        order.transmit( true );
        order.outsideRth( true );

        // Place the order
        return placeOrder( position.getContract( ), order );
    }

    public int buyMarket( Position position ) {
        Order order = new Order( );
        order.totalQuantity( position.getQuantity( ) );
        order.action( "BUY" );
        order.orderType( OrderType.MKT );
        order.transmit( true );
        order.outsideRth( true );

        // Place the order
        return placeOrder( position.getContract( ), order );
    }

}
