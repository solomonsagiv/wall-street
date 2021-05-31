package fillCounter;

import java.time.LocalDateTime;

public class Race {

    public static final int FUT_UP = 1;
    public static final int FUT_DOWN = 2;
    public static final int IND_UP = 3;
    public static final int IND_DOWN = 4;
    public static final int BOTH_UP = 5;
    public static final int BOTH_DOWN = 6;
    public static final int FUT_UP_IND_DOWN = 7;
    public static final int FUT_DOWN_IND_UP = 8;

    public double optimi_pesimi = 0;
    int type = 0;
    public double future_move = 0;
    public double index_move = 0;
    public double index_bid = 0;
    public double index_ask = 0;
    public double future = 0;
    public double index = 0;
    boolean open = false;
    LocalDateTime dateTime = null;
    public double move_grade = 0;
    public double op_grade = 0;

    public Race() {}

    public void open(double future, double index,double index_bid, double index_ask, int type, double future_move, double index_move) {
        this.type = type;
        this.future_move = future_move;
        this.index_move = index_move;
        this.index = index;
        this.index_bid = index_bid;
        this.index_ask = index_ask;
        this.future = future;
        this.optimi_pesimi = future - index;
        this.open = true;
    }

    public void clone_race(Race status_race) {
        this.optimi_pesimi = status_race.optimi_pesimi;
        this.type = status_race.type;
        this.future_move = status_race.future_move;
        this.index_move = status_race.index_move;
        this.open = status_race.open;
        this.future = status_race.future;
        this.index = status_race.index;
        this.index_bid = status_race.index_bid;
        this.index_ask = status_race.index_ask;
        this.move_grade = status_race.move_grade;
        this.op_grade = status_race.op_grade;
        this.dateTime = status_race.dateTime;
    }

    public void reset() {
        this.optimi_pesimi = 0;
        this.type = 0;
        this.future_move = 0;
        this.index_move = 0;
        this.index_bid = 0;
        this.index_ask = 0;
        this.future = 0;
        this.index = 0;
        this.move_grade = 0;
        this.op_grade = 0;
        this.open = false;
        this.dateTime = null;
    }

    @Override
    public String toString() {
        return "Race{" +
                "optimi_pesimi=" + optimi_pesimi +
                ", type=" + type +
                ", future_move=" + future_move +
                ", index_move=" + index_move +
                ", dateTime=" + dateTime +
                '}';
    }
}
