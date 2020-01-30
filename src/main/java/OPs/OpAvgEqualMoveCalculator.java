package OPs;

import options.Options;
import serverObjects.BASE_CLIENT_OBJECT;
import threads.MyThread;

import java.util.ArrayList;

public class OpAvgEqualMoveCalculator extends MyThread implements Runnable {

    // Variables
    private int sleep = 200;
    private double opPlag;

    private Options options;
    private boolean equalStatusOpAvg = false;
    private double startPriceOpAvg = 0;
    private double endPriceOpAvg = 0;
    private double moveOpAvg = 0;
    private double liveMoveOpAvg = 0;
    public double marginFromOpAvg;

    // Constructor
    public OpAvgEqualMoveCalculator( BASE_CLIENT_OBJECT client, double opPlag ) {
        super( client );
        setName( "EqualMoveOp" );
        this.opPlag = opPlag;
    }

    public OpAvgEqualMoveCalculator( BASE_CLIENT_OBJECT client, double opPlag, Options options ) {
        super( client );
        setName( "EqualMoveOp" );
        this.opPlag = opPlag;
        this.options = options;
    }

    @Override
    public void initRunnable() {
        setRunnable( this );
    }

    @Override
    public void run() {

        while ( isRun( ) ) {
            try {

                // Sleep
                Thread.sleep( sleep );

                // Calculate from opAvg
                calculateFromOpAvg( );

            } catch ( InterruptedException e ) {
                break;
            }

        }
    }

    private void calculateFromOpAvg() {

        marginFromOpAvg = options.getOp() - options.getOpAvg( );

        if ( marginFromOpAvg > oposite( opPlag ) && marginFromOpAvg < opPlag ) {

            // Start of the move
            if ( !equalStatusOpAvg ) {
                // Set start price
                startPriceOpAvg = getClient( ).getIndex( );
            }

            // Set equalLiveMove
            endPriceOpAvg = getClient( ).getIndex( );
            double equalLiveMove = endPriceOpAvg - startPriceOpAvg;
            setLiveMoveOpAvg( equalLiveMove );

            // Set status true
            equalStatusOpAvg = true;

        } else {

            // End of the move
            if ( equalStatusOpAvg ) {

                // Reset live move
                setLiveMoveOpAvg( 0 );

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

    public double getMoveOpAvg() {
        return moveOpAvg + liveMoveOpAvg;
    }

    public void setMoveOpAvg( double moveOpAvg ) {
        this.moveOpAvg = moveOpAvg;
    }

    public double getLiveMoveOpAvg() {
        return liveMoveOpAvg;
    }

    public void setLiveMoveOpAvg( double liveMoveOpAvg ) {
        this.liveMoveOpAvg = liveMoveOpAvg;
    }

    public void appendMoveOpAvg( double move ) {
        this.moveOpAvg += move;
    }

}
