package fillCounter;

import exp.Exp;
import locals.L;
import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;

public class FillCounterService extends MyBaseService {

    public static void main(String[] args) {


    }

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
    private boolean fut_down = false;

    private Exp exp;

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
        // Future races
        future_race();

        // Index races 
        index_races();
    }

    private void index_races() {

        if (index_change != 0) {
            move_cumu += index_change;
        }

    }

    private void future_race() {

        if (future_change != 0) {

            // Index change
            double margin = future_change - index_change;

            move_cumu += margin * -1;
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
