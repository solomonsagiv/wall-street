package serverObjects.indexObjects;

import options.Options;
import options.OptionsHandler;
import org.hibernate.SessionFactory;
import serverObjects.BASE_CLIENT_OBJECT;

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

    public OptionsHandler getOptionsHandler() {
        if ( optionsHandler == null ) {
            optionsHandler = new OptionsHandler( this );
        }
        return optionsHandler;
    }

}
