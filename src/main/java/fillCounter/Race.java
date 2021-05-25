package fillCounter;

public class Race {

    public static final int FUT_UP = 1;
    public static final int FUT_DOWN = 2;
    public static final int IND_UP = 3;
    public static final int IND_DOWN = 4;
    public static final int BOTH_UP = 5;
    public static final int BOTH_DOWN = 6;
    public static final int FUT_UP_IND_DOWN = 7;
    public static final int FUT_DOWN_IND_UP = 8;

    boolean open = false;
    double optimi_pesimi;
    int type;
    double future_move;
    double index_move;

    public Race() {}

    public void clone_race(Race status_race) {
        this.optimi_pesimi = status_race.optimi_pesimi;
        this.type = status_race.type;
        this.future_move = status_race.future_move;
        this.index_move = status_race.index_move;
    }

    public void reset_race() {
        this.optimi_pesimi = 0;
        this.type = 0;
        this.future_move = 0;
        this.index_move = 0;
        this.open = false;
    }

    public void open(double optimi_pesimi, int type, double future_move, double index_move) {
        this.optimi_pesimi = optimi_pesimi;
        this.type = type;
        this.future_move = future_move;
        this.index_move = index_move;
        this.open = true;
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
