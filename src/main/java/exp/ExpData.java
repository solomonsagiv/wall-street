package exp;

import locals.IJson;
import myJson.MyJson;
import options.JsonStrings;
import serverObjects.BASE_CLIENT_OBJECT;

public class ExpData implements IJson {

    // Variables
    String expName;
    BASE_CLIENT_OBJECT client;

    private double start = 0;
    private int indBidAskCounter = 0;

    // Constructor
    public ExpData(String expName, BASE_CLIENT_OBJECT client) {
        this.expName = expName;
        this.client = client;
    }

    private int getTotalIndBidAskCounter() {
        return indBidAskCounter + client.getIndexBidAskCounter();
    }

    public int getIndBidAskCounter() {
        return indBidAskCounter;
    }

    public void setIndBidAskCounter(int indBidAskCounter) {
        this.indBidAskCounter = indBidAskCounter;
    }

    public double getStart() {
        return start;
    }

    public void setStart(double start) {
        this.start = start;
    }

    @Override
    public MyJson getAsJson() {
        MyJson json = new MyJson();
        json.put(JsonStrings.start, start);
        json.put(JsonStrings.indBidAskCounter, indBidAskCounter);
        return json;
    }

    @Override
    public void loadFromJson(MyJson json) {
        setStart(json.getDouble(JsonStrings.start));
        setIndBidAskCounter(json.getInt(JsonStrings.indBidAskCounter));
    }

    @Override
    public MyJson getResetJson() {
        MyJson json = new MyJson();
        json.put(JsonStrings.start, start);
        json.put(JsonStrings.indBidAskCounter, getTotalIndBidAskCounter());
        return json;
    }
}