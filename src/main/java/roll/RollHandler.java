package roll;

import serverObjects.BASE_CLIENT_OBJECT;

import java.util.HashMap;
import java.util.Map;

public class RollHandler {

    // Variables
    Map<RollEnum, Roll> rollMap = new HashMap<>();
    BASE_CLIENT_OBJECT client;

    // Constructor
    public RollHandler(BASE_CLIENT_OBJECT client) {
        this.client = client;
    }

    // Funcs
    public void addRoll(RollEnum rollEnum, Roll roll) {
        rollMap.put(rollEnum, roll);
    }

    public Roll getRoll(RollEnum rollEnum) {
        if (!rollMap.containsKey(rollEnum))
            throw new NullPointerException(client.getName() + " Roll " + rollEnum.toString() + " not exist");
        return rollMap.get(rollEnum);
    }

    public Map<RollEnum, Roll> getRollMap() {
        return rollMap;
    }

}
