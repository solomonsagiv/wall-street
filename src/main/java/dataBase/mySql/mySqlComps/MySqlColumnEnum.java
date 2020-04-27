package dataBase.mySql.mySqlComps;

public enum MySqlColumnEnum {

    ID("id", MySqlDataTypeEnum.INT),
    NAME("id", MySqlDataTypeEnum.STRING),
    DATE("date", MySqlDataTypeEnum.STRING),
    OPEN("open", MySqlDataTypeEnum.DOUBLE),
    HIGH("high", MySqlDataTypeEnum.DOUBLE),
    LOW("low", MySqlDataTypeEnum.DOUBLE),
    CLOSE("close", MySqlDataTypeEnum.DOUBLE),
    CON_UP("con_up", MySqlDataTypeEnum.INT),
    CON_DOWN("con_down", MySqlDataTypeEnum.INT),
    IND_UP("index_up", MySqlDataTypeEnum.INT),
    IND_DOWN("index_down", MySqlDataTypeEnum.INT),
    OP_AVG("op_avg", MySqlDataTypeEnum.DOUBLE),
    FUT_BID_ASK_COUNTER("fut_bid_ask_counter", MySqlDataTypeEnum.INT),
    BASE("base", MySqlDataTypeEnum.DOUBLE),
    OPTIONS("options", MySqlDataTypeEnum.STRING),
    CON_BID_ASK_COUNTER("con_bid_ask_counter", MySqlDataTypeEnum.INT),
    EXP_NAME("exp_name", MySqlDataTypeEnum.STRING),
    TIME("time", MySqlDataTypeEnum.STRING),
    CON("con", MySqlDataTypeEnum.DOUBLE),
    IND("ind", MySqlDataTypeEnum.DOUBLE),
    IND_BID("indBid", MySqlDataTypeEnum.DOUBLE),
    IND_ASK("indAsk", MySqlDataTypeEnum.DOUBLE),
    IND_BID_ASK_COUNTER("indBidAskCounter", MySqlDataTypeEnum.INT),
    CON_WEEK("conWeek", MySqlDataTypeEnum.DOUBLE),
    CON_WEEK_BID("conWeekBid", MySqlDataTypeEnum.DOUBLE),
    CON_WEEK_ASK("conWeekAsk", MySqlDataTypeEnum.DOUBLE),
    CON_WEEK_BID_ASK_COUNTER_LIST("conWeekBidAskCounterList",MySqlDataTypeEnum.STRING ),
    CON_MONTH("conMonth", MySqlDataTypeEnum.DOUBLE),
    CON_MONTH_BID_ASK_COUNTER_LIST("conMonthBidAskCounterList",MySqlDataTypeEnum.STRING ),
    CON_MONTH_BID("conMonthBid", MySqlDataTypeEnum.DOUBLE),
    CON_MONTH_ASK("conMonthAsk", MySqlDataTypeEnum.DOUBLE),
    CON_QUARTER("conQuarter", MySqlDataTypeEnum.DOUBLE),
    CON_QUARTER_BID("conQuarterBid", MySqlDataTypeEnum.DOUBLE),
    CON_QUARTER_ASK("conQuarterAsk", MySqlDataTypeEnum.DOUBLE),
    CON_QUARTER_FAR("conQuarterFar", MySqlDataTypeEnum.DOUBLE),
    CON_QUARTER_FAR_BID("conQuarterFarBid", MySqlDataTypeEnum.DOUBLE),
    CON_QUARTER_FAR_ASK("conQuarterFarAsk", MySqlDataTypeEnum.DOUBLE),
    SEC_TYPE("secType", MySqlDataTypeEnum.STRING),
    CURRENCY("currency", MySqlDataTypeEnum.STRING),
    EXCHANGE("exchange", MySqlDataTypeEnum.STRING),
    TRADING_CLASS("tradingClass", MySqlDataTypeEnum.STRING),
    MULTIPLIER("multiplier", MySqlDataTypeEnum.STRING),
    PRIMARY_EXCHANGE("primaryExchange", MySqlDataTypeEnum.STRING),
    SYMBOL("symbol", MySqlDataTypeEnum.STRING),
    INCLUD_EXPIRED("includExpired", MySqlDataTypeEnum.STRING),
    INDEX_LIST("indexList", MySqlDataTypeEnum.STRING),
    OP_LIST("opList", MySqlDataTypeEnum.DOUBLE),
    IND_BID_ASK_COUNTER_LIST("indexBidAskCounterList", MySqlDataTypeEnum.STRING),
    QUARTER_FUT_BID_ASK_COUNTER_LIST("quarterFutBidAskCounterList", MySqlDataTypeEnum.STRING),
    QUARTER_FAR_FUT_BID_ASK_COUNTER_LIST("quarterFarFutBidAskCounterList", MySqlDataTypeEnum.STRING),
    LAST_TRADING_DATE_OR_CONTRACT_MONTH("lastTradingDayOrContractMonth", MySqlDataTypeEnum.STRING),
    E1("e1", MySqlDataTypeEnum.DOUBLE),
    E1_BID("e1_bid", MySqlDataTypeEnum.DOUBLE),
    E1_ASK("e1_ask", MySqlDataTypeEnum.DOUBLE),
    E2("e2", MySqlDataTypeEnum.DOUBLE),
    E2_BID("e2_bid", MySqlDataTypeEnum.DOUBLE),
    E2_ASK("e2_ask", MySqlDataTypeEnum.DOUBLE),
    ROLL("roll", MySqlDataTypeEnum.DOUBLE),
    ROLL_AVG("rollAvg", MySqlDataTypeEnum.DOUBLE);

    private final String name;
    private final MySqlDataTypeEnum dataType;

    MySqlColumnEnum(String name, MySqlDataTypeEnum dataType ) {
        this.name = name;
        this.dataType = dataType;
    }

    public String getName() {
        return name;
    }

    public MySqlDataTypeEnum getDataType() {
        return dataType;
    }


}


