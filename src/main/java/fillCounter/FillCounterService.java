package fillCounter;

import locals.L;

import java.util.ArrayList;

public class FillCounterService {

    // Variables
    private int sleep = 100;
    private String name = "Fill counter";
    private double index = 0;
    private double index_bid = 0;
    private double index_ask = 0;
    private double future = 0;
    private double index_0 = 0;
    private double future_0 = 0;
    private double index_change = 0;
    private double future_change = 0;
    private double bid_ask_margin = 0;
    private double move_cumu = 0;
    private double optimi_pesimi_cumu = 0;
    private double optimi_pesimi = 0;

    private ArrayList<Race> races = new ArrayList<>();
    private ArrayList<ArrayList<MySerie>> arrays;

    private int ind_serie_index;
    int fut_serie_index;

    private Race race;

    // Constructor
    public FillCounterService(ArrayList<ArrayList<MySerie>> arrays) {
        this.arrays = arrays;
    }

    public void run(double index, double index_bid, double index_ask, double future) {

        for (int i = 0; i < arrays.get(0).size(); i++) {
            // Update new data
            update_new_data(index, index_bid, index_ask, future);

            // Logic
            logic();

            // Update pre
            update_pre_data();
        }



    }

    private void logic() {
        if (index_0 != 0 && future_0 != 0) {
            // Step 1
            step_one();

            // Step 2
            step_two();
        }
    }

    private void step_two() {
        // Optimi
        op_fut_up();
    }

    private void op_fut_up() {

    }

    private void step_one() {
        // Open race
        open_race();

        // Close race
        close_race();
    }

    private void close_race() {
        // Close race if in race
        if (race != null) {
            // If future race
            if (race.type == Race.FUT_UP || race.type == Race.FUT_DOWN) {
                // Future move in race
                future_move_in_race();

                // index move in race
                index_move_in_race();
            } else {
                // Close race and update grade
                close_race_and_append_data_and_grades(race);
            }
        }
    }

    private void open_race() {
        // If no race open
        if (race == null) {
            // Who moved first
            find_who_open_race_first();
        }
    }

    private void find_who_open_race_first() {
        // Both
        if (future_change != 0 && index_change != 0) {
            both_open_race();
        }
        // Future
        if (future_change != 0) {
            future_open_race();
        }
        // Index
        if (index_change != 0) {
            index_open_race();
        }
    }

    private void both_open_race() {
        // Both up
        if (future_change > 0 && index_change > 0) {
            race = new Race(optimi_pesimi, Race.BOTH_UP, future_change, index_change);
            return;
        }
        // Both down
        if (future_change < 0 && index_change < 0) {
            race = new Race(optimi_pesimi, Race.BOTH_DOWN, future_change, index_change);
            return;
        }
        // Future up index down
        if (future_change > 0 && index_change < 0) {
            race = new Race(optimi_pesimi, Race.FUT_UP_IND_DOWN, future_change, index_change);
            return;
        }
        // Future down index up
        if (future_change < 0 && index_change > 0) {
            race = new Race(optimi_pesimi, Race.FUT_DOWN_IND_UP, future_change, index_change);
            return;
        }
    }

    private void close_race_and_append_data_and_grades(Race race) {
        // Validate can close race
        if (race.future_move != 0 && race.index_move != 0) {

            // Add race to list
            races.add(race);

            // Append grade to move cumu
            move_cumu += get_move_grade(race);

            // Delete race
            this.race = null;
            return;
        }
    }

    private void index_move_in_race() {
        // Last change Up
        if (index_change != 0) {
            // In fut up race
            if (race.type == Race.FUT_UP || race.type == Race.FUT_DOWN) {
                race.index_move = index_change;
            }
        }
    }

    private void future_move_in_race() {
        // Last change Up
        if (future_change > 0) {
            // Race is future up
            if (race.type == Race.FUT_UP) {
                race.future_move += future_change;
            }
            // Race is future down
            if (race.type == Race.FUT_DOWN) {
                // Close race if change bigger then open race move
                if (L.abs(future_change) > L.abs(race.future_move)) {
                    race = null;
                } else {
                    race.future_move += future_change;
                }
            }
        }

        // Last change Down
        if (future_change < 0) {
            // Race is up
            if (race.type == Race.FUT_UP) {
                // Close race if change bigger then open race move
                if (L.abs(future_change) > L.abs(race.future_move)) {
                    race = null;
                    return;
                } else {
                    race.future_move -= future_change;
                }
            }
            // Race is down
            if (race.type == Race.FUT_DOWN) {
                race.future_move -= future_change;
            }
        }
    }

    private void index_open_race() {
        // Up race
        if (index_change > 0) {
            race = new Race(optimi_pesimi, Race.IND_UP, future_change, index_change);
            return;
        }
        // Down race
        if (index_change < 0) {
            race = new Race(optimi_pesimi, Race.IND_DOWN, future_change, index_change);
            return;
        }
    }

    private void future_open_race() {
        // Up race
        if (future_change > 0) {
            race = new Race(optimi_pesimi, Race.FUT_UP, future_change, index_change);
            return;
        }
        // Down race
        if (future_change < 0) {
            race = new Race(optimi_pesimi, Race.FUT_DOWN, future_change, index_change);
            return;
        }
    }

    private void update_pre_data() {
        index_0 = index;
        future_0 = future;

        index_change = 0;
        future_change = 0;
    }

    private double get_move_grade(Race race) {

        double index_move = race.index_move;
        double future_move = race.future_move;

        double grade = 0;

        // Validate race can be close
        if (index_move != 0) {
            // Both up
            if (future_move > 0 && index_move > 0) {
                double move_margin = future_move - index_move;
                grade += move_margin * -1;
            }
            // Both down
            if (future_move < 0 && index_move < 0) {
                double move_margin = L.abs(future_move) - L.abs(index_move);
                grade += move_margin;
            }
            // Future up Index down
            if (future_move > 0 && index_move < 0) {
                grade += index_move;
            }
            // Future down Index up
            if (future_move < 0 && index_move > 0) {
                grade += index_move;
            }
        }
        return grade;
    }

    private void update_new_data(double index, double index_bid, double index_ask, double future) {
        this.index = index;
        this.future = future;
        this.index_bid = index_bid;
        this.index_ask = index_ask;

        //future = exp.get_future();
        bid_ask_margin = index_ask - index_bid;

        index_change = this.index - index_0;
        future_change = this.future - future_0;

        optimi_pesimi = this.future - this.index;
    }

    private class Race {

        public static final int FUT_UP = 1;
        public static final int FUT_DOWN = 2;
        public static final int IND_UP = 3;
        public static final int IND_DOWN = 4;
        public static final int BOTH_UP = 5;
        public static final int BOTH_DOWN = 6;
        public static final int FUT_UP_IND_DOWN = 7;
        public static final int FUT_DOWN_IND_UP = 8;

        double optimi_pesimi;
        int type;
        double future_move;
        double index_move;

        public Race(double optimi_pesimi, int type, double future_move, double index_move) {
            this.optimi_pesimi = optimi_pesimi;
            this.type = type;
            this.future_move = future_move;
            this.index_move = index_move;
            System.out.println(toString());
        }

        @Override
        public String toString() {
            return "Race{" +
                    "optimi_pesimi=" + optimi_pesimi +
                    ", type=" + type +
                    ", future_move=" + future_move +
                    ", index_move=" + index_move +
                    '}';
        }
    }

    public double getMove_cumu() {
        return move_cumu;
    }

    public void setSleep(int sleep) {
        this.sleep = sleep;
    }


}
