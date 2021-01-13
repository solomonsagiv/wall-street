package serverObjects.bitcoinObjects;

import exp.ExpMonth;
import exp.ExpStrings;
import exp.Exps;
import myJson.MyJson;
import serverObjects.ApiEnum;
import serverObjects.BASE_CLIENT_OBJECT;
import tws.TwsContractsEnum;

public class BITCOIN_CLIENT extends BASE_CLIENT_OBJECT {

    @Override
    public double getTheoAvgMargin() {
        return 0;
    }

    @Override
    public void loadFromJson(MyJson json) {
    }

    @Override
    public MyJson getResetJson() {
        return null;
    }

    @Override
    public ApiEnum getApi() {
        return ApiEnum.TWS;
    }

    @Override
    public void initBaseId() {

    }
}
