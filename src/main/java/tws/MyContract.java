package tws;

import com.ib.client.Contract;
import locals.IJson;
import myJson.MyJson;
import options.JsonEnum;

public class MyContract extends Contract implements IJson {

    private int myId;
    private boolean requested;
    private TwsContractsEnum type;

    public MyContract(int myId, TwsContractsEnum type) {
        this.myId = myId;
        this.type = type;
    }


    public MyContract(MyContract contract) {
        setMyId(contract.getMyId());
        symbol(contract.symbol());
        secType(contract.secType());
        primaryExch(contract.primaryExch());
        currency(contract.currency());
        tradingClass(contract.tradingClass());
        multiplier(contract.multiplier());
        includeExpired(contract.includeExpired());
        exchange(contract.exchange());
        localSymbol(contract.localSymbol());
        lastTradeDateOrContractMonth(contract.lastTradeDateOrContractMonth());
    }

    @Override
    public String toString() {
        return super.toString() + " \n" +
                "myId=" + myId +
                ", requested=" + requested +
                ", type=" + type +
                '}';
    }

    @Override
    public MyJson getAsJson() {
        MyJson object = new MyJson();
        object.put(JsonEnum.id.toString(), getMyId());
        object.put(JsonEnum.secType.toString(), secType());
        object.put(JsonEnum.currency.toString(), currency());
        object.put(JsonEnum.exchange.toString(), exchange());
        object.put(JsonEnum.tradingClass.toString(), tradingClass());
        object.put(JsonEnum.multiplier.toString(), multiplier());
        object.put(JsonEnum.symbol.toString(), symbol());
        object.put(JsonEnum.includeExpired.toString(), includeExpired());
        object.put(JsonEnum.lastTradingDateOrContractMonth.toString(), lastTradeDateOrContractMonth());
        return object;
    }

    @Override
    public void loadFromJson(MyJson object) {
        setMyId(object.getInt(JsonEnum.id.toString()));
        secType(object.getString(JsonEnum.secType.toString()));
        currency(object.getString(JsonEnum.currency.toString()));
        exchange(object.getString(JsonEnum.exchange.toString()));
        tradingClass(object.getString(JsonEnum.tradingClass.toString()));
        multiplier(object.getString(JsonEnum.multiplier.toString()));
        symbol(object.getString(JsonEnum.symbol.toString()));
        includeExpired(object.getBoolean(JsonEnum.includeExpired.toString()));
        lastTradeDateOrContractMonth(object.getString(JsonEnum.lastTradingDateOrContractMonth.toString()));
    }

    public void setType(String string) {
        switch (string) {
            case "INDEX":
                setType(TwsContractsEnum.INDEX);
                break;
            case "FUTURE":
                setType(TwsContractsEnum.FUTURE);
                break;
            case "FUTURE_FAR":
                setType(TwsContractsEnum.FUTURE_FAR);
                break;
            case "OPT_WEEK":
                setType(TwsContractsEnum.OPT_WEEK);
                break;
            case "OPT_MONTH":
                setType(TwsContractsEnum.OPT_MONTH);
                break;
            case "OPT_QUARTER":
                setType(TwsContractsEnum.OPT_E1);
                break;
            case "OPT_QUARTER_FAR":
                setType(TwsContractsEnum.OPT_E2);
                break;
            default:
                break;
        }
    }

    @Override
    public MyJson getResetJson() {
        return getAsJson();
    }

    public MyContract() {
    }

    public MyContract(TwsContractsEnum type) {
        this.type = type;
    }

    public int getMyId() {
        return myId;
    }

    public void setMyId(int myId) {
        this.myId = myId;
    }

    public boolean isRequested() {
        return requested;
    }

    public void setRequested(boolean requested) {
        this.requested = requested;
    }

    public TwsContractsEnum getType() {
        return type;
    }

    public void setType(TwsContractsEnum type) {
        this.type = type;
    }
}

enum MyContractEnum {
    ID,
    SEC_TYPE,
    CURRENCY,
    EXCHANGE,
    TRADING_CLASS,
    MULTIPLIER,
    SYMBOL,
    INCLUDE_EXPIRED
}