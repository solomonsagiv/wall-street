package serverObjects.bitcoinObjects;

public class Bitcoin extends BITCOIN_CLIENT {

    static Bitcoin client;

    // Get instance
    public static Bitcoin getInstance() {
        if (client == null) {
            client = new Bitcoin();
        }
        return client;
    }

}
