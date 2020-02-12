package OPs;

import options.Options;
import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;

import java.util.ArrayList;
import java.util.List;

public class OpAvgMoveService extends MyBaseService {

    // Variables
    private double opPlag;

    private Options options;
    private boolean equalStatusOpAvg = false;
    private double startPriceOpAvg = 0;
    private double endPriceOpAvg = 0;
    private double liveMove = 0;
    public double marginFromOpAvg;
    private double move;

    List moveList = new ArrayList<Double>();

    public OpAvgMoveService(BASE_CLIENT_OBJECT client, String name, int type, int sleep, double opPlag, Options options) {
        super(client, name, type, sleep);

        this.opPlag = opPlag;
        this.options = options;
    }


    @Override
    public void go() {
        calculateFromOpAvg();
    }

    private void calculateFromOpAvg() {

        marginFromOpAvg = options.getOp() - options.getOpAvg();

        if (marginFromOpAvg > oposite(opPlag) && marginFromOpAvg < opPlag) {

            // Start of the move
            if (!equalStatusOpAvg) {
                // Set start price
                startPriceOpAvg = getClient().getIndex();
            }

            // Set equalLiveMove
            endPriceOpAvg = getClient().getIndex();
            double equalLiveMove = endPriceOpAvg - startPriceOpAvg;
            setLiveMove(equalLiveMove);

            // Set status true
            equalStatusOpAvg = true;

        } else {

            // End of the move
            if (equalStatusOpAvg) {

                // Reset live move
                setLiveMove(0);

                // Set end price
                endPriceOpAvg = getClient().getIndex();

                // Get the move
                double move = endPriceOpAvg - startPriceOpAvg;

                // Append the move
                appendMove(move);
            }

            // Set status false
            equalStatusOpAvg = false;
        }
    }

    private double oposite(double d) {
        return d * -1;
    }
    public Options getOptions() {
        return options;
    }
    public void setOptions(Options options) {
        this.options = options;
    }
    public void setLiveMove(double liveMove) {
        this.liveMove = liveMove;
    }
    public void appendMove(double move) {
        this.move += move;
    }
    public double getMove() {
        return move + liveMove;
    }
    public void setMove(double move) {
        this.move = move;
    }
    public List getMoveList() {
        return moveList;
    }


}
