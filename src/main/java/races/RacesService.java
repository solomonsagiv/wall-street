package races;

import exp.ExpStrings;
import locals.L;
import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;

public class RacesService extends MyBaseService {

    private boolean R_ONE_UP, R_ONE_DOWN, R_TWO_UP, R_TWO_DOWN = false;
    private double r_one_price, r_two_price = 0;
    private double r_one_price_0, r_two_price_0;
    private double r_one_margin, r_two_margin = 0;
    private final double RACE_MARGIN = 0.2;
    private int r_one_up_points, r_one_down_points, r_two_up_points, r_two_down_points = 0;

    final double r_one_increase_points = 1;
    final double r_two_increase_points = 1;

    public RacesService(BASE_CLIENT_OBJECT client) {
        super(client);
    }

    @Override
    public void go() {

        // Find race
        race_finder();

        // Update data
        update_data();

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public int getSleep() {
        return 200;
    }


    private void race_finder() {

        // First time update data
        first_time_update_data();

        out_of_race();
        in_race();
    }

    private void first_time_update_data() {
        if (r_one_price == 0) {
           reset_races();
        }
    }

    // IN RACE
    private void in_race() {

        // ------------ R_ONE ------------ //
        // UP
        if (R_ONE_UP) {
            // R one close
            if (r_one_margin < L.opo(RACE_MARGIN)) {
                R_ONE_UP = false;
                return;
            }

            // R one win
            if (r_two_margin > RACE_MARGIN) {
                r_one_win_up();
                reset_races();
                return;
            }
        }

        // DOWN
        if (R_ONE_DOWN) {
            // R one close
            if (r_one_margin > RACE_MARGIN) {
                R_ONE_DOWN = false;
                return;
            }

            // R one win
            if (r_two_margin < L.opo(RACE_MARGIN)) {
                r_one_win_down();
                reset_races();
                return;
            }
        }

        // ------------ R_TWO ------------ //
        // UP
        if (R_TWO_UP) {
            // R two close
            if (r_two_margin < L.opo(RACE_MARGIN)) {
                R_TWO_UP = false;
                return;
            }

            // R one win
            if (r_one_margin > RACE_MARGIN) {
                r_two_win_up();
                reset_races();
                return;
            }
        }

        // DOWN
        if (R_TWO_DOWN) {
            // R one close
            if (r_two_margin > RACE_MARGIN) {
                R_TWO_DOWN = false;
                return;
            }

            // R one win
            if (r_one_margin < L.opo(RACE_MARGIN)) {
                r_two_win_down();
                reset_races();
                return;
            }
        }


    }

    // OUT OF RACE
    private void out_of_race() {
        // If no race
        if (!is_in_race()) {
            // RUNNER ONE UP
            if (r_one_margin > RACE_MARGIN) {
                R_ONE_UP = true;
                return;
            }

            // RUNNER ONE DOWN
            if (r_one_margin < L.opo(RACE_MARGIN)) {
                R_ONE_DOWN = true;
                return;
            }

            // RUNNER TWO UP
            if (r_two_margin > RACE_MARGIN) {
                R_TWO_UP = true;
                return;
            }

            // RUNNER TWO DOWN
            if (r_two_margin < L.opo(RACE_MARGIN)) {
                R_TWO_DOWN = true;
                return;
            }
        }
    }

    // Update data
    private void update_data() {
        // Update pre data
        r_one_price_0 = r_one_price;
        r_two_price_0 = r_two_price;

        // Update current data
        r_one_price = getClient().getIndex();
        r_two_price = getClient().getExps().getExp(ExpStrings.q1).get_future();

        // Update margin
        r_one_margin = r_one_price - r_one_price_0;
        r_two_margin = r_two_price - r_two_price_0;

        // Update client data
        client.setR_one_up(r_one_up_points);
        client.setR_one_down(r_one_down_points);
        client.setR_two_up(r_two_up_points);
        client.setR_two_down(r_two_down_points);
    }

    // Reset data
    private void reset_races() {
        R_ONE_UP = false;
        R_ONE_DOWN = false;
        R_TWO_UP = false;
        R_TWO_DOWN = false;

    }

    private boolean is_in_race() {
        return R_ONE_UP || R_ONE_DOWN || R_TWO_UP || R_TWO_DOWN;
    }

    private void r_one_win_up() {
        r_one_up_points += r_one_increase_points;
    }
    private void r_one_win_down() {
        r_one_down_points += r_one_increase_points;
    }
    private void r_two_win_up() {
        r_two_up_points += r_two_increase_points;
    }
    private void r_two_win_down() {
        r_two_down_points += r_two_increase_points;
    }

}
