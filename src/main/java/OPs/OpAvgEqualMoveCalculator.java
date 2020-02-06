package OPs;

import lists.MyDoubleList;
import locals.MyObjects;
import options.Options;
import serverObjects.BASE_CLIENT_OBJECT;
import threads.MyThread;

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
    private MyObjects.MyDouble opAvg;
    private MyObjects.MySimpleDouble move;

    private MyDoubleList moveList;

    public OpAvgEqualMoveCalculator( BASE_CLIENT_OBJECT client, double opPlag, Options options ) {
        super( client );
        setName( "EqualMoveOp" );
        this.opPlag = opPlag;
        this.options = options;
        this.opAvg = options.getOpAvg( );

        move = new MyObjects.MySimpleDouble( ) {
            @Override
            public double getVal() {
                return moveOpAvg + liveMoveOpAvg;
            }
        };

        moveList = new MyDoubleList( client, getMove(), "OpAvgMove" );
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

        marginFromOpAvg = options.getOp( ).getVal( ) - options.getOpAvg( ).getVal( );

        if ( marginFromOpAvg > oposite( opPlag ) && marginFromOpAvg < opPlag ) {

            // Start of the move
            if ( !equalStatusOpAvg ) {
                // Set start price
                startPriceOpAvg = getClient( ).getIndex( ).getVal( );
            }

            // Set equalLiveMove
            endPriceOpAvg = getClient( ).getIndex( ).getVal( );
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
                endPriceOpAvg = getClient( ).getIndex( ).getVal( );

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

    public double getLiveMoveOpAvg() {
        return liveMoveOpAvg;
    }

    public void setLiveMoveOpAvg( double liveMoveOpAvg ) {
        this.liveMoveOpAvg = liveMoveOpAvg;
    }

    public void appendMoveOpAvg( double move ) {
        this.moveOpAvg += move;
    }

    public MyObjects.MySimpleDouble getMove() {
        return move;
    }

    public MyDoubleList getMoveList() {
        return moveList;
    }
}
