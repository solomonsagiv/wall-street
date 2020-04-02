package options.optionsCalcs;

import options.Strike;

public interface IOptionsCalcs {

    double getStrikeInMoney();
    Strike getStrikeInMoneyIfZero();
    double getCalcDevidend();

}
