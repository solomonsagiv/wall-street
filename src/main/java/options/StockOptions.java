package options;

import com.ib.client.Contract;
import serverObjects.stockObjects.STOCK_OBJECT;

public class StockOptions extends Options {

    STOCK_OBJECT client;

    public StockOptions( STOCK_OBJECT client, OptionsEnum type, Contract twsContract ) {
        super( client, type, twsContract );
        this.client = client;
    }

    @Override
    public double getStrikeInMoney() {
        if ( currStrike != 0 ) {
            if ( client.getIndex( ) - currStrike > client.getStrikeMargin( ) ) {
                currStrike += client.getStrikeMargin( );
            } else if ( client.getIndex( ) - currStrike < -client.getStrikeMargin( ) ) {
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
            double newMargin = absolute( strike.getStrike( ) - client.getIndex( ) );

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

        double calcDev = getDevidend( ) * 360.0 / getDays( ) / client.getIndex( );

        if ( Double.isInfinite( calcDev ) ) {
            return 0;
        }

        return calcDev;
    }
    public double getCalcBorrow() {
        if ( getBorrow( ) != 0 ) {
            return getBorrow( );
        } else {
            return floor( client.getIndexBid( ) * 0.002 / 360.0 * getDays( ), 10000 );
        }
    }
}
