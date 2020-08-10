package options.optionsCalcs;

import exp.Exp;
import locals.L;
import options.Options;
import options.Strike;
import serverObjects.stockObjects.STOCK_OBJECT;

public class StockOptionsCalc implements IOptionsCalcs {

    // Variables
    Exp exp;
    Options options;
    String expName;
    STOCK_OBJECT client;

    // Constructor
    public StockOptionsCalc(STOCK_OBJECT client, String expName) {
        this.client = client;
        this.expName = expName;
    }

    @Override
    public double getStrikeInMoney() {

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

        for (Strike strike : getOptions().getStrikes()) {
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

        if (getOptions().getProps().getDevidend() <= 0) {
            return 0;
        }

        double calcDev = getOptions().getProps().getDevidend() * 360.0 / options.getProps().getDays() / client.getIndex();

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
            options = getExp().getOptions();
        }
        return options;
    }

    public double getPrice() {
        return getExp().getCalcFut();
    }

}