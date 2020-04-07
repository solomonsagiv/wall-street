package props;

import api.tws.TwsHandler;
import locals.IJsonDataBase;
import myJson.MyJson;
import options.JsonEnum;
import serverObjects.BASE_CLIENT_OBJECT;

import java.time.LocalTime;

public class ClientProps {

    BASE_CLIENT_OBJECT client;
    public TradingHours tradingHours;

    // Constructor
    public ClientProps( BASE_CLIENT_OBJECT client ) {
        this.client = client;
        tradingHours = new TradingHours();
    }

    public TradingHours getTradingHours() {
        return tradingHours;
    }

}
