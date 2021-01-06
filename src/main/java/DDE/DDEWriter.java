package DDE;

import com.pretty_tools.dde.DDEException;
import com.pretty_tools.dde.client.DDEClientConversation;
import gui.mainWindow.ConnectionPanel;
import locals.L;
import roll.RollEnum;
import serverObjects.indexObjects.Spx;
import threads.MyThread;

public class DDEWriter extends MyThread implements Runnable {

    Spx spx = Spx.getInstance( );
    String rollCell = "R9C14";
    String indexBidAskCounterCell = "R9C13";
    String opAvgDayCell = "R9C12";
    String opAvgWeekCell = "R10C12";
    String opAvgMonthCell = "R11C12";
    String opAvgE1Cell = "R12C12";
    String opAvgE2Cell = "R13C12";
    private boolean run = true;
    private DDEConnection ddeConnection = new DDEConnection( );
    private DDEClientConversation conversation;

    // Constructor
    public DDEWriter() {
        this.conversation = ddeConnection.createNewConversation( ConnectionPanel.excelLocationField.getText( ) );
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

                // Sleep
                Thread.sleep( 4000 );

                if ( spx.isStarted( ) ) {
                    // Write the data to the excel
                    writeData( );
                }

            } catch ( InterruptedException e ) {
                close( );
            }
        }
    }

    // Write the data to the excel
    private void writeData() {
        try {
            conversation.poke( rollCell, L.str( spx.getRollHandler( ).getRoll( RollEnum.E1_E2 ).getAvg( ) ) );
            conversation.poke( indexBidAskCounterCell, L.str( spx.getIndexBidAskCounter( ) ) );

            conversation.poke( opAvgDayCell, L.str( spx.getDayOpList( ).getAvg( ) ) );
            conversation.poke( opAvgWeekCell, L.str( spx.getWeekOpList( ).getAvg( ) ) );
            conversation.poke( opAvgMonthCell, L.str( spx.getMonthOpList( ).getAvg( ) ) );
            conversation.poke( opAvgE2Cell, L.str( spx.getE2OpList( ).getAvg( ) ) );
            try {
                conversation.poke( opAvgE1Cell, L.str( spx.getExps( ).getMainExp( ).getOpAvgFut( ) ) );
            } catch ( Exception e ) {
                e.printStackTrace( );
            }
        } catch ( DDEException e ) {
            System.out.println( "DDE request error on updateData()" );
            e.printStackTrace( );
        }
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