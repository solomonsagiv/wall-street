package serverObjects.stockObjects;

import options.OptionsHandler;
import serverObjects.BASE_CLIENT_OBJECT;

public abstract class STOCK_OBJECT extends BASE_CLIENT_OBJECT {


    public STOCK_OBJECT() {}

    public OptionsHandler getOptionsHandler() {
        if ( optionsHandler == null ) {
            optionsHandler = new OptionsHandler( this );
        }
        return optionsHandler;
    }

}
