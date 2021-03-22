package IDDE;

import arik.Arik;
import com.pretty_tools.dde.DDEException;
import com.pretty_tools.dde.client.DDEClientConversation;
import exp.Exps;
import locals.L;
import serverObjects.BASE_CLIENT_OBJECT;

public class DDEWriter_Spx extends IDDEWriter {

    String indexBidAskCounterCell = "R9C13";
    Exps exps;

    public DDEWriter_Spx( BASE_CLIENT_OBJECT client ) {
        super( client );
        this.exps = client.getExps( );
    }

    @Override
    public void write( DDEClientConversation conversation ) {
        try {
            conversation.poke( indexBidAskCounterCell, L.str( client.getIndexBidAskCounter( ) ) );
        } catch ( DDEException e ) {
            System.out.println( "DDE request error on updateData()" );
            Arik.getInstance( ).sendMessage( e.getStackTrace( ).toString( ) );
            e.printStackTrace( );
        }
    }

    // Write the data to the excel
}
