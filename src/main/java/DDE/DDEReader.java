package DDE;

import com.pretty_tools.dde.DDEException;
import com.pretty_tools.dde.client.DDEClientConversation;
import locals.L;
import locals.LocalHandler;
import serverObjects.ApiEnum;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.INDEX_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;
import threads.MyThread;

public class DDEReader extends MyThread implements Runnable {

    // Variables
    int sleep = 150;
    private DDEConnection ddeConnection = new DDEConnection( );
    private String excelPath = "C://Users/user/Desktop/DDE/[SPXT.xlsx]Trading";
    DDEClientConversation conversation;

    // Constructor
    public DDEReader() {
        super( );
        conversation = ddeConnection.createNewConversation( excelPath );
    }

    @Override
    public void initRunnable() {
        setRunnable( this );
    }

    @Override
    public void run() {

        while ( isRun( ) ) {
            try {
                // Sleep
                Thread.sleep( sleep );

                System.out.println( );
                System.out.println( Spx.getInstance().getFutureBid() );
                System.out.println(  Spx.getInstance().getFutureAsk() );
                System.out.println(Spx.getInstance().getIndex() );
                System.out.println(Spx.getInstance().getIndexBid() );
                System.out.println(Spx.getInstance().getIndexAsk() );

                // DDE
                read( );

            } catch ( InterruptedException e ) {
                break;
            } catch ( DDEException e ) {
                // TODO
                e.printStackTrace( );
            } catch ( Exception e ) {
                e.printStackTrace( );
            }
        }
    }

    private void read() throws DDEException {
        for ( BASE_CLIENT_OBJECT client : LocalHandler.clients ) {
            System.out.println("For loop " );
            if ( client.getApi() == ApiEnum.DDE ) {
                updateData( ( INDEX_CLIENT_OBJECT ) client );
            }
        }
    }

    private void updateData( INDEX_CLIENT_OBJECT client ) throws DDEException {

        // Future
        client.setFuture( L.dbl( conversation.request( client.getDdeCells( ).getFutCell( ) ) ) );
        client.setFutureBid( L.dbl( conversation.request( client.getDdeCells( ).getFutBidCell( ) ) ) );
        client.setFutureAsk( L.dbl( conversation.request( client.getDdeCells( ).getFutAskCell( ) ) ) );

        // Index
        client.setIndex( L.dbl( conversation.request( client.getDdeCells( ).getIndCell( ) ) ) );
        client.setIndexBid( L.dbl( conversation.request( client.getDdeCells( ).getIndBidCell( ) ) ) );
        client.setIndexAsk( L.dbl( conversation.request( client.getDdeCells( ).getIndAskCell( ) ) ) );
    }
}