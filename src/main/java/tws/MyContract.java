package tws;

import com.ib.client.Contract;
import locals.IJsonDataBase;
import myJson.MyJson;
import options.JsonEnum;

public class MyContract extends Contract implements IJsonDataBase {

    private int myId;
    private boolean requested;
    private TwsContractsEnum type;

    public MyContract(int myId, TwsContractsEnum type) {
        this.myId = myId;
        this.type = type;
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
        object.put(JsonEnum.ID.toString(), getMyId());
        object.put(JsonEnum.SEC_TYPE.toString(), secType());
        object.put(JsonEnum.CURRENCY.toString(), currency());
        object.put(JsonEnum.EXCHANGE.toString(), exchange());
        object.put(JsonEnum.TRADING_CLASS.toString(), tradingClass());
        object.put(JsonEnum.MULTIPLIER.toString(), multiplier());
        object.put(JsonEnum.SYMBOL.toString(), symbol());
        object.put(JsonEnum.INCLUDE_EXPIRED.toString(), includeExpired());
        object.put(JsonEnum.LAST_TRADIND_DATE_OR_CONTRACT_MONTH.toString(), lastTradeDateOrContractMonth());
        return object;
    }

    @Override
    public void loadFromJson(MyJson object) {
        setMyId(object.getInt(JsonEnum.ID.toString()));
        secType(object.getString(JsonEnum.SEC_TYPE.toString()));
        currency(object.getString(JsonEnum.CURRENCY.toString()));
        exchange(object.getString(JsonEnum.EXCHANGE.toString()));
        tradingClass(object.getString(JsonEnum.TRADING_CLASS.toString()));
        multiplier(object.getString(JsonEnum.MULTIPLIER.toString()));
        symbol(object.getString(JsonEnum.SYMBOL.toString()));
        includeExpired(object.getBoolean(JsonEnum.INCLUDE_EXPIRED.toString()));
        lastTradeDateOrContractMonth(object.getString(JsonEnum.LAST_TRADIND_DATE_OR_CONTRACT_MONTH.toString()));
    }


    public void setType( String string ) {
        switch ( string ) {
            case "INDEX":
                setType(TwsContractsEnum.INDEX);
                break;
            case "FUTURE":
                setType(TwsContractsEnum.FUTURE);
                break;
            case "OPT_WEEK":
                setType(TwsContractsEnum.OPT_WEEK);
                break;
            case "OPT_MONTH":
                setType(TwsContractsEnum.OPT_MONTH);
                break;
            case "OPT_QUARTER":
                setType(TwsContractsEnum.OPT_QUARTER);
                break;
            case "OPT_QUARTER_FAR":
                setType(TwsContractsEnum.OPT_QUARTER_FAR);
                break;
            default:
                break;
        }
    }

    @Override
    public MyJson getResetObject() {
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