package IDDEReaderUpdater;

import com.pretty_tools.dde.DDEException;
import com.pretty_tools.dde.client.DDEClientConversation;
import locals.L;
import serverObjects.BASE_CLIENT_OBJECT;

public abstract class IDDEReaderUpdater {

    protected BASE_CLIENT_OBJECT client;
    protected DDEClientConversation conversation;

    public IDDEReaderUpdater( BASE_CLIENT_OBJECT client ) {
        this.client = client;
    }

    public abstract void updateData( DDEClientConversation conversation );

    public double requestDouble( String cell, DDEClientConversation conversation ) {
        double d = 0;
        try {
            d = L.dbl( conversation.request( cell ) );
        } catch ( NumberFormatException | DDEException e ) {
            // TODO
        } finally {
            return d;
        }
    }

}
