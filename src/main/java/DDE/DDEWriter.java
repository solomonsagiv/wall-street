package DDE;

import com.pretty_tools.dde.DDEException;
import com.pretty_tools.dde.client.DDEClientConversation;
import options.OptionsEnum;
import roll.RollEnum;
import serverObjects.indexObjects.Spx;
import threads.MyThread;

public class DDEWriter extends MyThread implements Runnable {

    private String excelPath = "C://Users/user/Desktop/DDE/[SPXT.xlsx]Trading";
    private boolean run = true;
    private DDEConnection ddeConnection = new DDEConnection( );
    private DDEClientConversation conversation;

    Spx spx = Spx.getInstance( );

    String opAvgCell = "R2C10";
    String rollCell = "R2C11";
    String indexBidAskCounterCell = "R2C12";

    // Constructor
    public DDEWriter() {
        this.conversation = ddeConnection.createNewConversation( excelPath );
    }

    @Override
    public void initRunnable() {
        setName( "DDE Writer" );
        setRunnable( this );
    }

    @Override
    public void run() {

        while ( run ) {
            try {
                // Write the data to the excel
                writeData( );

                // Sleep
                Thread.sleep( 4000 );
            } catch ( InterruptedException e ) {
                close( );
            }
        }
    }

    // Write the data to the excel
    private void writeData() {
        try {
            conversation.poke( opAvgCell, str( spx.getOptionsHandler( ).getMainOptions().getOpAvgFuture( ) ) );
            conversation.poke( rollCell, str( spx.getRollHandler( ).getRoll( RollEnum.QUARTER_QUARTER_FAR ).getAvg( ) ) );
            conversation.poke( indexBidAskCounterCell, str( spx.getIndexBidAskCounter()) );
        } catch ( DDEException e ) {
            System.out.println( "DDE request error on updateData()" );
            e.printStackTrace( );
        }
    }

    public String str( Object o ) {
        return String.valueOf( o );
    }

    // Close
    public void close() {
        try {
            conversation.disconnect( );
        } catch ( DDEException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace( );
        }
        run = false;
    }
}