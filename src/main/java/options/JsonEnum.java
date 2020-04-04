package options;

public enum JsonEnum {

    private String s;

    private JsonEnum(String s) {
        this.s = s;
    }

    INTEREST("INTEREST"),
    DEVIDEND("DEVIDEND"),
    DATE,
    DAYS,
    TWS_CONTRACT,
    BID,
    ASK,
    LOW,
    HIGH,
    OPEN,
    INDEX_BID_ASK_COUNTER,
    DATA,
    PROPS,
    CONTRACT,
    CALL, PUT,
    STRIKE,
    OPTIONS,
    OP_AVG,
    OP,
    FUTURE,
    INDEX,
    BID_ASK_COUNTER,
    ID,
    SEC_TYPE,
    CURRENCY,
    EXCHANGE,
    TRADING_CLASS,
    MULTIPLIER,
    SYMBOL,
    INCLUDE_EXPIRED,
    LAST_TRADIND_DATE_OR_CONTRACT_MONTH
}
