package OPs;

import lists.MyDoubleList;
import locals.MyObjects;
import options.Options;
import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;

public class OpAvgMoveService extends MyBaseService {

    // Variables
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

    public OpAvgMoveService(BASE_CLIENT_OBJECT client, String name, int type, int sleep, double opPlag, Options options ) {
        super(client, name, type, sleep);

        this.opPlag = opPlag;
        this.options = options;

        opAvg = options.getOpAvg();
        move = new MyObjects.MySimpleDouble( ) {
            @Override
            public double getVal() {
                return moveOpAvg + liveMoveOpAvg;
            }
        };
        moveList = new MyDoubleList( client, getMove(), "OpAvgMove" );
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

    @Override
    public void go() {

    }
}
