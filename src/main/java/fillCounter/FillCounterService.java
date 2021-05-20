package fillCounter;

import locals.L;
import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;

import java.util.ArrayList;

public class FillCounterService extends MyBaseService {

    // Variables
    private int sleep = 100;
    private String name = "Fill counter";
    private double index = 0;
    private double future = 0;
    private double index_0 = 0;
    private double future_0 = 0;
    private double index_change = 0;
    private double future_change = 0;
    private double bid_ask_margin = 0;
    private double move_cumu = 0;
    private double optimi_pesimi_cumu = 0;
    private double optimi_pesimi = 0;
    private boolean fut_up = false;

    private ArrayList<Race> races = new ArrayList<>();
    private ArrayList<ArrayList<MySerie>> arrays;

    private double future_in_race_move = 0;
    private double index_in_race_move = 0;

    private boolean fut_down = false;

    private Race race;

    // Constructor
    public FillCounterService(BASE_CLIENT_OBJECT client, ArrayList<ArrayList<MySerie>> arrays) {
        super(client);
        this.arrays = arrays;
    }

    @Override
    public void go() {

        // Update new data
        update_new_data();

        // Logic
        logic();

        // Update pre
        update_pre_data();

    }

    private void logic() {
        // Step 1
        step_one();

        // Step 2
        step_two();
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
        }
        // Both down
        if (future_change < 0 && index_change < 0) {
            race = new Race(optimi_pesimi, Race.BOTH_DOWN, future_change, index_change);
        }
        // Future up index down
        if (future_change > 0 && index_change < 0) {
            race = new Race(optimi_pesimi, Race.FUT_UP_IND_DOWN, future_change, index_change);
        }
        // Future down index up
        if (future_change < 0 && index_change > 0) {
            race = new Race(optimi_pesimi, Race.FUT_DOWN_IND_UP, future_change, index_change);
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
        }
        // Down race
        if (index_change < 0) {
            race = new Race(optimi_pesimi, Race.IND_DOWN, future_change, index_change);
        }
    }

    private void future_open_race() {
        // Up race
        if (future_change > 0) {
            race = new Race(optimi_pesimi, Race.FUT_UP, future_change, index_change);
        }
        // Down race
        if (future_change < 0) {
            race = new Race(optimi_pesimi, Race.FUT_DOWN, future_change, index_change);
        }
    }

    private void index_races() {
        if (index_change != 0) {
            move_cumu += index_change;
        }
    }

    private void future_race() {
        if (future_change > 0) {
            fut_up = true;
            future_in_race_move += future_change;
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

    private void update_new_data() {
        index = getClient().getIndex();
        future = exp.get_future();
        bid_ask_margin = getClient().getIndexAsk() - getClient().getIndexBid();

        index_change = index - index_0;
        future_change = future - future_0;

        optimi_pesimi = future - index;
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
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getSleep() {
        return sleep;
    }

    public void setSleep(int sleep) {
        this.sleep = sleep;
    }
}
