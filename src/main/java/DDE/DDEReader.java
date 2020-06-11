package DDE;

import com.pretty_tools.dde.DDEException;
import com.pretty_tools.dde.client.DDEClientConversation;
import exp.Exp;
import exp.Exps;
import locals.L;
import locals.LocalHandler;
import options.Options;
import serverObjects.ApiEnum;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.INDEX_CLIENT_OBJECT;
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
            if ( client.getApi( ) == ApiEnum.DDE ) {
                updateData( ( INDEX_CLIENT_OBJECT ) client );
            }
        }
    }

    private void updateData( INDEX_CLIENT_OBJECT client ) throws DDEException {

        // Options
        for ( Exp exp : client.getExps( ).getExpList( ) ) {

            Options options = exp.getOptions();

            if ( options.getOptionsDDeCells( ) != null ) {
                exp.setFuture( L.dbl( conversation.request( options.getOptionsDDeCells( ).getFut( ) ) ) );
            }
        }

        // Index
        client.setIndex( L.dbl( conversation.request( client.getDdeCells( ).getCell( DDECellsEnum.IND ) ) ) );
        client.setIndexBid( L.dbl( conversation.request( client.getDdeCells( ).getCell( DDECellsEnum.IND_BID ) ) ) );
        client.setIndexAsk( L.dbl( conversation.request( client.getDdeCells( ).getCell( DDECellsEnum.IND_ASK ) ) ) );

        // Ticker
        client.setOpen( L.dbl( conversation.request( client.getDdeCells( ).getCell( DDECellsEnum.OPEN ) ) ) );
        client.setHigh( L.dbl( conversation.request( client.getDdeCells( ).getCell( DDECellsEnum.HIGH ) ) ) );
        client.setLow( L.dbl( conversation.request( client.getDdeCells( ).getCell( DDECellsEnum.LOW ) ) ) );
        client.setBase( L.dbl( conversation.request( client.getDdeCells( ).getCell( DDECellsEnum.BASE ) ) ) );

    }
}