package jibeDataGraber;

import java.util.Map;

public class DecisionsFuncHandler {

    private Map<String, DecisionsFunc> map;

    public DecisionsFuncHandler(Map<String, DecisionsFunc> map) {
        this.map = map;
    }

    public DecisionsFunc get_decision_func(String decision_func_name) {
        return map.get(decision_func_name);
    }

    public Map<String, DecisionsFunc> getMap() {
        return map;
    }
}
