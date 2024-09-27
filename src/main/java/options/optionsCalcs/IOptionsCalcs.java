package options.optionsCalcs;

import exp.Exp;
import options.Options;
import options.Strike;

public interface IOptionsCalcs {

    double getStrikeInMoney();

    Strike getStrikeInMoneyIfZero();

    double getCalcDevidend();

    public Exp getExp();

    public Options getOptions();

    double getPrice();

}
