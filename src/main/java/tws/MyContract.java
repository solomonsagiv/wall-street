package tws;

import com.ib.client.Contract;

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

public class MyContract extends Contract {

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

    public MyContract() {
    }

    public MyContract(TwsContractsEnum type) {
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

    public void setType(TwsContractsEnum type) {
        this.type = type;
    }
}