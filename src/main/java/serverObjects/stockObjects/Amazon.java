package serverObjects.stockObjects;

import DDE.DDECells;
import api.tws.requesters.AmazonRequester;
import serverObjects.ApiEnum;

public class Amazon extends STOCK_OBJECT {

    static Amazon client = null;

    // Constrtor
    public Amazon() {
        setName("amazon");
        setStrikeMargin(10);
        setDbId(5);
        setiTwsRequester(new AmazonRequester());
    }

    // Get instance
    public static Amazon getInstance() {
        if (client == null) {
            client = new Amazon();
        }
        return client;
    }

    @Override
    public double getTheoAvgMargin() {
        return 0.05;
    }

    @Override
    public ApiEnum getApi() {
        return ApiEnum.TWS;
    }

    @Override
    public void initBaseId() {
        setBaseId(50000);
    }
}
