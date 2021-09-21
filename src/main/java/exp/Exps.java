package exp;

import serverObjects.BASE_CLIENT_OBJECT;

import java.util.ArrayList;
import java.util.HashMap;

public class Exps {

    // Variables
    public BASE_CLIENT_OBJECT client;
    Exp mainExp;
    private HashMap<String, Exp> expMap = new HashMap<>();
    private ArrayList<Exp> expList = new ArrayList<>();

    // Constructor
    public Exps(BASE_CLIENT_OBJECT client) {
        this.client = client;
    }

    public void addExp(Exp exp) {
        expList.add(exp);
        expMap.put(exp.getName(), exp);
    }

    public boolean contains_exp(String exp_name) {
        for (Exp exp : expList) {
            if (exp.getName().equals(exp_name)) {
                return true;
            }
        }
        return false;
    }

    public Exp getExp(String name) {
        return getExpMap().get(name);
    }

    public Exp getExp(int index) {
        return getExpList().get(index);
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

    public Exp getMainExp() {
        if (mainExp == null) throw new NullPointerException("Main exp not set");
        return mainExp;
    }

    public void setMainExp(Exp mainExp) {
        this.mainExp = mainExp;
    }

}
