package DDE;

import arik.Arik;
import com.pretty_tools.dde.DDEException;
import com.pretty_tools.dde.client.DDEClientConversation;
import exp.ExpStrings;
import exp.Exps;
import gui.mainWindow.ConnectionPanel;
import locals.L;
import roll.RollEnum;
import serverObjects.BASE_CLIENT_OBJECT;
import threads.MyThread;

import java.net.UnknownHostException;

public class DDEWriter extends MyThread implements Runnable {

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
    Exps exps;
    BASE_CLIENT_OBJECT client;

    // Constructor
    public DDEWriter( BASE_CLIENT_OBJECT client ) {
        this.conversation = ddeConnection.createNewConversation( ConnectionPanel.excelLocationField.getText( ) );
        this.client = client;
        this.exps = client.getExps( );

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

                if ( client.isStarted( ) ) {
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
            conversation.poke( rollCell, L.str( client.getRollHandler( ).getRoll( RollEnum.E1_E2 ).getAvg( ) ) );
            conversation.poke( indexBidAskCounterCell, L.str( client.getIndexBidAskCounter( ) ) );

            conversation.poke( opAvgDayCell, L.str( exps.getExp( ExpStrings.day ).getOpAvgFut() ) );
            conversation.poke( opAvgWeekCell, L.str( exps.getExp( ExpStrings.week ).getOpAvgFut() ) );
            conversation.poke( opAvgMonthCell, L.str( exps.getExp( ExpStrings.month ).getOpAvgFut() ) );
            conversation.poke( opAvgE1Cell, L.str( exps.getExp( ExpStrings.e1 ).getOpAvgFut() ) );
            conversation.poke( opAvgE2Cell, L.str( exps.getExp( ExpStrings.e2 ).getOpAvgFut() ) );
        } catch ( DDEException | UnknownHostException e ) {
            System.out.println( "DDE request error on updateData()" );
            Arik.getInstance().sendMessage( e.getStackTrace().toString() );
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