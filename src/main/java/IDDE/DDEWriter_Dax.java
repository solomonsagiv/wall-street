package IDDE;

import com.pretty_tools.dde.client.DDEClientConversation;
import exp.Exps;
import locals.L;
import serverObjects.BASE_CLIENT_OBJECT;

public class DDEWriter_Dax extends IDDEWriter {

    Exps exps;

    final String R_ONE_UP = "R6C2";
    final String R_ONE_DOWN = "R7C2";
    final String R_TWO_UP = "R6C3";
    final String R_TWO_DOWN = "R7C3";

    public DDEWriter_Dax(BASE_CLIENT_OBJECT client) {
        super(client);
        this.exps = client.getExps();
    }

    @Override
    public void write(DDEClientConversation conversation) {

        // Create a file output stream to save the Excel file
        try {

            conversation.poke(R_ONE_UP, L.str(client.getR_one_up()));
            conversation.poke(R_ONE_DOWN, L.str(client.getR_one_down()));
            conversation.poke(R_TWO_UP, L.str(client.getR_two_up()));
            conversation.poke(R_TWO_DOWN, L.str(client.getR_two_down()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}