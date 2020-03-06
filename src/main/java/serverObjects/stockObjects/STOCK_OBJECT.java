package serverObjects.stockObjects;

import api.tws.TwsHandler;
import options.OptionsEnum;
import options.OptionsHandler;
import options.StockOptions;
import serverObjects.BASE_CLIENT_OBJECT;
import tws.MyContract;
import tws.TwsContractsEnum;

public abstract class STOCK_OBJECT extends BASE_CLIENT_OBJECT {

    public STOCK_OBJECT() {}

    @Override
    public void initOptionsHandler() throws Exception {

        StockOptions monthOptions = new StockOptions( this, OptionsEnum.MONTH, getTwsHandler().getMyContract( TwsContractsEnum.OPT_MONTH ) );

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

    @Override
    public void setIndex( double index ) {
        if ( this.index == 0 ) {
            this.index = index;
            getOptionsHandler( ).initOptions( index );
        }
    }
}
