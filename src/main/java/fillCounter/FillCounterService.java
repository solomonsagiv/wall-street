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
        // Optimi
        future_got_up();
    }

    private void future_got_up() {
        if (future_change > 0 && future > index) {

        }
    }

    private void update_pre_data() {
        index_0 = index;
        future_0 = future;
    }

    private void update_new_data() {
        index = getClient().getIndex();
        future = exp.get_future();

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
