package serverObjects.stockObjects;

import DDE.DDECells;
import api.tws.requesters.AppleRequester;
import serverObjects.ApiEnum;

public class Apple extends STOCK_OBJECT {

    static Apple client = null;

    // Constrtor
    public Apple() {
        setName("apple");
        setStrikeMargin(5);

        setDbId(4);
        setiTwsRequester(new AppleRequester());
    }

    // Get instance
    public static Apple getInstance() {
        if (client == null) {
            client = new Apple();
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
        setBaseId(30000);
    }
}
