package serverObjects.stockObjects;

import options.OptionsEnum;
import options.OptionsHandler;
import options.StockOptions;
import serverObjects.ApiEnum;
import serverObjects.BASE_CLIENT_OBJECT;
import tws.MyContract;
import tws.TwsContractsEnum;

public abstract class STOCK_OBJECT extends BASE_CLIENT_OBJECT {

    public STOCK_OBJECT() {}

    @Override
    public void initOptionsHandler() throws Exception {
        // Month
        MyContract monthContract = getTwsHandler().getMyContract( TwsContractsEnum.OPT_MONTH );
        StockOptions monthOptions = new StockOptions( monthContract.getMyId(), this, OptionsEnum.MONTH, monthContract );

        // Quarter
        MyContract quarterContract = getTwsHandler().getMyContract( TwsContractsEnum.OPT_QUARTER );
        StockOptions quarterOptions = new StockOptions( quarterContract.getMyId(), this, OptionsEnum.QUARTER, quarterContract );

        OptionsHandler optionsHandler = new OptionsHandler( this ) {
            @Override
            public void initOptions() {
                addOptions( monthOptions );
                addOptions( quarterOptions );
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

            // Request options tws
            if (getApi() == ApiEnum.TWS) {
                getTwsHandler().requestOptions( getOptionsHandler().getOptionsList() );
            }
        }
    }
}
