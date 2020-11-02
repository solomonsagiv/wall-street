package options.optionsCalcs;

import exp.Exp;
import locals.L;
import options.Options;
import options.Strike;
import serverObjects.indexObjects.INDEX_CLIENT_OBJECT;

public class IndexOptionsCalc implements IOptionsCalcs {

    // Variables
    Exp exp;
    Options options;
    String expName;
    INDEX_CLIENT_OBJECT client;

    // Constructor
    public IndexOptionsCalc(INDEX_CLIENT_OBJECT client, String expName) {
        this.client = client;
        this.expName = expName;
    }

    @Override
    public double getStrikeInMoney() {
        double currStrike = getOptions().getCurrStrike();

        if (currStrike != 0) {

            if (getPrice() - currStrike > client.getStrikeMargin()) {

                currStrike += client.getStrikeMargin();

            } else if (getPrice() - currStrike < -client.getStrikeMargin()) {

                currStrike -= client.getStrikeMargin();

            }
        } else {
            currStrike = getStrikeInMoneyIfZero().getStrike();
        }

        getOptions().setCurrStrike(currStrike);

        return currStrike;
    }

    @Override
    public Strike getStrikeInMoneyIfZero() {
        double margin = 1000000;
        Strike targetStrike = new Strike();

        for (Strike strike : getOptions().getStrikes()) {
            double newMargin = L.abs(strike.getStrike() - getPrice());

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

        if (getOptions().getProps().getDevidend() <= 0) {
            return 0;
        }

        double calcDev = getOptions().getProps().getDevidend() * 360.0 / getOptions().getProps().getDays() / getPrice();

        if (Double.isInfinite(calcDev)) {
            return 0;
        }

        return calcDev;
    }

    public Exp getExp() {
        if (exp == null) {
            exp = client.getExps().getExp(expName);
        }
        return exp;
    }

    public Options getOptions() {
        if (options == null) {
            options = client.getExps().getExp(expName).getOptions();
        }
        return options;
    }

    public double getPrice() {
        return getExp().getCalcFut();
    }

}
