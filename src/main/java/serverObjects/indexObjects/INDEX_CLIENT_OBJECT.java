package serverObjects.indexObjects;

import myJson.MyJson;
import options.JsonStrings;
import roll.RollEnum;
import serverObjects.BASE_CLIENT_OBJECT;

public abstract class INDEX_CLIENT_OBJECT extends BASE_CLIENT_OBJECT {


    public INDEX_CLIENT_OBJECT() {
        super();
    }

    @Override
    public MyJson getAsJson() {
        MyJson json = new MyJson();
        json.put(JsonStrings.ind, getIndex());
        json.put(JsonStrings.indBid, getIndexBid());
        json.put(JsonStrings.indAsk, getIndexAsk());
        json.put(JsonStrings.indBidAskCounter, getIndexBidAskCounter());
        json.put(JsonStrings.open, getOpen());
        json.put(JsonStrings.high, getHigh());
        json.put(JsonStrings.low, getLow());
        json.put(JsonStrings.base, getBase());
        json.put(JsonStrings.roll, getRollHandler().getRoll(RollEnum.E1_E2).getAsJson());
        json.put(JsonStrings.exps, getExps().getAsJson());
        return json;
    }

    @Override
    public void loadFromJson(MyJson json) {
        setIndexBidAskCounter(json.getInt(JsonStrings.indBidAskCounter));
        getExps().loadFromJson(new MyJson(json.getJSONObject(JsonStrings.exps)));
    }


    @Override
    public MyJson getResetJson() {
        MyJson json = new MyJson();
        json.put(JsonStrings.exps, exps.getResetJson());
        return json;
    }

}