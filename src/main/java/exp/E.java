package exp;

import options.OptionsDDeCells;
import options.optionsCalcs.IOptionsCalcs;
import serverObjects.BASE_CLIENT_OBJECT;
import tws.TwsContractsEnum;

public class E extends Exp {

    public E( BASE_CLIENT_OBJECT client, ExpEnum expEnum, TwsContractsEnum contractsEnum, IOptionsCalcs iOptionsCalcs ) {
        super( client, expEnum, contractsEnum, iOptionsCalcs );
    }

    public E( BASE_CLIENT_OBJECT client, ExpEnum expEnum, TwsContractsEnum twsContractsEnum, IOptionsCalcs iOptionsCalcs, OptionsDDeCells optionsDDeCells) {
        super(client, expEnum, twsContractsEnum, iOptionsCalcs, optionsDDeCells);
    }
}