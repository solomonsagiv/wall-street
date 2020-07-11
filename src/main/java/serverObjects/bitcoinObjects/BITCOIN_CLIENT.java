package serverObjects.bitcoinObjects;

import exp.ExpEnum;
import exp.ExpMonth;
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
    public void loadFromJson( MyJson json ) {

    }

    @Override
    public MyJson getResetJson() {
        return null;
    }

    @Override
    public void initExpHandler() {
        Exps exps = new Exps( this );

        ExpMonth expMonth = new ExpMonth( this, ExpEnum.MONTH, TwsContractsEnum.OPT_MONTH, null );

        exps.addExp( expMonth, ExpEnum.MONTH );

        setExps( exps );
    }

    @Override
    public void initDDECells() {

    }

    @Override
    public ApiEnum getApi() {
        return ApiEnum.TWS;
    }

    @Override
    public void initBaseId() {

    }
}
