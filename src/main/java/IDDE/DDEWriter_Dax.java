package IDDE;

import com.pretty_tools.dde.client.DDEClientConversation;
import exp.Exps;
import locals.L;
import races.Race_Logic;
import serverObjects.BASE_CLIENT_OBJECT;

public class DDEWriter_Dax extends IDDEWriter {

    Exps exps;

    final String R_ONE_UP = "R6C2";
    final String R_ONE_DOWN = "R7C2";
    final String R_TWO_UP = "R6C3";
    final String R_TWO_DOWN = "R7C3";

    Race_Logic index_q1;

    public DDEWriter_Dax(BASE_CLIENT_OBJECT client) {
        super(client);
        this.exps = client.getExps();
        index_q1 = client.getRacesService().get_race_logic(Race_Logic.RACE_RUNNER_ENUM.Q1_INDEX);
    }

    @Override
    public void write(DDEClientConversation conversation) {
        // Create a file output stream to save the Excel file
        try {
            conversation.poke(R_ONE_UP, L.str(index_q1.r_one_up_points));
            conversation.poke(R_ONE_DOWN, L.str(index_q1.r_one_down_points));
            conversation.poke(R_TWO_UP, L.str(index_q1.r_two_up_points));
            conversation.poke(R_TWO_DOWN, L.str(index_q1.r_two_down_points));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}