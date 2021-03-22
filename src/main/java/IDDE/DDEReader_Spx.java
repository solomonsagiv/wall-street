package IDDE;

import com.pretty_tools.dde.client.DDEClientConversation;
import exp.ExpStrings;
import serverObjects.BASE_CLIENT_OBJECT;

public class DDEReader_Spx extends IDDEReader {

    String indCell = "R2C3";
    String indBidCell = "R2C2";
    String indAskCell = "R2C4";
    String openCell = "R13C4";
    String highCell = "R13C1";
    String lowCell = "R13C2";
    String baseCell = "R11C5";
    String futDayCell = "R9C10";
    String futWeekCell = "R10C10";
    String futMonthCell = "R11C10";
    String e1Cell = "R12C10";
    String e2Cell = "R13C10";

    // Constructor
    public DDEReader_Spx( BASE_CLIENT_OBJECT client ) {
        super( client );
    }

    @Override
    public void updateData( DDEClientConversation conversation ) {

        // Index
        client.setIndex( requestDouble( indCell, conversation ) );
        client.setIndexBid( requestDouble( indBidCell, conversation ) );
        client.setIndexAsk( requestDouble( indAskCell, conversation ) );

        // Ticker
        client.setOpen( requestDouble( openCell, conversation ) );
        client.setHigh( requestDouble( highCell, conversation ) );
        client.setLow( requestDouble( lowCell, conversation ) );
        client.setBase( requestDouble( baseCell, conversation ) );

        // Exps
        client.getExps( ).getExp( ExpStrings.day ).setFuture( requestDouble( futDayCell, conversation ) );
        client.getExps( ).getExp( ExpStrings.week ).setFuture( requestDouble( futWeekCell, conversation ) );
        client.getExps( ).getExp( ExpStrings.month ).setFuture( requestDouble( futMonthCell, conversation ) );
        client.getExps( ).getExp( ExpStrings.q1 ).setFuture( requestDouble( e1Cell, conversation ) );
        client.getExps( ).getExp( ExpStrings.q2 ).setFuture( requestDouble( e2Cell, conversation ) );

    }
}
