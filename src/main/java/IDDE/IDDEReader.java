package IDDE;

import com.pretty_tools.dde.DDEException;
import com.pretty_tools.dde.client.DDEClientConversation;
import locals.L;
import serverObjects.BASE_CLIENT_OBJECT;

public abstract class IDDEReader {

    protected BASE_CLIENT_OBJECT client;

    public IDDEReader(BASE_CLIENT_OBJECT client) {
        this.client = client;
    }

    public abstract void updateData(DDEClientConversation conversation);

    public abstract void init_rates();

    public double requestDouble(String cell, DDEClientConversation conversation) {
        double d = 0;
        try {
            d = L.dbl(conversation.request(cell));
        } catch (NumberFormatException | DDEException e) {
            // TODO
            System.out.println("Cell: " + cell);
            e.printStackTrace();
        } finally {
            return d;
        }
    }
}