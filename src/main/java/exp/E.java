package exp;

import options.Options;
import options.optionsCalcs.IOptionsCalcs;
import options.optionsCalcs.IndexOptionsCalc;
import serverObjects.BASE_CLIENT_OBJECT;
import tws.TwsContractsEnum;

public class E extends Exp {

    public E( BASE_CLIENT_OBJECT client, IOptionsCalcs iOptionsCalcs ) {
        super( client, iOptionsCalcs );
    }
}