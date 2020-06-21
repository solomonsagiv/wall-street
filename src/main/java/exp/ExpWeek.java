package exp;

import options.optionsCalcs.IOptionsCalcs;
import serverObjects.BASE_CLIENT_OBJECT;
import tws.TwsContractsEnum;

public class ExpWeek extends Exp {

    public ExpWeek(BASE_CLIENT_OBJECT client, ExpEnum expEnum, TwsContractsEnum contractsEnum, IOptionsCalcs optionsCalcs ) {
        super(client, expEnum, contractsEnum, optionsCalcs);
    }
}

