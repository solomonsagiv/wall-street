package exp;

import options.optionsCalcs.IOptionsCalcs;
import serverObjects.BASE_CLIENT_OBJECT;
import tws.TwsContractsEnum;

public class ExpMonth extends Exp {

    public ExpMonth(BASE_CLIENT_OBJECT client, String expName, TwsContractsEnum contractsEnum, IOptionsCalcs iOptionsCalcs) {
        super(client, expName, contractsEnum, iOptionsCalcs);
    }
}
