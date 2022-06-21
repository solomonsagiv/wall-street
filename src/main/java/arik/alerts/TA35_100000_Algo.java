package arik.alerts;

import arik.Arik;
import arik.dataHandler.DataHandler;
import arik.dataHandler.DataObject;

import java.util.ArrayList;

public class TA35_100000_Algo extends ArikAlgoAlert {

    Arik arik;
    DataHandler dataHandler;

    DataObject ta35_index;
    DataObject ta35_df_5;
    DataObject ta35_df_6;


    // Constructor
    public TA35_100000_Algo(double target_price_for_position, ArrayList<Double> targets) {
        super(target_price_for_position);
        this.targets = targets;
        arik = Arik.getInstance();
        dataHandler = arik.getDataHandler();

        ta35_index = dataHandler.get(DataHandler.TA35_INDEX);
        ta35_df_5 = dataHandler.get(DataHandler.TA35_DF_5);
        ta35_df_6 = dataHandler.get(DataHandler.TA35_DF_6);

    }

    @Override
    public void go() {

        double index = arik.getDataHandler().get(DataHandler.TA35_INDEX).getValue();
        double v5 = ta35_df_5.getValue();
        double v6 = ta35_df_6.getValue();
        double pre_v5 = ta35_df_5.getPre_value();
        double pre_v6 = ta35_df_6.getPre_value();

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
//                ArikGrabData.update_pre_v5();

                String text = String.format("TA35 index = %s \n V5 = %s \n V6 = %s", index, v5, v6);
                Arik.getInstance().sendMessageToSlo(text);
            }

            // V6 check
            if (
                    ((pre_v6 < target && v6 > target) || (pre_v6 > target && v6 < target)) ||
                            ((pre_v6 < target_minus && v6 > target_minus) || (pre_v6 > target_minus && v6 < target_minus))
            ) {
                // Update pre data v6
//                ArikGrabData.update_pre_v6();

                String text = String.format("TA35 index = %s \n V5 = %s \n V6 = %s", index, v5, v6);
                Arik.getInstance().sendMessageToSlo(text);
            }
        }
    }
}
