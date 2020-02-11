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
    private double moveOpAvg = 0;
    private double liveMove = 0;
    public double marginFromOpAvg;
    private double move;

    List moveList = new ArrayList<Double>();

    public OpAvgMoveService(BASE_CLIENT_OBJECT client, String name, int type, int sleep, double opPlag, Options options ) {
        super(client, name, type, sleep);

        this.opPlag = opPlag;
        this.options = options;
    }

    private void calculateFromOpAvg() {

        marginFromOpAvg = options.getOp( ) - options.getOpAvg( );

        if ( marginFromOpAvg > oposite( opPlag ) && marginFromOpAvg < opPlag ) {

            // Start of the move
            if ( !equalStatusOpAvg ) {
                // Set start price
                startPriceOpAvg = getClient( ).getIndex( );
            }

            // Set equalLiveMove
            endPriceOpAvg = getClient( ).getIndex( );
            double equalLiveMove = endPriceOpAvg - startPriceOpAvg;
            setLiveMove( equalLiveMove );

            // Set status true
            equalStatusOpAvg = true;

        } else {

            // End of the move
            if ( equalStatusOpAvg ) {

                // Reset live move
                setLiveMove( 0 );

                // Set end price
                endPriceOpAvg = getClient( ).getIndex( );

                // Get the move
                double move = endPriceOpAvg - startPriceOpAvg;

                // Append the move
                appendMoveOpAvg( move );
            }

            // Set status false
            equalStatusOpAvg = false;
        }
    }

    private double oposite( double d ) {
        return d * -1;
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions( Options options ) {
        this.options = options;
    }

    public boolean isEqualStatusOpAvg() {
        return equalStatusOpAvg;
    }

    public void setEqualStatusOpAvg( boolean equalStatusOpAvg ) {
        this.equalStatusOpAvg = equalStatusOpAvg;
    }

    public double getStartPriceOpAvg() {
        return startPriceOpAvg;
    }

    public void setStartPriceOpAvg( double startPriceOpAvg ) {
        this.startPriceOpAvg = startPriceOpAvg;
    }

    public double getEndPriceOpAvg() {
        return endPriceOpAvg;
    }

    public void setEndPriceOpAvg( double endPriceOpAvg ) {
        this.endPriceOpAvg = endPriceOpAvg;
    }

    public void setMoveOpAvg( double moveOpAvg ) {
        this.moveOpAvg = moveOpAvg;
    }

    public double getLiveMove() {
        return liveMove;
    }

    public void setLiveMove( double liveMove ) {
        this.liveMove = liveMove;
    }

    public void appendMoveOpAvg( double move ) {
        this.moveOpAvg += move;
    }

    public double getMove() {
        return move + liveMove;
    }

    public List getMoveList() {
        return moveList;
    }

    @Override
    public void go() {

    }
}
