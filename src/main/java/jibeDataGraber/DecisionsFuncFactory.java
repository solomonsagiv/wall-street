package jibeDataGraber;

import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Ndx;
import serverObjects.indexObjects.Spx;

public class DecisionsFuncFactory {

    public static final String SPEED_900 = "SPEED_900";
    public static final String ACC_900 = "ACC_900";
    public static final String ACC_300 = "ACC_300";

    public static final String DF_N_5 = "DF_N_5";
    public static final String DF_N_15 = "DF_N_15";
    public static final String DF_N_60 = "DF_N_60";
    public static final String DF_N_DAY = "DF_N_DAY";

    public static final String DF_5 = "DF_5";
    public static final String DF_15 = "DF_15";
    public static final String DF_60 = "DF_60";
    public static final String DF_DAY = "DF_DAY";


    public static final String SESSION_4_VERSION_601 = "SESSION_4_VERSION_601";
    public static final String SESSION_4_VERSION_602 = "SESSION_4_VERSION_602";

    public static DecisionsFunc get_decision_func(BASE_CLIENT_OBJECT client, String decision_func_name) {

        if (client instanceof Spx) {
            // Switch case
            switch (decision_func_name.toUpperCase()) {
                case DF_N_5:
                    return new DecisionsFunc(DF_N_5, "data.research_spx500_df_n_300_cdf");
                case DF_5:
                    return new DecisionsFunc(DF_5, "data.research_spx500_df_300_cdf");
                case DF_N_15:
                    return new DecisionsFunc(DF_N_15, "data.research_spx500_df_n_900_cdf");
                case DF_15:
                    return new DecisionsFunc(DF_15, "data.research_spx500_df_900_cdf");
                case DF_N_60:
                    return new DecisionsFunc(DF_N_60, "data.research_spx500_df_n_3600_cdf");
                case DF_60:
                    return new DecisionsFunc(DF_60, "data.research_spx500_df_3600_cdf");
                case DF_N_DAY:
                    return new DecisionsFunc(DF_N_DAY, "data.research_spx500_day_mood_cdf");
                case DF_DAY:
                    return new DecisionsFunc(DF_DAY, "data.research_spx500_day_mood_cdf");
                default:
                    break;
            }
        }

        if (client instanceof Ndx) {
            // Switch case
            switch (decision_func_name.toUpperCase()) {
                case DF_N_5:
                    return new DecisionsFunc(DF_N_5, "data.research_ndx_df_n_300_cdf");
                case DF_5:
                    return new DecisionsFunc(DF_5, "data.research_ndx_df_300_cdf");
                case DF_N_15:
                    return new DecisionsFunc(DF_N_15, "data.research_ndx_df_n_900_cdf");
                case DF_15:
                    return new DecisionsFunc(DF_15, "data.research_ndx_df_900_cdf");
                case DF_N_60:
                    return new DecisionsFunc(DF_N_60, "data.research_ndx_df_n_3600_cdf");
                case DF_60:
                    return new DecisionsFunc(DF_60, "data.research_ndx_df_3600_cdf");
                case DF_N_DAY:
                    return new DecisionsFunc(DF_N_DAY, "data.research_ndx_day_mood_cdf");
                case DF_DAY:
                    return new DecisionsFunc(DF_DAY, "data.research_ndx_day_mood_cdf");
                default:
                    break;
            }
        }

        return null;
    }

}
