package jibeDataGraber;

public class DecisionsFuncFactory {

    public static final String SPX_SPEED_900 = "SPX_SPEED_900";
//    public static final String SPX_ACC_900 = "SPX_ACC_900";
//    public static final String SPX_ACC_300 = "SPX_ACC_300";
//    public static final String NDX_SPEED_900 = "NDX_SPEED_900";
//    public static final String NDX_ACC_900 = "NDX_ACC_900";
//    public static final String NDX_ACC_300 = "NDX_ACC_300";

    public static final String SPEED_900 = "SPEED_900";
    public static final String ACC_900 = "ACC_900";
    public static final String ACC_300 = "ACC_300";

    public static DecisionsFunc get_decision_func(String decision_func_name) {
        // Switch case
        switch (decision_func_name.toUpperCase()) {
            case SPX_SPEED_900:
                return new DecisionsFunc(SPX_SPEED_900, "data.research_spx500_501_speed_900");
            default:
                break;
        }
        return null;
    }

}
