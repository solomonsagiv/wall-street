package options;

import serverObjects.stockObjects.STOCK_OBJECT;
import tws.TwsContractsEnum;

public class StockOptions extends Options {

    STOCK_OBJECT client;

    public StockOptions(int baseID, STOCK_OBJECT client, OptionsEnum type, TwsContractsEnum contractType) {
        super(baseID, client, type, contractType);
        this.client = client;
    }

    @Override
    public double getStrikeInMoney() {
        if (currStrike != 0) {
            if (client.getIndex() - currStrike > client.getStrikeMargin()) {
                currStrike += client.getStrikeMargin();
            } else if (client.getIndex() - currStrike < -client.getStrikeMargin()) {
                currStrike -= client.getStrikeMargin();
            }
        } else {
            currStrike = getStrikeInMoneyIfZero().getStrike();
        }
        return currStrike;
    }

    @Override
    public Strike getStrikeInMoneyIfZero() {
        double margin = 1000000;
        Strike targetStrike = new Strike();

        for (Strike strike : getStrikes()) {
            double newMargin = absolute(strike.getStrike() - client.getIndex());

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

        if (getProps().getDevidend() <= 0) {
            return 0;
        }

        double calcDev = getProps().getDevidend() * 360.0 / getProps().getDays() / client.getIndex();

        if (Double.isInfinite(calcDev)) {
            return 0;
        }

        return calcDev;
    }


}
