package OPs;

import options.Options;
import serverObjects.BASE_CLIENT_OBJECT;
import threads.MyThread;

import java.util.ArrayList;

public class EqualMoveCalculator extends MyThread implements Runnable {

    // Variables
    private int sleep = 1000;
    private double opPlag;

    private Options options;
    private ArrayList< Double > moveListIndex = new ArrayList<>( );
    private boolean equalStatusOpAvg = false;
    private double startPriceOpAvg = 0;
    private double endPriceOpAvg = 0;
    private double moveOpAvg = 0;
    private double liveMoveOpAvg = 0;
    private boolean equalStatusIndex = false;
    private double startPriceIndex = 0;
    private double endPriceIndex = 0;
    private double moveIndex = 0;
    private double liveMoveIndex = 0;


    // Constructor
    public EqualMoveCalculator( BASE_CLIENT_OBJECT client, double opPlag ) {
        super( client );
        setName( "EqualMove" );
        this.opPlag = opPlag;
    }

    public EqualMoveCalculator( BASE_CLIENT_OBJECT client, double opPlag, Options options ) {
        super( client );
        setName( "EqualMove" );
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

                // Calculate from index
                calculateFromIndex( );

                // Calculate from opAvg
                calculateFromOpAvg( );

            } catch ( InterruptedException e ) {
                setRun( false );
                getHandler( ).close( );
            }

        }
    }

    private void calculateFromOpAvg() {


        double marginFromOpAvg = options.getOp() - options.getOpAvg( );

        System.out.println( "Margin: " + marginFromOpAvg );
        System.out.println( "Move: " + getMoveOpAvg() );
        System.out.println( "Live: " + getLiveMoveOpAvg() );

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
                double move = floor( endPriceOpAvg - startPriceOpAvg, 10 );

                // Append the move
                appendMoveOpAvg( move );
            }

            // Set status false
            equalStatusOpAvg = false;

        }
    }

    private void calculateFromIndex() {

        double op = options.getOp( );

        if ( op > oposite( opPlag ) && op < opPlag ) {

            // Start of the move
            if ( !equalStatusIndex ) {

                // Set start price
                startPriceIndex = getClient( ).getIndex( );

            }

            // Set equalLiveMove
            endPriceIndex = getClient( ).getIndex( );
            double equalLiveMove = endPriceIndex - startPriceIndex;
            setLiveMoveIndex( equalLiveMove );

            // Set status true
            equalStatusIndex = true;

        } else {

            // End of the move
            if ( equalStatusIndex ) {

                // Reset live move
                setLiveMoveIndex( 0 );

                // Set end price
                endPriceIndex = getClient( ).getIndex( );

                // Get the move
                double move = floor( endPriceIndex - startPriceIndex, 10 );

                // Append the move
                appendMoveIndex( move );

            }

            // Set status false
            equalStatusIndex = false;

        }
    }

    private double oposite( double d ) {
        return d * -1;
    }

    private double floor( double d, int zeros ) {
        return Math.floor( d * zeros ) / zeros;
    }

    private double getLiveMoveIndex() {
        return liveMoveIndex;
    }

    public void setLiveMoveIndex( double liveMove ) {
        this.liveMoveIndex = liveMove;
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions( Options options ) {
        this.options = options;
    }

    public double getMoveIndex() {
        return floor( moveIndex + liveMoveIndex, 10 );
    }

    public void setMoveIndex( double move ) {
        this.moveIndex = move;
    }

    public void appendMoveIndex( double move ) {
        this.moveIndex += move;
    }

    public ArrayList< Double > getMoveIndexListIndex() {
        return moveListIndex;
    }

    public void setMoveListIndex( ArrayList< Double > moveList ) {
        this.moveListIndex = moveList;
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
        return floor( moveOpAvg + liveMoveOpAvg, 10 );
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
