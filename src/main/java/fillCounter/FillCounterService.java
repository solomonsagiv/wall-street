package fillCounter;

import grades.GRADES;
import locals.L;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class FillCounterService {

    // Variables
    private double index = 0;
    private double index_bid = 0;
    private double index_ask = 0;
    private double future = 0;
    private double index_0 = 0;
    private double future_0 = 0;
    private double index_change = 0;
    private double future_change = 0;
    private double move_grade_cumu = 0;
    private double op_grade_cumu = 0;
    private ArrayList<Race> races = new ArrayList<>();
    private Race status_race;
    LocalDateTime dateTime;
    GRADES move_grade_giver;
    GRADES op_grade_giver;

    // Constructor
    public FillCounterService(GRADES move_grade_giver, GRADES op_grade_giver) {
        status_race = new Race();
        this.move_grade_giver = move_grade_giver;
        this.op_grade_giver = op_grade_giver;
    }

    public void run(LocalDateTime dateTime, double index, double index_bid, double index_ask, double future) {
        // Update new data
        update_new_data(dateTime, index, index_bid, index_ask, future);

        // IF PRE GOT DATA
        if (index_0 != 0 && future_0 != 0 && future != 0 && index_bid != 0 && index_ask != 0) {
            // Logic
            logic();

            // Check if there is done race
            close_race_and_append_data_and_grades(status_race);
        }

        // Update pre
        update_pre_data();
    }

    private void logic() {

        // ------------ NO  RACE ----------- //
        if (!status_race.open) {
            // Look for change
            // Both
            if (future_change != 0 && index_change != 0) {
                // BOTH UP
                if (future_change > 0 && index_change > 0) {
                    status_race.open(future, index,index_bid, index_ask, Race.BOTH_UP, future_change, index_change);
                    return;
                }
                // BOTH DOWN
                if (future_change < 0 && index_change < 0) {
                    status_race.open(future, index,index_bid, index_ask, Race.BOTH_DOWN, future_change, index_change);
                    return;
                }
                // FUTURE UP INDEX DOWN
                if (future_change > 0 && index_change < 0) {
                    status_race.open(future, index,index_bid, index_ask, Race.FUT_UP_IND_DOWN, future_change, index_change);
                    return;
                }
                // FUTURE DOWN INDEX UP
                if (future_change < 0 && index_change > 0) {
                    status_race.open(future, index,index_bid, index_ask, Race.FUT_DOWN_IND_UP, future_change, index_change);
                    return;
                }
                // FUTURE CHANGE
            } else if (future_change != 0) {
                // FUTURE UP
                if (future_change > 0) {
                    status_race.open(future, index,index_bid, index_ask, Race.FUT_UP, future_change, index_change);
                    return;
                }
                // FUTURE DOWN
                if (future_change < 0) {
                    status_race.open(future, index,index_bid, index_ask, Race.FUT_DOWN, future_change, index_change);
                    return;
                }
                // INDEX CHANGE
            } else if (index_change != 0) {
                // INDEX UP
                if (index_change > 0) {
                    status_race.open(future, index,index_bid, index_ask, Race.IND_UP, future_change, index_change);
                    return;
                }
                // INDEX DOWN
                if (index_change < 0) {
                    status_race.open(future, index,index_bid, index_ask, Race.IND_DOWN, future_change, index_change);
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

    private void close_race_and_append_data_and_grades(Race status_race) {

        // IS GOT RACE
        if (status_race.open) {

            // VALIDATE CAN CLOSE RACE
            if (status_race.index_move != 0) {

                System.out.println(move_grade_cumu);

                // APPEND MOVE GRADE
                double move_grade = move_grade_giver.get_grade(status_race);
                status_race.move_grade = move_grade;
                move_grade_cumu += move_grade;

                System.out.println(move_grade_cumu);

                // APPEND OP GRADE
                double op_grade = op_grade_giver.get_grade(status_race);
                status_race.op_grade = op_grade;
                op_grade_cumu += op_grade;

                // CLONE RACE
                Race race = new Race();
                race.clone_race(status_race);
                race.dateTime = this.dateTime;

                // ADD RACE TO LIST
                races.add(race);

                // RESET STATUS RACE
                status_race.reset();
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

        index_change = this.index - index_0;
        future_change = this.future - future_0;
    }

    public ArrayList<Race> getRaces() {
        return races;
    }

    public double getMove_grade_cumu() {
        return move_grade_cumu;
    }

    public double getOp_grade_cumu() {
        return op_grade_cumu;
    }
}
