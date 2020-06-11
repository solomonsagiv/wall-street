package options.optionsCalcs;

import exp.Exp;
import exp.ExpEnum;
import locals.L;
import options.Options;
import options.Strike;
import serverObjects.indexObjects.INDEX_CLIENT_OBJECT;
import serverObjects.stockObjects.STOCK_OBJECT;

public class StockOptionsCalc implements IOptionsCalcs {

    // Variables
    Exp exp;
    Options options;
    ExpEnum expEnum;
    STOCK_OBJECT client;

    // Constructor
    public StockOptionsCalc( STOCK_OBJECT client, ExpEnum expEnum ) {
        this.client = client;
        this.expEnum = expEnum;
    }

    @Override
    public double getStrikeInMoney(  ) {

        double currStrike = options.getCurrStrike();

        if (currStrike != 0) {
            if (client.getIndex() - currStrike > client.getStrikeMargin()) {
                currStrike += client.getStrikeMargin();
            } else if (client.getIndex() - currStrike < -client.getStrikeMargin()) {
                currStrike -= client.getStrikeMargin();
            }
        } else {
            currStrike = getStrikeInMoneyIfZero().getStrike();
        }

        // Update curr strike
        options.setCurrStrike(currStrike);
        return currStrike;
    }

    @Override
    public Strike getStrikeInMoneyIfZero() {
        double margin = 1000000;
        Strike targetStrike = new Strike();

        for (Strike strike : options.getStrikes()) {
            double newMargin = L.abs(strike.getStrike() - client.getIndex());

            if (newMargin < margin) {

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

        if (options.getProps().getDevidend() <= 0) {
            return 0;
        }

        double calcDev = options.getProps().getDevidend() * 360.0 / options.getProps().getDays() / client.getIndex();

        if (Double.isInfinite(calcDev)) {
            return 0;
        }

        return calcDev;
    }

    public Exp getExp() {
        if ( exp == null ) {
            exp = client.getExps().getExp( expEnum );
        }
        return exp;
    }

    public Options getOptions() {
        if ( options == null ) {
            options = getExp().getOptions();
        }
        return options;
    }

    public double getPrice() {
        return getExp().getFuture();
    }

}
