package serverObjects.stockObjects;

import DDE.DDECells;
import api.tws.requesters.MicrosoftRequester;
import serverObjects.ApiEnum;

public class Microsoft extends STOCK_OBJECT {

    static Microsoft client = null;

    // Constrtor
    public Microsoft() {
        setName("microsoft");
        setStrikeMargin(2.5);

        setDbId(9);
        setiTwsRequester(new MicrosoftRequester());
    }

    // Get instance
    public static Microsoft getInstance() {
        if (client == null) {
            client = new Microsoft();
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
        setBaseId(90000);
    }
}
