package jibeDataGraber;

public class DecisionsFuncFactory {

    public static final String SPX_SPEED_900 = "SPX_SPEED_900";
    public static final String SPX_ACC_900 = "SPX_ACC_900";
    public static final String SPX_ACC_300 = "SPX_ACC_300";
    public static final String NDX_SPEED_900 = "NDX_SPEED_900";
    public static final String NDX_ACC_900 = "NDX_ACC_900";
    public static final String NDX_ACC_300 = "NDX_ACC_300";

    public static DecisionsFunc get_decision_func(String decision_func_name) {
        // Switch case
        switch (decision_func_name.toUpperCase()) {
            case SPX_SPEED_900:
                return new DecisionsFunc(SPX_SPEED_900, "data.research_spx500_501_speed_900");
            case SPX_ACC_900:
                return new DecisionsFunc(SPX_ACC_900, "data.research_spx500_501_speed2_900");
            case SPX_ACC_300:
                return new DecisionsFunc(SPX_ACC_300, "data.research_spx500_501_speed2_300");
            case NDX_SPEED_900:
                return new DecisionsFunc(NDX_SPEED_900, "data.research_ndx_speed_900");
            case NDX_ACC_900:
                return new DecisionsFunc(NDX_ACC_900, "data.research_ndx_speed2_900");
            case NDX_ACC_300:
                return new DecisionsFunc(NDX_ACC_300, "data.research_ndx_speed2_300");
            default:
                break;
        }
        return null;
    }

}
