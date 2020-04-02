package options;

import com.ib.client.Contract;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.INDEX_CLIENT_OBJECT;
import tws.MyContract;

public class IndexOptions extends Options {

    protected INDEX_CLIENT_OBJECT client;

    // Constructor
    public IndexOptions( int baseID, INDEX_CLIENT_OBJECT client, OptionsEnum type ) {
        super( baseID, client, type );
    }

    public IndexOptions( int baseID, INDEX_CLIENT_OBJECT client, OptionsEnum type, OptionsDDeCells dDeCells ) {
        super( baseID, client, type, dDeCells );
    }

    @Override
    public double getStrikeInMoney() {

        if ( currStrike != 0 ) {

            if ( client.getFuture( ) - currStrike > client.getStrikeMargin( ) ) {

                currStrike += client.getStrikeMargin( );

            } else if ( client.getFuture( ) - currStrike < -client.getStrikeMargin( ) ) {

                currStrike -= client.getStrikeMargin( );

            }
        } else {
            currStrike = getStrikeInMoneyIfZero( ).getStrike( );
        }
        return currStrike;
    }

    @Override
    public Strike getStrikeInMoneyIfZero() {
        double margin = 1000000;
        Strike targetStrike = new Strike( );

        for ( Strike strike : getStrikes( ) ) {
            double newMargin = absolute( strike.getStrike( ) - client.getFuture( ) );

            if ( newMargin < margin ) {

                margin = newMargin;
                targetStrike = strike;

            } else {
                break;
            }
        }
        return targetStrike;
    }



}
