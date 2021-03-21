package serverObjects.bitcoinObjects;

import serverObjects.ApiEnum;
import serverObjects.BASE_CLIENT_OBJECT;

public class BITCOIN_CLIENT extends BASE_CLIENT_OBJECT {

    @Override
    public double getTheoAvgMargin() {
        return 0;
    }


    @Override
    public ApiEnum getApi() {
        return ApiEnum.DDE;
    }
}
