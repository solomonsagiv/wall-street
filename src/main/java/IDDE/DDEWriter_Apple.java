package IDDE;

import com.pretty_tools.dde.client.DDEClientConversation;
import exp.Exps;
import serverObjects.BASE_CLIENT_OBJECT;

public class DDEWriter_Apple extends IDDEWriter {

    Exps exps;

    public DDEWriter_Apple(BASE_CLIENT_OBJECT client) {
        super(client);
        this.exps = client.getExps();
    }

    @Override
    public void write(DDEClientConversation conversation) {
    }
}