package OPs;

import options.Options;
import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;

import java.util.ArrayList;
import java.util.List;

public class EqualMoveService extends MyBaseService {

    // Variables
    private double opPlag;
    private Options options;
    private boolean equalStatusIndex = false;
    private double startPrice = 0;
    private double endPrice = 0;
    private double move = 0;
    private double liveMove = 0;

    private List moveList = new ArrayList< Double >( );

    // Constructor
    public EqualMoveService( BASE_CLIENT_OBJECT client, Options options, double opPlag ) {
        super( client );

        this.options = options;
        this.opPlag = opPlag;
    }

    @Override
    public void go() {
        calculateFromIndex( );
    }

    private void calculateFromIndex() {

        double op = options.getOp( );

        // ----- Equal area ----- //
        if ( op > oposite( opPlag ) && op < opPlag ) {

            // Start of the move
            if ( !equalStatusIndex ) {

                // Set start price
                startPrice = getClient( ).getIndex( );

            }

            // Set equalLiveMove
            endPrice = getClient( ).getIndex( );
            double equalLiveMove = endPrice - startPrice;
            setLiveMove( equalLiveMove );

            // Set status true
            equalStatusIndex = true;

            // ----- Outside area ----- //
        } else {

            // End of the move
            if ( equalStatusIndex ) {

                // Reset live move
                setLiveMove( 0 );

                // Set end price
                endPrice = getClient( ).getIndex( );

                // Get the move
                double move = floor( endPrice - startPrice, 10 );

                // Append the move
                appendMoveIndex( move );

            }

            // Set status false
            equalStatusIndex = false;

        }
    }

    @Override
    public String getName() {
        return "equalMove";
    }

    @Override
    public int getSleep() {
        return 200;
    }

    @Override
    public int getType() {
        return MyBaseService.EQUAL_MOVE;
    }

    private double oposite( double d ) {
        return d * -1;
    }

    private double floor( double d, int zeros ) {
        return Math.floor( d * zeros ) / zeros;
    }

    public void setLiveMove( double liveMove ) {
        this.liveMove = liveMove;
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions( Options options ) {
        this.options = options;
    }

    public void setMove( double move ) {
        this.move = move;
    }

    public void appendMoveIndex( double move ) {
        this.move += move;
    }

    public List getMoveList() {
        return moveList;
    }

    public double getMove() {
        return move + liveMove;
    }
}
