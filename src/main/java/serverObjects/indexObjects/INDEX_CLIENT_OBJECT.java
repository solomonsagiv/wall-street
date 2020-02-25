package serverObjects.indexObjects;

import options.IndexOptions;
import options.Options;
import options.OptionsEnum;
import options.OptionsHandler;
import org.hibernate.SessionFactory;
import serverObjects.BASE_CLIENT_OBJECT;
import tws.TwsContractsEnum;

public abstract class INDEX_CLIENT_OBJECT extends BASE_CLIENT_OBJECT {

    private double future = 0;

    public INDEX_CLIENT_OBJECT() {
        super( );
    }

    public void setFuture( double future ) {
        if ( this.future == 0 ) {
            this.future = future;
            getOptionsHandler( ).initOptions( future );
        }
    }

    public double getFuture() {
        return future;
    }

    @Override
    public void initOptionsHandler() {

        IndexOptions weekOptions = new IndexOptions( this, OptionsEnum.WEEK, getTwsData().getContract( TwsContractsEnum.WEEK ) );
        IndexOptions monthOptions = new IndexOptions( this, OptionsEnum.MONTH, getTwsData().getContract( TwsContractsEnum.MONTH ) );
        IndexOptions quarterOptions = new IndexOptions( this, OptionsEnum.QUARTER, getTwsData().getContract( TwsContractsEnum.QUARTER ) );

        OptionsHandler optionsHandler = new OptionsHandler( this ) {
            @Override
            public void initOptions() {
                addOptions( weekOptions );
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
}
