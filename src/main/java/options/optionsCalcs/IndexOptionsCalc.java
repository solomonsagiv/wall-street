package options.optionsCalcs;

import exp.Exp;
import locals.L;
import options.Options;
import options.Strike;
import serverObjects.indexObjects.INDEX_CLIENT_OBJECT;

public class IndexOptionsCalc implements IOptionsCalcs {

    // Variables
    Exp exp;
    INDEX_CLIENT_OBJECT client;
    Options options;

    // Constructor
    public IndexOptionsCalc(INDEX_CLIENT_OBJECT client, Exp exp) {
        this.client = client;
        this.exp = exp;
        this.options = exp.getOptions();
    }

    @Override
    public double getStrikeInMoney() {

        double currStrike = options.getCurrStrike();

        if (currStrike != 0) {

            if (exp.getFuture() - currStrike > client.getStrikeMargin()) {

                currStrike += client.getStrikeMargin();

            } else if (exp.getFuture() - currStrike < -client.getStrikeMargin()) {

                currStrike -= client.getStrikeMargin();

            }
        } else {
            currStrike = getStrikeInMoneyIfZero().getStrike();
        }

        options.setCurrStrike(currStrike);

        return currStrike;
    }

    @Override
    public Strike getStrikeInMoneyIfZero() {
        double margin = 1000000;
        Strike targetStrike = new Strike();

        for (Strike strike : options.getStrikes()) {
            double newMargin = L.abs(strike.getStrike() - exp.getFuture());

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

        double calcDev = options.getProps().getDevidend() * 360.0 / options.getProps().getDays() / exp.getFuture();

        if (Double.isInfinite(calcDev)) {
            return 0;
        }

        return calcDev;
    }

}
