package serverObjects.bitcoinObjects;

import serverObjects.indexObjects.Dax;

public class Bitcoin extends BITCOIN_CLIENT {

    static Bitcoin client;

    // Get instance
    public static Bitcoin getInstance() {
        if ( client == null ) {
            client = new Bitcoin( );
        }
        return client;
    }

}
