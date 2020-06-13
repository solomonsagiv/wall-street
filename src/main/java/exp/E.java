package exp;

import options.OptionsDDeCells;
import options.optionsCalcs.IOptionsCalcs;
import serverObjects.BASE_CLIENT_OBJECT;

public class E extends Exp {

    public E(BASE_CLIENT_OBJECT client, ExpEnum expEnum, IOptionsCalcs iOptionsCalcs) {
        super(client, expEnum, iOptionsCalcs);
    }

    public E(BASE_CLIENT_OBJECT client, ExpEnum expEnum, IOptionsCalcs iOptionsCalcs, OptionsDDeCells optionsDDeCells) {
        super(client, expEnum, iOptionsCalcs, optionsDDeCells);
    }
}