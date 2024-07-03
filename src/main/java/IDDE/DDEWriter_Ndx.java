package IDDE;

import com.pretty_tools.dde.client.DDEClientConversation;
import exp.Exps;
import locals.L;
import races.Race_Logic;
import serverObjects.BASE_CLIENT_OBJECT;

public class DDEWriter_Ndx extends IDDEWriter {

    Exps exps;

    Race_Logic index_q1;
    final String R_ONE_UP = "R6C2";
    final String R_ONE_DOWN = "R7C2";
    final String R_TWO_UP = "R6C3";
    final String R_TWO_DOWN = "R7C3";

    Race_Logic q1_q2;
    final String QUA_R_ONE_UP = "R6C5";
    final String QUA_R_ONE_DOWN = "R7C5";
    final String QUA_R_TWO_UP = "R6C6";
    final String QUA_R_TWO_DOWN = "R7C6";

    public DDEWriter_Ndx(BASE_CLIENT_OBJECT client) {
        super(client);
        this.exps = client.getExps();
    }

    @Override
    public void write(DDEClientConversation conversation) {
        try {
            index_q1 = client.getRacesService().get_race_logic(Race_Logic.RACE_RUNNER_ENUM.Q1_INDEX);

            conversation.poke(R_ONE_UP, L.str(index_q1.r_one_up_points));
            conversation.poke(R_ONE_DOWN, L.str(index_q1.r_one_down_points));
            conversation.poke(R_TWO_UP, L.str(index_q1.r_two_up_points));
            conversation.poke(R_TWO_DOWN, L.str(index_q1.r_two_down_points));

            q1_q2 = client.getRacesService().get_race_logic(Race_Logic.RACE_RUNNER_ENUM.WEEK_Q1);

            conversation.poke(QUA_R_ONE_UP, L.str(q1_q2.r_one_up_points));
            conversation.poke(QUA_R_ONE_DOWN, L.str(q1_q2.r_one_down_points));
            conversation.poke(QUA_R_TWO_UP, L.str(q1_q2.r_two_up_points));
            conversation.poke(QUA_R_TWO_DOWN, L.str(q1_q2.r_two_down_points));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Write the data to the excel
}
