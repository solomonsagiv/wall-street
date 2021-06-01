package grades;

import fillCounter.Race;

public class OP_cumu_1 extends GRADES {

    @Override
    public double get_grade(Race race) {

        double index_move = race.index_move;
        double grade = 0;

        // INDEX_UP
        if (index_move > 0) {
            grade = index_move * (1 - calc_future_margin_between_bid_ask_present(race));
            return grade;
        }

        // INDEX DOWN
        if (index_move < 0) {
            grade = index_move * calc_future_margin_between_bid_ask_present(race);
            return grade;
        }

        return grade;
    }

    private double calc_future_margin_between_bid_ask_present(Race race) {
        double index_bid = race.index_bid;
        double index_ask = race.index_ask;
        double bid_ask_margin = index_ask - index_bid;
        double future = race.future;
        double future_from_index_bid_margin = future - index_bid;

        double present = (future_from_index_bid_margin / bid_ask_margin);
        return present;
    }

    /**
     * First move cumu
     *
     */

}
