package options;

import com.ib.client.Contract;
import serverObjects.indexObjects.INDEX_CLIENT_OBJECT;

public class IndexOptions extends Options {

    protected INDEX_CLIENT_OBJECT client;

    // Constructor
    public IndexOptions( int baseID, INDEX_CLIENT_OBJECT client, OptionsEnum type, Contract twsContract ) {
        super( baseID, client, type, twsContract );
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


    public double getCalcDevidend() {

        if ( devidend <= 0 ) {
            return 0;
        }

        double calcDev = getDevidend( ) * 360.0 / getDays( ) / client.getFuture( );

        if ( Double.isInfinite( calcDev ) ) {
            return 0;
        }

        return calcDev;
    }

    public double getCalcBorrow() {
        if ( getBorrow( ) != 0 ) {
            return getBorrow( );
        } else {
            return floor( client.getFuture( ) * 0.002 / 360.0 * getDays( ), 10000 );
        }
    }
}
