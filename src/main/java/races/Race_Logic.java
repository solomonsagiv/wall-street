package races;

import exp.ExpStrings;
import locals.L;
import serverObjects.BASE_CLIENT_OBJECT;

public class Race_Logic {

    public enum RACE_RUNNER_ENUM {
        Q1_INDEX(),
        Q1_Q2(),
        WEEK_Q1();
    }

    RACE_RUNNER_ENUM race_runners;
    private BASE_CLIENT_OBJECT client;

    private boolean R_ONE_UP, R_ONE_DOWN, R_TWO_UP, R_TWO_DOWN = false;
    private double r_one_price = 0, r_two_price = 0;
    private double r_one_price_0 = 0, r_two_price_0 = 0;
    private double r_one_margin, r_two_margin = 0;
    private double RACE_MARGIN;
    public double r_one_up_points, r_one_down_points, r_two_up_points, r_two_down_points = 0;
    private double r_one_points_from_database = 0;
    private double r_two_points_from_database = 0;

    final double r_one_increase_points = 1;
    final double r_two_increase_points = 1;

    private boolean one_two = true;

    public Race_Logic(BASE_CLIENT_OBJECT client, RACE_RUNNER_ENUM race_runners, double RACE_MARGIN) {
        this.client = client;
        this.race_runners = race_runners;
        this.RACE_MARGIN = RACE_MARGIN;
    }

    public void race_finder() {

        // First time update data
        first_time_update_data();

        if (one_two) {
            out_of_race_r1();
            in_race_r1();
        } else {
            out_of_race_r2();
            in_race_r2();
        }
    }

    private void first_time_update_data() {
        if (r_one_price == 0) {
            reset_races();
        }
    }

    // IN RACE
    private void in_race_r1() {
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

            // R one win
            if (r_one_margin > RACE_MARGIN) {
                r_two_win_up();
                reset_races();
                return;
            }

            // R two close
            if (r_two_margin < L.opo(RACE_MARGIN)) {
                R_TWO_UP = false;
                return;
            }
        }

        // DOWN
        if (R_TWO_DOWN) {

            // R one win
            if (r_one_margin < L.opo(RACE_MARGIN)) {
                r_two_win_down();
                reset_races();
                return;
            }

            // R one close
            if (r_two_margin > RACE_MARGIN) {
                R_TWO_DOWN = false;
                return;
            }
        }
    }

    private void in_race_r2() {

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

        // ------------ R_ONE ------------ //
        // UP
        if (R_ONE_UP) {

            // R one win
            if (r_two_margin > RACE_MARGIN) {
                r_one_win_up();
                reset_races();
                return;
            }

            // R one close
            if (r_one_margin < L.opo(RACE_MARGIN)) {
                R_ONE_UP = false;
                return;
            }
        }

        // DOWN
        if (R_ONE_DOWN) {

            // R one win
            if (r_two_margin < L.opo(RACE_MARGIN)) {
                r_one_win_down();
                reset_races();
                return;
            }

            // R one close
            if (r_one_margin > RACE_MARGIN) {
                R_ONE_DOWN = false;
                return;
            }
        }

    }


    // OUT OF RACE
    private void out_of_race_r1() {
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

    // OUT OF RACE
    private void out_of_race_r2() {
        // If no race
        if (!is_in_race()) {

            // RUNNER ONE UP
            if (r_two_margin > RACE_MARGIN) {
                R_TWO_UP = true;
                return;
            }

            // RUNNER ONE DOWN
            if (r_two_margin < L.opo(RACE_MARGIN)) {
                R_TWO_DOWN = true;
                return;
            }

            // RUNNER TWO UP
            if (r_one_margin > RACE_MARGIN) {
                R_ONE_UP = true;
                return;
            }

            // RUNNER TWO DOWN
            if (r_one_margin < L.opo(RACE_MARGIN)) {
                R_ONE_DOWN = true;
                return;
            }
        }
    }


    // Update data
    public void update_data() {
        // Update pre data
        r_one_price_0 = r_one_price;
        r_two_price_0 = r_two_price;

        // Update runner price
        update_runners_price();

        // Update margin
        r_one_margin = r_one_price - r_one_price_0;
        r_two_margin = r_two_price - r_two_price_0;

    }

    // Reset data
    private void reset_races() {
        R_ONE_UP = false;
        R_ONE_DOWN = false;
        R_TWO_UP = false;
        R_TWO_DOWN = false;

        one_two = !one_two;
    }

    // Update runners price
    private void update_runners_price() {
        switch (race_runners) {
            case Q1_INDEX:
                r_one_price = client.getIndex();
                r_two_price = client.getExps().getExp(ExpStrings.q1).get_future();
                return;
            case WEEK_Q1:
                r_one_price = client.getExps().getExp(ExpStrings.q1).get_future();
                r_two_price = client.getExps().getExp(ExpStrings.week).get_future();
                return;
            case Q1_Q2:
                r_one_price = client.getExps().getExp(ExpStrings.q1).get_future();
                r_two_price = client.getExps().getExp(ExpStrings.q2).get_future();
        }
    }

    public double get_sum_points() {
        return get_r_one_points() + get_r_two_points();
    }

    public double get_r_one_points() {
        return r_one_up_points - r_one_down_points;
    }

    public double get_r_two_points() {
        return r_two_up_points - r_two_down_points;
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

    public void setR_one_up_points(double r_one_up_points) {
        this.r_one_up_points = r_one_up_points;
    }

    public void setR_one_down_points(double r_one_down_points) {
        this.r_one_down_points = r_one_down_points;
    }

    public void setR_two_up_points(double r_two_up_points) {
        this.r_two_up_points = r_two_up_points;
    }

    public void setR_two_down_points(double r_two_down_points) {
        this.r_two_down_points = r_two_down_points;
    }

    public double getR_one_points_from_database() {
        return r_one_points_from_database;
    }

    public void setR_one_points_from_database(double r_one_points_from_database) {
        this.r_one_points_from_database = r_one_points_from_database;
    }

    public double getR_two_points_from_database() {
        return r_two_points_from_database;
    }

    public void setR_two_points_from_database(double r_two_points_from_database) {
        this.r_two_points_from_database = r_two_points_from_database;
    }

}
