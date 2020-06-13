package exp;

import options.optionsCalcs.IOptionsCalcs;
import serverObjects.BASE_CLIENT_OBJECT;

public class ExpWeek extends Exp {

    public ExpWeek(BASE_CLIENT_OBJECT client, ExpEnum expEnum, IOptionsCalcs iOptionsCalcs) {
        super(client, expEnum, iOptionsCalcs);
    }
}

