package dataBase.mySql.mySqlComps;

public enum MySqlColumnEnum {

    id("id", MySqlDataTypeEnum.INT),
    NAME("id", MySqlDataTypeEnum.STRING),
    date("date", MySqlDataTypeEnum.STRING),
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
    exp_name("exp_name", MySqlDataTypeEnum.STRING),
    SPX_STOCKS("spx", MySqlDataTypeEnum.STRING),
    DAX_STOCKS("dax", MySqlDataTypeEnum.STRING),
    time("time", MySqlDataTypeEnum.STRING),
    CON("con", MySqlDataTypeEnum.DOUBLE),
    IND("ind", MySqlDataTypeEnum.DOUBLE),
    IND_BID("indBid", MySqlDataTypeEnum.DOUBLE),
    IND_ASK("indAsk", MySqlDataTypeEnum.DOUBLE),
    IND_BID_ASK_COUNTER("indBidAskCounter", MySqlDataTypeEnum.INT),
    CON_WEEK("conWeek", MySqlDataTypeEnum.DOUBLE),
    CON_WEEK_BID("conWeekBid", MySqlDataTypeEnum.DOUBLE),
    CON_WEEK_ASK("conWeekAsk", MySqlDataTypeEnum.DOUBLE),
    conWeekBidAskCounterList("conWeekBidAskCounterList", MySqlDataTypeEnum.STRING),
    CON_MONTH("conMonth", MySqlDataTypeEnum.DOUBLE),
    conMonthBidAskCounterList("conMonthBidAskCounterList", MySqlDataTypeEnum.STRING),
    conMonthBid("conMonthBid", MySqlDataTypeEnum.DOUBLE),
    conMonthAsk("conMonthAsk", MySqlDataTypeEnum.DOUBLE),
    CON_QUARTER("conQuarter", MySqlDataTypeEnum.DOUBLE),
    CON_QUARTER_BID("conQuarterBid", MySqlDataTypeEnum.DOUBLE),
    CON_QUARTER_ASK("conQuarterAsk", MySqlDataTypeEnum.DOUBLE),
    CON_QUARTER_FAR("conQuarterFar", MySqlDataTypeEnum.DOUBLE),
    CON_QUARTER_FAR_BID("conQuarterFarBid", MySqlDataTypeEnum.DOUBLE),
    CON_QUARTER_FAR_ASK("conQuarterFarAsk", MySqlDataTypeEnum.DOUBLE),
    secType("secType", MySqlDataTypeEnum.STRING),
    currency("currency", MySqlDataTypeEnum.STRING),
    exchange("exchange", MySqlDataTypeEnum.STRING),
    tradingClass("tradingClass", MySqlDataTypeEnum.STRING),
    multiplier("multiplier", MySqlDataTypeEnum.STRING),
    primaryExchange("primaryExchange", MySqlDataTypeEnum.STRING),
    symbol("symbol", MySqlDataTypeEnum.STRING),
    includExpired("includExpired", MySqlDataTypeEnum.STRING),
    indexList("indexList", MySqlDataTypeEnum.STRING),
    opList("opList", MySqlDataTypeEnum.DOUBLE),
    indexBidAskCounterList("indexBidAskCounterList", MySqlDataTypeEnum.STRING),
    quarterFutBidAskCounterList("quarterFutBidAskCounterList", MySqlDataTypeEnum.STRING),
    quarterFarFutBidAskCounterList("quarterFarFutBidAskCounterList", MySqlDataTypeEnum.STRING),
    lastTradingDayOrContractMonth("lastTradingDayOrContractMonth", MySqlDataTypeEnum.STRING),
    E1("e1", MySqlDataTypeEnum.DOUBLE),
    E1_BID("e1_bid", MySqlDataTypeEnum.DOUBLE),
    E1_ASK("e1_ask", MySqlDataTypeEnum.DOUBLE),
    E2("e2", MySqlDataTypeEnum.DOUBLE),
    E2_BID("e2_bid", MySqlDataTypeEnum.DOUBLE),
    E2_ASK("e2_ask", MySqlDataTypeEnum.DOUBLE),
    roll("roll", MySqlDataTypeEnum.DOUBLE),
    ROLL_AVG("rollAvg", MySqlDataTypeEnum.DOUBLE),
    opAvgFutureList("opAvgFutureList", MySqlDataTypeEnum.STRING),
    data("data", MySqlDataTypeEnum.STRING),
    e1DeltaList("e1DeltaList", MySqlDataTypeEnum.STRING);

    private final String name;
    private final MySqlDataTypeEnum dataType;

    MySqlColumnEnum(String name, MySqlDataTypeEnum dataType) {
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


