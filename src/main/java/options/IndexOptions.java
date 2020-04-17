package options;

import serverObjects.indexObjects.INDEX_CLIENT_OBJECT;
import tws.TwsContractsEnum;

public class IndexOptions extends Options {

    protected INDEX_CLIENT_OBJECT client;

    // Constructor
    public IndexOptions(int baseID, INDEX_CLIENT_OBJECT client, OptionsEnum type, TwsContractsEnum contractType) {
        super( baseID, client, type, contractType );
    }

    public IndexOptions( int baseID, INDEX_CLIENT_OBJECT client, OptionsEnum type, TwsContractsEnum contractType, OptionsDDeCells dDeCells ) {
        super( baseID, client, type, contractType, dDeCells );
    }

    @Override
    public double getStrikeInMoney() {

        if ( currStrike != 0 ) {

            if ( getFuture( ) - currStrike > client.getStrikeMargin( ) ) {

                currStrike += client.getStrikeMargin( );

            } else if ( getFuture( ) - currStrike < -client.getStrikeMargin( ) ) {

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
            double newMargin = absolute( strike.getStrike( ) - getFuture( ) );

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

        if ( getProps().getDevidend() <= 0 ) {
            return 0;
        }

        double calcDev = getProps().getDevidend() * 360.0 / getProps().getDays() / getFuture( );

        if ( Double.isInfinite( calcDev ) ) {
            return 0;
        }

        return calcDev;
    }


}
