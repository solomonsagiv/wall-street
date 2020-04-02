package options.optionsCalcs;

import locals.L;
import options.Options;
import options.Strike;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.INDEX_CLIENT_OBJECT;

public class IndexOptionsCalcs implements IOptionsCalcs {

    // Variables
    INDEX_CLIENT_OBJECT client;
    Options options;

    /// Constructor
    public IndexOptionsCalcs( INDEX_CLIENT_OBJECT client, Options options ) {
        this.client = client;
        this.options = options;
    }

    @Override
    public double getStrikeInMoney() {

        if ( options.currStrike != 0 ) {

            if ( client.getFuture() - options.currStrike > client.getStrikeMargin( ) ) {

                options.currStrike += client.getStrikeMargin( );

            } else if ( client.getFuture( ) - options.currStrike < -client.getStrikeMargin( ) ) {

                options.currStrike -= client.getStrikeMargin( );

            }
        } else {
            options.currStrike = getStrikeInMoneyIfZero( ).getStrike( );
        }
        return options.currStrike;
    }

    @Override
    public Strike getStrikeInMoneyIfZero() {
        double margin = 1000000;
        Strike targetStrike = new Strike( );

        for ( Strike strike : options.getStrikes( ) ) {
            double newMargin = L.abs( strike.getStrike( ) - client.getFuture( ) );

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

        if ( options.getProps().getDevidend() <= 0 ) {
            return 0;
        }

        double calcDev = options.getProps().getDevidend() * 360.0 / options.getProps().getDays() / client.getFuture( );

        if ( Double.isInfinite( calcDev ) ) {
            return 0;
        }

        return calcDev;
    }
}
