package fillCounter;

import exp.Exp;
import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;

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

    private boolean fut_up = false;

    private double future_in_race_move = 0;
    private double index_in_race_move = 0;

    private boolean fut_down = false;

    private Exp exp;

    private Race race;

    // Constructor
    public FillCounterService(BASE_CLIENT_OBJECT client, Exp exp) {
        super(client);
        this.exp = exp;
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

        // Future open race
        future_open_race();

        // Index open race

        // Future close race

        // Index close race


    }

    private void future_open_race() {

        // Up race
        if (future_change > 0) {
            race = new Race(future - index_change, Race.FUT_UP, future_change);
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

    private void update_new_data() {


        index = getClient().getIndex();
        future = exp.get_future();
        bid_ask_margin = getClient().getIndexAsk() - getClient().getIndexBid();

        index_change = index - index_0;
        future_change = future - future_0;

    }

    private class Race {

        public static final int FUT_UP = 1;
        public static final int FUT_DOWN = 2;
        public static final int IND_UP = 3;
        public static final int IND_DOWN = 4;

        double optimi_pesimi;
        int type;
        double move;

        public Race(double optimi_pesimi, int type, double move) {
            this.optimi_pesimi = optimi_pesimi;
            this.type = type;
            this.move = move;
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
