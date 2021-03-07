package IDDE;

import arik.Arik;
import com.pretty_tools.dde.DDEException;
import com.pretty_tools.dde.client.DDEClientConversation;
import exp.ExpStrings;
import exp.Exps;
import locals.L;
import roll.RollEnum;
import serverObjects.BASE_CLIENT_OBJECT;

import java.net.UnknownHostException;

public class DDEWriter_B extends IDDEWriter {

    String rollCell = "R9C14";
    String indexBidAskCounterCell = "R9C13";
    String opAvgDayCell = "R9C12";
    String opAvgWeekCell = "R10C12";
    String opAvgMonthCell = "R11C12";
    String opAvgE1Cell = "R12C12";
    String opAvgE2Cell = "R13C12";
    String basketUpCell = "R9C14";
    String basketDownCell = "R9C15";
    Exps exps;

    public DDEWriter_B( BASE_CLIENT_OBJECT client ) {
        super( client );
        this.exps = client.getExps();
    }

    @Override
    public void write( DDEClientConversation conversation ) {
        try {
            conversation.poke( rollCell, L.str( client.getRollHandler( ).getRoll( RollEnum.WEEK_MONTH ).getAvg( ) ) );
            conversation.poke( indexBidAskCounterCell, L.str( client.getIndexBidAskCounter( ) ) );

            conversation.poke( opAvgDayCell, L.str( exps.getExp( ExpStrings.day ).getOpAvgFut() ) );
            conversation.poke( opAvgWeekCell, L.str( exps.getExp( ExpStrings.week ).getOpAvgFut() ) );
            conversation.poke( opAvgMonthCell, L.str( exps.getExp( ExpStrings.month ).getOpAvgFut() ) );
            conversation.poke( opAvgE1Cell, L.str( exps.getExp( ExpStrings.e1 ).getOpAvgFut() ) );
            conversation.poke( opAvgE2Cell, L.str( exps.getExp( ExpStrings.e2 ).getOpAvgFut() ) );
            conversation.poke( basketUpCell, L.str( client.getBasketFinder().getBasketUp() ) );
            conversation.poke( basketDownCell, L.str( client.getBasketFinder().getBasketDown() ) );
        } catch ( DDEException | UnknownHostException e ) {
            System.out.println( "DDE request error on updateData()" );
            Arik.getInstance().sendMessage( e.getStackTrace().toString() );
            e.printStackTrace( );
        }
    }
}