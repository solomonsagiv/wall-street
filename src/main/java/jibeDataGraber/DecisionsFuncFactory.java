package jibeDataGraber;

import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Ndx;
import serverObjects.indexObjects.Spx;

public class DecisionsFuncFactory {

    public static final String SPEED_900 = "SPEED_900";
    public static final String ACC_900 = "ACC_900";
    public static final String ACC_300 = "ACC_300";
    public static final String SESSION_4_VERSION_601 = "SESSION_4_VERSION_601";
    public static final String SESSION_4_VERSION_602 = "SESSION_4_VERSION_602";

    public static DecisionsFunc get_decision_func(BASE_CLIENT_OBJECT client, String decision_func_name) {

        if (client instanceof Spx) {
            // Switch case
            switch (decision_func_name.toUpperCase()) {
                case SPEED_900:
                    return new DecisionsFunc(SPEED_900, "data.research_spx500_501_speed_900");
                case ACC_900:
                    return new DecisionsFunc(ACC_900, "data.research_spx500_501_speed2_900");
                case ACC_300:
                    return new DecisionsFunc(ACC_300, "data.research_spx500_501_speed2_300");
                default:
                    break;
            }
        }

        if (client instanceof Ndx) {
            // Switch case
            switch (decision_func_name.toUpperCase()) {
                case SPEED_900:
                    return new DecisionsFunc(SPEED_900, "data.research_ndx_speed_900");
                case ACC_900:
                    return new DecisionsFunc(ACC_900, "data.research_ndx_speed2_900");
                case ACC_300:
                    return new DecisionsFunc(ACC_300, "data.research_ndx_speed2_300");
                case SESSION_4_VERSION_601:
                    return new DecisionsFunc(SESSION_4_VERSION_601, "data.ndx_decision_func");
                default:
                    break;
            }
        }

        return null;
    }

}
