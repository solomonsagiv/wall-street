package exp;

import locals.L;
import options.Options;
import options.Strike;
import options.optionsCalcs.IOptionsCalcs;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.INDEX_CLIENT_OBJECT;
import tws.TwsContractsEnum;

public class E_Index extends E {

    // Constructor
    public E_Index( BASE_CLIENT_OBJECT client ) {
        super( client );
    }

    @Override
    public double getStrikeInMoney() {
        double currStrike = options.getCurrStrike( );

        if ( currStrike != 0 ) {

            if ( getFuture( ) - currStrike > client.getStrikeMargin( ) ) {

                currStrike += client.getStrikeMargin( );

            } else if ( getFuture( ) - currStrike < -client.getStrikeMargin( ) ) {

                currStrike -= client.getStrikeMargin( );

            }
        } else {
            currStrike = getStrikeInMoneyIfZero( ).getStrike( );
        }

        options.setCurrStrike( currStrike );

        return currStrike;
    }

    @Override
    public Strike getStrikeInMoneyIfZero() {
        double margin = 1000000;
        Strike targetStrike = new Strike( );

        for ( Strike strike : options.getStrikes( ) ) {
            double newMargin = L.abs( strike.getStrike( ) - getFuture( ) );

            if ( newMargin < margin ) {

                margin = newMargin;
                targetStrike = strike;

            } else {
                break;
            }
        }
        return targetStrike;
    }

    @Override
    public double getCalcDevidend() {

        if ( options.getProps( ).getDevidend( ) <= 0 ) {
            return 0;
        }

        double calcDev = options.getProps( ).getDevidend( ) * 360.0 / options.getProps( ).getDays( ) / getFuture( );

        if ( Double.isInfinite( calcDev ) ) {
            return 0;
        }

        return calcDev;
    }

}
