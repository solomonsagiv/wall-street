package options;

import com.ib.client.Contract;
import serverObjects.stockObjects.STOCK_OBJECT;
import tws.MyContract;

public class StockOptions extends Options {

    STOCK_OBJECT client;

    public StockOptions( int baseID, STOCK_OBJECT client, OptionsEnum type ) {
        super( baseID, client, type );
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

}
