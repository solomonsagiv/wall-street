package grades;

import fillCounter.Race;
import locals.L;

public class OP_cumu_1 extends GRADES {

    @Override
    public double get_grade(Race race) {

        double index_move = race.index_move;
        double future_move = race.future_move;

        double grade = 0;

        // VALIDATE RACE CAN BE CLOSE
        if (index_move != 0) {
            // BOTH UP
            if (future_move > 0 && index_move > 0) {
                double move_margin = future_move - index_move;
                grade += move_margin * -1;
                return grade;
            }
            // BOTH DOWN
            if (future_move < 0 && index_move < 0) {
                double move_margin = L.abs(future_move) - L.abs(index_move);
                grade += move_margin;
                return grade;
            }
            // FUTURE UP INDEX DOWN
            if (future_move > 0 && index_move < 0) {
                double margin  = L.abs(future_move) + L.abs(index_move);
                grade -= margin;
                return grade;
            }
            // FUTURE DOWN INDEX UP
            if (future_move < 0 && index_move > 0) {
                double margin  = L.abs(future_move) + L.abs(index_move);
                grade += margin;
                return grade;
            }
            // IF INDEX MOVED
            if (index_move != 0) {
                grade += index_move;
                return grade;
            }
        }
        return grade;
    }

    /**
     * First move cumu
     *
     */

}
