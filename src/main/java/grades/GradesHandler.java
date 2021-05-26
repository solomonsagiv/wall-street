package grades;

public class GradesHandler {

    Move_cumu_1 move_cumu_1;
    OP_cumu_1 op_cumu_1;

    public GradesHandler() {
        move_cumu_1 = new Move_cumu_1();
        op_cumu_1 = new OP_cumu_1();
    }

    public Move_cumu_1 getMove_cumu_1() {
        return move_cumu_1;
    }

    public OP_cumu_1 getOp_cumu_1() {
        return op_cumu_1;
    }
}
