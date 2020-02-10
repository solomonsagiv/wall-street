package OPs;

import lists.MyDoubleList;
import lists.MyList;
import locals.MyObjects;
import options.Options;
import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;

import java.util.ArrayList;

public class EqualMoveService extends MyBaseService {

    // Variables
    private double opPlag;

    private Options options;
    private ArrayList< Double > moveListIndex = new ArrayList<>( );
    private boolean equalStatusIndex = false;
    private double startPriceIndex = 0;
    private double endPriceIndex = 0;
    private double moveIndex = 0;
    private double liveMoveIndex = 0;

    private MyObjects.MyDouble move;
    private MyList moveList;


    // Constructor
    public EqualMoveService(BASE_CLIENT_OBJECT client, String name, int type, int sleep, Options options, double opPlag ) {
        super(client, name, type, sleep);

        this.options = options;
        this.opPlag = opPlag;
        move = new MyObjects.MySimpleDouble( ) {
            @Override
            public double getVal() {
                return floor( moveIndex + liveMoveIndex, 10 );
            }
        };
        moveList = new MyDoubleList( client, getMove(), "EqualMove" );
    }

    @Override
    public void go() {
        calculateFromIndex();
    }

    private void calculateFromIndex() {

        double op = options.getOp().getVal();

        // ----- Equal area ----- //
        if ( op > oposite( opPlag ) && op < opPlag ) {

            // Start of the move
            if ( !equalStatusIndex ) {

                // Set start price
                startPriceIndex = getClient( ).getIndex( ).getVal();

            }

            // Set equalLiveMove
            endPriceIndex = getClient( ).getIndex( ).getVal();
            double equalLiveMove = endPriceIndex - startPriceIndex;
            setLiveMoveIndex( equalLiveMove );

            // Set status true
            equalStatusIndex = true;

            // ----- Outside area ----- //
        } else {

            // End of the move
            if ( equalStatusIndex ) {

                // Reset live move
                setLiveMoveIndex( 0 );

                // Set end price
                endPriceIndex = getClient( ).getIndex( ).getVal();

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

    public void setMoveIndex( double move ) {
        this.moveIndex = move;
    }

    public void appendMoveIndex( double move ) {
        this.moveIndex += move;
    }

    public ArrayList< Double > getMoveIndexListIndex() {
        return moveListIndex;
    }

    public MyObjects.MyDouble getMove() {
        return move;
    }

    public MyList getMoveList() {
        return moveList;
    }
}
