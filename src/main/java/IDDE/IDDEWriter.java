package IDDE;

import com.pretty_tools.dde.client.DDEClientConversation;
import serverObjects.BASE_CLIENT_OBJECT;

public abstract class IDDEWriter {

    protected BASE_CLIENT_OBJECT client;

    // Constructor
    public IDDEWriter(BASE_CLIENT_OBJECT client) {
        this.client = client;
    }

    public abstract void write(DDEClientConversation conversation);

}
