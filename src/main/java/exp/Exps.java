package exp;

import locals.IJson;
import locals.L;
import myJson.MyJson;
import options.fullOptions.PositionCalculator;
import serverObjects.BASE_CLIENT_OBJECT;

import java.util.ArrayList;
import java.util.HashMap;

public class Exps implements IJson {

    // Variables
    public BASE_CLIENT_OBJECT client;
    Exp mainExp;
    private HashMap<String, Exp> expMap = new HashMap<>();
    private ArrayList<Exp> expList = new ArrayList<>();
    private PositionCalculator positionCalculator;

    // Constructor
    public Exps(BASE_CLIENT_OBJECT client) {
        this.client = client;
        positionCalculator = new PositionCalculator(client);
    }

    public void addExp(Exp exp, String expName) {
        expList.add(exp);
        expMap.put(expName, exp);
    }

    private void initStartEndStrikes(double future) {

        double last = L.modulu(future);
        double margin = client.getStrikeMargin();

        double startStrike = last - (margin * 10);
        double endStrike = last + (margin * 10);

        client.setStartStrike(startStrike);
        client.setEndStrike(endStrike);

    }

    public void initOptions(double future) {

        initStartEndStrikes(future);

        for (Exp exp : getExpList()) {
            System.out.println("Init options: " + exp);
            exp.getOptions().initOptions();
        }

    }

    public Exp getExp(String name) {
        return getExpMap().get(name);
    }

    // Getters and setters
    public HashMap<String, Exp> getExpMap() {
        return expMap;
    }

    public void setExpMap(HashMap<String, Exp> expMap) {
        this.expMap = expMap;
    }

    public ArrayList<Exp> getExpList() {
        return expList;
    }

    public void setExpList(ArrayList<Exp> expList) {
        this.expList = expList;
    }

    public PositionCalculator getPositionCalculator() {
        return positionCalculator;
    }

    public void setPositionCalculator(PositionCalculator positionCalculator) {
        this.positionCalculator = positionCalculator;
    }

    public Exp getMainExp() {
        if (mainExp == null) throw new NullPointerException("Main exp not set");
        return mainExp;
    }

    public void setMainExp(Exp mainExp) {
        this.mainExp = mainExp;
    }

    @Override
    public MyJson getAsJson() {
        MyJson json = new MyJson();
        for (Exp exp : getExpList()) {
            json.put(exp.getName(), exp.getAsJson());
        }
        return json;
    }

    @Override
    public void loadFromJson(MyJson json) {
        for (String key : json.keySet()) {
            getExp(key).loadFromJson(new MyJson(json.getJSONObject(key).toString()));
        }
    }

    @Override
    public MyJson getResetJson() {
        MyJson json = new MyJson();
        for (Exp exp : getExpList()) {
            json.put(exp.getName(), exp.getResetJson());
        }
        return json;
    }
}
