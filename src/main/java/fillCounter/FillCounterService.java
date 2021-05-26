package fillCounter;

import grades.GRADES;
import locals.L;
import java.time.LocalDateTime;
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
    private Race status_race;
    LocalDateTime dateTime;
    GRADES grades;

    // Constructor
    public FillCounterService(GRADES grades) {
        status_race = new Race();
        this.grades = grades;
    }

    public void run(LocalDateTime dateTime, double index, double index_bid, double index_ask, double future) {
        // Update new data
        update_new_data(dateTime, index, index_bid, index_ask, future);

        // Logic
        logic();

        // Check if there is done race
        close_race_and_append_data_and_grades(status_race);

        // Update pre
        update_pre_data();
    }

    private void logic() {
        // IF PRE GOT DATA
        if (index_0 != 0 && future_0 != 0) {
            // ------------ NO  RACE ----------- //
            if (!status_race.open) {
                // Look for change
                // Both
                if (future_change != 0 && index_change != 0) {
                    // BOTH UP
                    if (future_change > 0 && index_change > 0) {
                        status_race.open(optimi_pesimi, Race.BOTH_UP, future_change, index_change);
                        return;
                    }
                    // BOTH DOWN
                    if (future_change < 0 && index_change < 0) {
                        status_race.open(optimi_pesimi, Race.BOTH_DOWN, future_change, index_change);
                        return;
                    }
                    // FUTURE UP INDEX DOWN
                    if (future_change > 0 && index_change < 0) {
                        status_race.open(optimi_pesimi, Race.FUT_UP_IND_DOWN, future_change, index_change);
                        return;
                    }
                    // FUTURE DOWN INDEX UP
                    if (future_change < 0 && index_change > 0) {
                        status_race.open(optimi_pesimi, Race.FUT_DOWN_IND_UP, future_change, index_change);
                        return;
                    }
                    // FUTURE CHANGE
                } else if (future_change != 0) {
                    // FUTURE UP
                    if (future_change > 0) {
                        status_race.open(optimi_pesimi, Race.FUT_UP, future_change, index_change);
                        return;
                    }
                    // FUTURE DOWN
                    if (future_change < 0) {
                        status_race.open(optimi_pesimi, Race.FUT_DOWN, future_change, index_change);
                        return;
                    }
                    // INDEX CHANGE
                } else if (index_change != 0) {
                    // INDEX UP
                    if (index_change > 0) {
                        status_race.open(optimi_pesimi, Race.IND_UP, future_change, index_change);
                        return;
                    }
                    // INDEX DOWN
                    if (index_change < 0) {
                        status_race.open(optimi_pesimi, Race.IND_DOWN, future_change, index_change);
                        return;
                    }
                }
            }

            // ------------ RACE ------------ //
            if (status_race.open) {

                // IF FUTURE UP RACE
                if (status_race.type == Race.FUT_UP) {
                    // BOTH CHANGED
                    if (future_change != 0 && index_change != 0) {
                        status_race.future_move += future_change;
                        status_race.index_move += index_change;
                        // todo Set race type again
                        return;
                    }

                    // FUTURE UP
                    if (future_change > 0) {
                        status_race.future_move += future_change;
                        return;
                    }

                    // FUTURE DOWN
                    if (future_change < 0) {
                        // NEW CHANGE BIGGER THAN RACE CHANGE TO THE OPPOSITE SIDE
                        if (L.abs(future_change) > status_race.future_move) {
                            status_race.type = Race.FUT_DOWN;
                        }
                        status_race.future_move += future_change;
                        return;
                    }

                    // INDEX UP
                    if (index_change != 0) {
                        status_race.index_move += index_change;
                        return;
                    }
                }

                // IF FUTURE DOWN RACE
                if (status_race.type == Race.FUT_DOWN) {
                    // BOTH CHANGED
                    if (future_change != 0 && index_change != 0) {
                        status_race.future_move += future_change;
                        status_race.index_move += index_change;
                        //todo Set race type again
                        return;
                    }

                    // FUTURE UP
                    if (future_change > 0) {
                        // NEW CHANGE BIGGER THAN RACE CHANGE TO THE OPPOSITE SIDE
                        if (future_change > L.abs(status_race.future_move)) {
                            status_race.type = Race.FUT_UP;
                        }
                        status_race.future_move += future_change;
                        return;
                    }

                    // FUTURE DOWN
                    if (future_change < 0) {
                        status_race.future_move += future_change;
                        return;
                    }

                    // INDEX CHANGE
                    if (index_change != 0) {
                        status_race.index_move += index_change;
                        return;
                    }
                }
            }
        }
    }

    private void close_race_and_append_data_and_grades(Race status_race) {

        // IS GOT RACE
        if (status_race.open) {

            // VALIDATE CAN CLOSE RACE
            if (status_race.index_move != 0) {

                // CLONE RACE
                Race race = new Race();
                race.clone_race(status_race);
                race.dateTime = this.dateTime;
                race.index_bid = this.index_bid;
                race.index_ask = this.index_ask;

                // ADD RACE TO LIST
                races.add(race);

                // RESET STATUS RACE
                status_race.reset();

                // Append grade to move cumu
                double grade = grades.get_grade(race);
                race.grade = grade;
                move_cumu += grade;

                return;
            }
        }
    }

    public void print_races() {
        for (Race race : races) {
            System.out.println(race.toString());
        }
    }

    private void update_pre_data() {
        index_0 = index;
        future_0 = future;

        index_change = 0;
        future_change = 0;
    }

    private void update_new_data(LocalDateTime dateTime, double index, double index_bid, double index_ask, double future) {
        this.dateTime = dateTime;
        this.index = index;
        this.future = future;
        this.index_bid = index_bid;
        this.index_ask = index_ask;

        bid_ask_margin = index_ask - index_bid;

        index_change = this.index - index_0;
        future_change = this.future - future_0;

        optimi_pesimi = this.future - this.index;
    }

    public ArrayList<Race> getRaces() {
        return races;
    }

    public double getMove_cumu() {
        return move_cumu;
    }

}
