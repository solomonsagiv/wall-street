package arik.alerts;

import arik.Arik;
import arik.grabdata.ArikGrabData;
import java.util.ArrayList;

public class TA35_100000_Algo extends ArikAlgoAlert {

    // Constructor
    public TA35_100000_Algo(double target_price_for_position, ArrayList<Double> targets) {
        super(target_price_for_position);
        this.targets = targets;
    }

    @Override
    public void go() {

        double index = ArikGrabData.ta35_index;
        double v5 = ArikGrabData.ta35_v5;
        double v6 = ArikGrabData.ta35_v6;
        double pre_v5 = ArikGrabData.ta35_pre_v5;
        double pre_v6 = ArikGrabData.ta35_pre_v6;

        ArrayList<Double> dfs = new ArrayList<>();
        dfs.add(v5);
        dfs.add(v6);

        // --------------- LONG --------------- //
        // Enter long
        if (!LONG) {
            if (v5 > target_price_for_position && v6 > target_price_for_position) {
                LONG = true;
                Arik.getInstance().sendMessageToEveryOne("LONG \n" + " TA35 " + index);
            }
        }

        // --------------- SHORT --------------- //
        // Enter short
        if (!SHORT) {
            if (v5 < target_price_for_position * -1 && v6 < target_price_for_position * -1) {
                SHORT = true;
                Arik.getInstance().sendMessageToEveryOne("SHORT \n" + " TA35 " + index);
            }
        }

        // --------------- Alert for every time passing midpoint --------------- //
        for (double target : targets) {
            double target_minus = target * -1;

            // V5 check
            if (
                    ((pre_v5 < target && v5 > target) || (pre_v5 > target && v5 < target)) ||
                            ((pre_v5 < target_minus && v5 > target_minus) || (pre_v5 > target_minus && v5 < target_minus))
            ) {
                // Update pre data v5
                ArikGrabData.update_pre_v5();

                String text = String.format("TA35 index = %s \n V5 = %s \n V6 = %s", index, v5, v6);
                Arik.getInstance().sendMessageToSlo(text);
            }

            // V6 check
            if (
                    ((pre_v6 < target && v6 > target) || (pre_v6 > target && v6 < target)) ||
                            ((pre_v6 < target_minus && v6 > target_minus) || (pre_v6 > target_minus && v6 < target_minus))
            ) {
                // Update pre data v6
                ArikGrabData.update_pre_v6();

                String text = String.format("TA35 index = %s \n V5 = %s \n V6 = %s", index, v5, v6);
                Arik.getInstance().sendMessageToSlo(text);
            }
        }
    }
}
