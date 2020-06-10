package exp;

import options.Options;
import options.optionsCalcs.IOptionsCalcs;
import serverObjects.BASE_CLIENT_OBJECT;
import tws.TwsContractsEnum;

public class ExpMonth extends Exp {

    public ExpMonth( BASE_CLIENT_OBJECT client, IOptionsCalcs iOptionsCalcs ) {
        super( client, iOptionsCalcs );
    }
}
