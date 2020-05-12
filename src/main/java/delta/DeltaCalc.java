package delta;

import options.Options;
import serverObjects.indexObjects.INDEX_CLIENT_OBJECT;

public class DeltaCalc {

    INDEX_CLIENT_OBJECT client;

    public DeltaCalc( INDEX_CLIENT_OBJECT client ) {
        this.client = client;
    }

    public void calc( Options options, int newVolume, double newLast ) {

        double quantity = newVolume - options.getFutureVolume( );
        double delta = 0;

        // Buy ( Last == pre ask )
        if ( newLast == options.getFutureAsk() ) {
            delta = quantity;
        }

        // Buy ( Last == pre bid )
        if ( newLast == options.getFutureBid() ) {
            delta = quantity * -1;
        }

        options.appendFutureDelta( delta );

        System.out.println( );
        System.out.println("Dax delta: " + options.getFutureDelta() + "$$$" );
    }

}
