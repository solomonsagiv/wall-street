package serverObjects.stockObjects;

import options.OptionsEnum;
import options.OptionsHandler;
import options.StockOptions;
import serverObjects.ApiEnum;
import serverObjects.BASE_CLIENT_OBJECT;
import tws.MyContract;
import tws.TwsContractsEnum;

public abstract class STOCK_OBJECT extends BASE_CLIENT_OBJECT {

    public STOCK_OBJECT() {
    }

    @Override
    public void initOptionsHandler() {
        // Month
        MyContract monthContract = getTwsHandler( ).getMyContract( TwsContractsEnum.OPT_QUARTER );
        StockOptions monthOptions = new StockOptions( monthContract.getMyId( ), this, OptionsEnum.QUARTER );

        // Quarter
        MyContract quarterContract = getTwsHandler( ).getMyContract( TwsContractsEnum.OPT_QUARTER_FAR );
        StockOptions quarterOptions = new StockOptions( quarterContract.getMyId( ), this, OptionsEnum.QUARTER_FAR );

        OptionsHandler optionsHandler = new OptionsHandler( this );
        optionsHandler.addOptions( monthOptions );
        optionsHandler.addOptions( quarterOptions );
        optionsHandler.setMainOptions( monthOptions );

        setOptionsHandler( optionsHandler );
    }

    @Override
    public void setIndex( double index ) {
        if ( this.index == 0 ) {
            getOptionsHandler( ).initOptions( index );

            // Request options tws
            if ( getApi( ) == ApiEnum.TWS ) {
                getTwsHandler( ).requestOptions( getOptionsHandler( ).getOptionsList( ) );
            }
        }

        this.index = index;
    }

}
