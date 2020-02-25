package serverObjects.stockObjects;

import options.OptionsEnum;
import options.OptionsHandler;
import options.StockOptions;
import serverObjects.BASE_CLIENT_OBJECT;
import tws.TwsContractsEnum;

public abstract class STOCK_OBJECT extends BASE_CLIENT_OBJECT {

    public STOCK_OBJECT() {}

    @Override
    public void initOptionsHandler() {

        StockOptions monthOptions = new StockOptions( this, OptionsEnum.MONTH, getTwsData().getContract( TwsContractsEnum.MONTH ) );

        OptionsHandler optionsHandler = new OptionsHandler( this ) {
            @Override
            public void initOptions() {
                addOptions( monthOptions );
            }

            @Override
            public void initMainOptions() {
                setMainOptions( monthOptions );
            }
        };
        setOptionsHandler( optionsHandler );
    }

}
